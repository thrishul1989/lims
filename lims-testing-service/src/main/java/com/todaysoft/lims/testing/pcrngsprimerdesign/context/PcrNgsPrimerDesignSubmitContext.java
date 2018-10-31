package com.todaysoft.lims.testing.pcrngsprimerdesign.context;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.todaysoft.lims.testing.base.entity.*;
import org.springframework.util.CollectionUtils;

import com.todaysoft.lims.testing.base.service.adapter.smm.UserMinimalModel;
import com.todaysoft.lims.testing.pcrngsprimerdesign.model.PcrNgsPrimerDesignSubmitRequest;
import com.todaysoft.lims.testing.pcrngsprimerdesign.model.PcrNgsPrimerDesignSubmitTaskArgs;

public class PcrNgsPrimerDesignSubmitContext
{
    private UserMinimalModel submiter;
    
    private TestingSheet sheetEntity;
    
    private Set<String> updatedSangerVerifyRecordKeys = new HashSet<String>();
    
    private Map<String, SangerVerifyRecord> sangerVerifyRecords = new HashMap<String, SangerVerifyRecord>();
    
    private Set<PcrNgsPrimerDesignSubmitTaskContext> successTasks = new HashSet<PcrNgsPrimerDesignSubmitTaskContext>();
    
    private Map<String, PcrNgsPrimerDesignSubmitTaskContext> tasks = new HashMap<String, PcrNgsPrimerDesignSubmitTaskContext>();
    
    private Map<String, PcrNgsPrimerDesignSubmitScheduleContext> successSchedules = new HashMap<String, PcrNgsPrimerDesignSubmitScheduleContext>();
    
    private Map<String, PcrNgsPrimerDesignSubmitScheduleContext> failureSchedules = new HashMap<String, PcrNgsPrimerDesignSubmitScheduleContext>();
    
    public Set<PcrNgsPrimerDesignSubmitTaskContext> getSuccessTasks()
    {
        return successTasks;
    }
    
    public Set<PcrNgsPrimerDesignSubmitTaskContext> getRelatedTasks()
    {
        return new HashSet<PcrNgsPrimerDesignSubmitTaskContext>(tasks.values());
    }
    
    public Set<PcrNgsPrimerDesignSubmitScheduleContext> getSuccessSchedules()
    {
        return new HashSet<PcrNgsPrimerDesignSubmitScheduleContext>(successSchedules.values());
    }
    
    public Set<PcrNgsPrimerDesignSubmitScheduleContext> getFailureSchedules()
    {
        return new HashSet<PcrNgsPrimerDesignSubmitScheduleContext>(failureSchedules.values());
    }
    
    public String getPreparedPrimerId(String scheduleId)
    {
        PcrNgsPrimerDesignSubmitScheduleContext scheduleContext = successSchedules.get(scheduleId);
        String taskId = scheduleContext.getPrimerPrepareNodeEntity().getTaskId();
        PcrNgsPrimerDesignSubmitTaskContext taskContext = tasks.get(taskId);
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
        PcrNgsPrimerDesignSubmitTaskContext taskContext = tasks.get(taskId);
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
    
    public void setContextForSubmitRequest(PcrNgsPrimerDesignSubmitRequest request)
    {
        if (CollectionUtils.isEmpty(request.getTasks()))
        {
            return;
        }

        PcrNgsPrimerDesignSubmitTaskContext taskContext;
        
        for (PcrNgsPrimerDesignSubmitTaskArgs taskArgs : request.getTasks())
        {
            taskContext = new PcrNgsPrimerDesignSubmitTaskContext();
            taskContext.setArgs(taskArgs);
            tasks.put(taskArgs.getId(), taskContext);
        }
    }
    
    public void setContextForTestingTask(TestingTask task)
    {
        PcrNgsPrimerDesignSubmitTaskContext taskContext = tasks.get(task.getId());
        taskContext.setTestingTaskEntity(task);
        
        if (taskContext.isPrimerPrepared())
        {
            successTasks.add(taskContext);
        }
    }
    
    public void setContextForTestingSchedule(TestingTask task, TestingSchedule schedule, TestingScheduleActive scheduleActive)
    {
        PcrNgsPrimerDesignSubmitScheduleContext scheduleContext = new PcrNgsPrimerDesignSubmitScheduleContext();
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
        PcrNgsPrimerDesignSubmitTaskContext taskContext = tasks.get(taskId);
        taskContext.setPrimerId(primerId);
    }
    
    public void setContextForUpdateSangerVerifyRecord(SangerVerifyRecord record)
    {
        sangerVerifyRecords.put(record.getId(), record);
        updatedSangerVerifyRecordKeys.add(record.getId());
    }
}
