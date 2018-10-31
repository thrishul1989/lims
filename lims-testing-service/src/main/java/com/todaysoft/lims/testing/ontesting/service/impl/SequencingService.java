package com.todaysoft.lims.testing.ontesting.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Lists;
import com.mysql.fabric.xmlrpc.base.Array;
import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.testing.activiti.service.IActivitiService;
import com.todaysoft.lims.testing.base.entity.OrderPlanTask;
import com.todaysoft.lims.testing.base.entity.SequencingRecord;
import com.todaysoft.lims.testing.base.entity.TestingMethod;
import com.todaysoft.lims.testing.base.entity.TestingSample;
import com.todaysoft.lims.testing.base.entity.TestingSchedule;
import com.todaysoft.lims.testing.base.entity.TestingScheduleHistory;
import com.todaysoft.lims.testing.base.entity.TestingSheet;
import com.todaysoft.lims.testing.base.entity.TestingTask;
import com.todaysoft.lims.testing.base.entity.TestingTaskResult;
import com.todaysoft.lims.testing.base.entity.TestingTaskRunVariable;
import com.todaysoft.lims.testing.base.model.ReagentKitSimpleModel;
import com.todaysoft.lims.testing.base.model.TaskConfig;
import com.todaysoft.lims.testing.base.model.TaskSemantic;
import com.todaysoft.lims.testing.base.model.TestingSheetCreateModel;
import com.todaysoft.lims.testing.base.service.ICommonService;
import com.todaysoft.lims.testing.base.service.ITestingScheduleService;
import com.todaysoft.lims.testing.base.service.ITestingSheetService;
import com.todaysoft.lims.testing.base.service.ITestingTaskService;
import com.todaysoft.lims.testing.base.service.adapter.bcm.BCMAdapter;
import com.todaysoft.lims.testing.base.service.adapter.bsm.BSMAdapter;
import com.todaysoft.lims.testing.base.service.adapter.smm.SMMAdapter;
import com.todaysoft.lims.testing.base.service.adapter.smm.UserMinimalModel;
import com.todaysoft.lims.testing.base.utils.JsonUtils;
import com.todaysoft.lims.testing.ontesting.context.SequencingSubmitContext;
import com.todaysoft.lims.testing.ontesting.context.SequencingSubmitNextTaskContext;
import com.todaysoft.lims.testing.ontesting.context.SequencingSubmitScheduleContext;
import com.todaysoft.lims.testing.ontesting.context.SequencingSubmitTaskContext;
import com.todaysoft.lims.testing.ontesting.dao.SequencingAssignableTaskSearcher;
import com.todaysoft.lims.testing.ontesting.model.SequencingAssignModel;
import com.todaysoft.lims.testing.ontesting.model.SequencingAssignRequest;
import com.todaysoft.lims.testing.ontesting.model.SequencingSheetVariables;
import com.todaysoft.lims.testing.ontesting.model.SequencingSubmitModel;
import com.todaysoft.lims.testing.ontesting.model.SequencingSubmitRequest;
import com.todaysoft.lims.testing.ontesting.model.SequencingTask;
import com.todaysoft.lims.testing.ontesting.model.SequencingTaskVariables;
import com.todaysoft.lims.testing.ontesting.service.ISequencingService;
import com.todaysoft.lims.testing.pooling.model.PoolingTaskVariables;
import com.todaysoft.lims.testing.qt.model.QtSampleAttributes;
import com.todaysoft.lims.testing.qt.model.QtTask;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.StringUtils;

@Service
public class SequencingService implements ISequencingService
{
    @Autowired
    private BaseDaoSupport baseDaoSupport;
    
    @Autowired
    private SMMAdapter smmadapter;
    
    @Autowired
    private BCMAdapter bcmadapter;
    
    @Autowired
    private BSMAdapter bsmadapter;
    
    @Autowired
    private ICommonService commonService;
    
    @Autowired
    private IActivitiService activitiService;
    
    @Autowired
    private ITestingTaskService testingTaskService;
    
    @Autowired
    private ITestingSheetService testingSheetService;
    
    @Autowired
    private ITestingScheduleService testingScheduleService;
    
    @Override
    public List<SequencingTask> getAssignableList(SequencingAssignableTaskSearcher searcher)
    {
        List<TestingTask> records = baseDaoSupport.find(searcher);
        
        if (CollectionUtils.isEmpty(records))
        {
            return Collections.emptyList();
        }
        
        List<SequencingTask> tasks = new ArrayList<SequencingTask>();
        
        for (TestingTask record : records)
        {
            tasks.add(wrap(record));
        }
        /*tasks.sort(Comparator.comparing(SequencingTask::getIfUrgent).reversed()
            .thenComparing(SequencingTask::getPlannedFinishDate)
            .thenComparing(SequencingTask::getCreateTime));*/
        return tasks;
    }
    
    @Override
    public SequencingAssignModel getAssignModel(String id)
    {
        SequencingAssignModel model = new SequencingAssignModel();
        
        if (StringUtils.isEmpty(id))
        {
            return model;
        }
        
        TestingTask task = baseDaoSupport.get(TestingTask.class, id);
        
        if (null == task)
        {
            return model;
        }
        
        QtSampleAttributes attributes = JsonUtils.asObject(task.getInputSample().getAttributes(), QtSampleAttributes.class);
        model.setId(task.getId());
        model.setSampleCode(task.getInputSample().getSampleCode());
        
        if (null == attributes || null == attributes.getConcn())
        {
            throw new IllegalStateException();
        }
        
        model.setConcn(attributes.getConcn());
        model.setFirstDiluteConcn(bcmadapter.getNGSFirstDiluteConcn());
        model.setFirstDiluteSampleInputVolume(bcmadapter.getNGSFirstDiluteSampleInputVolume());
        model.setSecondDiluteSampleInputVolume(bcmadapter.getNGSRTSampleInputVolume());
        model.setSecondDiluteReagentInputVolume(bcmadapter.getNGSRTReagentInputVolume());
        model.setSecondDiluteConcn(bcmadapter.getNGSSecondDiluteConcn());
        model.setFinalConcn(bcmadapter.getNGSFinalConcn());
        model.setFinalInputVolume(bcmadapter.getNGSFinalInputVolume());
        
        // 计算
        BigDecimal firstDiluteHTInputeVolume =
            model.getConcn()
                .multiply(model.getFirstDiluteSampleInputVolume())
                .divide(model.getFirstDiluteConcn(), 10, BigDecimal.ROUND_HALF_DOWN)
                .subtract(model.getFirstDiluteSampleInputVolume());
        model.setFirstDiluteHTInputVolume(firstDiluteHTInputeVolume);
        
        BigDecimal secondDiluteHTInputeVolume =
            model.getFirstDiluteConcn()
                .multiply(model.getSecondDiluteSampleInputVolume())
                .multiply(BigDecimal.valueOf(1000D))
                .divide(model.getSecondDiluteConcn(), 10, BigDecimal.ROUND_HALF_DOWN)
                .subtract(model.getSecondDiluteSampleInputVolume())
                .subtract(model.getSecondDiluteReagentInputVolume());
        model.setSecondDiluteHTInputVolume(secondDiluteHTInputeVolume);
        
        BigDecimal finalSampleInputVolume =
            model.getFinalConcn().multiply(model.getFinalInputVolume()).divide(model.getSecondDiluteConcn(), 10, BigDecimal.ROUND_HALF_DOWN);
        model.setFinalSampleInputVolume(finalSampleInputVolume);
        model.setFinalHTInputVolume(model.getFinalInputVolume().subtract(model.getFinalSampleInputVolume()));
        return model;
    }
    
    @Override
    @Transactional
    public void assign(SequencingAssignRequest request, String token)
    {
        TestingTask testingTask = testingTaskService.get(request.getId());
        testingTask.setStatus(TestingTask.STATUS_ASSIGNING);
        testingTaskService.modify(testingTask);
        
        SequencingTaskVariables variables = getTaskRunningVariables(testingTask.getId());
        variables.setFirstDiluteConcn(request.getFirstDiluteConcn());
        variables.setFirstDiluteSampleInputVolume(request.getFirstDiluteSampleInputVolume());
        variables.setFirstDiluteHTInputVolume(request.getFirstDiluteHTInputVolume());
        variables.setSecondDiluteConcn(request.getSecondDiluteConcn());
        variables.setSecondDiluteSampleInputVolume(request.getSecondDiluteSampleInputVolume());
        variables.setSecondDiluteReagentInputVolume(request.getSecondDiluteReagentInputVolume());
        variables.setSecondDiluteHTInputVolume(request.getSecondDiluteHTInputVolume());
        variables.setFinalConcn(request.getFinalConcn());
        variables.setFinalInputVolume(request.getFinalInputVolume());
        variables.setFinalSampleInputVolume(request.getFinalSampleInputVolume());
        variables.setFinalHTInputVolume(request.getFinalHTInputVolume());
        testingTaskService.updateVariables(testingTask.getId(), variables);
        
        // 创建任务单
        TestingSheetCreateModel model = buildTestingSheetCreateModel(request, token);
        //上机任务单的编号也用上机号，新需求 20170116
        if (null != testingTask.getInputSample())
        {
            model.setCode(testingTask.getInputSample().getSampleCode());
        }
        TestingSheet sheet = testingSheetService.create(model);
        
        // 启动流程
        activitiService.releaseTestingSheet(sheet);
        
        
        //更新冗余信息
        testingTaskService.updateTaskRedundantColumn(Arrays.asList(testingTask),1);
        
    }
    
    private TestingSheetCreateModel buildTestingSheetCreateModel(SequencingAssignRequest request, String token)
    {
        UserMinimalModel loginer = smmadapter.getLoginUser(token);
        UserMinimalModel tester = smmadapter.getUserByID(request.getTesterId());
        TaskConfig taskConfig = bcmadapter.getTaskConfigBySemantic(TaskSemantic.NGS_SEQ);
        
        TestingSheetCreateModel model = new TestingSheetCreateModel();
        model.setCode(commonService.getTaskCodeBySemantic(TaskSemantic.NGS_SEQ));
        model.setTaskSemantic(TaskSemantic.NGS_SEQ);
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
        model.setTasks(Collections.singletonList(request.getId()));
        
        SequencingSheetVariables variables = new SequencingSheetVariables();
        variables.setReagentKitId(request.getReagentKitId());
        variables.setFinalConcn(request.getFinalConcn());
        model.setVariables(variables);
        return model;
    }
    
    @Override
    public SequencingSubmitModel getSubmitModel(String id)
    {
        TestingSheet sheet = baseDaoSupport.get(TestingSheet.class, id);
        
        if (null == sheet)
        {
            return null;
        }
        
        SequencingSubmitModel model = new SequencingSubmitModel();
        model.setId(sheet.getId());
        model.setCode(sheet.getCode());
        model.setAssignerName(sheet.getAssignerName());
        model.setAssignTime(sheet.getAssignTime());
        model.setDescription(sheet.getDescription());
        model.setTestingSheetTaskList(sheet.getTestingSheetTaskList());
        SequencingSheetVariables variables = JsonUtils.asObject(sheet.getVariables(), SequencingSheetVariables.class);
        
        if (null != variables)
        {
            String reagentKitId = variables.getReagentKitId();
            ReagentKitSimpleModel reagentKit = bsmadapter.getReagentKit(reagentKitId);
            
            if (null != reagentKit)
            {
                model.setReagentKitName(reagentKit.getName());
            }
        }
        
        String taskId = sheet.getTestingSheetTaskList().get(0).getTestingTaskId();
        TestingTask task = baseDaoSupport.get(TestingTask.class, taskId);
        model.setSampleCode(task.getInputSample().getSampleCode());
        QtSampleAttributes attributes = JsonUtils.asObject(task.getInputSample().getAttributes(), QtSampleAttributes.class);
        
        if (null != attributes)
        {
            model.setConcn(attributes.getConcn());
        }
        
        SequencingTaskVariables taskVariables = getTaskRunningVariables(taskId);
        
        if (null != taskVariables)
        {
            model.setFirstDiluteConcn(taskVariables.getFirstDiluteConcn());
            model.setFirstDiluteSampleInputVolume(taskVariables.getFirstDiluteSampleInputVolume());
            model.setFirstDiluteHTInputVolume(taskVariables.getFirstDiluteHTInputVolume());
            model.setSecondDiluteConcn(taskVariables.getSecondDiluteConcn());
            model.setSecondDiluteSampleInputVolume(taskVariables.getSecondDiluteSampleInputVolume());
            model.setSecondDiluteReagentInputVolume(taskVariables.getSecondDiluteReagentInputVolume());
            model.setSecondDiluteHTInputVolume(taskVariables.getSecondDiluteHTInputVolume());
            model.setFinalConcn(taskVariables.getFinalConcn());
            model.setFinalInputVolume(taskVariables.getFinalInputVolume());
            model.setFinalSampleInputVolume(taskVariables.getFinalSampleInputVolume());
            model.setFinalHTInputVolume(taskVariables.getFinalHTInputVolume());
        }
        
        return model;
    }
    
    @Override
    @Transactional
    public void submit(SequencingSubmitRequest request, String token)
    {
        SequencingSubmitContext context = generateSubmitContext(request, token);
    
        
        // 2、更新实验任务状态
        doUpdateTestingTask(context);
        
        // 3、创建下一步任务
        doCreateNextNodeTasks(context);
        
        // 4、设置流程到下一节点
        doUpdateSchedules(context);
        
        // 5、更新任务单
        doUpdateSheet(context);
        
        // 6、保存测序结果
        doUpdateSequencingResult(context);
        
        // 7、完成工作流
        doCompleteProcess(context);
    }
    
    private void doUpdateTestingTask(SequencingSubmitContext context)
    {
        SequencingSubmitTaskContext taskContext = context.getTask();
        
        TestingTask task = taskContext.getTestingTaskEntity();
        task.setEndTime(new Date());
        task.setStatus(TestingTask.STATUS_END);
        task.setEndType(TestingTask.END_SUCCESS);
        baseDaoSupport.update(task);
        
        TestingTaskResult result = new TestingTaskResult();
        result.setTaskId(task.getId());
        result.setResult(TestingTaskResult.RESULT_SUCCESS);
        baseDaoSupport.insert(result);
    }
    
    private void doCreateNextNodeTasks(SequencingSubmitContext context)
    {
        SequencingSubmitNextTaskContext nextTaskContext = context.getNextTask();
        TestingTask nextTask = new TestingTask();
        nextTask.setName(nextTaskContext.getTaskName());
        nextTask.setSemantic(nextTaskContext.getTaskSemantic());
        nextTask.setStatus(TestingTask.STATUS_ASSIGNABLE);
        nextTask.setResubmit(false);
        nextTask.setResubmitCount(0);
        nextTask.setStartTime(new Date());
        nextTask.setInputSample(nextTaskContext.getInputSample());
        baseDaoSupport.insert(nextTask);
        
        TestingTaskRunVariable variables = new TestingTaskRunVariable();
        variables.setTestingTaskId(nextTask.getId());
        baseDaoSupport.insert(variables);
        
        context.setContextForCreateNextNodeTask(nextTask);
    }
    
    private void doUpdateSchedules(SequencingSubmitContext context)
    {
        Set<SequencingSubmitScheduleContext> schedules = context.getRelatedSchedules();
        
        TestingSchedule schedule;
        Date timestamp = new Date();
        
        for (SequencingSubmitScheduleContext scheduleContext : schedules)
        {
            schedule = scheduleContext.getTestingScheduleEntity();
            testingScheduleService.gotoNextNode(schedule, TaskSemantic.NGS_SEQ, context.getNextTask().getTestingTaskEntity(), timestamp);
        }
        //更新冗余信息，特殊处理测序编号
        context.getNextTask().getTestingTaskEntity().setTestingLaneCode(context.getNextTask().getTestingTaskEntity().getInputSample().getSampleCode());
        testingTaskService.updateTaskRedundantColumn(Arrays.asList(context.getNextTask().getTestingTaskEntity()), 0);
    }
    
    private void doUpdateSheet(SequencingSubmitContext context)
    {
        TestingSheet sheet = context.getSheetEntity();
        UserMinimalModel submiter = context.getSubmiter();
        sheet.setSubmiterId(submiter.getId());
        sheet.setSubmiterName(submiter.getName());
        sheet.setSubmitTime(new Date());
        baseDaoSupport.update(sheet);
    }
    
    private void doUpdateSequencingResult(SequencingSubmitContext context)
    {
        SequencingSubmitTaskContext taskContext = context.getTask();
        
        String hql = "FROM SequencingRecord r WHERE r.sample.id = :sampleId AND r.reference = false";
        List<SequencingRecord> records =
            baseDaoSupport.findByNamedParam(SequencingRecord.class, hql, new String[] {"sampleId"}, new Object[] {taskContext.getTestingTaskEntity()
                .getInputSample()
                .getId()});
        
        if (CollectionUtils.isEmpty(records) || records.size() > 1)
        {
            throw new IllegalStateException();
        }
        
        SequencingRecord record = records.get(0);
        record.setSequencingSheetId(context.getSheetEntity().getId());
        record.setSequencingTime(context.getSheetEntity().getSubmitTime());
        record.setSequencingCluster(taskContext.getCluster());
        record.setSequencingEffectiveRate(taskContext.getEffectiveRate());
        record.setSequencingEffectiveSize(taskContext.getEffectiveSize());
        record.setSequencingQ30(taskContext.getQ30());
        baseDaoSupport.update(record);
    }
    
    private void doCompleteProcess(SequencingSubmitContext context)
    {
        TestingSheet sheet = context.getSheetEntity();
        activitiService.submitTestingSheet(sheet.getId());
    }
    
    private SequencingSubmitContext generateSubmitContext(SequencingSubmitRequest request, String token)
    {
        TestingSheet sheet = baseDaoSupport.get(TestingSheet.class, request.getId());
        
        if (null == sheet)
        {
            throw new IllegalStateException();
        }
        
        SequencingSubmitContext context = new SequencingSubmitContext();
        context.setContextForSubmiter(smmadapter.getLoginUser(token));
        context.setContextForTestingSheet(sheet);
        
        String testingTaskId = sheet.getTestingSheetTaskList().get(0).getTestingTaskId();
        TestingTask task = baseDaoSupport.get(TestingTask.class, testingTaskId);
        context.setContextForTestingTask(task);
        context.setContextForTestingResult(request);
        
        List<TestingSchedule> schedules = testingScheduleService.getRelatedSchedules(testingTaskId);
        
        TaskConfig scheduleNextNodeConfig;
        
        for (TestingSchedule schedule : schedules)
        {
            scheduleNextNodeConfig = testingScheduleService.getScheduleNextNodeConfig(schedule, task.getSemantic());
            
            if (null == scheduleNextNodeConfig)
            {
                throw new IllegalStateException();
            }
            
            context.setContextForTestingSchedule(schedule, scheduleNextNodeConfig);
        }
        
        context.setContextForNextNodeTaskConfig();
        return context;
    }
    
    @Override
    public SequencingTaskVariables getTaskRunningVariables(String taskId)
    {
        String variables = testingTaskService.getVariables(taskId);
        
        if (StringUtils.isEmpty(variables))
        {
            return new SequencingTaskVariables();
        }
        
        return JsonUtils.asObject(variables, SequencingTaskVariables.class);
    }
    
    private SequencingTask wrap(TestingTask entity)
    {
        SequencingTask task = new SequencingTask();
        List<TestingSchedule> list = testingScheduleService.getRelatedSchedulesByTestingTask(entity.getId());
        //TestingSchedule testingschedule = Collections3.isNotEmpty(list) ? list.get(0) : null;
        BigDecimal testingDatasizes = new BigDecimal(0);
        List<String> noRepeatTask = new ArrayList<String>();
        
        for (TestingSchedule testingschedule : list)
        {
            if (null != testingschedule)
            {
                List<TestingScheduleHistory> historys = testingScheduleService.getTestingScheduleHistoryByScheduleID(testingschedule.getId());
                if (Collections3.isNotEmpty(historys))
                {
                    for (TestingScheduleHistory history : historys)
                    {
                        TestingTask testingtask = testingTaskService.get(history.getTaskId());
                        if(null==testingtask)
                        {
                            continue;
                        }
                        if ("POOLING".equals(testingtask.getSemantic()) && isRepeatTask(noRepeatTask, testingtask))
                        {
                            PoolingTaskVariables variables = testingTaskService.obtainVariables(testingtask.getId(), PoolingTaskVariables.class);
                            BigDecimal testingDatasize = variables == null ? null : variables.getTestingDatasize();
                            testingDatasizes = testingDatasizes.add(testingDatasize);
                        }
                    }
                }
            }
        }
        task.setTestingDatasize(testingDatasizes);
        task.setId(entity.getId());
        task.setSampleCode(entity.getInputSample().getSampleCode());
        task.setCreateTime(entity.getStartTime());
        //设置加急
        if(null == entity.getIfUrgent())
        {
            task.setIfUrgent(0);
        }
        else
        {
            task.setIfUrgent(entity.getIfUrgent());
        }
        task.setUrgentName(entity.getUrgentName());
        task.setUrgentUpdateTime(entity.getUrgentUpdateTime());
        //setPlannedFinishDate(entity, task);
        return task;
    }
    
    private void setPlannedFinishDate(TestingTask entity,SequencingTask task)
    {
        NamedQueryer queryer =
            NamedQueryer.builder()
            .hql("FROM TestingScheduleHistory tsh WHERE tsh.taskId = :taskId")
            .names(Lists.newArrayList("taskId"))
            .values(Lists.newArrayList(entity.getId()))
            .build();
        List<TestingScheduleHistory> historys = baseDaoSupport.find(queryer, TestingScheduleHistory.class);
        Date date = null;
        if(Collections3.isNotEmpty(historys))
        {
            TestingSchedule schedule = baseDaoSupport.get(TestingSchedule.class, historys.get(0).getScheduleId());
            List<OrderPlanTask> plans = Lists.newArrayList();
            String hql = "FROM OrderPlanTask opt WHERE opt.orderId = :orderId AND opt.productId = :productId "
                + " AND opt.sampleId = :sampleId AND opt.testingMethodId = :testingMethodId AND opt.taskSemantic = :taskSemantic";
            if(StringUtils.isNotEmpty(schedule.getVerifyKey()))
            {
                hql += " AND opt.verifyId = :verifyId";
                plans = baseDaoSupport.findByNamedParam(OrderPlanTask.class, hql, 
                    new String[] {"orderId","productId","sampleId","testingMethodId","taskSemantic","verifyId"}, 
                    new Object[] {schedule.getOrderId(),schedule.getProductId(),schedule.getSampleId(),schedule.getMethodId(),entity.getSemantic(),schedule.getVerifyKey()});
            }
            else
            {
                plans = baseDaoSupport.findByNamedParam(OrderPlanTask.class, hql, 
                    new String[] {"orderId","productId","sampleId","testingMethodId","taskSemantic"}, 
                    new Object[] {schedule.getOrderId(),schedule.getProductId(),schedule.getSampleId(),schedule.getMethodId(),entity.getSemantic()});
            }
            if(Collections3.isNotEmpty(plans))
            {
                date = plans.get(0).getPlannedFinishDate();
            }
        }
        if(null != date)
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
    
    //多重PCR和longpcr和PCR-NGS到相对定量合并任务，所以数据量只计算一次
    private boolean isRepeatTask(List<String> noRepeatTask, TestingTask task)
    {
        for (String taskId : noRepeatTask)
        {
            if (taskId.equals(task.getId()))
            {
                return false;
            }
            
        }
        noRepeatTask.add(task.getId());
        
        return true;
    }
}
