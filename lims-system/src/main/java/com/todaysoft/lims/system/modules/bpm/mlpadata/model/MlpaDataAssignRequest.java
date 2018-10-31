package com.todaysoft.lims.system.modules.bpm.mlpadata.model;

import java.util.List;

public class MlpaDataAssignRequest
{
    private String testerId;
    
    private String description;
    
    private List<MlpaDataTask> tasks;
    
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
    
    public List<MlpaDataTask> getTasks()
    {
        return tasks;
    }
    
    public void setTasks(List<MlpaDataTask> tasks)
    {
        this.tasks = tasks;
    }
}
