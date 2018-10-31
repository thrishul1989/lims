package com.todaysoft.lims.testing.firstpcr.model;

import java.util.List;

public class FirstPcrAssignModel
{
    private String taskCode;

    private List<FirstPcrTask> tasks;
    
    public List<FirstPcrTask> getTasks()
    {
        return tasks;
    }
    
    public void setTasks(List<FirstPcrTask> tasks)
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
