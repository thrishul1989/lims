package com.todaysoft.lims.testing.technicalanaly.model;

import java.util.Date;
import java.util.List;

public class TechnicalAnalySubmitModel
{
    private String id;
    
    private String code;
    
    private String assignerName;
    
    private Date assignTime;
    
    private String description;
    
    private boolean verified;

    private String submiterId;
    
    private List<TechnicalAnalyTask> tasks;
    
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
    
    public List<TechnicalAnalyTask> getTasks()
    {
        return tasks;
    }
    
    public void setTasks(List<TechnicalAnalyTask> tasks)
    {
        this.tasks = tasks;
    }
    
    public boolean isVerified()
    {
        return verified;
    }
    
    public void setVerified(boolean verified)
    {
        this.verified = verified;
    }

    public String getSubmiterId() {
        return submiterId;
    }

    public void setSubmiterId(String submiterId) {
        this.submiterId = submiterId;
    }
}
