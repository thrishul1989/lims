package com.todaysoft.lims.testing.primerdesign.context;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.util.CollectionUtils;

import com.todaysoft.lims.testing.base.entity.SangerVerifyRecord;
import com.todaysoft.lims.testing.base.entity.TestingSchedule;
import com.todaysoft.lims.testing.base.entity.TestingScheduleActive;
import com.todaysoft.lims.testing.base.entity.TestingSheet;
import com.todaysoft.lims.testing.base.entity.TestingTask;
import com.todaysoft.lims.testing.base.service.adapter.smm.UserMinimalModel;
import com.todaysoft.lims.testing.primerdesign.model.PrimerDesignSubmitRequest;
import com.todaysoft.lims.testing.primerdesign.model.PrimerDesignSubmitTaskArgs;

public class PrimerDesignSubmitContext
{
    private UserMinimalModel submiter;
    
    private TestingSheet sheetEntity;
    
    private Set<String> updatedSangerVerifyRecordKeys = new HashSet<String>();
    
    private Map<String, SangerVerifyRecord> sangerVerifyRecords = new HashMap<String, SangerVerifyRecord>();
    
    private Set<PrimerDesignSubmitTaskContext> successTasks = new HashSet<PrimerDesignSubmitTaskContext>();
    
    private Map<String, PrimerDesignSubmitTaskContext> tasks = new HashMap<String, PrimerDesignSubmitTaskContext>();
    
    private Map<String, PrimerDesignSubmitScheduleContext> successSchedules = new HashMap<String, PrimerDesignSubmitScheduleContext>();
    
    private Map<String, PrimerDesignSubmitScheduleContext> failureSchedules = new HashMap<String, PrimerDesignSubmitScheduleContext>();
    
    public Set<PrimerDesignSubmitTaskContext> getSuccessTasks()
    {
        return successTasks;
    }
    
    public Set<PrimerDesignSubmitTaskContext> getRelatedTasks()
    {
        return new HashSet<PrimerDesignSubmitTaskContext>(tasks.values());
    }
    
    public Set<PrimerDesignSubmitScheduleContext> getSuccessSchedules()
    {
        return new HashSet<PrimerDesignSubmitScheduleContext>(successSchedules.values());
    }
    
    public Set<PrimerDesignSubmitScheduleContext> getFailureSchedules()
    {
        return new HashSet<PrimerDesignSubmitScheduleContext>(failureSchedules.values());
    }
    
    public String getPreparedPrimerId(String scheduleId)
    {
        PrimerDesignSubmitScheduleContext scheduleContext = successSchedules.get(scheduleId);
        String taskId = scheduleContext.getPrimerPrepareNodeEntity().getTaskId();
        PrimerDesignSubmitTaskContext taskContext = tasks.get(taskId);
        return taskContext.getPrimerId();
    }
    
    public SangerVerifyRecord getSangerVerifyRecord(String verifyKey)
    {
        return sangerVerifyRecords.get(verifyKey);
    }
    
    public TestingSheet getSheetEntity()
    {
        return this.sheetEntity;
    }
    
    public UserMinimalModel getSubmiter()
    {
        return this.submiter;
    }
    
    public boolean isPrimerPrepared(String taskId)
    {
        PrimerDesignSubmitTaskContext taskContext = tasks.get(taskId);
        return taskContext.isPrimerPrepared();
    }
    
    public boolean isSangerVerifyRecordUpdate(String key)
    {
        return updatedSangerVerifyRecordKeys.contains(key);
    }
    
    public void setContextForSubmiter(UserMinimalModel submiter)
    {
        this.submiter = submiter;
    }
    
    public void setContextForTestingSheet(TestingSheet sheet)
    {
        this.sheetEntity = sheet;
    }
    
    public void setContextForSubmitRequest(PrimerDesignSubmitRequest request)
    {
        if (CollectionUtils.isEmpty(request.getTasks()))
        {
            return;
        }
        
        PrimerDesignSubmitTaskContext taskContext;
        
        for (PrimerDesignSubmitTaskArgs taskArgs : request.getTasks())
        {
            taskContext = new PrimerDesignSubmitTaskContext();
            taskContext.setArgs(taskArgs);
            tasks.put(taskArgs.getId(), taskContext);
        }
    }
    
    public void setContextForTestingTask(TestingTask task)
    {
        PrimerDesignSubmitTaskContext taskContext = tasks.get(task.getId());
        taskContext.setTestingTaskEntity(task);
        
        if (taskContext.isPrimerPrepared())
        {
            successTasks.add(taskContext);
        }
    }
    
    public void setContextForTestingSchedule(TestingTask task, TestingSchedule schedule, TestingScheduleActive scheduleActive)
    {
        PrimerDesignSubmitScheduleContext scheduleContext = new PrimerDesignSubmitScheduleContext();
        scheduleContext.setTestingScheduleEntity(schedule);
        scheduleContext.setPrimerPrepareNodeEntity(scheduleActive);
        
        boolean success = isPrimerPrepared(task.getId());
        
        if (success)
        {
            successSchedules.put(schedule.getId(), scheduleContext);
        }
        else
        {
            failureSchedules.put(schedule.getId(), scheduleContext);
        }
    }
    
    public void setContextForPreparePrimer(String taskId, String primerId)
    {
        PrimerDesignSubmitTaskContext taskContext = tasks.get(taskId);
        taskContext.setPrimerId(primerId);
    }
    
    public void setContextForUpdateSangerVerifyRecord(SangerVerifyRecord record)
    {
        sangerVerifyRecords.put(record.getId(), record);
        updatedSangerVerifyRecordKeys.add(record.getId());
    }
}
