package com.todaysoft.lims.system.modules.bpm.firstpcr.model;

import java.util.List;

public class FirstPCRAssignRequest
{
    private String taskCode;
    
    private String testerId;

    private String reagentKitId;

    private String secondPcrReagentKitId;

    private String secondPcrTesterId;

    private String description;
    
    private List<FirstPCRAssignTaskArgs> tasks;
    
    public List<FirstPCRAssignTaskArgs> getTasks()
    {
        return tasks;
    }
    
    public void setTasks(List<FirstPCRAssignTaskArgs> tasks)
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

    public String getSecondPcrReagentKitId() {
        return secondPcrReagentKitId;
    }

    public void setSecondPcrReagentKitId(String secondPcrReagentKitId) {
        this.secondPcrReagentKitId = secondPcrReagentKitId;
    }

    public String getSecondPcrTesterId() {
        return secondPcrTesterId;
    }

    public void setSecondPcrTesterId(String secondPcrTesterId) {
        this.secondPcrTesterId = secondPcrTesterId;
    }
}
