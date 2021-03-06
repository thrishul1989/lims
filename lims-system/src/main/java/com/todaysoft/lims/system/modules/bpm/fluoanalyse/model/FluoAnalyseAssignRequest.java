package com.todaysoft.lims.system.modules.bpm.fluoanalyse.model;

import java.util.List;

import com.todaysoft.lims.system.modules.bpm.fluotest.model.FluoTestTask;

public class FluoAnalyseAssignRequest
{
    private String id;
    
    private String testerId;
    
    private String description;
    
    private List<FluoAnalyseTask> tasks;

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

    public List<FluoAnalyseTask> getTasks()
    {
        return tasks;
    }

    public void setTasks(List<FluoAnalyseTask> tasks)
    {
        this.tasks = tasks;
    }
}
