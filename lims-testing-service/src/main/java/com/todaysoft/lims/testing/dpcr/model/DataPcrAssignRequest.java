package com.todaysoft.lims.testing.dpcr.model;

import java.util.List;

public class DataPcrAssignRequest
{
    private String testerId;

    private String description;

    private List<DataPcrAssignTaskArgs> tasks;

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

    public List<DataPcrAssignTaskArgs> getTasks() {
        return tasks;
    }

    public void setTasks(List<DataPcrAssignTaskArgs> tasks) {
        this.tasks = tasks;
    }

}
