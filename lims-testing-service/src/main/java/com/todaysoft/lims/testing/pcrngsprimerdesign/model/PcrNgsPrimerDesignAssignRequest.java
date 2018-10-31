package com.todaysoft.lims.testing.pcrngsprimerdesign.model;

import java.util.List;

public class PcrNgsPrimerDesignAssignRequest
{
    private String taskCode;

    private String testerId;

    private String description;

    private List<PcrNgsPrimerDesignAssignTaskArgs> tasks;

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

    public List<PcrNgsPrimerDesignAssignTaskArgs> getTasks() {
        return tasks;
    }

    public void setTasks(List<PcrNgsPrimerDesignAssignTaskArgs> tasks) {
        this.tasks = tasks;
    }
}
