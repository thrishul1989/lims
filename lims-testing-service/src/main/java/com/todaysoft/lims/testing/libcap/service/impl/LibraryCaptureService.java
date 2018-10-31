package com.todaysoft.lims.testing.libcap.service.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Lists;
import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.testing.activiti.service.IActivitiService;
import com.todaysoft.lims.testing.base.entity.Order;
import com.todaysoft.lims.testing.base.entity.OrderPlanTask;
import com.todaysoft.lims.testing.base.entity.Product;
import com.todaysoft.lims.testing.base.entity.ProductProbe;
import com.todaysoft.lims.testing.base.entity.ProductTestingMethod;
import com.todaysoft.lims.testing.base.entity.ReceivedSample;
import com.todaysoft.lims.testing.base.entity.TestingCaptureGroup;
import com.todaysoft.lims.testing.base.entity.TestingCaptureSample;
import com.todaysoft.lims.testing.base.entity.TestingMethod;
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
import com.todaysoft.lims.testing.base.model.BatchWrapTestingTaskContext;
import com.todaysoft.lims.testing.base.model.ProbeSimpleModel;
import com.todaysoft.lims.testing.base.model.ProductProbeConfig;
import com.todaysoft.lims.testing.base.model.ReagentKitSimpleModel;
import com.todaysoft.lims.testing.base.model.SampleTypeSimpleModel;
import com.todaysoft.lims.testing.base.model.TaskConfig;
import com.todaysoft.lims.testing.base.model.TaskSemantic;
import com.todaysoft.lims.testing.base.model.TaskSubmitModel;
import com.todaysoft.lims.testing.base.model.TestingSheetCreateModel;
import com.todaysoft.lims.testing.base.service.ICommonService;
import com.todaysoft.lims.testing.base.service.ITestingScheduleService;
import com.todaysoft.lims.testing.base.service.ITestingSheetService;
import com.todaysoft.lims.testing.base.service.ITestingTaskService;
import com.todaysoft.lims.testing.base.service.adapter.bcm.BCMAdapter;
import com.todaysoft.lims.testing.base.service.adapter.bsm.BSMAdapter;
import com.todaysoft.lims.testing.base.service.adapter.smm.SMMAdapter;
import com.todaysoft.lims.testing.base.service.adapter.smm.UserMinimalModel;
import com.todaysoft.lims.testing.base.utils.JsonUtils;
import com.todaysoft.lims.testing.dnaqc.model.DNAQcTask;
import com.todaysoft.lims.testing.libcap.context.LibraryCaptureSubmitContext;
import com.todaysoft.lims.testing.libcap.context.LibraryCaptureSubmitGroupContext;
import com.todaysoft.lims.testing.libcap.context.LibraryCaptureSubmitNextTaskContext;
import com.todaysoft.lims.testing.libcap.context.LibraryCaptureSubmitScheduleContext;
import com.todaysoft.lims.testing.libcap.context.LibraryCaptureSubmitSolveTaskContext;
import com.todaysoft.lims.testing.libcap.context.LibraryCaptureSubmitTestingTaskContext;
import com.todaysoft.lims.testing.libcap.dao.LibraryCaptureAssignableTaskSearcher;
import com.todaysoft.lims.testing.libcap.model.CaptureLibraryAttributes;
import com.todaysoft.lims.testing.libcap.model.LibraryCaptureAssignArgs;
import com.todaysoft.lims.testing.libcap.model.LibraryCaptureAssignGroupArgs;
import com.todaysoft.lims.testing.libcap.model.LibraryCaptureAssignGroupModel;
import com.todaysoft.lims.testing.libcap.model.LibraryCaptureAssignModel;
import com.todaysoft.lims.testing.libcap.model.LibraryCaptureAssignRequest;
import com.todaysoft.lims.testing.libcap.model.LibraryCaptureAssignTaskArgs;
import com.todaysoft.lims.testing.libcap.model.LibraryCaptureSampleAttributes;
import com.todaysoft.lims.testing.libcap.model.LibraryCaptureSheetGroupModel;
import com.todaysoft.lims.testing.libcap.model.LibraryCaptureSheetModel;
import com.todaysoft.lims.testing.libcap.model.LibraryCaptureSheetSampleModel;
import com.todaysoft.lims.testing.libcap.model.LibraryCaptureSheetTaskModel;
import com.todaysoft.lims.testing.libcap.model.LibraryCaptureSheetVariables;
import com.todaysoft.lims.testing.libcap.model.LibraryCaptureSubmitRequest;
import com.todaysoft.lims.testing.libcap.model.LibraryCaptureTask;
import com.todaysoft.lims.testing.libcap.model.LibraryCaptureTaskVariables;
import com.todaysoft.lims.testing.libcap.service.ILibraryCaptureService;
import com.todaysoft.lims.testing.libcre.model.LibraryAttributes;
import com.todaysoft.lims.testing.libcre.model.LibraryCreateTask;
import com.todaysoft.lims.testing.samplesotck.service.ITestingSheetSampleStorageService;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.StringUtils;

@Service
public class LibraryCaptureService implements ILibraryCaptureService
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
    private ITestingScheduleService testingScheduleService;
    
    @Autowired
    private ICommonService commonService;
    
    @Autowired
    private ITestingSheetSampleStorageService testingSheetSampleStorageService;
    
    @Override
    public List<LibraryCaptureTask> getAssignableList(LibraryCaptureAssignableTaskSearcher searcher)
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
        
        List<LibraryCaptureTask> tasks = new ArrayList<LibraryCaptureTask>();
        
        for (TestingTask record : records)
        {
            tasks.add(wrap(record, new BatchWrapTestingTaskContext()));
        }
        tasks.sort(Comparator.comparing(LibraryCaptureTask::getResubmitCount).reversed()
            .thenComparing(LibraryCaptureTask::getPlannedFinishDate));
        tasks.sort(Comparator.comparing(LibraryCaptureTask::getIfUrgent).reversed());
        return tasks;
    }
    
    @Override
    public LibraryCaptureAssignModel getAssignableModel(LibraryCaptureAssignArgs args)
    {
        LibraryCaptureAssignModel model = new LibraryCaptureAssignModel();
        
        if (null == args || StringUtils.isEmpty(args.getKeys()))
        {
            model.setGroups(Collections.emptyList());
            return model;
        }
        
        Set<String> includeKeys = new HashSet<String>(Arrays.asList(args.getKeys().split(",")));
        LibraryCaptureAssignableTaskSearcher searcher = new LibraryCaptureAssignableTaskSearcher();
        searcher.setIncludeKeys(includeKeys);
        
        List<TestingTask> tasks = baseDaoSupport.find(searcher);
        
        if (CollectionUtils.isEmpty(tasks))
        {
            model.setGroups(Collections.emptyList());
            return model;
        }
        
//        BatchWrapTestingTaskContext wrapContext = testingTaskService.getBatchWrapTestingTaskContext(includeKeys);
        
        String groupKey;
        TestingSchedule schedule;
        LibraryCaptureTask captureTask;
        List<TestingSchedule> schedules;
        List<ProductProbeConfig> probes;
        LibraryCaptureAssignGroupModel group;
        Map<String, LibraryCaptureAssignGroupModel> context = new HashMap<String, LibraryCaptureAssignGroupModel>();
        List<LibraryCaptureAssignGroupModel> groups = new ArrayList<LibraryCaptureAssignGroupModel>();
        
        for (TestingTask task : tasks)
        {
            schedules = testingScheduleService.getRelatedSchedules(task.getId());
            
            if (CollectionUtils.isEmpty(schedules) || schedules.size() > 1)
            {
                throw new IllegalStateException();
            }
            
            schedule = schedules.get(0);
            
            probes = bcmadapter.getProductProbeConfigs(schedule.getProductId(), schedule.getMethodId());
            
            if (CollectionUtils.isEmpty(probes))
            {
                throw new IllegalStateException();
            }
            
            captureTask = wrap(task, new BatchWrapTestingTaskContext());
            
            for (ProductProbeConfig probe : probes)
            {
                groupKey = getUniqueKey(probe);
                group = context.get(groupKey);
                
                if (null == group)
                {
                    group = new LibraryCaptureAssignGroupModel();
                    group.setProbeId(probe.getProbeId());
                    group.setProbeName(probe.getProbeName());
                    group.setProbeDatasize(probe.getProbeDatasize());
                    group.setTasks(new ArrayList<LibraryCaptureTask>());
                    groups.add(group);
                    context.put(groupKey, group);
                }
                //探针组超过20重新分组一个新组
                if (group.getTasks().size() > 19)
                {
                    group = new LibraryCaptureAssignGroupModel();
                    group.setProbeId(probe.getProbeId());
                    group.setProbeName(probe.getProbeName());
                    group.setProbeDatasize(probe.getProbeDatasize());
                    group.setTasks(new ArrayList<LibraryCaptureTask>());
                    groups.add(group);
                    context.put(groupKey, group);
                    group.getTasks().add(captureTask);
                    continue;
                }
                group.getTasks().add(captureTask);
                
            }
        }
        
        //对探针分组内的文库，按照index正序排序
        for (LibraryCaptureAssignGroupModel lcagm : groups)
        {
            if (Collections3.isNotEmpty(lcagm.getTasks()))
            {
                sortIntMethod(lcagm.getTasks());
                //位置排序
                sortLocationMethod(lcagm.getTasks());
            }
        }
        
        model.setGroups(groups);
        return model;
    }
    
    private void sortLocationMethod(List<LibraryCaptureTask> list)
    {
        Collections.sort(list, new Comparator<LibraryCaptureTask>()
        {
            @Override
            public int compare(LibraryCaptureTask o1, LibraryCaptureTask o2)
            {
                if (null != o1.getLocation() && null != o2.getLocation())
                {
                    return o1.getLocation().compareTo(o2.getLocation());
                }
                return 0;
            }
        });
    }
    
    private String getUniqueKey(ProductProbeConfig probe)
    {
        String datasize = new DecimalFormat("0.00").format(null == probe.getProbeDatasize() ? BigDecimal.ZERO : probe.getProbeDatasize());
        return probe.getProbeId() + "_" + datasize;
    }
    
    @Override
    @Transactional
    public void assign(LibraryCaptureAssignRequest request, String token)
    {
        List<String> keys = new ArrayList<String>();
        List<TestingCaptureGroup> groups = new ArrayList<TestingCaptureGroup>();
        Map<String, TestingTask> testingTasks = new HashMap<String, TestingTask>();
        
        if (!CollectionUtils.isEmpty(request.getGroups()))
        {
            TestingTask testingTask;
            TestingCaptureGroup captureGroup;
            TestingCaptureSample captureSample;
            
            for (LibraryCaptureAssignGroupArgs group : request.getGroups())
            {
                if (CollectionUtils.isEmpty(group.getTasks()))
                {
                    continue;
                }
                
                if (StringUtils.isEmpty(group.getTestingCode()))
                {
                    throw new IllegalStateException();
                }
                
                captureGroup = new TestingCaptureGroup();
                captureGroup.setBatchCode(request.getBatchCode());
                captureGroup.setTestingCode(group.getTestingCode());
                captureGroup.setProbeId(group.getProbeId());
                captureGroup.setProbeDatasize(group.getProbeDatasize());
                baseDaoSupport.insert(captureGroup);
                groups.add(captureGroup);
                
                for (LibraryCaptureAssignTaskArgs taskArgs : group.getTasks())
                {
                    testingTask = testingTasks.get(taskArgs.getId());
                    
                    if (null == testingTask)
                    {
                        testingTask = baseDaoSupport.get(TestingTask.class, taskArgs.getId());
                        testingTask.setStatus(TestingTask.STATUS_ASSIGNING);
                        testingTaskService.modify(testingTask);
                        testingTasks.put(taskArgs.getId(), testingTask);
                        keys.add(taskArgs.getId());
                    }
                    
                    captureSample = new TestingCaptureSample();
                    captureSample.setGroup(captureGroup);
                    captureSample.setInputSize(taskArgs.getSampleInputSize());
                    captureSample.setInputVolume(taskArgs.getSampleInputVolume());
                    captureSample.setSample(testingTask.getInputSample());
                    baseDaoSupport.insert(captureSample);
                }
            }
        }
        
        // 创建任务单
        TestingSheetCreateModel model = buildTestingSheetCreateModel(request, token, keys);
        TestingSheet sheet = testingSheetService.create(model);
        
        for (TestingCaptureGroup group : groups)
        {
            group.setSheetId(sheet.getId());
            baseDaoSupport.update(group);
        }
        
        //创建出库单
        testingSheetSampleStorageService.createStorageOut(sheet);
        
        // 启动流程
        activitiService.releaseTestingSheet(sheet);
    }
    
    private TestingSheetCreateModel buildTestingSheetCreateModel(LibraryCaptureAssignRequest request, String token, List<String> keys)
    {
        UserMinimalModel loginer = smmadapter.getLoginUser(token);
        UserMinimalModel tester = smmadapter.getUserByID(request.getTesterId());
        TaskConfig taskConfig = bcmadapter.getTaskConfigBySemantic(TaskSemantic.LIBRARY_CAP);
        
        TestingSheetCreateModel model = new TestingSheetCreateModel();
        model.setCode(commonService.getTaskCodeBySemantic(TaskSemantic.LIBRARY_CAP));
        model.setTaskSemantic(TaskSemantic.LIBRARY_CAP);
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
        model.setTasks(keys);
        
        // 设置文库捕获任务单自定义参数对象
        LibraryCaptureSheetVariables variables = new LibraryCaptureSheetVariables();
        variables.setReagentKitId(request.getReagentKit());
        variables.setMethods(request.getMethods());
        variables.setBatchCode(request.getBatchCode());
        variables.setLibraryInputSize(request.getLibraryInputSize());
        model.setVariables(variables);
        return model;
    }
    
    @Override
    public LibraryCaptureSheetModel getTestingSheet(String id)
    {
        TestingSheet entity = testingSheetService.getSheet(id);
        
        if (null == entity)
        {
            return null;
        }
        
        LibraryCaptureSheetModel sheet = new LibraryCaptureSheetModel();
        sheet.setId(entity.getId());
        sheet.setCode(entity.getCode());
        sheet.setAssignerName(entity.getAssignerName());
        sheet.setAssignTime(entity.getAssignTime());
        sheet.setDescription(entity.getDescription());
        
        LibraryCaptureSheetVariables variables = JsonUtils.asObject(entity.getVariables(), LibraryCaptureSheetVariables.class);
        
        if (null != variables)
        {
            String reagentKitId = variables.getReagentKitId();
            ReagentKitSimpleModel reagentKit = bsmadapter.getReagentKit(reagentKitId);
            
            if (null != reagentKit)
            {
                sheet.setReagentKitName(reagentKit.getName());
            }
            
            sheet.setMethods(variables.getMethods());
            sheet.setBatchCode(variables.getBatchCode());
            sheet.setLibraryInputSize(variables.getLibraryInputSize());
        }
        
        List<TestingCaptureGroup> captureGroups = getCaptureGroups(sheet.getId());
        List<LibraryCaptureSheetGroupModel> groups = wrapForGroups(captureGroups);
        
        Collections.sort(groups, new Comparator<LibraryCaptureSheetGroupModel>()
        {
            @Override
            public int compare(LibraryCaptureSheetGroupModel o1, LibraryCaptureSheetGroupModel o2)
            {
                try
                {
                    String batchCode = sheet.getBatchCode();
                    int i1 = Integer.valueOf(o1.getTestingCode().replaceAll(batchCode + "-", ""));
                    int i2 = Integer.valueOf(o2.getTestingCode().replaceAll(batchCode + "-", ""));
                    return i1 - i2 > 0 ? 1 : (i1 - i2 == 0) ? 0 : -1;
                }
                catch (Exception e)
                {
                    return 0;
                }
            }
        });
        
        sheet.setGroups(groups);
        
        List<LibraryCaptureSheetGroupModel> libraryGroups;
        Map<String, List<LibraryCaptureSheetGroupModel>> libraryGroupMapping = new HashMap<String, List<LibraryCaptureSheetGroupModel>>();
        
        for (LibraryCaptureSheetGroupModel group : groups)
        {
            for (LibraryCaptureSheetSampleModel library : group.getSamples())
            {
                libraryGroups = libraryGroupMapping.get(library.getSampleId());
                
                if (null == libraryGroups)
                {
                    libraryGroups = new ArrayList<LibraryCaptureSheetGroupModel>();
                    libraryGroupMapping.put(library.getSampleId(), libraryGroups);
                }
                
                libraryGroups.add(group);
            }
        }
        
        List<TestingSheetTask> sheetTasks = entity.getTestingSheetTaskList();
        List<LibraryCaptureSheetTaskModel> tasks = wrapForTasks(sheetTasks, libraryGroupMapping);
        sheet.setTasks(tasks);
        return sheet;
    }
    
    @Override
    public List<TestingCaptureGroup> getCaptureGroups(String sheetId)
    {
        String hql = "FROM TestingCaptureGroup g WHERE g.sheetId = :sheetId";
        return baseDaoSupport.findByNamedParam(TestingCaptureGroup.class, hql, new String[] {"sheetId"}, new Object[] {sheetId});
    }
    
    @Override
    @Transactional
    public void submit(LibraryCaptureSubmitRequest request, String token)
    {
        // 1、设置提交上下文数据
        LibraryCaptureSubmitContext context = generateSubmitContext(request, token);
        
        // 2、更新检测任务结果
        doUpdateTestingTasks(context);
        
        // 3、更新捕获任务结果
        doUpdateCaptureGroups(context);
        
        // 4、创建下一节点任务
        doCreateNextNodeTasks(context);
        
        // 5、设置成功样本相关流程激活节点状态
        doUpdateScheduleNextActives(context);
        
        // 6、设置质检不通过样本相关流程激活节点状态
        doUpdateScheduleErrorActives(context);
        
        // 7、设置重新处理的相关流程
        doSolveUnqualifiedSchedules(context);
        
        // 8、设置任务单提交结果
        doUpdateSheet(context);
        
        // 9、完成DNA任务单待办事项
        doCompleteProcess(context);
        
        //存储冗余信息
        
        Set<String> keySet = context.getNextTasksByIds().keySet();
        
        Iterator<String> it = keySet.iterator();
        while (it.hasNext())
        {
            String i = it.next();
            LibraryCaptureSubmitNextTaskContext s = context.getNextTasksByIds().get(i);
            //特殊处理接收样本为空的问题
            TestingSample sample = baseDaoSupport.get(TestingSample.class, s.getTestingTaskEntity().getInputSample().getId());
            s.getTestingTaskEntity().getInputSample().setReceivedSample(sample.getReceivedSample());
            testingTaskService.updateTaskRedundantColumn(Arrays.asList(s.getTestingTaskEntity()), 0);
        }
        
    }
    
    private void doUpdateTestingTasks(LibraryCaptureSubmitContext context)
    {
        Set<LibraryCaptureSubmitTestingTaskContext> records = context.getRelatedTasks();
        
        TaskSubmitModel taskSubmitData;
        
        for (LibraryCaptureSubmitTestingTaskContext record : records)
        {
            taskSubmitData = new TaskSubmitModel();
            taskSubmitData.setEntity(record.getTestingTaskEntity());
            taskSubmitData.setSuccess(record.isQualified());
            taskSubmitData.setFailureRemark(record.getUnqualifiedRemark());
            taskSubmitData.setFailureStrategy(record.getUnqualifiedStrategy());
            testingTaskService.submit(taskSubmitData);
        }
    }
    
    private void doUpdateCaptureGroups(LibraryCaptureSubmitContext context)
    {
        Set<LibraryCaptureSubmitGroupContext> groups = context.getRelatedCaptureGroups();
        
        TestingSample sample;
        TestingSample outputSample;
        TestingCaptureGroup group;
        ProbeSimpleModel probe;
        LibraryAttributes libraryAttributes;
        CaptureLibraryAttributes library;
        Set<CaptureLibraryAttributes> libraries;
        List<TestingCaptureSample> captureSamples;
        LibraryCaptureSampleAttributes attributes;
        Map<String, ProbeSimpleModel> probes = new HashMap<String, ProbeSimpleModel>();
        
        TaskConfig taskConfig = bcmadapter.getTaskConfigBySemantic(TaskSemantic.LIBRARY_CAP);
        SampleTypeSimpleModel outputSampleType = bcmadapter.getSampleType(taskConfig.getOutputSampleId());
        
        for (LibraryCaptureSubmitGroupContext groupContext : groups)
        {
            group = groupContext.getGroup();
            
            if (groupContext.isQualified())
            {
                libraries = new HashSet<CaptureLibraryAttributes>();
                attributes = new LibraryCaptureSampleAttributes();
                attributes.setConcn(groupContext.getConcn());
                attributes.setProbeId(group.getProbeId());
                attributes.setProbeDatasize(group.getProbeDatasize());
                
                probe = probes.get(group.getProbeId());
                
                if (null == probe)
                {
                    probe = bsmadapter.getProbe(group.getProbeId());
                    
                    if (null != probe)
                    {
                        probes.put(group.getProbeId(), probe);
                    }
                }
                
                if (null != probe)
                {
                    attributes.setProbeName(probe.getName());
                }
                
                captureSamples = group.getSamples();
                
                if (!CollectionUtils.isEmpty(captureSamples))
                {
                    for (TestingCaptureSample captureSample : captureSamples)
                    {
                        sample = captureSample.getSample();
                        libraryAttributes = JsonUtils.asObject(sample.getAttributes(), LibraryAttributes.class);
                        library = new CaptureLibraryAttributes();
                        library.setSampleCode(sample.getSampleCode());
                        library.setIndex(libraryAttributes.getIndex());
                        libraries.add(library);
                    }
                }
                
                attributes.setLibraries(libraries);
                
                outputSample = new TestingSample();
                
                if (null != outputSampleType)
                {
                    outputSample.setSampleTypeId(outputSampleType.getId());
                    outputSample.setSampleTypeName(outputSampleType.getName());
                }
                
                outputSample.setSampleCode(group.getTestingCode());
                outputSample.setAttributes(JsonUtils.asJson(attributes));
                baseDaoSupport.insert(outputSample);
                group.setResult(TestingTaskResult.RESULT_SUCCESS);
                context.setContextForCreateCaptureGroupOutputSample(group.getId(), outputSample);
            }
            else
            {
                group.setRemark(groupContext.getUnqualifiedRemark());
                group.setResult("1");
            }
            
            baseDaoSupport.update(group);
        }
    }
    
    private void doCreateNextNodeTasks(LibraryCaptureSubmitContext context)
    {
        Set<LibraryCaptureSubmitNextTaskContext> nextTasks = context.getNextNodeTasks();
        
        TestingTask task;
        TestingTaskRunVariable variables;
        Date timestamp = new Date();
        
        for (LibraryCaptureSubmitNextTaskContext nextTask : nextTasks)
        {
            task = new TestingTask();
            task.setName(nextTask.getTaskName());
            task.setSemantic(nextTask.getTaskSemantic());
            task.setStatus(TestingTask.STATUS_ASSIGNABLE);
            task.setResubmit(false);
            task.setResubmitCount(0);
            task.setStartTime(timestamp);
            task.setInputSample(nextTask.getGroupContext().getOutputSample());
            baseDaoSupport.insert(task);
            
            variables = new TestingTaskRunVariable();
            variables.setTestingTaskId(task.getId());
            baseDaoSupport.insert(variables);
            context.setContextForCreateNextNodeTask(nextTask.getTemporaryId(), task);
        }
    }
    
    private void doUpdateScheduleNextActives(LibraryCaptureSubmitContext context)
    {
        Set<LibraryCaptureSubmitScheduleContext> schedules = context.getGotoNextSchedules();
        
        TestingSchedule schedule;
        Date timestamp = new Date();
        
        List<TestingTask> nextTasks = null;
        
        for (LibraryCaptureSubmitScheduleContext scheduleContext : schedules)
        {
            schedule = scheduleContext.getTestingScheduleEntity();
            
            if (CollectionUtils.isEmpty(scheduleContext.getNextTasks()))
            {
                throw new IllegalStateException();
            }
            
            nextTasks = new ArrayList<TestingTask>();
            
            for (LibraryCaptureSubmitNextTaskContext nextTaskContext : scheduleContext.getNextTasks())
            {
                nextTasks.add(nextTaskContext.getTestingTaskEntity());
            }
            
            testingScheduleService.gotoNextNode(schedule, TaskSemantic.LIBRARY_CAP, nextTasks, timestamp);
        }
        
    }
    
    private void doUpdateScheduleErrorActives(LibraryCaptureSubmitContext context)
    {
        Set<LibraryCaptureSubmitScheduleContext> schedules = context.getGotoErrorSchedules();
        
        TestingSchedule schedule;
        
        for (LibraryCaptureSubmitScheduleContext scheduleContext : schedules)
        {
            schedule = scheduleContext.getTestingScheduleEntity();
            testingScheduleService.gotoError(schedule, TaskSemantic.LIBRARY_CAP);
        }
    }
    
    private void doSolveUnqualifiedSchedules(LibraryCaptureSubmitContext context)
    {
        Set<LibraryCaptureSubmitSolveTaskContext> solveTasks = context.getSolveUnqualifiedTasks();
        
        if (CollectionUtils.isEmpty(solveTasks))
        {
            return;
        }
        
        for (LibraryCaptureSubmitSolveTaskContext solveTaskContext : solveTasks)
        {
            if (CollectionUtils.isEmpty(solveTaskContext.getRelatedSchedules()))
            {
                continue;
            }
            
            for (LibraryCaptureSubmitScheduleContext scheduleContext : solveTaskContext.getRelatedSchedules().values())
            {
                doSolveUnqualifiedSchedule(scheduleContext, solveTaskContext);
            }
        }
    }
    
    private void doSolveUnqualifiedSchedule(LibraryCaptureSubmitScheduleContext scheduleContext, LibraryCaptureSubmitSolveTaskContext solveTaskContext)
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
        testingScheduleService.setScheduleActiveName(scheduleContext.getTestingScheduleEntity());
    }
    
    private void doUpdateSheet(LibraryCaptureSubmitContext context)
    {
        TestingSheet sheet = context.getSheetEntity();
        UserMinimalModel submiter = context.getSubmiter();
        sheet.setSubmiterId(submiter.getId());
        sheet.setSubmiterName(submiter.getName());
        sheet.setSubmitTime(new Date());
        baseDaoSupport.update(sheet);
    }
    
    private void doCompleteProcess(LibraryCaptureSubmitContext context)
    {
        TestingSheet sheet = context.getSheetEntity();
        activitiService.submitTestingSheet(sheet.getId());
    }
    
    private LibraryCaptureSubmitContext generateSubmitContext(LibraryCaptureSubmitRequest request, String token)
    {
        TestingSheet sheet = testingSheetService.getSheet(request.getId());
        
        if (null == sheet)
        {
            throw new IllegalStateException();
        }
        
        LibraryCaptureSubmitContext context = new LibraryCaptureSubmitContext();
        context.setContextForSubmiter(smmadapter.getLoginUser(token));
        context.setContextForTestingSheet(sheet);
        context.setContextForSubmitRequest(request);
        
        List<TestingCaptureGroup> groups = getCaptureGroups(sheet.getId());
        
        for (TestingCaptureGroup group : groups)
        {
            context.setContextForLibraryCaptureGroup(group);
        }
        
        List<TestingSheetTask> tasks = sheet.getTestingSheetTaskList();
        
        for (TestingSheetTask task : tasks)
        {
            setContextForTestingSheetTask(task, context);
        }
        
        return context;
    }
    
    private void setContextForTestingSheetTask(TestingSheetTask task, LibraryCaptureSubmitContext context)
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
    
    private List<LibraryCaptureSheetTaskModel> wrapForTasks(List<TestingSheetTask> sheetTasks, Map<String, List<LibraryCaptureSheetGroupModel>> libraryGroupMapping)
    {
        if (CollectionUtils.isEmpty(sheetTasks))
        {
            return Collections.emptyList();
        }
        
        TestingTask task;
        List<LibraryCaptureSheetTaskModel> tasks = new ArrayList<LibraryCaptureSheetTaskModel>();
        
        for (TestingSheetTask sheetTask : sheetTasks)
        {
            task = baseDaoSupport.get(TestingTask.class, sheetTask.getTestingTaskId());
            tasks.add(wrap(task, libraryGroupMapping));
        }
        
        return tasks;
    }
    
    private List<LibraryCaptureSheetGroupModel> wrapForGroups(List<TestingCaptureGroup> records)
    {
        if (CollectionUtils.isEmpty(records))
        {
            return Collections.emptyList();
        }
        
        LibraryCaptureSheetGroupModel group;
        List<LibraryCaptureSheetGroupModel> groups = new ArrayList<LibraryCaptureSheetGroupModel>();
        
        for (TestingCaptureGroup record : records)
        {
            group = wrapForGroup(record);
            
            if (null != group)
            {
                groups.add(group);
            }
        }
        
        return groups;
    }
    
    private LibraryCaptureSheetGroupModel wrapForGroup(TestingCaptureGroup group)
    {
        ProbeSimpleModel probe = bsmadapter.getProbe(group.getProbeId());
        
        LibraryCaptureSheetGroupModel model = new LibraryCaptureSheetGroupModel();
        model.setId(group.getId());
        model.setTestingCode(group.getTestingCode());
        model.setProbeId(group.getProbeId());
        model.setProbeName(probe.getName());
        model.setProbeDatasize(group.getProbeDatasize());
        
        List<TestingCaptureSample> captureSamples = group.getSamples();
        
        if (CollectionUtils.isEmpty(captureSamples))
        {
            return model;
        }
        
        LibraryCaptureSheetSampleModel sample;
        List<LibraryCaptureSheetSampleModel> samples = new ArrayList<LibraryCaptureSheetSampleModel>();
        
        TestingSample inputSample;
        LibraryAttributes attributes;
        ReceivedSample receivedSample;
        
        for (TestingCaptureSample captureSample : captureSamples)
        {
            sample = new LibraryCaptureSheetSampleModel();
            sample.setId(captureSample.getId());
            
            inputSample = captureSample.getSample();
            
            if (null == inputSample)
            {
                throw new IllegalStateException();
            }
            
            attributes = JsonUtils.asObject(inputSample.getAttributes(), LibraryAttributes.class);
            sample.setSampleId(captureSample.getSample().getId());
            sample.setInputSize(captureSample.getInputSize());
            sample.setInputVolume(captureSample.getInputVolume());
            sample.setSampleTypeId(inputSample.getSampleTypeId());
            sample.setSampleTypeName(inputSample.getSampleTypeName());
            sample.setSampleCode(inputSample.getSampleCode());
            sample.setLibraryIndex(attributes.getIndex());
            sample.setConcn(attributes.getConcn());
            sample.setVolume(attributes.getVolume());
            sample.setRatio2623(attributes.getRatio2623());
            sample.setRatio2628(attributes.getRatio2628());
            sample.setFragmentLengthStart(attributes.getFragmentLengthStart());
            sample.setFragmentLengthEnd(attributes.getFragmentLengthEnd());
            sample.setQualityLevel(attributes.getQualityLevel());
            
            receivedSample = captureSample.getSample().getReceivedSample();
            
            if (null != receivedSample)
            {
                sample.setOriginalSampleCode(receivedSample.getSampleCode());
                sample.setOriginalSampleName(receivedSample.getSampleName());
                sample.setOriginalSampleTypeId(receivedSample.getSampleTypeId());
                sample.setOriginalSampleTypeName(receivedSample.getSampleTypeName());
                sample.setOriginalSamplingDate(receivedSample.getSamplingDate());
            }
            
            samples.add(sample);
        }
        
        model.setSamples(samples);
        return model;
    }
    
    private LibraryCaptureTask wrap(TestingTask entity, BatchWrapTestingTaskContext context)
    {
        String id = entity.getId();
        LibraryCaptureTask task = new LibraryCaptureTask();
        
        // 基础信息
        task.setId(id);
        task.setStartTime(entity.getStartTime());
        task.setResubmit(entity.isResubmit());
        task.setResubmitCount(entity.getResubmitCount());
        
        // 订单-营销中心、合同编号
        task.setOrderType(entity.getOrderMarketingCenter());
        task.setOrderCode(entity.getOrderCode());
        task.setContractCode(entity.getOrderContractCode());
        task.setSampleCode(entity.getTestingSampleCode());
        task.setSampleTypeName(entity.getTestingSampleType());
        
        task.setOriginalSampleCode(entity.getReceivedSampleCode());
        task.setOriginalSampleName(entity.getReceivedSampleName());
        task.setOriginalSampleTypeId(entity.getReceivedSampleTypeId());
        task.setOriginalSampleTypeName(entity.getReceivedSampleType());
        task.setOriginalSamplingDate(entity.getReceivedSampleSamplingTime());
        task.setPurpose(StringUtils.isEmpty(entity.getReceivedSamplePurpose()) ? "2" : entity.getReceivedSamplePurpose());
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
        
        // 产品
        task.setProducts(entity.getProductName());
        
        task.setSampleTypeId(entity.getTestingSampleTypeId());
        
        LibraryAttributes attributes = JsonUtils.asObject(entity.getTestingSampleAttributes(), LibraryAttributes.class);
        
        if (null != attributes)
        {
            task.setLibraryIndex(attributes.getIndex());
            task.setConcn(attributes.getConcn());
            task.setVolume(attributes.getVolume());
            task.setRatio2623(attributes.getRatio2623());
            task.setRatio2628(attributes.getRatio2628());
            task.setQualityLevel(attributes.getQualityLevel());
            task.setFragmentLengthStart(attributes.getFragmentLengthStart());
            task.setFragmentLengthEnd(attributes.getFragmentLengthEnd());
        }
        
        // 实验样本
        
        // 收样样本
        
        // 任务参数
        LibraryCaptureTaskVariables variables = JsonUtils.asObject(entity.getTestingInputArgs(), LibraryCaptureTaskVariables.class);
        
        if (null != variables)
        {
            task.setRemark(variables.getRemark());
        }
        
        // 探针、数据量
        List<Product> products = testingTaskService.getRelatedProducts(id);
        List<TestingMethod> tMethod = testingTaskService.getRelatedTestingMethods(entity.getId());
        BigDecimal probeDatasize = new BigDecimal(0);
        for (Product p : products)
        {
            for (TestingMethod t : tMethod)
            {
                List<ProductProbeConfig> probes = bcmadapter.getProductProbeConfigs(p.getId(), t.getId());
                for (ProductProbeConfig por : probes)
                {
                    probeDatasize = probeDatasize.add(por.getProbeDatasize());
                }
            }
        }
        
        //获取任务关联探针
        List<TestingSchedule> shedules = testingScheduleService.getRelatedSchedules(entity.getId());
        if (Collections3.isNotEmpty(shedules))
        {
            Product product = baseDaoSupport.get(Product.class, shedules.get(0).getProductId());
            for (ProductTestingMethod ptm : product.getProductTestingMethodList())
            {
                if (ptm.getTestingMethod().getId().equals(shedules.get(0).getMethodId()))
                {
                    List<String> list = new ArrayList<String>();
                    for (ProductProbe productProbe : ptm.getProductProbe())
                    {
                        list.add(productProbe.getProbe().getName());
                    }
                    task.setProbes(StringUtils.join(list.toArray(), ","));
                    break;
                }
            }
        }
        
        task.setProbeDataSize(probeDatasize);
        
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
    
    private void setPlannedFinishDate(TestingTask entity,LibraryCaptureTask task)
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
    private LibraryCaptureSheetTaskModel wrap(TestingTask entity, Map<String, List<LibraryCaptureSheetGroupModel>> libraryGroupMapping)
    {
        Order order = testingTaskService.getRelatedOrder(entity.getId());
        List<Product> products = testingTaskService.getRelatedProducts(entity.getId());
        
        LibraryCaptureSheetTaskModel task = new LibraryCaptureSheetTaskModel();
        task.setId(entity.getId());
        
        if (null != order && null != order.getType())
        {
            task.setOrderType(order.getType().getName());
        }
        
        if (null != entity.getInputSample())
        {
            TestingSample inputSample = entity.getInputSample();
            task.setSampleCode(inputSample.getSampleCode());
            
            if (null != inputSample.getReceivedSample())
            {
                ReceivedSample receivedSample = inputSample.getReceivedSample();
                task.setOriginalSampleCode(receivedSample.getSampleCode());
                task.setOriginalSampleName(receivedSample.getSampleName());
                task.setOriginalSampleTypeName(receivedSample.getSampleTypeName());
            }
        }
        
        if (!CollectionUtils.isEmpty(products))
        {
            StringBuffer buf = new StringBuffer(128);
            
            for (Product product : products)
            {
                if (buf.length() > 0)
                {
                    buf.append("、");
                }
                
                buf.append(product.getName());
            }
            
            task.setProducts(buf.toString());
        }
        
        List<LibraryCaptureSheetGroupModel> assignedGroups = libraryGroupMapping.get(entity.getInputSample().getId());
        task.setAssignedGroups(assignedGroups);
        return task;
    }
    
    @Override
    public boolean validateBatchCode(String batchCode)
    {
        NamedQueryer queryer =
            NamedQueryer.builder()
                .hql("FROM TestingCaptureGroup t WHERE t.batchCode = :batchCode")
                .names(Lists.newArrayList("batchCode"))
                .values(Lists.newArrayList(batchCode))
                .build();
        List<TestingCaptureGroup> records = baseDaoSupport.find(queryer, TestingCaptureGroup.class);
        return records.isEmpty();
    }
    
    @SuppressWarnings("unchecked")
    private void sortIntMethod(List<LibraryCaptureTask> list)
    {
        Collections.sort(list, new Comparator()
        {
            @Override
            public int compare(Object o1, Object o2)
            {
                LibraryCaptureTask task1 = (LibraryCaptureTask)o1;
                LibraryCaptureTask task2 = (LibraryCaptureTask)o2;
                int index1 = 0;
                int index2 = 0;
                if (StringUtils.isNotEmpty(task1.getLibraryIndex()))
                {
                    index1 = Integer.parseInt(task1.getLibraryIndex());
                }
                if (StringUtils.isNotEmpty(task2.getLibraryIndex()))
                {
                    index2 = Integer.parseInt(task2.getLibraryIndex());
                }
                if (index1 > index2)
                {
                    return 1;
                }
                else if (index1 == index2)
                {
                    return 0;
                }
                else
                {
                    return -1;
                }
            }
        });
    }
}
