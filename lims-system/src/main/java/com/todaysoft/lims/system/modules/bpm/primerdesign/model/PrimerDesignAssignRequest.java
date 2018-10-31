package com.todaysoft.lims.system.modules.bpm.primerdesign.model;

import java.math.BigDecimal;
import java.util.List;

public class PrimerDesignAssignRequest
{
    private String taskCode;

    private String testerId;
    
    private String description;
    
    private List<PrimerDesignAssignTaskArgs> tasks;
    
    public List<PrimerDesignAssignTaskArgs> getTasks()
    {
        return tasks;
    }
    
    public void setTasks(List<PrimerDesignAssignTaskArgs> tasks)
    {
        this.tasks = tasks;
    }

    public String getTaskCode() {
        return taskCode;
    }

    public void setTaskCode(String taskCode) {
        this.taskCode = taskCode;
    }

    public String getTesterId() {
        return testerId;
    }

    public void setTesterId(String testerId) {
        this.testerId = testerId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
