package com.todaysoft.lims.biology.model;

import java.util.Date;

public class BiologyAnnotationMonitor {
    private String id;

    private String taskId;

    private String monitorTaskId;

    private Date startTime;

    private Date endTime;

    private Integer status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMonitorTaskId() {
        return monitorTaskId;
    }

    public void setMonitorTaskId(String monitorTaskId) {
        this.monitorTaskId = monitorTaskId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }


}