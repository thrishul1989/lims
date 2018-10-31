package com.todaysoft.lims.testing.pooling.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.transaction.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.todaysoft.lims.testing.base.entity.*;
import com.todaysoft.lims.testing.libcap.model.CaptureLibraryAttributes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Lists;
import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.testing.activiti.service.IActivitiService;
import com.todaysoft.lims.testing.base.model.SampleTypeSimpleModel;
import com.todaysoft.lims.testing.base.model.TaskConfig;
import com.todaysoft.lims.testing.base.model.TaskSemantic;
import com.todaysoft.lims.testing.base.service.ITestingScheduleService;
import com.todaysoft.lims.testing.base.service.ITestingTaskService;
import com.todaysoft.lims.testing.base.service.TestingCodeComparator;
import com.todaysoft.lims.testing.base.service.adapter.bcm.BCMAdapter;
import com.todaysoft.lims.testing.base.service.adapter.smm.SMMAdapter;
import com.todaysoft.lims.testing.base.service.adapter.smm.UserMinimalModel;
import com.todaysoft.lims.testing.base.utils.JsonUtils;
import com.todaysoft.lims.testing.libcap.model.LibraryCaptureSampleAttributes;
import com.todaysoft.lims.testing.libcap.model.LibraryCaptureTask;
import com.todaysoft.lims.testing.pooling.context.PoolingSubmitContext;
import com.todaysoft.lims.testing.pooling.context.PoolingSubmitNextTaskContext;
import com.todaysoft.lims.testing.pooling.context.PoolingSubmitScheduleContext;
import com.todaysoft.lims.testing.pooling.context.PoolingSubmitTaskContext;
import com.todaysoft.lims.testing.pooling.dao.PoolingAssignableTaskSearcher;
import com.todaysoft.lims.testing.pooling.model.PoolingAssignModel;
import com.todaysoft.lims.testing.pooling.model.PoolingAssignRequest;
import com.todaysoft.lims.testing.pooling.model.PoolingAssignTask;
import com.todaysoft.lims.testing.pooling.model.PoolingAssignTaskArgs;
import com.todaysoft.lims.testing.pooling.model.PoolingSheetVariables;
import com.todaysoft.lims.testing.pooling.model.PoolingSubmitModel;
import com.todaysoft.lims.testing.pooling.model.PoolingSubmitRequest;
import com.todaysoft.lims.testing.pooling.model.PoolingSubmitTask;
import com.todaysoft.lims.testing.pooling.model.PoolingTask;
import com.todaysoft.lims.testing.pooling.model.PoolingTaskVariables;
import com.todaysoft.lims.testing.pooling.service.IPoolingService;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.StringUtils;

@Service
public class PoolingService implements IPoolingService
{
    @Autowired
    private BaseDaoSupport baseDaoSupport;
    
    @Autowired
    private SMMAdapter smmadapter;
    
    @Autowired
    private BCMAdapter bcmadapter;
    
    @Autowired
    private IActivitiService activitiService;
    
    @Autowired
    private ITestingTaskService testingTaskService;
    
    @Autowired
    private ITestingScheduleService testingScheduleService;
    
    @Override
    public List<PoolingTask> getAssignableList(PoolingAssignableTaskSearcher searcher)
    {
        //混样 五中输入样本：捕获产物 文库 多重PCR产物 PCRNGS产物 LONGPCR产物
        List<TestingSheet> records = baseDaoSupport.find(searcher);
        
        if (CollectionUtils.isEmpty(records))
        {
            return Collections.emptyList();
        }
        
        PoolingTask task;
        List<PoolingTask> tasks = new ArrayList<PoolingTask>();
        
        for (TestingSheet record : records)
        {
            task = new PoolingTask();
            task.setId(record.getId());
            task.setCode(record.getCode());
            task.setTimestamp(record.getCreateTime());
            task.setStartTime(record.getCreateTime());
            //设置加急
           List<TestingSheetTask> sheetTaskList = record.getTestingSheetTaskList();
            //设置加急状态和原始样本列表
            setUrgentAndReceivedSampleCode(task,record,searcher);

            if(StringUtils.isNotEmpty(searcher.getSampleCode()))
            {
                if(existSampleCodes(task,searcher.getSampleCode()))
                {
                    tasks.add(task);
                }
            }else{
                tasks.add(task);
            }
        }

        tasks.sort(Comparator.comparing(PoolingTask::getIfUrgent).reversed()
            .thenComparing(PoolingTask::getTimestamp));
        return tasks;
    }

    private void setUrgentAndReceivedSampleCode(PoolingTask task, TestingSheet record, PoolingAssignableTaskSearcher searcher)
    {
        List<String> results = Lists.newArrayList();
        task.setIfUrgent(0);
        if(Collections3.isNotEmpty(record.getTestingSheetTaskList()))
        {
            for(TestingSheetTask testingSheetTask : record.getTestingSheetTaskList())
            {
                TestingTask testingTask = baseDaoSupport.get(TestingTask.class, testingSheetTask.getTestingTaskId());
                if(null != testingTask.getIfUrgent())
                {
                    if(1 == testingTask.getIfUrgent())
                    {
                        task.setIfUrgent(testingTask.getIfUrgent());
                        task.setUrgentName(testingTask.getUrgentName());
                        task.setUrgentUpdateTime(testingTask.getUrgentUpdateTime());
                    }
                }

                if(StringUtils.isNotEmpty(searcher.getSampleCode()))
                {
                    TestingSample testingSample = testingTask.getInputSample();
                    String sampleTypeId = testingSample.getSampleTypeId();
                    if(TaskSemantic.LONGPCR_SAMPLE_TYPE_ID.equals(sampleTypeId) || TaskSemantic.MULTIPCR_SAMPLE_TYPE_ID.equals(sampleTypeId) || TaskSemantic.CATCH_SAMPLE_TYPE_ID.equals(sampleTypeId))
                    {
                        //相关的样本编号放在json里面，查到之后 再查寻testingsample 取到原始样本编号
                        if(StringUtils.isNotEmpty(testingSample.getAttributes()))
                        {
                            LibraryCaptureSampleAttributes libObject = JsonUtils.asObject(testingSample.getAttributes(),LibraryCaptureSampleAttributes.class);
                            if(Collections3.isNotEmpty(libObject.getLibraries()))
                            {
                                for(CaptureLibraryAttributes obj:libObject.getLibraries())
                                {
                                    String tempSampleCode = obj.getSampleCode();
                                    TestingSample tempSample = getTestingSampleBySampleCode(tempSampleCode);
                                    if(null != tempSample)
                                    {
                                        if(null != tempSample.getReceivedSample())
                                        {
                                            results.add(tempSample.getReceivedSample().getSampleCode());
                                        }
                                    }
                                }

                            }else{
                                if(null != testingSample.getReceivedSample())
                                {
                                    results.add(testingSample.getReceivedSample().getSampleCode());
                                }
                            }
                        }
                    }else if(TaskSemantic.LIB_SAMPLE_TYPE_ID.equals(sampleTypeId))
                    {
                        //直接取原始样本编号
                        if(null != testingSample.getReceivedSample())
                        {
                            results.add(testingSample.getReceivedSample().getSampleCode());
                        }

                    }else if(TaskSemantic.PCRNGS_SAMPLE_TYPE_ID.equals(sampleTypeId))
                    {
                        //PCRNGS关联表
                        List<String> pcrNgsSamples = getTestingPcrNgsSampleByOutId(testingSample.getId());
                        results.addAll(pcrNgsSamples);
                    }
                }
            }
        }
        task.setReceivedSampleCode(results);
    }

    private boolean existSampleCodes(PoolingTask task, String sampleCode)
    {
        if(Collections3.isNotEmpty(task.getReceivedSampleCode()))
        {
            for(String code:task.getReceivedSampleCode())
            {
                if(code.contains(sampleCode))
                {
                    return true;
                }
            }
        }
        return false;
    }

    private void setPlannedFinishDate(TestingTask entity,PoolingTask task)
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
            if(null != task.getPlannedFinishDate())
            {
                if(date.after(task.getPlannedFinishDate()))//取最大的日期为应完成日期
                {
                    task.setPlannedFinishDate(date);
                }
            }
            else
            {
                task.setPlannedFinishDate(date);
            }
        }
        else
        {
            try
            {
                if(null == task.getPlannedFinishDate())
                {
                    task.setPlannedFinishDate(new SimpleDateFormat("yyyy-MM-dd").parse("1970-01-01"));
                }
            }
            catch (ParseException e)
            {
                e.printStackTrace();
            }
        }
    }
    
    @Override
    public PoolingAssignModel getAssignModel(String id)
    {
        TestingSheet sheet = baseDaoSupport.get(TestingSheet.class, id);
        
        if (null == sheet)
        {
            throw new IllegalStateException();
        }
        
        List<TestingSheetTask> sheetTasks = sheet.getTestingSheetTaskList();
        
        List<PoolingAssignTask> tasks = new ArrayList<PoolingAssignTask>();
        
        if (!CollectionUtils.isEmpty(sheetTasks))
        {
            for (TestingSheetTask sheetTask : sheetTasks)
            {
                tasks.add(wrapForAssignTask(sheetTask));
            }
            
            // 排序
            Collections.sort(tasks, new Comparator<PoolingAssignTask>()
            {
                @Override
                public int compare(PoolingAssignTask o1, PoolingAssignTask o2)
                {
                    return new TestingCodeComparator().compare(o1.getTestingCode(), o2.getTestingCode());
                }
            });
            
            // 计算
            calc(tasks);
        }
        
        PoolingAssignModel model = new PoolingAssignModel();
        model.setId(sheet.getId());
        model.setCode(sheet.getCode());
        model.setTasks(tasks);
        return model;
    }
    
    @Override
    public boolean isCodeUnique(String code)
    {
        String hql = "SELECT COUNT(*) FROM TestingSample s WHERE s.sampleCode = :code";
        return baseDaoSupport.findByNamedParam(Number.class, hql, new String[] {"code"}, new Object[] {code}).get(0).intValue() == 0;
    }
    
    @Override
    @Transactional
    public void assign(PoolingAssignRequest request, String token)
    {
        TestingSheet sheet = baseDaoSupport.get(TestingSheet.class, request.getId());
        
        if (null == sheet)
        {
            throw new IllegalStateException();
        }
        
        PoolingSheetVariables variables = JsonUtils.asObject(sheet.getVariables(), PoolingSheetVariables.class);
        
        if (null == variables)
        {
            variables = new PoolingSheetVariables();
        }
        
        variables.setPoolingCode(request.getPoolingCode());
        variables.setGlobalRatio(request.getGlobalRatio());
        
        UserMinimalModel loginer = smmadapter.getLoginUser(token);
        UserMinimalModel tester = smmadapter.getUserByID(request.getTesterId());
        sheet.setAssignerId(loginer.getId());
        sheet.setAssignerName(loginer.getName());
        sheet.setAssignTime(new Date());
        sheet.setTesterId(tester.getId());
        sheet.setTesterName(tester.getName());
        sheet.setDescription(request.getDescription());
        sheet.setVariables(JsonUtils.asJson(variables));
        baseDaoSupport.update(sheet);
        
        if (CollectionUtils.isEmpty(request.getTasks()))
        {
            return;
        }
        
        List<PoolingAssignTaskArgs> tasks = request.getTasks();
        
        PoolingTaskVariables taskVariables;
        
        TestingTask testingTask;
        
        for (PoolingAssignTaskArgs task : tasks)
        {
            testingTask = testingTaskService.get(task.getId());
            testingTask.setStatus(TestingTask.STATUS_ASSIGNING);
            testingTaskService.modify(testingTask);
            
            taskVariables = testingTaskService.obtainVariables(task.getId(), PoolingTaskVariables.class);
            taskVariables.setRqtv(task.getRqtv());
            taskVariables.setRelativeVolume(task.getRelativeVolume());
            taskVariables.setGlobalAdjustVolume(task.getGlobalAdjustVolume());
            taskVariables.setSpecifiedRatio(task.getSpecifiedRatio());
            taskVariables.setMixVolume(task.getMixVolume());
            taskVariables.setCtv(task.getCtv());
            testingTaskService.updateVariables(task.getId(), taskVariables);
            
            //更新冗余信息，特殊处理测序编号
            testingTask.setTestingLaneCode(request.getPoolingCode());
            testingTaskService.updateTaskRedundantColumn(Arrays.asList(testingTask), 1);
        }
        
        activitiService.releaseTestingSheet(sheet);
    }
    
    @Override
    public PoolingSubmitModel getSubmitModel(String id)
    {
        TestingSheet sheet = baseDaoSupport.get(TestingSheet.class, id);
        
        if (null == sheet)
        {
            return null;
        }
        
        PoolingSheetVariables variables = JsonUtils.asObject(sheet.getVariables(), PoolingSheetVariables.class);
        
        PoolingSubmitModel model = new PoolingSubmitModel();
        model.setId(sheet.getId());
        model.setCode(sheet.getCode());
        model.setAssignerName(sheet.getAssignerName());
        model.setAssignTime(sheet.getAssignTime());
        model.setDescription(sheet.getDescription());
        model.setGlobalRatio(variables.getGlobalRatio());
        model.setTesterName(sheet.getTesterName());
        model.setSubmitTime(sheet.getSubmitTime());
        
        List<PoolingSubmitTask> tasks = new ArrayList<PoolingSubmitTask>();
        
        if (!CollectionUtils.isEmpty(sheet.getTestingSheetTaskList()))
        {
            for (TestingSheetTask sheetTask : sheet.getTestingSheetTaskList())
            {
                tasks.add(wrapForSubmitTask(sheetTask));
            }
            
            // 排序
            Collections.sort(tasks, new Comparator<PoolingSubmitTask>()
            {
                @Override
                public int compare(PoolingSubmitTask o1, PoolingSubmitTask o2)
                {
                    return new TestingCodeComparator().compare(o1.getTestingCode(), o2.getTestingCode());
                }
            });
        }
        
        model.setTasks(tasks);
        return model;
    }
    
    @Override
    @Transactional
    public void submit(PoolingSubmitRequest request, String token)
    {
        PoolingSubmitContext context = generateSubmitContext(request, token);
        
        // 1、更新任务状态
        doUpdateTasks(context);
        
        // 1、创建混样产物
        doCreatePoolingSample(context);
        
        // 2、创建下一步任务
        doCreateNextNodeTasks(context);
        
        // 3、更新正常流程至下一步
        doUpdateScheduleNextActives(context);
        
        // 4、更新异常流程至异常状态
        doUpdateScheduleErrorActives(context);
        
        // 5、设置任务单提交结果
        doUpdateSheet(context);
        
        // 6、完成流程
        doCompleteProcess(context);
    }
    
    private PoolingSubmitContext generateSubmitContext(PoolingSubmitRequest request, String token)
    {
        TestingSheet sheet = baseDaoSupport.get(TestingSheet.class, request.getId());
        
        if (null == sheet)
        {
            throw new IllegalStateException();
        }
        
        PoolingSheetVariables variables = JsonUtils.asObject(sheet.getVariables(), PoolingSheetVariables.class);
        
        PoolingSubmitContext context = new PoolingSubmitContext();
        context.setContextForSubmiter(smmadapter.getLoginUser(token));
        context.setContextForTestingSheet(sheet);
        context.setContextForTestingSheetVariables(variables);
        
        List<TestingSheetTask> tasks = sheet.getTestingSheetTaskList();
        
        TestingTask testingTask;
        
        for (TestingSheetTask task : tasks)
        {
            testingTask = baseDaoSupport.get(TestingTask.class, task.getTestingTaskId());
            setContextForTestingTask(testingTask, context);
        }
        
        return context;
    }
    
    private void setContextForTestingTask(TestingTask task, PoolingSubmitContext context)
    {
        String id = task.getId();
        PoolingTaskVariables variables = testingTaskService.obtainVariables(id, PoolingTaskVariables.class);
        context.setContextForTestingTask(task);
        context.setContextForTestingTaskVariables(id, variables);
        
        List<TestingSchedule> schedules = testingScheduleService.getRelatedSchedules(id);
        
        TaskConfig scheduleNextNodeConfig;
        
        for (TestingSchedule schedule : schedules)
        {
            context.setContextForTestingSchedule(schedule);
            
            if (context.isTaskPoolingable(id))
            {
                scheduleNextNodeConfig = testingScheduleService.getScheduleNextNodeConfig(schedule, TaskSemantic.POOLING);
                
                if (null == scheduleNextNodeConfig)
                {
                    throw new IllegalStateException();
                }
                
                context.setContextForTestingScheduleNextNode(scheduleNextNodeConfig);
            }
            else
            {
                context.setContextForTestingScheduleError(schedule.getId());
            }
        }
    }
    
    private void doUpdateTasks(PoolingSubmitContext context)
    {
        Set<PoolingSubmitTaskContext> records = context.getRelatedTasks();
        
        TestingTask task;
        Date timestamp = new Date();
        TestingTaskResult result;
        
        for (PoolingSubmitTaskContext record : records)
        {
            task = record.getTestingTaskEntity();
            task.setEndTime(timestamp);
            task.setStatus(TestingTask.STATUS_END);
            
            if (record.isPoolingable())
            {
                task.setEndType(TestingTask.END_SUCCESS);
            }
            else
            {
                task.setEndType(TestingTask.END_FAILURE);
            }
            
            baseDaoSupport.update(task);
            
            result = new TestingTaskResult();
            result.setTaskId(task.getId());
            
            if (record.isPoolingable())
            {
                result.setResult(TestingTaskResult.RESULT_SUCCESS);
            }
            else
            {
                result.setResult(TestingTaskResult.RESULT_FAILURE_REPORT);
            }
            
            baseDaoSupport.insert(result);
        }
    }
    
    private void doCreatePoolingSample(PoolingSubmitContext context)
    {
        Set<PoolingSubmitTaskContext> poolingTasks = context.getPoolingTasks();
        
        if (CollectionUtils.isEmpty(poolingTasks))
        {
            return;
        }
        
        TaskConfig config = bcmadapter.getTaskConfigBySemantic(TaskSemantic.POOLING);
        SampleTypeSimpleModel sampleType = bcmadapter.getSampleType(config.getOutputSampleId());
        
        TestingSample outputSample = new TestingSample();
        outputSample.setSampleCode(context.getOutputSampleCode());
        outputSample.setSampleTypeId(sampleType.getId());
        outputSample.setSampleTypeName(sampleType.getName());
        baseDaoSupport.insert(outputSample);
        
        PoolingSample poolingSample;
        
        for (PoolingSubmitTaskContext taskContext : poolingTasks)
        {
            poolingSample = new PoolingSample();
            poolingSample.setMixedSample(outputSample);
            poolingSample.setSample(taskContext.getTestingTaskEntity().getInputSample());
            poolingSample.setInputVolume(taskContext.getTestingTaskVariables().getMixVolume());
            baseDaoSupport.insert(poolingSample);
        }
        
        context.setContextForCreateOutputSample(outputSample);
    }
    
    private void doCreateNextNodeTasks(PoolingSubmitContext context)
    {
        if (!context.isPoolingable())
        {
            return;
        }
        
        context.setContextForNextNodeTaskConfig();
        
        PoolingSubmitNextTaskContext nextTask = context.getNextTask();
        
        TestingTask task = new TestingTask();
        task.setName(nextTask.getTaskName());
        task.setSemantic(nextTask.getTaskSemantic());
        task.setStatus(TestingTask.STATUS_ASSIGNABLE);
        task.setResubmit(false);
        task.setResubmitCount(0);
        task.setStartTime(new Date());
        task.setInputSample(nextTask.getInputSample());
        baseDaoSupport.insert(task);
        
        TestingTaskRunVariable variables = new TestingTaskRunVariable();
        variables.setTestingTaskId(task.getId());
        baseDaoSupport.insert(variables);
        
        context.setContextForCreateNextNodeTask(task);
    }
    
    private void doUpdateScheduleNextActives(PoolingSubmitContext context)
    {
        Set<PoolingSubmitScheduleContext> schedules = context.getGotoNextSchedules();
        
        TestingSchedule schedule;
        Date timestamp = new Date();
        
        for (PoolingSubmitScheduleContext scheduleContext : schedules)
        {
            schedule = scheduleContext.getTestingScheduleEntity();
            testingScheduleService.gotoNextNode(schedule, TaskSemantic.POOLING, context.getNextTask().getTestingTaskEntity(), timestamp);
        }
        //存储冗余字段，特殊处理测序编号
        context.getNextTask().getTestingTaskEntity().setTestingLaneCode(context.getNextTask().getTestingTaskEntity().getInputSample().getSampleCode());
        testingTaskService.updateTaskRedundantColumn(Arrays.asList(context.getNextTask().getTestingTaskEntity()), 0);
    }
    
    private void doUpdateScheduleErrorActives(PoolingSubmitContext context)
    {
        Set<PoolingSubmitScheduleContext> schedules = context.getGotoErrorSchedules();
        
        TestingSchedule schedule;
        
        for (PoolingSubmitScheduleContext scheduleContext : schedules)
        {
            schedule = scheduleContext.getTestingScheduleEntity();
            testingScheduleService.gotoError(schedule, TaskSemantic.POOLING);
        }
    }
    
    private void doUpdateSheet(PoolingSubmitContext context)
    {
        TestingSheet sheet = context.getSheetEntity();
        UserMinimalModel submiter = context.getSubmiter();
        sheet.setSubmiterId(submiter.getId());
        sheet.setSubmiterName(submiter.getName());
        sheet.setSubmitTime(new Date());
        baseDaoSupport.update(sheet);
    }
    
    private void doCompleteProcess(PoolingSubmitContext context)
    {
        TestingSheet sheet = context.getSheetEntity();
        activitiService.submitTestingSheet(sheet.getId());
    }
    
    private PoolingAssignTask wrapForAssignTask(TestingSheetTask sheetTask)
    {
        TestingTask testingTask = baseDaoSupport.get(TestingTask.class, sheetTask.getTestingTaskId());
        
        if (null == testingTask)
        {
            throw new IllegalStateException();
        }
        
        List<TestingMethod> testingMethods = testingTaskService.getRelatedTestingMethods(testingTask.getId());
        
        if (CollectionUtils.isEmpty(testingMethods) || testingMethods.size() > 1)
        {
            throw new IllegalStateException();
        }
        
        PoolingAssignTask task = new PoolingAssignTask();
        task.setId(testingTask.getId());
        task.setTestingMethod(testingMethods.get(0).getName());
        task.setReceivedSample(testingTask.getReceivedSampleCode());
        
        if (null != testingTask.getInputSample())
        {
            TestingSample inputSample = testingTask.getInputSample();
            task.setSampleCode(inputSample.getSampleCode());
            task.setSampleTypeId(inputSample.getSampleTypeId());
            task.setSampleTypeName(inputSample.getSampleTypeName());
            
            if (isCaptureSample(inputSample))
            {
                LibraryCaptureSampleAttributes attributes = JsonUtils.asObject(inputSample.getAttributes(), LibraryCaptureSampleAttributes.class);
                
                if (null != attributes)
                {
                    task.setProbeName(attributes.getProbeName());
                    task.setProbeDatasize(attributes.getProbeDatasize());
                }
                
                task.setCaptureSample(true);
            }
            else
            {
                task.setCaptureSample(false);
            }
        }
        
        PoolingTaskVariables variables = testingTaskService.obtainVariables(testingTask.getId(), PoolingTaskVariables.class);
        
        if (null != variables)
        {
            task.setCtv(variables.getCtv());
            task.setTestingCode(variables.getTestingCode());
            task.setTestingDatasize(variables.getTestingDatasize());
            task.setConcn(variables.getPoolingConcn());
        }
        
        return task;
    }
    
    private PoolingSubmitTask wrapForSubmitTask(TestingSheetTask sheetTask)
    {
        PoolingAssignTask assignTask = wrapForAssignTask(sheetTask);
        
        PoolingSubmitTask submitTask = new PoolingSubmitTask();
        submitTask.setId(assignTask.getId());
        submitTask.setTestingCode(assignTask.getTestingCode());
        submitTask.setSampleCode(assignTask.getSampleCode());
        submitTask.setSampleTypeId(assignTask.getSampleTypeId());
        submitTask.setSampleTypeName(assignTask.getSampleTypeName());
        submitTask.setCaptureSample(assignTask.isCaptureSample());
        submitTask.setProbeName(assignTask.getProbeName());
        submitTask.setProbeDatasize(assignTask.getProbeDatasize());
        submitTask.setTestingDatasize(assignTask.getTestingDatasize());
        submitTask.setCtv(assignTask.getCtv());
        submitTask.setTestingMethod(assignTask.getTestingMethod());
        submitTask.setConcn(assignTask.getConcn());
        
        PoolingTaskVariables variables = testingTaskService.obtainVariables(sheetTask.getTestingTaskId(), PoolingTaskVariables.class);
        submitTask.setRqtv(variables.getRqtv());
        submitTask.setRelativeVolume(variables.getRelativeVolume());
        submitTask.setGlobalAdjustVolume(variables.getGlobalAdjustVolume());
        submitTask.setSpecifiedRatio(variables.getSpecifiedRatio());
        submitTask.setMixVolume(variables.getMixVolume());
        return submitTask;
    }
    
    private void calc(List<PoolingAssignTask> tasks)
    {
        BigDecimal ctv;
        BigDecimal refer = null;
        
        for (PoolingAssignTask task : tasks)
        {
            ctv = task.getCtv();
            
            if (null == ctv)
            {
                continue;
            }
            
            if (null == refer)
            {
                refer = ctv;
                continue;
            }
            
            refer = refer.max(ctv);
        }
        
        if (null == refer)
        {
            return;
        }
        
        BigDecimal rqtv;
        BigDecimal datasize;
        BigDecimal rqtInputVolume;
        
        for (PoolingAssignTask task : tasks)
        {
            ctv = task.getCtv();
            
            if (null == ctv)
            {
                continue;
            }
            
            rqtv = BigDecimal.valueOf(Math.pow(2D, refer.subtract(ctv).doubleValue()));
            task.setRqtv(rqtv);
            
            datasize = task.getTestingDatasize();
            
            if (null == datasize)
            {
                continue;
            }
            
            rqtInputVolume = datasize.divide(rqtv, 10, BigDecimal.ROUND_HALF_DOWN);
            task.setRqtInputVolume(rqtInputVolume);
        }
    }
    
    private boolean isCaptureSample(TestingSample sample)
    {
        return null == sample.getReceivedSample();
    }

    private TestingSample getTestingSampleBySampleCode(String sampleCode)
    {
        if(StringUtils.isEmpty(sampleCode))
        {
            return null;
        }
        String hql = "FROM TestingSample t WHERE t.sampleCode = '"+sampleCode+"'";
        List<TestingSample> results = baseDaoSupport.find(TestingSample.class,hql);
        return Collections3.getFirst(results);
    }

    private List<String> getTestingPcrNgsSampleByOutId(String sampleId)
    {
        List<String> results = Lists.newArrayList();
        String hql = "FROM TestingPcrNgsSample t WHERE t.outputSample.id='"+sampleId+"'";
        List<TestingPcrNgsSample> testingSamples = baseDaoSupport.find(TestingPcrNgsSample.class,hql);
        if(Collections3.isNotEmpty(testingSamples))
        {
            for(TestingPcrNgsSample temp:testingSamples)
            {
                if(null != temp.getInputSample() && null != temp.getInputSample().getReceivedSample())
                {
                    results.add(temp.getInputSample().getReceivedSample().getSampleCode());
                }
            }
        }
        return results;
    }
}
