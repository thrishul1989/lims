package com.todaysoft.lims.testing.dtdata.model;

import java.util.List;

public class DTDataAssignModel
{
    private String taskCode;

    private List<DTDataTask> tasks;
    
    public List<DTDataTask> getTasks()
    {
        return tasks;
    }
    
    public void setTasks(List<DTDataTask> tasks)
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
