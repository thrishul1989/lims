package com.todaysoft.lims.system.modules.bpm.fluotest.model;

import java.util.List;

import com.todaysoft.lims.system.modules.bpm.longpcr.model.LongPcrTask;

public class FluoTestAssignRequest
{
    private String id;
    
    private String testerId;
    
    private String reagentKit;
    
    private String description;
    
    private List<FluoTestTask> tasks;
    
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
    
    public String getReagentKit()
    {
        return reagentKit;
    }
    
    public void setReagentKit(String reagentKit)
    {
        this.reagentKit = reagentKit;
    }
    
    public String getDescription()
    {
        return description;
    }
    
    public void setDescription(String description)
    {
        this.description = description;
    }
    
    public List<FluoTestTask> getTasks()
    {
        return tasks;
    }
    
    public void setTasks(List<FluoTestTask> tasks)
    {
        this.tasks = tasks;
    }
    
}
