package com.todaysoft.lims.testing.dnaext.service.impl;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
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
import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.testing.activiti.service.IActivitiService;
import com.todaysoft.lims.testing.base.entity.MlpaVerifyRecord;
import com.todaysoft.lims.testing.base.entity.OrderPlanTask;
import com.todaysoft.lims.testing.base.entity.QpcrVerifyRecord;
import com.todaysoft.lims.testing.base.entity.SangerVerifyRecord;
import com.todaysoft.lims.testing.base.entity.TestingMethod;
import com.todaysoft.lims.testing.base.entity.TestingSample;
import com.todaysoft.lims.testing.base.entity.TestingSampleStorage;
import com.todaysoft.lims.testing.base.entity.TestingSchedule;
import com.todaysoft.lims.testing.base.entity.TestingScheduleHistory;
import com.todaysoft.lims.testing.base.entity.TestingSheet;
import com.todaysoft.lims.testing.base.entity.TestingSheetTask;
import com.todaysoft.lims.testing.base.entity.TestingTask;
import com.todaysoft.lims.testing.base.entity.TestingTaskRunVariable;
import com.todaysoft.lims.testing.base.entity.TestingTechnicalAnalyRecord;
import com.todaysoft.lims.testing.base.model.BatchWrapTestingTaskContext;
import com.todaysoft.lims.testing.base.model.ReagentKitSimpleModel;
import com.todaysoft.lims.testing.base.model.SampleTypeSimpleModel;
import com.todaysoft.lims.testing.base.model.TaskConfig;
import com.todaysoft.lims.testing.base.model.TaskSemantic;
import com.todaysoft.lims.testing.base.model.TestingSheetCreateModel;
import com.todaysoft.lims.testing.base.service.ICommonService;
import com.todaysoft.lims.testing.base.service.ITestingSampleService;
import com.todaysoft.lims.testing.base.service.ITestingScheduleService;
import com.todaysoft.lims.testing.base.service.ITestingSheetService;
import com.todaysoft.lims.testing.base.service.ITestingTaskService;
import com.todaysoft.lims.testing.base.service.TestingCodeComparator;
import com.todaysoft.lims.testing.base.service.adapter.bcm.BCMAdapter;
import com.todaysoft.lims.testing.base.service.adapter.bsm.BSMAdapter;
import com.todaysoft.lims.testing.base.service.adapter.smm.SMMAdapter;
import com.todaysoft.lims.testing.base.service.adapter.smm.UserMinimalModel;
import com.todaysoft.lims.testing.base.utils.JsonUtils;
import com.todaysoft.lims.testing.dnaext.context.DNAExtractSheetSubmitContext;
import com.todaysoft.lims.testing.dnaext.context.DNAExtractSheetSubmitNextTaskContext;
import com.todaysoft.lims.testing.dnaext.context.DNAExtractSheetSubmitScheduleContext;
import com.todaysoft.lims.testing.dnaext.context.DNAExtractSheetSubmitTaskContext;
import com.todaysoft.lims.testing.dnaext.dao.DNAExtractTaskSearcher;
import com.todaysoft.lims.testing.dnaext.model.DNAExtractAssignArgs;
import com.todaysoft.lims.testing.dnaext.model.DNAExtractAssignModel;
import com.todaysoft.lims.testing.dnaext.model.DNAExtractAssignSheet;
import com.todaysoft.lims.testing.dnaext.model.DNAExtractAssignSheetTask;
import com.todaysoft.lims.testing.dnaext.model.DNAExtractSheet;
import com.todaysoft.lims.testing.dnaext.model.DNAExtractSheetVariables;
import com.todaysoft.lims.testing.dnaext.model.DNAExtractTask;
import com.todaysoft.lims.testing.dnaext.model.DNAExtractTaskResultDetails;
import com.todaysoft.lims.testing.dnaext.model.DNAExtractTaskVariables;
import com.todaysoft.lims.testing.dnaext.service.IDNAExtractService;
import com.todaysoft.lims.testing.dnaqc.model.DNAQcSheetVariables;
import com.todaysoft.lims.testing.dnaqc.model.DNAQcTaskVariables;
import com.todaysoft.lims.testing.samplesotck.service.ITestingSheetSampleStorageService;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.StringUtils;

@Service
public class DNAExtractService implements IDNAExtractService
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
    private ITestingScheduleService testingScheduleService;
    
    @Autowired
    private ITestingTaskService testingTaskService;
    
    @Autowired
    private ITestingSheetService testingSheetService;
    
    @Autowired
    private ICommonService commonService;
    
    @Autowired
    private IActivitiService activitiService;
    
    @Autowired
    private ITestingSampleService testingSampleService;
    
    @Autowired
    private ITestingSheetSampleStorageService testingSheetSampleStorageService;
    
    @Override
    public List<DNAExtractTask> getAssignableList(DNAExtractTaskSearcher searcher)
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
        
        List<DNAExtractTask> tasks = new ArrayList<DNAExtractTask>();
        
        for (TestingTask record : records)
        {
            tasks.add(wrap(record, null));
        }
        tasks.sort(Comparator.comparing(DNAExtractTask::getResubmitCount).reversed().thenComparing(DNAExtractTask::getPlannedFinishDate));
        tasks.sort(Comparator.comparing(DNAExtractTask::getIfUrgent).reversed());
        return tasks;
    }
    
    @Override
    public DNAExtractAssignModel getAssignModel(DNAExtractAssignArgs args)
    {
        DNAExtractAssignModel model = new DNAExtractAssignModel();
        
        if (null == args || StringUtils.isEmpty(args.getKeys()))
        {
            model.setTasks(Collections.emptyList());
            return model;
        }
        
        Set<String> includeKeys = new HashSet<String>(Arrays.asList(args.getKeys().split(",")));
        DNAExtractTaskSearcher searcher = new DNAExtractTaskSearcher();
        searcher.setIncludeKeys(includeKeys);
        List<DNAExtractTask> tasks = this.getAssignableList(searcher);
        
        if (CollectionUtils.isEmpty(tasks))
        {
            model.setTasks(Collections.emptyList());
            return model;
        }
        
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
    
    @Override
    public TestingSheetCreateModel buildTestingSheetCreateModel(DNAExtractAssignSheet request, String token)
    {
        UserMinimalModel loginer = smmadapter.getLoginUser(token);
        UserMinimalModel tester = smmadapter.getUserByID(request.getExtractTesterId());
        TaskConfig taskConfig = bcmadapter.getTaskConfigBySemantic(TaskSemantic.DNA_EXTRACT);
        
        TestingSheetCreateModel model = new TestingSheetCreateModel();
        model.setCode(commonService.getTaskCodeBySemantic(TaskSemantic.DNA_EXTRACT));
        model.setTaskSemantic(TaskSemantic.DNA_EXTRACT);
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
        
        // 设置DNA提取任务单自定义参数对象
        DNAExtractSheetVariables variables = new DNAExtractSheetVariables();
        variables.setExtractReagentKitId(request.getExtractReagentKitId());
        variables.setQualityControlMethods(request.getQualityControlMethods());
        variables.setQualityControlTesterId(request.getQualityControlTesterId());
        variables.setQualityControlReagentKitId(request.getQualityControlReagentKitId());
        model.setVariables(variables);
        return model;
    }
    
    @Override
    @Transactional
    public void assign(DNAExtractAssignSheet request, String token)
    {
        // 更新DNA提取任务运行时参数
        TaskConfig taskConfig = bcmadapter.getTaskConfigBySemantic(TaskSemantic.DNA_EXTRACT);
        SampleTypeSimpleModel sampleType = bcmadapter.getSampleType(taskConfig.getOutputSampleId());
        if (!CollectionUtils.isEmpty(request.getTasks()))
        {
            TestingTask testingTask;
            DNAExtractTaskVariables variables;
            Map<String, Integer> derivedIndexContext = new HashMap<String, Integer>();
            
            for (DNAExtractAssignSheetTask task : request.getTasks())
            {
                testingTask = testingTaskService.get(task.getId());
                testingTask.setStatus(TestingTask.STATUS_ASSIGNING);
                testingTaskService.modify(testingTask);
                
                variables = testingTaskService.obtainVariables(task.getId(), DNAExtractTaskVariables.class);
                variables.setTestingCode(task.getTestingCode());
                variables.setSampleInputSize(task.getSampleInputSize());
                variables.setOutputSampleTypeId(sampleType.getId());
                variables.setOutputSampleTypeName(sampleType.getName());
                variables.setOutputSampleSize(taskConfig.getOutputVolume());
                variables.setOutputSampleCode(getDNASampleCode(testingTask.getInputSample(), derivedIndexContext));
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
    public DNAExtractSheet getSheet(String id)
    {
        TestingSheet entity = testingSheetService.getSheet(id);
        
        if (null == entity)
        {
            return null;
        }
        
        DNAExtractSheet sheet = new DNAExtractSheet();
        sheet.setId(entity.getId());
        sheet.setCode(entity.getCode());
        sheet.setAssignerName(entity.getAssignerName());
        sheet.setAssignTime(entity.getAssignTime());
        sheet.setTesterName(entity.getTesterName());
        sheet.setSubmitTime(entity.getSubmitTime());
        sheet.setDescription(entity.getDescription());
        
        DNAExtractSheetVariables variables = JsonUtils.asObject(entity.getVariables(), DNAExtractSheetVariables.class);
        
        if (null != variables)
        {
            String reagentKitId = variables.getExtractReagentKitId();
            ReagentKitSimpleModel reagentKit = bsmadapter.getReagentKit(reagentKitId);
            
            if (null != reagentKit)
            {
                sheet.setReagentKitName(reagentKit.getName());
            }
        }
        
        List<DNAExtractTask> tasks = wrap(entity.getTestingSheetTaskList());
        
        Collections.sort(tasks, new Comparator<DNAExtractTask>()
        {
            @Override
            public int compare(DNAExtractTask o1, DNAExtractTask o2)
            {
                return new TestingCodeComparator().compare(o1.getTestingCode(), o2.getTestingCode());
            }
        });
        
        sheet.setTasks(tasks);
        return sheet;
    }
    
    @Override
    @Transactional
    public void submitSheet(String id, String token)
    {
        // 1、设置提交上下文数据
        DNAExtractSheetSubmitContext context = generateSubmitContext(id, token);
        
        // 2、更新DNA提取任务结果
        doUpdateTasks(context);
        
        // 4、创建新节点任务
        doCreateNextNodeTasks(context);
        
        // 5、设置检测流程激活节点状态
        doUpdateScheduleActives(context);
        
        // 6、设置任务单提交结果
        doUpdateExtractSheet(context);
        
        // 7、完成DNA任务单待办事项
        doCompleteExtractProcess(context);
        
        // 8、生成DNA质检任务单
        doCreateQualityControlSheet(context);
        
        // 9.
    }
    
    private DNAExtractSheetSubmitContext generateSubmitContext(String id, String token)
    {
        TestingSheet sheet = testingSheetService.getSheet(id);
        
        if (null == sheet)
        {
            throw new IllegalStateException();
        }
        
        DNAExtractSheetSubmitContext context = new DNAExtractSheetSubmitContext();
        context.setSubmiter(smmadapter.getLoginUser(token));
        context.setContextForExtractTestingSheet(sheet);
        
        List<TestingSheetTask> tasks = sheet.getTestingSheetTaskList();
        
        for (TestingSheetTask task : tasks)
        {
            setContextForTask(task, context);
        }
        
        return context;
    }
    
    private void setContextForTask(TestingSheetTask task, DNAExtractSheetSubmitContext context)
    {
        String id = task.getTestingTaskId();
        TestingTask testingTask = testingTaskService.get(id);
        context.setConextForTestingTaskEntity(testingTask);
        
        DNAExtractTaskVariables variables = testingTaskService.obtainVariables(id, DNAExtractTaskVariables.class);
        context.setConextForTestingTaskVariables(id, variables);
        
        TaskConfig scheduleNextNodeConfig;
        List<TestingSchedule> schedules = testingScheduleService.getRelatedSchedules(id);
        
        for (TestingSchedule schedule : schedules)
        {
            scheduleNextNodeConfig = testingScheduleService.getScheduleNextNodeConfig(schedule, testingTask.getSemantic());
            
            if (null == scheduleNextNodeConfig)
            {
                throw new IllegalStateException();
            }
            
            context.setContextForTestingTaskRelatedSchedule(id, schedule, scheduleNextNodeConfig);
        }
    }
    
    private void doUpdateTasks(DNAExtractSheetSubmitContext context)
    {
        Set<DNAExtractSheetSubmitTaskContext> records = context.getRelatedTasks();
        
        TestingTask task;
        TestingSample outputSample;
        DNAExtractTaskResultDetails details;
        
        for (DNAExtractSheetSubmitTaskContext record : records)
        {
            task = record.getTestingTaskEntity();
            
            details = new DNAExtractTaskResultDetails();
            details.setTestingCode(record.getTestingCode());
            details.setOutputSampleCode(record.getOutputSampleCode());
            details.setOutputSampleSize(record.getOutputSampleSize());
            
            outputSample = new TestingSample();
            outputSample.setParentSample(task.getInputSample());
            outputSample.setReceivedSample(task.getInputSample().getReceivedSample());
            outputSample.setSampleCode(record.getOutputSampleCode());
            outputSample.setSampleTypeId(record.getOutputSampleTypeId());
            outputSample.setSampleTypeName(record.getOutputSampleTypeName());
            
            testingTaskService.asSuccess(task, details, outputSample);
            context.setContextForInsertOutputSample(outputSample);
        }
    }
    
    private void doCreateNextNodeTasks(DNAExtractSheetSubmitContext context)
    {
        Set<DNAExtractSheetSubmitNextTaskContext> nextTasks = context.getNextNodeTasks();
        
        TestingTask task;
        TestingTaskRunVariable variables;
        Date timestamp = new Date();
        
        for (DNAExtractSheetSubmitNextTaskContext nextTask : nextTasks)
        {
            task = new TestingTask();
            task.setName(nextTask.getTaskName());
            task.setSemantic(nextTask.getTaskSemantic());
            task.setStatus(TestingTask.STATUS_ASSIGNABLE);
            task.setResubmit(false);
            task.setResubmitCount(0);
            task.setStartTime(timestamp);
            task.setInputSample(context.getOutputSampleEntity(nextTask.getSampleCode()));
            baseDaoSupport.insert(task);
            
            variables = new TestingTaskRunVariable();
            variables.setTestingTaskId(task.getId());
            baseDaoSupport.insert(variables);
            
            context.setContextForCreateNextNodeTask(nextTask.getSampleCode(), task.getSemantic(), task, variables);
        }
    }
    
    private void doUpdateScheduleActives(DNAExtractSheetSubmitContext context)
    {
        Set<DNAExtractSheetSubmitScheduleContext> schedules = context.getRelatedSchedules();
        
        TestingSchedule schedule;
        TestingTask nextTask;
        Date timestamp = new Date();
        
        for (DNAExtractSheetSubmitScheduleContext scheduleContext : schedules)
        {
            schedule = scheduleContext.getTestingScheduleEntity();
            nextTask = context.getNextTask(scheduleContext.getNextNodeInputSampleCode(), scheduleContext.getNextNodeConfig().getSemantic());
            testingScheduleService.gotoNextNode(schedule, TaskSemantic.DNA_EXTRACT, nextTask, timestamp);
        }
        //更新冗余信息
        Collection<DNAExtractSheetSubmitNextTaskContext> nextTasks = context.getNextTasks().values();
        for (DNAExtractSheetSubmitNextTaskContext model : nextTasks)
        {
            testingTaskService.updateTaskRedundantColumn(Arrays.asList(model.getTestingTaskEntity()), 0);
        }
    }
    
    private void doUpdateExtractSheet(DNAExtractSheetSubmitContext context)
    {
        TestingSheet sheet = context.getExtractSheetEntity();
        UserMinimalModel submiter = context.getSubmiter();
        sheet.setSubmiterId(submiter.getId());
        sheet.setSubmiterName(submiter.getName());
        sheet.setSubmitTime(new Date());
        baseDaoSupport.update(sheet);
    }
    
    private void doCompleteExtractProcess(DNAExtractSheetSubmitContext context)
    {
        TestingSheet sheet = context.getExtractSheetEntity();
        activitiService.submitTestingSheet(sheet.getId());
    }
    
    private void doCreateQualityControlSheet(DNAExtractSheetSubmitContext context)
    {
        String semantic = TaskSemantic.DNA_QC;
        TaskConfig config = bcmadapter.getTaskConfigBySemantic(semantic);
        TestingSheet extractSheet = context.getExtractSheetEntity();
        DNAExtractSheetVariables variables = context.getExtractSheetVariables();
        UserMinimalModel tester = smmadapter.getUserByID(variables.getQualityControlTesterId());
        
        DNAQcSheetVariables qcVariables = new DNAQcSheetVariables();
        qcVariables.setQualityControlMethods(variables.getQualityControlMethods());
        qcVariables.setQualityControlReagentKitId(variables.getQualityControlReagentKitId());
        
        List<String> sheetTasks = new ArrayList<String>();
        Set<DNAExtractSheetSubmitNextTaskContext> nextNodeTasks = context.getNextNodeTasks();
        
        for (DNAExtractSheetSubmitNextTaskContext nextNodeTask : nextNodeTasks)
        {
            if (semantic.equals(nextNodeTask.getTaskSemantic()))
            {
                sheetTasks.add(nextNodeTask.getTestingTaskEntity().getId());
            }
        }
        
        Date timestamp = new Date();
        TestingSheetCreateModel model = new TestingSheetCreateModel();
        model.setTaskSemantic(TaskSemantic.DNA_QC);
        model.setTaskName(config.getName());
        String dnaQcSheetCode = "DNAZJ" + extractSheet.getCode().substring(3);//直接DNA提取到DNA质检的任务单号就用DNA提取的流水号
        model.setCode(dnaQcSheetCode);
        model.setDescription(extractSheet.getDescription());
        model.setAssignerId(extractSheet.getAssignerId());
        model.setAssignerName(extractSheet.getAssignerName());
        model.setAssignTime(timestamp);
        model.setCreateTime(timestamp);
        model.setTesterId(tester.getId());
        model.setTesterName(tester.getName());
        model.setVariables(qcVariables);
        model.setTasks(sheetTasks);
        TestingSheet sheet = testingSheetService.create(model);
        
        String hql = "FROM TestingSheetTask st WHERE st.testingSheet.id = :sheetId";
        List<TestingSheetTask> tasks = baseDaoSupport.findByNamedParam(TestingSheetTask.class, hql, new String[] {"sheetId"}, new Object[] {sheet.getId()});
        
        TestingTask task;
        TestingTaskRunVariable qcVariablesEntity;
        DNAQcTaskVariables qcTaskVariables;
        DNAExtractSheetSubmitNextTaskContext nextTaskContext;
        
        for (TestingSheetTask sheetTask : tasks)
        {
            nextTaskContext = context.getNextTaskConextById(sheetTask.getTestingTaskId());
            task = nextTaskContext.getTestingTaskEntity();
            qcVariablesEntity = nextTaskContext.getTestingTaskVariablesEntity();
            
            task.setStatus(TestingTask.STATUS_ASSIGNING);
            qcTaskVariables = testingTaskService.obtainVariables(task.getId(), DNAQcTaskVariables.class);
            qcTaskVariables.setTestingCode(nextTaskContext.getTestingCode());
            task.setTestingInputArgs(JsonUtils.asJson(qcTaskVariables));
            testingTaskService.modify(task);
            
            qcTaskVariables = testingTaskService.obtainVariables(task.getId(), DNAQcTaskVariables.class);
            qcTaskVariables.setTestingCode(nextTaskContext.getTestingCode());
            qcVariablesEntity.setText(JsonUtils.asJson(qcTaskVariables));
            baseDaoSupport.update(qcVariablesEntity);
        }
        
        activitiService.releaseTestingSheet(sheet);
    }
    
    private List<DNAExtractTask> wrap(List<TestingSheetTask> records)
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
        List<DNAExtractTask> tasks = new ArrayList<DNAExtractTask>();
        
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
    
    private DNAExtractTask wrap(TestingTask entity, BatchWrapTestingTaskContext context)
    {
        String id = entity.getId();
        
        DNAExtractTask task = new DNAExtractTask();
        
        // 基础信息
        task.setId(id);
        task.setStartTime(entity.getStartTime());
        task.setResubmit(entity.isResubmit());
        task.setResubmitCount(entity.getResubmitCount());
        
        task.setOrderType(entity.getOrderMarketingCenter());
        
        task.setSampleCode(entity.getTestingSampleCode());
        task.setSampleTypeId(entity.getTestingSampleTypeId());
        task.setSampleTypeName(entity.getReceivedSampleType());
        task.setSampleName(entity.getReceivedSampleName());
        task.setSamplingDate(entity.getReceivedSampleSamplingTime());
        task.setPurpose(entity.getReceivedSamplePurpose());
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
        DNAExtractTaskVariables variables = JsonUtils.asObject(entity.getTestingInputArgs(), DNAExtractTaskVariables.class);
        
        if (null != variables)
        {
            task.setLocation(variables.getLocation());
            task.setTestingCode(variables.getTestingCode());
            task.setSampleInputSize(variables.getSampleInputSize());
            task.setOutputSampleCode(variables.getOutputSampleCode());
            task.setOutputSampleSize(variables.getOutputSampleSize());
            task.setRemark(variables.getRemark());
        }
        
        if ("1".equals(task.getPurpose()))
        {
            task.setProducts(entity.getVerifyChromosomePosition());
        }
        else
        {
            task.setProducts(entity.getProductName());
        }
        
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
    
    private void setPlannedFinishDate(TestingTask entity, DNAExtractTask task)
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
    
    private String getDNASampleCode(TestingSample inputSample, Map<String, Integer> derivedIndexContext)
    {
        Integer index = derivedIndexContext.get(inputSample.getId());
        
        if (null == index)
        {
            int derivedCount = testingSampleService.getDerivedSampleCount(inputSample.getId());
            index = derivedCount + 1;
        }
        else
        {
            index++;
        }
        
        derivedIndexContext.put(inputSample.getId(), index);
        String code = new DecimalFormat("00").format(index);
        return "D" + inputSample.getSampleCode() + code;
    }
    
    @Override
    public TestingTechnicalAnalyRecord getChromLocationBy(String verifyKey, String taskSemantic)
    {
        String chromosomalLocation = "";
        TestingTechnicalAnalyRecord analyRecord = null;
        if (TestingMethod.SANGER.equals(taskSemantic) || TestingMethod.PCR_NGS.equals(taskSemantic))
        {
            SangerVerifyRecord sangerVerifyRecord = baseDaoSupport.get(SangerVerifyRecord.class, verifyKey);
            if (null != sangerVerifyRecord)
            {
                analyRecord = sangerVerifyRecord.getVerifyRecord().getAnalyRecord();
            }
            
        }
        else if (TestingMethod.MLPA.equals(taskSemantic))
        {
            MlpaVerifyRecord mlpaVerifyRecord = baseDaoSupport.get(MlpaVerifyRecord.class, verifyKey);
            
            if (null != mlpaVerifyRecord)
            {
                analyRecord = mlpaVerifyRecord.getVerifyRecord().getAnalyRecord();
            }
        }
        else if (TestingMethod.QPCR.equals(taskSemantic))
        {
            QpcrVerifyRecord qpcrVerifyRecord = baseDaoSupport.get(QpcrVerifyRecord.class, verifyKey);
            
            if (null != qpcrVerifyRecord)
            {
                analyRecord = qpcrVerifyRecord.getVerifyRecord().getAnalyRecord();
            }
        }
        return analyRecord;
    }
    
    @Override
    public String getByDnaExtCode(String semantic, String code)
    {
        String result = "";
        if (StringUtils.isNotEmpty(code))
        {
            String indexStr = "";
            String dnaQc = commonService.getTaskCodeBySemantic(semantic);
            String index = StringUtils.right(dnaQc, 2);
            if (TaskSemantic.DNA_QC.equals(semantic))
            {
                indexStr = "DNAZJ";
            }
            else if (TaskSemantic.CDNA_QC.equals(semantic))
            {
                indexStr = "CDNAZJ";
            }
            else if (TaskSemantic.LIBRARY_QC.equals(semantic))
            {
                indexStr = "LIBZJ";
            }
            else if (TaskSemantic.PCR_TWO.equals(semantic))
            {
                indexStr = "T";
            }
            String dateStr = StringUtils.reverse(code).substring(2, 8);
            result = indexStr + StringUtils.reverse(dateStr) + index;
        }
        return result;
    }
    
    @SuppressWarnings("unchecked")
    private void sortLocationMethod(List<DNAExtractTask> list)
    {
        Collections.sort(list, new Comparator()
        {
            @Override
            public int compare(Object o1, Object o2)
            {
                DNAExtractTask task1 = (DNAExtractTask)o1;
                DNAExtractTask task2 = (DNAExtractTask)o2;
                return task1.getLocation().compareTo(task2.getLocation());
            }
        });
    }
}
