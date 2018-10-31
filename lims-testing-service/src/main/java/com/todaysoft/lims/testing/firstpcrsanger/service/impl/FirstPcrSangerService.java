package com.todaysoft.lims.testing.firstpcrsanger.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
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
import com.todaysoft.lims.testing.base.entity.Order;
import com.todaysoft.lims.testing.base.entity.OrderExaminee;
import com.todaysoft.lims.testing.base.entity.OrderPlanTask;
import com.todaysoft.lims.testing.base.entity.Primer;
import com.todaysoft.lims.testing.base.entity.Product;
import com.todaysoft.lims.testing.base.entity.TestingSample;
import com.todaysoft.lims.testing.base.entity.TestingSampleStorage;
import com.todaysoft.lims.testing.base.entity.TestingSangerCount;
import com.todaysoft.lims.testing.base.entity.TestingSangerTestSample;
import com.todaysoft.lims.testing.base.entity.TestingSchedule;
import com.todaysoft.lims.testing.base.entity.TestingScheduleActive;
import com.todaysoft.lims.testing.base.entity.TestingScheduleHistory;
import com.todaysoft.lims.testing.base.entity.TestingSheet;
import com.todaysoft.lims.testing.base.entity.TestingSheetTask;
import com.todaysoft.lims.testing.base.entity.TestingTask;
import com.todaysoft.lims.testing.base.entity.TestingTaskResult;
import com.todaysoft.lims.testing.base.entity.TestingTaskRunVariable;
import com.todaysoft.lims.testing.base.model.ReagentKitSimpleModel;
import com.todaysoft.lims.testing.base.model.SampleTypeSimpleModel;
import com.todaysoft.lims.testing.base.model.TaskConfig;
import com.todaysoft.lims.testing.base.model.TaskSemantic;
import com.todaysoft.lims.testing.base.model.TestingSheetCreateModel;
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
import com.todaysoft.lims.testing.dnaqc.model.DNAAttributes;
import com.todaysoft.lims.testing.firstpcrsanger.context.FirstPcrSangerSubmitContext;
import com.todaysoft.lims.testing.firstpcrsanger.context.FirstPcrSangerSubmitNextTaskContext;
import com.todaysoft.lims.testing.firstpcrsanger.context.FirstPcrSangerSubmitScheduleContext;
import com.todaysoft.lims.testing.firstpcrsanger.context.FirstPcrSangerSubmitTaskContext;
import com.todaysoft.lims.testing.firstpcrsanger.dao.FirstPcrSangerAssignableTaskSearcher;
import com.todaysoft.lims.testing.firstpcrsanger.model.FirstPcrSangerAssignArgs;
import com.todaysoft.lims.testing.firstpcrsanger.model.FirstPcrSangerAssignModel;
import com.todaysoft.lims.testing.firstpcrsanger.model.FirstPcrSangerAssignRequest;
import com.todaysoft.lims.testing.firstpcrsanger.model.FirstPcrSangerAssignTaskArgs;
import com.todaysoft.lims.testing.firstpcrsanger.model.FirstPcrSangerSheetModel;
import com.todaysoft.lims.testing.firstpcrsanger.model.FirstPcrSangerSheetVariables;
import com.todaysoft.lims.testing.firstpcrsanger.model.FirstPcrSangerSubmitRequest;
import com.todaysoft.lims.testing.firstpcrsanger.model.FirstPcrSangerTask;
import com.todaysoft.lims.testing.firstpcrsanger.model.FirstPcrSangerTaskVariables;
import com.todaysoft.lims.testing.firstpcrsanger.service.IFirstPcrSangerService;
import com.todaysoft.lims.testing.samplesotck.service.ITestingSheetSampleStorageService;
import com.todaysoft.lims.testing.secondpcrsanger.model.SecondPcrSangerSheetVariables;
import com.todaysoft.lims.testing.secondpcrsanger.model.SecondPcrSangerTaskVariables;
import com.todaysoft.lims.testing.secondpcrsanger.service.ISecondPcrSangerService;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.DoubleCalculateUtils;
import com.todaysoft.lims.utils.StringUtils;

@Service
public class FirstPcrSangerService implements IFirstPcrSangerService
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
    private ISecondPcrSangerService secondPcrService;
    
    @Autowired
    private ITestingSheetSampleStorageService testingSheetSampleStorageService;
    
    @Override
    public List<FirstPcrSangerTask> getAssignableList(FirstPcrSangerAssignableTaskSearcher searcher)
    {
        List<TestingTask> records = baseDaoSupport.find(searcher);
        
        if (CollectionUtils.isEmpty(records))
        {
            return Collections.emptyList();
        }
        
        List<FirstPcrSangerTask> tasks = new ArrayList<FirstPcrSangerTask>();
        
        for (TestingTask record : records)
        {
            tasks.add(wrap(record));
        }
        
        tasks.sort(Comparator.comparing(FirstPcrSangerTask::getResubmitCount)
            .reversed()
            .thenComparing(FirstPcrSangerTask::getPlannedFinishDate)
            .thenComparing(FirstPcrSangerTask::getSampleName)
            .thenComparing(FirstPcrSangerTask::getForwardPrimerCode));
        //按加急降序
        tasks.sort(Comparator.comparing(FirstPcrSangerTask::getIfUrgent).reversed());
        return tasks;
    }
    
    @Override
    public FirstPcrSangerAssignModel getAssignableModel(FirstPcrSangerAssignArgs args)
    {
        FirstPcrSangerAssignModel model = new FirstPcrSangerAssignModel();
        
        if (null == args || StringUtils.isEmpty(args.getKeys()))
        {
            model.setTasks(Collections.emptyList());
            return model;
        }
        
        Set<String> includeKeys = new HashSet<String>(Arrays.asList(args.getKeys().split(",")));
        FirstPcrSangerAssignableTaskSearcher searcher = new FirstPcrSangerAssignableTaskSearcher();
        searcher.setIncludeKeys(includeKeys);
        List<FirstPcrSangerTask> tasks = getAssignableList(searcher);
        //位置降序   ww
        sortLocationMethod(tasks);
        // 设置实验编号
        String testingCode;
        
        for (int i = 0; i < tasks.size(); i++)
        {
            testingCode = commonService.getDNAExtractCode(i + 1);
            tasks.get(i).setPcrTestCode(testingCode);
        }
        model.setTasks(tasks);
        
        return model;
    }
    
    private void sortLocationMethod(List<FirstPcrSangerTask> list)
    {
        Collections.sort(list, new Comparator<FirstPcrSangerTask>()
        {
            
            @Override
            public int compare(FirstPcrSangerTask o1, FirstPcrSangerTask o2)
            {
                if (StringUtils.isNotEmpty(o1.getDnaLocation()) && StringUtils.isNotEmpty(o2.getDnaLocation()))
                {
                    return o1.getDnaLocation().compareTo(o2.getDnaLocation());
                }
                return 0;
            }
        });
    }
    
    @Override
    @Transactional
    public void assign(FirstPcrSangerAssignRequest request, String token)
    {
        if (!CollectionUtils.isEmpty(request.getTasks()))
        {
            TestingTask testingTask;
            FirstPcrSangerTaskVariables firstPcrTaskVariables;
            
            for (FirstPcrSangerAssignTaskArgs task : request.getTasks())
            {
                testingTask = testingTaskService.get(task.getId());
                testingTask.setStatus(TestingTask.STATUS_ASSIGNING);
                testingTaskService.modify(testingTask);
                
                firstPcrTaskVariables = getTaskRunningVariables(task.getId());
                firstPcrTaskVariables.setPcrTestCode(task.getPcrTestCode());
                firstPcrTaskVariables.setSampleInputQuantity(task.getSampleInputQuantity());
                testingTaskService.updateVariables(task.getId(), firstPcrTaskVariables);
                
                //更新冗余信息
                testingTaskService.updateTaskRedundantColumn(Arrays.asList(testingTask), 1);
                
            }
        }
        
        // 创建任务单
        TestingSheetCreateModel model = buildTestingSheetCreateModel(request, token);
        TestingSheet sheet = testingSheetService.create(model);
        
        //创建出库单
        testingSheetSampleStorageService.createStorageOut(sheet);
        
        // 启动流程
        activitiService.releaseTestingSheet(sheet);
    }
    
    public FirstPcrSangerTaskVariables getTaskRunningVariables(String taskId)
    {
        String variables = testingTaskService.getVariables(taskId);
        
        if (StringUtils.isEmpty(variables))
        {
            return new FirstPcrSangerTaskVariables();
        }
        
        return JsonUtils.asObject(variables, FirstPcrSangerTaskVariables.class);
    }
    
    private TestingSheetCreateModel buildTestingSheetCreateModel(FirstPcrSangerAssignRequest request, String token)
    {
        UserMinimalModel loginer = smmadapter.getLoginUser(token);
        UserMinimalModel tester = smmadapter.getUserByID(request.getTesterId());
        TaskConfig taskConfig = bcmadapter.getTaskConfigBySemantic(TaskSemantic.SANGER_PCR_ONE);
        
        TestingSheetCreateModel model = new TestingSheetCreateModel();
        model.setCode(commonService.getTaskCodeBySemantic(TaskSemantic.PCR_ONE));
        model.setTaskSemantic(TaskSemantic.SANGER_PCR_ONE);
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
        
        // 设置一次PCR任务单自定义参数对象
        FirstPcrSangerSheetVariables variables = new FirstPcrSangerSheetVariables();
        variables.setReagentKitId(request.getReagentKitId());
        variables.setSecondPcrReagentKitId(request.getSecondPcrReagentKitId());
        variables.setSecondPcrTesterId(request.getSecondPcrTesterId());
        model.setVariables(variables);
        return model;
    }
    
    @Override
    public FirstPcrSangerSheetModel getTestingSheet(String id)
    {
        Map<String, String> sampleLocationTempMap = Maps.newHashMap();
        Map<String, String> primerLocationTempMap = Maps.newHashMap();
        TestingSheet entity = testingSheetService.getSheet(id);
        
        if (null == entity)
        {
            return null;
        }
        
        FirstPcrSangerSheetModel sheet = new FirstPcrSangerSheetModel();
        sheet.setId(entity.getId());
        sheet.setCode(entity.getCode());
        sheet.setAssignerName(entity.getAssignerName());
        sheet.setAssignTime(entity.getAssignTime());
        sheet.setDescription(entity.getDescription());
        
        FirstPcrSangerSheetVariables variables = JsonUtils.asObject(entity.getVariables(), FirstPcrSangerSheetVariables.class);
        
        if (null != variables)
        {
            String reagentKitId = variables.getReagentKitId();
            ReagentKitSimpleModel reagentKit = bsmadapter.getReagentKit(reagentKitId);
            
            if (null != reagentKit)
            {
                sheet.setReagentKitName(reagentKit.getName());
            }
            
        }
        
        List<FirstPcrSangerTask> tasks = wrapForTesting(entity.getTestingSheetTaskList());
        
        Collections.sort(tasks, new Comparator<FirstPcrSangerTask>()
        {
            @Override
            public int compare(FirstPcrSangerTask o1, FirstPcrSangerTask o2)
            {
                return new TestingCodeComparator().compare(o1.getPcrTestCode(), o2.getPcrTestCode());
            }
        });
        
        //根据样本编号生成样本临时位置
        List<String> sampleCodeList = tasks.stream().map(FirstPcrSangerTask::getSampleCode).distinct().collect(Collectors.toList());
        
        List<String> primerCodeList = tasks.stream().map(FirstPcrSangerTask::getForwardPrimerCode).distinct().collect(Collectors.toList());
        
        for (int i = 0; i < sampleCodeList.size(); i++)
        {
            sampleLocationTempMap.put(sampleCodeList.get(i), commonService.getFirstPcrSampleTempLocation(i + 1));
        }
        
        for (int i = 0; i < primerCodeList.size(); i++)
        {
            primerLocationTempMap.put(primerCodeList.get(i), commonService.getPrimerTempLocation(i + 1));
        }
        
        String combineCode = "";
        for (FirstPcrSangerTask firstPcrTask : tasks)
        {
            firstPcrTask.setSampleLocationTemp(sampleLocationTempMap.get(firstPcrTask.getSampleCode()));
            firstPcrTask.setForwardPrimerLocationTemp(primerLocationTempMap.get(firstPcrTask.getForwardPrimerCode()));
            firstPcrTask.setReversePrimerLocationTemp(firstPcrTask.getForwardPrimerLocationTemp().replace("F", "R"));
            combineCode = getCombineCode(firstPcrTask, entity.getCode());
            firstPcrTask.setCombineCode(combineCode);
        }
        
        sheet.setTasks(tasks);
        return sheet;
    }
    
    @Override
    @Transactional
    public void submit(FirstPcrSangerSubmitRequest request, String token)
    {
        // 1、设置提交上下文数据
        FirstPcrSangerSubmitContext context = generateSubmitContext(request, token);
        
        // 2、更新任务结果
        doUpdateTasks(context);
        
        //删除已取消的任务节点activbe
        doDeleteCancerTasks(context);
        
        //创建一次PCR产物
        doCreateTestingSample(context);
        
        // 3、创建新节点任务
        doCreateNextNodeTasks(context);
        
        // 4、设置设计成功样本相关流程激活节点状态
        doUpdateScheduleNextActives(context);
        
        // 5、设置设计失败样本相关流程激活节点状态
        doUpdateScheduleErrorActives(context);
        
        // 6、设置任务单提交结果
        doUpdateFirstPcrSheet(context);
        
        // 7、完成任务单待办事项
        doCompleteProcess(context);
        
        // 8、生成二次PCR任务单
        doCreateSecondPcrControlSheet(context);
        
        //9、保存一次PCR临时产物 表
        doCreateSangerTestSample(context);
    }
    
    private FirstPcrSangerSubmitContext generateSubmitContext(FirstPcrSangerSubmitRequest request, String token)
    {
        TestingSheet sheet = testingSheetService.getSheet(request.getId());
        
        if (null == sheet)
        {
            throw new IllegalStateException();
        }
        
        FirstPcrSangerSubmitContext context = new FirstPcrSangerSubmitContext();
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
    
    private void setContextForTestingSheetTask(TestingSheetTask task, FirstPcrSangerSubmitContext context)
    {
        String id = task.getTestingTaskId();
        TestingTask testingTask = testingTaskService.get(id);
        context.setContextForTestingTask(testingTask);
        
        TaskConfig scheduleNextNodeConfig;
        TestingScheduleActive scheduleActive;
        List<TestingSchedule> schedules = testingScheduleService.getRelatedSchedules(id);
        
        for (TestingSchedule schedule : schedules)
        {
            Product product = baseDaoSupport.get(Product.class, schedule.getProductId());
            if (null == product)
            {
                throw new IllegalStateException();
            }
            scheduleNextNodeConfig = testingScheduleService.getScheduleNextNodeConfig(schedule, testingTask.getSemantic());
            
            if (null == scheduleNextNodeConfig)
            {
                throw new IllegalStateException();
            }
            
            scheduleActive = testingScheduleService.getScheduleActive(schedule.getId(), task.getTestingTaskId());
            context.setContextForTestingTaskRelatedSchedule(testingTask, schedule, scheduleActive, scheduleNextNodeConfig, product.getId());
        }
    }
    
    private void doUpdateTasks(FirstPcrSangerSubmitContext context)
    {
        Set<FirstPcrSangerSubmitTaskContext> records = context.getRelatedTasks();
        
        TestingTask task;
        Date timestamp = new Date();
        TestingTaskResult result;
        String testingTaskResult = "";
        
        for (FirstPcrSangerSubmitTaskContext record : records)
        {
            if (record.getRunningStatus().intValue() == TestingScheduleActive.STATUS_CANCER)
            {
                continue;
            }
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
            result.setDetails(JsonUtils.asJson(record.getDispose()));
            baseDaoSupport.insert(result);
        }
    }
    
    private void doDeleteCancerTasks(FirstPcrSangerSubmitContext context)
    {
        Set<FirstPcrSangerSubmitScheduleContext> nextTasks = context.getAbnormalCancerTasks();
        
        TestingScheduleActive active;
        for (FirstPcrSangerSubmitScheduleContext fc : nextTasks)
        {
            active = fc.getTestingScheduleActiveEntity();
            baseDaoSupport.delete(active);
        }
    }
    
    private void doCreateTestingSample(FirstPcrSangerSubmitContext context)
    {
        Set<FirstPcrSangerSubmitNextTaskContext> nextTasks = context.getNextNodeTasks();
        
        TaskConfig taskConfig = bcmadapter.getTaskConfigBySemantic(TaskSemantic.SANGER_PCR_ONE);
        
        SampleTypeSimpleModel outputSampleType = bcmadapter.getSampleType(taskConfig.getOutputSampleId());
        
        TestingSample outputSample;
        TestingSample parentSample;
        
        for (FirstPcrSangerSubmitNextTaskContext record : nextTasks)
        {
            String temId = record.getTemporaryId();
            outputSample = context.getNextTaskTestingSampleByTempId(temId);
            if (null == outputSample)
            {
                parentSample = baseDaoSupport.get(TestingSample.class, record.getInputSampleId());
                outputSample = new TestingSample();
                outputSample.setSampleCode(record.getCombineCode());
                outputSample.setSampleTypeId(outputSampleType.getId());
                outputSample.setSampleTypeName(outputSampleType.getName());
                outputSample.setReceivedSample(parentSample.getReceivedSample());
                outputSample.setParentSample(parentSample);
                baseDaoSupport.insert(outputSample);
                context.setNextTaskTestingSample(temId, outputSample);
            }
        }
    }
    
    private void doCreateNextNodeTasks(FirstPcrSangerSubmitContext context)
    {
        Set<FirstPcrSangerSubmitNextTaskContext> nextTasks = context.getNextNodeTasks();
        Map<String, TestingSample> nextTaskTestingSamples = context.getNextTaskTestingSamples();
        
        TestingTask task;
        Date timestamp = new Date();
        TestingTaskRunVariable variables;
        
        for (FirstPcrSangerSubmitNextTaskContext nextTask : nextTasks)
        {
            task = new TestingTask();
            task.setName(nextTask.getTaskName());
            task.setSemantic(nextTask.getTaskSemantic());
            task.setInputSample(nextTaskTestingSamples.get(nextTask.getTemporaryId()));
            task.setStatus(TestingTask.STATUS_ASSIGNABLE);
            task.setResubmit(false);
            task.setResubmitCount(0);
            task.setStartTime(timestamp);
            baseDaoSupport.insert(task);
            
            variables = new TestingTaskRunVariable();
            variables.setTestingTaskId(task.getId());
            baseDaoSupport.insert(variables);
            
            context.setContextForCreateNextNodeTask(nextTask.getTemporaryId(), task, variables);
        }
    }
    
    private void doUpdateScheduleNextActives(FirstPcrSangerSubmitContext context)
    {
        Set<FirstPcrSangerSubmitScheduleContext> schedules = context.getGotoNextSchedules();
        TestingTask nextTask;
        TestingSchedule schedule;
        TestingScheduleActive active;
        TestingScheduleHistory history;
        TestingTask activeTestingTask;
        Date timestamp = new Date();
        
        List<TestingScheduleActive> listActive = Lists.newArrayList();
        
        for (FirstPcrSangerSubmitScheduleContext scheduleContext : schedules)
        {
            schedule = scheduleContext.getTestingScheduleEntity();
            
            nextTask = context.getNextTaskCreatedId(scheduleContext.getId(), scheduleContext.getTestingTask().getId());
            
            active = scheduleContext.getTestingScheduleActiveEntity();
            active.setTaskId(nextTask.getId());
            active.setRunningStatus(TestingScheduleActive.STATUS_NORMAL);
            baseDaoSupport.update(active);
            
            listActive = testingScheduleService.getRunningScheduleActives(schedule.getId());
            
            String activeName = testingScheduleService.getScheduleActiveName(schedule, listActive);
            
            schedule.setActiveTask(activeName);
            
            baseDaoSupport.update(schedule);
            
            history = new TestingScheduleHistory();
            history.setScheduleId(schedule.getId());
            history.setTaskId(nextTask.getId());
            history.setTimestamp(timestamp);
            baseDaoSupport.insert(history);
            
            //设置加急
            Order order = baseDaoSupport.get(Order.class, schedule.getOrderId());
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
            
        }
        
        //更新冗余信息
        Collection<FirstPcrSangerSubmitNextTaskContext> nextTasks = context.getNextTasks().values();
        for (FirstPcrSangerSubmitNextTaskContext model : nextTasks)
        {
            testingTaskService.updateTaskRedundantColumn(Arrays.asList(model.getTestingTaskEntity()), 0);
        }
        
    }
    
    private void doUpdateScheduleErrorActives(FirstPcrSangerSubmitContext context)
    {
        Set<FirstPcrSangerSubmitScheduleContext> schedules = context.getGotoErrorSchedules();
        
        TestingSchedule thisSchedule;
        TestingScheduleActive thisActive;
        TestingTask thisTask;
        String dispose;
        TestingTask nextTask;
        TestingSangerCount testingSangerCount;
        for (FirstPcrSangerSubmitScheduleContext scheduleContext : schedules)
        {
            String info = "";
            dispose = scheduleContext.getDispose();
            thisSchedule = scheduleContext.getTestingScheduleEntity();
            thisActive = scheduleContext.getTestingScheduleActiveEntity();
            thisTask = scheduleContext.getTestingTask();
            if (null == thisTask)
            {
                throw new IllegalStateException();
            }
            
            if ("一次PCR".equals(dispose))
            {
                nextTask = new TestingTask();
                nextTask.setName(thisTask.getName());
                nextTask.setSemantic(thisTask.getSemantic());
                nextTask.setStatus(TestingTask.STATUS_ASSIGNABLE);
                nextTask.setResubmit(true);
                nextTask.setResubmitCount(null == thisTask.getResubmitCount() ? 1 : (thisTask.getResubmitCount() + 1));
                nextTask.setStartTime(new Date());
                nextTask.setInputSample(thisTask.getInputSample());
                baseDaoSupport.insert(nextTask);
                
                TestingTaskRunVariable variables = new TestingTaskRunVariable();
                FirstPcrSangerTaskVariables firstPcr = getTaskRunningVariables(thisTask.getId());
                variables.setTestingTaskId(nextTask.getId());
                variables.setText(JsonUtils.asJson(firstPcr));
                baseDaoSupport.insert(variables);
                
                thisActive.setTaskId(nextTask.getId());
                thisActive.setRunningStatus(TestingScheduleActive.STATUS_NORMAL);
                baseDaoSupport.update(thisActive);
                
                TestingScheduleHistory history = new TestingScheduleHistory();
                history.setScheduleId(thisSchedule.getId());
                history.setTaskId(nextTask.getId());
                history.setTimestamp(new Date());
                baseDaoSupport.insert(history);
                
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
            else
            {//实验取消
                info = "实验取消";
                baseDaoSupport.delete(thisActive);
                
                //更新sanger检测任务统计表
                testingSangerCount = testingScheduleService.getTestingSangerCountByScheduleId(thisSchedule.getId());
                if (null == testingSangerCount)
                {
                    throw new IllegalStateException();
                }
                testingSangerCount.setCancerNum(testingSangerCount.getCancerNum() + 1);
                baseDaoSupport.update(testingSangerCount);
            }
            
            List<TestingScheduleActive> listActive = testingScheduleService.getRunningScheduleActives(thisSchedule.getId());
            
            String activeName = testingScheduleService.getScheduleActiveName(thisSchedule, listActive);
            if (StringUtils.isNotEmpty(info))
            {
                String aName = thisTask.getName() + "-异常";
                if (StringUtils.isNotEmpty(activeName))
                {
                    if (activeName.contains(thisTask.getName()))
                    {
                        activeName = activeName.replace(thisTask.getName(), aName);
                    }
                    else
                    {
                        activeName = activeName + "|" + aName;
                    }
                }
                else
                {
                    activeName = aName;
                }
            }
            thisSchedule.setActiveTask(activeName);
            baseDaoSupport.update(thisSchedule);
        }
    }
    
    private void doUpdateFirstPcrSheet(FirstPcrSangerSubmitContext context)
    {
        TestingSheet sheet = context.getSheetEntity();
        UserMinimalModel submiter = context.getSubmiter();
        sheet.setSubmiterId(submiter.getId());
        sheet.setSubmiterName(submiter.getName());
        sheet.setSubmitTime(new Date());
        baseDaoSupport.update(sheet);
    }
    
    private void doCompleteProcess(FirstPcrSangerSubmitContext context)
    {
        TestingSheet sheet = context.getSheetEntity();
        activitiService.submitTestingSheet(sheet.getId());
    }
    
    private void doCreateSecondPcrControlSheet(FirstPcrSangerSubmitContext context)
    {
        Set<FirstPcrSangerSubmitNextTaskContext> nextNodeTasks = context.getNextNodeTasks();
        if (Collections3.isEmpty(nextNodeTasks))
        {
            return;
        }
        String semantic = TaskSemantic.SANGER_PCR_TWO;
        TaskConfig config = bcmadapter.getTaskConfigBySemantic(semantic);
        TestingSheet fpSheet = context.getSheetEntity();
        FirstPcrSangerSheetVariables variables = context.getFirstPcrSheetVariables();
        UserMinimalModel tester = smmadapter.getUserByID(variables.getSecondPcrTesterId());
        
        SecondPcrSangerSheetVariables spVariables = new SecondPcrSangerSheetVariables();
        spVariables.setReagentKitId(variables.getSecondPcrReagentKitId());
        
        List<String> sheetTasks = new ArrayList<String>();
        
        for (FirstPcrSangerSubmitNextTaskContext nextNodeTask : nextNodeTasks)
        {
            if (semantic.equals(nextNodeTask.getTaskSemantic()))
            {
                sheetTasks.add(nextNodeTask.getTestingTaskEntity().getId());
            }
        }
        
        Date timestamp = new Date();
        TestingSheetCreateModel model = new TestingSheetCreateModel();
        model.setTaskSemantic(TaskSemantic.SANGER_PCR_TWO);
        model.setTaskName(config.getName());
        model.setCode(commonService.getTaskCodeBySemantic(TaskSemantic.PCR_TWO));
        model.setDescription(fpSheet.getDescription());
        model.setAssignerId(fpSheet.getAssignerId());
        model.setAssignerName(fpSheet.getAssignerName());
        model.setAssignTime(timestamp);
        model.setCreateTime(timestamp);
        model.setTesterId(tester.getId());
        model.setTesterName(tester.getName());
        model.setVariables(spVariables);
        model.setTasks(sheetTasks);
        TestingSheet sheet = testingSheetService.create(model);
        
        String hql = "FROM TestingSheetTask st WHERE st.testingSheet.id = :sheetId";
        List<TestingSheetTask> tasks = baseDaoSupport.findByNamedParam(TestingSheetTask.class, hql, new String[] {"sheetId"}, new Object[] {sheet.getId()});
        
        TestingTask task;
        TestingTaskRunVariable spVariablesEntity;
        SecondPcrSangerTaskVariables spTaskVariables;
        FirstPcrSangerSubmitNextTaskContext nextTaskContext;
        
        for (TestingSheetTask sheetTask : tasks)
        {
            nextTaskContext = context.getNextTaskConextById(sheetTask.getTestingTaskId());
            task = nextTaskContext.getTestingTaskEntity();
            spVariablesEntity = nextTaskContext.getTestingTaskVariablesEntity();
            
            task.setStatus(TestingTask.STATUS_ASSIGNING);
            spTaskVariables = secondPcrService.getTaskRunningVariables(task.getId());
            spTaskVariables.setFpcrTaskCode(nextTaskContext.getPcrTaskCode());
            spTaskVariables.setFpcrTestCode(nextTaskContext.getPcrTestCode());
            spTaskVariables.setSpcrTestCode(nextTaskContext.getPcrTestCode());
            spTaskVariables.setPrimerLocationTemp(nextTaskContext.getForwardPrimerLocationTemp());
            spTaskVariables.setCombineCode(nextTaskContext.getCombineCode());
            spTaskVariables.setPrimerId(nextTaskContext.getPrimerId());
            spTaskVariables.setFlag(1);
            spTaskVariables.setForwardFlag(1);
            task.setTestingInputArgs(JsonUtils.asJson(spTaskVariables));
            testingTaskService.modify(task);
            
            spTaskVariables = secondPcrService.getTaskRunningVariables(task.getId());
            spTaskVariables.setFpcrTaskCode(nextTaskContext.getPcrTaskCode());
            spTaskVariables.setFpcrTestCode(nextTaskContext.getPcrTestCode());
            spTaskVariables.setSpcrTestCode(nextTaskContext.getPcrTestCode());
            spTaskVariables.setPrimerLocationTemp(nextTaskContext.getForwardPrimerLocationTemp());
            spTaskVariables.setCombineCode(nextTaskContext.getCombineCode());
            spTaskVariables.setPrimerId(nextTaskContext.getPrimerId());
            spTaskVariables.setFlag(1);
            spTaskVariables.setForwardFlag(1);
            spVariablesEntity.setText(JsonUtils.asJson(spTaskVariables));
            baseDaoSupport.update(spVariablesEntity);
        }
        
        activitiService.releaseTestingSheet(sheet);
    }
    
    private void doCreateSangerTestSample(FirstPcrSangerSubmitContext context)
    {
        Set<FirstPcrSangerSubmitNextTaskContext> nextNodeTasks = context.getNextNodeTasks();
        Map<String, TestingSample> nextTaskTestingSamples = context.getNextTaskTestingSamples();
        TestingSangerTestSample testingSangerTestSample;
        
        for (FirstPcrSangerSubmitNextTaskContext record : nextNodeTasks)
        {
            testingSangerTestSample = new TestingSangerTestSample();
            testingSangerTestSample.setScheduleId(record.getScheduleId());
            testingSangerTestSample.setTestingTask(record.getPreTestingTaskEntity());
            testingSangerTestSample.setOutputSample(nextTaskTestingSamples.get(record.getTemporaryId()));
            Primer primer = baseDaoSupport.get(Primer.class, record.getPrimerId());
            if (null != primer)
            {
                testingSangerTestSample.setPrimer(primer);
            }
            testingSangerTestSample.setCombineCode(record.getCombineCode());
            baseDaoSupport.insert(testingSangerTestSample);
        }
    }
    
    private List<FirstPcrSangerTask> wrapForTesting(List<TestingSheetTask> records)
    {
        if (CollectionUtils.isEmpty(records))
        {
            return Collections.emptyList();
        }
        
        TestingTask testingTask;
        List<FirstPcrSangerTask> tasks = new ArrayList<FirstPcrSangerTask>();
        
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
    
    private FirstPcrSangerTask wrap(TestingTask entity)
    {
        FirstPcrSangerTask task = new FirstPcrSangerTask();
        task.setId(entity.getId());
        task.setStartTime(entity.getStartTime());
        task.setResubmit(entity.isResubmit());
        task.setResubmitCount(entity.getResubmitCount());
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
        
        List<TestingScheduleActive> activeList = testingScheduleService.getScheduleActivesByTaskId(entity.getId());
        List<TestingSchedule> scheduleList = testingScheduleService.getRelatedSchedulesByTestingTask(entity.getId());
        if (Collections3.isNotEmpty(activeList))
        {
            task.setRunningStatus(activeList.get(0).getRunningStatus());
        }
        TestingSchedule testingSchedule = Collections3.getFirst(scheduleList);
        if (null != testingSchedule)
        {
            Order order = baseDaoSupport.get(Order.class, testingSchedule.getOrderId());
            if (null != order)
            {
                List<OrderExaminee> list = order.getOrderExamineeList();
                if (Collections3.isNotEmpty(list))
                {
                    task.setClinicalRecommend(Collections3.getFirst(list).getClinicalRecommend());
                }
            }
            
        }
        
        FirstPcrSangerTaskVariables variables = JsonUtils.asObject(entity.getTestingInputArgs(), FirstPcrSangerTaskVariables.class);
        
        BigDecimal sampleInputQuantity = null;
        String primerId = "";
        
        if (null != variables)
        {
            primerId = variables.getPrimerId();
            task.setPcrTestCode(variables.getPcrTestCode());
            
            task.setPrimerId(primerId);
            if (StringUtils.isNotEmpty(primerId))
            {
                Primer primer = baseDaoSupport.get(Primer.class, primerId);
                if (null != primer)
                {
                    task.setReversePrimerCode(primer.getReversePrimerName());
                    task.setGene(primer.getGene());
                    task.setForwardPrimerCode(primer.getForwardPrimerName());
                }
            }
            sampleInputQuantity = variables.getSampleInputQuantity();
        }
        
        DNAAttributes attributes = JsonUtils.asObject(entity.getTestingSampleAttributes(), DNAAttributes.class);
        if (null != attributes)
        {
            task.setConcentration(attributes.getConcn());
            if (null != sampleInputQuantity)
            {
                double d = DoubleCalculateUtils.div(sampleInputQuantity.doubleValue(), attributes.getConcn().doubleValue(), 2);
                task.setVolume(getInputVolume(d));
            }
        }
        task.setSampleName(entity.getReceivedSampleName());
        task.setSampleCode(entity.getReceivedSampleCode());
        
        task.setProduct(entity.getProductName());
        
        String hql = "FROM TestingSampleStorage tss WHERE tss.sampleCode = :sampleCode";
        NamedQueryer queryer =
            NamedQueryer.builder().hql(hql).names(Lists.newArrayList("sampleCode")).values(Lists.newArrayList(entity.getTestingSampleCode())).build();
        List<TestingSampleStorage> locations = baseDaoSupport.find(queryer, TestingSampleStorage.class);
        if (Collections3.isNotEmpty(locations))
        {
            TestingSampleStorage tss = Collections3.getFirst(locations);
            task.setDnaLocation(tss.getLocationCode());
            task.setStorageStatus(tss.getStatus());
        }
        //
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
    
    private void setPlannedFinishDate(TestingTask entity, FirstPcrSangerTask task)
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
    
    private BigDecimal getInputVolume(double d)
    {
        BigDecimal pcrMin = bcmadapter.getFirstPcrMinInputVolume();
        if (DoubleCalculateUtils.compare(d, pcrMin.doubleValue()) <= 0)
        {
            return pcrMin;
        }
        else
        {
            return new BigDecimal(d);
        }
    }
    
    private String getCombineCode(FirstPcrSangerTask firstPcrSangerTask, String sheetCode)
    {
        String code = "";
        if (null != firstPcrSangerTask)
        {
            code = sheetCode + "_" + firstPcrSangerTask.getSampleCode() + "_" + firstPcrSangerTask.getGene() + "_" + firstPcrSangerTask.getForwardPrimerCode();
        }
        return code;
    }
    
}
