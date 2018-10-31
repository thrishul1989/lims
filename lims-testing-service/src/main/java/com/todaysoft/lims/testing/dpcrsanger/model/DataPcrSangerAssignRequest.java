package com.todaysoft.lims.testing.dpcrsanger.model;

import java.util.List;

public class DataPcrSangerAssignRequest
{
    private String testerId;

    private String description;

    private List<DataPcrSangerAssignTaskArgs> tasks;

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

    public List<DataPcrSangerAssignTaskArgs> getTasks() {
        return tasks;
    }

    public void setTasks(List<DataPcrSangerAssignTaskArgs> tasks) {
        this.tasks = tasks;
    }

}
