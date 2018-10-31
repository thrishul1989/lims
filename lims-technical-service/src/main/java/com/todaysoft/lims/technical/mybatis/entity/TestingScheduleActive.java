package com.todaysoft.lims.technical.mybatis.entity;

public class TestingScheduleActive {
    private String id;

    private String scheduleId;

    private String taskId;

    private Boolean runningStatus;

    private String taskRefer;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public Boolean getRunningStatus() {
        return runningStatus;
    }

    public void setRunningStatus(Boolean runningStatus) {
        this.runningStatus = runningStatus;
    }

    public String getTaskRefer() {
        return taskRefer;
    }

    public void setTaskRefer(String taskRefer) {
        this.taskRefer = taskRefer;
    }
}