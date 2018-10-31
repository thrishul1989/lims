package com.todaysoft.lims.testing.libqc.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.testing.activiti.service.IActivitiService;
import com.todaysoft.lims.testing.base.entity.Order;
import com.todaysoft.lims.testing.base.entity.Product;
import com.todaysoft.lims.testing.base.entity.ReceivedSample;
import com.todaysoft.lims.testing.base.entity.TestingMethod;
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
import com.todaysoft.lims.testing.base.service.ITestingScheduleService;
import com.todaysoft.lims.testing.base.service.ITestingSheetService;
import com.todaysoft.lims.testing.base.service.ITestingTaskService;
import com.todaysoft.lims.testing.base.service.TestingCodeComparator;
import com.todaysoft.lims.testing.base.service.adapter.bcm.BCMAdapter;
import com.todaysoft.lims.testing.base.service.adapter.bsm.BSMAdapter;
import com.todaysoft.lims.testing.base.service.adapter.smm.SMMAdapter;
import com.todaysoft.lims.testing.base.service.adapter.smm.UserMinimalModel;
import com.todaysoft.lims.testing.base.utils.JsonUtils;
import com.todaysoft.lims.testing.libcre.model.LibraryAttributes;
import com.todaysoft.lims.testing.libcre.model.LibraryCreateTaskVariables;
import com.todaysoft.lims.testing.libqc.context.LibraryQcSubmitContext;
import com.todaysoft.lims.testing.libqc.context.LibraryQcSubmitNextTaskContext;
import com.todaysoft.lims.testing.libqc.context.LibraryQcSubmitScheduleContext;
import com.todaysoft.lims.testing.libqc.context.LibraryQcSubmitSolveTaskContext;
import com.todaysoft.lims.testing.libqc.context.LibraryQcSubmitTaskContext;
import com.todaysoft.lims.testing.libqc.model.LibraryQcSheetVariables;
import com.todaysoft.lims.testing.libqc.model.LibraryQcSubmitModel;
import com.todaysoft.lims.testing.libqc.model.LibraryQcSubmitRequest;
import com.todaysoft.lims.testing.libqc.model.LibraryQcTask;
import com.todaysoft.lims.testing.libqc.model.LibraryQcTaskVariables;
import com.todaysoft.lims.testing.libqc.service.ILibraryQcService;
import com.todaysoft.lims.testing.samplesotck.service.ITestingSheetSampleStorageService;

@Service
public class LibraryQcService implements ILibraryQcService
{
    @Autowired
    private SMMAdapter smmadapter;
    
    @Autowired
    private BSMAdapter bsmadapter;
    
    @Autowired
    private BCMAdapter bcmadapter;
    
    @Autowired
    private BaseDaoSupport baseDaoSupport;
    
    @Autowired
    private ITestingTaskService testingTaskService;
    
    @Autowired
    private ITestingSheetService testingSheetService;
    
    @Autowired
    private IActivitiService activitiService;
    
    @Autowired
    private ITestingScheduleService testingScheduleService;
    
    @Autowired
    private ITestingSheetSampleStorageService testingSheetSampleStorageService;
    
    @Override
    public LibraryQcSubmitModel getSheet(String id)
    {
        TestingSheet entity = testingSheetService.getSheet(id);
        
        if (null == entity)
        {
            return null;
        }
        
        LibraryQcSubmitModel sheet = new LibraryQcSubmitModel();
        sheet.setId(entity.getId());
        sheet.setCode(entity.getCode());
        sheet.setAssignerName(entity.getAssignerName());
        sheet.setAssignTime(entity.getAssignTime());
        sheet.setDescription(entity.getDescription());
        
        LibraryQcSheetVariables variables = JsonUtils.asObject(entity.getVariables(), LibraryQcSheetVariables.class);
        
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
        
        List<LibraryQcTask> tasks = wrap(entity.getTestingSheetTaskList());
        
        Collections.sort(tasks, new Comparator<LibraryQcTask>()
        {
            @Override
            public int compare(LibraryQcTask o1, LibraryQcTask o2)
            {
                return new TestingCodeComparator().compare(o1.getTestingCode(), o2.getTestingCode());
            }
        });
        
        sheet.setTasks(tasks);
        return sheet;
    }
    
    @Override
    @Transactional
    public void submit(LibraryQcSubmitRequest request, String token)
    {
        // 1、设置提交上下文数据
        LibraryQcSubmitContext context = generateSubmitContext(request, token);
        
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
    
    private LibraryQcSubmitContext generateSubmitContext(LibraryQcSubmitRequest request, String token)
    {
        TestingSheet sheet = testingSheetService.getSheet(request.getId());
        
        if (null == sheet)
        {
            throw new IllegalStateException();
        }
        
        LibraryQcSubmitContext context = new LibraryQcSubmitContext();
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
    
    private void doUpdateTasks(LibraryQcSubmitContext context)
    {
        Set<LibraryQcSubmitTaskContext> records = context.getRelatedTasks();
        
        TestingSample testingSample;
        LibraryAttributes attributes;
        TaskSubmitModel taskSubmitData;
        
        for (LibraryQcSubmitTaskContext record : records)
        {
            taskSubmitData = new TaskSubmitModel();
            taskSubmitData.setEntity(record.getTestingTaskEntity());
            taskSubmitData.setSuccess(record.isQualified());
            taskSubmitData.setFailureRemark(record.getUnqualifiedRemark());
            taskSubmitData.setFailureStrategy(record.getUnqualifiedStrategy());
            testingTaskService.submit(taskSubmitData);
            
            testingSample = record.getTestingTaskEntity().getInputSample();
            attributes = getLibraryAttributes(testingSample.getAttributes());
            attributes.setConcn(record.getConcn());
            attributes.setVolume(record.getVolume());
            attributes.setRatio2628(record.getRatio2628());
            attributes.setRatio2623(record.getRatio2623());
            attributes.setFragmentLengthStart(record.getFragmentLengthStart());
            attributes.setFragmentLengthEnd(record.getFragmentLengthEnd());
            attributes.setQualityLevel(record.getQualityLevel());
            testingSample.setAttributes(JsonUtils.asJson(attributes));
            baseDaoSupport.update(testingSample);
        }
    }
    
    private void doCreateNextNodeTasks(LibraryQcSubmitContext context)
    {
        Set<LibraryQcSubmitNextTaskContext> nextTasks = context.getNextNodeTasks();
        
        TestingTask task;
        TestingTaskRunVariable variables;
        TestingSample inputSample;
        Date timestamp = new Date();
        
        for (LibraryQcSubmitNextTaskContext nextTask : nextTasks)
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
    
    private void doUpdateScheduleNextActives(LibraryQcSubmitContext context)
    {
        Set<LibraryQcSubmitScheduleContext> schedules = context.getGotoNextSchedules();
        
        TestingSchedule schedule;
        TestingTask nextTask;
        Date timestamp = new Date();
        
        for (LibraryQcSubmitScheduleContext scheduleContext : schedules)
        {
            schedule = scheduleContext.getTestingScheduleEntity();
            nextTask = context.getNextTask(scheduleContext.getId(), scheduleContext.getNextNodeInputSampleId());
            testingScheduleService.gotoNextNode(schedule, TaskSemantic.LIBRARY_QC, nextTask, timestamp);
        }
        
        //存储冗余探针信息
        
        Set<String> keySet = context.getNextTasks().keySet();
        
        Iterator<String> it = keySet.iterator();
        while (it.hasNext())
        {
            String i = it.next();
            LibraryQcSubmitNextTaskContext s = context.getNextTasks().get(i);
            TestingTask task = s.getTestingTaskEntity();
            
            //特殊处理接收样本为空的问题
            TestingSample sample = baseDaoSupport.get(TestingSample.class, task.getInputSample().getId());
            task.getInputSample().setReceivedSample(sample.getReceivedSample());
            
            //更新冗余信息,存储探针数据量
            
            List<Product> products = testingTaskService.getRelatedProducts(task.getId());
            
            if (CollectionUtils.isEmpty(products) || products.size() > 1)
            {
                throw new IllegalStateException();
            }
            
            List<TestingMethod> methods = testingTaskService.getRelatedTestingMethods(task.getId());
            
            if (CollectionUtils.isEmpty(methods) || methods.size() > 1)
            {
                throw new IllegalStateException();
            }
            
            BigDecimal totalSize = bcmadapter.getTestingDatasize(products.get(0).getId(), methods.get(0).getId());
            task.setTestingCaptureDataSize(null == totalSize ? "" : totalSize.toString());
            
            testingTaskService.updateTaskRedundantColumn(Arrays.asList(task), 0);
            
        }
        
        //        //存储冗余信息结束
        
    }
    
    private void doUpdateScheduleErrorActives(LibraryQcSubmitContext context)
    {
        Set<LibraryQcSubmitScheduleContext> schedules = context.getGotoErrorSchedules();
        
        Set<LibraryQcSubmitScheduleContext> sovledSchedules = context.getGotoSolveSchedules();
        
        TestingSchedule schedule;
        
        for (LibraryQcSubmitScheduleContext scheduleContext : schedules)
        {
            schedule = scheduleContext.getTestingScheduleEntity();
            testingScheduleService.gotoError(schedule, TaskSemantic.LIBRARY_QC);
        }
        
        for (LibraryQcSubmitScheduleContext scheduleContext : sovledSchedules)
        {
            schedule = scheduleContext.getTestingScheduleEntity();
            testingScheduleService.gotoError(schedule, TaskSemantic.LIBRARY_QC);
        }
    }
    
    private void doSolveUnqualifiedSchedules(LibraryQcSubmitContext context)
    {
        Set<LibraryQcSubmitSolveTaskContext> solveTasks = context.getSolveUnqualifiedTasks();
        
        if (CollectionUtils.isEmpty(solveTasks))
        {
            return;
        }
        
        for (LibraryQcSubmitSolveTaskContext solveTaskContext : solveTasks)
        {
            if (CollectionUtils.isEmpty(solveTaskContext.getRelatedSchedules()))
            {
                continue;
            }
            
            for (LibraryQcSubmitScheduleContext scheduleContext : solveTaskContext.getRelatedSchedules().values())
            {
                doSolveUnqualifiedSchedule(scheduleContext, solveTaskContext);
            }
        }
    }
    
    private void doSolveUnqualifiedSchedule(LibraryQcSubmitScheduleContext scheduleContext, LibraryQcSubmitSolveTaskContext solveTaskContext)
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
            LibraryCreateTaskVariables libCreateVariables = new LibraryCreateTaskVariables();
            libCreateVariables.setRemark(scheduleContext.getUnqualifiedRemark());
            variables.setText(JsonUtils.asJson(libCreateVariables));
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
        if(null != order.getIfUrgent())
        {
            if(1 == order.getIfUrgent())
            {
                solvedTask.setIfUrgent(order.getIfUrgent());
                solvedTask.setUrgentName(order.getUrgentName());
                solvedTask.setUrgentUpdateTime(order.getUrgentUpdateTime());
                baseDaoSupport.update(solvedTask);
            }
        }
        
        //更新冗余信息
        testingTaskService.updateTaskRedundantColumn(Arrays.asList(solvedTask), 0);
    }
    
    private void doUpdateSheet(LibraryQcSubmitContext context)
    {
        TestingSheet sheet = context.getSheetEntity();
        UserMinimalModel submiter = context.getSubmiter();
        sheet.setSubmiterId(submiter.getId());
        sheet.setSubmiterName(submiter.getName());
        sheet.setSubmitTime(new Date());
        baseDaoSupport.update(sheet);
    }
    
    private void doCompleteProcess(LibraryQcSubmitContext context)
    {
        TestingSheet sheet = context.getSheetEntity();
        activitiService.submitTestingSheet(sheet.getId());
    }
    
    private void setContextForTestingSheetTask(TestingSheetTask task, LibraryQcSubmitContext context)
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
    
    private LibraryAttributes getLibraryAttributes(String attributes)
    {
        LibraryAttributes object = JsonUtils.asObject(attributes, LibraryAttributes.class);
        
        if (null == object)
        {
            object = new LibraryAttributes();
        }
        
        return object;
    }
    
    private LibraryQcTask wrap(TestingTask entity)
    {
        LibraryQcTaskVariables variables = JsonUtils.asObject(entity.getTestingInputArgs(), LibraryQcTaskVariables.class);
        
        LibraryQcTask task = new LibraryQcTask();
        task.setId(entity.getId());
        task.setResubmit(entity.isResubmit());
        task.setResubmitCount(entity.getResubmitCount());
        
        task.setOrderType(entity.getOrderMarketingCenter());
        
        if (null != entity.getInputSample())
        {
            TestingSample inputSample = entity.getInputSample();
            task.setSampleCode(inputSample.getSampleCode());
            task.setIndex(JSON.parseObject(inputSample.getAttributes()).getString("index"));
            if (null != inputSample.getReceivedSample())
            {
                ReceivedSample receivedSample = inputSample.getReceivedSample();
                task.setOriginalSampleCode(receivedSample.getSampleCode());
                task.setOriginalSampleName(receivedSample.getSampleName());
                task.setOriginalSampleTypeId(receivedSample.getSampleTypeId());
                task.setOriginalSampleTypeName(receivedSample.getSampleTypeName());
                task.setOriginalSamplingDate(receivedSample.getSamplingDate());
            }
        }
        
        if (null != variables)
        {
            task.setLocation(variables.getLocation());
            task.setTestingCode(variables.getTestingCode());
        }
        
        task.setProducts(entity.getProductName());
        
        return task;
    }
    
    private List<LibraryQcTask> wrap(List<TestingSheetTask> records)
    {
        if (CollectionUtils.isEmpty(records))
        {
            return Collections.emptyList();
        }
        
        TestingTask testingTask;
        List<LibraryQcTask> tasks = new ArrayList<LibraryQcTask>();
        
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
}
