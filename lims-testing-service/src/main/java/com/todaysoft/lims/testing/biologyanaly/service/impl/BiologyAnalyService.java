package com.todaysoft.lims.testing.biologyanaly.service.impl;

import static java.util.stream.Collectors.toList;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.testing.base.adapter.impl.DictAdapter;
import com.todaysoft.lims.testing.base.entity.ContractContent;
import com.todaysoft.lims.testing.base.entity.NgsSequecingTask;
import com.todaysoft.lims.testing.base.entity.Order;
import com.todaysoft.lims.testing.base.entity.OrderPlanTask;
import com.todaysoft.lims.testing.base.entity.OrderPrimarySample;
import com.todaysoft.lims.testing.base.entity.OrderSubsidiarySample;
import com.todaysoft.lims.testing.base.entity.PoolingSample;
import com.todaysoft.lims.testing.base.entity.Product;
import com.todaysoft.lims.testing.base.entity.ReceivedSample;
import com.todaysoft.lims.testing.base.entity.TechnicalAnalyTestingTask;
import com.todaysoft.lims.testing.base.entity.TestingMethod;
import com.todaysoft.lims.testing.base.entity.TestingPcrNgsSample;
import com.todaysoft.lims.testing.base.entity.TestingSample;
import com.todaysoft.lims.testing.base.entity.TestingSchedule;
import com.todaysoft.lims.testing.base.entity.TestingScheduleActive;
import com.todaysoft.lims.testing.base.entity.TestingScheduleHistory;
import com.todaysoft.lims.testing.base.entity.TestingSheet;
import com.todaysoft.lims.testing.base.entity.TestingSheetTask;
import com.todaysoft.lims.testing.base.entity.TestingTask;
import com.todaysoft.lims.testing.base.entity.TestingTaskResult;
import com.todaysoft.lims.testing.base.entity.TestingTaskRunVariable;
import com.todaysoft.lims.testing.base.model.ConcludingReportModel;
import com.todaysoft.lims.testing.base.model.DataSendingModel;
import com.todaysoft.lims.testing.base.model.FamilyRelationSex;
import com.todaysoft.lims.testing.base.model.SampleTypeSimpleModel;
import com.todaysoft.lims.testing.base.model.TaskConfig;
import com.todaysoft.lims.testing.base.model.TaskSemantic;
import com.todaysoft.lims.testing.base.model.TestingSheetCreateModel;
import com.todaysoft.lims.testing.base.service.ICommonService;
import com.todaysoft.lims.testing.base.service.ITestingSampleService;
import com.todaysoft.lims.testing.base.service.ITestingScheduleService;
import com.todaysoft.lims.testing.base.service.ITestingSheetService;
import com.todaysoft.lims.testing.base.service.ITestingTaskService;
import com.todaysoft.lims.testing.base.service.adapter.bcm.BCMAdapter;
import com.todaysoft.lims.testing.base.service.adapter.bmm.BMMAdapter;
import com.todaysoft.lims.testing.base.service.adapter.bmm.OrderSimpleModel;
import com.todaysoft.lims.testing.base.service.adapter.smm.SMMAdapter;
import com.todaysoft.lims.testing.base.service.adapter.smm.UserMinimalModel;
import com.todaysoft.lims.testing.base.utils.JsonUtils;
import com.todaysoft.lims.testing.biologyanaly.context.BiologyAnalySubmitContext;
import com.todaysoft.lims.testing.biologyanaly.context.BiologyAnalySubmitNextTaskContext;
import com.todaysoft.lims.testing.biologyanaly.context.BiologyAnalySubmitScheduleContext;
import com.todaysoft.lims.testing.biologyanaly.context.BiologyAnalySubmitTaskContext;
import com.todaysoft.lims.testing.biologyanaly.dao.BiologyAnalyTaskSearcher;
import com.todaysoft.lims.testing.biologyanaly.model.BioSampleSimpleModel;
import com.todaysoft.lims.testing.biologyanaly.model.BiologyAnalyDetailsRecord;
import com.todaysoft.lims.testing.biologyanaly.model.BiologyAnalySampleRecord;
import com.todaysoft.lims.testing.biologyanaly.model.BiologyAnalySubmitModel;
import com.todaysoft.lims.testing.biologyanaly.model.BiologyAnalySubmitRequest;
import com.todaysoft.lims.testing.biologyanaly.model.BiologyAnalyTask;
import com.todaysoft.lims.testing.biologyanaly.model.PcrNgsDataModel;
import com.todaysoft.lims.testing.biologyanaly.service.IBiologyAnalyService;
import com.todaysoft.lims.testing.libcap.model.CaptureLibraryAttributes;
import com.todaysoft.lims.testing.libcap.model.LibraryCaptureSampleAttributes;
import com.todaysoft.lims.testing.libcre.model.LibraryAttributes;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.StringUtils;

@Service
public class BiologyAnalyService implements IBiologyAnalyService
{
    private static Logger log = LoggerFactory.getLogger(BiologyAnalyService.class);
    
    @Autowired
    private BCMAdapter bcmadapter;
    
    @Autowired
    private BMMAdapter bmmadapter;
    
    @Autowired
    private SMMAdapter smmadapter;
    
    @Autowired
    private BaseDaoSupport baseDaoSupport;
    
    @Autowired
    private ITestingSampleService testingSampleService;
    
    @Autowired
    private ITestingTaskService testingTaskService;
    
    @Autowired
    private ITestingScheduleService testingScheduleService;
    
    @Autowired
    private ITestingSheetService testingSheetService;
    
    @Autowired
    private ICommonService commonService;
    
    @Autowired
    private DictAdapter dictAdapter;
    
    @Override
    public List<BiologyAnalyTask> getTaskList(BiologyAnalyTaskSearcher searcher)
    {
        List<TestingTask> records = baseDaoSupport.find(searcher);
        
        if (CollectionUtils.isEmpty(records))
        {
            return Collections.emptyList();
        }
        
        List<BiologyAnalyTask> tasks = new ArrayList<BiologyAnalyTask>();
        
        for (TestingTask record : records)
        {
            tasks.add(wrap(record));
        }
        /*
         * tasks.sort(Comparator.comparing(BiologyAnalyTask::getIfUrgent).reversed
         * () .thenComparing(BiologyAnalyTask::getCreateTime));
         */
        return tasks;
    }
    
    @Override
    public BiologyAnalySubmitModel getSubmitModel(String id)
    {
        TestingTask entity = baseDaoSupport.get(TestingTask.class, id);
        
        if (null == entity)
        {
            return null;
        }
        
        BiologyAnalySubmitModel model = new BiologyAnalySubmitModel();
        model.setId(entity.getId());
        model.setSampleCode(entity.getInputSample().getSampleCode());
        model.setCreateTime(entity.getStartTime());
        return model;
    }
    
    @Override
    public List<BiologyAnalySampleRecord> getSampleRecords(String id)
    {
        TestingTask entity = baseDaoSupport.get(TestingTask.class, id);
        
        if (null == entity)
        {
            log.warn("Can not found testing task entity by id {}", id);
            TestingSheet sheet = baseDaoSupport.get(TestingSheet.class, id);
            if (null == sheet)
            {
                return Collections.emptyList();
            }
            if (Collections3.isNotEmpty(sheet.getTestingSheetTaskList()))
            {
                TestingSheetTask sheetTask = Collections3.getFirst(sheet.getTestingSheetTaskList());
                entity = baseDaoSupport.get(TestingTask.class, sheetTask.getTestingTaskId());
                if (null == entity)
                {
                    return Collections.emptyList();
                }
            }
        }
        
        TestingSample mixedSample = entity.getInputSample();
        
        if (null == mixedSample)
        {
            log.warn("Testing task {} input sample is null.", id);
            return Collections.emptyList();
        }
        else
        {
            if (log.isDebugEnabled())
            {
                log.debug("Mixed Sample id {}, code {}", mixedSample.getId(), mixedSample.getSampleCode());
            }
        }
        
        String hql = "FROM PoolingSample s WHERE s.mixedSample.id = :sampleId";
        List<PoolingSample> samples = baseDaoSupport.findByNamedParam(PoolingSample.class, hql, new String[] {"sampleId"}, new Object[] {mixedSample.getId()});
        
        if (CollectionUtils.isEmpty(samples))
        {
            log.warn("Testing task {} pooling samples is empty", id);
            return Collections.emptyList();
        }
        
        TestingSample sample;
        List<BiologyAnalySampleRecord> instanceRecords;
        Set<String> recordedLibrary = new HashSet<String>();
        List<BiologyAnalySampleRecord> records = new ArrayList<BiologyAnalySampleRecord>();
        
        TaskConfig taskConfig = bcmadapter.getTaskConfigBySemantic(TaskSemantic.PCR_NGS);
        
        SampleTypeSimpleModel outputSampleType = bcmadapter.getSampleType(taskConfig.getOutputSampleId());
        
        if (null == outputSampleType)
        {
            throw new IllegalStateException();
        }
        
        for (PoolingSample ps : samples)
        {
            sample = ps.getSample();
            // 若该样本流程被取消，则不导出表
            if (!scheduleCancel(sample))
            {
                if (null == sample)
                {
                    log.error("Testing task {}, Pooling sample {} details is null", id, ps.getId());
                    throw new IllegalStateException();
                }
                
                instanceRecords = getSampleRecords(sample, mixedSample.getSampleCode(), recordedLibrary, outputSampleType);
                
                if (CollectionUtils.isEmpty(instanceRecords))
                {
                    continue;
                }
                
                records.addAll(instanceRecords);
            }
            
        }
        
        return records;
    }
    
    private boolean scheduleCancel(TestingSample sample)
    {
        List<TestingTask> tasks =
            baseDaoSupport.findByNamedParam(TestingTask.class,
                "from TestingTask t where t.inputSample.id=:sampleId",
                new String[] {"sampleId"},
                new Object[] {sample.getId()});
        
        List<TestingScheduleHistory> historys =
            baseDaoSupport.findByNamedParam(TestingScheduleHistory.class,
                "from TestingScheduleHistory t where t.taskId=:taskId",
                new String[] {"taskId"},
                new Object[] {Collections3.getFirst(tasks).getId()});
        TestingSchedule schedule = baseDaoSupport.get(TestingSchedule.class, Collections3.getFirst(historys).getScheduleId());
        if (null != schedule && 2 != schedule.getProccessState())
        {
            return false;
        }
        return true;
    }
    
    @Override
    public List<BiologyAnalyDetailsRecord> getDetailsRecords(String id)
    {
        TestingTask entity = baseDaoSupport.get(TestingTask.class, id);
        
        if (null == entity)
        {
            TestingSheet sheet = baseDaoSupport.get(TestingSheet.class, id);
            if (null == sheet)
            {
                return Collections.emptyList();
            }
            if (Collections3.isNotEmpty(sheet.getTestingSheetTaskList()))
            {
                TestingSheetTask sheetTask = Collections3.getFirst(sheet.getTestingSheetTaskList());
                entity = baseDaoSupport.get(TestingTask.class, sheetTask.getTestingTaskId());
                if (null == entity)
                {
                    return Collections.emptyList();
                }
            }
        }
        
        TestingSample mixedSample = entity.getInputSample();
        
        String hql = "FROM PoolingSample s WHERE s.mixedSample.id = :sampleId";
        List<PoolingSample> samples = baseDaoSupport.findByNamedParam(PoolingSample.class, hql, new String[] {"sampleId"}, new Object[] {mixedSample.getId()});
        
        if (CollectionUtils.isEmpty(samples))
        {
            return Collections.emptyList();
        }
        
        TestingSample sample;
        List<BiologyAnalyDetailsRecord> instanceRecords;
        Set<String> recordedLibrary = new HashSet<String>();
        SampleRelationContext sampleRelationContext = new SampleRelationContext();
        List<BiologyAnalyDetailsRecord> records = new ArrayList<BiologyAnalyDetailsRecord>();
        
        TaskConfig taskConfig = bcmadapter.getTaskConfigBySemantic(TaskSemantic.PCR_NGS);
        
        SampleTypeSimpleModel outputSampleType = bcmadapter.getSampleType(taskConfig.getOutputSampleId());
        
        if (null == outputSampleType)
        {
            throw new IllegalStateException();
        }
        
        for (PoolingSample ps : samples)
        {
            sample = ps.getSample();
            
            if (null == sample)
            {
                throw new IllegalStateException();
            }
            
            instanceRecords = getDetailsRecords(sample, mixedSample.getSampleCode(), sampleRelationContext, recordedLibrary, outputSampleType);
            
            if (CollectionUtils.isEmpty(instanceRecords))
            {
                continue;
            }
            
            records.addAll(instanceRecords);
        }
        
        // 处理参照样本合并
        Map<String, String> primaryMappings = sampleRelationContext.getRelationAsPrimaryKey2();
        Map<String, List<String>> referenceMappings = sampleRelationContext.getRelationAsReferenceKey2();
        
        List<BiologyAnalyDetailsRecord> result = new ArrayList<BiologyAnalyDetailsRecord>();
        
        String psrv;
        String pspv;
        String rsrv;
        String rspv;
        List<String> sl;
        boolean ignore;
        
        for (BiologyAnalyDetailsRecord record : records)
        {
            ignore = false;
            psrv = primaryMappings.get(record.getReceivedSampleCode());
            if (outputSampleType.getId().equals(record.getSampleTypeId()))// pcrngs
            {
                if (null != psrv)
                {
                    // pspv = referenceMappings.get(psrv);
                    sl = referenceMappings.get(psrv);
                    
                    if (Collections3.isNotEmpty(sl))
                    {
                        for (String s : sl)
                        {
                            if (s.equals(record.getReceivedSampleCode()))
                            {
                                record.setReferenceSampleCode(psrv);
                            }
                        }
                    }
                }
                else
                {
                    record.setReferenceSampleCode(record.getReceivedSampleCode());
                }
            }
            else
            {
                if (null != psrv)
                {
                    // pspv = referenceMappings.get(psrv);
                    sl = referenceMappings.get(psrv);
                    
                    if (Collections3.isNotEmpty(sl))
                    {
                        for (String s : sl)
                        {
                            if (s.equals(record.getReceivedSampleCode()))
                            {
                                record.setReferenceSampleCode(psrv);
                            }
                        }
                    }
                }
                else
                {
                    // rspv =
                    // referenceMappings.get(record.getReceivedSampleCode());
                    /*
                     * if (null != rspv) { rsrv = primaryMappings.get(rspv);
                     * 
                     * if (null != rsrv) { ignore = true; } }
                     */
                    List<String> sll = referenceMappings.get(record.getReceivedSampleCode());
                    if (Collections3.isNotEmpty(sll))
                    {
                        for (String s : sll)
                        {
                            rsrv = primaryMappings.get(s);
                            if (null != rsrv)
                            {
                                ignore = true;
                            }
                        }
                    }
                    
                }
            }
            
            if (!ignore)
            {
                result.add(record);
            }
        }
        
        // 获取对照样本详情，对照样本不在该任务单同样带出对照样本
        getRefernceSampleDetail(result, entity);
        
        // ww 2017.8.4
        if (Collections3.isNotEmpty(result))
        {
            for (BiologyAnalyDetailsRecord record : result)
            {
                if (outputSampleType.getId().equals(record.getSampleTypeId()))// pcrngs
                {
                    record.setReferenceSampleCode("");
                }
            }
        }
        return result;
    }
    
    private void getRefernceSampleDetail(List<BiologyAnalyDetailsRecord> result, TestingTask task)
    {
        for (BiologyAnalyDetailsRecord record : result)
        {
            
            if (StringUtils.isNotEmpty(record.getReferenceSampleCode()))
            {// 检测和对照在同一个任务单
            
                // 查询检测样本类型
                ReceivedSample primarySample = getReceivedSampleByCode(record.getReceivedSampleCode());
                
                record.setSampleName(null == primarySample ? "" : primarySample.getSampleName());
                record.setSampleType(null == primarySample ? "" : primarySample.getSampleTypeName());
                // 查询对照样本类型
                ReceivedSample subSample = getReceivedSampleByCode(record.getReferenceSampleCode());
                
                record.setReferenceSampleType(null == subSample ? "" : subSample.getSampleTypeName());
                
                // .....查询对照样本测序编号
                List<TestingSchedule> schedules = testingScheduleService.getRelatedSchedules(task.getId());
                if (Collections3.isEmpty(schedules))
                {
                    String hql =
                        "SELECT schedule FROM TestingScheduleHistory sh, TestingSchedule schedule WHERE sh.taskId = :taskId AND sh.scheduleId = schedule.id";
                    schedules = baseDaoSupport.findByNamedParam(TestingSchedule.class, hql, new String[] {"taskId"}, new Object[] {task.getId()});
                }
                for (TestingSchedule schedule : schedules)
                {
                    TestingSample testingSample = baseDaoSupport.get(TestingSample.class, schedule.getSampleId());
                    if (primarySample.getSampleId().equals(testingSample.getReceivedSample().getSampleId()))
                    {
                        List<TestingSchedule> subSchedules =
                            baseDaoSupport.findByNamedParam(TestingSchedule.class,
                                "from TestingSchedule t where t.orderId=:orderId and t.productId=:productId and t.methodId=:methodId and t.sampleId=(select s.id from TestingSample s where s.receivedSample.sampleId=:sampleId and s.parentSample.id is null)",
                                new String[] {"orderId", "productId", "methodId", "sampleId"},
                                new Object[] {schedule.getOrderId(), schedule.getProductId(), schedule.getMethodId(), subSample.getSampleId()});
                        if (Collections3.isNotEmpty(subSchedules))
                        {
                            String sequenceCode = testingScheduleService.getSequenceCode(Collections3.getFirst(subSchedules).getId());
                            record.setReferencePoolingCode(sequenceCode);
                        }
                    }
                }
            }
            else
            {
                List<OrderPrimarySample> primarySamples =
                    baseDaoSupport.findByNamedParam(OrderPrimarySample.class,
                        "from OrderPrimarySample o where o.sampleCode=:sampleCode",
                        new String[] {"sampleCode"},
                        new Object[] {record.getReceivedSampleCode()});
                if (Collections3.isNotEmpty(primarySamples))
                {// 分析样本是主样本
                    record.setFlag("sub");
                    // 查询检测样本类型
                    ReceivedSample mainSample = getReceivedSampleByCode(record.getReceivedSampleCode());
                    record.setSampleName(null == mainSample ? "" : mainSample.getSampleName());
                    record.setSampleType(null == mainSample ? "" : mainSample.getSampleTypeName());
                    // 查询对照样本
                    List<OrderSubsidiarySample> subSamples =
                        baseDaoSupport.findByNamedParam(OrderSubsidiarySample.class,
                            "from OrderSubsidiarySample o where o.order.id=:orderId and o.purpose=3",
                            new String[] {"orderId"},
                            new Object[] {Collections3.getFirst(primarySamples).getOrder().getId()});
                    if (Collections3.isNotEmpty(subSamples))
                    {
                        // 查询对照样本类型
                        ReceivedSample subReSample = getReceivedSampleByCode(Collections3.getFirst(subSamples).getSampleCode());
                        record.setReferenceSampleCode(null == subReSample ? "" : subReSample.getSampleCode());
                        record.setReferenceSampleType(null == subReSample ? "" : subReSample.getSampleTypeName());
                        // .....查询对照样本测序编号
                        List<TestingSchedule> schedules = testingScheduleService.getRelatedSchedules(task.getId());
                        if (Collections3.isEmpty(schedules))
                        {
                            String hql =
                                "SELECT schedule FROM TestingScheduleHistory sh, TestingSchedule schedule WHERE sh.taskId = :taskId AND sh.scheduleId = schedule.id";
                            schedules = baseDaoSupport.findByNamedParam(TestingSchedule.class, hql, new String[] {"taskId"}, new Object[] {task.getId()});
                        }
                        
                        for (TestingSchedule schedule : schedules)
                        {
                            TestingSample testingSample = baseDaoSupport.get(TestingSample.class, schedule.getSampleId());
                            if (mainSample.getSampleId().equals(testingSample.getReceivedSample().getSampleId()))
                            {
                                List<TestingSchedule> subSchedules =
                                    baseDaoSupport.findByNamedParam(TestingSchedule.class,
                                        "from TestingSchedule t where t.orderId=:orderId and t.productId=:productId and t.methodId=:methodId and t.sampleId=(select s.id from TestingSample s where s.receivedSample.sampleId=:sampleId and s.parentSample.id is null)",
                                        new String[] {"orderId", "productId", "methodId", "sampleId"},
                                        new Object[] {schedule.getOrderId(), schedule.getProductId(), schedule.getMethodId(), subReSample.getSampleId()});
                                if (Collections3.isNotEmpty(subSchedules))
                                {
                                    String sequenceCode = testingScheduleService.getSequenceCode(Collections3.getFirst(subSchedules).getId());
                                    record.setReferencePoolingCode(sequenceCode);
                                }
                            }
                        }
                    }
                }
                else
                {
                    List<OrderSubsidiarySample> subSamples =
                        baseDaoSupport.findByNamedParam(OrderSubsidiarySample.class,
                            "from OrderSubsidiarySample o where o.sampleCode=:sampleCode",
                            new String[] {"sampleCode"},
                            new Object[] {record.getReceivedSampleCode()});
                    if (Collections3.isNotEmpty(subSamples))
                    {
                        if (2 == Collections3.getFirst(subSamples).getPurpose())// 是检测样本
                        {
                            record.setFlag("sub");
                            // 查询检测样本类型
                            ReceivedSample mainSample = getReceivedSampleByCode(record.getReceivedSampleCode());
                            record.setSampleName(null == mainSample ? "" : mainSample.getSampleName());
                            record.setSampleType(null == mainSample ? "" : mainSample.getSampleTypeName());
                            // 查询对照样本
                            List<OrderSubsidiarySample> subbSamples =
                                baseDaoSupport.findByNamedParam(OrderSubsidiarySample.class,
                                    "from OrderSubsidiarySample o where o.order.id=:orderId and o.purpose=3",
                                    new String[] {"orderId"},
                                    new Object[] {Collections3.getFirst(subSamples).getOrder().getId()});
                            if (Collections3.isNotEmpty(subbSamples))
                            {
                                // 查询对照样本类型
                                ReceivedSample subReSample = getReceivedSampleByCode(Collections3.getFirst(subbSamples).getSampleCode());
                                record.setReferenceSampleCode(null == subReSample ? "" : subReSample.getSampleCode());
                                record.setReferenceSampleType(null == subReSample ? "" : subReSample.getSampleTypeName());
                                // .....查询对照样本测序编号
                                
                                List<TestingSchedule> schedules =
                                    baseDaoSupport.findByNamedParam(TestingSchedule.class,
                                        "from TestingSchedule t where 1=1 and EXISTS ("
                                            + " select sa.schedule.id from TestingScheduleActive sa where sa.taskId = :taskId and sa.schedule.id = t.id)",
                                        new String[] {"taskId"},
                                        new Object[] {task.getId()});
                                
                                for (TestingSchedule schedule : schedules)
                                {
                                    TestingSample testingSample = baseDaoSupport.get(TestingSample.class, schedule.getSampleId());
                                    if (mainSample.getSampleId().equals(testingSample.getReceivedSample().getSampleId()))
                                    {
                                        List<TestingSchedule> subSchedules =
                                            baseDaoSupport.findByNamedParam(TestingSchedule.class,
                                                "from TestingSchedule t where t.orderId=:orderId and t.productId=:productId and t.methodId=:methodId and t.sampleId=(select s.id from TestingSample s where s.receivedSample.sampleId=:sampleId and s.parentSample.id is null)",
                                                new String[] {"orderId", "productId", "methodId", "sampleId"},
                                                new Object[] {schedule.getOrderId(), schedule.getProductId(), schedule.getMethodId(), subReSample.getSampleId()});
                                        if (Collections3.isNotEmpty(subSchedules))
                                        {
                                            String sequenceCode = testingScheduleService.getSequenceCode(Collections3.getFirst(subSchedules).getId());
                                            record.setReferencePoolingCode(sequenceCode);
                                        }
                                    }
                                }
                                
                            }
                        }
                        else if (3 == Collections3.getFirst(subSamples).getPurpose())// 对照样本
                        {
                            record.setFlag("main");
                            record.setReferenceSampleCode(record.getReceivedSampleCode());
                            // 查询对照样本类型
                            ReceivedSample subReSample = getReceivedSampleByCode(Collections3.getFirst(subSamples).getSampleCode());
                            record.setReferenceSampleCode(null == subReSample ? "" : subReSample.getSampleCode());
                            record.setReferenceSampleType(null == subReSample ? "" : subReSample.getSampleTypeName());
                            
                            // 查询检测样本
                            List<ReceivedSample> mainSamples =
                                baseDaoSupport.findByNamedParam(ReceivedSample.class,
                                    "from ReceivedSample r where r.orderId=:orderId and (r.purpose is null or r.purpose = 2)",
                                    new String[] {"orderId"},
                                    new Object[] {Collections3.getFirst(subSamples).getOrder().getId()});
                            record.setReceivedSampleCode(Collections3.extractToString(mainSamples, "sampleCode", "/"));
                            record.setSampleName(Collections3.extractToString(mainSamples, "sampleName", "/"));
                            record.setSampleType(Collections3.extractToString(mainSamples, "sampleTypeName", "/"));
                            // .....查询检测样本测序编号
                            List<String> mainSequnceCode = new ArrayList<>();
                            for (ReceivedSample mainSample : mainSamples)
                            {
                                List<TestingSchedule> schedules =
                                    baseDaoSupport.findByNamedParam(TestingSchedule.class, "from TestingSchedule t where 1=1 and EXISTS ("
                                        + " select sa.schedule.id from TestingScheduleActive sa where sa.taskId= :taskId and sa.schedule.id = t.id)",
                                    
                                    new String[] {"taskId"}, new Object[] {task.getId()});
                                
                                for (TestingSchedule schedule : schedules)
                                {
                                    TestingSample testingSample = baseDaoSupport.get(TestingSample.class, schedule.getSampleId());
                                    if (subReSample.getSampleId().equals(testingSample.getReceivedSample().getSampleId()))
                                    {
                                        List<TestingSchedule> mainSchedules =
                                            baseDaoSupport.findByNamedParam(TestingSchedule.class,
                                                "from TestingSchedule t where t.orderId=:orderId and t.productId=:productId and t.methodId=:methodId and t.sampleId=(select s.id from TestingSample s where s.receivedSample.sampleId=:sampleId and s.parentSample.id is null)",
                                                new String[] {"orderId", "productId", "methodId", "sampleId"},
                                                new Object[] {schedule.getOrderId(), schedule.getProductId(), schedule.getMethodId(), mainSample.getSampleId()});
                                        if (Collections3.isNotEmpty(mainSchedules))
                                        {
                                            String sequenceCode = testingScheduleService.getSequenceCode(Collections3.getFirst(mainSchedules).getId());
                                            mainSequnceCode.add(sequenceCode);
                                        }
                                    }
                                }
                            }
                            record.setReferencePoolingCode(Collections3.convertToString(mainSequnceCode, "/"));
                            
                            // .....查询检测样本测序编号---------结束
                            
                        }
                    }
                    else
                    {// 科研样本
                        ReceivedSample primarySample = getReceivedSampleByCode(record.getReceivedSampleCode());
                        
                        record.setSampleName(null == primarySample ? "" : primarySample.getSampleName());
                        record.setSampleType(null == primarySample ? "" : primarySample.getSampleTypeName());
                    }
                    
                }
            }
        }
        
    }
    
    public ReceivedSample getReceivedSampleByCode(String sampleCode)
    {
        List<ReceivedSample> samples =
            baseDaoSupport.findByNamedParam(ReceivedSample.class,
                "from ReceivedSample o where o.sampleCode=:sampleCode",
                new String[] {"sampleCode"},
                new Object[] {sampleCode});
        if (Collections3.isNotEmpty(samples))
        {
            return Collections3.getFirst(samples);
        }
        return null;
    }
    
    @Override
    @Transactional
    public void submit(BiologyAnalySubmitRequest request, String token)
    {
        BiologyAnalySubmitContext context = generateSubmitContext(request, token);
        
        // 1、更新实验任务状态
        doUpdateTestingTask(context);
        
        // 2、创建下一节点任务
        doCreateNextNodeTasks(context);
        
        // 3、更新流程节点
        doUpdateTestingSchedules(context);
        
        // 4、创建任务单并提交结果
        doCreateAndSubmitSheet(context);
    }
    
    private BiologyAnalySubmitContext generateSubmitContext(BiologyAnalySubmitRequest request, String token)
    {
        TestingTask entity = baseDaoSupport.get(TestingTask.class, request.getId());
        
        if (null == entity)
        {
            throw new IllegalStateException();
        }
        
        TestingSample mixedSample = entity.getInputSample();
        
        String hql = "FROM PoolingSample s WHERE s.mixedSample.id = :sampleId";
        List<PoolingSample> samples = baseDaoSupport.findByNamedParam(PoolingSample.class, hql, new String[] {"sampleId"}, new Object[] {mixedSample.getId()});
        
        if (CollectionUtils.isEmpty(samples))
        {
            throw new IllegalStateException();
        }
        
        BiologyAnalySubmitContext context = new BiologyAnalySubmitContext();
        context.setContextForSubmiter(smmadapter.getLoginUser(token));
        context.setContextForTestingTask(entity);
        context.setContextForMixedSample(mixedSample);
        
        TaskConfig taskConfig = bcmadapter.getTaskConfigBySemantic(TaskSemantic.PCR_NGS);
        
        SampleTypeSimpleModel outputSampleType = bcmadapter.getSampleType(taskConfig.getOutputSampleId());
        
        if (null == outputSampleType)
        {
            throw new IllegalStateException();
        }
        
        // 混样样本分解
        for (PoolingSample ps : samples)
        {
            setContextForPoolingSample(ps, context, outputSampleType);
        }
        
        List<TestingSchedule> schedules = testingScheduleService.getRelatedSchedules(entity.getId());
        
        TaskConfig scheduleNextNodeConfig;
        // 判断合同订单是否只走到生信分析
        
        for (TestingSchedule schedule : schedules)
        {
            String nodeStr = schedule.getScheduleNodes();
            List<String> nodes = new ArrayList<String>(Arrays.asList(schedule.getScheduleNodes().split("\\/")));
            // 如果关联合同选择了原始数据或者技术分析，则只到生信分析节点
            Order order1 = baseDaoSupport.get(Order.class, schedule.getOrderId());
            DataSendingModel model = null;
            ConcludingReportModel concludingReportModel = null;
            if (null != order1 && null != order1.getContract())
            {
                String techAnaly = TaskSemantic.TECHNICAL_ANALY;
                List<ContractContent> contractContents =
                    baseDaoSupport.find(ContractContent.class, "from ContractContent c where c.contractId='" + order1.getContract().getId() + "'");
                if (Collections3.isNotEmpty(contractContents) && StringUtils.isNotEmpty(contractContents.get(0).getDeliveryMode()))
                {
                    String[] modes = Collections3.getFirst(contractContents).getDeliveryMode().split(",");
                    List<String> modeList = Arrays.asList(modes);
                    if (modeList.contains("1") || modeList.contains("2"))
                    {// 选择原始数据或者分析数据，进入数据发送
                        if (TaskSemantic.TECHNICAL_ANALY.equals(nodes.get(nodes.size() - 1)))
                        {
                            techAnaly = nodes.get(nodes.size() - 1);
                            nodes.remove(nodes.size() - 1);
                            nodeStr = Collections3.convertToString(nodes, "/");
                            
                        }
                        
                        model = new DataSendingModel();
                        model.setOrderId(schedule.getOrderId());
                    }
                    
                    if (modeList.contains("3") && null != order1.getType() && "4".equals(order1.getType().getId()))
                    {
                        // 如果科研订单选择了结题报告
                        if (TaskSemantic.TECHNICAL_ANALY.equals(nodes.get(nodes.size() - 1)))
                        {
                            techAnaly = nodes.get(nodes.size() - 1);
                            nodes.remove(nodes.size() - 1);
                            nodeStr = Collections3.convertToString(nodes, "/");
                            
                        }
                        concludingReportModel = new ConcludingReportModel();
                        concludingReportModel.setOrderId(schedule.getOrderId());
                    }
                    if (modeList.contains("3") && null != order1.getType() && !"4".equals(order1.getType().getId()))
                    {
                        // 非科研单子选了结题报告需要走到技术分析
                        if (TaskSemantic.BIOLOGY_ANALY.equals(nodes.get(nodes.size() - 1)))
                        {
                            nodes.add(techAnaly);
                            nodeStr = Collections3.convertToString(nodes, "/");
                            
                        }
                    }
                    
                }
            }
            
            schedule.setScheduleNodes(nodeStr);
            
            // 数据发送
            if (null != model)
            {
                testingScheduleService.dataSendingMessage(model);
            }
            if (null != concludingReportModel)
            {// 结题报告
                testingScheduleService.concludingReportMessage(concludingReportModel);
            }
            
        }
        
        for (TestingSchedule schedule : schedules)
        {
            scheduleNextNodeConfig = testingScheduleService.getScheduleNextNodeConfig(schedule, entity.getSemantic());
            
            if (null == scheduleNextNodeConfig)
            {
                // 没有下一个节点，到生信分析就结束
                schedule.setActiveTask("已完成");
                schedule.setEndTime(new Date());
                schedule.setEndType(TestingSchedule.END_SUCCESS);
                baseDaoSupport.update(schedule);
                
                List<TestingScheduleActive> actives = testingScheduleService.getScheduleActives(schedule.getId());
                
                if (!CollectionUtils.isEmpty(actives))
                {
                    for (TestingScheduleActive active : actives)
                    {
                        baseDaoSupport.delete(active);
                    }
                }
            }
            
            context.setContextForTestingSchedule(schedule, scheduleNextNodeConfig);
        }
        
        return context;
    }
    
    private void setContextForPoolingSample(PoolingSample poolingSample, BiologyAnalySubmitContext context, SampleTypeSimpleModel outputSampleType)
    {
        TestingSample sample = poolingSample.getSample();
        
        if (null == sample)
        {
            log.error("Pooling sample details is null, key {}", poolingSample.getId());
            throw new IllegalStateException();
        }
        
        // 捕获产物
        if (null == sample.getParentSample())
        {
            // PCR-NGS产物
            if (outputSampleType.getId().equals(sample.getSampleTypeId()))
            {
                TestingSample pcrNgsSample = testingSampleService.getTestingSampleByCode(sample.getSampleCode());
                
                TestingSchedule pcrNgsSchedule;
                
                if (null == pcrNgsSample)
                {
                    log.error("Can not found pcr-ngs sample by code {}", sample.getSampleCode());
                    throw new IllegalStateException();
                }
                
                List<TestingPcrNgsSample> pcrNgsSamples = getListByOutputSampleId(pcrNgsSample.getId());
                
                if (Collections3.isEmpty(pcrNgsSamples))
                {
                    throw new IllegalStateException();
                }
                
                for (TestingPcrNgsSample pcrNgsSampleTemp : pcrNgsSamples)
                {
                    pcrNgsSchedule = pcrNgsSampleTemp.getTestingSchedule();
                    
                    if (null == pcrNgsSchedule)
                    {
                        throw new IllegalStateException();
                    }
                    
                    context.setContextForLibrarySample(pcrNgsSampleTemp.getInputSample(), pcrNgsSampleTemp.getLibraryIndex().toString(), pcrNgsSchedule);
                }
            }
            else
            {
                LibraryCaptureSampleAttributes attributes = JsonUtils.asObject(sample.getAttributes(), LibraryCaptureSampleAttributes.class);
                
                if (null == attributes)
                {
                    log.error("Library capture sample attributes is null, sample id {}, sample code {}", sample.getId(), sample.getSampleCode());
                    throw new IllegalStateException();
                }
                
                Set<CaptureLibraryAttributes> libraries = attributes.getLibraries();
                
                if (CollectionUtils.isEmpty(libraries))
                {
                    log.error("Library capture sample contains no libraries, sample id {}, sample code {}", sample.getId(), sample.getSampleCode());
                    throw new IllegalStateException();
                }
                
                TestingSample librarySample;
                List<TestingSchedule> libraryRelatedSchedules;
                
                for (CaptureLibraryAttributes library : libraries)
                {
                    if (context.containsLibrary(library.getSampleCode()))
                    {
                        continue;
                    }
                    
                    librarySample = testingSampleService.getTestingSampleByCode(library.getSampleCode());
                    
                    if (null == librarySample)
                    {
                        log.error("Can not found library for code {}", library.getSampleCode());
                        throw new IllegalStateException();
                    }
                    
                    libraryRelatedSchedules = testingScheduleService.getRelatedSchedulesBySampleId(librarySample.getId());
                    
                    if (CollectionUtils.isEmpty(libraryRelatedSchedules) || libraryRelatedSchedules.size() > 1)
                    {
                        log.error("Can not found unique schedule for sample code {}", library.getSampleCode());
                        throw new IllegalStateException();
                    }
                    
                    context.setContextForLibrarySample(librarySample, library.getIndex(), libraryRelatedSchedules.get(0));
                }
            }
            
        }
        else
        {
            if (context.containsLibrary(sample.getSampleCode()))
            {
                return;
            }
            
            LibraryAttributes attributes = JsonUtils.asObject(sample.getAttributes(), LibraryAttributes.class);
            
            if (null == attributes)
            {
                throw new IllegalStateException();
            }
            
            List<TestingSchedule> libraryRelatedSchedules = testingScheduleService.getRelatedSchedulesBySampleId(sample.getId());
            
            if (CollectionUtils.isEmpty(libraryRelatedSchedules) || libraryRelatedSchedules.size() > 1)
            {
                throw new IllegalStateException();
            }
            
            context.setContextForLibrarySample(sample, attributes.getIndex(), libraryRelatedSchedules.get(0));
        }
    }
    
    private void doUpdateTestingTask(BiologyAnalySubmitContext context)
    {
        BiologyAnalySubmitTaskContext taskContext = context.getSubmitTaskContext();
        
        TestingTask task = taskContext.getTestingTaskEntity();
        task.setEndTime(new Date());
        task.setStatus(TestingTask.STATUS_END);
        task.setEndType(TestingTask.END_SUCCESS);
        baseDaoSupport.update(task);
        
        TestingTaskResult result = new TestingTaskResult();
        result.setTaskId(task.getId());
        result.setResult(TestingTaskResult.RESULT_SUCCESS);
        baseDaoSupport.insert(result);
    }
    
    private void doCreateNextNodeTasks(BiologyAnalySubmitContext context)
    {
        Set<BiologyAnalySubmitNextTaskContext> nextTasks = context.getNextTasksContext();
        
        TestingTask task;
        TestingTaskRunVariable variables;
        TestingSample inputSample;
        Date timestamp = new Date();
        
        for (BiologyAnalySubmitNextTaskContext nextTask : nextTasks)
        {
            task = new TestingTask();
            task.setName(nextTask.getTaskName());
            task.setSemantic(nextTask.getTaskSemantic());
            task.setStatus(TestingTask.STATUS_ASSIGNABLE);
            task.setResubmit(false);
            task.setResubmitCount(0);
            task.setStartTime(timestamp);
            inputSample = baseDaoSupport.get(TestingSample.class, nextTask.getInputSampleId());
            if (null == inputSample)
            {
                throw new IllegalStateException();
            }
            // inputSample = new TestingSample();
            // inputSample.setId(nextTask.getInputSampleId());
            task.setInputSample(inputSample);
            
            // 特殊处理测序编号
            task.setTestingLaneCode(context.getSequencingCode());
            baseDaoSupport.insert(task);
            
            variables = new TestingTaskRunVariable();
            variables.setTestingTaskId(task.getId());
            baseDaoSupport.insert(variables);
            context.setContextForCreateNextNodeTask(nextTask.getTemporaryId(), task);
            
            if (TaskSemantic.TECHNICAL_ANALY.equals(task.getSemantic()) || TaskSemantic.PCR_NGS_DATA_ANALYSIS.equals(task.getSemantic()))
            {
                TechnicalAnalyTestingTask tatt = new TechnicalAnalyTestingTask();
                tatt.setTaskId(task.getId());
                tatt.setSequencingCode(context.getSequencingCode());
                baseDaoSupport.insert(tatt);
            }
        }
    }
    
    private void doUpdateTestingSchedules(BiologyAnalySubmitContext context)
    {
        Set<BiologyAnalySubmitScheduleContext> schedules = context.getSchedulesContext();
        
        TestingSchedule schedule;
        TestingTask nextTask;
        Date timestamp = new Date();
        
        for (BiologyAnalySubmitScheduleContext scheduleContext : schedules)
        {
            schedule = scheduleContext.getTestingScheduleEntity();
            // 处理旧数据，已经完成了流程不需要再生成结束分析节点，后续可以删除这个补丁
            if (StringUtils.isEmpty(schedule.getEndType()))
            {
                nextTask = context.getScheduleNextTask(schedule.getId());
                if (null != nextTask)
                {
                    testingScheduleService.gotoNextNode(schedule, TaskSemantic.BIOLOGY_ANALY, nextTask, timestamp);
                }
            }
            
        }
        
        // 更新冗余信息,测序编号已经在上一步特殊处理
        
        Iterator<BiologyAnalySubmitNextTaskContext> it = context.getNextTasksContext().iterator();
        while (it.hasNext())
        {
            BiologyAnalySubmitNextTaskContext i = it.next();
            TestingTask task = i.getTestingTaskEntity();
            testingTaskService.updateTaskRedundantColumn(Arrays.asList(task), 0);
            
        }
        
    }
    
    private void doCreateAndSubmitSheet(BiologyAnalySubmitContext context)
    {
        Date timestamp = new Date();
        UserMinimalModel submiter = context.getSubmiter();
        TaskConfig taskConfig = bcmadapter.getTaskConfigBySemantic(TaskSemantic.BIOLOGY_ANALY);
        
        TestingSheetCreateModel model = new TestingSheetCreateModel();
        model.setCode(commonService.getTaskCodeBySemantic(TaskSemantic.BIOLOGY_ANALY));
        model.setTaskSemantic(TaskSemantic.BIOLOGY_ANALY);
        model.setTaskName(taskConfig.getName());
        model.setTesterId(submiter.getId());
        model.setTesterName(submiter.getName());
        model.setAssignerId(submiter.getId());
        model.setAssignerName(submiter.getName());
        model.setAssignTime(timestamp);
        model.setCreateTime(timestamp);
        model.setTasks(Collections.singletonList(context.getSubmitTaskContext().getTestingTaskEntity().getId()));
        TestingSheet sheet = testingSheetService.create(model);
        
        sheet.setSubmiterId(submiter.getId());
        sheet.setSubmiterName(submiter.getName());
        sheet.setSubmitTime(new Date());
        baseDaoSupport.update(sheet);
    }
    
    @Override
    public List<BiologyAnalySampleRecord> getSampleRecords(TestingSample sample, String poolingCode, Set<String> recordedLibraries, SampleTypeSimpleModel outputSampleType)
    {
        if (null == sample.getParentSample())
        {
            if (log.isDebugEnabled())
            {
                log.debug("Start to parse sample records for capture.");
            }
            
            LibraryCaptureSampleAttributes attributes = JsonUtils.asObject(sample.getAttributes(), LibraryCaptureSampleAttributes.class);
            
            if (null == attributes)
            {
                return Collections.emptyList();
            }
            
            Set<CaptureLibraryAttributes> libraries = attributes.getLibraries();
            
            if (CollectionUtils.isEmpty(libraries))
            {
                return Collections.emptyList();
            }
            
            BiologyAnalySampleRecord record;
            TestingSample librarySample;
            List<BiologyAnalySampleRecord> records = new ArrayList<BiologyAnalySampleRecord>();
            Map<String, TestingPcrNgsSample> maps = Maps.newHashMap();
            Map<String, String> locationMaps = Maps.newHashMap();
            String inputSampleId;
            String analysisLocation;
            String receivedSampleCode;
            if (outputSampleType.getId().equals(sample.getSampleTypeId()))
            {
                List<TestingPcrNgsSample> samples = getListByOutputSampleId(sample.getId());
                for (TestingPcrNgsSample pcrSample : samples)
                {
                    inputSampleId = pcrSample.getInputSample().getId();
                    if (!maps.containsKey(inputSampleId))
                    {
                        maps.put(inputSampleId, pcrSample);
                    }
                    if (locationMaps.containsKey(inputSampleId))
                    {
                        analysisLocation = locationMaps.get(inputSampleId) + "," + getAnalysisLocationByCombineCode(pcrSample.getCombineCode());
                    }
                    else
                    {
                        analysisLocation = getAnalysisLocationByCombineCode(pcrSample.getCombineCode());
                    }
                    locationMaps.put(inputSampleId, analysisLocation);
                    
                }
                for (TestingPcrNgsSample testingPcrNgsSample : maps.values())
                {
                    receivedSampleCode = testingPcrNgsSample.getInputSample().getReceivedSample().getSampleCode();
                    record = new BiologyAnalySampleRecord();
                    record.setPoolingCode(poolingCode);
                    record.setReceivedSampleCode(receivedSampleCode);
                    record.setLibraryIndex(testingPcrNgsSample.getLibraryIndex().toString());
                    record.setRecordCode(getPcrRocordCode(sample.getSampleCode(), receivedSampleCode));
                    records.add(record);
                }
            }
            else
            {
                for (CaptureLibraryAttributes library : libraries)
                {
                    librarySample = testingSampleService.getTestingSampleByCode(library.getSampleCode());
                    
                    if (null == librarySample)
                    {
                        continue;
                    }
                    
                    if (recordedLibraries.contains(librarySample.getId()))
                    {
                        continue;
                    }
                    
                    String code = getRecordCode(librarySample.getReceivedSample().getSampleCode(), librarySample.getId());
                    
                    // 文库捕获产物中存在因其他探针失败而无法继续的样本，需要过滤掉
                    if (null == code)
                    {
                        recordedLibraries.add(librarySample.getId());
                        continue;
                    }
                    else
                    {
                        record = new BiologyAnalySampleRecord();
                        record.setPoolingCode(poolingCode);
                        record.setReceivedSampleCode(librarySample.getReceivedSample().getSampleCode());
                        record.setLibraryIndex(library.getIndex());
                        record.setRecordCode(code);
                        records.add(record);
                    }
                    
                    recordedLibraries.add(librarySample.getId());
                }
            }
            
            return records;
        }
        else
        {
            if (log.isDebugEnabled())
            {
                log.debug("Start to parse sample records for library.");
            }
            
            if (recordedLibraries.contains(sample.getId()))
            {
                return Collections.emptyList();
            }
            
            LibraryAttributes attributes = JsonUtils.asObject(sample.getAttributes(), LibraryAttributes.class);
            
            if (null == attributes)
            {
                return Collections.emptyList();
            }
            
            BiologyAnalySampleRecord record = new BiologyAnalySampleRecord();
            record.setPoolingCode(poolingCode);
            record.setReceivedSampleCode(sample.getReceivedSample().getSampleCode());
            record.setLibraryIndex(attributes.getIndex());
            record.setRecordCode(getRecordCode(record.getReceivedSampleCode(), sample.getId()));
            recordedLibraries.add(sample.getId());
            return Collections.singletonList(record);
        }
    }
    
    private List<BiologyAnalyDetailsRecord> getDetailsRecords(TestingSample sample, String poolingCode, SampleRelationContext sampleRelationContext, Set<String> recordedLibraries, SampleTypeSimpleModel outputSampleType)
    {
        if (null == sample.getParentSample())
        {
            LibraryCaptureSampleAttributes attributes = JsonUtils.asObject(sample.getAttributes(), LibraryCaptureSampleAttributes.class);
            
            if (null == attributes)
            {
                return Collections.emptyList();
            }
            
            Set<CaptureLibraryAttributes> libraries = attributes.getLibraries();
            
            if (CollectionUtils.isEmpty(libraries))
            {
                return Collections.emptyList();
            }
            
            TestingSample librarySample;
            List<BiologyAnalyDetailsRecord> libraryRecords;
            List<BiologyAnalyDetailsRecord> records = new ArrayList<BiologyAnalyDetailsRecord>();
            Map<String, TestingPcrNgsSample> maps = Maps.newHashMap();
            Map<String, String> locationMaps = Maps.newHashMap();
            String inputSampleId;
            String analysisLocation = "";
            String receivedSampleCode;
            BiologyAnalyDetailsRecord recordDetail;
            TestingSchedule testingSchedule;
            
            if (outputSampleType.getId().equals(sample.getSampleTypeId()))
            {
                List<TestingPcrNgsSample> samples = getListByOutputSampleId(sample.getId());
                for (TestingPcrNgsSample pcrSample : samples)
                {
                    inputSampleId = pcrSample.getInputSample().getReceivedSample().getSampleId();
                    if (!maps.containsKey(inputSampleId))
                    {
                        maps.put(inputSampleId, pcrSample);
                    }
                    if (locationMaps.containsKey(inputSampleId))
                    {
                        analysisLocation = locationMaps.get(inputSampleId) + "," + getAnalysisLocationByCombineCode(pcrSample.getCombineCode());
                    }
                    else
                    {
                        analysisLocation = getAnalysisLocationByCombineCode(pcrSample.getCombineCode());
                    }
                    locationMaps.put(inputSampleId, analysisLocation);
                    
                }
                for (TestingPcrNgsSample testingPcrNgsSample : maps.values())
                {
                    testingSchedule = testingPcrNgsSample.getTestingSchedule();
                    ReceivedSample receivedSample = testingPcrNgsSample.getInputSample().getReceivedSample();
                    String sampleCode = receivedSample.getSampleCode();
                    String productId = testingSchedule.getProductId();
                    BioSampleSimpleModel assm = new BioSampleSimpleModel(testingSchedule.getOrderId(), productId, sampleCode);
                    Product product = baseDaoSupport.get(Product.class, productId);
                    OrderSimpleModel orderModel = null;
                    if (sampleRelationContext.getOrderSimpleModel().containsKey(assm.getOrderId() + "-" + assm.getProductId() + "-" + assm.getSampleCode()))
                    {
                        orderModel =
                            sampleRelationContext.getOrderSimpleModel().get(assm.getOrderId() + "-" + assm.getProductId() + "-" + assm.getSampleCode());
                    }
                    else
                    {
                        orderModel = bmmadapter.getOrder(assm);
                        sampleRelationContext.getOrderSimpleModel().put(assm.getOrderId() + "-" + assm.getProductId() + "-" + assm.getSampleCode(), orderModel);
                    }
                    String sampleName = receivedSample.getSampleName();
                    
                    String familyRelation = "";
                    if (StringUtils.isEmpty(orderModel.getFamilyRelation()))// 科技订单没有家系关系
                                                                            // 为空，不区分男女
                    {
                        familyRelation = "";
                    }
                    else
                    {
                        familyRelation = dictAdapter.getDictTestByCategoryAndValue("FAMILY_RELATION", orderModel.getFamilyRelation());
                    }
                    String sex = "";
                    
                    if (StringUtils.isEmpty(familyRelation))
                    {
                        sex = StringUtils.isNotEmpty(orderModel.getExamineeSex()) ? orderModel.getExamineeSex() : "未知";
                    }
                    else
                    {
                        FamilyRelationSex frs = new FamilyRelationSex();
                        String val = frs.map.get(familyRelation);
                        if (StringUtils.isNotEmpty(val))
                        {
                            if ("本人".equals(val))
                            {
                                sex = orderModel.getExamineeSex();
                            }
                            else
                            {
                                sex = val;
                            }
                        }
                        else
                        {
                            sex = "未知";
                        }
                    }
                    
                    receivedSampleCode = testingPcrNgsSample.getInputSample().getReceivedSample().getSampleCode();
                    recordDetail = new BiologyAnalyDetailsRecord();
                    recordDetail.setPoolingCode(poolingCode);
                    recordDetail.setOrderCode(orderModel.getOrderCode());
                    recordDetail.setRecordCode(getPcrRocordCode(sample.getSampleCode(), receivedSampleCode));
                    recordDetail.setReceivedSampleCode(receivedSampleCode);
                    recordDetail.setLibraryIndex(testingPcrNgsSample.getLibraryIndex().toString());
                    recordDetail.setCoordinate(locationMaps.get(testingPcrNgsSample.getInputSample().getReceivedSample().getSampleId()).replace("，", ","));
                    recordDetail.setContractCode(orderModel.getContractCode());
                    recordDetail.setExamineeSex(sex);
                    recordDetail.setTestingType(orderModel.getMarketingCenter());
                    recordDetail.setRemark(orderModel.getRemark());
                    recordDetail.setExamineeName(sampleName);
                    recordDetail.setProductCode(product == null ? "" : product.getCode());
                    recordDetail.setProductName(product == null ? "" : product.getName());
                    recordDetail.setFamilyRelation(familyRelation);
                    recordDetail.setExamineeDiagnosis(orderModel.getDiagnosisName());
                    recordDetail.setExamineeGenes(orderModel.getGeneName());
                    recordDetail.setExamineePhenotypes(orderModel.getExamineePhenotypes());
                    recordDetail.setMedicalHistory(orderModel.getMedicalHistory());
                    recordDetail.setFamilyMedicalHistory(orderModel.getFamilyMedicalHistory());
                    recordDetail.setClinicalRecommend(orderModel.getClinicalRecommend());
                    recordDetail.setSampleTypeId(sample.getSampleTypeId());
                    records.add(recordDetail);
                    sampleRelationContext.addSample2(orderModel.getId(), testingPcrNgsSample.getInputSample().getReceivedSample());
                    
                }
            }
            else
            {
                for (CaptureLibraryAttributes library : libraries)
                {
                    librarySample = testingSampleService.getTestingSampleByCode(library.getSampleCode());
                    
                    if (null == librarySample)
                    {
                        continue;
                    }
                    
                    if (recordedLibraries.contains(librarySample.getId()))
                    {
                        continue;
                    }
                    
                    libraryRecords = getDetailsRecordByLibrary(librarySample, poolingCode, sampleRelationContext);
                    recordedLibraries.add(librarySample.getId());
                    
                    if (!CollectionUtils.isEmpty(libraryRecords))
                    {
                        records.addAll(libraryRecords);
                    }
                }
            }
            
            return records;
        }
        else
        {
            if (recordedLibraries.contains(sample.getId()))
            {
                return Collections.emptyList();
            }
            
            List<BiologyAnalyDetailsRecord> records = getDetailsRecordByLibrary(sample, poolingCode, sampleRelationContext);
            recordedLibraries.add(sample.getId());
            
            if (null == records)
            {
                return Collections.emptyList();
            }
            
            return records;
        }
    }
    
    private List<BiologyAnalyDetailsRecord> getDetailsRecordByLibrary(TestingSample library, String poolingCode, SampleRelationContext sampleRelationContext)
    {
        TestingTask recordTask = getRecordTask(library.getId());
        
        if (null == recordTask)
        {
            return null;
        }
        
        String taskId = recordTask.getId();
        List<Product> products = testingTaskService.getRelatedProducts(taskId);
        
        if (CollectionUtils.isEmpty(products) || products.size() > 1)
        {
            return Collections.emptyList();
        }
        
        Product product = products.get(0);
        
        List<TestingMethod> methods = testingTaskService.getRelatedTestingMethods(taskId);
        
        if (CollectionUtils.isEmpty(methods) || methods.size() > 1)
        {
            return Collections.emptyList();
        }
        
        TestingMethod method = methods.get(0);
        String recordCode =
            String.format("%1$s_%2$s_%3$s", library.getReceivedSample().getSampleCode(), product.getCode(), getRecordMethodName(method.getName()));
        List<String> coordinates = bcmadapter.getAnalyCoordinates(product.getId(), method.getId());
        
        Order order = testingTaskService.getRelatedOrder(taskId);
        
        if (null == order)
        {
            return Collections.emptyList();
        }
        
        ReceivedSample receivedSample = recordTask.getInputSample().getReceivedSample();
        String sampleCode = receivedSample.getSampleCode();
        
        sampleRelationContext.addSample2(order.getId(), library.getReceivedSample());
        BioSampleSimpleModel bssm = new BioSampleSimpleModel(order.getId(), product.getId(), sampleCode);
        OrderSimpleModel orderModel = null;
        if (sampleRelationContext.getOrderSimpleModel().containsKey(bssm.getOrderId() + "-" + bssm.getProductId() + "-" + bssm.getSampleCode()))
        {
            orderModel = sampleRelationContext.getOrderSimpleModel().get(bssm.getOrderId() + "-" + bssm.getProductId() + "-" + bssm.getSampleCode());
        }
        else
        {
            orderModel = bmmadapter.getOrder(bssm);
            sampleRelationContext.getOrderSimpleModel().put(bssm.getOrderId() + "-" + bssm.getProductId() + "-" + bssm.getSampleCode(), orderModel);
        }
        
        String sampleName = receivedSample.getSampleName();
        String familyRelation = "";
        if (StringUtils.isEmpty(orderModel.getFamilyRelation()))// 科技订单没有家系关系
                                                                // 为空，不区分男女
        {
            familyRelation = "";
        }
        else
        {
            familyRelation = dictAdapter.getDictTestByCategoryAndValue("FAMILY_RELATION", orderModel.getFamilyRelation());
        }
        String sex = "";
        if (StringUtils.isEmpty(familyRelation))
        {
            sex = StringUtils.isNotEmpty(orderModel.getExamineeSex()) ? orderModel.getExamineeSex() : "未知";
        }
        else
        {
            FamilyRelationSex frs = new FamilyRelationSex();
            String val = frs.map.get(familyRelation);
            if (StringUtils.isNotEmpty(val))
            {
                if ("本人".equals(val))
                {
                    sex = orderModel.getExamineeSex();
                }
                else
                {
                    sex = val;
                }
            }
            else
            {
                sex = "未知";
            }
        }
        
        LibraryAttributes attributes = JsonUtils.asObject(library.getAttributes(), LibraryAttributes.class);
        
        List<BiologyAnalyDetailsRecord> records = new ArrayList<BiologyAnalyDetailsRecord>();
        
        if (CollectionUtils.isEmpty(coordinates))
        {
            BiologyAnalyDetailsRecord record = new BiologyAnalyDetailsRecord();
            record.setPoolingCode(poolingCode);
            record.setRecordCode(recordCode);
            record.setReceivedSampleCode(library.getReceivedSample().getSampleCode());
            record.setLibrarySampleCode(library.getSampleCode());
            record.setOrderCode(orderModel.getOrderCode());
            record.setLibraryIndex(attributes.getIndex());
            record.setContractCode(orderModel.getContractCode().replace("，", ","));
            record.setExamineeSex(sex);
            record.setTestingType(orderModel.getMarketingCenter());
            record.setRemark(orderModel.getRemark());
            record.setExamineeName(sampleName);
            record.setProductCode(product.getCode());
            record.setProductName(product.getName());
            record.setFamilyRelation(familyRelation);
            record.setExamineeDiagnosis(orderModel.getDiagnosisName());
            record.setExamineeGenes(orderModel.getGeneName());
            record.setExamineePhenotypes(orderModel.getExamineePhenotypes());
            record.setMedicalHistory(orderModel.getMedicalHistory());
            record.setFamilyMedicalHistory(orderModel.getFamilyMedicalHistory());
            record.setClinicalRecommend(orderModel.getClinicalRecommend());
            records.add(record);
        }
        else
        {
            BiologyAnalyDetailsRecord record;
            List<String> collect = coordinates.stream().sorted().collect(toList());
            String join = String.join(",", collect);
            record = new BiologyAnalyDetailsRecord();
            record.setPoolingCode(poolingCode);
            record.setRecordCode(recordCode);
            record.setReceivedSampleCode(library.getReceivedSample().getSampleCode());
            record.setLibrarySampleCode(library.getSampleCode());
            record.setLibraryIndex(attributes.getIndex());
            record.setOrderCode(orderModel.getOrderCode());
            record.setContractCode(orderModel.getContractCode());
            record.setExamineeSex(sex);
            record.setTestingType(orderModel.getMarketingCenter());
            record.setCoordinate(join);
            record.setRemark(orderModel.getRemark());
            record.setExamineeName(sampleName);
            record.setProductCode(product.getCode());
            record.setProductName(product.getName());
            record.setFamilyRelation(familyRelation);
            record.setExamineeDiagnosis(orderModel.getDiagnosisName());
            record.setExamineeGenes(orderModel.getGeneName());
            record.setExamineePhenotypes(orderModel.getExamineePhenotypes());
            record.setMedicalHistory(orderModel.getMedicalHistory());
            record.setFamilyMedicalHistory(orderModel.getFamilyMedicalHistory());
            record.setClinicalRecommend(orderModel.getClinicalRecommend());
            records.add(record);
        }
        
        return records;
    }
    
    private Object getRecordMethodName(String name)
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
        else if ("动态突变".equals(name))
        {
            return "DTTB";
            
        }
        else
        {
            return name;
        }
        
    }
    
    private String getRecordCode(String receivedSampleCode, String inputSampleId)
    {
        TestingTask recordTask = getRecordTask(inputSampleId);
        
        if (null == recordTask)
        {
            return null;
        }
        
        String taskId = recordTask.getId();
        List<Product> products = testingTaskService.getRelatedProducts(taskId);
        
        if (CollectionUtils.isEmpty(products) || products.size() > 1)
        {
            return null;
        }
        
        Product product = products.get(0);
        
        List<TestingMethod> methods = testingTaskService.getRelatedTestingMethods(taskId);
        
        if (CollectionUtils.isEmpty(methods) || methods.size() > 1)
        {
            return null;
        }
        
        TestingMethod method = methods.get(0);
        return String.format("%1$s_%2$s_%3$s", receivedSampleCode, product.getCode(), getRecordMethodName(method.getName()));
    }
    
    private TestingTask getRecordTask(String inputSampleId)
    {
        String hql = "FROM TestingTask t WHERE t.inputSample.id = :inputSampleId AND t.endType = :endSuccess AND t.semantic NOT IN :excluedSemantics";
        List<TestingTask> tasks =
            baseDaoSupport.findByNamedParam(TestingTask.class, hql, new String[] {"inputSampleId", "endSuccess", "excluedSemantics"}, new Object[] {
                inputSampleId, TestingTask.END_SUCCESS, Arrays.asList(TaskSemantic.LIBRARY_QC, TaskSemantic.POOLING)});
        
        if (CollectionUtils.isEmpty(tasks))
        {
            return null;
        }
        
        return tasks.get(0);
    }
    
    private BiologyAnalyTask wrap(TestingTask entity)
    {
        BiologyAnalyTask task = new BiologyAnalyTask();
        task.setId(entity.getId());
        task.setSampleCode(entity.getInputSample().getSampleCode());
        task.setCreateTime(entity.getStartTime());
        // 设置加急
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
        // setPlannedFinishDate(entity, task);
        return task;
    }
    
    private void setPlannedFinishDate(TestingTask entity, BiologyAnalyTask task)
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
    
    private List<TestingPcrNgsSample> getListByOutputSampleId(String outputId)
    {
        String hql = "FROM TestingPcrNgsSample t WHERE t.outputSample.id = :outputId ";
        List<TestingPcrNgsSample> samples = baseDaoSupport.findByNamedParam(TestingPcrNgsSample.class, hql, new String[] {"outputId"}, new Object[] {outputId});
        if (Collections3.isEmpty(samples))
        {
            return Lists.newArrayList();
        }
        return samples;
    }
    
    private String getAnalysisLocationByCombineCode(String combineCode)
    {
        String result = "";
        if (StringUtils.isNotEmpty(combineCode))
        {
            String arr[] = combineCode.split("-");
            if (arr != null && arr.length > 2)
            {
                result = arr[1] + "-" + arr[2];
            }
            else
            {
                throw new IllegalStateException();
            }
        }
        return result;
    }
    
    private String getPcrRocordCode(String pcrSampleCode, String receivedSampleCode)
    {
        String result = "";
        if (StringUtils.isNotEmpty(pcrSampleCode))
        {
            String arr[] = pcrSampleCode.split("-");
            if (arr != null && arr.length > 2)
            {
                result = arr[0] + "_" + receivedSampleCode + "_" + TestingMethod.PCR_NGS;
            }
            else
            {
                throw new IllegalStateException();
            }
        }
        else
        {
            throw new IllegalStateException();
        }
        return result;
    }
    
    @Override
    @Transactional
    public void goPcrNgsDataAnalysis(PcrNgsDataModel request)
    {
        //生信注释完 调用  生信注释那边已经把任务跟流程都一一对应上
        String taskId = request.getTaskId();
        List<TestingSchedule> scheduleList = testingScheduleService.getRelatedSchedules(taskId);
        if (Collections3.isEmpty(scheduleList))
        {
            scheduleList = testingScheduleService.getRelatedSchedulesByTestingTask(taskId);
        }
        if (Collections3.isEmpty(scheduleList))
        {
            log.error("biology annotation task related schedule is null.taskId is:" + taskId);
            throw new IllegalStateException();
        }
        for (TestingSchedule testingSchedule : scheduleList)
        {
            String pcrNgsSampleHql = " FROM TestingPcrNgsSample p WHERE p.testingSchedule.id=:scheduleId";
            List<TestingPcrNgsSample> pcrNgsSampleList =
                baseDaoSupport.findByNamedParam(TestingPcrNgsSample.class, pcrNgsSampleHql, new String[] {"scheduleId"}, new String[] {testingSchedule.getId()});
            if (Collections3.isEmpty(pcrNgsSampleList))
            {
                log.error("TestingPcrNgsSample is null.schedule id is:" + testingSchedule);
                throw new IllegalStateException();
            }
            //1.创建PCRNGS数据分析任务 删除之前的active
            List<TestingScheduleActive> actives = testingScheduleService.getScheduleActives(testingSchedule.getId());
            if (Collections3.isNotEmpty(actives))
            {
                for (TestingScheduleActive active : actives)
                {
                    baseDaoSupport.delete(active);
                }
            }
            TestingSample pcrNgsDataInputSample = pcrNgsSampleList.get(0).getInputSample();
            TestingTask task = new TestingTask();
            task.setName("PCR-NGS数据分析");
            task.setSemantic(TaskSemantic.PCR_NGS_DATA_ANALYSIS);
            task.setStatus(TestingTask.STATUS_ASSIGNABLE);
            task.setResubmit(false);
            task.setResubmitCount(0);
            task.setStartTime(new Date());
            if (null == pcrNgsDataInputSample)
            {
                throw new IllegalStateException();
            }
            task.setInputSample(pcrNgsDataInputSample);
            
            // 特殊处理测序编号
            task.setTestingLaneCode(request.getSquencingCode());
            baseDaoSupport.insert(task);
            
            TestingTaskRunVariable ariables = new TestingTaskRunVariable();
            ariables.setTestingTaskId(task.getId());
            baseDaoSupport.insert(ariables);
            
            // 激活新节点
            TestingScheduleActive active = new TestingScheduleActive();
            active.setSchedule(testingSchedule);
            active.setTaskId(task.getId());
            baseDaoSupport.insert(active);
            
            // 插入历史任务
            TestingScheduleHistory history = new TestingScheduleHistory();
            history.setScheduleId(testingSchedule.getId());
            history.setTaskId(task.getId());
            history.setTimestamp(new Date());
            baseDaoSupport.insert(history);
            
            TechnicalAnalyTestingTask tatt = new TechnicalAnalyTestingTask();
            tatt.setTaskId(task.getId());
            tatt.setSequencingCode(request.getSquencingCode());
            baseDaoSupport.insert(tatt);
            
            // 更新当前任务名称
            testingSchedule.setActiveTask("PCR-NGS数据分析");
            baseDaoSupport.update(testingSchedule);
            //更新任务 冗余信息
            testingTaskService.updateTaskRedundantColumn(Arrays.asList(task), 0);
        }
        
    }
    
    @Override
    @Transactional
    public void taskFailReport(PcrNgsDataModel request)
    {
        String taskId = request.getTaskId();
        List<TestingScheduleActive> list =
            baseDaoSupport.findByNamedParam(TestingScheduleActive.class,
                "from TestingScheduleActive t where t.taskId = :taskId",
                new String[] {"taskId"},
                new String[] {taskId});
        if (Collections3.isNotEmpty(list))
        {
            for (TestingScheduleActive active : list)
            {
                baseDaoSupport.delete(active);
            }
        }
        
        // 1.保存任务结果表为异常上报 先查询 如果已经存在了
        TestingTaskResult result = baseDaoSupport.get(TestingTaskResult.class, taskId);
        if (null == result)
        {
            result = new TestingTaskResult();
            result.setTaskId(taskId);
            result.setResult(TestingTaskResult.RESULT_FAILURE_REPORT);
            result.setRemark(request.getRemark());
            result.setTaskRefer(request.getTaskRefer());
            baseDaoSupport.insert(result);
        }
        else
        {
            result.setResult(TestingTaskResult.RESULT_FAILURE_REPORT);
            result.setRemark(request.getRemark());
            result.setTaskRefer(request.getTaskRefer());
            baseDaoSupport.update(result);
        }
        
        // 2.更新流程节点为异常
        if (Collections3.isEmpty(list))
        {
            log.error("task related schedule is null.taskId is:" + taskId);
        }
        else
        {
            if (TaskSemantic.BIOLOGY_ANNOTATION.equals(request.getTaskRefer()))
            {
                for (int i=0 ; i< list.size(); i++)//考虑到pcr-ngs，一个生信注释task关联多个流程都需要更新
                {
                   TestingSchedule testingSchedule = list.get(i).getSchedule();
                   testingSchedule.setActiveTask("生信注释-异常");
                   baseDaoSupport.update(testingSchedule);
                }

            }
            else if (TaskSemantic.TECHNICAL_ANALYSIS_TASK.equals(request.getTaskRefer()))
            {
                TestingSchedule testingSchedule = list.get(0).getSchedule();
                testingSchedule.setActiveTask("新技术分析-异常");
                baseDaoSupport.update(testingSchedule);
            }
        }
    }
    
    @Override
    @Transactional
    public void biologyTaskCreate(String divideTaskId)
    {
        
        log.info(" biologyTaskCreate.taskId is:{}.", divideTaskId);
        
        List<TestingScheduleActive> list =
            baseDaoSupport.findByNamedParam(TestingScheduleActive.class,
                "from TestingScheduleActive t where t.taskId = :taskId",
                new String[] {"taskId"},
                new String[] {divideTaskId});
        log.info(" activeTask size is:{}.", list.size());
        if (Collections3.isNotEmpty(list))
        {
            for (TestingScheduleActive active : list)
            {
                baseDaoSupport.delete(active);
            }
        }
        
        String hql = " FROM TestingScheduleHistory t WHERE t.taskId = '" + divideTaskId + "'";
        List<TestingScheduleHistory> histories = baseDaoSupport.find(TestingScheduleHistory.class, hql);
        log.info("histories size is:" + histories.size());
        TestingTask bioTask = null;
        TestingScheduleActive scheduleActive;
        TestingScheduleHistory scheduleHistory;
        boolean updateFlag = false;
        if (Collections3.isNotEmpty(histories))
        {
            for (TestingScheduleHistory history : histories)
            {
                String scheduleId = history.getScheduleId();
                TestingSchedule schedule = baseDaoSupport.get(TestingSchedule.class, scheduleId);
                schedule.setActiveTask("生信分析");
                baseDaoSupport.update(schedule);
                
                TestingTask qtTask = null;
                String scheduleTaskHql =
                    " FROM TestingScheduleHistory t WHERE t.scheduleId = '" + scheduleId
                        + "' AND EXISTS (SELECT task.id FROM TestingTask task WHERE t.taskId=task.id AND task.semantic='QT') ORDER BY t.timestamp DESC ";
                List<TestingScheduleHistory> scheduleTasks = baseDaoSupport.find(TestingScheduleHistory.class, scheduleTaskHql);
                if (Collections3.isNotEmpty(scheduleTasks))
                {
                    String taskId = Collections3.getFirst(scheduleTasks).getTaskId();
                    qtTask = baseDaoSupport.get(TestingTask.class, taskId);
                    if (null == bioTask)
                    {
                        bioTask = new TestingTask();
                        bioTask.setName("生信分析");
                        bioTask.setSemantic("BIOLOGY-ANALY");
                        bioTask.setStatus(TestingTask.STATUS_ASSIGNABLE);
                        bioTask.setResubmit(false);
                        bioTask.setResubmitCount(0);
                        bioTask.setStartTime(new Date());
                        bioTask.setInputSample(qtTask.getInputSample());


                        baseDaoSupport.insert(bioTask);
                        
                        TestingTaskRunVariable variables = new TestingTaskRunVariable();
                        variables.setTestingTaskId(bioTask.getId());
                        baseDaoSupport.insert(variables);
                    }
                    
                    scheduleActive = new TestingScheduleActive();
                    scheduleActive.setSchedule(schedule);
                    scheduleActive.setTaskId(bioTask.getId());
                    baseDaoSupport.insert(scheduleActive);
                    
                    scheduleHistory = new TestingScheduleHistory();
                    scheduleHistory.setScheduleId(scheduleId);
                    scheduleHistory.setTaskId(bioTask.getId());
                    scheduleHistory.setTimestamp(new Date());
                    baseDaoSupport.insert(scheduleHistory);
                    if (!updateFlag)
                    {
                        testingTaskService.updateTaskRedundantColumn(Arrays.asList(bioTask), 0);
                        updateFlag = true;
                        for (TestingScheduleHistory his:histories)
                        {
                            TestingSchedule testingSchedule = baseDaoSupport.get(TestingSchedule.class,his.getScheduleId());
                            Order o = baseDaoSupport.get(Order.class, testingSchedule.getOrderId());
                            //根据订单是否加急更新任务
                            if (StringUtils.isNotEmpty(o.getIfUrgent()) && o.getIfUrgent() == 1)
                            {
                                bioTask.setIfUrgent(o.getIfUrgent());
                                bioTask.setUrgentName(o.getUrgentName());
                                bioTask.setUrgentUpdateTime(o.getUrgentUpdateTime());
                                baseDaoSupport.update(bioTask);
                                break;
                            }
                        }


                    }
                }
            }
        }
    }
    
    @Override
    public List<BiologyAnalySampleRecord> getSampleRecordsBySampleCode(String sampleCode)
    {
        
        List<TestingSample> testingSamples =
            baseDaoSupport.findByNamedParam(TestingSample.class,
                "from TestingSample t where t.sampleCode=:sampleCode",
                new String[] {"sampleCode"},
                new Object[] {sampleCode});
        
        TestingSample mixedSample = Collections3.getFirst(testingSamples);
        
        if (null == mixedSample)
        {
            log.warn("Testing task {} input sample is null.");
            return Collections.emptyList();
        }
        else
        {
            if (log.isDebugEnabled())
            {
                log.debug("Mixed Sample id {}, code {}", mixedSample.getId(), mixedSample.getSampleCode());
            }
        }
        
        String hql = "FROM PoolingSample s WHERE s.mixedSample.id = :sampleId";
        List<PoolingSample> samples = baseDaoSupport.findByNamedParam(PoolingSample.class, hql, new String[] {"sampleId"}, new Object[] {mixedSample.getId()});
        
        if (CollectionUtils.isEmpty(samples))
        {
            log.warn("Testing task {} pooling samples is empty");
            return Collections.emptyList();
        }
        
        TestingSample sample;
        List<BiologyAnalySampleRecord> instanceRecords;
        Set<String> recordedLibrary = new HashSet<String>();
        List<BiologyAnalySampleRecord> records = new ArrayList<BiologyAnalySampleRecord>();
        
        TaskConfig taskConfig = bcmadapter.getTaskConfigBySemantic(TaskSemantic.PCR_NGS);
        
        SampleTypeSimpleModel outputSampleType = bcmadapter.getSampleType(taskConfig.getOutputSampleId());
        
        if (null == outputSampleType)
        {
            throw new IllegalStateException();
        }
        
        for (PoolingSample ps : samples)
        {
            sample = ps.getSample();
            // 若该样本流程被取消，则不导出表
            if (!scheduleCancel(sample))
            {
                if (null == sample)
                {
                    
                    throw new IllegalStateException();
                }
                
                instanceRecords = getSampleRecords(sample, mixedSample.getSampleCode(), recordedLibrary, outputSampleType);
                
                if (CollectionUtils.isEmpty(instanceRecords))
                {
                    continue;
                }
                
                records.addAll(instanceRecords);
            }
            
        }
        
        return records;
    }
    
    @Override
    public List<BiologyAnalyDetailsRecord> getDetailsRecordsBySampleCode(String sampleCode)
    {
        List<TestingSample> testingSamples =
            baseDaoSupport.findByNamedParam(TestingSample.class,
                "from TestingSample t where t.sampleCode=:sampleCode",
                new String[] {"sampleCode"},
                new Object[] {sampleCode});
        
        TestingSample mixedSample = Collections3.getFirst(testingSamples);
        
        String hql = "FROM PoolingSample s WHERE s.mixedSample.id = :sampleId";
        List<PoolingSample> samples = baseDaoSupport.findByNamedParam(PoolingSample.class, hql, new String[] {"sampleId"}, new Object[] {mixedSample.getId()});
        
        if (CollectionUtils.isEmpty(samples))
        {
            return Collections.emptyList();
        }
        
        TestingSample sample;
        List<BiologyAnalyDetailsRecord> instanceRecords;
        Set<String> recordedLibrary = new HashSet<String>();
        SampleRelationContext sampleRelationContext = new SampleRelationContext();
        List<BiologyAnalyDetailsRecord> records = new ArrayList<BiologyAnalyDetailsRecord>();
        
        TaskConfig taskConfig = bcmadapter.getTaskConfigBySemantic(TaskSemantic.PCR_NGS);
        
        SampleTypeSimpleModel outputSampleType = bcmadapter.getSampleType(taskConfig.getOutputSampleId());
        
        if (null == outputSampleType)
        {
            throw new IllegalStateException();
        }
        
        for (PoolingSample ps : samples)
        {
            sample = ps.getSample();
            
            if (null == sample)
            {
                throw new IllegalStateException();
            }
            
            instanceRecords = getDetailsRecords(sample, mixedSample.getSampleCode(), sampleRelationContext, recordedLibrary, outputSampleType);
            
            if (CollectionUtils.isEmpty(instanceRecords))
            {
                continue;
            }
            
            records.addAll(instanceRecords);
        }
        
        // 处理参照样本合并
        Map<String, String> primaryMappings = sampleRelationContext.getRelationAsPrimaryKey2();
        Map<String, List<String>> referenceMappings = sampleRelationContext.getRelationAsReferenceKey2();
        
        List<BiologyAnalyDetailsRecord> result = new ArrayList<BiologyAnalyDetailsRecord>();
        
        String psrv;
        String pspv;
        String rsrv;
        String rspv;
        List<String> sl;
        boolean ignore;
        
        for (BiologyAnalyDetailsRecord record : records)
        {
            ignore = false;
            psrv = primaryMappings.get(record.getReceivedSampleCode());
            if (outputSampleType.getId().equals(record.getSampleTypeId()))// pcrngs
            {
                if (null != psrv)
                {
                    // pspv = referenceMappings.get(psrv);
                    sl = referenceMappings.get(psrv);
                    
                    if (Collections3.isNotEmpty(sl))
                    {
                        for (String s : sl)
                        {
                            if (s.equals(record.getReceivedSampleCode()))
                            {
                                record.setReferenceSampleCode(psrv);
                            }
                        }
                    }
                }
                else
                {
                    record.setReferenceSampleCode(record.getReceivedSampleCode());
                }
            }
            else
            {
                if (null != psrv)
                {
                    // pspv = referenceMappings.get(psrv);
                    sl = referenceMappings.get(psrv);
                    
                    if (Collections3.isNotEmpty(sl))
                    {
                        for (String s : sl)
                        {
                            if (s.equals(record.getReceivedSampleCode()))
                            {
                                record.setReferenceSampleCode(psrv);
                            }
                        }
                    }
                }
                else
                {
                    // rspv =
                    // referenceMappings.get(record.getReceivedSampleCode());
                    /*
                     * if (null != rspv) { rsrv = primaryMappings.get(rspv);
                     * 
                     * if (null != rsrv) { ignore = true; } }
                     */
                    List<String> sll = referenceMappings.get(record.getReceivedSampleCode());
                    if (Collections3.isNotEmpty(sll))
                    {
                        for (String s : sll)
                        {
                            rsrv = primaryMappings.get(s);
                            if (null != rsrv)
                            {
                                ignore = true;
                            }
                        }
                    }
                    
                }
            }
            
            if (!ignore)
            {
                result.add(record);
            }
        }
        
        // 获取对照样本详情，对照样本不在该任务单同样带出对照样本
        TestingTask t = new TestingTask();
        List<NgsSequecingTask> ngsTasks =
            baseDaoSupport.findByNamedParam(NgsSequecingTask.class,
                "from NgsSequecingTask n where n.sequecingCode =:sampleCode",
                new String[] {"sampleCode"},
                new Object[] {sampleCode});
        if (Collections3.isNotEmpty(ngsTasks))
        {
            
            t.setId(Collections3.getFirst(ngsTasks).getId());
        }
        getRefernceSampleDetail(result, t);
        
        // ww 2017.8.4
        if (Collections3.isNotEmpty(result))
        {
            for (BiologyAnalyDetailsRecord record : result)
            {
                if (outputSampleType.getId().equals(record.getSampleTypeId()))// pcrngs
                {
                    record.setReferenceSampleCode("");
                }
            }
        }
        return result;
    }
}
