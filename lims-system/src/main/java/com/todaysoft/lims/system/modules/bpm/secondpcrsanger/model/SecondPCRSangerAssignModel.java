package com.todaysoft.lims.system.modules.bpm.secondpcrsanger.model;

import java.util.List;

public class SecondPCRSangerAssignModel
{
    private String taskCode;
    
    private List<SecondPCRSangerTask> tasks;
    
    public List<SecondPCRSangerTask> getTasks()
    {
        return tasks;
    }
    
    public void setTasks(List<SecondPCRSangerTask> tasks)
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
}
