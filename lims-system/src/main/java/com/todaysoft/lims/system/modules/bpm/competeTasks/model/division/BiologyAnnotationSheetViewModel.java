package com.todaysoft.lims.system.modules.bpm.competeTasks.model.division;


import com.todaysoft.lims.system.modules.bpm.bioanalysis.model.BiologyDivisionSheetSample;

import java.util.Date;
import java.util.List;

public class BiologyAnnotationSheetViewModel {

    private String code;

    private String squencingCode;

    private String dataCode;

    private String sampleCode;

    private Integer annotationState;

    private Integer statisticsState;

    private String statisticsData;

    private Integer resubmitCount;

    private Integer result;

    private String remark;

    private Date assignTime;

    private String testorName;

    private Date completeTime;

    private Integer status;

    private String solvePerson;

    public String getSolvePerson() {
        return solvePerson;
    }

    public void setSolvePerson(String solvePerson) {
        this.solvePerson = solvePerson;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSquencingCode() {
        return squencingCode;
    }

    public void setSquencingCode(String squencingCode) {
        this.squencingCode = squencingCode;
    }

    public String getDataCode() {
        return dataCode;
    }

    public void setDataCode(String dataCode) {
        this.dataCode = dataCode;
    }

    public String getSampleCode() {
        return sampleCode;
    }

    public void setSampleCode(String sampleCode) {
        this.sampleCode = sampleCode;
    }

    public Integer getAnnotationState() {
        return annotationState;
    }

    public void setAnnotationState(Integer annotationState) {
        this.annotationState = annotationState;
    }

    public Integer getStatisticsState() {
        return statisticsState;
    }

    public void setStatisticsState(Integer statisticsState) {
        this.statisticsState = statisticsState;
    }

    public String getStatisticsData() {
        return statisticsData;
    }

    public void setStatisticsData(String statisticsData) {
        this.statisticsData = statisticsData;
    }

    public Integer getResubmitCount() {
        return resubmitCount;
    }

    public void setResubmitCount(Integer resubmitCount) {
        this.resubmitCount = resubmitCount;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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
}
