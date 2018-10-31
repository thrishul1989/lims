package com.todaysoft.lims.testing.secondpcrsanger.model;

import java.util.List;

public class SecondPcrSangerAssignRequest
{
    private String taskCode;

    private String testerId;

    private String reagentKitId;

    private String description;

    private List<SecondPcrSangerAssignTaskArgs> tasks;

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

    public List<SecondPcrSangerAssignTaskArgs> getTasks() {
        return tasks;
    }

    public void setTasks(List<SecondPcrSangerAssignTaskArgs> tasks) {
        this.tasks = tasks;
    }

    public String getReagentKitId() {
        return reagentKitId;
    }

    public void setReagentKitId(String reagentKitId) {
        this.reagentKitId = reagentKitId;
    }
}
