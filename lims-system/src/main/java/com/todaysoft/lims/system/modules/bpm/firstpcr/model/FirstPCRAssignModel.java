package com.todaysoft.lims.system.modules.bpm.firstpcr.model;

import java.util.List;

public class FirstPCRAssignModel
{
    private String taskCode;
    
    private List<FirstPCRTask> tasks;
    
    public List<FirstPCRTask> getTasks()
    {
        return tasks;
    }
    
    public void setTasks(List<FirstPCRTask> tasks)
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
