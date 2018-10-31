package com.todaysoft.lims.testing.pcrngsdata.model;

import java.util.List;

public class PcrNgsDataAssignModel
{
    private String taskCode;

    private List<PcrNgsDataTask> tasks;
    
    public List<PcrNgsDataTask> getTasks()
    {
        return tasks;
    }
    
    public void setTasks(List<PcrNgsDataTask> tasks)
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
