package com.todaysoft.lims.system.modules.bpm.qpcr.model;

import java.util.List;

public class QpcrAssignRequest
{
    private String id;
    
    private String testerId;
    
    private String description;
    
    private List<QpcrTask> tasks;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getTesterId()
    {
        return testerId;
    }
    
    public void setTesterId(String testerId)
    {
        this.testerId = testerId;
    }
    
    public String getDescription()
    {
        return description;
    }
    
    public void setDescription(String description)
    {
        this.description = description;
    }
    
    public List<QpcrTask> getTasks()
    {
        return tasks;
    }
    
    public void setTasks(List<QpcrTask> tasks)
    {
        this.tasks = tasks;
    }
}
