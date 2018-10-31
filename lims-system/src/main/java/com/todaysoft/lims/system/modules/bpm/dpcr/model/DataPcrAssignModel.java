package com.todaysoft.lims.system.modules.bpm.dpcr.model;

import java.util.List;

public class DataPcrAssignModel
{
    private String taskCode;
    
    private List<DataPcrTask> tasks;
    
    public List<DataPcrTask> getTasks()
    {
        return tasks;
    }
    
    public void setTasks(List<DataPcrTask> tasks)
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
