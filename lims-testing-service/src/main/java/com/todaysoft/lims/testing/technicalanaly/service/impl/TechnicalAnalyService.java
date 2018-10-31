package com.todaysoft.lims.testing.technicalanaly.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Lists;
import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.testing.activiti.service.IActivitiService;
import com.todaysoft.lims.testing.base.entity.MlpaVerifyRecord;
import com.todaysoft.lims.testing.base.entity.Order;
import com.todaysoft.lims.testing.base.entity.OrderPlanTask;
import com.todaysoft.lims.testing.base.entity.OrderPrimarySample;
import com.todaysoft.lims.testing.base.entity.OrderSubsidiarySample;
import com.todaysoft.lims.testing.base.entity.Primer;
import com.todaysoft.lims.testing.base.entity.Product;
import com.todaysoft.lims.testing.base.entity.ProductProbe;
import com.todaysoft.lims.testing.base.entity.ProductTestingMethod;
import com.todaysoft.lims.testing.base.entity.QpcrVerifyRecord;
import com.todaysoft.lims.testing.base.entity.ReceivedSample;
import com.todaysoft.lims.testing.base.entity.SangerVerifyRecord;
import com.todaysoft.lims.testing.base.entity.TechnicalAnalyTestingTask;
import com.todaysoft.lims.testing.base.entity.TestingDataPic;
import com.todaysoft.lims.testing.base.entity.TestingMethod;
import com.todaysoft.lims.testing.base.entity.TestingResamplingRecord;
import com.todaysoft.lims.testing.base.entity.TestingSample;
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
import com.todaysoft.lims.testing.base.model.TaskConfig;
import com.todaysoft.lims.testing.base.model.TaskSemantic;
import com.todaysoft.lims.testing.base.model.TestingNode;
import com.todaysoft.lims.testing.base.model.TestingSheetCreateModel;
import com.todaysoft.lims.testing.base.model.VariableModel;
import com.todaysoft.lims.testing.base.service.ICommonService;
import com.todaysoft.lims.testing.base.service.ITestingResolveService;
import com.todaysoft.lims.testing.base.service.ITestingSampleService;
import com.todaysoft.lims.testing.base.service.ITestingScheduleService;
import com.todaysoft.lims.testing.base.service.ITestingSheetService;
import com.todaysoft.lims.testing.base.service.ITestingTaskService;
import com.todaysoft.lims.testing.base.service.ITestingVerifyService;
import com.todaysoft.lims.testing.base.service.adapter.bcm.BCMAdapter;
import com.todaysoft.lims.testing.base.service.adapter.bmm.BMMAdapter;
import com.todaysoft.lims.testing.base.service.adapter.bmm.OrderSimpleModel;
import com.todaysoft.lims.testing.base.service.adapter.bmm.OrderVerifySampleModel;
import com.todaysoft.lims.testing.base.service.adapter.smm.SMMAdapter;
import com.todaysoft.lims.testing.base.service.adapter.smm.UserMinimalModel;
import com.todaysoft.lims.testing.base.utils.JsonUtils;
import com.todaysoft.lims.testing.base.utils.OrderConstants;
import com.todaysoft.lims.testing.biologyanaly.model.BioSampleSimpleModel;
import com.todaysoft.lims.testing.listener.service.ISampleEventService;
import com.todaysoft.lims.testing.mlpadata.service.IMlpaDataService;
import com.todaysoft.lims.testing.primerdesign.model.PrimerDesignTaskVariables;
import com.todaysoft.lims.testing.technicalanaly.context.TechnicalAnalySubmitContext;
import com.todaysoft.lims.testing.technicalanaly.context.TechnicalAnalySubmitScheduleContext;
import com.todaysoft.lims.testing.technicalanaly.context.TechnicalAnalySubmitTaskContext;
import com.todaysoft.lims.testing.technicalanaly.dao.TechnicalAnalyAssignableTaskSearcher;
import com.todaysoft.lims.testing.technicalanaly.model.AssignVerifyProcessResult;
import com.todaysoft.lims.testing.technicalanaly.model.TechnicalAnalyAssignArgs;
import com.todaysoft.lims.testing.technicalanaly.model.TechnicalAnalyAssignModel;
import com.todaysoft.lims.testing.technicalanaly.model.TechnicalAnalyAssignRequest;
import com.todaysoft.lims.testing.technicalanaly.model.TechnicalAnalyAssignTaskArgs;
import com.todaysoft.lims.testing.technicalanaly.model.TechnicalAnalySheetReceivedSample;
import com.todaysoft.lims.testing.technicalanaly.model.TechnicalAnalySheetSamplesModel;
import com.todaysoft.lims.testing.technicalanaly.model.TechnicalAnalySheetVariables;
import com.todaysoft.lims.testing.technicalanaly.model.TechnicalAnalySubmitModel;
import com.todaysoft.lims.testing.technicalanaly.model.TechnicalAnalySubmitRecord;
import com.todaysoft.lims.testing.technicalanaly.model.TechnicalAnalySubmitRequest;
import com.todaysoft.lims.testing.technicalanaly.model.TechnicalAnalySubmitVerifyRequest;
import com.todaysoft.lims.testing.technicalanaly.model.TechnicalAnalySubmitVerifyTaskArgs;
import com.todaysoft.lims.testing.technicalanaly.model.TechnicalAnalyTask;
import com.todaysoft.lims.testing.technicalanaly.model.TechnicalAnalyVerifySample;
import com.todaysoft.lims.testing.technicalanaly.service.ITechnicalAnalyService;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.StringUtils;

@Service
public class TechnicalAnalyService implements ITechnicalAnalyService
{
    private static Logger log = LoggerFactory.getLogger(TechnicalAnalyService.class);
    
    @Autowired
    private BMMAdapter bmmadapter;
    
    @Autowired
    private BCMAdapter bcmadapter;
    
    @Autowired
    private SMMAdapter smmadapter;
    
    @Autowired
    private BaseDaoSupport baseDaoSupport;
    
    @Autowired
    private ICommonService commonService;
    
    @Autowired
    private IActivitiService activitiService;
    
    @Autowired
    private ITestingTaskService testingTaskService;
    
    @Autowired
    private ITestingSheetService testingSheetService;
    
    @Autowired
    private ITestingVerifyService testingVerifyService;
    
    @Autowired
    private ITestingSampleService testingSampleService;
    
    @Autowired
    private ITestingResolveService resolveService;
    
    @Autowired
    private ITestingScheduleService testingScheduleService;
    
    @Autowired
    private IMlpaDataService mlpaDataService;
    
    @Autowired
    private ISampleEventService sampleStoragedService;
    
    @Override
    public List<TechnicalAnalyTask> getAssignableList(TechnicalAnalyAssignableTaskSearcher searcher)
    {
        List<Object[]> records = baseDaoSupport.find(searcher);
        
        if (CollectionUtils.isEmpty(records))
        {
            return Collections.emptyList();
        }
        
        List<TechnicalAnalyTask> tasks = new ArrayList<TechnicalAnalyTask>();
        
        TechnicalAnalyTask task = null;
        for (Object[] record : records)
        {
            task = new TechnicalAnalyTask();
            task.setId((String)record[0]);
            task.setSequencingCode((String)record[1]);
            task.setPlannedFinishDate((Date)record[2]);
            task.setIfUrgent(null == record[3] ? 0 : (int)record[3]);
            task.setStartTime((Date)record[4]);
            
            tasks.add(task);
        }
        
        return tasks;
    }
    
    @Override
    public List<TechnicalAnalyTask> getAssignableListDetail(TechnicalAnalyAssignableTaskSearcher searcher)
    {
        List<Object[]> records = baseDaoSupport.find(searcher.toDetailQuery(), Object[].class);
        
        if (CollectionUtils.isEmpty(records))
        {
            return Collections.emptyList();
        }
        
        Set<String> keys = new HashSet<String>();
        
        for (Object[] record : records)
        {
            keys.add(((TestingTask)record[0]).getId());
        }
        
        List<TechnicalAnalyTask> tasks = new ArrayList<TechnicalAnalyTask>();
        
        records.parallelStream().forEach(record -> {
            tasks.add(wrap((TestingTask)record[0], (TechnicalAnalyTestingTask)record[1], null, false));
        });
        return tasks;
    }
    
    @Override
    public TechnicalAnalyAssignModel getAssignModel(TechnicalAnalyAssignArgs args)
    {
        TechnicalAnalyAssignModel model = new TechnicalAnalyAssignModel();
        
        if (null == args || StringUtils.isEmpty(args.getKeys()))
        {
            model.setTasks(Collections.emptyList());
            return model;
        }
        
        Set<String> includeKeys = new HashSet<String>(Arrays.asList(args.getKeys().split(",")));
        TechnicalAnalyAssignableTaskSearcher searcher = new TechnicalAnalyAssignableTaskSearcher();
        searcher.setIncludeKeys(includeKeys);
        model.setTasks(this.getAssignableListDetail(searcher));
        return model;
    }
    
    @Override
    @Transactional
    public void assign(TechnicalAnalyAssignRequest request, String token)
    {
        if (!CollectionUtils.isEmpty(request.getTasks()))
        {
            TestingTask testingTask;
            
            for (TechnicalAnalyAssignTaskArgs task : request.getTasks())
            {
                testingTask = testingTaskService.get(task.getId());
                testingTask.setStatus(TestingTask.STATUS_ASSIGNING);
                testingTaskService.modify(testingTask);
            }
        }
        
        TestingSheetCreateModel model = buildTestingSheetCreateModel(request, token);
        TestingSheet sheet = testingSheetService.create(model);
        
        activitiService.releaseTestingSheet(sheet);
    }
    
    @Override
    public TechnicalAnalySubmitModel getSubmitModel(String id)
    {
        TechnicalAnalySubmitModel model = new TechnicalAnalySubmitModel();
        TestingSheet entity = testingSheetService.getSheet(id);
        
        if (null == entity)
        {
            return null;
        }
        
        TechnicalAnalySheetVariables variables = JsonUtils.asObject(entity.getVariables(), TechnicalAnalySheetVariables.class);
        
        model.setId(entity.getId());
        model.setCode(entity.getCode());
        model.setAssignerName(entity.getAssignerName());
        model.setAssignTime(entity.getAssignTime());
        model.setDescription(entity.getDescription());
        model.setSubmiterId(entity.getSubmiterId());
        model.setVerified(null != variables && variables.isVerified());
        
        List<TechnicalAnalyTask> tasks = new ArrayList<TechnicalAnalyTask>();
        model.setTasks(tasks);
        
        List<TestingSheetTask> sheetTasks = entity.getTestingSheetTaskList();
        
        if (CollectionUtils.isEmpty(sheetTasks))
        {
            return model;
        }
        
        Set<String> keys = new HashSet<String>();
        
        for (TestingSheetTask sheetTask : sheetTasks)
        {
            keys.add(sheetTask.getTestingTaskId());
        }
        
        //        BatchWrapTestingTaskContext context = testingTaskService.getBatchWrapTestingTaskContextWithIncludes(keys, "PRODUCT_PRINCIPAL");
        
        TestingTask testingTask;
        TechnicalAnalyTestingTask tatt;
        
        for (TestingSheetTask sheetTask : entity.getTestingSheetTaskList())
        {
            testingTask = testingTaskService.get(sheetTask.getTestingTaskId());
            tatt = baseDaoSupport.get(TechnicalAnalyTestingTask.class, testingTask.getId());
            TechnicalAnalyTask technicalanalytask = wrap(testingTask, tatt, null, false);
            tasks.add(technicalanalytask);
        }
        
        return model;
    }
    
    @Override
    public TechnicalAnalySubmitModel getExportModel(String id)
    {
        TechnicalAnalySubmitModel model = new TechnicalAnalySubmitModel();
        TestingSheet entity = testingSheetService.getSheet(id);
        
        if (null == entity)
        {
            return null;
        }
        
        TechnicalAnalySheetVariables variables = JsonUtils.asObject(entity.getVariables(), TechnicalAnalySheetVariables.class);
        
        model.setId(entity.getId());
        model.setCode(entity.getCode());
        model.setAssignerName(entity.getAssignerName());
        model.setAssignTime(entity.getAssignTime());
        model.setDescription(entity.getDescription());
        model.setVerified(null != variables && variables.isVerified());
        
        List<TechnicalAnalyTask> tasks = new ArrayList<TechnicalAnalyTask>();
        model.setTasks(tasks);
        
        List<TestingSheetTask> sheetTasks = entity.getTestingSheetTaskList();
        
        if (CollectionUtils.isEmpty(sheetTasks))
        {
            return model;
        }
        
        Set<String> keys = new HashSet<String>();
        
        for (TestingSheetTask sheetTask : sheetTasks)
        {
            keys.add(sheetTask.getTestingTaskId());
        }
        
        //        BatchWrapTestingTaskContext context = testingTaskService.getBatchWrapTestingTaskContextWithIncludes(keys, "PRODUCT_PRINCIPAL");
        
        TestingTask testingTask;
        TechnicalAnalyTestingTask tatt;
        
        for (TestingSheetTask sheetTask : entity.getTestingSheetTaskList())
        {
            testingTask = testingTaskService.get(sheetTask.getTestingTaskId());
            tatt = baseDaoSupport.get(TechnicalAnalyTestingTask.class, testingTask.getId());
            TechnicalAnalyTask technicalanalytask = wrap(testingTask, tatt, null, true);
            tasks.add(technicalanalytask);
        }
        
        return model;
    }
    
    @Override
    public TechnicalAnalySheetSamplesModel getSamplesModel(String id)
    {
        TestingSheet entity = testingSheetService.getSheet(id);
        
        if (null == entity)
        {
            log.warn("Can not found testing sheet by id {}", id);
            TechnicalAnalySheetSamplesModel model = new TechnicalAnalySheetSamplesModel();
            model.setSamples(Collections.emptyList());
            return model;
        }
        
        if (CollectionUtils.isEmpty(entity.getTestingSheetTaskList()))
        {
            log.warn("Can not found testing tasks for sheet {}", id);
            TechnicalAnalySheetSamplesModel model = new TechnicalAnalySheetSamplesModel();
            model.setSamples(Collections.emptyList());
            return model;
        }
        
        ReceivedSample sample;
        TestingTask testingTask;
        TestingTaskResult result;
        Set<String> exists = new HashSet<String>();
        List<TechnicalAnalySheetReceivedSample> samples = new ArrayList<TechnicalAnalySheetReceivedSample>();
        
        for (TestingSheetTask sheetTask : entity.getTestingSheetTaskList())
        {
            result = baseDaoSupport.get(TestingTaskResult.class, sheetTask.getTestingTaskId());
            
            if (null != result && !TestingTaskResult.RESULT_SUCCESS.equals(result.getResult()))
            {
                log.info("Ignore failure task for id {}", sheetTask.getTestingTaskId());
                continue;
            }
            
            testingTask = testingTaskService.get(sheetTask.getTestingTaskId());
            List<ReceivedSample> receiveSamples =
                baseDaoSupport.findByNamedParam(ReceivedSample.class,
                    "from ReceivedSample r where r.sampleCode=:sampleCode",
                    new String[] {"sampleCode"},
                    new Object[] {testingTask.getReceivedSampleCode()});
            sample = Collections3.getFirst(receiveSamples);
            
            if (null == sample)
            {
                log.error("Can not found received sample for task {}", sheetTask.getTestingTaskId());
                throw new IllegalStateException();
            }
            
            if (exists.contains(sample.getSampleId()))
            {
                continue;
            }
            /***
             * exception
             */
            samples.add(wrap(sample, testingTask.getInputSample()));
            exists.add(sample.getSampleId());
        }
        
        TechnicalAnalySheetSamplesModel model = new TechnicalAnalySheetSamplesModel();
        model.setSamples(samples);
        return model;
    }
    
    private TechnicalAnalySheetReceivedSample wrap(ReceivedSample receivedSample, TestingSample librarySample)
    {
        TechnicalAnalySheetReceivedSample record = new TechnicalAnalySheetReceivedSample();
        record.setId(receivedSample.getSampleId());
        record.setSampleCode(receivedSample.getSampleCode());
        if (!receivedSample.getSampleTypeName().equals("基因组DNA"))//如果原始样本不是基因组DNA，
        {
            List<TestingSample> samples =
                baseDaoSupport.findByNamedParam(TestingSample.class,
                    "from TestingSample t where t.parentSample.sampleCode=:receivedSampleCode",
                    new String[] {"receivedSampleCode"},
                    new Object[] {receivedSample.getSampleCode()});
            // 默认取最后一个DNA样本，最新的DNA样本
            record.setDerivedDNASampleId(Collections3.getFirst(samples).getId());
        }
        else
        //如果原始样本是基因组DNA,则投入样本就是它
        {
            TestingSample inputSample = testingSampleService.getTestingSampleByReceivedSample(receivedSample.getSampleId());
            record.setDerivedDNASampleId(inputSample.getId());
        }
        
        TechnicalAnalyVerifySample verifySample;
        List<TechnicalAnalyVerifySample> verifySamples = new ArrayList<TechnicalAnalyVerifySample>();
        List<OrderVerifySampleModel> records = bmmadapter.getOrderVerifySamples(receivedSample.getOrderId());
        
        if (!CollectionUtils.isEmpty(records))
        {
            TestingSample sample;
            
            for (OrderVerifySampleModel model : records)
            {
                sample = testingSampleService.getTestingSampleByReceivedSample(model.getSampleId());
                
                if (null == sample)
                {
                    throw new IllegalStateException();
                }
                
                verifySample = new TechnicalAnalyVerifySample();
                verifySample.setId(sample.getId());
                verifySample.setFamilyRelation(model.getFamilyRelation());
                verifySamples.add(verifySample);
            }
        }
        
        record.setVerifySamples(verifySamples);
        log.info("Received Sample code {}", record.getSampleCode());
        return record;
    }
    
    @Override
    @Transactional
    public void verify(TechnicalAnalySubmitVerifyRequest request, String token)
    {
        TestingSheet sheet = testingSheetService.getSheet(request.getId());
        
        if (null == sheet)
        {
            throw new IllegalStateException();
        }
        
        TechnicalAnalySheetVariables variables = JsonUtils.asObject(sheet.getVariables(), TechnicalAnalySheetVariables.class);
        
        if (null == variables)
        {
            variables = new TechnicalAnalySheetVariables();
        }
        
        variables.setVerified(true);
        sheet.setVariables(JsonUtils.asJson(variables));
        baseDaoSupport.update(sheet);
        
        Date timestamp = new Date();
        TestingTask testingTask;
        TestingTaskResult result;
        List<TestingSchedule> schedules;
        
        boolean existsQualified = false;
        
        for (TechnicalAnalySubmitVerifyTaskArgs task : request.getTasks())
        {
            if (task.isQualified())
            {
                existsQualified = true;
                continue;
            }
            
            testingTask = baseDaoSupport.get(TestingTask.class, task.getId());
            testingTask.setEndTime(timestamp);
            testingTask.setStatus(TestingTask.STATUS_END);
            testingTask.setEndType(TestingTask.END_FAILURE);
            baseDaoSupport.update(testingTask);
            
            result = new TestingTaskResult();
            result.setTaskId(task.getId());
            result.setRemark(task.getUnqualifiedRemark());
            result.setResult(task.getUnqualifiedStrategy());
            baseDaoSupport.insert(result);
            
            schedules = testingScheduleService.getRelatedSchedules(testingTask.getId());
            
            for (TestingSchedule schedule : schedules)
            {
                testingScheduleService.gotoError(schedule, TaskSemantic.TECHNICAL_ANALY);
            }
        }
        
        // 所有数据都不合格，提交任务单
        if (!existsQualified)
        {
            UserMinimalModel submiter = smmadapter.getLoginUser(token);
            sheet.setSubmiterId(submiter.getId());
            sheet.setSubmiterName(submiter.getName());
            sheet.setSubmitTime(new Date());
            baseDaoSupport.update(sheet);
            
            activitiService.submitTestingSheet(sheet.getId());
        }
    }
    
    @Override
    @Transactional
    public List<AssignVerifyProcessResult> submit(TechnicalAnalySubmitRequest request, String token, VariableModel model, List<TestingTask> tasks)
    {
        
        TechnicalAnalySubmitContext context = generateSubmitContext(request, token, model);
        
        // 1、保存技术分析结果数据
        doSaveTechnicalAnalyRecord(context);
        
        //1.2、保存图片数据
        //8.保存图片
        mlpaDataService.doSaveDataAnalyPic(request.getPicList(), TaskSemantic.TECHNICAL_ANALY, request.getId(), 1);
        
        if (StringUtils.isEmpty(context.getSheetEntity().getSubmiterId()))
        {
            // 2、更新检测任务
            doUpdateTestingTasks(context);
            
            // 3、更新检测流程
            doUpdateTestingSchedules(context);
            
            if (null != request.getSubmitType() && 2 != request.getSubmitType().intValue())
            {
                // 4、更新任务单
                doUpdateTestingSheet(context);
                
                // 5、完成检测流程
                doCompleteProcess(context);
            }
            
        }
        
        // 6、下达验证流程
        List<AssignVerifyProcessResult> assignVerifyProcessResults = doAssignVerifyProcess(context, tasks);
        return assignVerifyProcessResults;
    }
    
    private TechnicalAnalySubmitContext generateSubmitContext(TechnicalAnalySubmitRequest request, String token, VariableModel model)
    {
        TestingSheet sheet = baseDaoSupport.get(TestingSheet.class, request.getId());
        
        if (null == sheet)
        {
            throw new IllegalStateException();
        }
        
        TechnicalAnalySubmitContext context = new TechnicalAnalySubmitContext();
        context.setContextForSubmitResult(request);
        context.setContextForSubmiter(smmadapter.getLoginUser(token));
        context.setContextForTestingSheet(sheet);
        
        //        List<TestingSheetTask> tasks = sheet.getTestingSheetTaskList();
        List<String> tasks = request.getTaskList();
        
        TestingTask task;
        TestingTaskResult taskResult;
        TestingSchedule schedule;
        List<TestingSchedule> schedules = null;
        List<String> scheduleIds = Lists.newArrayList();
        for (String taskId : tasks)
        {
            task = baseDaoSupport.get(TestingTask.class, taskId);
            taskResult = baseDaoSupport.get(TestingTaskResult.class, taskId);
            
            if (null != taskResult && !TestingTaskResult.RESULT_SUCCESS.equals(taskResult.getResult()))
            {
                continue;
            }
            
            context.setContextForTestingTask(task);
            
            if (StringUtils.isEmpty(sheet.getSubmiterId()))
            {
                schedules = testingScheduleService.getRelatedSchedules(task.getId());
            }
            else
            {
                //补提
                List<TestingScheduleHistory> historys =
                    baseDaoSupport.find(TestingScheduleHistory.class, "from TestingScheduleHistory t where t.taskId='" + task.getId() + "'");
                if (Collections3.isNotEmpty(historys))
                {
                    schedules = baseDaoSupport.find(TestingSchedule.class, "from TestingSchedule t where t.id='" + historys.get(0).getScheduleId() + "'");
                }
                //                
                //                //补提设置全局历史提交验证，防止重复提交
                //                List<TestingVerifyRecord> historyVerifyRecords =
                //                    baseDaoSupport.find(TestingVerifyRecord.class, "from TestingVerifyRecord t where t.analyRecord.sheet.id='" + sheet.getId() + "'");
                //                for (TestingVerifyRecord historyVerifyRecord : historyVerifyRecords)
                //                {
                //                    context.setContextForCreateVerifySchedule(historyVerifyRecord);
                //                }
                
            }
            
            if ((CollectionUtils.isEmpty(schedules) || schedules.size() != 1))
            {
                throw new IllegalStateException();
            }
            
            schedule = schedules.get(0);
            context.setContextForTestingSchedule(task, schedule);
            if (!scheduleIds.contains(schedule.getId()))
            {
                scheduleIds.add(schedule.getId());
            }
        }
        String ids = StringUtils.join(scheduleIds, ",");
        model.setScheduleIds(ids);
        return context;
    }
    
    private void doSaveTechnicalAnalyRecord(TechnicalAnalySubmitContext context)
    {
        List<TechnicalAnalySubmitRecord> records = context.getTechnicalAnalyRecords();
        
        TestingSample verifyInputSample;
        TestingVerifyRecord verifyRecord;
        TestingTechnicalAnalyRecord entity;
        
        for (TechnicalAnalySubmitRecord record : records)
        {
            entity = new TestingTechnicalAnalyRecord();
            BeanUtils.copyProperties(record.getRecord(), entity);
            entity.setSheet(context.getSheetEntity());
            entity.setOtherFields(JsonUtils.asJson(record.getRecord().getMap()));
            baseDaoSupport.insert(entity);
            
            if (record.isVerify() && !CollectionUtils.isEmpty(record.getVerifySamples()))
            {
                for (TechnicalAnalyVerifySample sample : record.getVerifySamples())
                {
                    verifyInputSample = new TestingSample();
                    verifyInputSample.setId(sample.getId());
                    
                    verifyRecord = new TestingVerifyRecord();
                    verifyRecord.setAnalyRecord(entity);
                    verifyRecord.setInputSample(verifyInputSample);
                    verifyRecord.setInputSampleFamilyRelation(sample.getFamilyRelation());
                    baseDaoSupport.insert(verifyRecord);
                    context.setContextForCreateTestingVerifyRecord(record, sample, verifyRecord);
                }
            }
        }
    }
    
    private void doUpdateTestingTasks(TechnicalAnalySubmitContext context)
    {
        Set<TechnicalAnalySubmitTaskContext> tasks = context.getTasks();
        
        TestingTask task;
        Date timestamp = new Date();
        TestingTaskResult result;
        
        for (TechnicalAnalySubmitTaskContext taskContext : tasks)
        {
            task = taskContext.getTestingTaskEntity();
            task.setEndType(TestingTask.END_SUCCESS);
            task.setEndTime(timestamp);
            task.setStatus(TestingTask.STATUS_END);
            baseDaoSupport.update(task);
            
            result = new TestingTaskResult();
            result.setTaskId(task.getId());
            result.setResult(TestingTaskResult.RESULT_SUCCESS);
            baseDaoSupport.insert(result);
        }
    }
    
    private void doUpdateTestingSchedules(TechnicalAnalySubmitContext context)
    {
        Set<TechnicalAnalySubmitScheduleContext> schedules = context.getSchedules();
        
        TestingSchedule schedule;
        List<TestingScheduleActive> actives;
        Date timestamp = new Date();
        List<String> ids = Lists.newArrayList();
        
        for (TechnicalAnalySubmitScheduleContext scheduleContext : schedules)
        {
            schedule = scheduleContext.getTestingScheduleEntity();
            schedule.setActiveTask("已完成");
            schedule.setEndTime(timestamp);
            schedule.setEndType(TestingSchedule.END_SUCCESS);
            baseDaoSupport.update(schedule);
            ids.add(schedule.getId());
            
            actives = testingScheduleService.getScheduleActives(schedule.getId());
            
            if (!CollectionUtils.isEmpty(actives))
            {
                for (TestingScheduleActive active : actives)
                {
                    baseDaoSupport.delete(active);
                }
            }
            
            //报告处理为上报的流程，重新实验走完分析节点，状态置为合格
            testingScheduleService.updateReportSample(schedule.getId());
        }
    }
    
    private void doUpdateTestingSheet(TechnicalAnalySubmitContext context)
    {
        TestingSheet sheet = context.getSheetEntity();
        UserMinimalModel submiter = context.getSubmiter();
        sheet.setSubmiterId(submiter.getId());
        sheet.setSubmiterName(submiter.getName());
        sheet.setSubmitTime(new Date());
        baseDaoSupport.update(sheet);
    }
    
    private void doCompleteProcess(TechnicalAnalySubmitContext context)
    {
        TestingSheet sheet = context.getSheetEntity();
        activitiService.submitTestingSheet(sheet.getId());
    }
    
    private List<AssignVerifyProcessResult> doAssignVerifyProcess(TechnicalAnalySubmitContext context, List<TestingTask> tasks)
    {
        List<TechnicalAnalySubmitRecord> records = context.getTechnicalAnalyRecords();
        
        TestingVerifyRecord verifyRecord;
        
        String batchNo = testingVerifyService.getSangerVerifyCode();
        
        String dataCode = "";
        
        List<AssignVerifyProcessResult> assignVerifyProcessResults = new ArrayList<AssignVerifyProcessResult>();
        
        for (TechnicalAnalySubmitRecord record : records)
        {
            dataCode = record.getRecord().getDataCode();
            
            if (!record.isVerify() || CollectionUtils.isEmpty(record.getVerifySamples()))
            {
                continue;
            }
            
            for (TechnicalAnalyVerifySample sample : record.getVerifySamples())
            {
                verifyRecord = context.getTestingVerifyRecord(record, sample);
                
                if (verifyRecord == null)
                {
                    throw new IllegalStateException();
                }
                
                if (context.isAssigned(verifyRecord))
                {
                    continue;
                }
                
                AssignVerifyProcessResult assignVerifyProcessResult = assignVerifyProcess(verifyRecord, context, batchNo, dataCode, tasks);
                if (assignVerifyProcessResult != null)
                {
                    assignVerifyProcessResults.add(assignVerifyProcessResult);
                }
            }
        }
        
        return assignVerifyProcessResults;
    }
    
    private AssignVerifyProcessResult assignVerifyProcess(TestingVerifyRecord record, TechnicalAnalySubmitContext context, String batchNo, String dataCode, List<TestingTask> taskList)
    {
        log.info("数据编号：" + dataCode + ",开始下达验证流程");
        List<String> schduleIds = new ArrayList<>();
        Set<String> orderIds = new HashSet<>();
        TestingSchedule relatedSchedule = context.getTestingSchedule(record.getAnalyRecord().getDataCode());
        
        Product product = null;
        
        if (StringUtils.isNotEmpty(dataCode))
        {
            product = testingScheduleService.getProductByDataCode(dataCode);
        }
        
        if (null == relatedSchedule)
        {
            throw new IllegalStateException();
        }
        
        String method = record.getAnalyRecord().getVerifyMethod();
        
        // 查找验证方法
        String hql = "FROM TestingMethod m WHERE m.type = :type AND m.semantic = :semantic AND m.enabled = :enabled";
        List<TestingMethod> methods =
            baseDaoSupport.findByNamedParam(TestingMethod.class, hql, new String[] {"type", "semantic", "enabled"}, new Object[] {"2", method.toUpperCase(),
                true});
        
        if (CollectionUtils.isEmpty(methods))
        {
            return null;
        }
        
        TestingMethod testingMethod = methods.get(0);
        
        //判断验证样本是否已经存在DNA，存在则设置样本为DNA，不需要再走提取节点（因为存在补提的情况，验证样本已经做过DNA）
        String sampleTypeId = testingMethod.getInputSampleTypeId();
        TestingSample sample = baseDaoSupport.get(TestingSample.class, record.getInputSample().getId());
        if (!sample.getSampleTypeId().equals(sampleTypeId))
        {
            List<TestingSample> dnaSampls =
                baseDaoSupport.find(TestingSample.class, "from TestingSample t where t.parentSample.id='" + record.getInputSample().getId() + "'");
            for (TestingSample dnaSample : dnaSampls)
            {
                List<TestingTask> taskss = testingTaskService.getRelatedTasks(dnaSample.getId(), TaskSemantic.DNA_QC);
                
                if (!CollectionUtils.isEmpty(taskss))
                {
                    for (TestingTask task : taskss)
                    {
                        if (null != task.getEndType() && task.getEndType().intValue() == TestingTask.END_SUCCESS)
                        {
                            record.setInputSample(dnaSample);
                            break;
                        }
                    }
                }
            }
            
        }
        
        String testingSampleIdForSchedule = searchOriginSampleIfResample(sample.getReceivedSample().getSampleId());
        //------------------------------------------//
        
        if (TestingMethod.SANGER.equals(testingMethod.getSemantic()))
        {
            SangerVerifyRecord sangerVerifyRecord = new SangerVerifyRecord();
            sangerVerifyRecord.setVerifyRecord(record);
            sangerVerifyRecord.setCode(getCombineCode(batchNo, record));
            
            String inputSampleTypeId = testingMethod.getInputSampleTypeId();
            TestingSample inputSample = baseDaoSupport.get(TestingSample.class, record.getInputSample().getId());
            
            if (inputSample.getSampleTypeId().equals(inputSampleTypeId))
            {
                // 实验产生的DNA可以直接使用
                if (inputSample.getParentSample() != null)
                {
                    sangerVerifyRecord.setDnaSample(record.getInputSample());
                }
                else
                {
                    // 直接送样的DNA样本，质检合格可以直接使用
                    List<TestingTask> tasks = testingTaskService.getRelatedTasks(inputSample.getId(), TaskSemantic.DNA_QC);
                    
                    if (!CollectionUtils.isEmpty(tasks))
                    {
                        for (TestingTask task : tasks)
                        {
                            if (null != task.getEndType() && task.getEndType().intValue() == TestingTask.END_SUCCESS)
                            {
                                sangerVerifyRecord.setDnaSample(record.getInputSample());
                                break;
                            }
                        }
                    }
                }
            }
            
            // 获取已设计引物
            Primer primer = testingVerifyService.getSangerVerifyPrimer(record);
            
            sangerVerifyRecord.setPrimer(primer);
            baseDaoSupport.insert(sangerVerifyRecord);
            
            // 创建流程
            
            StringBuffer process = new StringBuffer();
            
            String sampleId = "";
            if (null == inputSample.getParentSample())
            {
                sampleId = inputSample.getSampleTypeId();
            }
            else
            {
                sampleId = inputSample.getParentSample().getSampleTypeId();
            }
            
            List<TestingNode> prepareNodes = resolveService.resolve(sampleId, inputSampleTypeId);
            
            if (!CollectionUtils.isEmpty(prepareNodes))
            {
                process.append(Collections3.extractToString(prepareNodes, "type", "/"));
                
                process.append("/");
            }
            
            process.append(testingMethod.getTestingProcess());
            
            if (!StringUtils.isEmpty(testingMethod.getAnalyseProcess()))
            {
                process.append("/" + testingMethod.getAnalyseProcess());
            }
            
            TestingSchedule schedule =
                getExistsSchedule(relatedSchedule.getOrderId(), relatedSchedule.getProductId(), inputSample, relatedSchedule.getSampleId());
            
            //或者主样本
            if (isPrimarySample(inputSample) || null == schedule
                || (StringUtils.isNotEmpty(schedule) && context.getUpdateScheduleList().contains(schedule.getId()))) //若多个流程 一个已经更新了 后面都新增
            {
                schedule = new TestingSchedule();
                //如果当前流程被暂停或者取消，相应验证流程也将暂停取消
                schedule.setProccessState(relatedSchedule.getProccessState());
                schedule.setOrderId(relatedSchedule.getOrderId());
                if (null != product)
                {
                    schedule.setProductId(product.getId());
                }
                else
                {
                    schedule.setProductId(relatedSchedule.getProductId());
                }
                schedule.setMethodId(testingMethod.getId());
                schedule.setSampleId(StringUtils.isNotEmpty(testingSampleIdForSchedule) ? testingSampleIdForSchedule : inputSample.getId());
                schedule.setScheduleNodes(process.toString());
                schedule.setStartTime(new Date());
                schedule.setVerifyKey(sangerVerifyRecord.getId());
                schedule.setVerifyTarget(relatedSchedule.getId());
                baseDaoSupport.insert(schedule);
            }
            else
            {
                schedule.setProccessState(relatedSchedule.getProccessState());
                schedule.setOrderId(relatedSchedule.getOrderId());
                schedule.setMethodId(testingMethod.getId());
                schedule.setSampleId(StringUtils.isNotEmpty(testingSampleIdForSchedule) ? testingSampleIdForSchedule : inputSample.getId());
                schedule.setScheduleNodes(process.toString());
                schedule.setVerifyKey(sangerVerifyRecord.getId());
                schedule.setVerifyTarget(relatedSchedule.getId());
                baseDaoSupport.update(schedule);
                context.setUpdateScheduleList(schedule.getId());
            }
            schduleIds.add(schedule.getId());
            orderIds.add(schedule.getOrderId());
            context.setContextForCreateVerifySchedule(record);
            
            // 引物合成/设计任务
            if (null == sangerVerifyRecord.getPrimer())
            {
                TestingTask primerTask = context.getPrimerTask(record.getAnalyRecord());
                
                TestingTechnicalAnalyRecord technicalAnalyRecord = record.getAnalyRecord();
                
                TestingTask existPrimerTask =
                    getTestingTaskByChromAndLocation1(technicalAnalyRecord.getChromosome(), technicalAnalyRecord.getBeginLocus(), "Sanger");
                
                if (null == primerTask)
                {
                    TaskConfig sangerPrimerPrepareTaskConfig = context.getTaskConfig(TaskSemantic.PRIMER_DESIGN);
                    
                    if (null == sangerPrimerPrepareTaskConfig)
                    {
                        sangerPrimerPrepareTaskConfig = bcmadapter.getTaskConfigBySemantic(TaskSemantic.PRIMER_DESIGN);
                        context.setContextForTaskConfig(TaskSemantic.PRIMER_DESIGN, sangerPrimerPrepareTaskConfig);
                    }
                    
                    //引物库没匹配到引物的 还要判断下是否已经存在了相同的引物设计合成任务 用染色体 跟位置1去查询
                    if (null != existPrimerTask)
                    {
                        primerTask = existPrimerTask;
                    }
                    else
                    {
                        primerTask = new TestingTask();
                        primerTask.setName(sangerPrimerPrepareTaskConfig.getName());
                        primerTask.setSemantic(sangerPrimerPrepareTaskConfig.getSemantic());
                        primerTask.setInputSample(inputSample);
                        primerTask.setStartTime(new Date());
                        primerTask.setStatus(TaskSemantic.TESTING_TASK_WAITASSIGN);
                        primerTask.setResubmit(false);
                        primerTask.setResubmitCount(0);
                        baseDaoSupport.insert(primerTask);
                        
                        TestingTechnicalAnalyRecord analyRecord = record.getAnalyRecord();
                        PrimerDesignTaskVariables pv = new PrimerDesignTaskVariables();
                        pv.setGene(analyRecord.getGeneSymbol());
                        pv.setCodingExon(analyRecord.getExon());
                        pv.setChromosomeLocation(analyRecord.getChrLocation());
                        pv.setChromosomeNumber(analyRecord.getChromosome());
                        pv.setBeginLocus(analyRecord.getBeginLocus());
                        pv.setEndLocus(analyRecord.getEndLocus());
                        
                        TestingTaskRunVariable variable = new TestingTaskRunVariable();
                        variable.setTestingTaskId(primerTask.getId());
                        variable.setText(JsonUtils.asJson(pv));
                        baseDaoSupport.insert(variable);
                        
                    }
                    context.setContextForCreateSangerPrimerPrepareTask(record.getAnalyRecord(), primerTask);
                }
                
                TestingScheduleActive active = new TestingScheduleActive();
                active.setSchedule(schedule);
                active.setTaskId(primerTask.getId());
                baseDaoSupport.insert(active);
                
                if (isNotExist(taskList, primerTask))
                {
                    taskList.add(primerTask);
                }
                
                TestingScheduleHistory history = new TestingScheduleHistory();
                history.setScheduleId(schedule.getId());
                history.setTaskId(primerTask.getId());
                history.setTimestamp(new Date());
                baseDaoSupport.insert(history);
                
                schedule.setActiveTask(primerTask.getName());
                baseDaoSupport.update(schedule);
                
                //设置加急
                Order order = baseDaoSupport.get(Order.class, schedule.getOrderId());
                if (null != order.getIfUrgent())
                {
                    if (1 == order.getIfUrgent())
                    {
                        primerTask.setIfUrgent(order.getIfUrgent());
                        primerTask.setUrgentName(order.getUrgentName());
                        primerTask.setUrgentUpdateTime(order.getUrgentUpdateTime());
                        baseDaoSupport.update(primerTask);
                    }
                }
                
            }
            else
            {
                //如果引物库有这个引物而且待下达或者待实验也有这个任务 此时优先选择这个任务
                TestingTechnicalAnalyRecord technicalAnalyRecord = record.getAnalyRecord();
                TestingTask primerTask = context.getPrimerTask(record.getAnalyRecord());
                TestingTask existPrimerTask = null;
                if (null == primerTask)
                {
                    existPrimerTask = getTestingTaskByChromAndLocation1(technicalAnalyRecord.getChromosome(), technicalAnalyRecord.getBeginLocus(), "Sanger");
                    primerTask = existPrimerTask;
                }
                else
                {
                    existPrimerTask = primerTask;
                }
                if (null != existPrimerTask)
                {
                    TaskConfig sangerPrimerPrepareTaskConfig = context.getTaskConfig(TaskSemantic.PRIMER_DESIGN);
                    
                    if (null == sangerPrimerPrepareTaskConfig)
                    {
                        sangerPrimerPrepareTaskConfig = bcmadapter.getTaskConfigBySemantic(TaskSemantic.PRIMER_DESIGN);
                        context.setContextForTaskConfig(TaskSemantic.PRIMER_DESIGN, sangerPrimerPrepareTaskConfig);
                    }
                    
                    TestingScheduleActive active = new TestingScheduleActive();
                    active.setSchedule(schedule);
                    active.setTaskId(primerTask.getId());
                    baseDaoSupport.insert(active);
                    
                    if (isNotExist(taskList, primerTask))
                    {
                        taskList.add(primerTask);
                    }
                    
                    TestingScheduleHistory history = new TestingScheduleHistory();
                    history.setScheduleId(schedule.getId());
                    history.setTaskId(primerTask.getId());
                    history.setTimestamp(new Date());
                    baseDaoSupport.insert(history);
                    
                    schedule.setActiveTask(primerTask.getName());
                    baseDaoSupport.update(schedule);
                    
                    context.setContextForCreateSangerPrimerPrepareTask(record.getAnalyRecord(), primerTask);
                    
                    //引物设置为空，因为要重新做这个任务
                    sangerVerifyRecord.setPrimer(null);
                    baseDaoSupport.update(sangerVerifyRecord);
                    
                    //设置加急
                    Order order = baseDaoSupport.get(Order.class, schedule.getOrderId());
                    if (null != order.getIfUrgent())
                    {
                        if (1 == order.getIfUrgent())
                        {
                            primerTask.setIfUrgent(order.getIfUrgent());
                            primerTask.setUrgentName(order.getUrgentName());
                            primerTask.setUrgentUpdateTime(order.getUrgentUpdateTime());
                            baseDaoSupport.update(primerTask);
                        }
                    }
                }
            }
            
            //创建任务
            String testingNode = "";
            if (null == sangerVerifyRecord.getDnaSample())
            {
                //如果是空的 就要走DNA提取或者DNA质检
                testingNode = process.toString().split("\\/")[0];
            }
            else
            {
                //如果不是空的就要截取出来第一个任务
                String preNodes = Collections3.extractToString(prepareNodes, "type", "/") + "/";
                testingNode = process.toString().replace(preNodes, "").split("\\/")[0];
            }
            
            if (!TaskSemantic.PCR_ONE.equals(testingNode) || null != sangerVerifyRecord.getPrimer())
            {
                TaskConfig config = context.getTaskConfig(testingNode);
                
                if (null == config)
                {
                    config = bcmadapter.getTaskConfigBySemantic(testingNode);
                    context.setContextForTaskConfig(testingNode, config);
                }
                
                TestingTask task = context.getSampleTask(inputSample, config);
                
                if (null == task)
                {
                    task = existDNATask(inputSample, config);
                    if (null == task)
                    {
                        task = new TestingTask();
                        task.setName(config.getName());
                        task.setSemantic(config.getSemantic());
                        task.setInputSample(inputSample);
                        task.setStartTime(new Date());
                        task.setStatus(TaskSemantic.TESTING_TASK_WAITASSIGN);
                        task.setResubmit(false);
                        task.setResubmitCount(0);
                        baseDaoSupport.insert(task);
                        
                        TestingTaskRunVariable variable = new TestingTaskRunVariable();
                        variable.setTestingTaskId(task.getId());
                        baseDaoSupport.insert(variable);
                        context.setContextForCreateSampleTask(task);
                        
                    }
                    
                }
                if (checkNotExistsScheduleActive(task.getId(), schedule.getId()))
                {
                    TestingScheduleActive active = new TestingScheduleActive();
                    active.setSchedule(schedule);
                    active.setTaskId(task.getId());
                    baseDaoSupport.insert(active);
                    
                    TestingScheduleHistory history = new TestingScheduleHistory();
                    history.setScheduleId(schedule.getId());
                    history.setTaskId(task.getId());
                    history.setTimestamp(new Date());
                    baseDaoSupport.insert(history);
                }
                
                //存储冗余字段
                if (isNotExist(taskList, task))
                {
                    taskList.add(task);
                }
                
                //设置加急
                Order order = baseDaoSupport.get(Order.class, schedule.getOrderId());
                if (null != order.getIfUrgent())
                {
                    if (1 == order.getIfUrgent())
                    {
                        task.setIfUrgent(order.getIfUrgent());
                        task.setUrgentName(order.getUrgentName());
                        task.setUrgentUpdateTime(order.getUrgentUpdateTime());
                        baseDaoSupport.update(task);
                    }
                }
                
                String activeTask = schedule.getActiveTask();
                
                if (StringUtils.isNotEmpty(activeTask) && !activeTask.equals(task.getName())
                    && !activeTask.equals(OrderConstants.ORDER_VERTIFY_SAMPLE_TEMP_FINISHED))
                {
                    activeTask = activeTask + "|" + task.getName();
                }
                else
                {
                    activeTask = task.getName();
                }
                schedule.setActiveTask(activeTask);
                baseDaoSupport.update(schedule);
                
            }
        }
        
        //QPCR验证
        else if (TestingMethod.QPCR.equals(testingMethod.getSemantic()))
        {
            QpcrVerifyRecord qpcrVerifyRecord = new QpcrVerifyRecord();
            qpcrVerifyRecord.setVerifyRecord(record);
            qpcrVerifyRecord.setCombineCode(getCombineCode(batchNo, record));
            
            String inputSampleTypeId = testingMethod.getInputSampleTypeId();
            TestingSample inputSample = baseDaoSupport.get(TestingSample.class, record.getInputSample().getId());
            
            if (inputSample.getSampleTypeId().equals(inputSampleTypeId))
            {
                // 实验产生的DNA可以直接使用
                if (inputSample.getParentSample() != null)
                {
                    qpcrVerifyRecord.setDnaSample(record.getInputSample());
                }
                else
                {
                    // 直接送样的DNA样本，质检合格可以直接使用
                    List<TestingTask> tasks = testingTaskService.getRelatedTasks(inputSample.getId(), TaskSemantic.DNA_QC);
                    
                    if (!CollectionUtils.isEmpty(tasks))
                    {
                        for (TestingTask task : tasks)
                        {
                            if (null != task.getEndType() && task.getEndType().intValue() == TestingTask.END_SUCCESS)
                            {
                                qpcrVerifyRecord.setDnaSample(record.getInputSample());
                                break;
                            }
                        }
                    }
                }
            }
            
            baseDaoSupport.insert(qpcrVerifyRecord);
            
            // 创建流程
            
            StringBuffer process = new StringBuffer();
            
            String sampleId = "";
            if (null == inputSample.getParentSample())
            {
                sampleId = inputSample.getSampleTypeId();
            }
            else
            {
                sampleId = inputSample.getParentSample().getSampleTypeId();
            }
            
            List<TestingNode> prepareNodes = resolveService.resolve(sampleId, inputSampleTypeId);
            
            if (!CollectionUtils.isEmpty(prepareNodes))
            {
                process.append(Collections3.extractToString(prepareNodes, "type", "/"));
                
                process.append("/");
            }
            
            process.append(testingMethod.getTestingProcess());
            
            if (!StringUtils.isEmpty(testingMethod.getAnalyseProcess()))
            {
                process.append("/" + testingMethod.getAnalyseProcess());
            }
            
            TestingSchedule schedule =
                getExistsSchedule(relatedSchedule.getOrderId(), relatedSchedule.getProductId(), inputSample, relatedSchedule.getSampleId());
            if (isPrimarySample(inputSample) || null == schedule
                || (StringUtils.isNotEmpty(schedule) && context.getUpdateScheduleList().contains(schedule.getId())))
            {
                schedule = new TestingSchedule();
                //如果当前流程被暂停或者取消，相应验证流程也将暂停取消
                schedule.setProccessState(relatedSchedule.getProccessState());
                schedule.setOrderId(relatedSchedule.getOrderId());
                if (null != product)
                {
                    schedule.setProductId(product.getId());
                }
                else
                {
                    schedule.setProductId(relatedSchedule.getProductId());
                }
                schedule.setMethodId(testingMethod.getId());
                schedule.setSampleId(StringUtils.isNotEmpty(testingSampleIdForSchedule) ? testingSampleIdForSchedule : inputSample.getId());
                schedule.setScheduleNodes(process.toString());
                schedule.setStartTime(new Date());
                schedule.setVerifyKey(qpcrVerifyRecord.getId());
                schedule.setVerifyTarget(relatedSchedule.getId());
                baseDaoSupport.insert(schedule);
            }
            else
            {
                
                schedule.setProccessState(relatedSchedule.getProccessState());
                schedule.setOrderId(relatedSchedule.getOrderId());
                schedule.setMethodId(testingMethod.getId());
                schedule.setSampleId(StringUtils.isNotEmpty(testingSampleIdForSchedule) ? testingSampleIdForSchedule : inputSample.getId());
                schedule.setScheduleNodes(process.toString());
                schedule.setVerifyKey(qpcrVerifyRecord.getId());
                schedule.setVerifyTarget(relatedSchedule.getId());
                baseDaoSupport.update(schedule);
                
            }
            schduleIds.add(schedule.getId());
            orderIds.add(schedule.getOrderId());
            context.setContextForCreateVerifySchedule(record);
            
            //创建任务
            String testingNode = "";
            if (null == qpcrVerifyRecord.getDnaSample())
            {
                //如果是空的 就要走DNA提取或者DNA质检
                testingNode = process.toString().split("\\/")[0];
            }
            else
            {
                //如果不是空的就要截取出来第一个任务
                String preNodes = Collections3.extractToString(prepareNodes, "type", "/") + "/";
                testingNode = process.toString().replace(preNodes, "").split("\\/")[0];
            }
            
            TaskConfig config = context.getTaskConfig(testingNode);
            if (null == config)
            {
                config = bcmadapter.getTaskConfigBySemantic(testingNode);
                context.setContextForTaskConfig(testingNode, config);
            }
            
            TestingTask task = context.getSampleTask(inputSample, config);
            
            if (null == task)
            {
                task = existDNATask(inputSample, config);
                if (null == task)
                {
                    task = new TestingTask();
                    task.setName(config.getName());
                    task.setSemantic(config.getSemantic());
                    task.setInputSample(inputSample);
                    task.setStartTime(new Date());
                    task.setStatus(TaskSemantic.TESTING_TASK_WAITASSIGN);
                    task.setResubmit(false);
                    task.setResubmitCount(0);
                    baseDaoSupport.insert(task);
                    
                    TestingTaskRunVariable variable = new TestingTaskRunVariable();
                    variable.setTestingTaskId(task.getId());
                    baseDaoSupport.insert(variable);
                    context.setContextForCreateSampleTask(task);
                }
                
            }
            //激活任务最新节点
            
            TestingScheduleActive active = new TestingScheduleActive();
            active.setSchedule(schedule);
            active.setTaskId(task.getId());
            baseDaoSupport.insert(active);
            
            //存储冗余字段
            if (isNotExist(taskList, task))
            {
                taskList.add(task);
            }
            
            TestingScheduleHistory history = new TestingScheduleHistory();
            history.setScheduleId(schedule.getId());
            history.setTaskId(task.getId());
            history.setTimestamp(new Date());
            baseDaoSupport.insert(history);
            
            //设置加急
            Order order = baseDaoSupport.get(Order.class, schedule.getOrderId());
            if (null != order.getIfUrgent())
            {
                if (1 == order.getIfUrgent())
                {
                    task.setIfUrgent(order.getIfUrgent());
                    task.setUrgentName(order.getUrgentName());
                    task.setUrgentUpdateTime(order.getUrgentUpdateTime());
                    baseDaoSupport.update(task);
                }
            }
            
            schedule.setActiveTask(task.getName());
            baseDaoSupport.update(schedule);
            
        }
        
        //MLPA验证
        else if (TestingMethod.MLPA.equals(testingMethod.getSemantic()))
        {
            MlpaVerifyRecord mlpaVerifyRecord = new MlpaVerifyRecord();
            mlpaVerifyRecord.setVerifyRecord(record);
            
            mlpaVerifyRecord.setCombineCode(getCombineCode(batchNo, record));
            
            String inputSampleTypeId = testingMethod.getInputSampleTypeId();
            TestingSample inputSample = baseDaoSupport.get(TestingSample.class, record.getInputSample().getId());
            
            if (inputSample.getSampleTypeId().equals(inputSampleTypeId))
            {
                // 实验产生的DNA可以直接使用
                if (inputSample.getParentSample() != null)
                {
                    mlpaVerifyRecord.setDnaSample(record.getInputSample());
                }
                else
                {
                    // 直接送样的DNA样本，质检合格可以直接使用
                    List<TestingTask> tasks = testingTaskService.getRelatedTasks(inputSample.getId(), TaskSemantic.DNA_QC);
                    
                    if (!CollectionUtils.isEmpty(tasks))
                    {
                        for (TestingTask task : tasks)
                        {
                            if (null != task.getEndType() && task.getEndType().intValue() == TestingTask.END_SUCCESS)
                            {
                                mlpaVerifyRecord.setDnaSample(record.getInputSample());
                                break;
                            }
                        }
                    }
                }
            }
            
            baseDaoSupport.insert(mlpaVerifyRecord);
            
            // 创建流程
            StringBuffer process = new StringBuffer();
            
            String sampleId = "";
            if (null == inputSample.getParentSample())
            {
                sampleId = inputSample.getSampleTypeId();
            }
            else
            {
                sampleId = inputSample.getParentSample().getSampleTypeId();
            }
            
            List<TestingNode> prepareNodes = resolveService.resolve(sampleId, inputSampleTypeId);
            
            if (!CollectionUtils.isEmpty(prepareNodes))
            {
                process.append(Collections3.extractToString(prepareNodes, "type", "/"));
                
                process.append("/");
            }
            
            process.append(testingMethod.getTestingProcess());
            
            if (!StringUtils.isEmpty(testingMethod.getAnalyseProcess()))
            {
                process.append("/" + testingMethod.getAnalyseProcess());
            }
            
            TestingSchedule schedule =
                getExistsSchedule(relatedSchedule.getOrderId(), relatedSchedule.getProductId(), inputSample, relatedSchedule.getSampleId());
            if (isPrimarySample(inputSample) || null == schedule
                || (StringUtils.isNotEmpty(schedule) && context.getUpdateScheduleList().contains(schedule.getId())))
            {
                schedule = new TestingSchedule();
                //如果当前流程被暂停或者取消，相应验证流程也将暂停取消
                schedule.setProccessState(relatedSchedule.getProccessState());
                schedule.setOrderId(relatedSchedule.getOrderId());
                if (null != product)
                {
                    schedule.setProductId(product.getId());
                }
                else
                {
                    schedule.setProductId(relatedSchedule.getProductId());
                }
                schedule.setMethodId(testingMethod.getId());
                schedule.setSampleId(StringUtils.isNotEmpty(testingSampleIdForSchedule) ? testingSampleIdForSchedule : inputSample.getId());
                schedule.setScheduleNodes(process.toString());
                schedule.setStartTime(new Date());
                schedule.setVerifyKey(mlpaVerifyRecord.getId());
                schedule.setVerifyTarget(relatedSchedule.getId());
                baseDaoSupport.insert(schedule);
            }
            else
            {
                
                schedule.setProccessState(relatedSchedule.getProccessState());
                schedule.setOrderId(relatedSchedule.getOrderId());
                schedule.setMethodId(testingMethod.getId());
                schedule.setSampleId(StringUtils.isNotEmpty(testingSampleIdForSchedule) ? testingSampleIdForSchedule : inputSample.getId());
                schedule.setScheduleNodes(process.toString());
                schedule.setVerifyKey(mlpaVerifyRecord.getId());
                schedule.setVerifyTarget(relatedSchedule.getId());
                baseDaoSupport.update(schedule);
                
            }
            
            schduleIds.add(schedule.getId());
            orderIds.add(schedule.getOrderId());
            context.setContextForCreateVerifySchedule(record);
            //创建任务
            String testingNode = "";
            if (null == mlpaVerifyRecord.getDnaSample())
            {
                //如果是空的 就要走DNA提取或者DNA质检
                testingNode = process.toString().split("\\/")[0];
            }
            else
            {
                //如果不是空的就要截取出来第一个任务
                String preNodes = Collections3.extractToString(prepareNodes, "type", "/") + "/";
                testingNode = process.toString().replace(preNodes, "").split("\\/")[0];
            }
            
            TaskConfig config = context.getTaskConfig(testingNode);
            if (null == config)
            {
                config = bcmadapter.getTaskConfigBySemantic(testingNode);
                context.setContextForTaskConfig(testingNode, config);
            }
            
            TestingTask task = context.getSampleTask(inputSample, config);
            
            if (null == task)
            {
                task = existDNATask(inputSample, config);
                if (null == task)
                {
                    task = new TestingTask();
                    task.setName(config.getName());
                    task.setSemantic(config.getSemantic());
                    task.setInputSample(inputSample);
                    task.setStartTime(new Date());
                    task.setStatus(TaskSemantic.TESTING_TASK_WAITASSIGN);
                    task.setResubmit(false);
                    task.setResubmitCount(0);
                    baseDaoSupport.insert(task);
                    
                    TestingTaskRunVariable variable = new TestingTaskRunVariable();
                    variable.setTestingTaskId(task.getId());
                    baseDaoSupport.insert(variable);
                    context.setContextForCreateSampleTask(task);
                    
                }
                
            }
            
            TestingScheduleActive active = new TestingScheduleActive();
            active.setSchedule(schedule);
            active.setTaskId(task.getId());
            baseDaoSupport.insert(active);
            
            //存储冗余字段
            if (isNotExist(taskList, task))
            {
                taskList.add(task);
            }
            
            TestingScheduleHistory history = new TestingScheduleHistory();
            history.setScheduleId(schedule.getId());
            history.setTaskId(task.getId());
            history.setTimestamp(new Date());
            baseDaoSupport.insert(history);
            
            //设置加急
            Order order = baseDaoSupport.get(Order.class, schedule.getOrderId());
            if (null != order.getIfUrgent())
            {
                if (1 == order.getIfUrgent())
                {
                    task.setIfUrgent(order.getIfUrgent());
                    task.setUrgentName(order.getUrgentName());
                    task.setUrgentUpdateTime(order.getUrgentUpdateTime());
                    baseDaoSupport.update(task);
                }
            }
            
            schedule.setActiveTask(task.getName());
            baseDaoSupport.update(schedule);
            
        }
        
        //PCR-NGS验证
        else if (TestingMethod.PCR_NGS.equals(testingMethod.getSemantic()))
        {
            SangerVerifyRecord sangerVerifyRecord = new SangerVerifyRecord();
            sangerVerifyRecord.setVerifyRecord(record);
            sangerVerifyRecord.setCode(getCombineCode(batchNo, record));
            
            String inputSampleTypeId = testingMethod.getInputSampleTypeId();
            TestingSample inputSample = baseDaoSupport.get(TestingSample.class, record.getInputSample().getId());
            
            if (inputSample.getSampleTypeId().equals(inputSampleTypeId))
            {
                // 实验产生的DNA可以直接使用
                if (inputSample.getParentSample() != null)
                {
                    sangerVerifyRecord.setDnaSample(record.getInputSample());
                }
                else
                {
                    // 直接送样的DNA样本，质检合格可以直接使用
                    List<TestingTask> tasks = testingTaskService.getRelatedTasks(inputSample.getId(), TaskSemantic.DNA_QC);
                    
                    if (!CollectionUtils.isEmpty(tasks))
                    {
                        for (TestingTask task : tasks)
                        {
                            if (null != task.getEndType() && task.getEndType().intValue() == TestingTask.END_SUCCESS)
                            {
                                sangerVerifyRecord.setDnaSample(record.getInputSample());
                                break;
                            }
                        }
                    }
                }
            }
            
            // 获取已设计引物
            Primer primer = testingVerifyService.getPcrNgsVerifyPrimer(record);
            
            sangerVerifyRecord.setPrimer(primer);
            baseDaoSupport.insert(sangerVerifyRecord);
            
            // 创建流程
            
            StringBuffer process = new StringBuffer();
            
            String sampleId = "";
            if (null == inputSample.getParentSample())
            {
                sampleId = inputSample.getSampleTypeId();
            }
            else
            {
                sampleId = inputSample.getParentSample().getSampleTypeId();
            }
            
            List<TestingNode> prepareNodes = resolveService.resolve(sampleId, inputSampleTypeId);
            
            if (!CollectionUtils.isEmpty(prepareNodes))
            {
                process.append(Collections3.extractToString(prepareNodes, "type", "/"));
                
                process.append("/");
            }
            
            process.append(testingMethod.getTestingProcess());
            
            if (!StringUtils.isEmpty(testingMethod.getAnalyseProcess()))
            {
                process.append("/" + testingMethod.getAnalyseProcess());
            }
            
            TestingSchedule schedule =
                getExistsSchedule(relatedSchedule.getOrderId(), relatedSchedule.getProductId(), inputSample, relatedSchedule.getSampleId());
            
            if (isPrimarySample(inputSample) || null == schedule
                || (StringUtils.isNotEmpty(schedule) && context.getUpdateScheduleList().contains(schedule.getId())))
            {
                schedule = new TestingSchedule();
                //如果当前流程被暂停或者取消，相应验证流程也将暂停取消
                schedule.setProccessState(relatedSchedule.getProccessState());
                schedule.setOrderId(relatedSchedule.getOrderId());
                if (null != product)
                {
                    schedule.setProductId(product.getId());
                }
                else
                {
                    schedule.setProductId(relatedSchedule.getProductId());
                }
                schedule.setMethodId(testingMethod.getId());
                schedule.setSampleId(StringUtils.isNotEmpty(testingSampleIdForSchedule) ? testingSampleIdForSchedule : inputSample.getId());
                schedule.setScheduleNodes(process.toString());
                schedule.setStartTime(new Date());
                schedule.setVerifyKey(sangerVerifyRecord.getId());
                schedule.setVerifyTarget(relatedSchedule.getId());
                baseDaoSupport.insert(schedule);
            }
            else
            {
                schedule.setProccessState(relatedSchedule.getProccessState());
                schedule.setOrderId(relatedSchedule.getOrderId());
                schedule.setMethodId(testingMethod.getId());
                schedule.setSampleId(StringUtils.isNotEmpty(testingSampleIdForSchedule) ? testingSampleIdForSchedule : inputSample.getId());
                schedule.setScheduleNodes(process.toString());
                schedule.setVerifyKey(sangerVerifyRecord.getId());
                schedule.setVerifyTarget(relatedSchedule.getId());
                baseDaoSupport.update(schedule);
            }
            schduleIds.add(schedule.getId());
            orderIds.add(schedule.getOrderId());
            context.setContextForCreateVerifySchedule(record);
            
            // 引物合成/设计任务
            if (null == sangerVerifyRecord.getPrimer())
            {
                TestingTask primerTask = context.getPrimerTask(record.getAnalyRecord());
                
                TestingTechnicalAnalyRecord technicalAnalyRecord = record.getAnalyRecord();
                
                TestingTask existPrimerTask =
                    getTestingTaskByChromAndLocation1(technicalAnalyRecord.getChromosome(), technicalAnalyRecord.getBeginLocus(), "PCR-NGS");
                
                if (null == primerTask)
                {
                    TaskConfig sangerPrimerPrepareTaskConfig = context.getTaskConfig(TaskSemantic.PCR_NGS_PRIMER_DESIGN);
                    
                    if (null == sangerPrimerPrepareTaskConfig)
                    {
                        sangerPrimerPrepareTaskConfig = bcmadapter.getTaskConfigBySemantic(TaskSemantic.PCR_NGS_PRIMER_DESIGN);
                        context.setContextForTaskConfig(TaskSemantic.PCR_NGS_PRIMER_DESIGN, sangerPrimerPrepareTaskConfig);
                    }
                    
                    //引物库没匹配到引物的 还要判断下是否已经存在了相同的引物设计合成任务 用染色体 跟位置1去查询
                    if (null != existPrimerTask)
                    {
                        primerTask = existPrimerTask;
                    }
                    else
                    {
                        primerTask = new TestingTask();
                        primerTask.setName(sangerPrimerPrepareTaskConfig.getName());
                        primerTask.setSemantic(sangerPrimerPrepareTaskConfig.getSemantic());
                        primerTask.setInputSample(inputSample);
                        primerTask.setStartTime(new Date());
                        primerTask.setStatus(TaskSemantic.TESTING_TASK_WAITASSIGN);
                        primerTask.setResubmit(false);
                        primerTask.setResubmitCount(0);
                        baseDaoSupport.insert(primerTask);
                        
                        TestingTechnicalAnalyRecord analyRecord = record.getAnalyRecord();
                        PrimerDesignTaskVariables pv = new PrimerDesignTaskVariables();
                        pv.setGene(analyRecord.getGeneSymbol());
                        pv.setCodingExon(analyRecord.getExon());
                        pv.setChromosomeLocation(analyRecord.getChrLocation());
                        pv.setChromosomeNumber(analyRecord.getChromosome());
                        pv.setBeginLocus(analyRecord.getBeginLocus());
                        pv.setEndLocus(analyRecord.getEndLocus());
                        
                        TestingTaskRunVariable variable = new TestingTaskRunVariable();
                        variable.setTestingTaskId(primerTask.getId());
                        variable.setText(JsonUtils.asJson(pv));
                        baseDaoSupport.insert(variable);
                        
                    }
                    context.setContextForCreateSangerPrimerPrepareTask(record.getAnalyRecord(), primerTask);
                }
                
                TestingScheduleActive active = new TestingScheduleActive();
                active.setSchedule(schedule);
                active.setTaskId(primerTask.getId());
                baseDaoSupport.insert(active);
                
                //存储冗余字段
                if (isNotExist(taskList, primerTask))
                {
                    taskList.add(primerTask);
                }
                
                TestingScheduleHistory history = new TestingScheduleHistory();
                history.setScheduleId(schedule.getId());
                history.setTaskId(primerTask.getId());
                history.setTimestamp(new Date());
                baseDaoSupport.insert(history);
                
                //设置加急
                Order order = baseDaoSupport.get(Order.class, schedule.getOrderId());
                if (null != order.getIfUrgent())
                {
                    if (1 == order.getIfUrgent())
                    {
                        primerTask.setIfUrgent(order.getIfUrgent());
                        primerTask.setUrgentName(order.getUrgentName());
                        primerTask.setUrgentUpdateTime(order.getUrgentUpdateTime());
                        baseDaoSupport.update(primerTask);
                    }
                }
                
                schedule.setActiveTask(primerTask.getName());
                baseDaoSupport.update(schedule);
                
            }
            else
            {
                //如果引物库有这个引物而且待下达或者待实验也有这个任务 此时优先选择这个任务
                TestingTechnicalAnalyRecord technicalAnalyRecord = record.getAnalyRecord();
                TestingTask primerTask = context.getPrimerTask(record.getAnalyRecord());
                TestingTask existPrimerTask = null;
                if (null == primerTask)
                {
                    existPrimerTask = getTestingTaskByChromAndLocation1(technicalAnalyRecord.getChromosome(), technicalAnalyRecord.getBeginLocus(), "PCR-NGS");
                    primerTask = existPrimerTask;
                }
                else
                {
                    existPrimerTask = primerTask;
                }
                if (null != existPrimerTask)
                {
                    TaskConfig sangerPrimerPrepareTaskConfig = context.getTaskConfig(TaskSemantic.PRIMER_DESIGN);
                    
                    if (null == sangerPrimerPrepareTaskConfig)
                    {
                        sangerPrimerPrepareTaskConfig = bcmadapter.getTaskConfigBySemantic(TaskSemantic.PRIMER_DESIGN);
                        context.setContextForTaskConfig(TaskSemantic.PRIMER_DESIGN, sangerPrimerPrepareTaskConfig);
                    }
                    
                    TestingScheduleActive active = new TestingScheduleActive();
                    active.setSchedule(schedule);
                    active.setTaskId(primerTask.getId());
                    baseDaoSupport.insert(active);
                    
                    if (isNotExist(taskList, primerTask))
                    {
                        taskList.add(primerTask);
                    }
                    
                    TestingScheduleHistory history = new TestingScheduleHistory();
                    history.setScheduleId(schedule.getId());
                    history.setTaskId(primerTask.getId());
                    history.setTimestamp(new Date());
                    baseDaoSupport.insert(history);
                    
                    schedule.setActiveTask(primerTask.getName());
                    baseDaoSupport.update(schedule);
                    
                    context.setContextForCreateSangerPrimerPrepareTask(record.getAnalyRecord(), primerTask);
                    
                    //引物设置为空，因为要重新做这个任务
                    sangerVerifyRecord.setPrimer(null);
                    baseDaoSupport.update(sangerVerifyRecord);
                    
                    //设置加急
                    Order order = baseDaoSupport.get(Order.class, schedule.getOrderId());
                    if (null != order.getIfUrgent())
                    {
                        if (1 == order.getIfUrgent())
                        {
                            primerTask.setIfUrgent(order.getIfUrgent());
                            primerTask.setUrgentName(order.getUrgentName());
                            primerTask.setUrgentUpdateTime(order.getUrgentUpdateTime());
                            baseDaoSupport.update(primerTask);
                        }
                    }
                }
            }
            
            //创建任务
            String testingNode = "";
            if (null == sangerVerifyRecord.getDnaSample())
            {
                //如果是空的 就要走DNA提取或者DNA质检
                testingNode = process.toString().split("\\/")[0];
            }
            else
            {
                //如果不是空的就要截取出来第一个任务
                String preNodes = Collections3.extractToString(prepareNodes, "type", "/") + "/";
                testingNode = process.toString().replace(preNodes, "").split("\\/")[0];
            }
            
            if (!TaskSemantic.PCR_NGS.equals(testingNode) || null != sangerVerifyRecord.getPrimer())
            {
                TaskConfig config = context.getTaskConfig(testingNode);
                
                if (null == config)
                {
                    config = bcmadapter.getTaskConfigBySemantic(testingNode);
                    context.setContextForTaskConfig(testingNode, config);
                }
                
                TestingTask task = context.getSampleTask(inputSample, config);
                
                if (null == task)
                {
                    task = existDNATask(inputSample, config);
                    if (null == task)
                    {
                        task = new TestingTask();
                        task.setName(config.getName());
                        task.setSemantic(config.getSemantic());
                        task.setInputSample(inputSample);
                        task.setStartTime(new Date());
                        task.setStatus(TaskSemantic.TESTING_TASK_WAITASSIGN);
                        task.setResubmit(false);
                        task.setResubmitCount(0);
                        baseDaoSupport.insert(task);
                        
                        TestingTaskRunVariable variable = new TestingTaskRunVariable();
                        variable.setTestingTaskId(task.getId());
                        baseDaoSupport.insert(variable);
                        context.setContextForCreateSampleTask(task);
                        
                    }
                    
                }
                if (checkNotExistsScheduleActive(task.getId(), schedule.getId()))
                {
                    TestingScheduleActive active = new TestingScheduleActive();
                    active.setSchedule(schedule);
                    active.setTaskId(task.getId());
                    baseDaoSupport.insert(active);
                    
                    TestingScheduleHistory history = new TestingScheduleHistory();
                    history.setScheduleId(schedule.getId());
                    history.setTaskId(task.getId());
                    history.setTimestamp(new Date());
                    baseDaoSupport.insert(history);
                }
                
                //存储冗余字段
                if (isNotExist(taskList, task))
                {
                    taskList.add(task);
                }
                
                //设置加急
                Order order = baseDaoSupport.get(Order.class, schedule.getOrderId());
                if (null != order.getIfUrgent())
                {
                    if (1 == order.getIfUrgent())
                    {
                        task.setIfUrgent(order.getIfUrgent());
                        task.setUrgentName(order.getUrgentName());
                        task.setUrgentUpdateTime(order.getUrgentUpdateTime());
                        baseDaoSupport.update(task);
                    }
                }
                
                String activeTask = schedule.getActiveTask();
                
                if (StringUtils.isNotEmpty(activeTask) && !activeTask.equals(task.getName())
                    && !activeTask.equals(OrderConstants.ORDER_VERTIFY_SAMPLE_TEMP_FINISHED))
                {
                    activeTask = activeTask + "|" + task.getName();
                }
                else
                {
                    activeTask = task.getName();
                }
                schedule.setActiveTask(activeTask);
                baseDaoSupport.update(schedule);
            }
        }
        AssignVerifyProcessResult assignVerifyProcessResult = new AssignVerifyProcessResult();
        assignVerifyProcessResult.setDataCode(dataCode);
        assignVerifyProcessResult.setSchduleIds(schduleIds);
        assignVerifyProcessResult.setOrderIds(orderIds);
        return assignVerifyProcessResult;
        //testingScheduleService.sendOrderVerifyTestingStartMessage(schduleIds, orderIds); //不能在此处发送消息，消费者可能拿不到这里保存的数据，需要放到事物外面
    }
    
    private boolean checkNotExistsScheduleActive(String taskId, String scheduleId)
    {
        String hql = " FROM TestingScheduleHistory sh WHERE sh.taskId =:taskId AND sh.scheduleId =:scheduleId";
        List<TestingScheduleHistory> schedules =
            baseDaoSupport.findByNamedParam(TestingScheduleHistory.class, hql, new String[] {"taskId", "scheduleId"}, new Object[] {taskId, scheduleId});
        return Collections3.isEmpty(schedules);
    }
    
    private boolean isPrimarySample(TestingSample inputSample)
    {
        if (StringUtils.isNotEmpty(inputSample.getReceivedSample()))
        {
            if (StringUtils.isNotEmpty(inputSample.getReceivedSample().getBusinessType())
                && inputSample.getReceivedSample().getBusinessType().equals(OrderConstants.ORDER_SAMPLE_PRIMARY))
            {
                return true;
            }
        }
        return false;
    }
    
    /**
     * 原验证样本启动方法默认跟随主样本，后改为新建的独立方法
     * @param orderId
     * @param productId
     * @param methodId
     * @param sampleId
     * @return
     */
    private TestingSchedule getExistsSchedule(String orderId, String productId, TestingSample testingSample, String mainSampleId)
    {
        String hql =
            "FROM TestingSchedule t WHERE t.orderId = :orderId AND t.productId = :productId  AND t.sampleId !=:mainSampleId and t.verifyTarget is null ";
        List<TestingSchedule> result =
            baseDaoSupport.findByNamedParam(TestingSchedule.class, hql, new String[] {"orderId", "productId", "mainSampleId"}, new Object[] {orderId,
                productId, mainSampleId});
        if (Collections3.isNotEmpty(result))
        { //可能有多个附属样本，过滤样本ID
          //ps：重新送样情况，页面传递新样本id 数据库存老的样本id record存放都是原始样本ID  未考虑多次一个样本多次重新送样的情况
            String actualSampleId = searchOriginSampleIfResample(testingSample.getReceivedSample().getSampleId());
            if (StringUtils.isEmpty(actualSampleId))
            {
                actualSampleId = testingSample.getId();
            }
            String innerId = actualSampleId;
            List<TestingSchedule> re = result.stream().filter(o -> o.getSampleId().equals(innerId)).collect(Collectors.toList());
            return Collections3.isNotEmpty(re) ? Collections3.getFirst(re) : null;
        }
        else
        {
            return null;
        }
    }
    
    private String searchOriginSampleIfResample(String testingSampleId)
    {
        String actualSampleId = "";
        TestingResamplingRecord record = sampleStoragedService.getRecordByReceivedResendSampleId(testingSampleId);//判断是否是重新送样
        if (null != record) //说明是重新送样
        {
            TestingSample abnormalTestingSample = findTestingSampleByReceivedSampleId(record.getAbnormalSampleId());
            if (StringUtils.isNotEmpty(abnormalTestingSample))
            {
                actualSampleId = abnormalTestingSample.getId();
            }
        }
        return actualSampleId;
    }
    
    public TestingSample findTestingSampleByReceivedSampleId(String receiveSampleId)
    {
        if (StringUtils.isEmpty(receiveSampleId))
        {
            return null;
        }
        String hqlSample = "FROM TestingSample ts WHERE ts.receivedSample.sampleId = :sampleId AND ts.parentSample IS NULL";
        NamedQueryer queryerSample =
            NamedQueryer.builder().hql(hqlSample).names(Lists.newArrayList("sampleId")).values(Lists.newArrayList(receiveSampleId)).build();
        List<TestingSample> samples = baseDaoSupport.find(queryerSample, TestingSample.class);
        return Collections3.isNotEmpty(samples) ? Collections3.getFirst(samples) : null;
    }
    
    //存在补提的情况，已经在做DNA提取或者质检的任务不需要重复生成任务
    private TestingTask existDNATask(TestingSample inputSample, TaskConfig config)
    {
        TestingTask result = null;
        if (TaskSemantic.DNA_EXTRACT.equals(config.getSemantic()) || TaskSemantic.DNA_QC.equals(config.getSemantic()))
        {
            List<TestingTask> dnaTasks = testingTaskService.getRelatedTasks(inputSample.getId(), TaskSemantic.DNA_EXTRACT);
            List<TestingTask> qcTasks = testingTaskService.getRelatedTasks(inputSample.getId(), TaskSemantic.DNA_QC);
            if (Collections3.isNotEmpty(dnaTasks))
            {
                for (TestingTask dnaTask : dnaTasks)
                {
                    if (dnaTask.getStatus().equals(TestingTask.STATUS_ASSIGNABLE) || dnaTask.getStatus().equals(TestingTask.STATUS_ASSIGNING))
                    {
                        result = dnaTask;
                        return result;
                    }
                    
                }
                
            }
            else if (Collections3.isNotEmpty(qcTasks))
            {
                for (TestingTask qcTask : qcTasks)
                {
                    if (qcTask.getStatus().equals(TestingTask.STATUS_ASSIGNABLE) || qcTask.getStatus().equals(TestingTask.STATUS_ASSIGNING))
                    {
                        result = qcTask;
                        return result;
                    }
                }
                
            }
            
            return result;
        }
        else
        {
            return result;
        }
        
    }
    
    private TestingSheetCreateModel buildTestingSheetCreateModel(TechnicalAnalyAssignRequest request, String token)
    {
        UserMinimalModel loginer = smmadapter.getLoginUser(token);
        UserMinimalModel tester = smmadapter.getUserByID(request.getTesterId());
        TaskConfig taskConfig = bcmadapter.getTaskConfigBySemantic(TaskSemantic.TECHNICAL_ANALY);
        
        TestingSheetCreateModel model = new TestingSheetCreateModel();
        model.setCode(commonService.getTaskCodeBySemantic(TaskSemantic.TECHNICAL_ANALY));
        model.setTaskSemantic(TaskSemantic.TECHNICAL_ANALY);
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
        
        return model;
    }
    
    private TechnicalAnalyTask wrap(TestingTask entity, TechnicalAnalyTestingTask taskExt, BatchWrapTestingTaskContext context, boolean isExport)
    {
        String id = entity.getId();
        
        TechnicalAnalyTask task = new TechnicalAnalyTask();
        
        // 基本信息
        task.setId(id);
        task.setSequencingCode(taskExt.getSequencingCode());
        task.setResubmit(entity.isResubmit());
        task.setResubmitCount(entity.getResubmitCount());
        task.setOrderCode(entity.getOrderCode());
        task.setSubmitTime(entity.getOrderSubmitTime());
        task.setDoctorAssist(entity.getOrderCustomerAssist());
        task.setMarketingCenter(entity.getOrderMarketingCenter());
        
        task.setContractCode(entity.getOrderContractCode());
        
        task.setContractType(entity.getContractMarketingCenter());
        
        task.setCustomerName(entity.getOrderCustomerName());
        task.setCustomerCompanyName(entity.getOrderCustomerCompany());
        task.setStartTime(entity.getStartTime());
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
        
        // 检测产品
        task.setProductCode(entity.getProductCode());
        task.setProductName(entity.getProductName());
        
        task.setTechnicalPrincipal(entity.getProductTechnicalPrincipals());
        try
        {
            if (StringUtils.isNotEmpty(entity.getProductReportDeadline()))
            {
                task.setReportDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(entity.getProductReportDeadline()));
                
            }
        }
        catch (ParseException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        // 检测方法
        
        task.setMethodName(entity.getTestingMethodName());
        
        // 收样样本
        
        task.setSampleCode(entity.getTestingSampleCode());
        task.setSampleTypeName(entity.getReceivedSampleType());
        task.setReceivedSampleCode(entity.getReceivedSampleCode());
        task.setReceivedSampleName(entity.getReceivedSampleName());
        
        // 数据校验结果
        TestingTaskResult result = baseDaoSupport.get(TestingTaskResult.class, entity.getId());
        
        if (null != result && !TestingTaskResult.RESULT_SUCCESS.equals(result.getResult()))
        {
            task.setQualified(false);
            task.setUnqualifiedRemark(result.getRemark());
            task.setQualifiedType(0);
        }
        else
        {
            task.setQualified(true);
            if (3 == entity.getStatus().intValue())//如果该任务已经结束了（状态为3） 标记为1 否则为2
            {
                task.setQualifiedType(1);
            }
            else
            {
                task.setQualifiedType(2);
            }
        }
        
        // 数据编号
        task.setDataCode(entity.getTestingAnalyDataCode());
        
        //样本关系
        if (StringUtils.isNotEmpty(entity.getReceivedSampleCode()))
        {
            String hqlRelation = "FROM OrderSubsidiarySample oss WHERE oss.sampleCode = :sampleCode";
            NamedQueryer queryerRelation =
                NamedQueryer.builder()
                    .hql(hqlRelation)
                    .names(Lists.newArrayList("sampleCode"))
                    .values(Lists.newArrayList(entity.getReceivedSampleCode()))
                    .build();
            List<OrderSubsidiarySample> relations = baseDaoSupport.find(queryerRelation, OrderSubsidiarySample.class);
            if (Collections3.isNotEmpty(relations))
            {
                String familyRelation = Collections3.getFirst(relations).getFamilyRelation();
                
                String hqlDict = "SELECT d.text FROM Dict d WHERE d.category = :category AND d.value = :familyRelation";
                NamedQueryer queryerDict =
                    NamedQueryer.builder()
                        .hql(hqlDict)
                        .names(Lists.newArrayList("category", "familyRelation"))
                        .values(Lists.newArrayList("FAMILY_RELATION", familyRelation))
                        .build();
                List<String> dicts = baseDaoSupport.find(queryerDict, String.class);
                if (Collections3.isNotEmpty(dicts))
                {
                    task.setFamilyRelation(Collections3.getFirst(dicts));
                }
            }
            else
            {
                task.setFamilyRelation("本人");
            }
        }
        
        List<TestingSchedule> schedules = testingScheduleService.getRelatedSchedules(task.getId());
        TestingSchedule schedule = null;
        if (Collections3.isNotEmpty(schedules))
        {
            schedule = Collections3.getFirst(schedules);
        }
        else
        {
            String hql = "SELECT schedule FROM TestingScheduleHistory sh, TestingSchedule schedule WHERE sh.taskId = :taskId AND sh.scheduleId = schedule.id";
            schedules = baseDaoSupport.findByNamedParam(TestingSchedule.class, hql, new String[] {"taskId"}, new Object[] {task.getId()});
            if (Collections3.isNotEmpty(schedules))
            {
                schedule = Collections3.getFirst(schedules);
            }
        }
        String sampleCode = entity.getReceivedSampleCode();
        ProductTestingMethod ptm = null;
        if (StringUtils.isNotEmpty(schedule.getProductId()) && StringUtils.isNotEmpty(schedule.getMethodId()))
        {
            String hql1 = "FROM ProductTestingMethod ptm WHERE ptm.testingMethod.id = :methodId AND ptm.product.id = :productId";
            
            List<ProductTestingMethod> records =
                baseDaoSupport.findByNamedParam(ProductTestingMethod.class, hql1, new String[] {"methodId", "productId"}, new Object[] {schedule.getMethodId(),
                    schedule.getProductId()});
            ptm = Collections3.getFirst(records);
            if (null != ptm)
            {
                task.setDataTemplateId(ptm.getDataTemplateId());
            }
        }
        
        if (isExport)
        {
            String hql2 = "FROM ProductProbe pp WHERE pp.productTestingMethod.id = :id";
            String probeNames = "";
            if (null != ptm)
            {
                List<ProductProbe> pps = baseDaoSupport.findByNamedParam(ProductProbe.class, hql2, new String[] {"id",}, new Object[] {ptm.getId()});
                for (ProductProbe pp : pps)
                {
                    String probeName = (null == pp.getProbe()) ? "" : pp.getProbe().getName();
                    probeNames += probeName + ",";
                }
            }
            task.setProbeName(probeNames);
            
            BioSampleSimpleModel bssm = new BioSampleSimpleModel(schedule.getOrderId(), schedule.getProductId(), sampleCode);
            OrderSimpleModel orderModel = bmmadapter.getOrder(bssm);
            
            if (null != orderModel)
            {
                task.setName(orderModel.getName());
                task.setSex(orderModel.getExamineeSex());
                task.setAge(orderModel.getAge());
                task.setCaseNum(orderModel.getRecordNo());
                task.setClinicalDiagnosis(orderModel.getDiagnosisName());
                task.setFocusGenes(orderModel.getGeneName());
                task.setPhenotype(orderModel.getExamineePhenotypes());
                task.setCaseSummary(orderModel.getMedicalHistory());
                task.setFamilyHistorySummary(orderModel.getFamilyMedicalHistory());
                task.setBusinessLeader(orderModel.getBusinessLeader());
                task.setRemark(orderModel.getRemark());
            }
            
            task.setSex(entity.getReceivedSampleSex());
            
            if (StringUtils.isNotEmpty(task.getOrderCode()))
            {
                String hqlRelation = "FROM OrderSubsidiarySample oss WHERE oss.order.code = :orderCode AND oss.purpose IN('2','3')";
                NamedQueryer queryerRelation =
                    NamedQueryer.builder().hql(hqlRelation).names(Lists.newArrayList("orderCode")).values(Lists.newArrayList(task.getOrderCode())).build();
                List<OrderSubsidiarySample> relations = baseDaoSupport.find(queryerRelation, OrderSubsidiarySample.class);
                
                List<OrderPrimarySample> orderPrimarySample =
                    baseDaoSupport.find(OrderPrimarySample.class, "from OrderPrimarySample p where p.order.code='" + task.getOrderCode() + "'");
                task.setCheckedCount(relations.size() + orderPrimarySample.size());
            }
            
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
        
        //Lane号
        int ngsseqCount = 0;
        if (null != schedule)
        {
            task.setPoolingCode(testingScheduleService.getSequenceCode(schedule.getId()));
            //第一次技术分析异常后重新混样次数（补测次数）
            //获取第一次技术分析异常的时间
            List<TestingScheduleHistory> tthistorys =
                baseDaoSupport.find(TestingScheduleHistory.class,
                    "from TestingScheduleHistory t where EXISTS(select tt.id from TestingTask tt where tt.id = t.taskId and tt.semantic = 'TECHNICAL-ANALY' and tt.status = 3 and tt.endType = 0) and t.scheduleId = '"
                        + schedule.getId() + "' order by t.timestamp");
            if (Collections3.isNotEmpty(tthistorys))
            {
                NamedQueryer queryer =
                    NamedQueryer.builder()
                        .hql("SELECT t FROM TestingScheduleHistory t WHERE "
                            + " EXISTS ( select tt.id from TestingTask tt where tt.id = t.taskId and tt.semantic = 'POOLING') AND t.scheduleId = :scheduleId AND t.timestamp > :timestamp group by t.timestamp ")
                        .names(Arrays.asList("scheduleId", "timestamp"))
                        .values(Arrays.asList(schedule.getId(), tthistorys.get(0).getTimestamp()))
                        .build();
                List<TestingScheduleHistory> history = baseDaoSupport.find(queryer, TestingScheduleHistory.class);
                ngsseqCount = Collections3.isNotEmpty(history) ? history.size() : 0;
            }
            task.setNgsseqCount(ngsseqCount);
        }
        
        return task;
    }
    
    private void setPlannedFinishDate(TestingTask entity, TechnicalAnalyTask task)
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
    
    public TestingSheetTask getSheetTask(String taskId)
    {
        String hql = "FROM TestingSheetTask t WHERE t.testingTaskId = :taskId ";
        List<TestingSheetTask> records = baseDaoSupport.findByNamedParam(TestingSheetTask.class, hql, new String[] {"taskId"}, new String[] {taskId});
        return Collections3.isNotEmpty(records) ? records.get(0) : null;
    }
    
    /**QPCR没有染色体位置*/
    public String getQpcrCombineCode(String batchNo, TestingVerifyRecord record)
    {
        String combineCode = "";
        String sampleCode = "";
        TestingSample inputSample = baseDaoSupport.get(TestingSample.class, record.getInputSample().getId());
        ReceivedSample receivedSample = inputSample.getReceivedSample();
        if (null != receivedSample)
        {
            sampleCode = receivedSample.getSampleCode();
        }
        TestingTechnicalAnalyRecord analyRecord = record.getAnalyRecord();
        if (null != record && null != analyRecord)
        {
            combineCode = batchNo + "_" + sampleCode + "_" + analyRecord.getGeneSymbol();
        }
        return combineCode;
    }
    
    public String getCombineCode(String batchNo, TestingVerifyRecord record)
    {
        String combineCode = "";
        String sampleCode = "";
        TestingSample inputSample = baseDaoSupport.get(TestingSample.class, record.getInputSample().getId());
        ReceivedSample receivedSample = inputSample.getReceivedSample();
        if (null != receivedSample)
        {
            sampleCode = receivedSample.getSampleCode();
        }
        TestingTechnicalAnalyRecord analyRecord = record.getAnalyRecord();
        if (null != record && null != analyRecord)
        {
            combineCode = batchNo + "_" + sampleCode + "_" + analyRecord.getGeneSymbol();
            if (StringUtils.isNotEmpty(analyRecord.getChromosomalLocation()))
            {
                combineCode = combineCode + "-" + analyRecord.getChromosomalLocation();
            }
        }
        return combineCode;
    }
    
    @Override
    public TestingTask getTestingTaskByChromAndLocation1(String chrom, String location1, String verifyMethod)
    {
        TestingTask testingTask;
        
        String hql =
            "FROM SangerVerifyRecord s where s.verifyRecord.analyRecord.chromosome=:chrom and s.verifyRecord.analyRecord.beginLocus=:location1 and s.verifyRecord.analyRecord.verifyMethod=:method ";
        
        List<SangerVerifyRecord> sangerVerifyRecords =
            baseDaoSupport.findByNamedParam(SangerVerifyRecord.class, hql, new String[] {"chrom", "location1", "method"}, new Object[] {chrom, location1,
                verifyMethod});
        
        if (Collections3.isEmpty(sangerVerifyRecords))
        {
            return null;
        }
        
        String taskSemantic = "";
        if ("Sanger".equals(verifyMethod))
        {
            taskSemantic = TaskSemantic.PRIMER_DESIGN;
        }
        else
        {
            taskSemantic = TaskSemantic.PCR_NGS_PRIMER_DESIGN;
        }
        for (SangerVerifyRecord temp : sangerVerifyRecords)
        {
            testingTask = testingScheduleService.getTestingTaskActiveByVerifyKey(temp.getId(), taskSemantic);
            
            if (null != testingTask)
            {
                return testingTask;
            }
        }
        
        return null;
        
    }
    
    public String getMarketCenter(Integer val)
    {
        if (val == 1)
        {
            return "临床遗传";
        }
        else if (val == 2)
        {
            return "临床肿瘤";
        }
        else if (val == 3)
        {
            return "健康筛查";
        }
        else
        {
            return "科技服务";
        }
    }
    
    @Override
    public List<TechnicalAnalyTask> getListBySequencingCode(TechnicalAnalyAssignableTaskSearcher searcher)
    {
        StringBuffer hql = new StringBuffer(512);
        hql.append("FROM TestingTask t, TechnicalAnalyTestingTask tatt WHERE t.semantic = :semantic AND t.id = tatt.taskId");
        hql.append(" AND tatt.sequencingCode = :sequencingCode ORDER BY t.status, t.startTime");
        NamedQueryer queryer =
            NamedQueryer.builder()
                .hql(hql.toString())
                .names(Lists.newArrayList("semantic", "sequencingCode"))
                .values(Lists.newArrayList(TaskSemantic.TECHNICAL_ANALY, searcher.getSequencingCode()))
                .build();
        
        List<Object[]> records = baseDaoSupport.find(queryer, Object[].class);
        
        if (CollectionUtils.isEmpty(records))
        {
            return Collections.emptyList();
        }
        
        Set<String> keys = new HashSet<String>();
        
        for (Object[] record : records)
        {
            //流程被取消的不需要导出
            if (!scheduleCancel(((TestingTask)record[0]).getId()))
            {
                keys.add(((TestingTask)record[0]).getId());
            }
            
        }
        
        //        BatchWrapTestingTaskContext context = testingTaskService.getBatchWrapTestingTaskContextWithIncludes(keys, "PRODUCT_PRINCIPAL");
        
        List<TechnicalAnalyTask> tasks = new ArrayList<TechnicalAnalyTask>();
        
        for (Object[] record : records)
        {
            //流程被取消的不需要导出
            if (!scheduleCancel(((TestingTask)record[0]).getId()))
            {
                tasks.add(wrap((TestingTask)record[0], (TechnicalAnalyTestingTask)record[1], null, true));
            }
            
        }
        
        return tasks;
    }
    
    private boolean scheduleCancel(String taskId)
    {
        
        List<TestingScheduleHistory> historys =
            baseDaoSupport.findByNamedParam(TestingScheduleHistory.class,
                "from TestingScheduleHistory t where t.taskId=:taskId",
                new String[] {"taskId"},
                new Object[] {taskId});
        if (Collections3.isEmpty(historys))
        {
            return true;
        }
        TestingSchedule schedule = baseDaoSupport.get(TestingSchedule.class, Collections3.getFirst(historys).getScheduleId());
        if (null != schedule && 2 != schedule.getProccessState())
        {
            return false;
        }
        return true;
    }
    
    @Override
    public Object getRecordMethodName(String name)
    {
        if ("多重PCR检测".equals(name))
        {
            return TestingMethod.MULTI_PCR;
            
        }
        else if ("Long-PCR检测".equals(name))
        {
            return TestingMethod.LONG_PCR;
            
        }
        else if ("PCR-NGS验证".equals(name))
        {
            return TestingMethod.PCR_NGS;
            
        }
        else if ("Sanger检测".equals(name))
        {
            return "SANGER";
            
        }
        else if ("NGS".equals(name))
        {
            return "NGS";
            
        }
        else if ("CapNGS".equals(name))
        {
            return "CapNGS";
            
        }
        else if ("MLPA检测".equals(name))
        {
            return "MLPA";
        }
        else if ("MLPA验证".equals(name))
        {
            return "MLPA-VERIFY";
        }
        else if ("动态突变".equals(name))
        {
            return "DTTB";
            
        }
        else if ("荧光检测".equals(name))
        {
            return "FLUO";
            
        }
        else
        {
            return name;
        }
    }
    
    public boolean isNotExist(List<TestingTask> list, TestingTask task)
    {
        for (TestingTask temp : list)
        {
            if (temp.getId().equals(task.getId()))
            {
                return false;
            }
        }
        return true;
    }
    
    @Transactional
    public void doSaveTechnicalAnalyPic(TechnicalAnalySubmitRequest request)
    {
        List<TestingDataPic> picList = request.getPicList();
        String sheetId = request.getId();
        //1.根据数据编号跟文件名称查询 如果查到了就更新URL，否则插入一条记录
        if (Collections3.isNotEmpty(picList))
        {
            TestingDataPic testingDataPic;
            for (TestingDataPic record : picList)
            {
                String dataCode = record.getDataCode();
                TestingSchedule relateSchedule = testingScheduleService.getTestingScheduleByDataCode(dataCode, TaskSemantic.TECHNICAL_ANALY, 1);
                String hql = " FROM TestingDataPic t WHERE t.dataCode=:dataCode AND t.picName=:picName ";
                List<TestingDataPic> records =
                    baseDaoSupport.findByNamedParam(TestingDataPic.class,
                        hql,
                        new String[] {"dataCode", "picName"},
                        new String[] {record.getDataCode(), record.getPicName()});
                if (Collections3.isNotEmpty(records))
                {
                    //存在相同的图片就更新地址
                    testingDataPic = Collections3.getFirst(records);
                    testingDataPic.setPicUrl(record.getPicUrl());
                    baseDaoSupport.update(testingDataPic);
                }
                else
                {
                    testingDataPic = new TestingDataPic();
                    if (null != relateSchedule)
                    {
                        testingDataPic.setOrderId(relateSchedule.getOrderId());
                        testingDataPic.setProductId(relateSchedule.getProductId());
                        testingDataPic.setMethodId(relateSchedule.getMethodId());
                        testingDataPic.setSampleId(relateSchedule.getSampleId());
                    }
                    testingDataPic.setSheetId(sheetId);
                    testingDataPic.setPicName(record.getPicName());
                    testingDataPic.setPicUrl(record.getPicUrl());
                    testingDataPic.setDataCode(dataCode);
                    baseDaoSupport.insert(testingDataPic);
                }
            }
        }
    }
}
