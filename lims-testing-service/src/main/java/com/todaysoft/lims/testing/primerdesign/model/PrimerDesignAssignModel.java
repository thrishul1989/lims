package com.todaysoft.lims.testing.primerdesign.model;

import java.util.List;

public class PrimerDesignAssignModel
{
    private String taskCode;

    private List<PrimerDesignTask> tasks;
    
    public List<PrimerDesignTask> getTasks()
    {
        return tasks;
    }
    
    public void setTasks(List<PrimerDesignTask> tasks)
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
