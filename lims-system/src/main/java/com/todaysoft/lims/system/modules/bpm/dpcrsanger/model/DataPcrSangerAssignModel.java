package com.todaysoft.lims.system.modules.bpm.dpcrsanger.model;

import java.util.List;

public class DataPcrSangerAssignModel
{
    private String taskCode;
    
    private List<DataPcrSangerTask> tasks;
    
    public List<DataPcrSangerTask> getTasks()
    {
        return tasks;
    }
    
    public void setTasks(List<DataPcrSangerTask> tasks)
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
