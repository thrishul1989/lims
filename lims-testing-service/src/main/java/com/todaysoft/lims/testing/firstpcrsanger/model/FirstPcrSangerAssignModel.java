package com.todaysoft.lims.testing.firstpcrsanger.model;

import java.util.List;

public class FirstPcrSangerAssignModel
{
    private String taskCode;

    private List<FirstPcrSangerTask> tasks;
    
    public List<FirstPcrSangerTask> getTasks()
    {
        return tasks;
    }
    
    public void setTasks(List<FirstPcrSangerTask> tasks)
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
