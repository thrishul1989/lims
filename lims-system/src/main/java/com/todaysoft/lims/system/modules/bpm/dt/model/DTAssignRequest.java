package com.todaysoft.lims.system.modules.bpm.dt.model;

import java.util.List;

public class DTAssignRequest
{
    private String id;
    
    private String testerId;
    
    private String reagentKitId;
    
    private String description;
    
    private List<DTTask> tasks;
    
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
    
    public String getReagentKitId()
    {
        return reagentKitId;
    }
    
    public void setReagentKitId(String reagentKitId)
    {
        this.reagentKitId = reagentKitId;
    }
    
    public String getDescription()
    {
        return description;
    }
    
    public void setDescription(String description)
    {
        this.description = description;
    }

    public List<DTTask> getTasks() {
        return tasks;
    }

    public void setTasks(List<DTTask> tasks) {
        this.tasks = tasks;
    }
}
