package com.todaysoft.lims.technical.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Lists;
import com.todaysoft.lims.technical.model.request.TechnicalAnalySubmitRequest;
import com.todaysoft.lims.technical.mybatis.TechnicalAnalyRecordMapper;
import com.todaysoft.lims.technical.mybatis.entity.TechnicalAnalyRecord;
import com.todaysoft.lims.technical.mybatis.entity.TechnicalAnalyRecordWithBLOBs;
import com.todaysoft.lims.technical.mybatis.entity.TechnicalAnalyVerify;
import com.todaysoft.lims.technical.mybatis.entity.TechnicalAnalysisTask;
import com.todaysoft.lims.technical.mybatis.entity.TestingSample;
import com.todaysoft.lims.technical.mybatis.entity.TestingSchedule;
import com.todaysoft.lims.technical.mybatis.entity.TestingTask;
import com.todaysoft.lims.technical.utils.StringUtils;

@Component
public class TechnicalAnalySubmitContext
{
    private UserMinimalModel submiter;
    
    private Map<String, TechnicalAnalySubmitTaskContext> tasks = new HashMap<String, TechnicalAnalySubmitTaskContext>();
    
    private Map<String, TechnicalAnalySubmitScheduleContext> schedules = new HashMap<String, TechnicalAnalySubmitScheduleContext>();
    
    private List<TechnicalAnalySubmitRecord> records = new ArrayList<TechnicalAnalySubmitRecord>();
    
    private Map<String, TestingSchedule> receivedSampleScheduleMapping = new HashMap<String, TestingSchedule>();
    
    private Map<String, TechnicalAnalyVerify> verifys = new HashMap<String, TechnicalAnalyVerify>();
    
    private Map<String, TestingTask> primerTasks = new HashMap<String, TestingTask>();
    
    private Map<String, TestingTask> sampleTasks = new HashMap<String, TestingTask>();
    
    private Map<String, TaskConfig> taskConfigs = new HashMap<String, TaskConfig>();
    
    private Set<String> assignedVerifyInstances = new HashSet<String>();
    
    private List<TestingTask> updateList = Lists.newArrayList();
    
    @Autowired
    private TechnicalAnalyRecordMapper technicalAnalyRecordMapper;
    
    public List<TestingTask> getUpdateList()
    {
        return updateList;
    }
    
    public void setUpdateList(List<TestingTask> updateList)
    {
        this.updateList = updateList;
    }
    
    public UserMinimalModel getSubmiter()
    {
        return submiter;
    }
    
    public Map<String, TestingTask> getPrimerTasks()
    {
        return primerTasks;
    }
    
    public void setPrimerTasks(Map<String, TestingTask> primerTasks)
    {
        this.primerTasks = primerTasks;
    }
    
    public Map<String, TestingTask> getSampleTasks()
    {
        return sampleTasks;
    }
    
    public void setSampleTasks(Map<String, TestingTask> sampleTasks)
    {
        this.sampleTasks = sampleTasks;
    }
    
    public TechnicalAnalyVerify getTestingVerifyRecord(TechnicalAnalySubmitRecord record, TechnicalAnalyVerifySample verifySample)
    {
        return verifys.get(record.getTemporaryId() + "_" + verifySample.getId());
    }
    
    public TestingSchedule getTestingSchedule(String receivedSampleCode)
    {
        return receivedSampleScheduleMapping.get(receivedSampleCode);
    }
    
    public TestingTask getPrimerTask(TechnicalAnalyRecord record)
    {
        String key = record.getChromosome() + "_" + record.getBeginLocus() + "_" + record.getVerifyMethod();
        return primerTasks.get(key);
    }
    
    public boolean isAssigned(TechnicalAnalyVerify verify, TechnicalAnalyRecord record)
    {
        String key = getVerifyRecordKey(verify, record);
        return assignedVerifyInstances.contains(key);
    }
    
    public TestingTask getSampleTask(TestingSample sample, TaskConfig config)
    {
        String semantic = config.getSemantic();
        
        if (TaskSemantic.DNA_EXTRACT.equals(semantic) || TaskSemantic.DNA_QC.equals(semantic))
        {
            String key = sample.getId() + "_" + semantic;
            return sampleTasks.get(key);
        }
        
        return null;
    }
    
    public TaskConfig getTaskConfig(String semantic)
    {
        return taskConfigs.get(semantic);
    }
    
    public Set<TechnicalAnalySubmitTaskContext> getTasks()
    {
        return new HashSet<TechnicalAnalySubmitTaskContext>(tasks.values());
    }
    
    public Set<TechnicalAnalySubmitScheduleContext> getSchedules()
    {
        return new HashSet<TechnicalAnalySubmitScheduleContext>(schedules.values());
    }
    
    public List<TechnicalAnalySubmitRecord> getTechnicalAnalyRecords()
    {
        return records;
    }
    
    public void setContextForSubmitResult(TechnicalAnalySubmitRequest request)
    {
        if (null != request && !CollectionUtils.isEmpty(request.getRecords()))
        {
            records.addAll(request.getRecords());
        }
    }
    
    public void setContextForSubmiter(UserMinimalModel submiter)
    {
        this.submiter = submiter;
    }
    
    public void setContextForTestingTask(TechnicalAnalysisTask task)
    {
        TechnicalAnalySubmitTaskContext taskContext = new TechnicalAnalySubmitTaskContext();
        taskContext.setTestingTaskEntity(task);
        tasks.put(task.getId(), taskContext);
    }
    
    public void setContextForTestingSchedule(TechnicalAnalysisTask task, TestingSchedule schedule)
    {
        TechnicalAnalySubmitScheduleContext scheduleContext = new TechnicalAnalySubmitScheduleContext();
        scheduleContext.setTestingScheduleEntity(schedule);
        
        if (StringUtils.isNotEmpty(task.getTestingAnalyDataCode()))
        {
            receivedSampleScheduleMapping.put(task.getTestingAnalyDataCode(), schedule);
        }
        
        schedules.put(schedule.getId(), scheduleContext);
    }
    
    public void setContextForCreateTestingVerifyRecord(TechnicalAnalySubmitRecord record, TechnicalAnalyVerifySample verifySample, TechnicalAnalyVerify verifyRecord)
    {
        if (StringUtils.isEmpty(record.getTemporaryId()))
        {
            record.setTemporaryId(UUID.randomUUID().toString().replaceAll("-", ""));
        }
        
        verifys.put(record.getTemporaryId() + "_" + verifySample.getId(), verifyRecord);
    }
    
    public void setContextForTaskConfig(String semantic, TaskConfig config)
    {
        taskConfigs.put(semantic, config);
    }
    
    public void setContextForCreateSangerPrimerPrepareTask(TechnicalAnalyRecord record, TestingTask task)
    {
        String key = record.getChromosome() + "_" + record.getBeginLocus() + "_" + record.getVerifyMethod();
        primerTasks.put(key, task);
    }
    
    public void setContextForCreateSampleTask(TestingTask task)
    {
        String semantic = task.getSemantic();
        
        if (TaskSemantic.DNA_EXTRACT.equals(semantic) || TaskSemantic.DNA_QC.equals(semantic))
        {
            String key = task.getInputSampleId() + "_" + semantic;
            sampleTasks.put(key, task);
        }
    }
    
    public void setContextForCreateVerifySchedule(TechnicalAnalyVerify verify, TechnicalAnalyRecord record)
    {
        assignedVerifyInstances.add(getVerifyRecordKey(verify, record));
    }
    
    public String getVerifyRecordKey(TechnicalAnalyVerify verify, TechnicalAnalyRecord record)
    {
        
        String method = record.getVerifyMethod();
        
        // Sanger验证的标识为染色体+位置1
        if ("SANGER".equals(method.toUpperCase()))
        {
            String chromosome = record.getChromosome();
            String beginLocus = record.getBeginLocus();
            return method + "_" + verify.getInputSampleId() + "_" + chromosome + "_" + beginLocus;
        }
        else if ("PCR-NGS".equals(method.toUpperCase()))
        {
            String chromosome = record.getChromosome();
            String beginLocus = record.getBeginLocus();
            return method + "_" + verify.getInputSampleId() + "_" + chromosome + "_" + beginLocus;
        }
        else if ("MLPA".equals(method.toUpperCase()))
        {
            String gene = record.getGeneSymbol();
            return method + "_" + verify.getInputSampleId() + "_" + gene;
        }
        else if ("QPCR".equals(method.toUpperCase()))
        {
            String gene = record.getGeneSymbol();
            String chroLocation = record.getChromosomeLocation();
            
            return method + "_" + verify.getInputSampleId() + "_" + gene + "_" + chroLocation;
        }
        else
        {
            // 增加其他验证时补充
            return method + "_" + verify.getInputSampleId();
        }
    }
}
