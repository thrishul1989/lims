package com.todaysoft.lims.testing.firstpcr.service.impl;

import java.math.BigDecimal;
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
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
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
import com.todaysoft.lims.testing.base.entity.ReceivedSample;
import com.todaysoft.lims.testing.base.entity.SangerVerifyRecord;
import com.todaysoft.lims.testing.base.entity.TestingSample;
import com.todaysoft.lims.testing.base.entity.TestingSampleStorage;
import com.todaysoft.lims.testing.base.entity.TestingSchedule;
import com.todaysoft.lims.testing.base.entity.TestingScheduleActive;
import com.todaysoft.lims.testing.base.entity.TestingScheduleHistory;
import com.todaysoft.lims.testing.base.entity.TestingSheet;
import com.todaysoft.lims.testing.base.entity.TestingSheetTask;
import com.todaysoft.lims.testing.base.entity.TestingTask;
import com.todaysoft.lims.testing.base.entity.TestingTaskResult;
import com.todaysoft.lims.testing.base.entity.TestingTaskRunVariable;
import com.todaysoft.lims.testing.base.entity.TestingTechnicalAnalyRecord;
import com.todaysoft.lims.testing.base.entity.TestingVerifyRecord;
import com.todaysoft.lims.testing.base.model.BatchWrapTestingTaskContext;
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
import com.todaysoft.lims.testing.dnaqc.model.DNAAttributes;
import com.todaysoft.lims.testing.firstpcr.context.FirstPcrSubmitContext;
import com.todaysoft.lims.testing.firstpcr.context.FirstPcrSubmitNextTaskContext;
import com.todaysoft.lims.testing.firstpcr.context.FirstPcrSubmitScheduleContext;
import com.todaysoft.lims.testing.firstpcr.context.FirstPcrSubmitTaskContext;
import com.todaysoft.lims.testing.firstpcr.dao.FirstPcrAssignableTaskSearcher;
import com.todaysoft.lims.testing.firstpcr.model.FirstPcrAssignArgs;
import com.todaysoft.lims.testing.firstpcr.model.FirstPcrAssignModel;
import com.todaysoft.lims.testing.firstpcr.model.FirstPcrAssignRequest;
import com.todaysoft.lims.testing.firstpcr.model.FirstPcrAssignTaskArgs;
import com.todaysoft.lims.testing.firstpcr.model.FirstPcrSheetModel;
import com.todaysoft.lims.testing.firstpcr.model.FirstPcrSheetVariables;
import com.todaysoft.lims.testing.firstpcr.model.FirstPcrSubmitRequest;
import com.todaysoft.lims.testing.firstpcr.model.FirstPcrTask;
import com.todaysoft.lims.testing.firstpcr.model.FirstPcrTaskVariables;
import com.todaysoft.lims.testing.firstpcr.service.IFirstPcrService;
import com.todaysoft.lims.testing.primerdesign.model.PrimerDesignTaskVariables;
import com.todaysoft.lims.testing.samplesotck.service.ITestingSheetSampleStorageService;
import com.todaysoft.lims.testing.secondpcr.model.SecondPcrSheetVariables;
import com.todaysoft.lims.testing.secondpcr.model.SecondPcrTaskVariables;
import com.todaysoft.lims.testing.secondpcr.service.ISecondPcrService;
import com.todaysoft.lims.testing.technicalanaly.service.ITechnicalAnalyService;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.DoubleCalculateUtils;
import com.todaysoft.lims.utils.StringUtils;

@Service
public class FirstPcrService implements IFirstPcrService
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
    private ISecondPcrService secondPcrService;
    
    @Autowired
    private ITestingSheetSampleStorageService testingSheetSampleStorageService;
    
    @Autowired
    private ITechnicalAnalyService technicalAnalyService;
    
    private Logger logger = org.apache.log4j.Logger.getLogger(FirstPcrService.class);
    
    @Override
    @Transactional
    public List<FirstPcrTask> getAssignableList(FirstPcrAssignableTaskSearcher searcher)
    {
        List<FirstPcrTask> tasks = new ArrayList<FirstPcrTask>();
        try
        {
            List<TestingTask> records = baseDaoSupport.find(searcher);
            
            if (CollectionUtils.isEmpty(records))
            {
                return Collections.emptyList();
            }
            
            Set<String> keys = new HashSet<String>();
            
            for (TestingTask record : records)
            {
                keys.add(record.getId());
            }
            
            for (TestingTask record : records)
            {
                tasks.add(wrap(record, null));
            }
            List<FirstPcrTask> primerCodeNullList = tasks.stream().filter(x -> StringUtils.isEmpty(x.getForwardPrimerCode())).collect(Collectors.toList());
            if (Collections3.isNotEmpty(primerCodeNullList))
            {
                tasks.removeAll(primerCodeNullList);
            }
            
            if (Collections3.isNotEmpty(primerCodeNullList))
            {
                tasks.addAll(primerCodeNullList);
            }
            
            tasks.sort(Comparator.comparing(FirstPcrTask::getIfUrgent)//是否加急
                .thenComparing(FirstPcrTask::getResubmitCount)
                //重做次数
                .reversed()
                .thenComparing(FirstPcrTask::getOrderId)
                .thenComparing(FirstPcrTask::getChromLocation)
                //位点
                .thenComparing(FirstPcrTask::getSampleName)//收样样本-样本名称
            );
            
            for (FirstPcrTask task : tasks)
            {
                if (task.isVerify())
                {
                    task.setVerifyScheme("");
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
        return tasks;
    }
    
    @Override
    public FirstPcrAssignModel getAssignableModel(FirstPcrAssignArgs args)
    {
        FirstPcrAssignModel model = new FirstPcrAssignModel();
        
        if (null == args || StringUtils.isEmpty(args.getKeys()))
        {
            model.setTasks(Collections.emptyList());
            return model;
        }
        
        Set<String> includeKeys = new HashSet<String>(Arrays.asList(args.getKeys().split(",")));
        FirstPcrAssignableTaskSearcher searcher = new FirstPcrAssignableTaskSearcher();
        searcher.setIncludeKeys(includeKeys);
        List<FirstPcrTask> tasks = getAssignableList(searcher);
        
        //位置排序
        sortLocationMethod(tasks);
        
        //下达任务不足96个时，随机空出一个位置作为质控点
        if (Collections3.isNotEmpty(includeKeys) && includeKeys.size() < 96)
        {
            Random random = new Random();
            int index = random.nextInt(includeKeys.size() + 1);
            FirstPcrTask emptyTask = new FirstPcrTask();
            tasks.add(index, emptyTask);
        }
        
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
    
    private void sortLocationMethod(List<FirstPcrTask> list)
    {
        Collections.sort(list, new Comparator<FirstPcrTask>()
        {
            
            @Override
            public int compare(FirstPcrTask o1, FirstPcrTask o2)
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
    public void assign(FirstPcrAssignRequest request, String token)
    {
        if (!CollectionUtils.isEmpty(request.getTasks()))
        {
            TestingTask testingTask;
            FirstPcrTaskVariables firstPcrTaskVariables;
            
            for (FirstPcrAssignTaskArgs task : request.getTasks())
            {
                if (StringUtils.isNotEmpty(task.getId()))
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
        }
        
        // 创建任务单
        TestingSheetCreateModel model = buildTestingSheetCreateModel(request, token);
        TestingSheet sheet = testingSheetService.create(model);
        
        //创建出库单
        testingSheetSampleStorageService.createStorageOut(sheet);
        
        // 启动流程
        activitiService.releaseTestingSheet(sheet);
    }
    
    public FirstPcrTaskVariables getTaskRunningVariables(String taskId)
    {
        String variables = testingTaskService.getVariables(taskId);
        
        if (StringUtils.isEmpty(variables))
        {
            return new FirstPcrTaskVariables();
        }
        
        return JsonUtils.asObject(variables, FirstPcrTaskVariables.class);
    }
    
    private TestingSheetCreateModel buildTestingSheetCreateModel(FirstPcrAssignRequest request, String token)
    {
        UserMinimalModel loginer = smmadapter.getLoginUser(token);
        UserMinimalModel tester = smmadapter.getUserByID(request.getTesterId());
        TaskConfig taskConfig = bcmadapter.getTaskConfigBySemantic(TaskSemantic.PCR_ONE);
        
        TestingSheetCreateModel model = new TestingSheetCreateModel();
        model.setCode(commonService.getTaskCodeBySemantic(TaskSemantic.PCR_ONE));
        model.setTaskSemantic(TaskSemantic.PCR_ONE);
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
            model.setTasks(request.getTasks().stream().filter(o -> StringUtils.isNotEmpty(o.getId())).map(o -> o.getId()).collect(Collectors.toList()));
        }
        
        // 设置一次PCR任务单自定义参数对象
        FirstPcrSheetVariables variables = new FirstPcrSheetVariables();
        variables.setReagentKitId(request.getReagentKitId());
        variables.setSecondPcrReagentKitId(request.getSecondPcrReagentKitId());
        variables.setSecondPcrTesterId(request.getSecondPcrTesterId());
        model.setVariables(variables);
        return model;
    }
    
    @Override
    @Transactional
    public FirstPcrSheetModel getTestingSheet(String id)
    {
        Map<String, String> sampleLocationTempMap = Maps.newHashMap();
        Map<String, String> primerLocationTempMap = Maps.newHashMap();
        TestingSheet entity = testingSheetService.getSheet(id);
        
        if (null == entity)
        {
            return null;
        }
        
        FirstPcrSheetModel sheet = new FirstPcrSheetModel();
        sheet.setId(entity.getId());
        sheet.setCode(entity.getCode());
        sheet.setAssignerName(entity.getAssignerName());
        sheet.setAssignTime(entity.getAssignTime());
        sheet.setDescription(entity.getDescription());
        
        FirstPcrSheetVariables variables = JsonUtils.asObject(entity.getVariables(), FirstPcrSheetVariables.class);
        
        if (null != variables)
        {
            String reagentKitId = variables.getReagentKitId();
            ReagentKitSimpleModel reagentKit = bsmadapter.getReagentKit(reagentKitId);
            
            if (null != reagentKit)
            {
                sheet.setReagentKitName(reagentKit.getName());
            }
            
        }
        
        List<FirstPcrTask> tasks = wrapForTesting(entity.getTestingSheetTaskList());
        FirstPcrTask emptyTask = new FirstPcrTask();
        List<String> codeList = getTestCodeList();
        List<String> testCodeList = Lists.newArrayList();
        for (FirstPcrTask task : tasks)
        {
            testCodeList.add(task.getPcrTestCode());
        }
        for (String code : codeList)
        {
            if (!testCodeList.contains(code))
            {
                emptyTask.setPcrTestCode(code);
                tasks.add(emptyTask);
                break;
            }
        }
        
        Collections.sort(tasks, new Comparator<FirstPcrTask>()
        {
            @Override
            public int compare(FirstPcrTask o1, FirstPcrTask o2)
            {
                return new TestingCodeComparator().compare(o1.getPcrTestCode(), o2.getPcrTestCode());
            }
        });
        
        //根据样本编号生成样本临时位置
        List<String> sampleCodeList = tasks.stream().map(FirstPcrTask::getSampleCode).distinct().collect(Collectors.toList());
        
        List<String> primerCodeList = tasks.stream().map(FirstPcrTask::getForwardPrimerCode).distinct().collect(Collectors.toList());
        
        for (int i = 0; i < sampleCodeList.size(); i++)
        {
            sampleLocationTempMap.put(sampleCodeList.get(i), commonService.getFirstPcrSampleTempLocation(i + 1));
        }
        
        for (int i = 0; i < primerCodeList.size(); i++)
        {
            primerLocationTempMap.put(primerCodeList.get(i), commonService.getPrimerTempLocation(i + 1));
        }
        
        for (FirstPcrTask firstPcrTask : tasks)
        {
            firstPcrTask.setSampleLocationTemp(sampleLocationTempMap.get(firstPcrTask.getSampleCode()));
            firstPcrTask.setForwardPrimerLocationTemp(primerLocationTempMap.get(firstPcrTask.getForwardPrimerCode()));
            firstPcrTask.setReversePrimerLocationTemp(firstPcrTask.getForwardPrimerLocationTemp().replace("F", "R"));
            logger.info(firstPcrTask.getId() + "~~样本存储位置~~" + firstPcrTask.getDnaLocation());
        }
        sortLocationMethod(tasks);//位置排序
        sheet.setTasks(tasks);
        return sheet;
    }
    
    @Override
    @Transactional
    public void submit(FirstPcrSubmitRequest request, String token)
    {
        // 1、设置提交上下文数据
        FirstPcrSubmitContext context = generateSubmitContext(request, token);
        
        // 2、更新任务结果
        doUpdateTasks(context);
        
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
    }
    
    private FirstPcrSubmitContext generateSubmitContext(FirstPcrSubmitRequest request, String token)
    {
        TestingSheet sheet = testingSheetService.getSheet(request.getId());
        
        if (null == sheet)
        {
            throw new IllegalStateException();
        }
        
        FirstPcrSubmitContext context = new FirstPcrSubmitContext();
        context.setContextForSubmiter(smmadapter.getLoginUser(token));
        context.setContextForTestingSheet(sheet);
        context.setContextForSubmitRequest(request.getTasks());
        
        List<TestingSheetTask> tasks = sheet.getTestingSheetTaskList();
        
        for (TestingSheetTask task : tasks)
        {
            setContextForTestingSheetTask(task, context);
        }
        
        context.setQcPointTestCode(request.getQcPointTestCode());
        context.setQcPointPrimerLocation(request.getQcPointPrimerLocation());
        
        return context;
    }
    
    private void setContextForTestingSheetTask(TestingSheetTask task, FirstPcrSubmitContext context)
    {
        String id = task.getTestingTaskId();
        TestingTask testingTask = testingTaskService.get(id);
        context.setContextForTestingTask(testingTask);
        
        TaskConfig scheduleNextNodeConfig;
        TestingScheduleActive scheduleActive;
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
    
    private void doUpdateTasks(FirstPcrSubmitContext context)
    {
        Set<FirstPcrSubmitTaskContext> records = context.getRelatedTasks();
        
        TestingTask task;
        Date timestamp = new Date();
        TestingTaskResult result;
        String testingTaskResult = "";
        
        for (FirstPcrSubmitTaskContext record : records)
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
            
            //先查询防止已经存在
            result = baseDaoSupport.get(TestingTaskResult.class, task.getId());
            if (null == result)
            {
                result = new TestingTaskResult();
                result.setTaskId(task.getId());
                result.setResult(testingTaskResult);
                result.setRemark(record.getRemark());
                result.setDetails(JsonUtils.asJson(record.getDispose()));
                baseDaoSupport.insert(result);
            }
            else
            {
                result.setResult(testingTaskResult);
                result.setRemark(record.getRemark());
                result.setDetails(JsonUtils.asJson(record.getDispose()));
                baseDaoSupport.update(result);
            }
        }
    }
    
    private void doCreateNextNodeTasks(FirstPcrSubmitContext context)
    {
        Set<FirstPcrSubmitNextTaskContext> nextTasks = context.getNextNodeTasks();
        
        TestingTask task;
        Date timestamp = new Date();
        TestingTaskRunVariable variables;
        
        for (FirstPcrSubmitNextTaskContext nextTask : nextTasks)
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
            baseDaoSupport.insert(variables);
            
            context.setContextForCreateNextNodeTask(nextTask.getTemporaryId(), task, variables);
        }
    }
    
    private void doUpdateScheduleNextActives(FirstPcrSubmitContext context)
    {
        Set<FirstPcrSubmitScheduleContext> schedules = context.getGotoNextSchedules();
        
        String nextTaskId;
        TestingSchedule schedule;
        TestingScheduleActive active;
        TestingScheduleHistory history;
        TestingTask activeTestingTask;
        Date timestamp = new Date();
        
        for (FirstPcrSubmitScheduleContext scheduleContext : schedules)
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
    }
    
    private void doUpdateScheduleErrorActives(FirstPcrSubmitContext context)
    {
        Set<FirstPcrSubmitScheduleContext> schedules = context.getGotoErrorSchedules();
        
        TestingSchedule thisSchedule;
        TestingScheduleActive thisActive;
        TestingTask thisTask;
        String dispose;
        TestingTask nextTask;
        for (FirstPcrSubmitScheduleContext scheduleContext : schedules)
        {
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
                
                thisSchedule.setActiveTask(thisTask.getName());
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
            else if ("重新设计引物".equals(dispose))
            {
                TaskConfig primerConfig = bcmadapter.getTaskConfigBySemantic(TaskSemantic.PRIMER_DESIGN);
                
                //正常情况上一次的引物设计不可能为空（如果技术分析提交的验证不需要做引物设计，此时为空-就会招不到这个lastTask，因为没存历史表）
                TestingTask lastTask = testingScheduleService.getScheduleNodeLastTestingTask(thisSchedule.getId(), TaskSemantic.PRIMER_DESIGN);
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
            else
            {//实验取消
                thisSchedule.setActiveTask(thisSchedule.getActiveTask() + "-异常");
                baseDaoSupport.update(thisSchedule);
                
                baseDaoSupport.delete(thisActive);
            }
        }
    }
    
    private void doUpdateFirstPcrSheet(FirstPcrSubmitContext context)
    {
        TestingSheet sheet = context.getSheetEntity();
        UserMinimalModel submiter = context.getSubmiter();
        sheet.setSubmiterId(submiter.getId());
        sheet.setSubmiterName(submiter.getName());
        sheet.setSubmitTime(new Date());
        baseDaoSupport.update(sheet);
    }
    
    private void doCompleteProcess(FirstPcrSubmitContext context)
    {
        TestingSheet sheet = context.getSheetEntity();
        activitiService.submitTestingSheet(sheet.getId());
    }
    
    private void doCreateSecondPcrControlSheet(FirstPcrSubmitContext context)
    {
        Set<FirstPcrSubmitNextTaskContext> nextNodeTasks = context.getNextNodeTasks();
        if (Collections3.isEmpty(nextNodeTasks))
        {
            return;
        }
        String semantic = TaskSemantic.PCR_TWO;
        TaskConfig config = bcmadapter.getTaskConfigBySemantic(semantic);
        TestingSheet fpSheet = context.getSheetEntity();
        FirstPcrSheetVariables variables = context.getFirstPcrSheetVariables();
        UserMinimalModel tester = smmadapter.getUserByID(variables.getSecondPcrTesterId());
        
        SecondPcrSheetVariables spVariables = new SecondPcrSheetVariables();
        spVariables.setReagentKitId(variables.getSecondPcrReagentKitId());
        spVariables.setQcPointTestCode(context.getQcPointTestCode());
        spVariables.setQcPointPrimerLocation(context.getQcPointPrimerLocation());
        
        List<String> sheetTasks = new ArrayList<String>();
        
        for (FirstPcrSubmitNextTaskContext nextNodeTask : nextNodeTasks)
        {
            if (semantic.equals(nextNodeTask.getTaskSemantic()))
            {
                sheetTasks.add(nextNodeTask.getTestingTaskEntity().getId());
            }
        }
        
        Date timestamp = new Date();
        TestingSheetCreateModel model = new TestingSheetCreateModel();
        model.setTaskSemantic(TaskSemantic.PCR_TWO);
        model.setTaskName(config.getName());
        String secondPcrSheetCode = "T" + fpSheet.getCode().substring(1);
        model.setCode(secondPcrSheetCode);
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
        SecondPcrTaskVariables spTaskVariables;
        FirstPcrSubmitNextTaskContext nextTaskContext;
        
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
            spTaskVariables.setFlag(1);
            spTaskVariables.setForwardFlag(1);
            task.setTestingInputArgs(JsonUtils.asJson(spTaskVariables));
            testingTaskService.modify(task);
            
            spTaskVariables = secondPcrService.getTaskRunningVariables(task.getId());
            spTaskVariables.setFpcrTaskCode(nextTaskContext.getPcrTaskCode());
            spTaskVariables.setFpcrTestCode(nextTaskContext.getPcrTestCode());
            spTaskVariables.setSpcrTestCode(nextTaskContext.getPcrTestCode());
            spTaskVariables.setPrimerLocationTemp(nextTaskContext.getForwardPrimerLocationTemp());
            spTaskVariables.setFlag(1);
            spTaskVariables.setForwardFlag(1);
            spVariablesEntity.setText(JsonUtils.asJson(spTaskVariables));
            baseDaoSupport.update(spVariablesEntity);
        }
        
        activitiService.releaseTestingSheet(sheet);
    }
    
    private List<FirstPcrTask> wrapForTesting(List<TestingSheetTask> records)
    {
        if (CollectionUtils.isEmpty(records))
        {
            return Collections.emptyList();
        }
        
        Set<String> keys = new HashSet<String>();
        
        for (TestingSheetTask record : records)
        {
            keys.add(record.getTestingTaskId());
        }
        
        //        BatchWrapTestingTaskContext context =
        //            testingTaskService.getBatchWrapTestingTaskContext(keys, new String[] {"PRODUCT", "METHOD"}, new String[] {"SANGER_VERIFY_RECORD"});
        
        TestingTask testingTask;
        List<FirstPcrTask> tasks = new ArrayList<FirstPcrTask>();
        
        for (TestingSheetTask record : records)
        {
            if (StringUtils.isNotEmpty(record.getTestingTaskId()))
            {
                testingTask = testingTaskService.get(record.getTestingTaskId());
                
                if (null == testingTask)
                {
                    throw new IllegalStateException();
                }
                
                tasks.add(wrap(testingTask, null));
            }
        }
        
        return tasks;
    }
    
    private FirstPcrTask wrap(TestingTask entity, BatchWrapTestingTaskContext context)
    {
        String id = entity.getId();
        
        FirstPcrTask task = new FirstPcrTask();
        
        // 基础信息
        task.setId(id);
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
        
        List<TestingSchedule> schedules = testingScheduleService.getRelatedSchedules(entity.getId());
        if (Collections3.isEmpty(schedules))
        {
            schedules = testingScheduleService.getRelatedSchedulesByTestingTask(entity.getId());
        }
        if (Collections3.isNotEmpty(schedules))
        {
            task.setOrderId(schedules.get(0).getOrderId());
        }
        // 验证相关
        
        boolean isVerify = false;
        
        // 收样样本
        task.setSampleName(entity.getReceivedSampleName());
        task.setSampleCode(entity.getReceivedSampleCode());
        if ("1".equals(entity.getReceivedSamplePurpose()))
        {
            isVerify = true;
        }
        task.setVerify(isVerify);
        task.setForwardPrimerCode(entity.getTestingPrimerName());
        task.setChromLocation(entity.getVerifyChromosomePosition());
        task.setVerifyScheme(entity.getTestingVerifyScheme());
        
        // 实验样本
        DNAAttributes attributesOfDNA = JsonUtils.asObject(entity.getTestingSampleAttributes(), DNAAttributes.class);
        if (null != attributesOfDNA)
        {
            task.setConcentration(attributesOfDNA.getConcn());
        }
        
        // 任务参数
        FirstPcrTaskVariables variables = JsonUtils.asObject(entity.getTestingInputArgs(), FirstPcrTaskVariables.class);
        
        if (null != variables)
        {
            task.setPcrTestCode(variables.getPcrTestCode());
            task.setRemark(variables.getRemark());
        }
        
        if (null != attributesOfDNA && null != variables)
        {
            BigDecimal sampleInputQuantity = variables.getSampleInputQuantity();
            BigDecimal concn = attributesOfDNA.getConcn();
            
            if (null != sampleInputQuantity && null != concn)
            {
                double volume = DoubleCalculateUtils.div(sampleInputQuantity.doubleValue(), attributesOfDNA.getConcn().doubleValue(), 2);
                task.setVolume(getInputVolume(volume));
            }
        }
        
        task.setCombineCode(entity.getTestingCombineCode());
        
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
    
    private void setPlannedFinishDate(TestingTask entity, FirstPcrTask task)
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
        BigDecimal pcrMax = bcmadapter.getFirstPcrMaxInputVolume();
        double min = 1;
        if (DoubleCalculateUtils.compare(d, min) <= 0)
        {
            return new BigDecimal(min);
        }
        else if (DoubleCalculateUtils.compare(d, pcrMax.doubleValue()) <= 0)
        {
            return new BigDecimal(d);
        }
        else
        {
            return pcrMax;
        }
    }
    
    public String getVerifySchemeByTechnicalAnalyRecordId(String id)
    {
        String hql = "FROM TestingVerifyRecord t where t.analyRecord.id=:id order by t.inputSampleFamilyRelation desc";
        List<TestingVerifyRecord> list = baseDaoSupport.findByNamedParam(TestingVerifyRecord.class, hql, new String[] {"id"}, new Object[] {id});
        String result = Collections3.extractToString(list, "inputSampleFamilyRelation", ",");
        return result;
    }
    
    public boolean checkMajorOrTestSample(String testSampleId)
    {
        TestingSample testingSample = baseDaoSupport.get(TestingSample.class, testSampleId);
        ReceivedSample receivedSample = testingSample.getReceivedSample();
        if (null != receivedSample && "1".equals(receivedSample.getPurpose()))//1.用途是验证的
        {
            return false;
        }
        return true;
    }
    
    private List<String> getTestCodeList()
    {
        List<String> codeList = Lists.newArrayList();
        String code = "";
        for (int i = 0; i < 96; i++)
        {
            code = commonService.getDNAExtractCode(i + 1);
            codeList.add(code);
        }
        return codeList;
    }
}
