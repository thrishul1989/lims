package com.todaysoft.lims.system.modules.bpm.bioanalysis.model;

import java.util.Date;

public class BiologyDivisionMonitor {

    private String id;

    private String sheetId;

    private String taskId;

    private Date startTime;

    private Date endTime;

    private Integer status;

    private String failLog;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSheetId() {
        return sheetId;
    }

    public void setSheetId(String sheetId) {
        this.sheetId = sheetId;
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

    public String getFailLog() {
        return failLog;
    }

    public void setFailLog(String failLog) {
        this.failLog = failLog;
    }
}