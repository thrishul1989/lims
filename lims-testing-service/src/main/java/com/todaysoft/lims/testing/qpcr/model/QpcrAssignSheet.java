package com.todaysoft.lims.testing.qpcr.model;

import java.util.List;

public class QpcrAssignSheet
{
    
    private String id;
    
    private String testerId;
    
    private String description;
    
    private List<QpcrTestTask> tasks;
    
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
    
    public List<QpcrTestTask> getTasks()
    {
        return tasks;
    }
    
    public void setTasks(List<QpcrTestTask> tasks)
    {
        this.tasks = tasks;
    }
    
}
