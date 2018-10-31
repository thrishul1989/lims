package com.todaysoft.lims.testing.abnormal.service.core;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Lists;
import com.todaysoft.lims.testing.abnormal.model.AbnormalTaskSolveRequest;
import com.todaysoft.lims.testing.abnormal.service.impl.AbnormalSolveContext;
import com.todaysoft.lims.testing.base.adapter.IBiologyAdapter;
import com.todaysoft.lims.testing.base.entity.BiologyAnnotationTask;
import com.todaysoft.lims.testing.base.entity.Order;
import com.todaysoft.lims.testing.base.entity.SangerVerifyRecord;
import com.todaysoft.lims.testing.base.entity.TechnicalAnalyTestingTask;
import com.todaysoft.lims.testing.base.entity.TestingMethod;
import com.todaysoft.lims.testing.base.entity.TestingSample;
import com.todaysoft.lims.testing.base.entity.TestingSangerTestSample;
import com.todaysoft.lims.testing.base.entity.TestingSchedule;
import com.todaysoft.lims.testing.base.entity.TestingScheduleActive;
import com.todaysoft.lims.testing.base.entity.TestingScheduleHistory;
import com.todaysoft.lims.testing.base.entity.TestingTask;
import com.todaysoft.lims.testing.base.entity.TestingTaskRunVariable;
import com.todaysoft.lims.testing.base.entity.TestingTechnicalAnalyRecord;
import com.todaysoft.lims.testing.base.model.TaskConfig;
import com.todaysoft.lims.testing.base.model.TaskSemantic;
import com.todaysoft.lims.testing.base.service.ITestingScheduleService;
import com.todaysoft.lims.testing.base.service.ITestingTaskService;
import com.todaysoft.lims.testing.base.service.adapter.bcm.BCMAdapter;
import com.todaysoft.lims.testing.base.utils.JsonUtils;
import com.todaysoft.lims.testing.cdnaext.model.CDNAExtractTaskVariables;
import com.todaysoft.lims.testing.dnaext.model.DNAExtractTaskVariables;
import com.todaysoft.lims.testing.dnaqc.model.DNAQcTaskVariables;
import com.todaysoft.lims.testing.dt.model.DTTaskVariables;
import com.todaysoft.lims.testing.dtdata.model.DTDataTaskVariables;
import com.todaysoft.lims.testing.firstpcr.model.FirstPcrTaskVariables;
import com.todaysoft.lims.testing.firstpcrsanger.model.FirstPcrSangerTaskVariables;
import com.todaysoft.lims.testing.libcap.model.LibraryCaptureTaskVariables;
import com.todaysoft.lims.testing.libcre.model.LibraryCreateTaskVariables;
import com.todaysoft.lims.testing.mlpatest.model.MlpaTestTaskVariables;
import com.todaysoft.lims.testing.pcrngsprimerdesign.model.PcrNgsPrimerDesignTaskVariables;
import com.todaysoft.lims.testing.pcrngstest.model.PcrNgsTestTaskVariables;
import com.todaysoft.lims.testing.primerdesign.model.PrimerDesignTaskVariables;
import com.todaysoft.lims.testing.secondpcr.model.SecondPcrTaskVariables;
import com.todaysoft.lims.testing.secondpcrsanger.model.SecondPcrSangerTaskVariables;
import com.todaysoft.lims.testing.technicalanaly.service.ITechnicalAnalyService;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.IdGen;
import com.todaysoft.lims.utils.StringUtils;

@Component
public class SpecifyNodeSolver extends AbstractAbnormalSolver
{
    @Autowired
    private BCMAdapter bcmadapter;
    
    @Autowired
    private ITestingTaskService testingTaskService;
    
    @Autowired
    private ITestingScheduleService testingScheduleService;
    
    @Autowired
    private ITechnicalAnalyService technicalAnalyService;
    
    @Autowired
    private IBiologyAdapter biologyAdapter;
    
    @Override
    protected String process(AbnormalTaskSolveRequest request, List<String> scheduleIds, String token)
    {
        String strategy = request.getStrategy();
        String remark = request.getRemark();
        String inputSampleId = request.getInputSampleId();
        String semantic = request.getSemantic();
        TestingTask abnormalTask = null;
        List<TestingSchedule> schedules = Lists.newArrayList();
        if (TaskSemantic.TECHNICAL_ANALYSIS_TASK.equals(semantic))
        {
            schedules = testingScheduleService.getScheduleHistorys(request.getId());
        }
        else if (TaskSemantic.BIOLOGY_ANNOTATION.equals(semantic))
        {
            schedules = testingScheduleService.getScheduleHistorys(request.getId());
            if (schedules.size() > 1) //生信注释任务关联多个流程，说明是pcr-ngs
            {
                TestingSchedule sc = schedules.get(0);
                schedules.removeAll(schedules);
                schedules.add(sc);
            }
        }
        else
        {
            abnormalTask = baseDaoSupport.get(TestingTask.class, request.getId());
            if (null == abnormalTask)
            {
                return "";
            }
            schedules = testingScheduleService.getRelatedSchedulesByTestingTask(abnormalTask.getId());
            if (CollectionUtils.isEmpty(schedules))
            {
                return "";
            }
        }
        
        TestingTask solveTask = null;
        AbnormalSolveContext context = new AbnormalSolveContext();
        
        TestingMethod testingMethod;
        Map<String, TestingTask> cacheMap = new HashMap<String, TestingTask>();
        for (TestingSchedule schedule : schedules)
        {
            testingMethod = baseDaoSupport.get(TestingMethod.class, schedule.getMethodId());
            if (null == testingMethod)
            {
                throw new IllegalStateException();
            }
            TaskConfig config = bcmadapter.getTaskConfigBySemantic(strategy);
            
            if (null == config)
            {
                throw new IllegalStateException();
            }
            TestingTask lastTask;
            TestingSangerTestSample testingSangerTestSample;
            FirstPcrSangerTaskVariables firstPcrSangerTaskVariables;
            //如果是sanger检测的，所有操作都要特殊处理
            if (TestingMethod.SANGER_TEST.equals(testingMethod.getSemantic()))
            {
                if (TaskSemantic.DNA_EXTRACT.equals(config.getSemantic()) || TaskSemantic.DNA_QC.equals(config.getSemantic()))
                {
                    //重新做DNA提取 这个实验相关的所有引物都要重新做Sanger检测，也就是重新做这个检测产品
                    //相关任务未下达的直接结束，且删掉avtive表  已经下达的设置运行状态为1 即已取消 这个任务可做可不做，但是不用创建下一步任务了
                    lastTask = testingScheduleService.getScheduleNodeLastTestingTaskComplete(schedule.getId(), strategy);
                    if (null == lastTask)
                    {
                        lastTask = testingScheduleService.getScheduleNodeLastTestingTaskComplete(schedule.getId(), TaskSemantic.DNA_QC);
                        strategy = TaskSemantic.DNA_QC;
                        config = bcmadapter.getTaskConfigBySemantic(strategy);
                    }
                    
                    //如果直接原始原本是DNA，则不提取DNA，直接当重新质检处理
                    if (null == lastTask)
                    {
                        throw new IllegalStateException();
                    }
                    
                    List<TestingScheduleActive> listActive = testingScheduleService.getScheduleActives(schedule.getId());
                    TestingTask activeTask;
                    List<String> sangerStr = Arrays.asList(TaskSemantic.SANGER_PCR_ONE, TaskSemantic.SANGER_PCR_TWO, TaskSemantic.SANGER_DATA_ANALYSIS);
                    for (TestingScheduleActive active : listActive)
                    {
                        activeTask = baseDaoSupport.get(TestingTask.class, active.getTaskId());
                        if (null == activeTask || !sangerStr.contains(activeTask.getSemantic()))
                        {
                            continue;
                        }
                        if (TestingTask.STATUS_ASSIGNABLE == activeTask.getStatus().intValue())
                        {
                            activeTask.setStatus(TestingTask.STATUS_END);
                            activeTask.setEndTime(new Date());
                            activeTask.setEndType(TestingTask.END_FAILURE);
                            baseDaoSupport.update(activeTask);
                            baseDaoSupport.delete(active);
                        }
                        else
                        {
                            active.setRunningStatus(TestingScheduleActive.STATUS_CANCER);
                            baseDaoSupport.update(active);
                        }
                    }
                    
                    solveTask = context.getSolvedTask(lastTask);
                    
                    TestingSample testingSample = baseDaoSupport.get(TestingSample.class, inputSampleId);
                    TestingTask dnaTask = null;
                    if (TaskSemantic.DNA_EXTRACT.equals(config.getSemantic()))
                    {
                        dnaTask = testingTaskService.getUncompletedTestingTask(testingSample.getReceivedSample().getSampleId(), TaskSemantic.DNA_EXTRACT);
                    }
                    else
                    {
                        dnaTask = testingTaskService.getUncompletedTestingTask(testingSample.getId(), TaskSemantic.DNA_QC);
                    }
                    
                    if (null == solveTask && null == dnaTask)
                    {
                        solveTask = new TestingTask();
                        solveTask.setName(config.getName());
                        solveTask.setSemantic(config.getSemantic());
                        solveTask.setStatus(TestingTask.STATUS_ASSIGNABLE);
                        solveTask.setResubmit(true);
                        solveTask.setResubmitCount(null == lastTask.getResubmitCount() ? 1 : (lastTask.getResubmitCount() + 1));
                        solveTask.setStartTime(new Date());
                        solveTask.setInputSample(lastTask.getInputSample());
                        baseDaoSupport.insert(solveTask);
                        
                        TestingTaskRunVariable variables = new TestingTaskRunVariable();
                        variables.setTestingTaskId(solveTask.getId());
                        String jsons = getJsonRemarkStrByTaskSemantic(strategy, remark, lastTask);
                        variables.setText(jsons);
                        baseDaoSupport.insert(variables);
                        context.solve(lastTask, solveTask);
                        
                    }
                    TestingScheduleActive activeInsert = new TestingScheduleActive();
                    activeInsert.setSchedule(schedule);
                    activeInsert.setTaskId(solveTask.getId());
                    activeInsert.setRunningStatus(TestingScheduleActive.STATUS_NORMAL);
                    baseDaoSupport.insert(activeInsert);
                    
                    TestingScheduleHistory history = new TestingScheduleHistory();
                    history.setScheduleId(schedule.getId());
                    history.setTaskId(solveTask.getId());
                    history.setTimestamp(new Date());
                    baseDaoSupport.insert(history);
                    
                    testingTaskService.updateTaskRedundantColumn(Arrays.asList(solveTask), 0);
                }
                else if (TaskSemantic.SANGER_PCR_ONE.equals(config.getSemantic()))
                {
                    if (StringUtils.isEmpty(inputSampleId))
                    {
                        throw new IllegalStateException();
                    }
                    testingSangerTestSample = testingScheduleService.getSangerTestPcrOneBySampleId(inputSampleId);
                    
                    if (null == testingSangerTestSample)//说明是一次PCR直接取消的，不是数据分析取消的
                    {
                        lastTask = abnormalTask;
                    }
                    else
                    {
                        
                        lastTask = testingSangerTestSample.getTestingTask();
                    }
                    
                    solveTask = context.getSolvedTask(lastTask);
                    
                    firstPcrSangerTaskVariables = getPcrOneTaskRunningVariables(lastTask.getId());
                    
                    if (null == solveTask)
                    {
                        solveTask = new TestingTask();
                        solveTask.setName(config.getName());
                        solveTask.setSemantic(config.getSemantic());
                        solveTask.setStatus(TestingTask.STATUS_ASSIGNABLE);
                        solveTask.setResubmit(true);
                        solveTask.setResubmitCount(null == lastTask.getResubmitCount() ? 1 : (lastTask.getResubmitCount() + 1));
                        solveTask.setStartTime(new Date());
                        solveTask.setInputSample(lastTask.getInputSample());
                        baseDaoSupport.insert(solveTask);
                        
                        TestingTaskRunVariable variables = new TestingTaskRunVariable();
                        variables.setTestingTaskId(solveTask.getId());
                        if (null != testingSangerTestSample)
                        {
                            firstPcrSangerTaskVariables.setPrimerId(testingSangerTestSample.getPrimer().getId());
                        }
                        firstPcrSangerTaskVariables.setRemark(remark);
                        variables.setText(JsonUtils.asJson(firstPcrSangerTaskVariables));
                        baseDaoSupport.insert(variables);
                        
                        context.solve(lastTask, solveTask);
                    }
                    
                    TestingScheduleActive active = new TestingScheduleActive();
                    active.setSchedule(schedule);
                    active.setTaskId(solveTask.getId());
                    baseDaoSupport.insert(active);
                    
                    TestingScheduleHistory history = new TestingScheduleHistory();
                    history.setScheduleId(schedule.getId());
                    history.setTaskId(solveTask.getId());
                    history.setTimestamp(new Date());
                    baseDaoSupport.insert(history);
                    
                    testingTaskService.updateTaskRedundantColumn(Arrays.asList(solveTask), 0);
                    
                }
                else if (TaskSemantic.SANGER_PCR_TWO.equals(config.getSemantic()) || TaskSemantic.SANGER_PCR_TWO_REVERSE.equals(config.getSemantic()))
                {
                    
                    config = bcmadapter.getTaskConfigBySemantic(TaskSemantic.SANGER_PCR_TWO);
                    
                    if (StringUtils.isEmpty(inputSampleId))
                    {
                        throw new IllegalStateException();
                    }
                    testingSangerTestSample = testingScheduleService.getSangerTestPcrOneBySampleId(inputSampleId);
                    
                    List<TestingTask> taskList = testingTaskService.getRelatedTasks(inputSampleId, TaskSemantic.SANGER_PCR_TWO);
                    
                    if (Collections3.isEmpty(taskList))
                    {
                        throw new IllegalStateException();
                    }
                    
                    lastTask = Collections3.getFirst(taskList);
                    
                    SecondPcrSangerTaskVariables secondPcrVariable = getPcrTwoTaskRunningVariables(lastTask.getId());
                    
                    solveTask = context.getSolvedTask(lastTask);
                    
                    if (null == solveTask)
                    {
                        solveTask = new TestingTask();
                        solveTask.setName(config.getName());
                        solveTask.setSemantic(config.getSemantic());
                        solveTask.setStatus(TestingTask.STATUS_ASSIGNABLE);
                        solveTask.setResubmit(true);
                        solveTask.setResubmitCount(null == lastTask.getResubmitCount() ? 1 : (lastTask.getResubmitCount() + 1));
                        solveTask.setStartTime(new Date());
                        solveTask.setInputSample(lastTask.getInputSample());
                        baseDaoSupport.insert(solveTask);
                        
                        TestingTaskRunVariable variables = new TestingTaskRunVariable();
                        variables.setTestingTaskId(solveTask.getId());
                        secondPcrVariable.setRemark(request.getRemark());
                        secondPcrVariable.setFlag(2);
                        if (TaskSemantic.SANGER_PCR_TWO.equals(strategy))
                        {
                            secondPcrVariable.setForwardFlag(1);
                        }
                        else
                        {
                            secondPcrVariable.setForwardFlag(2);
                        }
                        secondPcrVariable.setRemark(remark);
                        secondPcrVariable.setCombineCode(testingSangerTestSample.getCombineCode());
                        secondPcrVariable.setPrimerId(testingSangerTestSample.getPrimer().getId());
                        
                        variables.setText(JsonUtils.asJson(secondPcrVariable));
                        baseDaoSupport.insert(variables);
                        
                        context.solve(lastTask, solveTask);
                    }
                    
                    TestingScheduleActive active = new TestingScheduleActive();
                    active.setSchedule(schedule);
                    active.setTaskId(solveTask.getId());
                    active.setRunningStatus(TestingScheduleActive.STATUS_NORMAL);
                    baseDaoSupport.insert(active);
                    
                    TestingScheduleHistory history = new TestingScheduleHistory();
                    history.setScheduleId(schedule.getId());
                    history.setTaskId(solveTask.getId());
                    history.setTimestamp(new Date());
                    baseDaoSupport.insert(history);
                    
                    testingTaskService.updateTaskRedundantColumn(Arrays.asList(solveTask), 0);
                }
                
                List<TestingScheduleActive> listActive = testingScheduleService.getRunningScheduleActives(schedule.getId());
                
                String activeName = testingScheduleService.getScheduleActiveName(schedule, listActive);
                
                if (StringUtils.isEmpty(activeName))
                {
                    schedule.setActiveTask("已完成");
                    schedule.setEndType(TestingSchedule.END_SUCCESS);
                    schedule.setEndTime(new Date());
                }
                else
                {
                    schedule.setActiveTask(activeName);
                }
                baseDaoSupport.update(schedule);
            }
            else
            {
                if (TaskSemantic.BIOLOGY_ANNOTATION.equals(strategy))//生信注释特殊处理
                {
                    BiologyAnnotationTask reAnnotationTask = new BiologyAnnotationTask();
                    if (TaskSemantic.BIOLOGY_ANNOTATION.equals(semantic))//生信注释任务异常处理选择重新注释
                    {
                        BiologyAnnotationTask biologyAnnotationTask = baseDaoSupport.get(BiologyAnnotationTask.class, request.getId());//直接获取生信注释的相关信息
                        reAnnotationTask = new BiologyAnnotationTask();//重新生成task任务
                        BeanUtils.copyProperties(biologyAnnotationTask, reAnnotationTask);
                        reAnnotationTask.setId(IdGen.uuid());
                    }
                    else
                    {//技术分析或者新技术分析异常上报处理选择重新注释（新技术分析异常处理目前没有选择重新注释的功能）
                        List<TestingSchedule> testingSchedules = testingScheduleService.getScheduleHistorys(request.getId());
                        if (Collections3.isNotEmpty(testingSchedules))
                        {
                            List<TestingScheduleHistory> histories =
                                testingScheduleService.getTestingScheduleHistoryByScheduleID(testingSchedules.get(0).getId());
                            if (Collections3.isNotEmpty(histories))
                            {
                                for (TestingScheduleHistory hi : histories)
                                {
                                    if (StringUtils.isNotEmpty(hi.getTaskRefer()) && TaskSemantic.BIOLOGY_ANNOTATION.equals(hi.getTaskRefer()))
                                    {
                                        reAnnotationTask = baseDaoSupport.get(BiologyAnnotationTask.class, hi.getTaskId());
                                        break;
                                    }
                                }
                            }
                        }
                        
                    }
                    reAnnotationTask.setStatus(0);
                    reAnnotationTask.setAnnotationState(0);
                    reAnnotationTask.setStartTime(new Date());
                    reAnnotationTask.setEndTime(null);
                    baseDaoSupport.insert(BiologyAnnotationTask.class, reAnnotationTask);
                    //重新实验
                    biologyAdapter.reTodoBiologyAnnotation(reAnnotationTask);
                    List<TestingSchedule> scheduleList = testingScheduleService.getScheduleHistorys(request.getId());
                    for (TestingSchedule sc : scheduleList)
                    {
                        TestingScheduleActive active = new TestingScheduleActive();
                        active.setSchedule(sc);
                        active.setTaskId(reAnnotationTask.getId());
                        active.setTaskRefer(strategy);
                        baseDaoSupport.insert(active);
                        
                        TestingScheduleHistory history = new TestingScheduleHistory();
                        history.setScheduleId(sc.getId());
                        history.setTaskId(reAnnotationTask.getId());
                        history.setTimestamp(new Date());
                        history.setTaskRefer(strategy);
                        baseDaoSupport.insert(history);
                        
                        sc.setEndTime(null);
                        sc.setEndType(null);
                        sc.setActiveTask("生信注释");
                        baseDaoSupport.update(sc);
                    }
                    
                    request.setId(request.getId());
                    
                }
                else if (TaskSemantic.TECHNICAL_ANALYSIS_TASK.equals(strategy))
                {
                    //重新实验
                    biologyAdapter.reTodoTechnicalAnalysis(request.getId());
                    //
                    TestingScheduleActive active = new TestingScheduleActive();
                    active.setSchedule(schedule);
                    active.setTaskId(request.getId());
                    active.setTaskRefer(strategy);
                    baseDaoSupport.insert(active);
                    
                    TestingScheduleHistory history = new TestingScheduleHistory();
                    history.setScheduleId(schedule.getId());
                    history.setTaskId(request.getId());
                    history.setTimestamp(new Date());
                    history.setTaskRefer(strategy);
                    baseDaoSupport.insert(history);
                    
                    schedule.setEndTime(null);
                    schedule.setEndType(null);
                    schedule.setActiveTask("新技术分析");
                    baseDaoSupport.update(schedule);
                }
                else
                {
                    //验证流程默认跳过DNA提取或者质检节点，所以历史任务没有该任务,从历史任务查询该样本是否做过提取或质检
                    TestingSample testingSample = baseDaoSupport.get(TestingSample.class, schedule.getSampleId());
                    
                    lastTask = testingScheduleService.getScheduleNodeLastTestingTask(schedule.getId(), strategy);
                    
                    if (TaskSemantic.PCR_NGS_PRIMER_DESIGN.equals(strategy) || TaskSemantic.PRIMER_DESIGN.equals(strategy))
                    {
                        TaskConfig primerConfig = bcmadapter.getTaskConfigBySemantic(strategy);
                        TestingTask existPrimerTask = null;
                        TestingTechnicalAnalyRecord record = null;
                        if (null != lastTask)
                        {
                            record = testingScheduleService.getAnalRecordByTaskId(lastTask.getId());
                        }
                        else
                        {
                            //直接根据验证流程的verifykey找验证数据
                            String verifyKey = schedule.getVerifyKey();
                            if (StringUtils.isNotEmpty(verifyKey))
                            {
                                SangerVerifyRecord sangerVerifyRecord = baseDaoSupport.get(SangerVerifyRecord.class, verifyKey);
                                if (null != sangerVerifyRecord)
                                {
                                    record = sangerVerifyRecord.getVerifyRecord().getAnalyRecord();
                                }
                            }
                        }
                        
                        if (null != record)
                        {
                            existPrimerTask = technicalAnalyService.getTestingTaskByChromAndLocation1(record.getChromosome(), record.getBeginLocus(), "Sanger");
                        }
                        
                        if (null == existPrimerTask)
                        {
                            if (StringUtils.isNotEmpty(cacheMap.get(record.getId())))
                            {
                                solveTask = cacheMap.get(record.getId());
                            }
                            else
                            {
                                solveTask = new TestingTask();
                                solveTask.setName(primerConfig.getName());
                                solveTask.setSemantic(primerConfig.getSemantic());
                                solveTask.setStatus(TestingTask.STATUS_ASSIGNABLE);
                                solveTask.setResubmit(true);
                                solveTask.setResubmitCount(null == lastTask ? 1 : (null == lastTask.getResubmitCount() ? 1 : (lastTask.getResubmitCount() + 1)));
                                solveTask.setStartTime(new Date());
                                solveTask.setInputSample(null == lastTask ? testingSample : lastTask.getInputSample());
                                baseDaoSupport.insert(solveTask);
                                
                                TestingTaskRunVariable variables = new TestingTaskRunVariable();
                                variables.setTestingTaskId(solveTask.getId());
                                PrimerDesignTaskVariables pdVal = new PrimerDesignTaskVariables();
                                pdVal.setRemark(remark);
                                variables.setText(JsonUtils.asJson(pdVal));
                                baseDaoSupport.insert(variables);
                                cacheMap.put(record.getId(), solveTask);
                            }
                        }
                        
                        else
                        {
                            solveTask = existPrimerTask;
                        }
                    }
                    else
                    {
                        
                        if (null == lastTask)
                        {
                            if (null != testingSample.getParentSample())
                            {
                                List<TestingTask> historys =
                                    baseDaoSupport.findByNamedParam(TestingTask.class,
                                        " from TestingTask t where t.inputSample.id=:sampleId and t.semantic=:semantic order by t.resubmitCount desc",
                                        new String[] {"sampleId", "semantic"},
                                        new Object[] {testingSample.getParentSample().getId(), strategy});
                                if (Collections3.isNotEmpty(historys))
                                {
                                    lastTask = Collections3.getFirst(historys);
                                }
                            }
                            else
                            {
                                List<TestingTask> historys =
                                    baseDaoSupport.findByNamedParam(TestingTask.class,
                                        " from TestingTask t where t.inputSample.id=:sampleId and t.semantic=:semantic order by t.resubmitCount desc",
                                        new String[] {"sampleId", "semantic"},
                                        new Object[] {testingSample.getId(), strategy});
                                if (Collections3.isNotEmpty(historys))
                                {
                                    lastTask = Collections3.getFirst(historys);
                                }
                            }
                        }
                        
                        solveTask = context.getSolvedTask(lastTask);
                        
                        if (null == solveTask)
                        {
                            solveTask = new TestingTask();
                            //如果已有该任务，不重复生成任务
                            //如果是dna提取或者质检 才需要判断 其他情况不做处理 已有该任务，不重复生成任务
                            boolean isDna = false;
                            TestingTask dnaTask = null;
                            if (TaskSemantic.DNA_EXTRACT.equals(config.getSemantic()) || TaskSemantic.DNA_QC.equals(config.getSemantic()))
                            {
                                dnaTask =
                                    testingTaskService.getUncompletedTestingTask(lastTask.getInputSample().getReceivedSample().getSampleId(),
                                        config.getSemantic());
                                isDna = true;
                            }
                            if (!isDna || (isDna && null == dnaTask))
                            {
                                solveTask.setName(config.getName());
                                solveTask.setSemantic(config.getSemantic());
                                solveTask.setStatus(TestingTask.STATUS_ASSIGNABLE);
                                solveTask.setResubmit(true);
                                solveTask.setResubmitCount(null == lastTask.getResubmitCount() ? 1 : (lastTask.getResubmitCount() + 1));
                                solveTask.setStartTime(new Date());
                                solveTask.setInputSample(lastTask.getInputSample());
                                baseDaoSupport.insert(solveTask);
                                
                                TestingTaskRunVariable variables = new TestingTaskRunVariable();
                                
                                if (TaskSemantic.MLPA_DATA_ANALYSIS.equals(solveTask.getSemantic()))
                                {
                                    String mlpaDataRunvariable = testingTaskService.getVariables(lastTask.getId());
                                    variables.setText(mlpaDataRunvariable);
                                }
                                else if (TaskSemantic.PCR_TWO.equals(solveTask.getSemantic()) || TaskSemantic.DATA_ANALYSIS.equals(solveTask.getSemantic()))
                                {
                                    String mlpaDataRunvariable = testingTaskService.getVariables(lastTask.getId());
                                    variables.setText(mlpaDataRunvariable);
                                }
                                else
                                {
                                    String jsons = getJsonRemarkStrByTaskSemantic(solveTask.getSemantic(), remark, lastTask);
                                    variables.setText(jsons);
                                }
                                
                                variables.setTestingTaskId(solveTask.getId());
                                baseDaoSupport.insert(variables);
                                context.solve(lastTask, solveTask);
                                
                                if (TaskSemantic.PCR_NGS_DATA_ANALYSIS.equals(solveTask.getSemantic()))
                                {
                                    //更新生信上机号
                                    TechnicalAnalyTestingTask technicalAnalyTestingTask = baseDaoSupport.get(TechnicalAnalyTestingTask.class, lastTask.getId());
                                    if (null != technicalAnalyTestingTask)
                                    {
                                        TechnicalAnalyTestingTask technicalAnalyTestingTaskIn = new TechnicalAnalyTestingTask();
                                        technicalAnalyTestingTaskIn.setTaskId(solveTask.getId());
                                        technicalAnalyTestingTaskIn.setSequencingCode(technicalAnalyTestingTask.getSequencingCode());
                                        baseDaoSupport.insert(technicalAnalyTestingTaskIn);
                                    }
                                }
                                
                                if (TaskSemantic.TECHNICAL_ANALY.equals(solveTask.getSemantic()))
                                {
                                    //存储技术分析测序编号
                                    List<TechnicalAnalyTestingTask> analyTask =
                                        baseDaoSupport.find(TechnicalAnalyTestingTask.class,
                                            "from TechnicalAnalyTestingTask t where t.taskId='" + lastTask.getId() + "'");
                                    if (Collections3.isNotEmpty(analyTask))
                                    {
                                        TechnicalAnalyTestingTask analyTestingTask = new TechnicalAnalyTestingTask();
                                        analyTestingTask.setSequencingCode(Collections3.getFirst(analyTask).getSequencingCode());
                                        analyTestingTask.setTaskId(solveTask.getId());
                                        baseDaoSupport.insert(analyTestingTask);
                                    }
                                }
                            }
                            else
                            {
                                solveTask = dnaTask;
                            }
                        }
                    }
                    
                    TestingScheduleActive active = new TestingScheduleActive();
                    active.setSchedule(schedule);
                    active.setTaskId(solveTask.getId());
                    baseDaoSupport.insert(active);
                    
                    TestingScheduleHistory history = new TestingScheduleHistory();
                    history.setScheduleId(schedule.getId());
                    history.setTaskId(solveTask.getId());
                    history.setTimestamp(new Date());
                    baseDaoSupport.insert(history);
                    
                    testingScheduleService.setScheduleActiveName(schedule);
                    
                    schedule.setEndTime(null);
                    schedule.setEndType(null);
                    baseDaoSupport.update(schedule);
                    
                    testingTaskService.updateTaskRedundantColumn(Arrays.asList(solveTask), 0);
                }
            }
            //设置加急
            Order order = baseDaoSupport.get(Order.class, schedule.getOrderId());
            if (null != order.getIfUrgent())
            {
                if (1 == order.getIfUrgent())
                {
                    solveTask.setIfUrgent(order.getIfUrgent());
                    solveTask.setUrgentName(order.getUrgentName());
                    solveTask.setUrgentUpdateTime(order.getUrgentUpdateTime());
                    baseDaoSupport.update(solveTask);
                }
            }
        }
        return "";
    }
    
    private FirstPcrSangerTaskVariables getPcrOneTaskRunningVariables(String taskId)
    {
        String variables = testingTaskService.getVariables(taskId);
        
        if (StringUtils.isEmpty(variables))
        {
            return new FirstPcrSangerTaskVariables();
        }
        
        return JsonUtils.asObject(variables, FirstPcrSangerTaskVariables.class);
    }
    
    public DTDataTaskVariables getTaskRunningVariables(String taskId)
    {
        String variables = testingTaskService.getVariables(taskId);
        
        if (StringUtils.isEmpty(variables))
        {
            return new DTDataTaskVariables();
        }
        
        return JsonUtils.asObject(variables, DTDataTaskVariables.class);
    }
    
    private SecondPcrSangerTaskVariables getPcrTwoTaskRunningVariables(String taskId)
    {
        String variables = testingTaskService.getVariables(taskId);
        
        if (StringUtils.isEmpty(variables))
        {
            return new SecondPcrSangerTaskVariables();
        }
        
        return JsonUtils.asObject(variables, SecondPcrSangerTaskVariables.class);
    }
    
    private String getJsonRemarkStrByTaskSemantic(String semantic, String remark, TestingTask lastTask)
    {
        String jsons = "";
        switch (semantic)
        {
            case TaskSemantic.DNA_EXTRACT:
                DNAExtractTaskVariables dnaExVariables = new DNAExtractTaskVariables();
                dnaExVariables.setRemark(remark);
                jsons = JsonUtils.asJson(dnaExVariables);
                break;
            case TaskSemantic.DNA_QC:
                DNAQcTaskVariables dnaQcVariables = new DNAQcTaskVariables();
                dnaQcVariables.setRemark(remark);
                jsons = JsonUtils.asJson(dnaQcVariables);
                break;
            case TaskSemantic.CDNA_EXTRACT:
                CDNAExtractTaskVariables cdnaExVariables = new CDNAExtractTaskVariables();
                cdnaExVariables.setRemark(remark);
                jsons = JsonUtils.asJson(cdnaExVariables);
                break;
            case TaskSemantic.LIBRARY_CRE:
                LibraryCreateTaskVariables libCreVariables = new LibraryCreateTaskVariables();
                libCreVariables.setRemark(remark);
                jsons = JsonUtils.asJson(libCreVariables);
                break;
            case TaskSemantic.LIBRARY_CAP:
                LibraryCaptureTaskVariables libCapVariables = new LibraryCaptureTaskVariables();
                libCapVariables.setRemark(remark);
                jsons = JsonUtils.asJson(libCapVariables);
                break;
            case TaskSemantic.DT:
                DTTaskVariables dtVariables = new DTTaskVariables();
                dtVariables.setRemark(remark);
                jsons = JsonUtils.asJson(dtVariables);
                break;
            case TaskSemantic.DT_DATA_ANALYSIS:
                DTDataTaskVariables dtDataVariables = getTaskRunningVariables(lastTask.getId());
                dtDataVariables.setRemark(remark);
                jsons = JsonUtils.asJson(dtDataVariables);
                break;
            case TaskSemantic.MLPA:
                MlpaTestTaskVariables mlpaVariables = new MlpaTestTaskVariables();
                mlpaVariables.setRemark(remark);
                jsons = JsonUtils.asJson(mlpaVariables);
                break;
            case TaskSemantic.LONG_PCR:
                Map longpcrVariables = new HashMap<>();
                longpcrVariables.put("remark", remark);
                jsons = JsonUtils.asJson(longpcrVariables);
                break;
            case TaskSemantic.MULTI_PCR:
                Map multipcrVariables = new HashMap<>();
                multipcrVariables.put("remark", remark);
                jsons = JsonUtils.asJson(multipcrVariables);
                break;
            case TaskSemantic.FLUO_TEST:
                Map fluotestVariables = new HashMap<>();
                fluotestVariables.put("remark", remark);
                jsons = JsonUtils.asJson(fluotestVariables);
                break;
            case TaskSemantic.PRIMER_DESIGN:
                PrimerDesignTaskVariables pdVariables = new PrimerDesignTaskVariables();
                pdVariables.setRemark(remark);
                jsons = JsonUtils.asJson(pdVariables);
                break;
            case TaskSemantic.PCR_ONE:
                FirstPcrTaskVariables fpVariables = new FirstPcrTaskVariables();
                fpVariables.setRemark(remark);
                jsons = JsonUtils.asJson(fpVariables);
                break;
            case TaskSemantic.PCR_TWO:
                SecondPcrTaskVariables spVariables = new SecondPcrTaskVariables();
                spVariables.setRemark(remark);
                jsons = JsonUtils.asJson(spVariables);
                break;
            case TaskSemantic.QPCR:
                Map qpcrVariables = new HashMap<>();
                qpcrVariables.put("remark", remark);
                jsons = JsonUtils.asJson(qpcrVariables);
                break;
            case TaskSemantic.PCR_NGS_PRIMER_DESIGN:
                PcrNgsPrimerDesignTaskVariables pnpVariables = new PcrNgsPrimerDesignTaskVariables();
                pnpVariables.setRemark(remark);
                jsons = JsonUtils.asJson(pnpVariables);
                break;
            case TaskSemantic.PCR_NGS:
                PcrNgsTestTaskVariables ptVariables = new PcrNgsTestTaskVariables();
                ptVariables.setRemark(remark);
                jsons = JsonUtils.asJson(ptVariables);
                break;
            default:
                break;
        }
        
        return jsons;
    }
}
