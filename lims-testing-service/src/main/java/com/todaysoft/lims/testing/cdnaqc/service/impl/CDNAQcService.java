package com.todaysoft.lims.testing.cdnaqc.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.testing.activiti.service.IActivitiService;
import com.todaysoft.lims.testing.base.entity.Order;
import com.todaysoft.lims.testing.base.entity.TestingSample;
import com.todaysoft.lims.testing.base.entity.TestingSchedule;
import com.todaysoft.lims.testing.base.entity.TestingScheduleActive;
import com.todaysoft.lims.testing.base.entity.TestingScheduleHistory;
import com.todaysoft.lims.testing.base.entity.TestingSheet;
import com.todaysoft.lims.testing.base.entity.TestingSheetTask;
import com.todaysoft.lims.testing.base.entity.TestingTask;
import com.todaysoft.lims.testing.base.entity.TestingTaskRunVariable;
import com.todaysoft.lims.testing.base.model.ReagentKitSimpleModel;
import com.todaysoft.lims.testing.base.model.TaskConfig;
import com.todaysoft.lims.testing.base.model.TaskSemantic;
import com.todaysoft.lims.testing.base.model.TaskSubmitModel;
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
import com.todaysoft.lims.testing.cdnaext.model.CDNAExtractTaskVariables;
import com.todaysoft.lims.testing.cdnaqc.context.CDNAQcSubmitContext;
import com.todaysoft.lims.testing.cdnaqc.context.CDNAQcSubmitNextTaskContext;
import com.todaysoft.lims.testing.cdnaqc.context.CDNAQcSubmitScheduleContext;
import com.todaysoft.lims.testing.cdnaqc.context.CDNAQcSubmitSolveTaskContext;
import com.todaysoft.lims.testing.cdnaqc.context.CDNAQcSubmitTaskContext;
import com.todaysoft.lims.testing.cdnaqc.dao.CDNAQcTaskSearcher;
import com.todaysoft.lims.testing.cdnaqc.model.CDNAAttributes;
import com.todaysoft.lims.testing.cdnaqc.model.CDNAQcAssignArgs;
import com.todaysoft.lims.testing.cdnaqc.model.CDNAQcAssignModel;
import com.todaysoft.lims.testing.cdnaqc.model.CDNAQcAssignSheet;
import com.todaysoft.lims.testing.cdnaqc.model.CDNAQcAssignSheetTask;
import com.todaysoft.lims.testing.cdnaqc.model.CDNAQcSheet;
import com.todaysoft.lims.testing.cdnaqc.model.CDNAQcSheetVariables;
import com.todaysoft.lims.testing.cdnaqc.model.CDNAQcSubmitSheetModel;
import com.todaysoft.lims.testing.cdnaqc.model.CDNAQcTask;
import com.todaysoft.lims.testing.cdnaqc.model.CDNAQcTaskVariables;
import com.todaysoft.lims.testing.cdnaqc.service.ICDNAQcService;
import com.todaysoft.lims.testing.dnaqc.model.DNAQcSheetVariables;
import com.todaysoft.lims.testing.samplesotck.service.ITestingSheetSampleStorageService;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.StringUtils;

@Service
public class CDNAQcService implements ICDNAQcService
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
    private ICommonService commonService;
    
    @Autowired
    private IActivitiService activitiService;
    
    @Autowired
    private ITestingScheduleService testingScheduleService;
    
    @Autowired
    private ITestingSheetSampleStorageService testingSheetSampleStorageService;
    
    @Override
    public List<CDNAQcTask> todo(CDNAQcTaskSearcher searcher)
    {
        List<TestingTask> records = baseDaoSupport.find(searcher);
        
        if (CollectionUtils.isEmpty(records))
        {
            return Collections.emptyList();
        }
        
        List<CDNAQcTask> tasks = new ArrayList<CDNAQcTask>();
        
        for (TestingTask record : records)
        {
            tasks.add(wrap(record));
        }
        
        return tasks;
    }
    
    @Override
    public CDNAQcAssignModel cdnaQcAssignList(CDNAQcAssignArgs args)
    {
        CDNAQcAssignModel model = new CDNAQcAssignModel();
        
        if (null == args || StringUtils.isEmpty(args.getKeys()))
        {
            model.setTasks(Collections.emptyList());
            return model;
        }
        
        Set<String> includeKeys = new HashSet<String>(Arrays.asList(args.getKeys().split(",")));
        CDNAQcTaskSearcher searcher = new CDNAQcTaskSearcher();
        searcher.setIncludeKeys(includeKeys);
        List<CDNAQcTask> tasks = this.todo(searcher);
        
        if (CollectionUtils.isEmpty(tasks))
        {
            model.setTasks(Collections.emptyList());
            return model;
        }
        
        //位置排序  ww 
        sortLocationMethod(tasks);
        
        // 设置实验编号
        String testingCode;
        
        for (int i = 0; i < tasks.size(); i++)
        {
            testingCode = commonService.getDNAExtractCode(i + 1);
            tasks.get(i).setTestingCode(testingCode);
        }
        
        model.setTasks(tasks);
        return model;
    }
    
    private void sortLocationMethod(List<CDNAQcTask> list)
    {
        Collections.sort(list, new Comparator<CDNAQcTask>()
        {
            
            @Override
            public int compare(CDNAQcTask o1, CDNAQcTask o2)
            {
                return o1.getLocation().compareTo(o2.getLocation());
            }
        });
    }
    
    @Override
    public TestingSheetCreateModel buildTestingSheetCreateModel(CDNAQcAssignSheet request, String token)
    {
        UserMinimalModel loginer = smmadapter.getLoginUser(token);
        UserMinimalModel tester = smmadapter.getUserByID(request.getQualityControlTesterId());
        TaskConfig taskConfig = bcmadapter.getTaskConfigBySemantic(TaskSemantic.CDNA_QC);
        
        TestingSheetCreateModel model = new TestingSheetCreateModel();
        model.setCode(commonService.getTaskCodeBySemantic(TaskSemantic.CDNA_QC));
        model.setTaskSemantic(TaskSemantic.CDNA_QC);
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
        
        // 设置DNA质检任务单自定义参数对象
        DNAQcSheetVariables variables = new DNAQcSheetVariables();
        variables.setQualityControlMethods(request.getQualityControlMethods());
        variables.setQualityControlReagentKitId(request.getQualityControlReagentKitId());
        model.setVariables(variables);
        return model;
    }
    
    @Override
    @Transactional
    public void assign(CDNAQcAssignSheet request, String token)
    {
        // 更新DNA质检任务运行时参数
        if (!CollectionUtils.isEmpty(request.getTasks()))
        {
            TestingTask testingTask;
            CDNAQcTaskVariables variables;
            
            for (CDNAQcAssignSheetTask task : request.getTasks())
            {
                testingTask = testingTaskService.get(task.getId());
                testingTask.setStatus(TestingTask.STATUS_ASSIGNING);
                testingTaskService.modify(testingTask);
                
                variables = testingTaskService.obtainVariables(task.getId(), CDNAQcTaskVariables.class);
                variables.setTestingCode(task.getTestingCode());
                testingTaskService.updateVariables(task.getId(), variables);
                
                //更新冗余信息
                testingTaskService.updateTaskRedundantColumn(Arrays.asList(testingTask), 1);
                
            }
        }
        
        // 创建任务单
        TestingSheetCreateModel model = buildTestingSheetCreateModel(request, token);
        TestingSheet sheet = testingSheetService.create(model);
        
        // 启动流程
        activitiService.releaseTestingSheet(sheet);
    }
    
    @Override
    public CDNAQcSheet getTestingSheet(String id)
    {
        TestingSheet entity = testingSheetService.getSheet(id);
        
        if (null == entity)
        {
            return null;
        }
        
        CDNAQcSheet sheet = new CDNAQcSheet();
        sheet.setId(entity.getId());
        sheet.setCode(entity.getCode());
        sheet.setAssignerName(entity.getAssignerName());
        sheet.setAssignTime(entity.getAssignTime());
        sheet.setDescription(entity.getDescription());
        
        CDNAQcSheetVariables variables = JsonUtils.asObject(entity.getVariables(), CDNAQcSheetVariables.class);
        
        if (null != variables)
        {
            String reagentKitId = variables.getQualityControlReagentKitId();
            ReagentKitSimpleModel reagentKit = bsmadapter.getReagentKit(reagentKitId);
            
            if (null != reagentKit)
            {
                sheet.setReagentKitName(reagentKit.getName());
            }
            
            sheet.setQualityControlMethods(variables.getQualityControlMethods());
            
        }
        
        List<CDNAQcTask> tasks = wrap(entity.getTestingSheetTaskList());
        
        Collections.sort(tasks, new Comparator<CDNAQcTask>()
        {
            @Override
            public int compare(CDNAQcTask o1, CDNAQcTask o2)
            {
                return new TestingCodeComparator().compare(o1.getTestingCode(), o2.getTestingCode());
            }
        });
        
        sheet.setTasks(tasks);
        return sheet;
    }
    
    @Override
    @Transactional
    public void submitSheet(CDNAQcSubmitSheetModel request, String token)
    {
        // 1、设置提交上下文数据
        CDNAQcSubmitContext context = generateSubmitContext(request, token);
        
        // 2、更新DNA质检任务结果
        doUpdateTasks(context);
        
        // 3、创建质检通过样本新节点任务
        doCreateNextNodeTasks(context);
        
        // 4、设置质检通过样本相关流程激活节点状态
        doUpdateScheduleNextActives(context);
        
        // 5、设置质检不通过样本相关流程激活节点状态
        doUpdateScheduleErrorActives(context);
        
        // 6、设置质检不通过，重新处理的相关流程
        doSolveUnqualifiedSchedules(context);
        
        // 7、设置任务单提交结果
        doUpdateSheet(context);
        
        // 8、完成DNA任务单待办事项
        doCompleteProcess(context);
        
        //生成质检入库单
        testingSheetSampleStorageService.createStorageIn(context.getSheetEntity());
    }
    
    private CDNAQcSubmitContext generateSubmitContext(CDNAQcSubmitSheetModel request, String token)
    {
        TestingSheet sheet = testingSheetService.getSheet(request.getId());
        
        if (null == sheet)
        {
            throw new IllegalStateException();
        }
        
        CDNAQcSubmitContext context = new CDNAQcSubmitContext();
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
    
    private void doUpdateTasks(CDNAQcSubmitContext context)
    {
        Set<CDNAQcSubmitTaskContext> records = context.getRelatedTasks();
        
        TestingSample testingSample;
        CDNAAttributes attributes;
        TaskSubmitModel taskSubmitData;
        
        for (CDNAQcSubmitTaskContext record : records)
        {
            taskSubmitData = new TaskSubmitModel();
            taskSubmitData.setEntity(record.getTestingTaskEntity());
            taskSubmitData.setSuccess(record.isQualified());
            taskSubmitData.setFailureRemark(record.getUnqualifiedRemark());
            taskSubmitData.setFailureStrategy(record.getUnqualifiedStrategy());
            testingTaskService.submit(taskSubmitData);
            
            attributes = new CDNAAttributes();
            attributes.setConcn(record.getConcn());
            attributes.setVolume(record.getVolume());
            attributes.setRatio2628(record.getRatio2628());
            attributes.setRatio2623(record.getRatio2623());
            attributes.setQualityLevel(record.getQualityLevel());
            testingSample = record.getTestingTaskEntity().getInputSample();
            testingSample.setAttributes(JsonUtils.asJson(attributes));
            baseDaoSupport.update(testingSample);
        }
    }
    
    private void doCreateNextNodeTasks(CDNAQcSubmitContext context)
    {
        Set<CDNAQcSubmitNextTaskContext> nextTasks = context.getNextNodeTasks();
        
        TestingTask task;
        TestingTaskRunVariable variables;
        TestingSample inputSample;
        Date timestamp = new Date();
        
        for (CDNAQcSubmitNextTaskContext nextTask : nextTasks)
        {
            task = new TestingTask();
            task.setName(nextTask.getTaskName());
            task.setSemantic(nextTask.getTaskSemantic());
            task.setStatus(TestingTask.STATUS_ASSIGNABLE);
            task.setResubmit(false);
            task.setResubmitCount(0);
            task.setStartTime(timestamp);
            inputSample = new TestingSample();
            inputSample.setId(nextTask.getInputSampleId());
            task.setInputSample(inputSample);
            baseDaoSupport.insert(task);
            
            variables = new TestingTaskRunVariable();
            variables.setTestingTaskId(task.getId());
            baseDaoSupport.insert(variables);
            context.setContextForCreateNextNodeTask(nextTask.getTemporaryId(), task);
        }
    }
    
    private void doUpdateScheduleNextActives(CDNAQcSubmitContext context)
    {
        Set<CDNAQcSubmitScheduleContext> schedules = context.getGotoNextSchedules();
        
        TestingSchedule schedule;
        TestingTask nextTask;
        Date timestamp = new Date();
        
        for (CDNAQcSubmitScheduleContext scheduleContext : schedules)
        {
            schedule = scheduleContext.getTestingScheduleEntity();
            nextTask = context.getNextTask(scheduleContext.getId(), scheduleContext.getNextNodeInputSampleId());
            testingScheduleService.gotoNextNode(schedule, TaskSemantic.CDNA_QC, nextTask, timestamp);
        }
        
        //更新冗余信息
        Collection<CDNAQcSubmitNextTaskContext> nextTasks = context.getNextTasks().values();
        for (CDNAQcSubmitNextTaskContext model : nextTasks)
        {
            testingTaskService.updateTaskRedundantColumn(Arrays.asList(model.getTestingTaskEntity()), 0);
        }
        
    }
    
    private void doUpdateScheduleErrorActives(CDNAQcSubmitContext context)
    {
        Set<CDNAQcSubmitScheduleContext> schedules = context.getGotoErrorSchedules();
        
        TestingSchedule schedule;
        
        for (CDNAQcSubmitScheduleContext scheduleContext : schedules)
        {
            schedule = scheduleContext.getTestingScheduleEntity();
            testingScheduleService.gotoError(schedule, TaskSemantic.CDNA_QC);
        }
    }
    
    private void doSolveUnqualifiedSchedules(CDNAQcSubmitContext context)
    {
        Set<CDNAQcSubmitSolveTaskContext> solveTasks = context.getSolveUnqualifiedTasks();
        
        if (CollectionUtils.isEmpty(solveTasks))
        {
            return;
        }
        
        for (CDNAQcSubmitSolveTaskContext solveTaskContext : solveTasks)
        {
            if (CollectionUtils.isEmpty(solveTaskContext.getRelatedSchedules()))
            {
                continue;
            }
            
            for (CDNAQcSubmitScheduleContext scheduleContext : solveTaskContext.getRelatedSchedules().values())
            {
                doSolveUnqualifiedSchedule(scheduleContext, solveTaskContext);
            }
        }
    }
    
    private void doSolveUnqualifiedSchedule(CDNAQcSubmitScheduleContext scheduleContext, CDNAQcSubmitSolveTaskContext solveTaskContext)
    {
        TestingTask solvedTask = solveTaskContext.getSolvedTask();
        
        if (solvedTask == null)
        {
            TaskConfig config = bcmadapter.getTaskConfigBySemantic(solveTaskContext.getStrategy());
            
            if (null == config)
            {
                throw new IllegalStateException();
            }
            
            TestingTask lastTask = testingScheduleService.getScheduleNodeLastTestingTask(scheduleContext.getId(), solveTaskContext.getStrategy());
            
            if (null == lastTask)
            {
                throw new IllegalStateException();
            }
            
            solvedTask = new TestingTask();
            solvedTask.setName(config.getName());
            solvedTask.setSemantic(config.getSemantic());
            solvedTask.setStatus(TestingTask.STATUS_ASSIGNABLE);
            solvedTask.setResubmit(true);
            solvedTask.setResubmitCount(null == lastTask.getResubmitCount() ? 1 : (lastTask.getResubmitCount() + 1));
            solvedTask.setStartTime(new Date());
            solvedTask.setInputSample(lastTask.getInputSample());
            baseDaoSupport.insert(solvedTask);
            
            TestingTaskRunVariable variables = new TestingTaskRunVariable();
            variables.setTestingTaskId(solvedTask.getId());
            CDNAExtractTaskVariables cdnaVariables = new CDNAExtractTaskVariables();
            cdnaVariables.setRemark(scheduleContext.getUnqualifiedRemark());
            variables.setText(JsonUtils.asJson(cdnaVariables));
            baseDaoSupport.insert(variables);
            
            solveTaskContext.setSolvedTask(solvedTask);
        }
        
        TestingScheduleActive active = new TestingScheduleActive();
        active.setSchedule(scheduleContext.getTestingScheduleEntity());
        active.setTaskId(solvedTask.getId());
        baseDaoSupport.insert(active);
        
        TestingScheduleHistory history = new TestingScheduleHistory();
        history.setScheduleId(scheduleContext.getId());
        history.setTaskId(solvedTask.getId());
        history.setTimestamp(new Date());
        baseDaoSupport.insert(history);
        
        testingScheduleService.setScheduleActiveName(scheduleContext.getTestingScheduleEntity());
        //设置加急
        Order order = baseDaoSupport.get(Order.class, scheduleContext.getTestingScheduleEntity().getOrderId());
        if (null != order.getIfUrgent())
        {
            if (1 == order.getIfUrgent())
            {
                solvedTask.setIfUrgent(order.getIfUrgent());
                solvedTask.setUrgentName(order.getUrgentName());
                solvedTask.setUrgentUpdateTime(order.getUrgentUpdateTime());
                baseDaoSupport.update(solvedTask);
            }
        }
        
        //跟新冗余信息
        testingTaskService.updateTaskRedundantColumn(Arrays.asList(solvedTask), 0);
    }
    
    private void doUpdateSheet(CDNAQcSubmitContext context)
    {
        TestingSheet sheet = context.getSheetEntity();
        UserMinimalModel submiter = context.getSubmiter();
        sheet.setSubmiterId(submiter.getId());
        sheet.setSubmiterName(submiter.getName());
        sheet.setSubmitTime(new Date());
        baseDaoSupport.update(sheet);
    }
    
    private void doCompleteProcess(CDNAQcSubmitContext context)
    {
        TestingSheet sheet = context.getSheetEntity();
        activitiService.submitTestingSheet(sheet.getId());
    }
    
    private void setContextForTestingSheetTask(TestingSheetTask task, CDNAQcSubmitContext context)
    {
        String id = task.getTestingTaskId();
        TestingTask testingTask = testingTaskService.get(id);
        context.setContextForTestingTask(testingTask);
        
        TaskConfig scheduleNextNodeConfig;
        List<TestingSchedule> schedules = testingScheduleService.getRelatedSchedules(id);
        
        for (TestingSchedule schedule : schedules)
        {
            scheduleNextNodeConfig = testingScheduleService.getScheduleNextNodeConfig(schedule, testingTask.getSemantic());
            
            if (null == scheduleNextNodeConfig)
            {
                throw new IllegalStateException();
            }
            
            context.setContextForTestingTaskRelatedSchedule(testingTask, schedule, scheduleNextNodeConfig);
        }
    }
    
    private List<CDNAQcTask> wrap(List<TestingSheetTask> records)
    {
        if (CollectionUtils.isEmpty(records))
        {
            return Collections.emptyList();
        }
        
        TestingTask testingTask;
        List<CDNAQcTask> tasks = new ArrayList<CDNAQcTask>();
        
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
    
    private CDNAQcTask wrap(TestingTask entity)
    {
        CDNAQcTaskVariables variables = JsonUtils.asObject(entity.getTestingInputArgs(), CDNAQcTaskVariables.class);
        
        CDNAQcTask task = new CDNAQcTask();
        task.setId(entity.getId());
        task.setResubmit(entity.isResubmit());
        task.setResubmitCount(entity.getResubmitCount());
        
        task.setOrderType(entity.getOrderMarketingCenter());
        task.setSampleCode(entity.getTestingSampleCode());
        task.setOriginalSampleCode(entity.getReceivedSampleCode());
        task.setOriginalSampleName(entity.getReceivedSampleName());
        task.setOriginalSampleTypeId(entity.getReceivedSampleTypeId());
        task.setOriginalSampleTypeName(entity.getReceivedSampleType());
        task.setOriginalSamplingDate(entity.getReceivedSampleSamplingTime());
        //设置加急
        task.setIfUrgent(entity.getIfUrgent());
        task.setUrgentName(entity.getUrgentName());
        task.setUrgentUpdateTime(entity.getUrgentUpdateTime());
        
        if (null != variables)
        {
            task.setLocation(variables.getLocation());
            task.setTestingCode(variables.getTestingCode());
            task.setRemark(variables.getRemark());
        }
        
        task.setProducts(entity.getProductName());
        
        return task;
    }
}
