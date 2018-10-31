package com.todaysoft.lims.testing.secondpcr.model;

import java.util.List;

public class SecondPcrAssignModel
{
    private String taskCode;

    private List<SecondPcrTask> tasks;
    
    public List<SecondPcrTask> getTasks()
    {
        return tasks;
    }
    
    public void setTasks(List<SecondPcrTask> tasks)
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
