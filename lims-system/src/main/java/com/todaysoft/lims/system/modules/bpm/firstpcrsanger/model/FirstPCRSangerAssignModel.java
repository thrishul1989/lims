package com.todaysoft.lims.system.modules.bpm.firstpcrsanger.model;

import java.util.List;

public class FirstPCRSangerAssignModel
{
    private String taskCode;
    
    private List<FirstPCRSangerTask> tasks;
    
    public List<FirstPCRSangerTask> getTasks()
    {
        return tasks;
    }
    
    public void setTasks(List<FirstPCRSangerTask> tasks)
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
