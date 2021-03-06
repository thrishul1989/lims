package com.todaysoft.lims.system.modules.bpm.dtdata.model;

import java.util.List;

public class DTDataAssignRequest
{
    private String testerId;

    private String description;
    
    private List<DTDataAssignTaskArgs> tasks;
    
    public List<DTDataAssignTaskArgs> getTasks()
    {
        return tasks;
    }
    
    public void setTasks(List<DTDataAssignTaskArgs> tasks)
    {
        this.tasks = tasks;
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

}
