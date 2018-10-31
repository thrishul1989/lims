package com.todaysoft.lims.testing.libcre.service.impl;

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

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Lists;
import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.testing.activiti.service.IActivitiService;
import com.todaysoft.lims.testing.base.entity.OrderPlanTask;
import com.todaysoft.lims.testing.base.entity.TestingSample;
import com.todaysoft.lims.testing.base.entity.TestingSampleStorage;
import com.todaysoft.lims.testing.base.entity.TestingSampleTemporary;
import com.todaysoft.lims.testing.base.entity.TestingSchedule;
import com.todaysoft.lims.testing.base.entity.TestingScheduleHistory;
import com.todaysoft.lims.testing.base.entity.TestingSheet;
import com.todaysoft.lims.testing.base.entity.TestingSheetTask;
import com.todaysoft.lims.testing.base.entity.TestingTask;
import com.todaysoft.lims.testing.base.entity.TestingTaskResult;
import com.todaysoft.lims.testing.base.entity.TestingTaskRunVariable;
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
import com.todaysoft.lims.testing.dnaext.service.IDNAExtractService;
import com.todaysoft.lims.testing.dnaqc.model.DNAAttributes;
import com.todaysoft.lims.testing.libcap.model.LibraryCaptureTask;
import com.todaysoft.lims.testing.libcre.context.LibraryCreateSheetSubmitContext;
import com.todaysoft.lims.testing.libcre.context.LibraryCreateSheetSubmitNextTaskContext;
import com.todaysoft.lims.testing.libcre.context.LibraryCreateSheetSubmitScheduleContext;
import com.todaysoft.lims.testing.libcre.context.LibraryCreateSheetSubmitTaskContext;
import com.todaysoft.lims.testing.libcre.dao.LibraryCreateTaskSearcher;
import com.todaysoft.lims.testing.libcre.model.LibCreAssignArgs;
import com.todaysoft.lims.testing.libcre.model.LibCreAssignModel;
import com.todaysoft.lims.testing.libcre.model.LibraryAttributes;
import com.todaysoft.lims.testing.libcre.model.LibraryCreateAssignRequest;
import com.todaysoft.lims.testing.libcre.model.LibraryCreateAssignTaskArgs;
import com.todaysoft.lims.testing.libcre.model.LibraryCreateSheet;
import com.todaysoft.lims.testing.libcre.model.LibraryCreateSheetVariables;
import com.todaysoft.lims.testing.libcre.model.LibraryCreateTask;
import com.todaysoft.lims.testing.libcre.model.LibraryCreateTaskResultDetails;
import com.todaysoft.lims.testing.libcre.model.LibraryCreateTaskVariables;
import com.todaysoft.lims.testing.libcre.service.ILibraryCreateService;
import com.todaysoft.lims.testing.libqc.model.LibraryQcSheetVariables;
import com.todaysoft.lims.testing.libqc.model.LibraryQcTaskVariables;
import com.todaysoft.lims.testing.samplesotck.service.ITestingSheetSampleStorageService;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.StringUtils;

@Service
public class LibraryCreateService implements ILibraryCreateService
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
    private ITestingSampleService testingSampleService;
    
    @Autowired
    private IDNAExtractService dnaExtractService;
    
    @Autowired
    private ITestingSheetSampleStorageService testingSheetSampleStorageService;
    
    @Override
    public List<LibraryCreateTask> getAssignableList(LibraryCreateTaskSearcher searcher)
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
        
        List<LibraryCreateTask> tasks = new ArrayList<LibraryCreateTask>();
        
        for (TestingTask record : records)
        {
            tasks.add(wrap(record, null));
        }
        tasks.sort(Comparator.comparing(LibraryCreateTask::getResubmitCount).reversed()
            .thenComparing(LibraryCreateTask::getPlannedFinishDate));
        tasks.sort(Comparator.comparing(LibraryCreateTask::getIfUrgent).reversed());
        return tasks;
    }
    
    @Override
    public LibCreAssignModel getAssignModel(LibCreAssignArgs args)
    {
        LibCreAssignModel model = new LibCreAssignModel();
        
        if (null == args || StringUtils.isEmpty(args.getKeys()))
        {
            model.setTasks(Collections.emptyList());
            return model;
        }
        
        Set<String> includeKeys = new HashSet<String>(Arrays.asList(args.getKeys().split(",")));
        LibraryCreateTaskSearcher searcher = new LibraryCreateTaskSearcher();
        searcher.setIncludeKeys(includeKeys);
        List<LibraryCreateTask> tasks = this.getAssignableList(searcher);
        
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
    public TestingSheetCreateModel buildTestingSheetCreateModel(LibraryCreateAssignRequest request, String token)
    {
        UserMinimalModel loginer = smmadapter.getLoginUser(token);
        UserMinimalModel tester = smmadapter.getUserByID(request.getCreateTesterId());
        TaskConfig taskConfig = bcmadapter.getTaskConfigBySemantic(TaskSemantic.LIBRARY_CRE);
        
        TestingSheetCreateModel model = new TestingSheetCreateModel();
        model.setCode(commonService.getTaskCodeBySemantic(TaskSemantic.LIBRARY_CRE));
        model.setTaskSemantic(TaskSemantic.LIBRARY_CRE);
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
            
            for (LibraryCreateAssignTaskArgs task : request.getTasks())
            {
                keys.add(task.getId());
            }
            
            model.setTasks(keys);
        }
        
        // 设置DNA质检任务单自定义参数对象
        LibraryCreateSheetVariables variables = new LibraryCreateSheetVariables();
        variables.setCreateReagentKitId(request.getCreateReagentKitId());
        variables.setQualityControlTesterId(request.getQualityControlTesterId());
        variables.setQualityControlReagentKitId(request.getQualityControlReagentKitId());
        variables.setQualityControlMethods(request.getQualityControlMethods());
        model.setVariables(variables);
        return model;
    }
    
    @Override
    @Transactional
    public void assign(LibraryCreateAssignRequest request, String token)
    {
        // 更新文库创建任务运行时参数
        if (!CollectionUtils.isEmpty(request.getTasks()))
        {
            TestingTask testingTask;
            LibraryCreateTaskVariables variables;
            TaskConfig taskConfig = bcmadapter.getTaskConfigBySemantic(TaskSemantic.LIBRARY_CRE);
            SampleTypeSimpleModel sampleType = bcmadapter.getSampleType(taskConfig.getOutputSampleId());
            Map<String, Integer> derivedIndexContext = new HashMap<String, Integer>();
            
            for (LibraryCreateAssignTaskArgs task : request.getTasks())
            {
                testingTask = testingTaskService.get(task.getId());
                testingTask.setStatus(TestingTask.STATUS_ASSIGNING);
                testingTaskService.modify(testingTask);
                
                variables = testingTaskService.obtainVariables(task.getId(), LibraryCreateTaskVariables.class);
                variables.setTestingCode(task.getTestingCode());
                variables.setSampleInputSize(task.getSampleInputSize());
                variables.setSampleInputVolume(task.getSampleInputVolume());
                variables.setTeInputVolume(task.getTeInputVolume());
                variables.setTestingInputVolume(task.getTestingInputVolume());
                variables.setLibraryIndex(task.getLibraryIndex());
                variables.setOutputSampleTypeId(sampleType.getId());
                variables.setOutputSampleTypeName(sampleType.getName());
                variables.setOutputSampleSize(taskConfig.getOutputVolume());
                variables.setOutputSampleCode(getLibrarySampleCode(testingTask.getInputSample(), derivedIndexContext));
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
    public LibraryCreateSheet getSheet(String id)
    {
        TestingSheet entity = testingSheetService.getSheet(id);
        
        if (null == entity)
        {
            return null;
        }
        
        LibraryCreateSheet sheet = new LibraryCreateSheet();
        sheet.setId(entity.getId());
        sheet.setCode(entity.getCode());
        sheet.setAssignerName(entity.getAssignerName());
        sheet.setAssignTime(entity.getAssignTime());
        sheet.setTesterName(entity.getTesterName());
        sheet.setSubmitTime(entity.getSubmitTime());
        sheet.setDescription(entity.getDescription());
        
        LibraryCreateSheetVariables variables = JsonUtils.asObject(entity.getVariables(), LibraryCreateSheetVariables.class);
        
        if (null != variables)
        {
            String reagentKitId = variables.getCreateReagentKitId();
            ReagentKitSimpleModel reagentKit = bsmadapter.getReagentKit(reagentKitId);
            
            if (null != reagentKit)
            {
                sheet.setReagentKitName(reagentKit.getName());
            }
        }
        
        List<LibraryCreateTask> tasks = wrap(entity.getTestingSheetTaskList());
        
        Collections.sort(tasks, new Comparator<LibraryCreateTask>()
        {
            @Override
            public int compare(LibraryCreateTask o1, LibraryCreateTask o2)
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
        LibraryCreateSheetSubmitContext context = generateSubmitContext(id, token);
        
        // 2、更新文库创建任务结果
        doUpdateTasks(context);
        
        // 3、创建新节点任务
        doCreateNextNodeTasks(context);
        
        // 4、设置检测流程激活节点状态
        doUpdateScheduleActives(context);
        
        // 5、设置任务单提交结果
        doUpdateCreateSheet(context);
        
        // 6、完成DNA任务单待办事项
        doCompleteCreateProcess(context);
        
        // 7、生成文库质检任务单
        doCreateQualityControlSheet(context);
    }
    
    private LibraryCreateSheetSubmitContext generateSubmitContext(String id, String token)
    {
        TestingSheet sheet = testingSheetService.getSheet(id);
        
        if (null == sheet)
        {
            throw new IllegalStateException();
        }
        
        LibraryCreateSheetSubmitContext context = new LibraryCreateSheetSubmitContext();
        context.setSubmiter(smmadapter.getLoginUser(token));
        context.setContextForCreateTestingSheet(sheet);
        
        List<TestingSheetTask> tasks = sheet.getTestingSheetTaskList();
        
        for (TestingSheetTask task : tasks)
        {
            setContextForTask(task, context);
        }
        
        return context;
    }
    
    private void setContextForTask(TestingSheetTask task, LibraryCreateSheetSubmitContext context)
    {
        String id = task.getTestingTaskId();
        TestingTask testingTask = testingTaskService.get(id);
        context.setConextForTestingTaskEntity(testingTask);
        
        LibraryCreateTaskVariables variables = testingTaskService.obtainVariables(id, LibraryCreateTaskVariables.class);
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
    
    private void doUpdateTasks(LibraryCreateSheetSubmitContext context)
    {
        Set<LibraryCreateSheetSubmitTaskContext> records = context.getRelatedTasks();
        
        TestingTask task;
        Date timestamp = new Date();
        TestingTaskResult result;
        TestingSample outputSample;
        LibraryAttributes attributes;
        LibraryCreateTaskResultDetails details;
        
        for (LibraryCreateSheetSubmitTaskContext record : records)
        {
            task = record.getTestingTaskEntity();
            task.setEndType(TestingTask.END_SUCCESS);
            task.setEndTime(timestamp);
            task.setStatus(TestingTask.STATUS_END);
            baseDaoSupport.update(task);
            
            details = new LibraryCreateTaskResultDetails();
            details.setTestingCode(record.getTestingCode());
            details.setOutputSampleCode(record.getOutputSampleCode());
            details.setOutputSampleSize(record.getOutputSampleSize());
            
            result = new TestingTaskResult();
            result.setTaskId(task.getId());
            result.setResult(TestingTaskResult.RESULT_SUCCESS);
            result.setDetails(JsonUtils.asJson(details));
            baseDaoSupport.insert(result);
            
            attributes = new LibraryAttributes();
            attributes.setIndex(record.getLibraryIndex());
            outputSample = new TestingSample();
            outputSample.setParentSample(task.getInputSample());
            outputSample.setReceivedSample(task.getInputSample().getReceivedSample());
            outputSample.setSampleCode(record.getOutputSampleCode());
            outputSample.setSampleTypeId(record.getOutputSampleTypeId());
            outputSample.setSampleTypeName(record.getOutputSampleTypeName());
            outputSample.setAttributes(JsonUtils.asJson(attributes));
            baseDaoSupport.insert(outputSample);
            //删除临时表中的样本编号，保证编号生成的时效性
            baseDaoSupport.execute("delete from TestingSampleTemporary s where s.sampleCode='" + record.getOutputSampleCode() + "'");
            context.setContextForInsertOutputSample(outputSample);
        }
    }
    
    private void doCreateNextNodeTasks(LibraryCreateSheetSubmitContext context)
    {
        Set<LibraryCreateSheetSubmitNextTaskContext> nextTasks = context.getNextNodeTasks();
        
        TestingTask task;
        TestingTaskRunVariable variables;
        Date timestamp = new Date();
        
        for (LibraryCreateSheetSubmitNextTaskContext nextTask : nextTasks)
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
    
    private void doUpdateScheduleActives(LibraryCreateSheetSubmitContext context)
    {
        Set<LibraryCreateSheetSubmitScheduleContext> schedules = context.getRelatedSchedules();
        
        TestingSchedule schedule;
        TestingTask nextTask;
        Date timestamp = new Date();
        
        for (LibraryCreateSheetSubmitScheduleContext scheduleContext : schedules)
        {
            schedule = scheduleContext.getTestingScheduleEntity();
            nextTask = context.getNextTask(scheduleContext.getNextNodeInputSampleCode(), scheduleContext.getNextNodeConfig().getSemantic());
            testingScheduleService.gotoNextNode(schedule, TaskSemantic.LIBRARY_CRE, nextTask, timestamp);
        }
        
        //更新冗余信息
        Collection<LibraryCreateSheetSubmitNextTaskContext> nextTasks = context.getNextTasks().values();
        for (LibraryCreateSheetSubmitNextTaskContext model : nextTasks)
        {
            testingTaskService.updateTaskRedundantColumn(Arrays.asList(model.getTestingTaskEntity()), 0);
        }
        
    }
    
    private void doUpdateCreateSheet(LibraryCreateSheetSubmitContext context)
    {
        TestingSheet sheet = context.getLibraryCreateSheetEntity();
        UserMinimalModel submiter = context.getSubmiter();
        sheet.setSubmiterId(submiter.getId());
        sheet.setSubmiterName(submiter.getName());
        sheet.setSubmitTime(new Date());
        baseDaoSupport.update(sheet);
    }
    
    private void doCompleteCreateProcess(LibraryCreateSheetSubmitContext context)
    {
        TestingSheet sheet = context.getLibraryCreateSheetEntity();
        activitiService.submitTestingSheet(sheet.getId());
    }
    
    private void doCreateQualityControlSheet(LibraryCreateSheetSubmitContext context)
    {
        String semantic = TaskSemantic.LIBRARY_QC;
        TaskConfig config = bcmadapter.getTaskConfigBySemantic(semantic);
        TestingSheet createSheet = context.getLibraryCreateSheetEntity();
        LibraryCreateSheetVariables variables = context.getLibraryCreateSheetVariables();
        UserMinimalModel tester = smmadapter.getUserByID(variables.getQualityControlTesterId());
        
        LibraryQcSheetVariables qcVariables = new LibraryQcSheetVariables();
        qcVariables.setQualityControlMethods(variables.getQualityControlMethods());
        qcVariables.setQualityControlReagentKitId(variables.getQualityControlReagentKitId());
        
        List<String> sheetTasks = new ArrayList<String>();
        Set<LibraryCreateSheetSubmitNextTaskContext> nextNodeTasks = context.getNextNodeTasks();
        
        for (LibraryCreateSheetSubmitNextTaskContext nextNodeTask : nextNodeTasks)
        {
            if (semantic.equals(nextNodeTask.getTaskSemantic()))
            {
                sheetTasks.add(nextNodeTask.getTestingTaskEntity().getId());
            }
        }
        
        Date timestamp = new Date();
        TestingSheetCreateModel model = new TestingSheetCreateModel();
        model.setTaskSemantic(TaskSemantic.LIBRARY_QC);
        model.setTaskName(config.getName());
        String libQcSHeetCode = dnaExtractService.getByDnaExtCode(TaskSemantic.LIBRARY_QC, createSheet.getCode());
        model.setCode(libQcSHeetCode);
        model.setDescription(createSheet.getDescription());
        model.setAssignerId(createSheet.getAssignerId());
        model.setAssignerName(createSheet.getAssignerName());
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
        LibraryQcTaskVariables qcTaskVariables;
        LibraryCreateSheetSubmitNextTaskContext nextTaskContext;
        
        for (TestingSheetTask sheetTask : tasks)
        {
            nextTaskContext = context.getNextTaskConextById(sheetTask.getTestingTaskId());
            task = nextTaskContext.getTestingTaskEntity();
            qcVariablesEntity = nextTaskContext.getTestingTaskVariablesEntity();
            qcTaskVariables = testingTaskService.obtainVariables(task.getId(), LibraryQcTaskVariables.class);
            qcTaskVariables.setTestingCode(nextTaskContext.getTestingCode());
            task.setStatus(TestingTask.STATUS_ASSIGNING);
            task.setTestingInputArgs(JsonUtils.asJson(qcTaskVariables));
            testingTaskService.modify(task);
            
            qcTaskVariables = testingTaskService.obtainVariables(task.getId(), LibraryQcTaskVariables.class);
            qcTaskVariables.setTestingCode(nextTaskContext.getTestingCode());
            qcVariablesEntity.setText(JsonUtils.asJson(qcTaskVariables));
            baseDaoSupport.update(qcVariablesEntity);
        }
        
        activitiService.releaseTestingSheet(sheet);
    }
    
    private List<LibraryCreateTask> wrap(List<TestingSheetTask> records)
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
        List<LibraryCreateTask> tasks = new ArrayList<LibraryCreateTask>();
        
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
    
    private LibraryCreateTask wrap(TestingTask entity, BatchWrapTestingTaskContext context)
    {
        LibraryCreateTask task = new LibraryCreateTask();
        
        // 基础信息
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
        
        // 订单-营销中心、合同编号
        task.setOrderType(entity.getOrderMarketingCenter());
        task.setContractCode(entity.getOrderContractCode());
        
        // 产品
        task.setProducts(entity.getProductName());
        
        // 检测方法相关
        task.setTestingMethod(entity.getTestingMethodName());
        
        task.setSampleCode(entity.getTestingSampleCode());
        task.setSampleTypeName(entity.getTestingSampleType());
        
        task.setOriginalSampleCode(entity.getReceivedSampleCode());
        task.setOriginalSampleName(entity.getReceivedSampleName());
        task.setOriginalSampleTypeId(entity.getReceivedSampleTypeId());
        task.setOriginalSampleTypeName(entity.getReceivedSampleType());
        task.setOriginalSamplingDate(entity.getReceivedSampleSamplingTime());
        task.setPurpose(StringUtils.isEmpty(entity.getReceivedSamplePurpose()) ? "2" : entity.getReceivedSamplePurpose());
        
        task.setSampleTypeId(entity.getTestingSampleTypeId());
        DNAAttributes attributes = JsonUtils.asObject(entity.getTestingSampleAttributes(), DNAAttributes.class);
        
        if (null != attributes)
        {
            task.setConcn(attributes.getConcn());
            task.setVolume(attributes.getVolume());
            task.setRatio2623(attributes.getRatio2623());
            task.setRatio2628(attributes.getRatio2628());
            task.setQualityLevel(attributes.getQualityLevel());
        }
        // 收样样本
        // 任务参数
        LibraryCreateTaskVariables variables = JsonUtils.asObject(entity.getTestingInputArgs(), LibraryCreateTaskVariables.class);
        
        if (null != variables)
        {
            task.setLocation(variables.getLocation());
            task.setTestingCode(variables.getTestingCode());
            task.setSampleInputSize(variables.getSampleInputSize());
            task.setSampleInputVolume(variables.getSampleInputVolume());
            task.setTeInputVolume(variables.getTeInputVolume());
            task.setTestingInputVolume(variables.getTestingInputVolume());
            task.setLibraryIndex(variables.getLibraryIndex());
            task.setOutputSampleCode(variables.getOutputSampleCode());
            task.setOutputSampleSize(variables.getOutputSampleSize());
            task.setRemark(variables.getRemark());
        }
        
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
    
    private void setPlannedFinishDate(TestingTask entity,LibraryCreateTask task)
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
    
    private String getLibrarySampleCode(TestingSample inputSample, Map<String, Integer> derivedIndexContext)
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
        //将产物编号存入临时表，防止编号重复
        TestingSampleTemporary temporatySample = new TestingSampleTemporary();
        temporatySample.setParentSample(inputSample);
        temporatySample.setSampleCode("L" + inputSample.getSampleCode() + code);
        baseDaoSupport.insert(temporatySample);
        return "L" + inputSample.getSampleCode() + code;
    }
    
    @SuppressWarnings("unchecked")
    private void sortLocationMethod(List<LibraryCreateTask> list)
    {
        Collections.sort(list, new Comparator()
        {
            @Override
            public int compare(Object o1, Object o2)
            {
                LibraryCreateTask task1 = (LibraryCreateTask)o1;
                LibraryCreateTask task2 = (LibraryCreateTask)o2;
                if (null != task1.getLocation() && null != task2.getLocation())
                {
                    return task1.getLocation().compareTo(task2.getLocation());
                }
                return 0;
            }
        });
    }
}
