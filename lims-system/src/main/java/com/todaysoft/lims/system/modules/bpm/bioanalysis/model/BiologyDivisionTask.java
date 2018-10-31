package com.todaysoft.lims.system.modules.bpm.bioanalysis.model;

import java.util.Date;

public class BiologyDivisionTask {

    public static final Integer WAITING_DIVISION_STATUS=0;

    public static final Integer GOING_DIVISION_STATUS=1;

    public static final Integer FAIL_DIVISION_STATUS=2;

    public static final Integer SUCCESS_DIVISION_STATUS=3;

    private String id;

    private String sequencingCode;

    private String sequencingType;

    private String runCode;

    private String laneCode;

    private Date startTime;

    private Date endTime;

    private Integer status;

    private Boolean resubmit;

    private Integer resubmitCount;

    private Boolean ifUrgent;

    private Date urgentUpdateTime;

    private String urgentName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSequencingCode() {
        return sequencingCode;
    }

    public void setSequencingCode(String sequencingCode) {
        this.sequencingCode = sequencingCode;
    }

    public String getSequencingType() {
        return sequencingType;
    }

    public void setSequencingType(String sequencingType) {
        this.sequencingType = sequencingType;
    }

    public String getRunCode() {
        return runCode;
    }

    public void setRunCode(String runCode) {
        this.runCode = runCode;
    }

    public String getLaneCode() {
        return laneCode;
    }

    public void setLaneCode(String laneCode) {
        this.laneCode = laneCode;
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

    public Boolean getResubmit() {
        return resubmit;
    }

    public void setResubmit(Boolean resubmit) {
        this.resubmit = resubmit;
    }

    public Integer getResubmitCount() {
        return resubmitCount;
    }

    public void setResubmitCount(Integer resubmitCount) {
        this.resubmitCount = resubmitCount;
    }

    public Boolean getIfUrgent() {
        return ifUrgent;
    }

    public void setIfUrgent(Boolean ifUrgent) {
        this.ifUrgent = ifUrgent;
    }

    public Date getUrgentUpdateTime() {
        return urgentUpdateTime;
    }

    public void setUrgentUpdateTime(Date urgentUpdateTime) {
        this.urgentUpdateTime = urgentUpdateTime;
    }

    public String getUrgentName() {
        return urgentName;
    }

    public void setUrgentName(String urgentName) {
        this.urgentName = urgentName;
    }
}