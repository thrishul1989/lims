package com.todaysoft.lims.system.modules.bpm.fluoanalyse.model;

import java.util.Date;
import java.util.List;

import com.todaysoft.lims.system.modules.bpm.tecanalys.model.TechnicalAnalyTask;

public class FluoAnalyseSubmitModel
{
    private String id;
    
    private String code;
    
    private String assignerName;
    
    private Date assignTime;
    
    private String description;
    
    private String tester;
    
    private List<FluoAnalyseTask> tasks;
    
    private Date submitTime;
    
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
    
    public String getTester()
    {
        return tester;
    }
    
    public void setTester(String tester)
    {
        this.tester = tester;
    }
    
    public List<FluoAnalyseTask> getTasks()
    {
        return tasks;
    }
    
    public void setTasks(List<FluoAnalyseTask> tasks)
    {
        this.tasks = tasks;
    }
    
    public Date getSubmitTime()
    {
        return submitTime;
    }
    
    public void setSubmitTime(Date submitTime)
    {
        this.submitTime = submitTime;
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
