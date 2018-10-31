package com.todaysoft.lims.testing.dnaqc.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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

import com.google.common.collect.Lists;
import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.testing.activiti.service.IActivitiService;
import com.todaysoft.lims.testing.base.entity.Order;
import com.todaysoft.lims.testing.base.entity.OrderPlanTask;
import com.todaysoft.lims.testing.base.entity.Primer;
import com.todaysoft.lims.testing.base.entity.Product;
import com.todaysoft.lims.testing.base.entity.SangerVerifyRecord;
import com.todaysoft.lims.testing.base.entity.TestingMethod;
import com.todaysoft.lims.testing.base.entity.TestingSample;
import com.todaysoft.lims.testing.base.entity.TestingSampleStorage;
import com.todaysoft.lims.testing.base.entity.TestingSangerCount;
import com.todaysoft.lims.testing.base.entity.TestingSchedule;
import com.todaysoft.lims.testing.base.entity.TestingScheduleActive;
import com.todaysoft.lims.testing.base.entity.TestingScheduleHistory;
import com.todaysoft.lims.testing.base.entity.TestingSheet;
import com.todaysoft.lims.testing.base.entity.TestingSheetTask;
import com.todaysoft.lims.testing.base.entity.TestingTask;
import com.todaysoft.lims.testing.base.entity.TestingTaskRunVariable;
import com.todaysoft.lims.testing.base.model.BatchWrapTestingTaskContext;
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
import com.todaysoft.lims.testing.dnaext.model.DNAExtractTaskVariables;
import com.todaysoft.lims.testing.dnaext.service.IDNAExtractService;
import com.todaysoft.lims.testing.dnaqc.context.DNAQcSubmitContext;
import com.todaysoft.lims.testing.dnaqc.context.DNAQcSubmitNextTaskContext;
import com.todaysoft.lims.testing.dnaqc.context.DNAQcSubmitScheduleContext;
import com.todaysoft.lims.testing.dnaqc.context.DNAQcSubmitSolveTaskContext;
import com.todaysoft.lims.testing.dnaqc.context.DNAQcSubmitTaskContext;
import com.todaysoft.lims.testing.dnaqc.dao.DNAQcTaskSearcher;
import com.todaysoft.lims.testing.dnaqc.model.DNAAttributes;
import com.todaysoft.lims.testing.dnaqc.model.DNAQcAssignArgs;
import com.todaysoft.lims.testing.dnaqc.model.DNAQcAssignModel;
import com.todaysoft.lims.testing.dnaqc.model.DNAQcAssignSheet;
import com.todaysoft.lims.testing.dnaqc.model.DNAQcAssignSheetTask;
import com.todaysoft.lims.testing.dnaqc.model.DNAQcSheet;
import com.todaysoft.lims.testing.dnaqc.model.DNAQcSheetVariables;
import com.todaysoft.lims.testing.dnaqc.model.DNAQcSubmitSheetModel;
import com.todaysoft.lims.testing.dnaqc.model.DNAQcTask;
import com.todaysoft.lims.testing.dnaqc.model.DNAQcTaskVariables;
import com.todaysoft.lims.testing.dnaqc.service.IDNAQcService;
import com.todaysoft.lims.testing.firstpcrsanger.model.FirstPcrSangerTaskVariables;
import com.todaysoft.lims.testing.samplesotck.service.ITestingSheetSampleStorageService;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.StringUtils;

@Service
public class DNAQcService implements IDNAQcService
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
    
    @Autowired
    private IDNAExtractService dnaExtractService;
    
    @Override
    public List<DNAQcTask> todo(DNAQcTaskSearcher searcher)
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
        
        //        BatchWrapTestingTaskContext context = testingTaskService.getBatchWrapTestingTaskContext(keys);
        
        List<DNAQcTask> tasks = new ArrayList<DNAQcTask>();
        
        for (TestingTask record : records)
        {
            tasks.add(wrap(record, null));
        }
        tasks.sort(Comparator.comparing(DNAQcTask::getResubmitCount).reversed().thenComparing(DNAQcTask::getPlannedFinishDate));
        tasks.sort(Comparator.comparing(DNAQcTask::getIfUrgent).reversed());
        return tasks;
    }
    
    @Override
    public DNAQcAssignModel dnaQcAssignList(DNAQcAssignArgs args)
    {
        DNAQcAssignModel model = new DNAQcAssignModel();
        
        if (null == args || StringUtils.isEmpty(args.getKeys()))
        {
            model.setTasks(Collections.emptyList());
            return model;
        }
        
        Set<String> includeKeys = new HashSet<String>(Arrays.asList(args.getKeys().split(",")));
        DNAQcTaskSearcher searcher = new DNAQcTaskSearcher();
        searcher.setIncludeKeys(includeKeys);
        List<DNAQcTask> tasks = this.todo(searcher);
        
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
    
    private void sortLocationMethod(List<DNAQcTask> list)
    {
        Collections.sort(list, new Comparator<DNAQcTask>()
        {
            
            @Override
            public int compare(DNAQcTask o1, DNAQcTask o2)
            {
                return o1.getLocation().compareTo(o2.getLocation());
            }
        });
    }
    
    @Override
    public TestingSheetCreateModel buildTestingSheetCreateModel(DNAQcAssignSheet request, String token)
    {
        UserMinimalModel loginer = smmadapter.getLoginUser(token);
        UserMinimalModel tester = smmadapter.getUserByID(request.getQualityControlTesterId());
        TaskConfig taskConfig = bcmadapter.getTaskConfigBySemantic(TaskSemantic.DNA_QC);
        
        TestingSheetCreateModel model = new TestingSheetCreateModel();
        model.setCode(commonService.getTaskCodeBySemantic(TaskSemantic.DNA_QC) + "-Q");
        model.setTaskSemantic(TaskSemantic.DNA_QC);
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
            
            for (DNAQcAssignSheetTask task : request.getTasks())
            {
                keys.add(task.getId());
            }
            
            model.setTasks(keys);
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
    public void assign(DNAQcAssignSheet request, String token)
    {
        // 更新DNA质检任务运行时参数
        if (!CollectionUtils.isEmpty(request.getTasks()))
        {
            TestingTask testingTask;
            DNAQcTaskVariables variables;
            
            for (DNAQcAssignSheetTask task : request.getTasks())
            {
                testingTask = testingTaskService.get(task.getId());
                testingTask.setStatus(TestingTask.STATUS_ASSIGNING);
                testingTaskService.modify(testingTask);
                
                variables = testingTaskService.obtainVariables(task.getId(), DNAQcTaskVariables.class);
                variables.setTestingCode(task.getTestingCode());
                testingTaskService.updateVariables(task.getId(), variables);
                
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
    
    @Override
    public DNAQcSheet getTestingSheet(String id)
    {
        TestingSheet entity = testingSheetService.getSheet(id);
        
        if (null == entity)
        {
            return null;
        }
        
        DNAQcSheet sheet = new DNAQcSheet();
        sheet.setId(entity.getId());
        sheet.setCode(entity.getCode());
        sheet.setAssignerName(entity.getAssignerName());
        sheet.setAssignTime(entity.getAssignTime());
        sheet.setDescription(entity.getDescription());
        
        DNAQcSheetVariables variables = JsonUtils.asObject(entity.getVariables(), DNAQcSheetVariables.class);
        
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
        
        List<DNAQcTask> tasks = wrap(entity.getTestingSheetTaskList());
        
        Collections.sort(tasks, new Comparator<DNAQcTask>()
        {
            @Override
            public int compare(DNAQcTask o1, DNAQcTask o2)
            {
                return new TestingCodeComparator().compare(o1.getTestingCode(), o2.getTestingCode());
            }
        });
        
        sheet.setTasks(tasks);
        return sheet;
    }
    
    @Override
    @Transactional
    public void submitSheet(DNAQcSubmitSheetModel request, String token)
    {
        // 1、设置提交上下文数据
        DNAQcSubmitContext context = generateSubmitContext(request, token);
        
        // 2、更新DNA质检任务结果
        doUpdateTasks(context);
        
        // 3、创建质检通过样本新节点任务
        doCreateNextNodeTasks(context);
        
        // 处理sanger验证流程 DNA质检通过但是引物设计没完成 直接完成该任务，并删掉scheduleActive 流程表里面的active_task值 更新验证表DNA值
        doUpdateWaitConcurrentNodeShedules(context);
        
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
        
        //更新下个节点任务冗余字段
        
    }
    
    private DNAQcSubmitContext generateSubmitContext(DNAQcSubmitSheetModel request, String token)
    {
        TestingSheet sheet = testingSheetService.getSheet(request.getId());
        
        if (null == sheet)
        {
            throw new IllegalStateException();
        }
        
        DNAQcSubmitContext context = new DNAQcSubmitContext();
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
    
    private void doUpdateTasks(DNAQcSubmitContext context)
    {
        Set<DNAQcSubmitTaskContext> records = context.getRelatedTasks();
        
        TestingSample testingSample;
        DNAAttributes attributes;
        TaskSubmitModel taskSubmitData;
        
        for (DNAQcSubmitTaskContext record : records)
        {
            taskSubmitData = new TaskSubmitModel();
            taskSubmitData.setEntity(record.getTestingTaskEntity());
            taskSubmitData.setSuccess(record.isQualified());
            taskSubmitData.setFailureRemark(record.getUnqualifiedRemark());
            taskSubmitData.setFailureStrategy(record.getUnqualifiedStrategy());
            testingTaskService.submit(taskSubmitData);
            
            attributes = new DNAAttributes();
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
    
    private void doCreateNextNodeTasks(DNAQcSubmitContext context)
    {
        Set<DNAQcSubmitNextTaskContext> nextTasks = context.getNextNodeTasks();
        
        TestingTask task;
        TestingTaskRunVariable variables;
        TestingSample inputSample;
        Primer primer;
        FirstPcrSangerTaskVariables variablesPcr;
        Date timestamp = new Date();
        for (DNAQcSubmitNextTaskContext nextTask : nextTasks)
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
            if (nextTask.getTaskSemantic().equals(TaskSemantic.SANGER_PCR_ONE))
            {
                primer = nextTask.getPrimerVar();
                variablesPcr = new FirstPcrSangerTaskVariables();
                if (null != primer)
                {
                    variablesPcr.setForwardPrimerName(primer.getForwardPrimerName());
                    variablesPcr.setPrimerId(primer.getId());
                }
                variables.setText(JsonUtils.asJson(variablesPcr));
            }
            
            if (nextTask.getTaskSemantic().equals(TaskSemantic.DT))
            {//动态突变特殊处理引物冗余存储
                List<Product> products = testingTaskService.getRelatedProducts(task.getId());
                
                List<String> primers = Lists.newArrayList();
                List<String> primerProductStr = Lists.newArrayList();
                
                if (!CollectionUtils.isEmpty(products))
                {
                    for (Product product : products)
                    {
                        //根据产品id 查询相关的引物
                        List<Primer> primerProductList = testingTaskService.getPrimerListByProductCode(product.getCode());
                        primerProductList.stream().forEach(x -> primerProductStr.add(x.getForwardPrimerName()));
                        primers.addAll(primerProductStr);
                        
                    }
                    List<String> result = new ArrayList<String>(new HashSet<String>(primerProductStr));
                    result.sort((h1, h2) -> h1.compareTo(h2));
                    if (Collections3.isNotEmpty(result))
                    {
                        task.setTestingPrimerName(Collections3.convertToString(result, ","));
                    }
                    
                }
                
            }
            baseDaoSupport.insert(variables);
            context.setContextForCreateNextNodeTask(nextTask.getTemporaryId(), task);
        }
        
    }
    
    private void doUpdateWaitConcurrentNodeShedules(DNAQcSubmitContext context)
    {
        Set<DNAQcSubmitScheduleContext> schedules = context.getWaitConcurrentNodeShedules();
        
        TestingSchedule schedule;
        
        for (DNAQcSubmitScheduleContext scheduleContext : schedules)
        {
            schedule = scheduleContext.getTestingScheduleEntity();
            testingScheduleService.waitConcurrentNodeForDNAQC(schedule, baseDaoSupport.get(TestingSample.class, scheduleContext.getNextNodeInputSampleId()));
        }
    }
    
    private void doUpdateScheduleNextActives(DNAQcSubmitContext context)
    {
        Set<DNAQcSubmitScheduleContext> schedules = context.getGotoNextSchedules();
        Set<DNAQcSubmitNextTaskContext> nextTasks = context.getNextNodeTasks();
        List<DNAQcSubmitNextTaskContext> nextTaskList = Lists.newArrayList();
        if (Collections3.isNotEmpty(nextTasks))
        {
            nextTaskList = new ArrayList<DNAQcSubmitNextTaskContext>(nextTasks);
        }
        
        TestingSchedule schedule;
        TestingTask nextTask;
        TestingSangerCount testingSangerCount;
        Date timestamp = new Date();
        if (Collections3.isNotEmpty(schedules)) //有下一步流程
        {
            for (DNAQcSubmitScheduleContext scheduleContext : schedules)
            {
                List<TestingTask> tasks = Lists.newArrayList();
                List<DNAQcSubmitNextTaskContext> taskFilter = Lists.newArrayList();
                schedule = scheduleContext.getTestingScheduleEntity();
                String nextTaskSematic = StringUtils.isNotEmpty(scheduleContext.getNextNodeConfig()) ? scheduleContext.getNextNodeConfig().getSemantic() : "";
                //处理PCR_NGS SANGER验证（不需要做引物设计，只需要做DNA提取的情况下，不会回填DNA样本到LIMS_TESTING_SANGER_VERIFY，如果一次PCR重做引物设计就会出问题了，没有下一个任务了）
                if (TaskSemantic.PCR_ONE.equals(nextTaskSematic) || TaskSemantic.PCR_NGS.equals(nextTaskSematic))
                {
                    TestingSample dnaSample = baseDaoSupport.get(TestingSample.class, scheduleContext.getNextNodeInputSampleId());
                    String verifyKey = schedule.getVerifyKey();
                    
                    if (StringUtils.isEmpty(verifyKey))
                    {
                        throw new IllegalStateException();
                    }
                    
                    SangerVerifyRecord sangerVerifyRecord = baseDaoSupport.get(SangerVerifyRecord.class, schedule.getVerifyKey());
                    sangerVerifyRecord.setDnaSample(dnaSample);
                    baseDaoSupport.update(sangerVerifyRecord);
                }
                if (TaskSemantic.SANGER_PCR_ONE.equals(nextTaskSematic))
                {
                    taskFilter =
                        nextTaskList.stream()
                            .filter(x -> x.getTemporaryId().contains(scheduleContext.getId() + "_" + scheduleContext.getNextNodeInputSampleId()))
                            .collect(Collectors.toList());
                    taskFilter.stream().forEach(x -> tasks.add(x.getTestingTaskEntity()));
                    testingSangerCount = testingScheduleService.getTestingSangerCountByScheduleId(schedule.getId());
                    if (null == testingSangerCount)
                    {
                        testingSangerCount = new TestingSangerCount();
                        testingSangerCount.setSchedule(schedule);
                        testingSangerCount.setTotalNum(tasks.size());
                        testingSangerCount.setCancerNum(0);
                        testingSangerCount.setCompleteNum(0);
                        baseDaoSupport.insert(testingSangerCount);
                    }
                    else
                    {
                        testingSangerCount.setSchedule(schedule);
                        testingSangerCount.setTotalNum(tasks.size());
                        testingSangerCount.setCancerNum(0);
                        testingSangerCount.setCompleteNum(0);
                        baseDaoSupport.update(testingSangerCount);
                    }
                    
                }
                else
                {
                    nextTask = context.getNextTask(scheduleContext.getId(), scheduleContext.getNextNodeInputSampleId());
                    if (nextTask != null)
                    {
                        tasks.add(nextTask);
                    }
                    
                }
                testingScheduleService.gotoNextNode(schedule, TaskSemantic.DNA_QC, tasks, timestamp);
                
                //更新冗余信息
                testingTaskService.updateTaskRedundantColumn(tasks, 0);
            }
        }
        else
        {
            System.out.println("aaaabbbb");
        }
    }
    
    private void doUpdateScheduleErrorActives(DNAQcSubmitContext context)
    {
        Set<DNAQcSubmitScheduleContext> schedules = context.getGotoErrorSchedules();
        
        TestingSchedule schedule;
        
        for (DNAQcSubmitScheduleContext scheduleContext : schedules)
        {
            schedule = scheduleContext.getTestingScheduleEntity();
            testingScheduleService.gotoError(schedule, TaskSemantic.DNA_QC);
        }
    }
    
    private void doSolveUnqualifiedSchedules(DNAQcSubmitContext context)
    {
        Set<DNAQcSubmitSolveTaskContext> solveTasks = context.getSolveUnqualifiedTasks();
        
        if (CollectionUtils.isEmpty(solveTasks))
        {
            return;
        }
        
        for (DNAQcSubmitSolveTaskContext solveTaskContext : solveTasks)
        {
            if (CollectionUtils.isEmpty(solveTaskContext.getRelatedSchedules()))
            {
                continue;
            }
            
            for (DNAQcSubmitScheduleContext scheduleContext : solveTaskContext.getRelatedSchedules().values())
            {
                doSolveUnqualifiedSchedule(scheduleContext, solveTaskContext);
            }
        }
    }
    
    private void doSolveUnqualifiedSchedule(DNAQcSubmitScheduleContext scheduleContext, DNAQcSubmitSolveTaskContext solveTaskContext)
    {
        TestingTask solvedTask = solveTaskContext.getSolvedTask();
        
        //查询之前的activetask，并删除
        TestingScheduleActive activeDelete =
            testingScheduleService.getScheduleActive(scheduleContext.getTestingScheduleEntity().getId(), solveTaskContext.getUnqualifiedTask().getId());
        
        if (null != activeDelete)
        {
            baseDaoSupport.delete(activeDelete);
        }
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
            DNAExtractTaskVariables dnaVariables = new DNAExtractTaskVariables();
            dnaVariables.setRemark(scheduleContext.getQcUnqualifiedRemark());
            variables.setText(JsonUtils.asJson(dnaVariables));
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
    
    private void doUpdateSheet(DNAQcSubmitContext context)
    {
        TestingSheet sheet = context.getSheetEntity();
        UserMinimalModel submiter = context.getSubmiter();
        sheet.setSubmiterId(submiter.getId());
        sheet.setSubmiterName(submiter.getName());
        sheet.setSubmitTime(new Date());
        baseDaoSupport.update(sheet);
    }
    
    private void doCompleteProcess(DNAQcSubmitContext context)
    {
        TestingSheet sheet = context.getSheetEntity();
        activitiService.submitTestingSheet(sheet.getId());
    }
    
    private void setContextForTestingSheetTask(TestingSheetTask task, DNAQcSubmitContext context)
    {
        String id = task.getTestingTaskId();
        TestingTask testingTask = testingTaskService.get(id);
        context.setContextForTestingTask(testingTask);
        
        TaskConfig scheduleNextNodeConfig;
        List<TestingSchedule> schedules = testingScheduleService.getRelatedSchedules(id);
        String scheduleConcurrentNode;
        TestingMethod testingMethodSangerTest = testingTaskService.getTestingMethodBySemantic(TestingMethod.SANGER_TEST);
        
        for (TestingSchedule schedule : schedules)
        {
            scheduleNextNodeConfig = testingScheduleService.getScheduleNextNodeConfig(schedule, testingTask.getSemantic());
            
            /*if (null == scheduleNextNodeConfig)
            {
                throw new IllegalStateException(); 有可能是附属样本提前做-暂时已完成
            }*/
            if (null == scheduleNextNodeConfig)
            {
                context.setContextForTestingTaskRelatedSchedule(testingTask, schedule, scheduleNextNodeConfig, null, "", null);
                continue;
            }
            
            scheduleConcurrentNode = getScheduleConcurrentNode(schedule);
            
            if (TaskSemantic.SANGER_PCR_ONE.equals(scheduleNextNodeConfig.getSemantic()))
            {
                Product product = baseDaoSupport.get(Product.class, schedule.getProductId());
                if (null == product)
                {
                    throw new IllegalStateException();
                }
                
                List<Primer> list = getPrimerListByProductCodeAndAppType(testingMethodSangerTest.getId(), product.getCode());
                
                for (Primer primer : list)
                {
                    context.setContextForTestingTaskRelatedSchedule(testingTask,
                        schedule,
                        scheduleNextNodeConfig,
                        scheduleConcurrentNode,
                        product.getId(),
                        primer);
                }
                
            }
            else
            {
                context.setContextForTestingTaskRelatedSchedule(testingTask, schedule, scheduleNextNodeConfig, scheduleConcurrentNode, "", null);
            }
        }
    }
    
    private String getScheduleConcurrentNode(TestingSchedule testingSchedule)
    {
        
        String verifyKey = testingSchedule.getVerifyKey();
        TestingMethod testingMethod = baseDaoSupport.get(TestingMethod.class, testingSchedule.getMethodId());
        if (null == testingMethod)
        {
            throw new IllegalStateException();
        }
        String methodSemantic = testingMethod.getSemantic();
        if (TestingMethod.SANGER.equals(methodSemantic))
        {
            SangerVerifyRecord sangerVerifyRecord = baseDaoSupport.get(SangerVerifyRecord.class, verifyKey);
            if (null != sangerVerifyRecord && null == sangerVerifyRecord.getPrimer())
            {
                return TaskSemantic.PRIMER_DESIGN;
            }
            
        }
        else if (TestingMethod.PCR_NGS.equals(methodSemantic))
        {
            SangerVerifyRecord sangerVerifyRecord = baseDaoSupport.get(SangerVerifyRecord.class, verifyKey);
            if (null != sangerVerifyRecord && null == sangerVerifyRecord.getPrimer())
            {
                return TaskSemantic.PCR_NGS_PRIMER_DESIGN;
            }
        }
        
        return null;
    }
    
    private List<DNAQcTask> wrap(List<TestingSheetTask> records)
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
        
        //        BatchWrapTestingTaskContext context = testingTaskService.getBatchWrapTestingTaskContext(keys);
        
        TestingTask testingTask;
        List<DNAQcTask> tasks = new ArrayList<DNAQcTask>();
        
        for (TestingSheetTask record : records)
        {
            testingTask = testingTaskService.get(record.getTestingTaskId());
            
            if (null == testingTask)
            {
                throw new IllegalStateException();
            }
            
            tasks.add(wrap(testingTask, null));
        }
        
        return tasks;
    }
    
    private DNAQcTask wrap(TestingTask entity, BatchWrapTestingTaskContext context)
    {
        String id = entity.getId();
        
        DNAQcTask task = new DNAQcTask();
        
        // 基本信息
        task.setId(id);
        task.setStartTime(entity.getStartTime());
        task.setResubmit(entity.isResubmit());
        task.setResubmitCount(entity.getResubmitCount());
        
        // 营销中心
        task.setOrderType(entity.getOrderMarketingCenter());
        
        // 实验样本
        task.setSampleCode(entity.getTestingSampleCode());
        
        task.setOriginalSampleCode(entity.getReceivedSampleCode());
        task.setOriginalSampleName(entity.getReceivedSampleName());
        task.setOriginalSampleTypeName(entity.getReceivedSampleType());
        task.setOriginalSamplingDate(entity.getReceivedSampleSamplingTime());
        task.setOriginalSamplePurpose(entity.getReceivedSamplePurpose());
        task.setOriginalSampleTypeId(entity.getReceivedSampleTypeId());
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
        
        //  参数相关
        DNAQcTaskVariables variables = JsonUtils.asObject(entity.getTestingInputArgs(), DNAQcTaskVariables.class);
        
        if (null != variables)
        {
            task.setLocation(variables.getLocation());
            task.setTestingCode(variables.getTestingCode());
            task.setRemark(variables.getRemark());
        }
        
        if ("1".equals(task.getOriginalSamplePurpose()))
        {
            // 验证流程
            task.setProducts(entity.getVerifyChromosomePosition());
        }
        else
        {
            // 检测流程-产品相关
            task.setProducts(entity.getProductName());
        }
        
        // 检测方法相关
        task.setTestingMethods(entity.getTestingMethodName());
        
        String hql = "FROM TestingSampleStorage tss WHERE tss.sampleCode = :sampleCode";
        NamedQueryer queryer =
            NamedQueryer.builder().hql(hql).names(Lists.newArrayList("sampleCode")).values(Lists.newArrayList(entity.getTestingSampleCode())).build();
        List<TestingSampleStorage> locations = baseDaoSupport.find(queryer, TestingSampleStorage.class);
        if (Collections3.isNotEmpty(locations))
        {
            TestingSampleStorage tss = Collections3.getFirst(locations);
            task.setLocation(tss.getLocationCode());
            task.setStorageStatus(tss.getStatus());
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
    
    private void setPlannedFinishDate(TestingTask entity, DNAQcTask task)
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
    
    @Override
    public List<Primer> getPrimerListByProductCodeAndAppType(String appType, String productCode)
    {
        String hql = "FROM Primer p where p.productCode=:productCode AND p.applicationType=:applicationType AND p.deleted = false ";
        NamedQueryer queryer =
            NamedQueryer.builder()
                .hql(hql)
                .names(Lists.newArrayList("productCode", "applicationType"))
                .values(Lists.newArrayList((Object)productCode, appType))
                .build();
        List<Primer> records = baseDaoSupport.find(queryer, Primer.class);
        if (Collections3.isNotEmpty(records))
        {
            return records;
        }
        return Lists.newArrayList();
    }
    
}
