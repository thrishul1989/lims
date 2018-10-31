package com.todaysoft.lims.testing.secondpcr.model;

import java.util.List;

public class SecondPcrAssignRequest
{
    private String taskCode;

    private String testerId;

    private String reagentKitId;

    private String description;

    private List<SecondPcrAssignTaskArgs> tasks;

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

    public List<SecondPcrAssignTaskArgs> getTasks() {
        return tasks;
    }

    public void setTasks(List<SecondPcrAssignTaskArgs> tasks) {
        this.tasks = tasks;
    }

    public String getReagentKitId() {
        return reagentKitId;
    }

    public void setReagentKitId(String reagentKitId) {
        this.reagentKitId = reagentKitId;
    }
}
