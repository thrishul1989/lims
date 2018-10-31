package com.todaysoft.lims.technical.mybatis.entity;

import java.util.Date;

public class TestingReportSample {
    private String id;

    private String reportId;

    private String scheduleId;

    private String methodId;

    private String sampleId;

    private String purpose;

    private Boolean qualified;

    private String unqualifiedRemark;

    private String unqualifiedStrategy;

    private Date updateTime;

    private String combineType;

    private String mutationSource;

    private String combineCode;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    public String getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
    }

    public String getMethodId() {
        return methodId;
    }

    public void setMethodId(String methodId) {
        this.methodId = methodId;
    }

    public String getSampleId() {
        return sampleId;
    }

    public void setSampleId(String sampleId) {
        this.sampleId = sampleId;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public Boolean getQualified() {
        return qualified;
    }

    public void setQualified(Boolean qualified) {
        this.qualified = qualified;
    }

    public String getUnqualifiedRemark() {
        return unqualifiedRemark;
    }

    public void setUnqualifiedRemark(String unqualifiedRemark) {
        this.unqualifiedRemark = unqualifiedRemark;
    }

    public String getUnqualifiedStrategy() {
        return unqualifiedStrategy;
    }

    public void setUnqualifiedStrategy(String unqualifiedStrategy) {
        this.unqualifiedStrategy = unqualifiedStrategy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getCombineType() {
        return combineType;
    }

    public void setCombineType(String combineType) {
        this.combineType = combineType;
    }

    public String getMutationSource() {
        return mutationSource;
    }

    public void setMutationSource(String mutationSource) {
        this.mutationSource = mutationSource;
    }

    public String getCombineCode() {
        return combineCode;
    }

    public void setCombineCode(String combineCode) {
        this.combineCode = combineCode;
    }
}