package com.todaysoft.lims.system.modules.bpm.pcrngstest.model;

import java.util.List;

public class PcrNgsTestAssignModel
{
    private String taskCode;
    
    private List<PcrNgsTestTask> tasks;
    
    public List<PcrNgsTestTask> getTasks()
    {
        return tasks;
    }
    
    public void setTasks(List<PcrNgsTestTask> tasks)
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
