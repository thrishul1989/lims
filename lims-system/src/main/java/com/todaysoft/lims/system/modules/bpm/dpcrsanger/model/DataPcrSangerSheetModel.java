package com.todaysoft.lims.system.modules.bpm.dpcrsanger.model;

import java.util.Date;
import java.util.List;

import com.todaysoft.lims.system.modules.bpm.tecanalys.model.TechnicalAnalyTask;

public class DataPcrSangerSheetModel
{
    private String id;
    
    private String code;
    
    private String assignerName;
    
    private Date assignTime;
    
    private String description;
    
    private List<DataPcrSangerTask> tasks;
    
    private List<TechnicalAnalyTask> analysTasks;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getCode()
    {
        return code;
    }
    
    public void setCode(String code)
    {
        this.code = code;
    }
    
    public String getAssignerName()
    {
        return assignerName;
    }
    
    public void setAssignerName(String assignerName)
    {
        this.assignerName = assignerName;
    }
    
    public Date getAssignTime()
    {
        return assignTime;
    }
    
    public void setAssignTime(Date assignTime)
    {
        this.assignTime = assignTime;
    }
    
    public String getDescription()
    {
        return description;
    }
    
    public void setDescription(String description)
    {
        this.description = description;
    }
    
    public List<DataPcrSangerTask> getTasks()
    {
        return tasks;
    }
    
    public void setTasks(List<DataPcrSangerTask> tasks)
    {
        this.tasks = tasks;
    }
    
    public List<TechnicalAnalyTask> getAnalysTasks()
    {
        return analysTasks;
    }
    
    public void setAnalysTasks(List<TechnicalAnalyTask> analysTasks)
    {
        this.analysTasks = analysTasks;
    }
}
