package com.todaysoft.lims.testing.technicalanaly.model;

import java.util.List;

public class TechnicalAnalyAssignRequest
{
    private String testerId;
    
    private String description;
    
    private List<TechnicalAnalyAssignTaskArgs> tasks;
    
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
    
    public List<TechnicalAnalyAssignTaskArgs> getTasks()
    {
        return tasks;
    }
    
    public void setTasks(List<TechnicalAnalyAssignTaskArgs> tasks)
    {
        this.tasks = tasks;
    }
}
