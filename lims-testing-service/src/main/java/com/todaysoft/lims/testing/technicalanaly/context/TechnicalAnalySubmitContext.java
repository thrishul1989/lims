package com.todaysoft.lims.testing.technicalanaly.context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.springframework.util.CollectionUtils;

import com.google.common.collect.Lists;
import com.todaysoft.lims.testing.base.entity.TestingSample;
import com.todaysoft.lims.testing.base.entity.TestingSchedule;
import com.todaysoft.lims.testing.base.entity.TestingSheet;
import com.todaysoft.lims.testing.base.entity.TestingTask;
import com.todaysoft.lims.testing.base.entity.TestingTechnicalAnalyRecord;
import com.todaysoft.lims.testing.base.entity.TestingVerifyRecord;
import com.todaysoft.lims.testing.base.model.TaskConfig;
import com.todaysoft.lims.testing.base.model.TaskSemantic;
import com.todaysoft.lims.testing.base.service.adapter.smm.UserMinimalModel;
import com.todaysoft.lims.testing.technicalanaly.model.TechnicalAnalySubmitRecord;
import com.todaysoft.lims.testing.technicalanaly.model.TechnicalAnalySubmitRequest;
import com.todaysoft.lims.testing.technicalanaly.model.TechnicalAnalyVerifySample;
import com.todaysoft.lims.utils.StringUtils;

public class TechnicalAnalySubmitContext
{
    private UserMinimalModel submiter;
    
    private TestingSheet sheetEntity;
    
    private Map<String, TechnicalAnalySubmitTaskContext> tasks = new HashMap<String, TechnicalAnalySubmitTaskContext>();
    
    private Map<String, TechnicalAnalySubmitScheduleContext> schedules = new HashMap<String, TechnicalAnalySubmitScheduleContext>();
    
    private List<TechnicalAnalySubmitRecord> records = new ArrayList<TechnicalAnalySubmitRecord>();
    
    private Map<String, TestingSchedule> receivedSampleScheduleMapping = new HashMap<String, TestingSchedule>();
    
    private Map<String, TestingVerifyRecord> verifyRecords = new HashMap<String, TestingVerifyRecord>();
    
    private Map<String, TestingTask> primerTasks = new HashMap<String, TestingTask>();
    
    private Map<String, TestingTask> sampleTasks = new HashMap<String, TestingTask>();
    
    private Map<String, TaskConfig> taskConfigs = new HashMap<String, TaskConfig>();
    
    private Set<String> assignedVerifyInstances = new HashSet<String>();
    
    private List<TestingTask> updateList = Lists.newArrayList();
    
    private List<String> updateScheduleList = new ArrayList<String>();
    
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
    
    public TestingSheet getSheetEntity()
    {
        return sheetEntity;
    }
    
    public TestingVerifyRecord getTestingVerifyRecord(TechnicalAnalySubmitRecord record, TechnicalAnalyVerifySample verifySample)
    {
        return verifyRecords.get(record.getTemporaryId() + "_" + verifySample.getId());
    }
    
    public TestingSchedule getTestingSchedule(String receivedSampleCode)
    {
        return receivedSampleScheduleMapping.get(receivedSampleCode);
    }
    
    public TestingTask getPrimerTask(TestingTechnicalAnalyRecord record)
    {
        String key = record.getChromosome() + "_" + record.getBeginLocus() + "_" + record.getVerifyMethod();
        return primerTasks.get(key);
    }
    
    public boolean isAssigned(TestingVerifyRecord record)
    {
        String key = getVerifyRecordKey(record);
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
    
    public void setContextForTestingSheet(TestingSheet entity)
    {
        this.sheetEntity = entity;
    }
    
    public void setContextForTestingTask(TestingTask task)
    {
        TechnicalAnalySubmitTaskContext taskContext = new TechnicalAnalySubmitTaskContext();
        taskContext.setTestingTaskEntity(task);
        tasks.put(task.getId(), taskContext);
    }
    
    public void setContextForTestingSchedule(TestingTask task, TestingSchedule schedule)
    {
        TechnicalAnalySubmitScheduleContext scheduleContext = new TechnicalAnalySubmitScheduleContext();
        scheduleContext.setTestingScheduleEntity(schedule);
        
        if (StringUtils.isNotEmpty(task.getTestingAnalyDataCode()))
        {
            receivedSampleScheduleMapping.put(task.getTestingAnalyDataCode(), schedule);
        }
        
        schedules.put(schedule.getId(), scheduleContext);
    }
    
    public void setContextForCreateTestingVerifyRecord(TechnicalAnalySubmitRecord record, TechnicalAnalyVerifySample verifySample, TestingVerifyRecord verifyRecord)
    {
        if (StringUtils.isEmpty(record.getTemporaryId()))
        {
            record.setTemporaryId(UUID.randomUUID().toString().replaceAll("-", ""));
        }
        
        verifyRecords.put(record.getTemporaryId() + "_" + verifySample.getId(), verifyRecord);
    }
    
    public void setContextForTaskConfig(String semantic, TaskConfig config)
    {
        taskConfigs.put(semantic, config);
    }
    
    public void setContextForCreateSangerPrimerPrepareTask(TestingTechnicalAnalyRecord record, TestingTask task)
    {
        String key = record.getChromosome() + "_" + record.getBeginLocus() + "_" + record.getVerifyMethod();
        primerTasks.put(key, task);
    }
    
    public void setContextForCreateSampleTask(TestingTask task)
    {
        String semantic = task.getSemantic();
        
        if (TaskSemantic.DNA_EXTRACT.equals(semantic) || TaskSemantic.DNA_QC.equals(semantic))
        {
            String key = task.getInputSample().getId() + "_" + semantic;
            sampleTasks.put(key, task);
        }
    }
    
    public void setContextForCreateVerifySchedule(TestingVerifyRecord record)
    {
        assignedVerifyInstances.add(getVerifyRecordKey(record));
    }
    
    private String getVerifyRecordKey(TestingVerifyRecord record)
    {
        String method = record.getAnalyRecord().getVerifyMethod();
        String sample = record.getInputSample().getId();
        
        // Sanger验证的标识为染色体+位置1
        if ("SANGER".equals(method.toUpperCase()))
        {
            String chromosome = record.getAnalyRecord().getChromosome();
            String beginLocus = record.getAnalyRecord().getBeginLocus();
            return method + "_" + sample + "_" + chromosome + "_" + beginLocus;
        }
        else if ("PCR-NGS".equals(method.toUpperCase()))
        {
            String chromosome = record.getAnalyRecord().getChromosome();
            String beginLocus = record.getAnalyRecord().getBeginLocus();
            return method + "_" + sample + "_" + chromosome + "_" + beginLocus;
        }
        else if ("MLPA".equals(method.toUpperCase()))
        {
            String gene = record.getAnalyRecord().getGeneSymbol();
            return method + "_" + sample + "_" + gene;
        }
        else if ("QPCR".equals(method.toUpperCase()))
        {
            String gene = record.getAnalyRecord().getGeneSymbol();
            String chroLocation = record.getAnalyRecord().getChrLocation();
            
            return method + "_" + sample + "_" + gene + "_" + chroLocation;
        }
        else
        {
            // 增加其他验证时补充
            return method + "_" + sample;
        }
    }
    
    public List<String> getUpdateScheduleList()
    {
        return updateScheduleList;
    }
    
    public void setUpdateScheduleList(List<String> updateScheduleList)
    {
        this.updateScheduleList = updateScheduleList;
    }
    
    public void setUpdateScheduleList(String id)
    {
        this.updateScheduleList.add(id);
    }
}
