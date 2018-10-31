package com.todaysoft.lims.technical.mybatis.entity;

import java.math.BigDecimal;
import java.util.Date;

public class ReceivedSample {
    private String sampleId;

    private Integer businessType;

    private String orderId;

    private String typeId;

    private String typeName;

    private String sampleCode;

    private String sampleName;

    private BigDecimal sampleSize;

    private Date samplingDate;

    private Integer purpose;

    private String lsLocation;

    private BigDecimal lsSize;

    private Integer lsStatus;

    private String tsLocation;

    private BigDecimal tsSize;

    private Integer tsStatus;

    private Integer processStatus;

    public String getSampleId() {
        return sampleId;
    }

    public void setSampleId(String sampleId) {
        this.sampleId = sampleId;
    }

    public Integer getBusinessType() {
        return businessType;
    }

    public void setBusinessType(Integer businessType) {
        this.businessType = businessType;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getSampleCode() {
        return sampleCode;
    }

    public void setSampleCode(String sampleCode) {
        this.sampleCode = sampleCode;
    }

    public String getSampleName() {
        return sampleName;
    }

    public void setSampleName(String sampleName) {
        this.sampleName = sampleName;
    }

    public BigDecimal getSampleSize() {
        return sampleSize;
    }

    public void setSampleSize(BigDecimal sampleSize) {
        this.sampleSize = sampleSize;
    }

    public Date getSamplingDate() {
        return samplingDate;
    }

    public void setSamplingDate(Date samplingDate) {
        this.samplingDate = samplingDate;
    }

    public Integer getPurpose() {
        return purpose;
    }

    public void setPurpose(Integer purpose) {
        this.purpose = purpose;
    }

    public String getLsLocation() {
        return lsLocation;
    }

    public void setLsLocation(String lsLocation) {
        this.lsLocation = lsLocation;
    }

    public BigDecimal getLsSize() {
        return lsSize;
    }

    public void setLsSize(BigDecimal lsSize) {
        this.lsSize = lsSize;
    }

    public Integer getLsStatus() {
        return lsStatus;
    }

    public void setLsStatus(Integer lsStatus) {
        this.lsStatus = lsStatus;
    }

    public String getTsLocation() {
        return tsLocation;
    }

    public void setTsLocation(String tsLocation) {
        this.tsLocation = tsLocation;
    }

    public BigDecimal getTsSize() {
        return tsSize;
    }

    public void setTsSize(BigDecimal tsSize) {
        this.tsSize = tsSize;
    }

    public Integer getTsStatus() {
        return tsStatus;
    }

    public void setTsStatus(Integer tsStatus) {
        this.tsStatus = tsStatus;
    }

    public Integer getProcessStatus() {
        return processStatus;
    }

    public void setProcessStatus(Integer processStatus) {
        this.processStatus = processStatus;
    }
}