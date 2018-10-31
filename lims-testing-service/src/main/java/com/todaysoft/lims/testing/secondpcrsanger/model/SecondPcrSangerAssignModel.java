package com.todaysoft.lims.testing.secondpcrsanger.model;

import java.util.List;

public class SecondPcrSangerAssignModel
{
    private String taskCode;

    private List<SecondPcrSangerTask> tasks;
    
    public List<SecondPcrSangerTask> getTasks()
    {
        return tasks;
    }
    
    public void setTasks(List<SecondPcrSangerTask> tasks)
    {
        this.tasks = tasks;
    }

    public String getTaskCode() {
        return taskCode;
    }

    public void setTaskCode(String taskCode) {
        this.taskCode = taskCode;
    }
}
