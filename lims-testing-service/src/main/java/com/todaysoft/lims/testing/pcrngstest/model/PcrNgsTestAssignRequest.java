package com.todaysoft.lims.testing.pcrngstest.model;

import java.util.List;

public class PcrNgsTestAssignRequest
{
    private String taskCode;

    private String testerId;

    private String reagentKitId;

    private String description;

    private List<PcrNgsTestAssignTaskArgs> tasks;

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

    public List<PcrNgsTestAssignTaskArgs> getTasks() {
        return tasks;
    }

    public void setTasks(List<PcrNgsTestAssignTaskArgs> tasks) {
        this.tasks = tasks;
    }

    public String getReagentKitId() {
        return reagentKitId;
    }

    public void setReagentKitId(String reagentKitId) {
        this.reagentKitId = reagentKitId;
    }
}
