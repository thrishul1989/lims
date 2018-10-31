package com.todaysoft.lims.testing.mlpatest.model;

import java.util.List;

public class MlpaTestAssignRequest
{
    private String testerId;

    private String reagentKitId;

    private String description;

    private List<MlpaTestTask> tasks;

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

    public String getReagentKitId() {
        return reagentKitId;
    }

    public void setReagentKitId(String reagentKitId) {
        this.reagentKitId = reagentKitId;
    }

    public List<MlpaTestTask> getTasks() {
        return tasks;
    }

    public void setTasks(List<MlpaTestTask> tasks) {
        this.tasks = tasks;
    }
}
