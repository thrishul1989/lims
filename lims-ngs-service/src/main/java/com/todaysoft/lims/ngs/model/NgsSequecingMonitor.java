package com.todaysoft.lims.ngs.model;

import java.util.Date;

public class NgsSequecingMonitor {
    private String ngsSheetId;

    private String monitorTaskId;

    private Date sequecingStartTime;

    private Date sequecingEndTime;

    private Integer sequecingStatus;

    private Boolean fileIntegrity;

    private String sequecingLog;

    public String getNgsSheetId() {
        return ngsSheetId;
    }

    public void setNgsSheetId(String ngsSheetId) {
        this.ngsSheetId = ngsSheetId;
    }

    public String getMonitorTaskId() {
        return monitorTaskId;
    }

    public void setMonitorTaskId(String monitorTaskId) {
        this.monitorTaskId = monitorTaskId;
    }

    public Date getSequecingStartTime() {
        return sequecingStartTime;
    }

    public void setSequecingStartTime(Date sequecingStartTime) {
        this.sequecingStartTime = sequecingStartTime;
    }

    public Date getSequecingEndTime() {
        return sequecingEndTime;
    }

    public void setSequecingEndTime(Date sequecingEndTime) {
        this.sequecingEndTime = sequecingEndTime;
    }

    public Integer getSequecingStatus() {
        return sequecingStatus;
    }

    public void setSequecingStatus(Integer sequecingStatus) {
        this.sequecingStatus = sequecingStatus;
    }

    public Boolean getFileIntegrity() {
        return fileIntegrity;
    }

    public void setFileIntegrity(Boolean fileIntegrity) {
        this.fileIntegrity = fileIntegrity;
    }

    public String getSequecingLog() {
        return sequecingLog;
    }

    public void setSequecingLog(String sequecingLog) {
        this.sequecingLog = sequecingLog;
    }
}