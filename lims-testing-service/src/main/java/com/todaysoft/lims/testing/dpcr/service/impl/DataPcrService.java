package com.todaysoft.lims.testing.dpcr.service.impl;

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
import com.todaysoft.lims.testing.dpcr.context.DataPcrSubmitContext;
import com.todaysoft.lims.testing.dpcr.context.DataPcrSubmitNextTaskContext;
import com.todaysoft.lims.testing.dpcr.context.DataPcrSubmitScheduleContext;
import com.todaysoft.lims.testing.dpcr.context.DataPcrSubmitTaskContext;
import com.todaysoft.lims.testing.dpcr.dao.DataPcrAssignableTaskSearcher;
import com.todaysoft.lims.testing.dpcr.model.DataPcrAssignArgs;
import com.todaysoft.lims.testing.dpcr.model.DataPcrAssignModel;
import com.todaysoft.lims.testing.dpcr.model.DataPcrAssignRequest;
import com.todaysoft.lims.testing.dpcr.model.DataPcrAssignTaskArgs;
import com.todaysoft.lims.testing.dpcr.model.DataPcrSheetModel;
import com.todaysoft.lims.testing.dpcr.model.DataPcrSheetVariables;
import com.todaysoft.lims.testing.dpcr.model.DataPcrSubmitRequest;
import com.todaysoft.lims.testing.dpcr.model.DataPcrSubmitTaskArgs;
import com.todaysoft.lims.testing.dpcr.model.DataPcrTask;
import com.todaysoft.lims.testing.dpcr.model.DataPcrTaskVariables;
import com.todaysoft.lims.testing.dpcr.service.IDataPcrService;
import com.todaysoft.lims.testing.firstpcr.model.FirstPcrTaskVariables;
import com.todaysoft.lims.testing.mlpadata.service.IMlpaDataService;
import com.todaysoft.lims.testing.primerdesign.model.PrimerDesignTaskVariables;
import com.todaysoft.lims.testing.secondpcr.model.SecondPcrTaskVariables;
import com.todaysoft.lims.testing.technicalanaly.service.ITechnicalAnalyService;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.StringUtils;

@Service
public class DataPcrService implements IDataPcrService
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
    private IMlpaDataService dataService;
    
    @Autowired
    private ITechnicalAnalyService technicalAnalyService;
    
    @Override
    public List<DataPcrTask> getAssignableList(DataPcrAssignableTaskSearcher searcher, int searchType)
    {
        List<TestingTask> records = baseDaoSupport.find(searcher);
        List<String> pcrTaskCodeList = Lists.newArrayList();
        Map<String, String> mapIds = Maps.newHashMap();
        
        if (CollectionUtils.isEmpty(records))
        {
            return Collections.emptyList();
        }
        
        List<DataPcrTask> tasks = new ArrayList<DataPcrTask>();
        
        for (TestingTask record : records)
        {
            DataPcrTask taskResult = wrap(record);
            if (searchType == 1)
            {
                if (!pcrTaskCodeList.contains(taskResult.getPcrTaskCode()))
                {
                    tasks.add(taskResult);
                    pcrTaskCodeList.add(taskResult.getPcrTaskCode());
                    mapIds.put(taskResult.getPcrTaskCode(), taskResult.getId());
                }
                else
                {
                    String ids = mapIds.get(taskResult.getPcrTaskCode());
                    mapIds.put(taskResult.getPcrTaskCode(), ids + "," + taskResult.getId());
                    
                }
            }
            else
            {
                tasks.add(taskResult);
            }
            
        }
        //把任务编号一样的合并到一条记录 id存逗号隔开的合并searchType:1-查询合并任务编号的列表 2-查询所有
        if (searchType == 1)
        {
            tasks.stream().forEach(x -> x.setId(mapIds.get(x.getPcrTaskCode())));
        }
        /*tasks.sort(Comparator.comparing(DataPcrTask::getIfUrgent).reversed()
            .thenComparing(DataPcrTask::getPlannedFinishDate));*/
        return tasks;
    }
    
    @Override
    public DataPcrAssignModel getAssignableModel(DataPcrAssignArgs args)
    {
        DataPcrAssignModel model = new DataPcrAssignModel();
        Map<String, String> primerLocationTempMap = Maps.newHashMap();
        
        if (null == args || StringUtils.isEmpty(args.getKeys()))
        {
            model.setTasks(Collections.emptyList());
            return model;
        }
        
        Set<String> includeKeys = new HashSet<String>(Arrays.asList(args.getKeys().split(",")));
        DataPcrAssignableTaskSearcher searcher = new DataPcrAssignableTaskSearcher();
        searcher.setIncludeKeys(includeKeys);
        List<DataPcrTask> tasks = getAssignableList(searcher, 2);
        tasks.sort((DataPcrTask h1, DataPcrTask h2) -> h1.getPcrTestCode().compareTo(h2.getPcrTestCode()));
        model.setTasks(tasks);
        return model;
    }
    
    @Override
    @Transactional
    public void assign(DataPcrAssignRequest request, String token)
    {
        if (!CollectionUtils.isEmpty(request.getTasks()))
        {
            TestingTask testingTask;
            DataPcrTaskVariables firstPcrTaskVariables;
            
            for (DataPcrAssignTaskArgs task : request.getTasks())
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
    public DataPcrTaskVariables getTaskRunningVariables(String taskId)
    {
        String variables = testingTaskService.getVariables(taskId);
        
        if (StringUtils.isEmpty(variables))
        {
            return new DataPcrTaskVariables();
        }
        
        return JsonUtils.asObject(variables, DataPcrTaskVariables.class);
    }
    
    private TestingSheetCreateModel buildTestingSheetCreateModel(DataPcrAssignRequest request, String token)
    {
        UserMinimalModel loginer = smmadapter.getLoginUser(token);
        UserMinimalModel tester = smmadapter.getUserByID(request.getTesterId());
        TaskConfig taskConfig = bcmadapter.getTaskConfigBySemantic(TaskSemantic.DATA_ANALYSIS);
        
        TestingSheetCreateModel model = new TestingSheetCreateModel();
        model.setCode(commonService.getTaskCodeBySemantic(TaskSemantic.DATA_ANALYSIS));
        model.setTaskSemantic(TaskSemantic.DATA_ANALYSIS);
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
        DataPcrSheetVariables variables = new DataPcrSheetVariables();
        model.setVariables(variables);
        return model;
    }
    
    @Override
    public DataPcrSheetModel getTestingSheet(String id)
    {
        TestingSheet entity = testingSheetService.getSheet(id);
        
        if (null == entity)
        {
            return null;
        }
        
        DataPcrSheetModel sheet = new DataPcrSheetModel();
        sheet.setId(entity.getId());
        sheet.setCode(entity.getCode());
        sheet.setAssignerName(entity.getAssignerName());
        sheet.setAssignTime(entity.getAssignTime());
        sheet.setDescription(entity.getDescription());
        
        List<DataPcrTask> tasks = wrapForTesting(entity.getTestingSheetTaskList());
        
        Collections.sort(tasks, new Comparator<DataPcrTask>()
        {
            @Override
            public int compare(DataPcrTask o1, DataPcrTask o2)
            {
                return new TestingCodeComparator().compare(o1.getPcrTestCode(), o2.getPcrTestCode());
            }
        });
        
        sheet.setTasks(tasks);
        return sheet;
    }
    
    @Override
    @Transactional
    public void submit(DataPcrSubmitRequest request, String token, VariableModel model)
    {
        // 1、设置提交上下文数据
        DataPcrSubmitContext context = generateSubmitContext(request, token);
        
        // 2、更新任务结果
        doUpdateTasks(context);
        
        // 3、创建新节点任务
        //        doCreateNextNodeTasks(context);
        
        // 4、设置设计成功样本相关流程激活节点状态
        doUpdateScheduleNextActives(context, model);
        
        // 5、设置设计成功样本相关流程激活节点状态
        doUpdateScheduleErrorActives(context);
        
        // 6、设置任务单提交结果
        doUpdateSecondPcrSheet(context);
        
        // 7、完成任务单待办事项
        doCompleteProcess(context);
        
        //8.保存图片
        dataService.doSaveDataAnalyPic(request.getPicList(), TaskSemantic.DATA_ANALYSIS, request.getId(), 2);
    }
    
    private DataPcrSubmitContext generateSubmitContext(DataPcrSubmitRequest request, String token)
    {
        TestingSheet sheet = testingSheetService.getSheet(request.getId());
        
        if (null == sheet)
        {
            throw new IllegalStateException();
        }
        
        DataPcrSubmitContext context = new DataPcrSubmitContext();
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
    
    private void setContextForTestingSheetTask(TestingSheetTask task, DataPcrSubmitContext context)
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
    
    private void doUpdateTasks(DataPcrSubmitContext context)
    {
        Set<DataPcrSubmitTaskContext> records = context.getRelatedTasks();
        
        TestingTask task;
        Date timestamp = new Date();
        TestingTaskResult result;
        DataPcrSubmitTaskArgs dataPcrSubmitTaskArgs;
        String testingTaskResult = "";
        
        for (DataPcrSubmitTaskContext record : records)
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
            result.setDetails(JsonUtils.asJson(record.getDataPcrSubmitTaskArgs()));
            baseDaoSupport.insert(result);
        }
    }
    
    private void doCreateNextNodeTasks(DataPcrSubmitContext context)
    {
        Set<DataPcrSubmitNextTaskContext> nextTasks = context.getNextNodeTasks();
        
        TestingTask task;
        Date timestamp = new Date();
        
        for (DataPcrSubmitNextTaskContext nextTask : nextTasks)
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
    
    private void doUpdateScheduleNextActives(DataPcrSubmitContext context, VariableModel model)
    {
        Set<DataPcrSubmitScheduleContext> schedules = context.getGotoNextSchedules();
        
        TestingSchedule schedule;
        TestingScheduleActive active;
        Date timestamp = new Date();
        List<String> ids = Lists.newArrayList();
        for (DataPcrSubmitScheduleContext scheduleContext : schedules)
        {
            schedule = scheduleContext.getTestingScheduleEntity();
            schedule.setActiveTask("已完成");
            schedule.setEndType(TestingSchedule.END_SUCCESS);
            schedule.setEndTime(timestamp);
            baseDaoSupport.update(schedule);
            
            active = scheduleContext.getTestingScheduleActiveEntity();
            baseDaoSupport.delete(active);
            ids.add(schedule.getId());
            
            //报告处理为上报的流程，重新实验走完分析节点，状态置为合格
            testingScheduleService.updateReportSample(schedule.getId());
        }
        String scheduleIds = StringUtils.join(ids, ",");
        model.setScheduleIds(scheduleIds);
    }
    
    private void doUpdateScheduleErrorActives(DataPcrSubmitContext context)
    {
        Set<DataPcrSubmitScheduleContext> schedules = context.getGotoErrorSchedules();
        
        TestingSchedule thisSchedule;
        TestingScheduleActive thisActive;
        String dispose;
        TestingTask thisTask;
        TestingTask nextTask;
        TestingTask lastTask;
        for (DataPcrSubmitScheduleContext scheduleContext : schedules)
        {
            dispose = scheduleContext.getDispose();
            thisSchedule = scheduleContext.getTestingScheduleEntity();
            thisActive = scheduleContext.getTestingScheduleActiveEntity();
            thisTask = scheduleContext.getTestingTask();
            
            //报告处理为上报的流程，重新实验走完分析节点，状态置为合格
            testingScheduleService.updateReportSample(thisSchedule.getId());
            
            if ("重新设计引物".equals(dispose))
            {
                TaskConfig primerConfig = bcmadapter.getTaskConfigBySemantic(TaskSemantic.PRIMER_DESIGN);
                
                lastTask = testingScheduleService.getScheduleNodeLastTestingTask(thisSchedule.getId(), TaskSemantic.PRIMER_DESIGN);
                
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
                        existPrimerTask = technicalAnalyService.getTestingTaskByChromAndLocation1(record.getChromosome(), record.getBeginLocus(), "Sanger");
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
                    PrimerDesignTaskVariables pdVal = new PrimerDesignTaskVariables();
                    pdVal.setRemark(scheduleContext.getRemark());
                    variables.setText(JsonUtils.asJson(pdVal));
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
            else if ("一次PCR".equals(dispose))
            {
                lastTask = testingScheduleService.getScheduleNodeLastTestingTask(thisSchedule.getId(), TaskSemantic.PCR_ONE);
                if (null == lastTask)
                {
                    throw new IllegalStateException();
                }
                
                nextTask = new TestingTask();
                nextTask.setName(lastTask.getName());
                nextTask.setSemantic(lastTask.getSemantic());
                nextTask.setStatus(TestingTask.STATUS_ASSIGNABLE);
                nextTask.setResubmit(true);
                nextTask.setResubmitCount(null == lastTask.getResubmitCount() ? 1 : (lastTask.getResubmitCount() + 1));
                nextTask.setStartTime(new Date());
                nextTask.setInputSample(lastTask.getInputSample());
                baseDaoSupport.insert(nextTask);
                
                TestingTaskRunVariable variables = new TestingTaskRunVariable();
                variables.setTestingTaskId(nextTask.getId());
                FirstPcrTaskVariables pcrVal = new FirstPcrTaskVariables();
                pcrVal.setRemark(scheduleContext.getRemark());
                variables.setText(JsonUtils.asJson(pcrVal));
                baseDaoSupport.insert(variables);
                
                thisActive.setTaskId(nextTask.getId());
                baseDaoSupport.update(thisActive);
                
                TestingScheduleHistory history = new TestingScheduleHistory();
                history.setScheduleId(thisSchedule.getId());
                history.setTaskId(nextTask.getId());
                history.setTimestamp(new Date());
                baseDaoSupport.insert(history);
                
                thisSchedule.setActiveTask(lastTask.getName());
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
            else if ("重新测序".equals(dispose) || "反向测序".equals(dispose))
            {
                lastTask = testingScheduleService.getScheduleNodeLastTestingTask(thisSchedule.getId(), TaskSemantic.PCR_TWO);
                SecondPcrTaskVariables secondPcrVariable = getPcrTwoTaskRunningVariables(lastTask.getId());
                
                if (null == lastTask)
                {
                    throw new IllegalStateException();
                }
                
                nextTask = new TestingTask();
                nextTask.setName(lastTask.getName());
                nextTask.setSemantic(lastTask.getSemantic());
                nextTask.setStatus(TestingTask.STATUS_ASSIGNABLE);
                nextTask.setResubmit(true);
                nextTask.setResubmitCount(null == lastTask.getResubmitCount() ? 1 : (lastTask.getResubmitCount() + 1));
                nextTask.setStartTime(new Date());
                nextTask.setInputSample(lastTask.getInputSample());
                baseDaoSupport.insert(nextTask);
                
                TestingTaskRunVariable variables = new TestingTaskRunVariable();
                variables.setTestingTaskId(nextTask.getId());
                secondPcrVariable.setRemark(scheduleContext.getRemark());
                secondPcrVariable.setFlag(2);
                if ("重新测序".equals(dispose))
                {
                    secondPcrVariable.setForwardFlag(1);
                }
                else
                {
                    secondPcrVariable.setForwardFlag(2);
                }
                
                variables.setText(JsonUtils.asJson(secondPcrVariable));
                baseDaoSupport.insert(variables);
                
                thisActive.setTaskId(nextTask.getId());
                baseDaoSupport.update(thisActive);
                
                TestingScheduleHistory history = new TestingScheduleHistory();
                history.setScheduleId(thisSchedule.getId());
                history.setTaskId(nextTask.getId());
                history.setTimestamp(new Date());
                baseDaoSupport.insert(history);
                
                thisSchedule.setActiveTask(lastTask.getName());
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
                //                thisSchedule.setEndType(TestingSchedule.END_FAILURE);
                //                thisSchedule.setEndTime(new Date());
                baseDaoSupport.update(thisSchedule);
                
                baseDaoSupport.delete(thisActive);
                
            }
            
        }
    }
    
    private void doUpdateSecondPcrSheet(DataPcrSubmitContext context)
    {
        TestingSheet sheet = context.getSheetEntity();
        UserMinimalModel submiter = context.getSubmiter();
        sheet.setSubmiterId(submiter.getId());
        sheet.setSubmiterName(submiter.getName());
        sheet.setSubmitTime(new Date());
        baseDaoSupport.update(sheet);
    }
    
    private void doCompleteProcess(DataPcrSubmitContext context)
    {
        TestingSheet sheet = context.getSheetEntity();
        activitiService.submitTestingSheet(sheet.getId());
    }
    
    private List<DataPcrTask> wrapForTesting(List<TestingSheetTask> records)
    {
        if (CollectionUtils.isEmpty(records))
        {
            return Collections.emptyList();
        }
        
        TestingTask testingTask;
        List<DataPcrTask> tasks = new ArrayList<DataPcrTask>();
        
        for (TestingSheetTask record : records)
        {
            testingTask = testingTaskService.get(record.getTestingTaskId());
            
            if (null == testingTask)
            {
                throw new IllegalStateException();
            }
            
            tasks.add(wrap(testingTask));
        }
        
        return tasks;
    }
    
    private DataPcrTask wrap(TestingTask entity)
    {
        DataPcrTask task = new DataPcrTask();
        task.setId(entity.getId());
        task.setStartTime(entity.getStartTime());
        //根据testingTask 查询相关的流程 再查寻到技术分析数据表
        DataPcrTaskVariables variables = JsonUtils.asObject(entity.getTestingInputArgs(), DataPcrTaskVariables.class);
        
        if (null != variables)
        {
            task.setPcrTaskCode(variables.getPcrTaskCode());
            task.setPcrTestCode(variables.getPcrTestCode());
            task.setTestorName(variables.getTestorName());
            task.setTestDate(variables.getTestDate());
        }
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
        
        task.setSampleName(entity.getReceivedSampleName());
        task.setSampleCode(entity.getReceivedSampleCode());
        task.setGene(entity.getVerifyGene());
        task.setChromosomeLocation(entity.getVerifyChromosomePosition());
        task.setCombineCode(entity.getTestingCombineCode());
        
        task.setInputSampleFamilyRelation(entity.getFamilyRelation());
        task.setProductName(entity.getProductName());
        task.setOrderCode(entity.getOrderCode());
        //task.setSequencingCode(entity.getTestingLaneCode());
        
        //查找 未冗余存储的 测序编号 和 家系关系，导出任务单时展示
        List<TestingSchedule> schedules = testingScheduleService.getRelatedSchedules(entity.getId());
        if (Collections3.isEmpty(schedules))
        {
            schedules = testingScheduleService.getRelatedSchedulesByTestingTask(entity.getId());
        }
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
        return task;
    }
    
    private void setPlannedFinishDate(TestingTask entity, DataPcrTask task)
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
    
    public void setTaskVariables(DataPcrTask task, SangerVerifyRecord sangerVerifyRecord, TestingSchedule testingSchedule, TestingTask entity)
    {
        //查找家系关系
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
        
        //查找测序编号
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
            
            methodId = getMethodId(semantic);
            
            String hqlSample = "FROM TestingSample ts WHERE ts.receivedSample.sampleId = :sampleId AND ts.parentSample IS NULL";
            String sampleId = "";
            NamedQueryer queryerSample =
                NamedQueryer.builder()
                    .hql(hqlSample)
                    .names(Lists.newArrayList("sampleId"))
                    .values(Lists.newArrayList(entity.getInputSample().getReceivedSample().getSampleId()))
                    .build();
            List<TestingSample> samples = baseDaoSupport.find(queryerSample, TestingSample.class);
            if (Collections3.isNotEmpty(samples))
            {
                TestingSample sample = Collections3.getFirst(samples);
                sampleId = sample.getId();
            }
            
            List<TestingSchedule> testingSchedules = getSchedules(testingSchedule.getOrderId(), testingSchedule.getProductId(), methodId, sampleId);
            if (Collections3.isEmpty(testingSchedules))
            {
                methodId = getMethodId("SANGER");
                testingSchedules = getSchedules(testingSchedule.getOrderId(), testingSchedule.getProductId(), methodId, sampleId);
            }
            if (Collections3.isNotEmpty(testingSchedules))
            {
                TestingSchedule ts = Collections3.getFirst(testingSchedules);
                
                String tsId = ts.getId();
                if (StringUtils.isNotEmpty(ts.getVerifyTarget()))
                {
                    tsId = ts.getVerifyTarget();
                }
                
                String hql =
                    "SELECT tatt FROM TechnicalAnalyTestingTask tatt, TestingScheduleHistory tsh WHERE tsh.scheduleId = :scheduleId AND tatt.taskId = tsh.taskId";
                NamedQueryer queryer = NamedQueryer.builder().hql(hql).names(Lists.newArrayList("scheduleId")).values(Lists.newArrayList(tsId)).build();
                List<TechnicalAnalyTestingTask> technicalAnalyTestingTasks = baseDaoSupport.find(queryer, TechnicalAnalyTestingTask.class);
                if (Collections3.isNotEmpty(technicalAnalyTestingTasks))
                {
                    TechnicalAnalyTestingTask technicalAnalyTestingTask = Collections3.getFirst(technicalAnalyTestingTasks);
                    task.setSequencingCode(technicalAnalyTestingTask.getSequencingCode());
                }
            }
        }
    }
    
    private List<TestingSchedule> getSchedules(String orderId, String productId, String methodId, String sampleId)
    {
        String hql =
            "FROM TestingSchedule ts WHERE ts.orderId = :orderId AND ts.productId = :productId AND ts.methodId = :methodId AND ts.sampleId = :sampleId";
        NamedQueryer queryer =
            NamedQueryer.builder()
                .hql(hql)
                .names(Lists.newArrayList("orderId", "productId", "methodId", "sampleId"))
                .values(Lists.newArrayList(orderId, productId, methodId, sampleId))
                .build();
        List<TestingSchedule> testingSchedules = baseDaoSupport.find(queryer, TestingSchedule.class);
        return testingSchedules;
    }
    
    private String getMethodId(String semantic)
    {
        String methodId = "";
        String hql = "FROM TestingMethod tm WHERE tm.semantic = :semantic";
        NamedQueryer queryer = NamedQueryer.builder().hql(hql).names(Lists.newArrayList("semantic")).values(Lists.newArrayList(semantic)).build();
        List<TestingMethod> methods = baseDaoSupport.find(queryer, TestingMethod.class);
        if (Collections3.isNotEmpty(methods))
        {
            TestingMethod method = Collections3.getFirst(methods);
            methodId = method.getId();
        }
        return methodId;
    }
    
    private String getTestingMethodId()
    {
        String hql = "From TestingMethod m where m.semantic = :semantic";
        NamedQueryer queryer = NamedQueryer.builder().hql(hql).names(Lists.newArrayList("semantic")).values(Lists.newArrayList("SANGER")).build();
        return baseDaoSupport.find(queryer, TestingMethod.class).get(0).getId();
    }
}
