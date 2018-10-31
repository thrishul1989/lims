package com.todaysoft.lims.system.modules.bpm.mlpa.model;

import java.util.List;

public class MlpaTestAssignRequest
{
    private String id;
    
    private String testerId;
    
    private String reagentKitId;
    
    private String description;
    
    private List<MlpaTestTask> tasks;
    
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

    public List<MlpaTestTask> getTasks() {
        return tasks;
    }

    public void setTasks(List<MlpaTestTask> tasks) {
        this.tasks = tasks;
    }
}
