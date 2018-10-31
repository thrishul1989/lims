package com.todaysoft.lims.system.modules.bpm.pcrngsdata.model;

import java.util.List;

public class PcrNgsDataAssignRequest
{
    private String testerId;

    private String description;
    
    private List<PcrNgsDataAssignTaskArgs> tasks;
    
    public List<PcrNgsDataAssignTaskArgs> getTasks()
    {
        return tasks;
    }
    
    public void setTasks(List<PcrNgsDataAssignTaskArgs> tasks)
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
