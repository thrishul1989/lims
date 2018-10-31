package com.todaysoft.lims.biology.model;


import java.util.Date;
import java.util.List;

public class BiologyDivisionSheetViewModel {

    private String squencingCode;

    private String sequencingType;

    private String runCode;

    private String laneCode;

    private Date assignTime;

    private String testorName;

    private Date completeTime;

    private Integer status;

    private String description;

    private String totalFile;

    private List<BiologyDivisionSheetSample> sheetSampleList;

    public String getSquencingCode() {
        return squencingCode;
    }

    public void setSquencingCode(String squencingCode) {
        this.squencingCode = squencingCode;
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

    public Date getAssignTime() {
        return assignTime;
    }

    public void setAssignTime(Date assignTime) {
        this.assignTime = assignTime;
    }

    public String getTestorName() {
        return testorName;
    }

    public void setTestorName(String testorName) {
        this.testorName = testorName;
    }

    public Date getCompleteTime() {
        return completeTime;
    }

    public void setCompleteTime(Date completeTime) {
        this.completeTime = completeTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<BiologyDivisionSheetSample> getSheetSampleList() {
        return sheetSampleList;
    }

    public void setSheetSampleList(List<BiologyDivisionSheetSample> sheetSampleList) {
        this.sheetSampleList = sheetSampleList;
    }

    public String getTotalFile() {
        return totalFile;
    }

    public void setTotalFile(String totalFile) {
        this.totalFile = totalFile;
    }
}
