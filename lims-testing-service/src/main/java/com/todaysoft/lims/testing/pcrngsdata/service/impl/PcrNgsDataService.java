package com.todaysoft.lims.testing.pcrngsdata.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.testing.activiti.service.IActivitiService;
import com.todaysoft.lims.testing.base.entity.DataTemplate;
import com.todaysoft.lims.testing.base.entity.Dict;
import com.todaysoft.lims.testing.base.entity.Order;
import com.todaysoft.lims.testing.base.entity.OrderPlanTask;
import com.todaysoft.lims.testing.base.entity.OrderSubsidiarySample;
import com.todaysoft.lims.testing.base.entity.SangerVerifyRecord;
import com.todaysoft.lims.testing.base.entity.TechnicalAnalyTestingTask;
import com.todaysoft.lims.testing.base.entity.TestingMethod;
import com.todaysoft.lims.testing.base.entity.TestingSample;
import com.todaysoft.lims.testing.base.entity.TestingSchedule;
import com.todaysoft.lims.testing.base.entity.TestingScheduleActive;
import com.todaysoft.lims.testing.base.entity.TestingScheduleHistory;
import com.todaysoft.lims.testing.base.entity.TestingSheet;
import com.todaysoft.lims.testing.base.entity.TestingSheetTask;
import com.todaysoft.lims.testing.base.entity.TestingTask;
import com.todaysoft.lims.testing.base.entity.TestingTaskResult;
import com.todaysoft.lims.testing.base.entity.TestingTaskRunVariable;
import com.todaysoft.lims.testing.base.entity.TestingTechnicalAnalyRecord;
import com.todaysoft.lims.testing.base.model.TaskConfig;
import com.todaysoft.lims.testing.base.model.TaskSemantic;
import com.todaysoft.lims.testing.base.model.TestingSheetCreateModel;
import com.todaysoft.lims.testing.base.model.VariableModel;
import com.todaysoft.lims.testing.base.service.ICommonService;
import com.todaysoft.lims.testing.base.service.ITestingScheduleService;
import com.todaysoft.lims.testing.base.service.ITestingSheetService;
import com.todaysoft.lims.testing.base.service.ITestingTaskService;
import com.todaysoft.lims.testing.base.service.TestingCodeComparator;
import com.todaysoft.lims.testing.base.service.adapter.bcm.BCMAdapter;
import com.todaysoft.lims.testing.base.service.adapter.bsm.BSMAdapter;
import com.todaysoft.lims.testing.base.service.adapter.smm.SMMAdapter;
import com.todaysoft.lims.testing.base.service.adapter.smm.UserMinimalModel;
import com.todaysoft.lims.testing.base.utils.JsonUtils;
import com.todaysoft.lims.testing.mlpadata.service.IMlpaDataService;
import com.todaysoft.lims.testing.pcrngsdata.context.PcrNgsDataSubmitContext;
import com.todaysoft.lims.testing.pcrngsdata.context.PcrNgsDataSubmitNextTaskContext;
import com.todaysoft.lims.testing.pcrngsdata.context.PcrNgsDataSubmitScheduleContext;
import com.todaysoft.lims.testing.pcrngsdata.context.PcrNgsDataSubmitTaskContext;
import com.todaysoft.lims.testing.pcrngsdata.dao.PcrNgsDataAssignableTaskSearcher;
import com.todaysoft.lims.testing.pcrngsdata.model.PcrNgsDataAssignArgs;
import com.todaysoft.lims.testing.pcrngsdata.model.PcrNgsDataAssignModel;
import com.todaysoft.lims.testing.pcrngsdata.model.PcrNgsDataAssignRequest;
import com.todaysoft.lims.testing.pcrngsdata.model.PcrNgsDataAssignTaskArgs;
import com.todaysoft.lims.testing.pcrngsdata.model.PcrNgsDataSheetModel;
import com.todaysoft.lims.testing.pcrngsdata.model.PcrNgsDataSheetVariables;
import com.todaysoft.lims.testing.pcrngsdata.model.PcrNgsDataSubmitRequest;
import com.todaysoft.lims.testing.pcrngsdata.model.PcrNgsDataSubmitTaskArgs;
import com.todaysoft.lims.testing.pcrngsdata.model.PcrNgsDataTask;
import com.todaysoft.lims.testing.pcrngsdata.model.PcrNgsDataTaskVariables;
import com.todaysoft.lims.testing.pcrngsdata.service.IPcrNgsDataService;
import com.todaysoft.lims.testing.pcrngsprimerdesign.model.PcrNgsPrimerDesignTaskVariables;
import com.todaysoft.lims.testing.pcrngstest.model.PcrNgsTestTaskVariables;
import com.todaysoft.lims.testing.secondpcr.model.SecondPcrTaskVariables;
import com.todaysoft.lims.testing.technicalanaly.service.ITechnicalAnalyService;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.StringUtils;

@Service
public class PcrNgsDataService implements IPcrNgsDataService
{
    @Autowired
    private SMMAdapter smmadapter;
    
    @Autowired
    private BCMAdapter bcmadapter;
    
    @Autowired
    private BSMAdapter bsmadapter;
    
    @Autowired
    private BaseDaoSupport baseDaoSupport;
    
    @Autowired
    private ITestingTaskService testingTaskService;
    
    @Autowired
    private ITestingSheetService testingSheetService;
    
    @Autowired
    private IActivitiService activitiService;
    
    @Autowired
    private ICommonService commonService;
    
    @Autowired
    private ITestingScheduleService testingScheduleService;
    
    @Autowired
    private IMlpaDataService mlpaDataService;
    
    @Autowired
    private ITechnicalAnalyService technicalAnalyService;
    
    @Override
    public List<PcrNgsDataTask> getAssignableList(PcrNgsDataAssignableTaskSearcher searcher)
    {
        List<Object[]> records = baseDaoSupport.find(searcher);
        
        if (CollectionUtils.isEmpty(records))
        {
            return Collections.emptyList();
        }
        
        List<PcrNgsDataTask> tasks = new ArrayList<PcrNgsDataTask>();
        
        for (Object[] record : records)
        {
            tasks.add(wrap((TestingTask)record[0], (TechnicalAnalyTestingTask)record[1]));
        }
        tasks.sort(Comparator.comparing(PcrNgsDataTask::getResubmitCount)
            .reversed()
            .thenComparing(PcrNgsDataTask::getStartTime)
            .thenComparing(PcrNgsDataTask::getOrderId)
            .thenComparing(PcrNgsDataTask::getChromosomeLocation)
            .thenComparing(PcrNgsDataTask::getSampleName));
        //按加急降序
        tasks.sort(Comparator.comparing(PcrNgsDataTask::getIfUrgent).reversed());
        return tasks;
    }
    
    @Override
    public PcrNgsDataAssignModel getAssignableModel(PcrNgsDataAssignArgs args)
    {
        PcrNgsDataAssignModel model = new PcrNgsDataAssignModel();
        Map<String, String> primerLocationTempMap = Maps.newHashMap();
        
        if (null == args || StringUtils.isEmpty(args.getKeys()))
        {
            model.setTasks(Collections.emptyList());
            return model;
        }
        
        Set<String> includeKeys = new HashSet<String>(Arrays.asList(args.getKeys().split(",")));
        PcrNgsDataAssignableTaskSearcher searcher = new PcrNgsDataAssignableTaskSearcher();
        searcher.setIncludeKeys(includeKeys);
        List<PcrNgsDataTask> tasks = getAssignableList(searcher);
        model.setTasks(tasks);
        return model;
    }
    
    @Override
    @Transactional
    public void assign(PcrNgsDataAssignRequest request, String token)
    {
        if (!CollectionUtils.isEmpty(request.getTasks()))
        {
            TestingTask testingTask;
            PcrNgsDataTaskVariables firstPcrTaskVariables;
            
            for (PcrNgsDataAssignTaskArgs task : request.getTasks())
            {
                testingTask = testingTaskService.get(task.getId());
                testingTask.setStatus(TestingTask.STATUS_ASSIGNING);
                testingTaskService.modify(testingTask);
            }
        }
        
        // 创建任务单
        TestingSheetCreateModel model = buildTestingSheetCreateModel(request, token);
        TestingSheet sheet = testingSheetService.create(model);
        
        // 启动流程
        activitiService.releaseTestingSheet(sheet);
    }
    
    @Override
    public PcrNgsDataTaskVariables getTaskRunningVariables(String taskId)
    {
        String variables = testingTaskService.getVariables(taskId);
        
        if (StringUtils.isEmpty(variables))
        {
            return new PcrNgsDataTaskVariables();
        }
        
        return JsonUtils.asObject(variables, PcrNgsDataTaskVariables.class);
    }
    
    private TestingSheetCreateModel buildTestingSheetCreateModel(PcrNgsDataAssignRequest request, String token)
    {
        UserMinimalModel loginer = smmadapter.getLoginUser(token);
        UserMinimalModel tester = smmadapter.getUserByID(request.getTesterId());
        TaskConfig taskConfig = bcmadapter.getTaskConfigBySemantic(TaskSemantic.PCR_NGS_DATA_ANALYSIS);
        
        TestingSheetCreateModel model = new TestingSheetCreateModel();
        model.setCode(commonService.getTaskCodeBySemantic(TaskSemantic.PCR_NGS_DATA_ANALYSIS));
        model.setTaskSemantic(TaskSemantic.PCR_NGS_DATA_ANALYSIS);
        model.setTaskName(taskConfig.getName());
        
        if (null != tester)
        {
            model.setTesterId(tester.getId());
            model.setTesterName(tester.getName());
        }
        
        if (null != loginer)
        {
            model.setAssignerId(loginer.getId());
            model.setAssignerName(loginer.getName());
        }
        
        Date timestamp = new Date();
        model.setAssignTime(timestamp);
        model.setCreateTime(timestamp);
        model.setDescription(request.getDescription());
        
        if (Collections3.isNotEmpty(request.getTasks()))
        {
            model.setTasks(request.getTasks().stream().map(o -> o.getId()).collect(Collectors.toList()));
        }
        
        // 设置二次PCR任务单自定义参数对象
        PcrNgsDataSheetVariables variables = new PcrNgsDataSheetVariables();
        model.setVariables(variables);
        return model;
    }
    
    @Override
    public PcrNgsDataSheetModel getTestingSheet(String id)
    {
        TestingSheet entity = testingSheetService.getSheet(id);
        
        if (null == entity)
        {
            return null;
        }
        
        PcrNgsDataSheetModel sheet = new PcrNgsDataSheetModel();
        sheet.setId(entity.getId());
        sheet.setCode(entity.getCode());
        sheet.setAssignerName(entity.getAssignerName());
        sheet.setAssignTime(entity.getAssignTime());
        sheet.setDescription(entity.getDescription());
        
        List<PcrNgsDataTask> tasks = wrapForTesting(entity.getTestingSheetTaskList());
        
        Collections.sort(tasks, new Comparator<PcrNgsDataTask>()
        {
            @Override
            public int compare(PcrNgsDataTask o1, PcrNgsDataTask o2)
            {
                return new TestingCodeComparator().compare(o1.getPcrTestCode(), o2.getPcrTestCode());
            }
        });
        
        sheet.setTasks(tasks);
        return sheet;
    }
    
    @Override
    @Transactional
    public void submit(PcrNgsDataSubmitRequest request, String token, VariableModel model)
    {
        // 1、设置提交上下文数据
        PcrNgsDataSubmitContext context = generateSubmitContext(request, token);
        
        // 2、更新任务结果
        doUpdateTasks(context);
        
        // 3、创建新节点任务
        //        doCreateNextNodeTasks(context);
        
        // 4、设置设计成功样本相关流程激活节点状态
        doUpdateScheduleNextActives(context, model);
        
        // 5、设置设计成功样本相关流程激活节点状态
        doUpdateScheduleErrorActives(context);
        
        // 6、设置任务单提交结果
        doUpdatePcrNgsDataSheet(context);
        
        // 7、完成任务单待办事项
        doCompleteProcess(context);
        
        //8.保存图片
        //保存图片
        mlpaDataService.doSaveDataAnalyPic(request.getPicList(), TaskSemantic.PCR_NGS_DATA_ANALYSIS, request.getId(), 2);
    }
    
    private PcrNgsDataSubmitContext generateSubmitContext(PcrNgsDataSubmitRequest request, String token)
    {
        TestingSheet sheet = testingSheetService.getSheet(request.getId());
        
        if (null == sheet)
        {
            throw new IllegalStateException();
        }
        
        PcrNgsDataSubmitContext context = new PcrNgsDataSubmitContext();
        context.setContextForSubmiter(smmadapter.getLoginUser(token));
        context.setContextForTestingSheet(sheet);
        context.setContextForSubmitRequest(request.getTasks());
        
        List<TestingSheetTask> tasks = sheet.getTestingSheetTaskList();
        
        for (TestingSheetTask task : tasks)
        {
            setContextForTestingSheetTask(task, context);
        }
        
        return context;
    }
    
    private void setContextForTestingSheetTask(TestingSheetTask task, PcrNgsDataSubmitContext context)
    {
        String id = task.getTestingTaskId();
        TestingTask testingTask = testingTaskService.get(id);
        context.setContextForTestingTask(testingTask);
        
        TestingScheduleActive scheduleActive;
        List<TestingSchedule> schedules = testingScheduleService.getRelatedSchedules(id);
        
        for (TestingSchedule schedule : schedules)
        {
            scheduleActive = testingScheduleService.getScheduleActive(schedule.getId(), task.getTestingTaskId());
            
            if (null == scheduleActive)
            {
                throw new IllegalStateException();
            }
            context.setContextForTestingTaskRelatedSchedule(testingTask, schedule, scheduleActive);
        }
    }
    
    private void doUpdateTasks(PcrNgsDataSubmitContext context)
    {
        Set<PcrNgsDataSubmitTaskContext> records = context.getRelatedTasks();
        
        TestingTask task;
        Date timestamp = new Date();
        TestingTaskResult result;
        PcrNgsDataSubmitTaskArgs dataPcrSubmitTaskArgs;
        String testingTaskResult = "";
        
        for (PcrNgsDataSubmitTaskContext record : records)
        {
            task = record.getTestingTaskEntity();
            task.setEndTime(timestamp);
            task.setStatus(TestingTask.STATUS_END);
            if (0 == record.getResult().intValue())
            {
                task.setEndType(TestingTask.END_SUCCESS);
                testingTaskResult = "0";
            }
            else
            {
                task.setEndType(TestingTask.END_FAILURE);
                if ("实验取消".equals(record.getDispose()))
                {
                    testingTaskResult = "1";
                }
                else
                {
                    testingTaskResult = "2";
                }
            }
            baseDaoSupport.update(task);
            
            result = new TestingTaskResult();
            result.setTaskId(task.getId());
            result.setResult(testingTaskResult);
            result.setRemark(record.getRemark());
            result.setDetails(JsonUtils.asJson(record.getPcrNgsDataSubmitTaskArgs()));
            baseDaoSupport.insert(result);
        }
    }
    
    private void doCreateNextNodeTasks(PcrNgsDataSubmitContext context)
    {
        Set<PcrNgsDataSubmitNextTaskContext> nextTasks = context.getNextNodeTasks();
        
        TestingTask task;
        Date timestamp = new Date();
        
        for (PcrNgsDataSubmitNextTaskContext nextTask : nextTasks)
        {
            task = new TestingTask();
            task.setName(nextTask.getTaskName());
            task.setSemantic(nextTask.getTaskSemantic());
            task.setInputSample(nextTask.getTestingSample());
            task.setStatus(TestingTask.STATUS_ASSIGNABLE);
            task.setResubmit(false);
            task.setResubmitCount(0);
            task.setStartTime(timestamp);
            baseDaoSupport.insert(task);
            
            context.setContextForCreateNextNodeTask(nextTask.getTemporaryId(), task);
        }
    }
    
    private void doUpdateScheduleNextActives(PcrNgsDataSubmitContext context, VariableModel model)
    {
        Set<PcrNgsDataSubmitScheduleContext> schedules = context.getGotoNextSchedules();
        
        TestingSchedule schedule;
        TestingScheduleActive active;
        Date timestamp = new Date();
        List<String> ids = Lists.newArrayList();
        for (PcrNgsDataSubmitScheduleContext scheduleContext : schedules)
        {
            schedule = scheduleContext.getTestingScheduleEntity();
            schedule.setActiveTask("已完成");
            schedule.setEndType(TestingSchedule.END_SUCCESS);
            schedule.setEndTime(timestamp);
            baseDaoSupport.update(schedule);
            
            active = scheduleContext.getTestingScheduleActiveEntity();
            baseDaoSupport.delete(active);
            ids.add(schedule.getId());
        }
        String scheduleIds = StringUtils.join(ids, ",");
        model.setScheduleIds(scheduleIds);
    }
    
    private void doUpdateScheduleErrorActives(PcrNgsDataSubmitContext context)
    {
        Set<PcrNgsDataSubmitScheduleContext> schedules = context.getGotoErrorSchedules();
        
        TestingSchedule thisSchedule;
        TestingScheduleActive thisActive;
        String dispose;
        TestingTask thisTask;
        TestingTask nextTask;
        TestingTask lastTask;
        for (PcrNgsDataSubmitScheduleContext scheduleContext : schedules)
        {
            dispose = scheduleContext.getDispose();
            thisSchedule = scheduleContext.getTestingScheduleEntity();
            thisActive = scheduleContext.getTestingScheduleActiveEntity();
            thisTask = scheduleContext.getTestingTask();
            
            //报告处理为上报的流程，重新实验走完分析节点，状态置为合格
            testingScheduleService.updateReportSample(thisSchedule.getId());
            
            if ("重新设计引物".equals(dispose))
            {
                lastTask = testingScheduleService.getScheduleNodeLastTestingTask(thisSchedule.getId(), TaskSemantic.PCR_NGS_PRIMER_DESIGN);
                
                TaskConfig primerConfig = bcmadapter.getTaskConfigBySemantic(TaskSemantic.PCR_NGS_PRIMER_DESIGN);
                
                TestingTask existPrimerTask = null;
                TestingTechnicalAnalyRecord record = null;
                if (null != lastTask)
                {
                    record = testingScheduleService.getAnalRecordByTaskId(lastTask.getId());
                }
                else
                {
                    //直接根据验证流程的verifykey找验证数据
                    String verifyKey = thisSchedule.getVerifyKey();
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
                    existPrimerTask = context.getPrimerTask(record);
                    if (null == existPrimerTask)
                    {
                        existPrimerTask = technicalAnalyService.getTestingTaskByChromAndLocation1(record.getChromosome(), record.getBeginLocus(), "PCR-NGS");
                        
                    }
                }
                
                if (null == existPrimerTask)
                {
                    nextTask = new TestingTask();
                    nextTask.setName(primerConfig.getName());
                    nextTask.setSemantic(primerConfig.getSemantic());
                    nextTask.setStatus(TestingTask.STATUS_ASSIGNABLE);
                    nextTask.setResubmit(true);
                    nextTask.setResubmitCount(null == lastTask ? 1 : (null == lastTask.getResubmitCount() ? 1 : (lastTask.getResubmitCount() + 1)));
                    nextTask.setStartTime(new Date());
                    nextTask.setInputSample(null == lastTask ? thisTask.getInputSample() : lastTask.getInputSample());
                    baseDaoSupport.insert(nextTask);
                    
                    TestingTaskRunVariable variables = new TestingTaskRunVariable();
                    variables.setTestingTaskId(nextTask.getId());
                    PcrNgsPrimerDesignTaskVariables ppdVal = new PcrNgsPrimerDesignTaskVariables();
                    ppdVal.setRemark(scheduleContext.getRemark());
                    variables.setText(JsonUtils.asJson(ppdVal));
                    baseDaoSupport.insert(variables);
                }
                else
                {
                    nextTask = existPrimerTask;
                }
                if (null != record)
                {
                    context.setContextForCreateSangerPrimerPrepareTask(record, nextTask);
                }
                
                thisActive.setTaskId(nextTask.getId());
                baseDaoSupport.update(thisActive);
                
                TestingScheduleHistory history = new TestingScheduleHistory();
                history.setScheduleId(thisSchedule.getId());
                history.setTaskId(nextTask.getId());
                history.setTimestamp(new Date());
                baseDaoSupport.insert(history);
                
                thisSchedule.setActiveTask(primerConfig.getName());
                baseDaoSupport.update(thisSchedule);
                
                //设置加急
                Order order = baseDaoSupport.get(Order.class, thisSchedule.getOrderId());
                if (null != order.getIfUrgent())
                {
                    if (1 == order.getIfUrgent())
                    {
                        nextTask.setIfUrgent(order.getIfUrgent());
                        nextTask.setUrgentName(order.getUrgentName());
                        nextTask.setUrgentUpdateTime(order.getUrgentUpdateTime());
                        baseDaoSupport.update(nextTask);
                    }
                }
                //更新冗余信息
                testingTaskService.updateTaskRedundantColumn(Arrays.asList(nextTask), 0);
                
            }
            else if ("重新PCR-NGS实验".equals(dispose))
            {
                lastTask = testingScheduleService.getScheduleNodeLastTestingTask(thisSchedule.getId(), TaskSemantic.PCR_NGS);
                
                TaskConfig pcrNgsConfig = bcmadapter.getTaskConfigBySemantic(TaskSemantic.PCR_NGS);
                
                nextTask = new TestingTask();
                nextTask.setName(pcrNgsConfig.getName());
                nextTask.setSemantic(pcrNgsConfig.getSemantic());
                nextTask.setStatus(TestingTask.STATUS_ASSIGNABLE);
                nextTask.setResubmit(true);
                nextTask.setResubmitCount(null == lastTask ? 1 : (null == lastTask.getResubmitCount() ? 1 : (lastTask.getResubmitCount() + 1)));
                nextTask.setStartTime(new Date());
                nextTask.setInputSample(null == lastTask ? thisTask.getInputSample() : lastTask.getInputSample());
                baseDaoSupport.insert(nextTask);
                
                TestingTaskRunVariable variables = new TestingTaskRunVariable();
                variables.setTestingTaskId(nextTask.getId());
                PcrNgsTestTaskVariables ptVal = new PcrNgsTestTaskVariables();
                ptVal.setRemark(scheduleContext.getRemark());
                variables.setText(JsonUtils.asJson(ptVal));
                baseDaoSupport.insert(variables);
                
                thisActive.setTaskId(nextTask.getId());
                baseDaoSupport.update(thisActive);
                
                TestingScheduleHistory history = new TestingScheduleHistory();
                history.setScheduleId(thisSchedule.getId());
                history.setTaskId(nextTask.getId());
                history.setTimestamp(new Date());
                baseDaoSupport.insert(history);
                
                thisSchedule.setActiveTask(pcrNgsConfig.getName());
                baseDaoSupport.update(thisSchedule);
                
                //设置加急
                Order order = baseDaoSupport.get(Order.class, thisSchedule.getOrderId());
                if (null != order.getIfUrgent())
                {
                    if (1 == order.getIfUrgent())
                    {
                        nextTask.setIfUrgent(order.getIfUrgent());
                        nextTask.setUrgentName(order.getUrgentName());
                        nextTask.setUrgentUpdateTime(order.getUrgentUpdateTime());
                        baseDaoSupport.update(nextTask);
                    }
                }
                
                //更新冗余信息
                testingTaskService.updateTaskRedundantColumn(Arrays.asList(nextTask), 0);
                
            }
            else if ("实验取消".equals(dispose))
            {
                String taskName = "";
                if (null != thisTask)
                {
                    taskName = thisTask.getName();
                }
                thisSchedule.setActiveTask(taskName + "-异常");
                baseDaoSupport.update(thisSchedule);
                
                baseDaoSupport.delete(thisActive);
                
            }
        }
    }
    
    private void doUpdatePcrNgsDataSheet(PcrNgsDataSubmitContext context)
    {
        TestingSheet sheet = context.getSheetEntity();
        UserMinimalModel submiter = context.getSubmiter();
        sheet.setSubmiterId(submiter.getId());
        sheet.setSubmiterName(submiter.getName());
        sheet.setSubmitTime(new Date());
        baseDaoSupport.update(sheet);
    }
    
    private void doCompleteProcess(PcrNgsDataSubmitContext context)
    {
        TestingSheet sheet = context.getSheetEntity();
        activitiService.submitTestingSheet(sheet.getId());
    }
    
    private List<PcrNgsDataTask> wrapForTesting(List<TestingSheetTask> records)
    {
        if (CollectionUtils.isEmpty(records))
        {
            return Collections.emptyList();
        }
        
        TestingTask testingTask;
        TechnicalAnalyTestingTask tatt;
        List<PcrNgsDataTask> tasks = new ArrayList<PcrNgsDataTask>();
        
        for (TestingSheetTask record : records)
        {
            testingTask = testingTaskService.get(record.getTestingTaskId());
            
            if (null == testingTask)
            {
                throw new IllegalStateException();
            }
            tatt = baseDaoSupport.get(TechnicalAnalyTestingTask.class, testingTask.getId());
            tasks.add(wrap(testingTask, tatt));
        }
        
        return tasks;
    }
    
    private PcrNgsDataTask wrap(TestingTask entity, TechnicalAnalyTestingTask taskExt)
    {
        PcrNgsDataTask task = new PcrNgsDataTask();
        List<TestingSchedule> schedules = testingScheduleService.getRelatedSchedules(entity.getId());
        if (Collections3.isNotEmpty(schedules))
        {
            schedules = testingScheduleService.getRelatedSchedulesByTestingTask(entity.getId());
            task.setOrderId(schedules.get(0).getOrderId());
        }
        task.setId(entity.getId());
        task.setBioTestCode(taskExt.getSequencingCode());
        task.setStartTime(entity.getStartTime());
        task.setResubmit(entity.isResubmit());
        task.setResubmitCount(entity.getResubmitCount());
        task.setSampleName(entity.getReceivedSampleName());
        task.setSampleCode(entity.getReceivedSampleCode());
        task.setGene(entity.getVerifyGene());
        task.setChromosomeLocation(entity.getVerifyChromosomePosition());
        task.setCombineCode(entity.getTestingCombineCode());
        task.setInputSampleFamilyRelation(entity.getFamilyRelation());
        task.setProductName(entity.getProductName());
        task.setOrderCode(entity.getOrderCode());
        task.setStartTime(entity.getStartTime());
        //task.setSequencingCode(entity.getTestingLaneCode());
        //设置加急
        if (null == entity.getIfUrgent())
        {
            task.setIfUrgent(0);
        }
        else
        {
            task.setIfUrgent(entity.getIfUrgent());
        }
        
        task.setUrgentName(entity.getUrgentName());
        task.setUrgentUpdateTime(entity.getUrgentUpdateTime());
        
        //查找 未冗余存储的 测序编号 和 家系关系，导出任务单时展示
        TestingSchedule testingSchedule;
        SangerVerifyRecord sangerVerifyRecord;
        if (Collections3.isNotEmpty(schedules))
        {
            testingSchedule = schedules.get(0);
            if (null != testingSchedule)
            {
                sangerVerifyRecord = baseDaoSupport.get(SangerVerifyRecord.class, testingSchedule.getVerifyKey());
                if (null != sangerVerifyRecord)
                {
                    setTaskVariables(task, sangerVerifyRecord, testingSchedule, entity);
                }
            }
        }
        
        String hql = "From DataTemplate d where d.testingMethod.id = :id and d.delFlag = false";
        NamedQueryer queryer = NamedQueryer.builder().hql(hql).names(Lists.newArrayList("id")).values(Lists.newArrayList(getTestingMethodId())).build();
        List<DataTemplate> datatemplates = baseDaoSupport.find(queryer, DataTemplate.class);
        if (Collections3.isNotEmpty(datatemplates))
        {
            task.setDataTemplateId(datatemplates.get(0).getId());
        }
        //应完成时间
        //setPlannedFinishDate(entity, task);
        if (null != entity.getPlannedFinishDate())
        {
            task.setPlannedFinishDate(entity.getPlannedFinishDate());
        }
        else
        {
            try
            {
                task.setPlannedFinishDate(new SimpleDateFormat("yyyy-MM-dd").parse("1970-01-01"));
            }
            catch (ParseException e)
            {
                e.printStackTrace();
            }
        }
        return task;
    }
    
    private void setPlannedFinishDate(TestingTask entity, PcrNgsDataTask task)
    {
        NamedQueryer queryer =
            NamedQueryer.builder()
                .hql("FROM TestingScheduleHistory tsh WHERE tsh.taskId = :taskId")
                .names(Lists.newArrayList("taskId"))
                .values(Lists.newArrayList(entity.getId()))
                .build();
        List<TestingScheduleHistory> historys = baseDaoSupport.find(queryer, TestingScheduleHistory.class);
        Date date = null;
        if (Collections3.isNotEmpty(historys))
        {
            TestingSchedule schedule = baseDaoSupport.get(TestingSchedule.class, historys.get(0).getScheduleId());
            List<OrderPlanTask> plans = Lists.newArrayList();
            String hql =
                "FROM OrderPlanTask opt WHERE opt.orderId = :orderId AND opt.productId = :productId "
                    + " AND opt.sampleId = :sampleId AND opt.testingMethodId = :testingMethodId AND opt.taskSemantic = :taskSemantic";
            if (StringUtils.isNotEmpty(schedule.getVerifyKey()))
            {
                hql += " AND opt.verifyId = :verifyId";
                plans =
                    baseDaoSupport.findByNamedParam(OrderPlanTask.class,
                        hql,
                        new String[] {"orderId", "productId", "sampleId", "testingMethodId", "taskSemantic", "verifyId"},
                        new Object[] {schedule.getOrderId(), schedule.getProductId(), schedule.getSampleId(), schedule.getMethodId(), entity.getSemantic(),
                            schedule.getVerifyKey()});
            }
            else
            {
                plans =
                    baseDaoSupport.findByNamedParam(OrderPlanTask.class,
                        hql,
                        new String[] {"orderId", "productId", "sampleId", "testingMethodId", "taskSemantic"},
                        new Object[] {schedule.getOrderId(), schedule.getProductId(), schedule.getSampleId(), schedule.getMethodId(), entity.getSemantic()});
            }
            if (Collections3.isNotEmpty(plans))
            {
                date = plans.get(0).getPlannedFinishDate();
            }
        }
        if (null != date)
        {
            task.setPlannedFinishDate(date);
        }
        else
        {
            try
            {
                task.setPlannedFinishDate(new SimpleDateFormat("yyyy-MM-dd").parse("1970-01-01"));
            }
            catch (ParseException e)
            {
                e.printStackTrace();
            }
        }
    }
    
    public SecondPcrTaskVariables getPcrTwoTaskRunningVariables(String taskId)
    {
        String variables = testingTaskService.getVariables(taskId);
        
        if (StringUtils.isEmpty(variables))
        {
            return new SecondPcrTaskVariables();
        }
        
        return JsonUtils.asObject(variables, SecondPcrTaskVariables.class);
    }
    
    public void setTaskVariables(PcrNgsDataTask task, SangerVerifyRecord sangerVerifyRecord, TestingSchedule testingSchedule, TestingTask entity)
    {
        String hqlRelation = "FROM OrderSubsidiarySample oss WHERE oss.sampleCode = :sampleCode";
        NamedQueryer queryerRelation =
            NamedQueryer.builder().hql(hqlRelation).names(Lists.newArrayList("sampleCode")).values(Lists.newArrayList(entity.getReceivedSampleCode())).build();
        List<OrderSubsidiarySample> relations = baseDaoSupport.find(queryerRelation, OrderSubsidiarySample.class);
        if (Collections3.isNotEmpty(relations))
        {
            String familyRelation = Collections3.getFirst(relations).getFamilyRelation();
            
            String hqlDict = "FROM Dict d WHERE d.category = :category";
            NamedQueryer queryerDict =
                NamedQueryer.builder().hql(hqlDict).names(Lists.newArrayList("category")).values(Lists.newArrayList("FAMILY_RELATION")).build();
            List<Dict> dicts = baseDaoSupport.find(queryerDict, Dict.class);
            for (Dict dict : dicts)
            {
                if (StringUtils.isNotEmpty(familyRelation) && familyRelation.equals(dict.getValue()))
                {
                    task.setFamilyRelation(dict.getText());
                }
            }
        }
        else
        {
            task.setFamilyRelation("本人");
        }
        
        List<String> splitCode = Lists.newArrayList();
        if (StringUtils.isNotEmpty(sangerVerifyRecord.getVerifyRecord().getAnalyRecord().getDataCode()))
        {
            splitCode = Arrays.asList(sangerVerifyRecord.getVerifyRecord().getAnalyRecord().getDataCode().split("_"));
        }
        if (Collections3.isNotEmpty(splitCode))
        {
            String methodId = "";
            
            String methodName = splitCode.get(2);
            String semantic = "";
            switch (methodName)
            {
                case "MPCR":
                    semantic = "MULTI-PCR";
                    break;
                case "NGS":
                    semantic = "NGS";
                    break;
                case "CapNGS":
                    semantic = "CAP-NGS";
                    break;
                case "LPCR":
                    semantic = "Long-PCR";
                    break;
                default:
                    break;
            }
            if (StringUtils.isEmpty(semantic))
            {
                semantic = methodName;
            }
            
            String hql0 = "FROM TestingMethod tm WHERE tm.semantic = :semantic";
            NamedQueryer queryer0 = NamedQueryer.builder().hql(hql0).names(Lists.newArrayList("semantic")).values(Lists.newArrayList(semantic)).build();
            List<TestingMethod> methods = baseDaoSupport.find(queryer0, TestingMethod.class);
            if (Collections3.isNotEmpty(methods))
            {
                TestingMethod method = Collections3.getFirst(methods);
                methodId = method.getId();
            }
            
            String hqlSample = "FROM TestingSample ts WHERE ts.receivedSample.sampleId = :sampleId AND ts.parentSample IS NULL";
            String sampleId = "";
            NamedQueryer queryerSample =
                NamedQueryer.builder()
                    .hql(hqlSample)
                    .names(Lists.newArrayList("sampleId"))
                    .values(Lists.newArrayList(entity.getInputSample().getReceivedSample().getSampleId()))
                    .build();
            List<TestingSample> samples = baseDaoSupport.find(queryerSample, TestingSample.class);
            if (Collections3.isNotEmpty(methods))
            {
                TestingSample sample = Collections3.getFirst(samples);
                sampleId = sample.getId();
            }
            
            String hql =
                "FROM TestingSchedule ts WHERE ts.orderId = :orderId AND ts.productId = :productId AND ts.methodId = :methodId AND ts.sampleId = :sampleId";
            NamedQueryer queryer =
                NamedQueryer.builder()
                    .hql(hql)
                    .names(Lists.newArrayList("orderId", "productId", "methodId", "sampleId"))
                    .values(Lists.newArrayList(testingSchedule.getOrderId(), testingSchedule.getProductId(), methodId, sampleId))
                    .build();
            List<TestingSchedule> testingSchedules = baseDaoSupport.find(queryer, TestingSchedule.class);
            if (Collections3.isNotEmpty(testingSchedules))
            {
                TestingSchedule ts = Collections3.getFirst(testingSchedules);
                
                String hql1 = "FROM TestingScheduleHistory tsh WHERE tsh.scheduleId = :scheduleId";
                NamedQueryer queryer1 = NamedQueryer.builder().hql(hql1).names(Lists.newArrayList("scheduleId")).values(Lists.newArrayList(ts.getId())).build();
                List<TestingScheduleHistory> testingScheduleHistorys = baseDaoSupport.find(queryer1, TestingScheduleHistory.class);
                if (Collections3.isNotEmpty(testingScheduleHistorys))
                {
                    for (TestingScheduleHistory tsh : testingScheduleHistorys)
                    {
                        String hql2 = "FROM TechnicalAnalyTestingTask tatt WHERE tatt.taskId = :taskId";
                        NamedQueryer queryer2 =
                            NamedQueryer.builder().hql(hql2).names(Lists.newArrayList("taskId")).values(Lists.newArrayList(tsh.getTaskId())).build();
                        List<TechnicalAnalyTestingTask> technicalAnalyTestingTasks = baseDaoSupport.find(queryer2, TechnicalAnalyTestingTask.class);
                        if (Collections3.isNotEmpty(technicalAnalyTestingTasks))
                        {
                            TechnicalAnalyTestingTask technicalAnalyTestingTask = Collections3.getFirst(technicalAnalyTestingTasks);
                            task.setSequencingCode(technicalAnalyTestingTask.getSequencingCode());
                        }
                    }
                }
            }
        }
    }
    
    private String getTestingMethodId()
    {
        String hql = "From TestingMethod m where m.semantic = :semantic";
        NamedQueryer queryer = NamedQueryer.builder().hql(hql).names(Lists.newArrayList("semantic")).values(Lists.newArrayList("PCR-NGS")).build();
        return baseDaoSupport.find(queryer, TestingMethod.class).get(0).getId();
    }
}
