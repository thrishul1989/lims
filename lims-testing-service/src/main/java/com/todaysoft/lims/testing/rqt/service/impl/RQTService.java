package com.todaysoft.lims.testing.rqt.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.testing.activiti.service.IActivitiService;
import com.todaysoft.lims.testing.base.entity.Product;
import com.todaysoft.lims.testing.base.entity.TestingMethod;
import com.todaysoft.lims.testing.base.entity.TestingSample;
import com.todaysoft.lims.testing.base.entity.TestingSampleStorage;
import com.todaysoft.lims.testing.base.entity.TestingSchedule;
import com.todaysoft.lims.testing.base.entity.TestingSheet;
import com.todaysoft.lims.testing.base.entity.TestingSheetTask;
import com.todaysoft.lims.testing.base.entity.TestingTask;
import com.todaysoft.lims.testing.base.entity.TestingTaskResult;
import com.todaysoft.lims.testing.base.entity.TestingTaskRunVariable;
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
import com.todaysoft.lims.testing.base.service.TestingCodeComparator;
import com.todaysoft.lims.testing.base.service.adapter.bcm.BCMAdapter;
import com.todaysoft.lims.testing.base.service.adapter.bsm.BSMAdapter;
import com.todaysoft.lims.testing.base.service.adapter.smm.SMMAdapter;
import com.todaysoft.lims.testing.base.service.adapter.smm.UserMinimalModel;
import com.todaysoft.lims.testing.base.utils.JsonUtils;
import com.todaysoft.lims.testing.libcap.model.CaptureLibraryAttributes;
import com.todaysoft.lims.testing.libcap.model.LibraryCaptureSampleAttributes;
import com.todaysoft.lims.testing.libcre.model.LibraryAttributes;
import com.todaysoft.lims.testing.multipcr.model.MultipcrAssignModel;
import com.todaysoft.lims.testing.pooling.model.PoolingSheetVariables;
import com.todaysoft.lims.testing.rqt.context.RQTSubmitContext;
import com.todaysoft.lims.testing.rqt.context.RQTSubmitNextTaskContext;
import com.todaysoft.lims.testing.rqt.context.RQTSubmitScheduleContext;
import com.todaysoft.lims.testing.rqt.context.RQTSubmitTaskContext;
import com.todaysoft.lims.testing.rqt.dao.RQTAssignableTaskSearcher;
import com.todaysoft.lims.testing.rqt.model.RQTAssignArgs;
import com.todaysoft.lims.testing.rqt.model.RQTAssignModel;
import com.todaysoft.lims.testing.rqt.model.RQTAssignRequest;
import com.todaysoft.lims.testing.rqt.model.RQTAssignTaskArgs;
import com.todaysoft.lims.testing.rqt.model.RQTSheetModel;
import com.todaysoft.lims.testing.rqt.model.RQTSheetVariables;
import com.todaysoft.lims.testing.rqt.model.RQTSubmitRequest;
import com.todaysoft.lims.testing.rqt.model.RQTTask;
import com.todaysoft.lims.testing.rqt.model.RQTTaskResultDetails;
import com.todaysoft.lims.testing.rqt.model.RQTTaskVariables;
import com.todaysoft.lims.testing.rqt.service.IRQTService;
import com.todaysoft.lims.testing.samplesotck.service.ITestingSheetSampleStorageService;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.StringUtils;

@Service
public class RQTService implements IRQTService
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
    private ITestingSheetSampleStorageService testingSheetSampleStorageService;
    
    @Autowired
    private ITestingScheduleService testingScheduleService;

    private static Logger log = LoggerFactory.getLogger(RQTService.class);
    
    @Override
    public List<RQTTask> getAssignableList(RQTAssignableTaskSearcher searcher)
    {
        List<TestingTask> records = baseDaoSupport.find(searcher);
        
        if (CollectionUtils.isEmpty(records))
        {
            return Collections.emptyList();
        }
        
        List<RQTTask> tasks = new ArrayList<RQTTask>();
        
        for (TestingTask record : records)
        {
            tasks.add(wrap(record));
        }
        
        TaskConfig taskConfig = bcmadapter.getTaskConfigBySemantic(TaskSemantic.PCR_NGS);
        
        SampleTypeSimpleModel outputSampleType = bcmadapter.getSampleType(taskConfig.getOutputSampleId());
        
        BigDecimal pcrNgsDataSize = bcmadapter.getPcrNgsRqtDataSize();
        
        BigDecimal pcrNgsCapVol = bcmadapter.getPcrNgsRqtCapVol();
        
        RQTTask task;
        String testingCode;
        
        if (!CollectionUtils.isEmpty(tasks))
        {
            for (int i = 0; i < tasks.size(); i++)
            {
                task = tasks.get(i);
                testingCode = commonService.getDNAExtractCode(i + 1);
                tasks.get(i).setTestingCode(testingCode);
                
                if (task.isCaptureSample())
                {
                    if (outputSampleType.getId().equals(task.getSampleTypeId()))
                    {
                        //计算出样本个数 数据量除以单个
                        int sampleCount = 0;
                        if (null != task.getProbeDatasize())
                        {
                            BigDecimal num = task.getProbeDatasize().divide(pcrNgsDataSize).setScale(0, BigDecimal.ROUND_HALF_UP);
                            sampleCount = num.intValue();
                        }
                        task.setSampleInputVolume(pcrNgsCapVol.multiply(new BigDecimal(sampleCount)));
                    }
                    else
                    {
                        
                        BigDecimal singleDatasize = null == task.getProbeDatasize() ? BigDecimal.ZERO : task.getProbeDatasize();
                        int sampleCount = CollectionUtils.isEmpty(task.getLibraries()) ? 0 : task.getLibraries().size();
                        if (null == task.getSampleInputVolume())
                        {
                            task.setSampleInputVolume(bcmadapter.getCaptureSampleInputVolumeForRQT());
                        }
                        
                        //                        task.setTotalDatasize(singleDatasize.multiply(BigDecimal.valueOf(sampleCount)));
                        
                    }
                }
                else
                {
                    task.setSampleInputVolume(bcmadapter.getLibrarySampleInputVolumeForRQT());
                    task.setTotalDatasize(getLibraryDatasize(task.getId()));
                }
                if (null != task.getConcn())
                {
                    task.setPoolingConcn(getPoolingConcn(task.getConcn()));
                    task.setTeInputVolume(getTeInputVolume(task.getConcn(), task.getSampleInputVolume(), task.getPoolingConcn()));
                }
            }
        }
        return tasks;
    }
    
    @Override
    public RQTAssignModel getAssignableModel(RQTAssignArgs args)
    {
        RQTAssignModel model = new RQTAssignModel();
        
        TaskConfig taskConfig = bcmadapter.getTaskConfigBySemantic(TaskSemantic.PCR_NGS);
        
        SampleTypeSimpleModel outputSampleType = bcmadapter.getSampleType(taskConfig.getOutputSampleId());
        
        BigDecimal pcrNgsDataSize = bcmadapter.getPcrNgsRqtDataSize();
        
        BigDecimal pcrNgsCapVol = bcmadapter.getPcrNgsRqtCapVol();
        
        if (null == args || StringUtils.isEmpty(args.getKeys()))
        {
            model.setTasks(Collections.emptyList());
            return model;
        }
        
        Set<String> includeKeys = new HashSet<String>(Arrays.asList(args.getKeys().split(",")));
        RQTAssignableTaskSearcher searcher = new RQTAssignableTaskSearcher();
        searcher.setIncludeKeys(includeKeys);
        List<RQTTask> tasks = getAssignableList(searcher);
        
        RQTTask task;
        String testingCode;
        
        if (!CollectionUtils.isEmpty(tasks))
        {
            for (int i = 0; i < tasks.size(); i++)
            {
                task = tasks.get(i);
                if (task.isCaptureSample())
                {
                    if (outputSampleType.getId().equals(task.getSampleTypeId()))
                    {
                        //计算出样本个数 数据量除以单个
                        int sampleCount = 0;
                        if (null != task.getProbeDatasize())
                        {
                            BigDecimal num = task.getProbeDatasize().divide(pcrNgsDataSize).setScale(0, BigDecimal.ROUND_HALF_UP);
                            sampleCount = num.intValue();
                        }
                        task.setSampleInputVolume(pcrNgsCapVol.multiply(new BigDecimal(sampleCount)));
                    }
                    else
                    {
                        
                        BigDecimal singleDatasize = null == task.getProbeDatasize() ? BigDecimal.ZERO : task.getProbeDatasize();
                        int sampleCount = CollectionUtils.isEmpty(task.getLibraries()) ? 0 : task.getLibraries().size();
                        
                        //                        task.setTotalDatasize(singleDatasize.multiply(BigDecimal.valueOf(sampleCount)));
                        
                    }
                    
                }
                else
                {
                    
                    task.setSampleInputVolume(bcmadapter.getLibrarySampleInputVolumeForRQT());
                    task.setTotalDatasize(getLibraryDatasize(task.getId()));
                    
                }
                
                task.setPoolingConcn(getPoolingConcn(task.getConcn()));
                task.setTeInputVolume(getTeInputVolume(task.getConcn(), task.getSampleInputVolume(), task.getPoolingConcn()));
            }
        }
        //位置排序
        sortLocationMethod(tasks);
        for(int i = 0; i < tasks.size(); i++)
        {
            testingCode = commonService.getDNAExtractCode(i + 1);
            tasks.get(i).setTestingCode(testingCode);
        }
        model.setTasks(tasks);
        return model;
    }
    
    private void sortLocationMethod(List<RQTTask> list)
    {
        Collections.sort(list,new Comparator<RQTTask>()
        {

            @Override
            public int compare(RQTTask o1, RQTTask o2)
            {
                if(StringUtils.isEmpty(o2.getLocation()) && StringUtils.isNotEmpty(o1.getLocation()))
                {
                    return -1;
                }
                if(StringUtils.isNotEmpty(o1.getLocation()) && StringUtils.isNotEmpty(o2.getLocation()))
                {
                    return o1.getLocation().compareTo(o2.getLocation());
                }
                return 0;
            }
        });
    }
    
    @Override
    @Transactional
    public void assign(RQTAssignRequest request, String token)
    {
        int isCreateStorage = 0;
        if (!CollectionUtils.isEmpty(request.getTasks()))
        {
            TestingTask testingTask;
            RQTTaskVariables variables;

            for (RQTAssignTaskArgs task : request.getTasks())
            {
                testingTask = testingTaskService.get(task.getId());
                testingTask.setStatus(TestingTask.STATUS_ASSIGNING);
                testingTaskService.modify(testingTask);
                
                variables = getTaskRunningVariables(task.getId());
                variables.setTestingCode(task.getTestingCode());
                variables.setPoolingConcn(task.getPoolingConcn());
                variables.setSampleInputVolume(task.getSampleInputVolume());
                variables.setTeInputVolume(task.getTeInputVolume());
                variables.setTestingDatasize(task.getTestingDatasize());
                testingTaskService.updateVariables(task.getId(), variables);
                
                //更新冗余信息
                testingTaskService.updateTaskRedundantColumn(Arrays.asList(testingTask), 1);

                //如果有一个是NGS且是文库的就入库
                if("文库".equals(testingTask.getTestingSampleType()) && "NGS".equals(testingTask.getTestingMethodName()))
                {
                    isCreateStorage++;
                }
            }
        }
        
        // 创建任务单
        TestingSheetCreateModel model = buildTestingSheetCreateModel(request, token);
        TestingSheet sheet = testingSheetService.create(model);

        //创建出库单  如果样本都不是文库 则不需要创建入库单  如果有一个文库就创建出库单，到时候过滤非文库的样本
        if(0 < isCreateStorage)
        {
            testingSheetSampleStorageService.createStorageOut(sheet);
        }

        // 启动流程
        activitiService.releaseTestingSheet(sheet);
    }
    
    @Override
    public RQTTaskVariables getTaskRunningVariables(String taskId)
    {
        String variables = testingTaskService.getVariables(taskId);
        
        if (StringUtils.isEmpty(variables))
        {
            return new RQTTaskVariables();
        }
        
        return JsonUtils.asObject(variables, RQTTaskVariables.class);
    }
    
    @Override
    public RQTTaskResultDetails getTaskResultDetails(String taskId)
    {
        TestingTaskResult result = baseDaoSupport.get(TestingTaskResult.class, taskId);
        
        if (null == result || StringUtils.isEmpty(result.getDetails()))
        {
            return new RQTTaskResultDetails();
        }
        
        return JsonUtils.asObject(result.getDetails(), RQTTaskResultDetails.class);
    }
    
    private TestingSheetCreateModel buildTestingSheetCreateModel(RQTAssignRequest request, String token)
    {
        UserMinimalModel loginer = smmadapter.getLoginUser(token);
        UserMinimalModel tester = smmadapter.getUserByID(request.getTesterId());
        TaskConfig taskConfig = bcmadapter.getTaskConfigBySemantic(TaskSemantic.RQT);
        
        TestingSheetCreateModel model = new TestingSheetCreateModel();
        model.setCode(commonService.getTaskCodeBySemantic(TaskSemantic.RQT));
        model.setTaskSemantic(TaskSemantic.RQT);
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
            
            for (RQTAssignTaskArgs task : request.getTasks())
            {
                keys.add(task.getId());
            }
            
            model.setTasks(keys);
        }
        
        // 设置文库捕获任务单自定义参数对象
        RQTSheetVariables variables = new RQTSheetVariables();
        variables.setReagentKitId(request.getReagentKitId());
        variables.setDesignDatasize(request.getDesignDatasize());
        model.setVariables(variables);
        return model;
    }
    
    @Override
    public RQTSheetModel getTestingSheet(String id)
    {
        TestingSheet entity = testingSheetService.getSheet(id);
        
        if (null == entity)
        {
            return null;
        }
        
        RQTSheetModel sheet = new RQTSheetModel();
        sheet.setId(entity.getId());
        sheet.setCode(entity.getCode());
        sheet.setAssignerName(entity.getAssignerName());
        sheet.setAssignTime(entity.getAssignTime());
        sheet.setDescription(entity.getDescription());
        
        RQTSheetVariables variables = JsonUtils.asObject(entity.getVariables(), RQTSheetVariables.class);
        
        if (null != variables)
        {
            String reagentKitId = variables.getReagentKitId();
            ReagentKitSimpleModel reagentKit = bsmadapter.getReagentKit(reagentKitId);
            
            if (null != reagentKit)
            {
                sheet.setReagentKitName(reagentKit.getName());
            }
            
            sheet.setDesignDatasize(variables.getDesignDatasize());
        }
        
        List<RQTTask> tasks = wrapForTesting(entity.getTestingSheetTaskList());
        
        Collections.sort(tasks, new Comparator<RQTTask>()
        {
            @Override
            public int compare(RQTTask o1, RQTTask o2)
            {
                return new TestingCodeComparator().compare(o1.getTestingCode(), o2.getTestingCode());
            }
        });
        
        sheet.setTasks(tasks);
        return sheet;
    }
    
    @Override
    @Transactional
    public void submit(RQTSubmitRequest request, String token)
    {
        // 1、设置提交上下文数据
        RQTSubmitContext context = generateSubmitContext(request, token);
        
        // 2、更新检测任务结果
        doUpdateTestingTasks(context);
        
        // 3、创建下一步任务
        doCreateNextNodeTasks(context);
        
        // 4、更新流程
        doUpdateSchedules(context);
        
        // 3、设置任务单提交结果
        doUpdateRQTSheet(context);
        
        // 4、完成DNA任务单待办事项
        doCompleteProcess(context);
        
        // 5、创建混样任务单
        doCreatePoolingSheet(context);
    }
    
    private RQTSubmitContext generateSubmitContext(RQTSubmitRequest request, String token)
    {
        TestingSheet sheet = testingSheetService.getSheet(request.getId());
        
        if (null == sheet)
        {
            throw new IllegalStateException();
        }
        
        RQTSheetVariables variables = JsonUtils.asObject(sheet.getVariables(), RQTSheetVariables.class);
        
        RQTSubmitContext context = new RQTSubmitContext();
        context.setContextForSubmiter(smmadapter.getLoginUser(token));
        context.setContextForTestingSheet(sheet);
        context.setContextForTestingSheetVariables(variables);
        context.setContextForTaskArgs(request.getTasks());
        
        for (TestingSheetTask task : sheet.getTestingSheetTaskList())
        {
            setContextForTask(task, context);
        }
        
        return context;
    }
    
    private void setContextForTask(TestingSheetTask task, RQTSubmitContext context)
    {
        TestingTask testingTask = testingTaskService.get(task.getTestingTaskId());
        RQTTaskVariables testingTaskVariables = getTaskRunningVariables(task.getTestingTaskId());
        context.setContextForTestingTask(testingTask);
        context.setContextForTestingTaskVariables(task.getTestingTaskId(), testingTaskVariables);
        
        TaskConfig scheduleNextNodeConfig;
        List<TestingSchedule> schedules = testingScheduleService.getRelatedSchedules(testingTask.getId());
        
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
    
    private void doUpdateTestingTasks(RQTSubmitContext context)
    {
        Set<RQTSubmitTaskContext> records = context.getRelatedTasks();
        
        RQTTaskResultDetails details;
        TaskSubmitModel taskSubmitData;
        
        for (RQTSubmitTaskContext record : records)
        {
            details = new RQTTaskResultDetails();
            details.setCtv(record.getCtv());
            details.setTestingCode(record.getTestingCode());
            details.setPoolingConcn(record.getPoolingConcn());
            details.setSampleInputVolume(record.getSampleInputVolume());
            details.setTeInputVolume(record.getTeInputVolume());
            details.setTestingDatasize(record.getTestingDatasize());
            
            taskSubmitData = new TaskSubmitModel();
            taskSubmitData.setEntity(record.getTestingTaskEntity());
            taskSubmitData.setSuccess(true);
            taskSubmitData.setDetails(details);
            testingTaskService.submit(taskSubmitData);
        }
    }
    
    private void doCreateNextNodeTasks(RQTSubmitContext context)
    {
        Set<RQTSubmitNextTaskContext> nextTasks = context.getNextNodeTasks();
        
        TestingTask task;
        TestingTaskRunVariable variables;
        TestingSample inputSample;
        Date timestamp = new Date();
        
        for (RQTSubmitNextTaskContext nextTask : nextTasks)
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
            
            if (!CollectionUtils.isEmpty(nextTask.getVariables()))
            {
                variables.setText(JsonUtils.asJson(nextTask.getVariables()));
            }
            
            baseDaoSupport.insert(variables);
            context.setContextForCreateNextNodeTask(nextTask.getTemporaryId(), task);
            
        }
    }
    
    private void doUpdateSchedules(RQTSubmitContext context)
    {
        Set<RQTSubmitScheduleContext> schedules = context.getRelatedSchedules();
        
        TestingSchedule schedule;
        List<TestingTask> nextTasks;
        Date timestamp = new Date();
        
        for (RQTSubmitScheduleContext scheduleContext : schedules)
        {
            nextTasks = new ArrayList<TestingTask>();
            
            if (!CollectionUtils.isEmpty(scheduleContext.getNextTasks()))
            {
                for (RQTSubmitNextTaskContext nextTaskContext : scheduleContext.getNextTasks())
                {
                    nextTasks.add(nextTaskContext.getTestingTaskEntity());
                }
            }
            
            schedule = scheduleContext.getTestingScheduleEntity();
            testingScheduleService.gotoNextNode(schedule, TaskSemantic.RQT, nextTasks, timestamp);
            
            //存储冗余信息
            if (!CollectionUtils.isEmpty(scheduleContext.getNextTasks()))
            {
                for (RQTSubmitNextTaskContext nextTaskContext : scheduleContext.getNextTasks())
                {
                    testingTaskService.updateTaskRedundantColumn(Arrays.asList(nextTaskContext.getTestingTaskEntity()), 0);
                }
            }
            
        }
    }
    
    private void doUpdateRQTSheet(RQTSubmitContext context)
    {
        TestingSheet sheet = context.getSheetEntity();
        UserMinimalModel submiter = context.getSubmiter();
        sheet.setSubmiterId(submiter.getId());
        sheet.setSubmiterName(submiter.getName());
        sheet.setSubmitTime(new Date());
        baseDaoSupport.update(sheet);
    }
    
    private void doCompleteProcess(RQTSubmitContext context)
    {
        TestingSheet sheet = context.getSheetEntity();
        activitiService.submitTestingSheet(sheet.getId());
    }
    
    private void doCreatePoolingSheet(RQTSubmitContext context)
    {
        PoolingSheetVariables variables = new PoolingSheetVariables();
        variables.setRqtsheetCode(context.getSheetEntity().getCode());
        variables.setDesignDatasize(context.getSheetVariables().getDesignDatasize());
        
        Set<RQTSubmitNextTaskContext> nextTasks = context.getNextNodeTasks();
        
        List<String> sheetTasks = new ArrayList<String>();
        
        for (RQTSubmitNextTaskContext nextTask : nextTasks)
        {
            sheetTasks.add(nextTask.getTestingTaskEntity().getId());
        }
        
        TaskConfig config = bcmadapter.getTaskConfigBySemantic(TaskSemantic.POOLING);
        
        TestingSheetCreateModel model = new TestingSheetCreateModel();
        model.setTaskSemantic(TaskSemantic.POOLING);
        model.setTaskName(config.getName());
        model.setCode(commonService.getTaskCodeBySemantic(TaskSemantic.POOLING));
        model.setVariables(variables);
        model.setTasks(sheetTasks);
        model.setCreateTime(new Date());
        testingSheetService.create(model);
    }
    
    @Override
    public BigDecimal getPoolingConcn(BigDecimal concn)
    {
        if (null == concn)
        {
            throw new IllegalArgumentException();
        }
        
        BigDecimal high = BigDecimal.valueOf(1.5D);
        BigDecimal normal = BigDecimal.valueOf(1D);
        return concn.compareTo(high) >= 0 ? high : (concn.compareTo(normal) >= 0 ? normal : concn);
    }
    
    @Override
    public BigDecimal getLibraryDatasize(String taskId)
    {
        //        List<Product> products = testingTaskService.getRelatedProducts(taskId);
        //        
        //        if (CollectionUtils.isEmpty(products) || products.size() > 1)
        //        {
        //            throw new IllegalStateException();
        //        }
        //        
        //        List<TestingMethod> methods = testingTaskService.getRelatedTestingMethods(taskId);
        //        
        //        if (CollectionUtils.isEmpty(methods) || methods.size() > 1)
        //        {
        //            throw new IllegalStateException();
        //        }
        //        
        //        return bcmadapter.getTestingDatasize(products.get(0).getId(), methods.get(0).getId());
        TestingTask task = baseDaoSupport.get(TestingTask.class, taskId);
        return new BigDecimal(null == task.getTestingCaptureDataSize() ? "0" : task.getTestingCaptureDataSize());
    }
    
    @Override
    public BigDecimal getTeInputVolume(BigDecimal sampleConcn, BigDecimal sampleInputVolume, BigDecimal poolingConcn)
    {
        BigDecimal result = new BigDecimal(0);
        if (null == sampleConcn || null == sampleInputVolume || null == poolingConcn)
        {
            throw new IllegalArgumentException();
        }
        if(result.compareTo(poolingConcn)==0)
        {
            log.error(" poolingConcn is zero {}.",poolingConcn.toString());
            return result;
        }
        return (sampleInputVolume.divide(poolingConcn, 10, BigDecimal.ROUND_CEILING).multiply(sampleConcn)).subtract(sampleInputVolume);
    }
    
    @Override
    public Map<String, List<String>> validateIndex(String ids)
    {
        String[] idArray = ids.split(",");
        List<String> libraryCodeList = Lists.newArrayList();
        List<String> indexList = Lists.newArrayList();
        List<String> refList = Lists.newArrayList();//作为是否重复参考的第一条记录集合
        List<String> repeatList = Lists.newArrayList();//和前面记录有重复的记录集合
        
        Map<String, List<String>> colorGroup = new HashMap<String, List<String>>();
        List<String> colors = Lists.newArrayList("#FFC1C1", "#FFA54F", "#FFBBFF", "#EECFA1", "#EEEED1", "#F08080", "#E0EEE0", "#CDB38B", "#BFEFFF", "#7CCD7C");
        int colorIndex = 0;
        
        //获取到refList和repeatList，是否重复判断标准是：文库编号不同，index相同
        for (int i = 0; i < idArray.length; i++)
        {
            TestingTask entity = testingTaskService.get(idArray[i]);
            if (null != entity)
            {
                TestingSample inputSample = entity.getInputSample();
                //判断是否是捕获产物
                if (isCaptureSample(inputSample))
                {
                    LibraryCaptureSampleAttributes attributes = JsonUtils.asObject(inputSample.getAttributes(), LibraryCaptureSampleAttributes.class);
                    
                    if (null != attributes)
                    {
                        if (!CollectionUtils.isEmpty(attributes.getLibraries()))
                        {
                            for (CaptureLibraryAttributes library : attributes.getLibraries())
                            {
                                if (Collections3.isNotEmpty(libraryCodeList) && Collections3.isNotEmpty(indexList))
                                {
                                    if (indexList.contains(library.getIndex()))
                                    {
                                        if (!libraryCodeList.contains(library.getSampleCode()))
                                        {
                                            if (!repeatList.contains(entity.getId()))
                                            {
                                                repeatList.add(entity.getId());
                                            }
                                        }
                                        else
                                        {
                                            if (!libraryCodeList.contains(library.getSampleCode()))
                                            {
                                                libraryCodeList.add(library.getSampleCode());
                                            }
                                            if (!indexList.contains(library.getIndex()))
                                            {
                                                indexList.add(library.getIndex());
                                            }
                                            if (!refList.contains(idArray[i]))
                                            {
                                                refList.add(idArray[i]);
                                            }
                                        }
                                    }
                                    else
                                    {
                                        if (!libraryCodeList.contains(library.getSampleCode()))
                                        {
                                            libraryCodeList.add(library.getSampleCode());
                                        }
                                        if (!indexList.contains(library.getIndex()))
                                        {
                                            indexList.add(library.getIndex());
                                        }
                                        if (!refList.contains(idArray[i]))
                                        {
                                            refList.add(idArray[i]);
                                        }
                                    }
                                }
                                else
                                {
                                    libraryCodeList.add(library.getSampleCode());
                                    indexList.add(library.getIndex());
                                    refList.add(idArray[i]);
                                }
                            }
                        }
                    }
                }
                else
                {
                    LibraryAttributes attributes = JsonUtils.asObject(inputSample.getAttributes(), LibraryAttributes.class);
                    
                    if (null != attributes)
                    {
                        if (Collections3.isNotEmpty(libraryCodeList) && Collections3.isNotEmpty(indexList))
                        {
                            if (indexList.contains(attributes.getIndex()))
                            {
                                if (!libraryCodeList.contains(inputSample.getSampleCode()))
                                {
                                    if (!repeatList.contains(entity.getId()))
                                    {
                                        repeatList.add(entity.getId());
                                    }
                                }
                                else
                                {
                                    if (!libraryCodeList.contains(inputSample.getSampleCode()))
                                    {
                                        libraryCodeList.add(inputSample.getSampleCode());
                                    }
                                    if (!indexList.contains(attributes.getIndex()))
                                    {
                                        indexList.add(attributes.getIndex());
                                    }
                                    if (!refList.contains(idArray[i]))
                                    {
                                        refList.add(idArray[i]);
                                    }
                                }
                            }
                            else
                            {
                                if (!libraryCodeList.contains(inputSample.getSampleCode()))
                                {
                                    libraryCodeList.add(inputSample.getSampleCode());
                                }
                                if (!indexList.contains(attributes.getIndex()))
                                {
                                    indexList.add(attributes.getIndex());
                                }
                                if (!refList.contains(idArray[i]))
                                {
                                    refList.add(idArray[i]);
                                }
                            }
                        }
                        else
                        {
                            libraryCodeList.add(inputSample.getSampleCode());
                            indexList.add(attributes.getIndex());
                            refList.add(idArray[i]);
                        }
                    }
                }
            }
        }
        
        //把参考样本也add到repeatList
        for (String index : indexList)
        {
            for (String refId : refList)
            {
                TestingTask entity = testingTaskService.get(refId);
                if (null != entity)
                {
                    TestingSample inputSample = entity.getInputSample();
                    if (isCaptureSample(inputSample))
                    {
                        LibraryCaptureSampleAttributes attributes = JsonUtils.asObject(inputSample.getAttributes(), LibraryCaptureSampleAttributes.class);
                        if (null != attributes)
                        {
                            if (!CollectionUtils.isEmpty(attributes.getLibraries()))
                            {
                                for (CaptureLibraryAttributes library : attributes.getLibraries())
                                {
                                    if (index.equals(library.getIndex()) && !repeatList.contains(refId))
                                    {
                                        repeatList.add(refId);
                                    }
                                }
                            }
                        }
                    }
                    else
                    {
                        LibraryAttributes attributes = JsonUtils.asObject(inputSample.getAttributes(), LibraryAttributes.class);
                        if (null != attributes)
                        {
                            if (index.equals(attributes.getIndex()) && !repeatList.contains(refId))
                            {
                                repeatList.add(refId);
                            }
                        }
                    }
                }
            }
        }
        
        //根据index将repeatList分组
        for (String index : indexList)
        {
            List<String> colorIds = Lists.newArrayList();
            for (String repeatId : repeatList)
            {
                TestingTask entity = testingTaskService.get(repeatId);
                if (null != entity)
                {
                    TestingSample inputSample = entity.getInputSample();
                    if (isCaptureSample(inputSample))
                    {
                        LibraryCaptureSampleAttributes attributes = JsonUtils.asObject(inputSample.getAttributes(), LibraryCaptureSampleAttributes.class);
                        if (null != attributes)
                        {
                            if (!CollectionUtils.isEmpty(attributes.getLibraries()))
                            {
                                for (CaptureLibraryAttributes library : attributes.getLibraries())
                                {
                                    if (index.equals(library.getIndex()) && !colorIds.contains(repeatId))
                                    {
                                        colorIds.add(repeatId);
                                    }
                                }
                            }
                        }
                    }
                    else
                    {
                        LibraryAttributes attributes = JsonUtils.asObject(inputSample.getAttributes(), LibraryAttributes.class);
                        if (null != attributes)
                        {
                            if (index.equals(attributes.getIndex()) && !colorIds.contains(repeatId))
                            {
                                colorIds.add(repeatId);
                            }
                        }
                    }
                }
            }
            
            if (Collections3.isNotEmpty(colorIds))
            {
                boolean flag = false;
                String capCode = "";
                List<String> capCodeList = Lists.newArrayList();
                //让单一文库或者捕获产物可以下单
                for (String id : colorIds)
                {
                    TestingTask entity = testingTaskService.get(id);
                    if (null != entity)
                    {
                        TestingSample inputSample = entity.getInputSample();
                        if (isCaptureSample(inputSample))
                        {
                            int m = inputSample.getSampleCode().lastIndexOf("-");
                            capCode = inputSample.getSampleCode().substring(0, m);
                            if (Collections3.isNotEmpty(capCodeList))
                            {
                                if (!capCodeList.contains(capCode))
                                {
                                    flag = true;
                                    break;
                                }
                            }
                            else
                            {
                                capCodeList.add(capCode);
                            }
                        }
                        else
                        {
                            capCode = inputSample.getSampleCode();
                            if (Collections3.isNotEmpty(capCodeList))
                            {
                                if (!capCodeList.contains(capCode))
                                {
                                    flag = true;
                                    break;
                                }
                            }
                            else
                            {
                                capCodeList.add(capCode);
                            }
                        }
                    }
                }
                if (flag)
                {
                    colorGroup.put(colors.get(colorIndex), colorIds);
                    colorIndex++;
                    if (colorIndex == colors.size())
                    {
                        colorIndex = 0;
                    }
                }
            }
        }
        return colorGroup;
    }
    
    private List<RQTTask> wrapForTesting(List<TestingSheetTask> records)
    {
        if (CollectionUtils.isEmpty(records))
        {
            return Collections.emptyList();
        }
        
        TestingTask testingTask;
        List<RQTTask> tasks = new ArrayList<RQTTask>();
        
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
    
    private RQTTask wrap(TestingTask entity)
    {
        RQTTask task = new RQTTask();
        task.setId(entity.getId());
        task.setStartTime(entity.getStartTime());
        RQTTaskVariables variables = getTaskRunningVariables(entity.getId());
        
        if (null != variables)
        {
            task.setTestingCode(variables.getTestingCode());
            task.setPoolingConcn(variables.getPoolingConcn());
            task.setSampleInputVolume(variables.getSampleInputVolume());
            task.setTeInputVolume(variables.getTeInputVolume());
            task.setTotalDatasize(variables.getTestingDatasize());
        }
        
        //设置加急
        task.setIfUrgent(entity.getIfUrgent());
        task.setUrgentName(entity.getUrgentName());
        task.setUrgentUpdateTime(entity.getUrgentUpdateTime());
        
        task.setTestingMethod(entity.getTestingMethodName());
        
        task.setSampleCode(entity.getTestingSampleCode());
        
        task.setSampleTypeId(entity.getTestingSampleTypeId());
        task.setSampleTypeName(entity.getTestingSampleType());
        
        if (StringUtils.isEmpty(entity.getReceivedSampleCode()))
        {
            LibraryCaptureSampleAttributes attributes = JsonUtils.asObject(entity.getTestingSampleAttributes(), LibraryCaptureSampleAttributes.class);
            
            if (null != attributes)
            {
                System.out.println(attributes.getLibraries().size());
                task.setConcn(attributes.getConcn());
                task.setProbeName(attributes.getProbeName());
                task.setProbeDatasize(attributes.getProbeDatasize());
                task.setLibraries(attributes.getLibraries());
                
                task.setTotalDatasize(attributes.getProbeDatasize());
            }
            
            task.setCaptureSample(true);
        }
        else
        {
            LibraryAttributes attributes = JsonUtils.asObject(entity.getTestingSampleAttributes(), LibraryAttributes.class);
            
            if (null != attributes)
            {
                CaptureLibraryAttributes library = new CaptureLibraryAttributes();
                library.setIndex(attributes.getIndex());
                library.setSampleCode(entity.getTestingSampleCode());
                task.setLibraries(Collections.singleton(library));
                task.setConcn(attributes.getConcn());
                
                task.setTotalDatasize(getLibraryDatasize(task.getId()));
            }
            
            task.setCaptureSample(false);
        }
        
        //文库类型的有出入库状态
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
        
        return task;
    }
    
    private boolean isCaptureSample(TestingSample sample)
    {
        return null == sample.getReceivedSample();
    }
}
