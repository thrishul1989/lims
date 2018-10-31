package com.todaysoft.lims.testing.pcrngsprimerdesign.model;

import java.util.List;

public class PcrNgsPrimerDesignAssignModel
{
    private String taskCode;

    private List<PcrNgsPrimerDesignTask> tasks;
    
    public List<PcrNgsPrimerDesignTask> getTasks()
    {
        return tasks;
    }
    
    public void setTasks(List<PcrNgsPrimerDesignTask> tasks)
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
