package com.todaysoft.lims.system.modules.bpm.qpcr.model;


import java.util.List;

public class QpcrAssignGroupModel
{
    private int referenceNumber;

    private List<QpcrTask> tasks;

    public int getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(int referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public List<QpcrTask> getTasks() {
        return tasks;
    }

    public void setTasks(List<QpcrTask> tasks) {
        this.tasks = tasks;
    }
}
