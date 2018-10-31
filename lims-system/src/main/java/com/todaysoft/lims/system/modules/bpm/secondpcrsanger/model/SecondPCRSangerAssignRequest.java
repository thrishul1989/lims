package com.todaysoft.lims.system.modules.bpm.secondpcrsanger.model;

import java.util.List;

public class SecondPCRSangerAssignRequest
{
    private String taskCode;
    
    private String testerId;

    private String reagentKitId;
    
    private String description;
    
    private List<SecondPCRSangerAssignTaskArgs> tasks;
    
    public List<SecondPCRSangerAssignTaskArgs> getTasks()
    {
        return tasks;
    }
    
    public void setTasks(List<SecondPCRSangerAssignTaskArgs> tasks)
    {
        this.tasks = tasks;
    }
    
    public String getTaskCode()
    {
        return taskCode;
    }
    
    public void setTaskCode(String taskCode)
    {
        this.taskCode = taskCode;
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

    public String getReagentKitId() {
        return reagentKitId;
    }

    public void setReagentKitId(String reagentKitId) {
        this.reagentKitId = reagentKitId;
    }
}
