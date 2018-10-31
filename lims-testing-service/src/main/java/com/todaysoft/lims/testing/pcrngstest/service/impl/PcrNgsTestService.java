package com.todaysoft.lims.testing.pcrngstest.service.impl;

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
import com.todaysoft.lims.testing.base.entity.ReceivedSample;
import com.todaysoft.lims.testing.base.entity.SangerVerifyRecord;
import com.todaysoft.lims.testing.base.entity.TestingMethod;
import com.todaysoft.lims.testing.base.entity.TestingPcrNgsSample;
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
import com.todaysoft.lims.testing.libcap.model.CaptureLibraryAttributes;
import com.todaysoft.lims.testing.libcap.model.LibraryCaptureSampleAttributes;
import com.todaysoft.lims.testing.pcrngsprimerdesign.model.PcrNgsPrimerDesignTaskVariables;
import com.todaysoft.lims.testing.pcrngstest.context.PcrNgsTestSubmitContext;
import com.todaysoft.lims.testing.pcrngstest.context.PcrNgsTestSubmitNextTaskContext;
import com.todaysoft.lims.testing.pcrngstest.context.PcrNgsTestSubmitScheduleContext;
import com.todaysoft.lims.testing.pcrngstest.context.PcrNgsTestSubmitTaskContext;
import com.todaysoft.lims.testing.pcrngstest.dao.PcrNgsTestAssignableTaskSearcher;
import com.todaysoft.lims.testing.pcrngstest.model.PcrNgsTestAssignArgs;
import com.todaysoft.lims.testing.pcrngstest.model.PcrNgsTestAssignModel;
import com.todaysoft.lims.testing.pcrngstest.model.PcrNgsTestAssignRequest;
import com.todaysoft.lims.testing.pcrngstest.model.PcrNgsTestAssignTaskArgs;
import com.todaysoft.lims.testing.pcrngstest.model.PcrNgsTestSheetModel;
import com.todaysoft.lims.testing.pcrngstest.model.PcrNgsTestSheetVariables;
import com.todaysoft.lims.testing.pcrngstest.model.PcrNgsTestSubmitRequest;
import com.todaysoft.lims.testing.pcrngstest.model.PcrNgsTestTask;
import com.todaysoft.lims.testing.pcrngstest.model.PcrNgsTestTaskVariables;
import com.todaysoft.lims.testing.pcrngstest.service.IPcrNgsTestService;
import com.todaysoft.lims.testing.samplesotck.service.ITestingSheetSampleStorageService;
import com.todaysoft.lims.testing.secondpcr.service.ISecondPcrService;
import com.todaysoft.lims.testing.technicalanaly.service.ITechnicalAnalyService;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.DoubleCalculateUtils;
import com.todaysoft.lims.utils.StringUtils;

@Service
public class PcrNgsTestService implements IPcrNgsTestService
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
    
    @Override
    @Transactional
    public List<PcrNgsTestTask> getAssignableList(PcrNgsTestAssignableTaskSearcher searcher)
    {
        List<TestingTask> records = baseDaoSupport.find(searcher);
        
        if (CollectionUtils.isEmpty(records))
        {
            return Collections.emptyList();
        }
        
        List<PcrNgsTestTask> tasks = new ArrayList<PcrNgsTestTask>();
        
        for (TestingTask record : records)
        {
            tasks.add(wrap(record));
        }
        List<PcrNgsTestTask> primerCodeNullList = tasks.stream().filter(x -> StringUtils.isEmpty(x.getForwardPrimerCode())).collect(Collectors.toList());
        if (Collections3.isNotEmpty(primerCodeNullList))
        {
            tasks.removeAll(primerCodeNullList);
        }
        
        if (Collections3.isNotEmpty(primerCodeNullList))
        {
            tasks.addAll(primerCodeNullList);
        }
        tasks.sort(Comparator.comparing(PcrNgsTestTask::getResubmitCount)
            .reversed()
            .thenComparing(PcrNgsTestTask::getOrderId)
            .thenComparing(PcrNgsTestTask::getChromLocation)
            .thenComparing(PcrNgsTestTask::getSampleName));
        //根据加急倒序
        tasks.sort(Comparator.comparing(PcrNgsTestTask::getIfUrgent).reversed());
        return tasks;
    }
    
    @Override
    public PcrNgsTestAssignModel getAssignableModel(PcrNgsTestAssignArgs args)
    {
        PcrNgsTestAssignModel model = new PcrNgsTestAssignModel();
        
        if (null == args || StringUtils.isEmpty(args.getKeys()))
        {
            model.setTasks(Collections.emptyList());
            return model;
        }
        
        Set<String> includeKeys = new HashSet<String>(Arrays.asList(args.getKeys().split(",")));
        PcrNgsTestAssignableTaskSearcher searcher = new PcrNgsTestAssignableTaskSearcher();
        searcher.setIncludeKeys(includeKeys);
        List<PcrNgsTestTask> tasks = getAssignableList(searcher);
        
        tasks.sort((h1, h2) -> h1.getSampleName().compareTo(h2.getSampleName()));
        
        //位置排序
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
    
    private void sortLocationMethod(List<PcrNgsTestTask> list)
    {
        Collections.sort(list, new Comparator<PcrNgsTestTask>()
        {
            
            @Override
            public int compare(PcrNgsTestTask o1, PcrNgsTestTask o2)
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
    public void assign(PcrNgsTestAssignRequest request, String token)
    {
        if (!CollectionUtils.isEmpty(request.getTasks()))
        {
            TestingTask testingTask;
            PcrNgsTestTaskVariables pcrNgsTestTaskVariables;
            
            for (PcrNgsTestAssignTaskArgs task : request.getTasks())
            {
                testingTask = testingTaskService.get(task.getId());
                testingTask.setStatus(TestingTask.STATUS_ASSIGNING);
                testingTaskService.modify(testingTask);
                
                pcrNgsTestTaskVariables = getTaskRunningVariables(task.getId());
                pcrNgsTestTaskVariables.setPcrTestCode(task.getPcrTestCode());
                pcrNgsTestTaskVariables.setSampleInputSize(task.getSampleInputSize());
                pcrNgsTestTaskVariables.setSampleInputVolume(task.getSampleInputVolume());
                pcrNgsTestTaskVariables.setLibraryIndex(task.getLibraryIndex());
                testingTaskService.updateVariables(task.getId(), pcrNgsTestTaskVariables);
                
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
    
    public PcrNgsTestTaskVariables getTaskRunningVariables(String taskId)
    {
        String variables = testingTaskService.getVariables(taskId);
        
        if (StringUtils.isEmpty(variables))
        {
            return new PcrNgsTestTaskVariables();
        }
        
        return JsonUtils.asObject(variables, PcrNgsTestTaskVariables.class);
    }
    
    private TestingSheetCreateModel buildTestingSheetCreateModel(PcrNgsTestAssignRequest request, String token)
    {
        UserMinimalModel loginer = smmadapter.getLoginUser(token);
        UserMinimalModel tester = smmadapter.getUserByID(request.getTesterId());
        TaskConfig taskConfig = bcmadapter.getTaskConfigBySemantic(TaskSemantic.PCR_NGS);
        
        TestingSheetCreateModel model = new TestingSheetCreateModel();
        model.setCode(commonService.getTaskCodeBySemantic(TaskSemantic.PCR_NGS));
        model.setTaskSemantic(TaskSemantic.PCR_NGS);
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
        PcrNgsTestSheetVariables variables = new PcrNgsTestSheetVariables();
        variables.setReagentKitId(request.getReagentKitId());
        model.setVariables(variables);
        return model;
    }
    
    @Override
    @Transactional
    public PcrNgsTestSheetModel getTestingSheet(String id)
    {
        Map<String, String> sampleLocationTempMap = Maps.newHashMap();
        Map<String, String> primerLocationTempMap = Maps.newHashMap();
        TestingSheet entity = testingSheetService.getSheet(id);
        
        if (null == entity)
        {
            return null;
        }
        
        PcrNgsTestSheetModel sheet = new PcrNgsTestSheetModel();
        sheet.setId(entity.getId());
        sheet.setCode(entity.getCode());
        sheet.setAssignerName(entity.getAssignerName());
        sheet.setAssignTime(entity.getAssignTime());
        sheet.setDescription(entity.getDescription());
        
        PcrNgsTestSheetVariables variables = JsonUtils.asObject(entity.getVariables(), PcrNgsTestSheetVariables.class);
        
        if (null != variables)
        {
            String reagentKitId = variables.getReagentKitId();
            ReagentKitSimpleModel reagentKit = bsmadapter.getReagentKit(reagentKitId);
            
            if (null != reagentKit)
            {
                sheet.setReagentKitName(reagentKit.getName());
            }
            
        }
        
        List<PcrNgsTestTask> tasks = wrapForTesting(entity.getTestingSheetTaskList());
        
        Collections.sort(tasks, new Comparator<PcrNgsTestTask>()
        {
            @Override
            public int compare(PcrNgsTestTask o1, PcrNgsTestTask o2)
            {
                return new TestingCodeComparator().compare(o1.getPcrTestCode(), o2.getPcrTestCode());
            }
        });
        
        //根据样本编号生成样本临时位置
        List<String> sampleCodeList = tasks.stream().map(PcrNgsTestTask::getSampleCode).distinct().collect(Collectors.toList());
        
        List<String> primerCodeList = tasks.stream().map(PcrNgsTestTask::getForwardPrimerCode).distinct().collect(Collectors.toList());
        
        for (int i = 0; i < sampleCodeList.size(); i++)
        {
            sampleLocationTempMap.put(sampleCodeList.get(i), commonService.getFirstPcrSampleTempLocation(i + 1));
        }
        
        for (int i = 0; i < primerCodeList.size(); i++)
        {
            primerLocationTempMap.put(primerCodeList.get(i), commonService.getPrimerTempLocation(i + 1));
        }
        
        for (PcrNgsTestTask firstPcrTask : tasks)
        {
            firstPcrTask.setSampleLocationTemp(sampleLocationTempMap.get(firstPcrTask.getSampleCode()));
            firstPcrTask.setForwardPrimerLocationTemp(primerLocationTempMap.get(firstPcrTask.getForwardPrimerCode()));
            firstPcrTask.setReversePrimerLocationTemp(firstPcrTask.getForwardPrimerLocationTemp().replace("F", "R"));
        }
        
        Map<Integer, String> mapIndexLocation = Maps.newHashMap();
        Integer index;
        String indexLocation = "";
        for (int i = 0; i < tasks.size(); i++)
        {
            index = tasks.get(i).getLibraryIndex();
            if (StringUtils.isNotEmpty(mapIndexLocation.get(index)))
            {
                indexLocation = mapIndexLocation.get(index);
            }
            else
            {
                indexLocation = commonService.getDNAExtractCode(i + 1);
                mapIndexLocation.put(index, indexLocation);
            }
            tasks.get(i).setLibraryIndexLocation("Index-" + indexLocation);
        }
        
        sheet.setTasks(tasks);
        return sheet;
    }
    
    @Override
    @Transactional
    public void submit(PcrNgsTestSubmitRequest request, String token)
    {
        // 1、设置提交上下文数据
        PcrNgsTestSubmitContext context = generateSubmitContext(request, token);
        
        // 2、更新任务结果
        doUpdateTasks(context);
        
        // 3.生成新的产物
        doCreateTestingSample(context);
        
        // 4、创建新节点任务
        doCreateNextNodeTasks(context);
        
        // 5、设置设计成功样本相关流程激活节点状态
        doUpdateScheduleNextActives(context);
        
        // 6、设置设计失败样本相关流程激活节点状态
        doUpdateScheduleErrorActives(context);
        
        // 7、设置任务单提交结果
        doUpdateFirstPcrSheet(context);
        
        // 8、完成任务单待办事项
        doCompleteProcess(context);
        
        // 9、取PCR-NGS扩展任务的输出PCRNGS-DNA临时产物 并保存PCR-NGS实验提交合并的任务信息，这样生信分析任务提交时候好分解任务到数据分析
        doCreatePcrNgsSample(context);
        
    }
    
    private PcrNgsTestSubmitContext generateSubmitContext(PcrNgsTestSubmitRequest request, String token)
    {
        TestingSheet sheet = testingSheetService.getSheet(request.getId());
        
        if (null == sheet)
        {
            throw new IllegalStateException();
        }
        
        PcrNgsTestSubmitContext context = new PcrNgsTestSubmitContext();
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
    
    private void setContextForTestingSheetTask(TestingSheetTask task, PcrNgsTestSubmitContext context)
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
    
    private void doUpdateTasks(PcrNgsTestSubmitContext context)
    {
        Set<PcrNgsTestSubmitTaskContext> records = context.getRelatedTasks();
        
        TestingTask task;
        Date timestamp = new Date();
        TestingTaskResult result;
        String testingTaskResult = "";
        
        for (PcrNgsTestSubmitTaskContext record : records)
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
            result.setDetails(JsonUtils.asJson(record.getDispose()));
            baseDaoSupport.insert(result);
        }
    }
    
    private void doCreateTestingSample(PcrNgsTestSubmitContext context)
    {
        Set<PcrNgsTestSubmitTaskContext> records = context.getRelatedTasks();
        
        TestingSheet testingSheet = context.getSheetEntity();
        
        String sheetId = testingSheet.getId();
        
        TestingSample rqtTestingSample;
        
        TestingSample outputSample;
        
        LibraryCaptureSampleAttributes attributes;
        
        TaskConfig taskConfig = bcmadapter.getTaskConfigBySemantic(TaskSemantic.PCR_NGS);
        
        BigDecimal pcrNgsConcn = bcmadapter.getPcrNgsRqtConcn();
        
        BigDecimal pcrNgsDataSize = bcmadapter.getPcrNgsRqtDataSize();
        
        SampleTypeSimpleModel outputSampleType = bcmadapter.getSampleType(taskConfig.getOutputSampleId());
        
        Set<CaptureLibraryAttributes> libraries = new HashSet<>();
        
        CaptureLibraryAttributes captureLibraryAttributes;
        
        List<String> indexStr = Lists.newArrayList();
        
        int sucCount = 0;
        
        for (PcrNgsTestSubmitTaskContext record : records)
        {
            if (0 == record.getResult().intValue())
            {
                String index = record.getLibraryIndex().toString();
                if (!indexStr.contains(index))
                {
                    indexStr.add(index);
                    captureLibraryAttributes = new CaptureLibraryAttributes();
                    captureLibraryAttributes.setIndex(record.getLibraryIndex().toString());
                    captureLibraryAttributes.setSampleCode(testingSheet.getCode() + "-" + TestingMethod.PCR_NGS);
                    libraries.add(captureLibraryAttributes);
                }
                sucCount++;
            }
        }
        BigDecimal countDataSize = new BigDecimal(DoubleCalculateUtils.mul(pcrNgsDataSize.doubleValue(), new Double(sucCount)));
        
        for (PcrNgsTestSubmitTaskContext record : records)
        {
            if (0 == record.getResult().intValue())
            {
                rqtTestingSample = context.getRqtTestingSample(sheetId);
                if (null == rqtTestingSample)
                {
                    attributes = new LibraryCaptureSampleAttributes();
                    outputSample = new TestingSample();
                    outputSample.setSampleCode(testingSheet.getCode() + "-" + TestingMethod.PCR_NGS);
                    outputSample.setSampleTypeId(outputSampleType.getId());
                    outputSample.setSampleTypeName(outputSampleType.getName());
                    attributes.setConcn(pcrNgsConcn);
                    attributes.setLibraries(libraries);
                    attributes.setProbeDatasize(countDataSize);
                    outputSample.setAttributes(JsonUtils.asJson(attributes));
                    baseDaoSupport.insert(outputSample);
                    
                    context.setRqtTestingSample(sheetId, outputSample);
                }
            }
        }
    }
    
    private void doCreateNextNodeTasks(PcrNgsTestSubmitContext context)
    {
        Set<PcrNgsTestSubmitNextTaskContext> nextTasks = context.getNextNodeTasks();
        
        Set<TestingTask> nextRqtTask;
        
        TestingSheet testingSheet = context.getSheetEntity();
        
        String sheetId = testingSheet.getId();
        
        TestingSample rqtTestingSample = context.getRqtTestingSample(sheetId);
        
        TestingTask task;
        Date timestamp = new Date();
        TestingTaskRunVariable variables;
        
        for (PcrNgsTestSubmitNextTaskContext nextTask : nextTasks)
        {
            nextRqtTask = context.getNextRqtTask();
            if (Collections3.isEmpty(nextRqtTask))
            {
                task = new TestingTask();
                task.setName(nextTask.getTaskName());
                task.setSemantic(nextTask.getTaskSemantic());
                task.setInputSample(rqtTestingSample);
                task.setStatus(TestingTask.STATUS_ASSIGNABLE);
                task.setResubmit(false);
                task.setResubmitCount(0);
                task.setStartTime(timestamp);
                baseDaoSupport.insert(task);
                
                variables = new TestingTaskRunVariable();
                variables.setTestingTaskId(task.getId());
                baseDaoSupport.insert(variables);
            }
            else
            {
                task = nextRqtTask.iterator().next();
                variables = context.getNextRqtVar(task.getId());
            }
            
            context.setContextForCreateNextNodeTask(nextTask.getTemporaryId(), task, variables);
        }
    }
    
    private void doUpdateScheduleNextActives(PcrNgsTestSubmitContext context)
    {
        Set<PcrNgsTestSubmitScheduleContext> schedules = context.getGotoNextSchedules();
        
        String nextTaskId;
        TestingSchedule schedule;
        TestingScheduleActive active;
        TestingScheduleHistory history;
        TestingTask activeTestingTask;
        Date timestamp = new Date();
        
        for (PcrNgsTestSubmitScheduleContext scheduleContext : schedules)
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
    
    private void doUpdateScheduleErrorActives(PcrNgsTestSubmitContext context)
    {
        Set<PcrNgsTestSubmitScheduleContext> schedules = context.getGotoErrorSchedules();
        
        TestingSchedule thisSchedule;
        TestingScheduleActive thisActive;
        TestingTask thisTask;
        String dispose;
        TestingTask nextTask;
        for (PcrNgsTestSubmitScheduleContext scheduleContext : schedules)
        {
            dispose = scheduleContext.getDispose();
            thisSchedule = scheduleContext.getTestingScheduleEntity();
            thisActive = scheduleContext.getTestingScheduleActiveEntity();
            thisTask = scheduleContext.getTestingTask();
            if (null == thisTask)
            {
                throw new IllegalStateException();
            }
            
            if ("重新实验".equals(dispose))
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
                TestingTask lastTask = testingScheduleService.getScheduleNodeLastTestingTask(thisSchedule.getId(), TaskSemantic.PCR_NGS_PRIMER_DESIGN);
                
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
            else
            {//实验取消
                thisSchedule.setActiveTask(thisSchedule.getActiveTask() + "-异常");
                baseDaoSupport.update(thisSchedule);
                
                baseDaoSupport.delete(thisActive);
            }
        }
    }
    
    private void doUpdateFirstPcrSheet(PcrNgsTestSubmitContext context)
    {
        TestingSheet sheet = context.getSheetEntity();
        UserMinimalModel submiter = context.getSubmiter();
        sheet.setSubmiterId(submiter.getId());
        sheet.setSubmiterName(submiter.getName());
        sheet.setSubmitTime(new Date());
        baseDaoSupport.update(sheet);
    }
    
    private void doCompleteProcess(PcrNgsTestSubmitContext context)
    {
        TestingSheet sheet = context.getSheetEntity();
        activitiService.submitTestingSheet(sheet.getId());
    }
    
    private void doCreatePcrNgsSample(PcrNgsTestSubmitContext context)
    {
        Set<PcrNgsTestSubmitNextTaskContext> nextNodeTasks = context.getNextNodeTasks();
        if (Collections3.isEmpty(nextNodeTasks))
        {
            return;
        }
        TestingSheet testingSheet = context.getSheetEntity();
        
        String sheetId = testingSheet.getId();
        
        TestingSample rqtTestingSample = context.getRqtTestingSample(sheetId);
        
        TestingPcrNgsSample testingPcrNgsSample;
        
        TestingSample dnaLocalSample;
        
        TestingSchedule testingSchedule;
        
        TaskConfig taskConfig = bcmadapter.getTaskConfigBySemantic(TaskSemantic.PCR_NGS_EXTEND);
        
        if (null == taskConfig)
        {
            throw new IllegalStateException();
        }
        
        SampleTypeSimpleModel outputSampleType = bcmadapter.getSampleType(taskConfig.getOutputSampleId());
        
        if (null == outputSampleType)
        {
            throw new IllegalStateException();
        }
        
        for (PcrNgsTestSubmitNextTaskContext record : nextNodeTasks)
        {
            testingSchedule = baseDaoSupport.get(TestingSchedule.class, record.getScheduleId());
            
            dnaLocalSample = new TestingSample();
            dnaLocalSample.setSampleCode(record.getCombineCode() + "_" + record.getTestingSample().getSampleCode());
            dnaLocalSample.setSampleTypeId(outputSampleType.getId());
            dnaLocalSample.setSampleTypeName(outputSampleType.getName());
            dnaLocalSample.setReceivedSample(record.getTestingSample().getReceivedSample());
            dnaLocalSample.setParentSample(record.getTestingSample());
            baseDaoSupport.insert(dnaLocalSample);
            
            testingPcrNgsSample = new TestingPcrNgsSample();
            testingPcrNgsSample.setOutputSample(rqtTestingSample);
            testingPcrNgsSample.setInputSample(dnaLocalSample);
            testingPcrNgsSample.setTestingSchedule(testingSchedule);
            testingPcrNgsSample.setCombineCode(record.getCombineCode());
            testingPcrNgsSample.setLibraryIndex(record.getLibraryIndex());
            testingPcrNgsSample.setSampleVolume(record.getVolume());
            baseDaoSupport.insert(testingPcrNgsSample);
            
        }
    }
    
    private List<PcrNgsTestTask> wrapForTesting(List<TestingSheetTask> records)
    {
        if (CollectionUtils.isEmpty(records))
        {
            return Collections.emptyList();
        }
        
        TestingTask testingTask;
        List<PcrNgsTestTask> tasks = new ArrayList<PcrNgsTestTask>();
        
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
    
    private PcrNgsTestTask wrap(TestingTask entity)
    {
        PcrNgsTestTask task = new PcrNgsTestTask();
        task.setId(entity.getId());
        task.setStartTime(entity.getStartTime());
        task.setSampleName(entity.getReceivedSampleName() == null ? "" : entity.getReceivedSampleName());
        task.setSampleCode(entity.getReceivedSampleCode());
        task.setForwardPrimerCode(entity.getTestingPrimerName());
        task.setReversePrimerCode(entity.getTestingPrimerReverseName());
        task.setVerifyScheme(entity.getTestingVerifyScheme());
        task.setResubmit(entity.isResubmit());
        task.setResubmitCount(entity.getResubmitCount());
        task.setChromLocation(entity.getVerifyChromosomePosition() == null ? "" : entity.getVerifyChromosomePosition());
        task.setCombineCode(entity.getTestingCombineCode());
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
        
        //根据testingTask 查询相关的流程 再查寻到技术分析数据表
        List<TestingSchedule> schedules = testingScheduleService.getRelatedSchedules(entity.getId());
        if (Collections3.isEmpty(schedules))
        {
            schedules = testingScheduleService.getRelatedSchedulesByTestingTask(entity.getId());
        }
        TestingSchedule testingSchedule;
        PcrNgsTestTaskVariables variables = JsonUtils.asObject(entity.getTestingInputArgs(), PcrNgsTestTaskVariables.class);
        
        DNAAttributes attributes = JsonUtils.asObject(entity.getTestingSampleAttributes(), DNAAttributes.class);
        if (null != attributes)
        {
            task.setConcentration(attributes.getConcn());
        }
        
        if (null != variables)
        {
            task.setPcrTestCode(variables.getPcrTestCode());
            task.setLibraryIndex(variables.getLibraryIndex());
            task.setVolume(variables.getSampleInputVolume());
            task.setRemark(variables.getRemark());
        }
        
        if (Collections3.isNotEmpty(schedules))
        {
            testingSchedule = schedules.get(0);
            task.setOrderId(testingSchedule.getOrderId() == null ? "" : testingSchedule.getOrderId());
        }
        
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
    
    private void setPlannedFinishDate(TestingTask entity, PcrNgsTestTask task)
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
        if (DoubleCalculateUtils.compare(d, pcrMin.doubleValue()) <= 0)
        {
            return pcrMin;
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
    
}
