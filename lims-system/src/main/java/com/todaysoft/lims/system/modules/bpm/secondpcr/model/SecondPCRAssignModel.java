package com.todaysoft.lims.system.modules.bpm.secondpcr.model;

import java.util.List;

public class SecondPCRAssignModel
{
    private String taskCode;
    
    private List<SecondPCRTask> tasks;
    
    public List<SecondPCRTask> getTasks()
    {
        return tasks;
    }
    
    public void setTasks(List<SecondPCRTask> tasks)
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
