package com.todaysoft.lims.testing.secondpcrsanger.service.impl;

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

import com.google.common.collect.Lists;
import com.todaysoft.lims.testing.base.entity.*;
import com.todaysoft.lims.testing.secondpcrsanger.context.SecondPcrSangerSubmitContext;
import com.todaysoft.lims.testing.secondpcrsanger.context.SecondPcrSangerSubmitNextTaskContext;
import com.todaysoft.lims.testing.secondpcrsanger.context.SecondPcrSangerSubmitScheduleContext;
import com.todaysoft.lims.testing.secondpcrsanger.context.SecondPcrSangerSubmitTaskContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Maps;
import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.testing.activiti.service.IActivitiService;
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
import com.todaysoft.lims.testing.multipcr.model.MultiPcrAssignTask;
import com.todaysoft.lims.testing.secondpcrsanger.dao.SecondPcrSangerAssignableTaskSearcher;
import com.todaysoft.lims.testing.secondpcrsanger.model.*;
import com.todaysoft.lims.testing.secondpcrsanger.service.ISecondPcrSangerService;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.StringUtils;

@Service
public class SecondPcrSangerService implements ISecondPcrSangerService
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
    public List<SecondPcrSangerTask> getAssignableList(SecondPcrSangerAssignableTaskSearcher searcher)
    {
        List<TestingTask> records = baseDaoSupport.find(searcher);
        
        if (CollectionUtils.isEmpty(records))
        {
            return Collections.emptyList();
        }
        
        List<SecondPcrSangerTask> tasks = new ArrayList<SecondPcrSangerTask>();
        List<SecondPcrSangerTask> result = new ArrayList<SecondPcrSangerTask>();
        
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
        result.sort(Comparator.comparing(SecondPcrSangerTask::getResubmitCount).reversed()
            .thenComparing(SecondPcrSangerTask::getPlannedFinishDate));
        result.sort(Comparator.comparing(SecondPcrSangerTask::getIfUrgent).reversed());
        return result;
    }
    
    @Override
    public SecondPcrSangerAssignModel getAssignableModel(SecondPcrSangerAssignArgs args)
    {
        SecondPcrSangerAssignModel model = new SecondPcrSangerAssignModel();
        Map<String, String> primerLocationTempMap = Maps.newHashMap();
        
        if (null == args || StringUtils.isEmpty(args.getKeys()))
        {
            model.setTasks(Collections.emptyList());
            return model;
        }
        
        Set<String> includeKeys = new HashSet<String>(Arrays.asList(args.getKeys().split(",")));
        SecondPcrSangerAssignableTaskSearcher searcher = new SecondPcrSangerAssignableTaskSearcher();
        searcher.setIncludeKeys(includeKeys);
        List<SecondPcrSangerTask> tasks = getAssignableList(searcher);
        // 设置实验编号
        String testingCode;
        
        for (int i = 0; i < tasks.size(); i++)
        {
            testingCode = commonService.getDNAExtractCode(i + 1);
            tasks.get(i).setSpcrTestCode(testingCode);
        }
        List<String> primerCodeList = tasks.stream().map(SecondPcrSangerTask::getForwardPrimerCode).distinct().collect(Collectors.toList());
        for (int i = 0; i < primerCodeList.size(); i++)
        {
            primerLocationTempMap.put(primerCodeList.get(i), commonService.getSecondPrimerLocation(i + 1));
        }
        
        for (SecondPcrSangerTask secondPcrTask : tasks)
        {
            secondPcrTask.setForwardPrimerLocationTemp(primerLocationTempMap.get(secondPcrTask.getForwardPrimerCode()));
        }
        model.setTasks(tasks);
        return model;
    }
    
    @Override
    @Transactional
    public void assign(SecondPcrSangerAssignRequest request, String token)
    {
        if (!CollectionUtils.isEmpty(request.getTasks()))
        {
            TestingTask testingTask;
            SecondPcrSangerTaskVariables firstPcrTaskVariables;
            
            for (SecondPcrSangerAssignTaskArgs task : request.getTasks())
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
    public SecondPcrSangerTaskVariables getTaskRunningVariables(String taskId)
    {
        String variables = testingTaskService.getVariables(taskId);
        
        if (StringUtils.isEmpty(variables))
        {
            return new SecondPcrSangerTaskVariables();
        }
        
        return JsonUtils.asObject(variables, SecondPcrSangerTaskVariables.class);
    }
    
    private TestingSheetCreateModel buildTestingSheetCreateModel(SecondPcrSangerAssignRequest request, String token)
    {
        UserMinimalModel loginer = smmadapter.getLoginUser(token);
        UserMinimalModel tester = smmadapter.getUserByID(request.getTesterId());
        TaskConfig taskConfig = bcmadapter.getTaskConfigBySemantic(TaskSemantic.SANGER_PCR_TWO);
        
        TestingSheetCreateModel model = new TestingSheetCreateModel();
        model.setCode(commonService.getTaskCodeBySemantic(TaskSemantic.PCR_TWO));
        model.setTaskSemantic(TaskSemantic.SANGER_PCR_TWO);
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
            
            for (SecondPcrSangerAssignTaskArgs task : request.getTasks())
            {
                keys.add(task.getId());
            }
            
            model.setTasks(keys);
        }
        
        // 设置二次PCR任务单自定义参数对象
        SecondPcrSangerSheetVariables variables = new SecondPcrSangerSheetVariables();
        variables.setReagentKitId(request.getReagentKitId());
        model.setVariables(variables);
        return model;
    }
    
    @Override
    public SecondPcrSangerSheetModel getTestingSheet(String id)
    {
        
        Map<String, String> sampleLocationTempMap = Maps.newHashMap();
        TestingSheet entity = testingSheetService.getSheet(id);
        
        if (null == entity)
        {
            return null;
        }
        
        SecondPcrSangerSheetModel sheet = new SecondPcrSangerSheetModel();
        sheet.setId(entity.getId());
        sheet.setCode(entity.getCode());
        sheet.setAssignerName(entity.getAssignerName());
        sheet.setAssignTime(entity.getAssignTime());
        sheet.setTesterName(entity.getTesterName());
        sheet.setSubmitTime(entity.getSubmitTime());
        sheet.setDescription(entity.getDescription());
        
        SecondPcrSangerSheetVariables variables = JsonUtils.asObject(entity.getVariables(), SecondPcrSangerSheetVariables.class);
        
        if (null != variables)
        {
            String reagentKitId = variables.getReagentKitId();
            ReagentKitSimpleModel reagentKit = bsmadapter.getReagentKit(reagentKitId);
            
            if (null != reagentKit)
            {
                sheet.setReagentKitName(reagentKit.getName());
            }
        }
        
        List<SecondPcrSangerTask> tasks = wrapForTesting(entity.getTestingSheetTaskList());
        
        Collections.sort(tasks, new Comparator<SecondPcrSangerTask>()
        {
            @Override
            public int compare(SecondPcrSangerTask o1, SecondPcrSangerTask o2)
            {
                return new TestingCodeComparator().compare(o1.getSpcrTestCode(), o2.getSpcrTestCode());
            }
        });
        
        //根据样本编号生成样本临时位置
        List<String> sampleCodeList = tasks.stream().map(SecondPcrSangerTask::getSampleCode).distinct().collect(Collectors.toList());
        
        for (int i = 0; i < sampleCodeList.size(); i++)
        {
            sampleLocationTempMap.put(sampleCodeList.get(i), commonService.getFirstPcrSampleTempLocation(i + 1));
        }
        
        for (SecondPcrSangerTask secondPcrSangerTask : tasks)
        {
            secondPcrSangerTask.setSampleLocationTemp(sampleLocationTempMap.get(secondPcrSangerTask.getSampleCode()));
        }
        
        sheet.setTasks(tasks);
        return sheet;
    }
    
    @Override
    @Transactional
    public void submit(SecondPcrSangerSubmitRequest request, String token)
    {
        // 1、设置提交上下文数据
        SecondPcrSangerSubmitContext context = generateSubmitContext(request, token);
        
        // 2、更新任务结果
        doUpdateTasks(context);
        
        //删除已取消的任务节点activbe
        doDeleteCancerTasks(context);
        
        // 3、创建新节点任务
        doCreateNextNodeTasks(context);
        
        // 4、设置设计成功样本相关流程激活节点状态
        doUpdateScheduleNextActives(context);
        
        // 5、设置任务单提交结果
        doUpdateSecondPcrSheet(context);
        
        // 6、完成任务单待办事项
        doCompleteProcess(context);
    }
    
    private SecondPcrSangerSubmitContext generateSubmitContext(SecondPcrSangerSubmitRequest request, String token)
    {
        TestingSheet sheet = testingSheetService.getSheet(request.getId());
        
        if (null == sheet)
        {
            throw new IllegalStateException();
        }
        
        SecondPcrSangerSubmitContext context = new SecondPcrSangerSubmitContext();
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
    
    private void setContextForTestingSheetTask(TestingSheetTask task, SecondPcrSangerSubmitContext context)
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
            
            scheduleActive = testingScheduleService.getScheduleActive(schedule.getId(), task.getTestingTaskId());
            context.setContextForTestingTaskRelatedSchedule(testingTask, schedule, scheduleActive, scheduleNextNodeConfig);
        }
    }
    
    private void doUpdateTasks(SecondPcrSangerSubmitContext context)
    {
        Set<SecondPcrSangerSubmitTaskContext> records = context.getRelatedTasks();
        Map<String, TestingScheduleActive> abnormalCancerTasks = context.getAbnormalCancerTasks();
        
        TestingTask task;
        Date timestamp = new Date();
        TestingTaskResult result;
        
        for (SecondPcrSangerSubmitTaskContext record : records)
        {
            if (abnormalCancerTasks.containsKey(record.getTestingTaskEntity().getId()))
            {
                continue;
            }
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
    
    private void doDeleteCancerTasks(SecondPcrSangerSubmitContext context)
    {
        Set<TestingScheduleActive> cancerTasks = context.getAbnormalCancerTasksSet();
        
        for (TestingScheduleActive active : cancerTasks)
        {
            baseDaoSupport.delete(active);
        }
    }
    
    private void doCreateNextNodeTasks(SecondPcrSangerSubmitContext context)
    {
        Set<SecondPcrSangerSubmitNextTaskContext> nextTasks = context.getNextNodeTasks();
        
        TestingTask task;
        Date timestamp = new Date();
        TestingTaskRunVariable variables;
        SecondPcrSangerSubmitTaskArgs secondPcrSubmitTaskArgs;
        
        for (SecondPcrSangerSubmitNextTaskContext nextTask : nextTasks)
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
    
    private void doUpdateScheduleNextActives(SecondPcrSangerSubmitContext context)
    {
        Set<SecondPcrSangerSubmitScheduleContext> schedules = context.getGotoNextSchedules();
        
        String nextTaskId;
        TestingSchedule schedule;
        TestingScheduleActive active;
        TestingScheduleHistory history;
        TestingTask activeTestingTask;
        Date timestamp = new Date();
        List<TestingScheduleActive> listActive = Lists.newArrayList();
        
        for (SecondPcrSangerSubmitScheduleContext scheduleContext : schedules)
        {
            schedule = scheduleContext.getTestingScheduleEntity();
            
            activeTestingTask = context.getNextTaskCreatedId(scheduleContext.getId(), scheduleContext.getTestingTask().getId());
            
            active = scheduleContext.getTestingScheduleActiveEntity();
            active.setTaskId(activeTestingTask.getId());
            active.setRunningStatus(TestingScheduleActive.STATUS_NORMAL);
            baseDaoSupport.update(active);
            
            listActive = testingScheduleService.getRunningScheduleActives(schedule.getId());
            
            String activeName = testingScheduleService.getScheduleActiveName(schedule, listActive);
            
            schedule.setActiveTask(activeName);
            
            baseDaoSupport.update(schedule);
            
            history = new TestingScheduleHistory();
            history.setScheduleId(schedule.getId());
            history.setTaskId(activeTestingTask.getId());
            history.setTimestamp(timestamp);
            baseDaoSupport.insert(history);
            
            //设置加急
            Order order = baseDaoSupport.get(Order.class, schedule.getOrderId());
            if(null != order.getIfUrgent())
            {
                if(1 == order.getIfUrgent())
                {
                    activeTestingTask.setIfUrgent(order.getIfUrgent());
                    activeTestingTask.setUrgentName(order.getUrgentName());
                    activeTestingTask.setUrgentUpdateTime(order.getUrgentUpdateTime());
                    baseDaoSupport.update(activeTestingTask);
                }
            }
            
            //更新冗余信息
            testingTaskService.updateTaskRedundantColumn(Arrays.asList(activeTestingTask), 0);
            
        }
    }
    
    private void doUpdateSecondPcrSheet(SecondPcrSangerSubmitContext context)
    {
        TestingSheet sheet = context.getSheetEntity();
        UserMinimalModel submiter = context.getSubmiter();
        sheet.setSubmiterId(submiter.getId());
        sheet.setSubmiterName(submiter.getName());
        sheet.setSubmitTime(new Date());
        baseDaoSupport.update(sheet);
    }
    
    private void doCompleteProcess(SecondPcrSangerSubmitContext context)
    {
        TestingSheet sheet = context.getSheetEntity();
        activitiService.submitTestingSheet(sheet.getId());
    }
    
    private List<SecondPcrSangerTask> wrapForTesting(List<TestingSheetTask> records)
    {
        if (CollectionUtils.isEmpty(records))
        {
            return Collections.emptyList();
        }
        
        TestingTask testingTask;
        List<SecondPcrSangerTask> tasks = new ArrayList<SecondPcrSangerTask>();
        
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
    
    private SecondPcrSangerTask wrap(TestingTask entity)
    {
        SecondPcrSangerTask task = new SecondPcrSangerTask();
        task.setId(entity.getId());
        task.setStartTime(entity.getStartTime());
        task.setResubmit(entity.isResubmit());
        task.setResubmitCount(entity.getResubmitCount());
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
        
        List<TestingScheduleActive> activeList = testingScheduleService.getScheduleActivesByTaskId(entity.getId());
        if (Collections3.isNotEmpty(activeList))
        {
            task.setRunningStatus(activeList.get(0).getRunningStatus());
        }
        
        SecondPcrSangerTaskVariables variables = JsonUtils.asObject(entity.getTestingInputArgs(), SecondPcrSangerTaskVariables.class);

        String fpTaskCode = "";
        String fpTestCode = "";
        String spTestCode = "";
        String forwardPrimer = "";
        String primerLocationTemp = "";
        Integer forwardFlag = 1;
        Primer primer = null;

        task.setSampleName(entity.getReceivedSampleName());
        task.setSampleCode(entity.getReceivedSampleCode());
        
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
            task.setCombineCode(variables.getCombineCode());
            task.setPrimerId(variables.getPrimerId());
            if (StringUtils.isNotEmpty(variables.getPrimerId()))
            {
                primer = baseDaoSupport.get(Primer.class, variables.getPrimerId());
            }
        }
        
        if (null != primer)
        {
            if (1 == forwardFlag.intValue())//1次PCR直接过来的二次PCR没有primer临时位置
            {
                forwardPrimer = primer.getForwardPrimerName();
            }
            else
            {
                forwardPrimer = primer.getReversePrimerName();
            }
            task.setForwardPrimerCode(forwardPrimer);
            task.setReversePrimerCode(primer.getReversePrimerName());
        }
        
        task.setSixNineInfo(getSixNineInfo(spTestCode, fpTaskCode, fpTestCode, forwardPrimer));
        task.setNgsSeqCode(task.getCombineCode() + "_" + forwardPrimer);
        //应完成时间
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
    
    private void setPlannedFinishDate(TestingTask entity,SecondPcrSangerTask task)
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
