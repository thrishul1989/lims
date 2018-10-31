package com.todaysoft.lims.testing.mlpatest.model;


import java.util.List;

public class MlpaTestAssignGroupModel
{
    private int referenceNumber;

    private List<MlpaTestTask> tasks;

    public int getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(int referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public List<MlpaTestTask> getTasks() {
        return tasks;
    }

    public void setTasks(List<MlpaTestTask> tasks) {
        this.tasks = tasks;
    }
}
