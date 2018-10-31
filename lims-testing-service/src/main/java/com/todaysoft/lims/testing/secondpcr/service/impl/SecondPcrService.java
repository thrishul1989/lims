package com.todaysoft.lims.testing.secondpcr.service.impl;

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
import com.todaysoft.lims.testing.base.entity.Order;
import com.todaysoft.lims.testing.base.entity.OrderPlanTask;
import com.todaysoft.lims.testing.base.entity.TestingSchedule;
import com.todaysoft.lims.testing.base.entity.TestingScheduleActive;
import com.todaysoft.lims.testing.base.entity.TestingScheduleHistory;
import com.todaysoft.lims.testing.base.entity.TestingSheet;
import com.todaysoft.lims.testing.base.entity.TestingSheetTask;
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
import com.todaysoft.lims.testing.base.service.TestingCodeComparator;
import com.todaysoft.lims.testing.base.service.adapter.bcm.BCMAdapter;
import com.todaysoft.lims.testing.base.service.adapter.bsm.BSMAdapter;
import com.todaysoft.lims.testing.base.service.adapter.smm.SMMAdapter;
import com.todaysoft.lims.testing.base.service.adapter.smm.UserMinimalModel;
import com.todaysoft.lims.testing.base.utils.JsonUtils;
import com.todaysoft.lims.testing.fluotest.model.FluoTestAssignTask;
import com.todaysoft.lims.testing.secondpcr.context.SecondPcrSubmitContext;
import com.todaysoft.lims.testing.secondpcr.context.SecondPcrSubmitNextTaskContext;
import com.todaysoft.lims.testing.secondpcr.context.SecondPcrSubmitScheduleContext;
import com.todaysoft.lims.testing.secondpcr.context.SecondPcrSubmitTaskContext;
import com.todaysoft.lims.testing.secondpcr.dao.SecondPcrAssignableTaskSearcher;
import com.todaysoft.lims.testing.secondpcr.model.SecondPcrAssignArgs;
import com.todaysoft.lims.testing.secondpcr.model.SecondPcrAssignModel;
import com.todaysoft.lims.testing.secondpcr.model.SecondPcrAssignRequest;
import com.todaysoft.lims.testing.secondpcr.model.SecondPcrAssignTaskArgs;
import com.todaysoft.lims.testing.secondpcr.model.SecondPcrSheetModel;
import com.todaysoft.lims.testing.secondpcr.model.SecondPcrSheetVariables;
import com.todaysoft.lims.testing.secondpcr.model.SecondPcrSubmitRequest;
import com.todaysoft.lims.testing.secondpcr.model.SecondPcrSubmitTaskArgs;
import com.todaysoft.lims.testing.secondpcr.model.SecondPcrTask;
import com.todaysoft.lims.testing.secondpcr.model.SecondPcrTaskVariables;
import com.todaysoft.lims.testing.secondpcr.service.ISecondPcrService;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.StringUtils;

@Service
public class SecondPcrService implements ISecondPcrService
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
    
    @Override
    public List<SecondPcrTask> getAssignableList(SecondPcrAssignableTaskSearcher searcher)
    {
        List<TestingTask> records = baseDaoSupport.find(searcher);
        
        if (CollectionUtils.isEmpty(records))
        {
            return Collections.emptyList();
        }
        
        List<SecondPcrTask> tasks = new ArrayList<SecondPcrTask>();
        List<SecondPcrTask> result = new ArrayList<SecondPcrTask>();
        
        for (TestingTask record : records)
        {
            tasks.add(wrap(record));
        }
        if (StringUtils.isNotEmpty(searcher.getPcrTaskCode()))
        {
            result = tasks.stream().filter(task -> task.getPcrTaskCode().contains(searcher.getPcrTaskCode())).collect(Collectors.toList());
        }
        else
        {
            result = tasks;
        }
        
        result.sort(Comparator.comparing(SecondPcrTask::getResubmitCount)
            .reversed()
            .thenComparing(SecondPcrTask::getPcrTaskCode)
            .thenComparing(SecondPcrTask::getPcrTestCode));
        //按加急降序
        result.sort(Comparator.comparing(SecondPcrTask::getIfUrgent).reversed());
        return result;
    }
    
    @Override
    public SecondPcrAssignModel getAssignableModel(SecondPcrAssignArgs args)
    {
        SecondPcrAssignModel model = new SecondPcrAssignModel();
        Map<String, String> primerLocationTempMap = Maps.newHashMap();
        
        if (null == args || StringUtils.isEmpty(args.getKeys()))
        {
            model.setTasks(Collections.emptyList());
            return model;
        }
        
        Set<String> includeKeys = new HashSet<String>(Arrays.asList(args.getKeys().split(",")));
        SecondPcrAssignableTaskSearcher searcher = new SecondPcrAssignableTaskSearcher();
        searcher.setIncludeKeys(includeKeys);
        List<SecondPcrTask> tasks = getAssignableList(searcher);
        // 设置实验编号
        String testingCode;
        
        for (int i = 0; i < tasks.size(); i++)
        {
            testingCode = commonService.getDNAExtractCode(i + 1);
            tasks.get(i).setSpcrTestCode(testingCode);
        }
        List<String> primerCodeList = tasks.stream().map(SecondPcrTask::getForwardPrimerCode).distinct().collect(Collectors.toList());
        for (int i = 0; i < primerCodeList.size(); i++)
        {
            primerLocationTempMap.put(primerCodeList.get(i), commonService.getSecondPrimerLocation(i + 1));
        }
        
        for (SecondPcrTask secondPcrTask : tasks)
        {
            secondPcrTask.setForwardPrimerLocationTemp(primerLocationTempMap.get(secondPcrTask.getForwardPrimerCode()));
        }
        model.setTasks(tasks);
        return model;
    }
    
    @Override
    @Transactional
    public void assign(SecondPcrAssignRequest request, String token)
    {
        if (!CollectionUtils.isEmpty(request.getTasks()))
        {
            TestingTask testingTask;
            SecondPcrTaskVariables firstPcrTaskVariables;
            
            for (SecondPcrAssignTaskArgs task : request.getTasks())
            {
                testingTask = testingTaskService.get(task.getId());
                testingTask.setStatus(TestingTask.STATUS_ASSIGNING);
                testingTaskService.modify(testingTask);
                
                firstPcrTaskVariables = getTaskRunningVariables(task.getId());
                firstPcrTaskVariables.setSpcrTestCode(task.getSpcrTestCode());
                firstPcrTaskVariables.setFpcrTaskCode(task.getPcrTaskCode());
                firstPcrTaskVariables.setFpcrTestCode(task.getPcrTestCode());
                firstPcrTaskVariables.setPrimerLocationTemp(task.getForwardPrimerLocationTemp());
                firstPcrTaskVariables.setFlag(2);
                testingTaskService.updateVariables(task.getId(), firstPcrTaskVariables);
                
                //更新冗余信息
                testingTaskService.updateTaskRedundantColumn(Arrays.asList(testingTask), 0);
                
            }
        }
        
        // 创建任务单
        TestingSheetCreateModel model = buildTestingSheetCreateModel(request, token);
        TestingSheet sheet = testingSheetService.create(model);
        
        // 启动流程
        activitiService.releaseTestingSheet(sheet);
    }
    
    @Override
    public SecondPcrTaskVariables getTaskRunningVariables(String taskId)
    {
        String variables = testingTaskService.getVariables(taskId);
        
        if (StringUtils.isEmpty(variables))
        {
            return new SecondPcrTaskVariables();
        }
        
        return JsonUtils.asObject(variables, SecondPcrTaskVariables.class);
    }
    
    private TestingSheetCreateModel buildTestingSheetCreateModel(SecondPcrAssignRequest request, String token)
    {
        UserMinimalModel loginer = smmadapter.getLoginUser(token);
        UserMinimalModel tester = smmadapter.getUserByID(request.getTesterId());
        TaskConfig taskConfig = bcmadapter.getTaskConfigBySemantic(TaskSemantic.PCR_TWO);
        
        TestingSheetCreateModel model = new TestingSheetCreateModel();
        model.setCode(commonService.getTaskCodeBySemantic(TaskSemantic.PCR_TWO)+"-Q");
        model.setTaskSemantic(TaskSemantic.PCR_TWO);
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
        
        if (!CollectionUtils.isEmpty(request.getTasks()))
        {
            List<String> keys = new ArrayList<String>();
            
            for (SecondPcrAssignTaskArgs task : request.getTasks())
            {
                keys.add(task.getId());
            }
            
            model.setTasks(keys);
        }
        
        // 设置二次PCR任务单自定义参数对象
        SecondPcrSheetVariables variables = new SecondPcrSheetVariables();
        variables.setReagentKitId(request.getReagentKitId());
        model.setVariables(variables);
        return model;
    }
    
    @Override
    public SecondPcrSheetModel getTestingSheet(String id)
    {
        TestingSheet entity = testingSheetService.getSheet(id);
        
        if (null == entity)
        {
            return null;
        }
        
        SecondPcrSheetModel sheet = new SecondPcrSheetModel();
        sheet.setId(entity.getId());
        sheet.setCode(entity.getCode());
        sheet.setAssignerName(entity.getAssignerName());
        sheet.setAssignTime(entity.getAssignTime());
        sheet.setTesterName(entity.getTesterName());
        sheet.setSubmitTime(entity.getSubmitTime());
        sheet.setDescription(entity.getDescription());
        
        SecondPcrSheetVariables variables = JsonUtils.asObject(entity.getVariables(), SecondPcrSheetVariables.class);
        
        List<SecondPcrTask> tasks = wrapForTesting(entity.getTestingSheetTaskList());
        
        if (null != variables)
        {
            String reagentKitId = variables.getReagentKitId();
            ReagentKitSimpleModel reagentKit = bsmadapter.getReagentKit(reagentKitId);
            
            if (null != reagentKit)
            {
                sheet.setReagentKitName(reagentKit.getName());
            }
            
            if (StringUtils.isNotEmpty(variables.getQcPointTestCode()) && StringUtils.isNotEmpty(variables.getQcPointPrimerLocation()))
            {
                SecondPcrTask emptyTask = new SecondPcrTask();
                emptyTask.setPcrTestCode(variables.getQcPointTestCode());
                emptyTask.setSpcrTestCode(variables.getQcPointTestCode());
                emptyTask.setForwardPrimerLocationTemp(variables.getQcPointPrimerLocation());
                tasks.add(emptyTask);
            }
        }
        
        Collections.sort(tasks, new Comparator<SecondPcrTask>()
        {
            @Override
            public int compare(SecondPcrTask o1, SecondPcrTask o2)
            {
                return new TestingCodeComparator().compare(o1.getSpcrTestCode(), o2.getSpcrTestCode());
            }
        });
        
        sheet.setTasks(tasks);
        return sheet;
    }
    
    @Override
    @Transactional
    public void submit(SecondPcrSubmitRequest request, String token)
    {
        // 1、设置提交上下文数据
        SecondPcrSubmitContext context = generateSubmitContext(request, token);
        
        // 2、更新任务结果
        doUpdateTasks(context);
        
        // 3、创建新节点任务
        doCreateNextNodeTasks(context);
        
        // 4、设置设计成功样本相关流程激活节点状态
        doUpdateScheduleNextActives(context);
        
        // 5、设置任务单提交结果
        doUpdateSecondPcrSheet(context);
        
        // 6、完成任务单待办事项
        doCompleteProcess(context);
    }
    
    private SecondPcrSubmitContext generateSubmitContext(SecondPcrSubmitRequest request, String token)
    {
        TestingSheet sheet = testingSheetService.getSheet(request.getId());
        
        if (null == sheet)
        {
            throw new IllegalStateException();
        }
        
        SecondPcrSubmitContext context = new SecondPcrSubmitContext();
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
    
    private void setContextForTestingSheetTask(TestingSheetTask task, SecondPcrSubmitContext context)
    {
        String id = task.getTestingTaskId();
        TestingTask testingTask = testingTaskService.get(id);
        context.setContextForTestingTask(testingTask);
        
        TaskConfig scheduleNextNodeConfig;
        TestingScheduleActive scheduleActive;
        List<TestingScheduleActive> scheduleActives;
        List<TestingSchedule> schedules = testingScheduleService.getRelatedSchedules(id);
        
        for (TestingSchedule schedule : schedules)
        {
            scheduleNextNodeConfig = testingScheduleService.getScheduleNextNodeConfig(schedule, testingTask.getSemantic());
            
            if (null == scheduleNextNodeConfig)
            {
                throw new IllegalStateException();
            }
            
            scheduleActives = testingScheduleService.getScheduleActives(schedule.getId());
            
            if (scheduleActives.size() != 1)
            {
                throw new IllegalStateException();
            }
            
            scheduleActive = null;
            for (TestingScheduleActive temp : scheduleActives)
            {
                TestingTask t = baseDaoSupport.get(TestingTask.class, temp.getTaskId());
                if (TaskSemantic.PCR_TWO.equals(t.getSemantic()))
                {
                    scheduleActive = temp;
                }
            }
            context.setContextForTestingTaskRelatedSchedule(testingTask, schedule, scheduleActive, scheduleNextNodeConfig);
        }
    }
    
    private void doUpdateTasks(SecondPcrSubmitContext context)
    {
        Set<SecondPcrSubmitTaskContext> records = context.getRelatedTasks();
        
        TestingTask task;
        Date timestamp = new Date();
        TestingTaskResult result;
        
        for (SecondPcrSubmitTaskContext record : records)
        {
            task = record.getTestingTaskEntity();
            task.setEndTime(timestamp);
            task.setStatus(TestingTask.STATUS_END);
            task.setEndType(TestingTask.END_SUCCESS);
            baseDaoSupport.update(task);
            
            result = new TestingTaskResult();
            result.setTaskId(task.getId());
            baseDaoSupport.insert(result);
        }
    }
    
    private void doCreateNextNodeTasks(SecondPcrSubmitContext context)
    {
        Set<SecondPcrSubmitNextTaskContext> nextTasks = context.getNextNodeTasks();
        
        TestingTask task;
        Date timestamp = new Date();
        TestingTaskRunVariable variables;
        SecondPcrSubmitTaskArgs secondPcrSubmitTaskArgs;
        
        for (SecondPcrSubmitNextTaskContext nextTask : nextTasks)
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
            
            variables = new TestingTaskRunVariable();
            variables.setTestingTaskId(task.getId());
            secondPcrSubmitTaskArgs = nextTask.getSecondPcrSubmitTaskArgs();
            secondPcrSubmitTaskArgs.setId(null);
            variables.setText(JsonUtils.asJson(secondPcrSubmitTaskArgs));
            baseDaoSupport.insert(variables);
            
            context.setContextForCreateNextNodeTask(nextTask.getTemporaryId(), task);
        }
    }
    
    private void doUpdateScheduleNextActives(SecondPcrSubmitContext context)
    {
        Set<SecondPcrSubmitScheduleContext> schedules = context.getGotoNextSchedules();
        
        String nextTaskId;
        TestingSchedule schedule;
        TestingScheduleActive active;
        TestingScheduleHistory history;
        TestingTask activeTestingTask;
        Date timestamp = new Date();
        
        for (SecondPcrSubmitScheduleContext scheduleContext : schedules)
        {
            schedule = scheduleContext.getTestingScheduleEntity();
            schedule.setActiveTask(scheduleContext.getNextNodeConfig().getName());
            baseDaoSupport.update(schedule);
            
            nextTaskId = context.getNextTaskCreatedId(scheduleContext.getId(), scheduleContext.getNextNodeInputSampleId());
            activeTestingTask = new TestingTask();
            activeTestingTask.setId(nextTaskId);
            
            active = scheduleContext.getTestingScheduleActiveEntity();
            active.setTaskId(activeTestingTask.getId());
            baseDaoSupport.update(active);
            
            history = new TestingScheduleHistory();
            history.setScheduleId(schedule.getId());
            history.setTaskId(nextTaskId);
            history.setTimestamp(timestamp);
            baseDaoSupport.insert(history);
            
            //更新冗余信息
            TestingTask nextTask = baseDaoSupport.get(TestingTask.class, nextTaskId);
            testingTaskService.updateTaskRedundantColumn(Arrays.asList(nextTask), 0);
            
            //设置加急
            Order order = baseDaoSupport.get(Order.class, schedule.getOrderId());
            if(null != order.getIfUrgent())
            {
                if(1 == order.getIfUrgent())
                {
                    nextTask.setIfUrgent(order.getIfUrgent());
                    nextTask.setUrgentName(order.getUrgentName());
                    nextTask.setUrgentUpdateTime(order.getUrgentUpdateTime());
                    baseDaoSupport.update(nextTask);
                }
            }
        }
    }
    
    private void doUpdateSecondPcrSheet(SecondPcrSubmitContext context)
    {
        TestingSheet sheet = context.getSheetEntity();
        UserMinimalModel submiter = context.getSubmiter();
        sheet.setSubmiterId(submiter.getId());
        sheet.setSubmiterName(submiter.getName());
        sheet.setSubmitTime(new Date());
        baseDaoSupport.update(sheet);
    }
    
    private void doCompleteProcess(SecondPcrSubmitContext context)
    {
        TestingSheet sheet = context.getSheetEntity();
        activitiService.submitTestingSheet(sheet.getId());
    }
    
    private List<SecondPcrTask> wrapForTesting(List<TestingSheetTask> records)
    {
        if (CollectionUtils.isEmpty(records))
        {
            return Collections.emptyList();
        }
        
        TestingTask testingTask;
        List<SecondPcrTask> tasks = new ArrayList<SecondPcrTask>();
        
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
    
    private SecondPcrTask wrap(TestingTask entity)
    {
        SecondPcrTask task = new SecondPcrTask();
        task.setId(entity.getId());
        task.setStartTime(entity.getStartTime());
        //设置加急
        if(null == entity.getIfUrgent())
        {
            task.setIfUrgent(0);
        }else{
            task.setIfUrgent(entity.getIfUrgent());
        }
        
        task.setUrgentName(entity.getUrgentName());
        task.setUrgentUpdateTime(entity.getUrgentUpdateTime());
        
        //根据testingTask 查询相关的流程 再查寻到技术分析数据表
        SecondPcrTaskVariables variables = JsonUtils.asObject(entity.getTestingInputArgs(), SecondPcrTaskVariables.class);
        
        String fpTaskCode = "";
        String fpTestCode = "";
        String spTestCode = "";
        String forwardPrimer = "";
        String primerLocationTemp = "";
        Integer forwardFlag = 1;
        
        if (null != variables)
        {
            fpTaskCode = variables.getFpcrTaskCode();
            fpTestCode = variables.getFpcrTestCode();
            spTestCode = variables.getSpcrTestCode();
            if (StringUtils.isNotEmpty(variables.getForwardFlag()))
            {
                forwardFlag = variables.getForwardFlag();
            }
            primerLocationTemp = variables.getPrimerLocationTemp();
            task.setSpcrTestCode(spTestCode);
            task.setPcrTaskCode(fpTaskCode);
            task.setPcrTestCode(fpTestCode);
            task.setForwardPrimerLocationTemp(primerLocationTemp);
            task.setFlag(variables.getFlag());
            task.setDescription(variables.getRemark());
        }
        task.setSampleName(entity.getReceivedSampleName());
        task.setSampleCode(entity.getTestingSampleCode());
        task.setResubmit(entity.isResubmit());
        task.setResubmitCount(entity.getResubmitCount());
        task.setCombineCode(entity.getTestingCombineCode());
        
        if (1 == forwardFlag.intValue())//1次PCR直接过来的二次PCR没有primer临时位置
        {
            forwardPrimer = entity.getTestingPrimerName();
        }
        else
        {
            forwardPrimer = entity.getTestingPrimerReverseName();
        }
        task.setForwardPrimerCode(forwardPrimer);
        task.setReversePrimerCode(entity.getTestingPrimerReverseName());
        task.setSixNineInfo(getSixNineInfo(spTestCode, fpTaskCode, fpTestCode, forwardPrimer));
        task.setNgsSeqCode(task.getCombineCode() + "_" + forwardPrimer);
        //
        //setPlannedFinishDate(entity, task);
        if(null != entity.getPlannedFinishDate())
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
    
    private void setPlannedFinishDate(TestingTask entity,SecondPcrTask task)
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
    
    private String getSixNineInfo(String spTestCode, String fpTaskCode, String fpTestCode, String primerCode)
    {
        String sixNineStr = "";
        if (StringUtils.isEmpty(spTestCode))
        {
            return sixNineStr;
        }
        sixNineStr = spTestCode + ":" + fpTaskCode + "-" + fpTestCode + ":" + "   " + primerCode;
        return sixNineStr;
    }
    
}
