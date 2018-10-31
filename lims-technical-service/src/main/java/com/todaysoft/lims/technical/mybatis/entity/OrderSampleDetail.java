package com.todaysoft.lims.technical.mybatis.entity;

import java.math.BigDecimal;
import java.util.Date;

public class OrderSampleDetail {
    
    public static final Long SAMPLEBTYPE_PRIMARYSAMPLE=1L;//主样本
    
    private String id;

    private String orderId;

    private String orderCode;

    private Boolean orderType;

    private String customerId;

    private String customerName;

    private String salesmanId;

    private String salesmanName;

    private String sampleId;

    private Long sampleBtype;

    private String sampleTypeId;

    private String sampleTypeName;

    private String sampleCode;

    private String sampleName;

    private BigDecimal sampleSize;

    private Date samplingDate;

    private Integer purpose;

    private Integer transportStatus;

    private Date updateTime;

    private Integer sampleErrorStatus;

    private String errorReason;

    private Date acceptSampleTime;

    private String sampleErrorNewNo;

    private String errorDealRemark;

    private String familyRelation;

    private Integer synchronizedStatus;

    private Integer ownerPhenotype;

    private String ownerName;

    private Short ownerAge;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public Boolean getOrderType() {
        return orderType;
    }

    public void setOrderType(Boolean orderType) {
        this.orderType = orderType;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getSalesmanId() {
        return salesmanId;
    }

    public void setSalesmanId(String salesmanId) {
        this.salesmanId = salesmanId;
    }

    public String getSalesmanName() {
        return salesmanName;
    }

    public void setSalesmanName(String salesmanName) {
        this.salesmanName = salesmanName;
    }

    public String getSampleId() {
        return sampleId;
    }

    public void setSampleId(String sampleId) {
        this.sampleId = sampleId;
    }

    public Long getSampleBtype() {
        return sampleBtype;
    }

    public void setSampleBtype(Long sampleBtype) {
        this.sampleBtype = sampleBtype;
    }

    public String getSampleTypeId() {
        return sampleTypeId;
    }

    public void setSampleTypeId(String sampleTypeId) {
        this.sampleTypeId = sampleTypeId;
    }

    public String getSampleTypeName() {
        return sampleTypeName;
    }

    public void setSampleTypeName(String sampleTypeName) {
        this.sampleTypeName = sampleTypeName;
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

    public Integer getTransportStatus() {
        return transportStatus;
    }

    public void setTransportStatus(Integer transportStatus) {
        this.transportStatus = transportStatus;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getSampleErrorStatus() {
        return sampleErrorStatus;
    }

    public void setSampleErrorStatus(Integer sampleErrorStatus) {
        this.sampleErrorStatus = sampleErrorStatus;
    }

    public String getErrorReason() {
        return errorReason;
    }

    public void setErrorReason(String errorReason) {
        this.errorReason = errorReason;
    }

    public Date getAcceptSampleTime() {
        return acceptSampleTime;
    }

    public void setAcceptSampleTime(Date acceptSampleTime) {
        this.acceptSampleTime = acceptSampleTime;
    }

    public String getSampleErrorNewNo() {
        return sampleErrorNewNo;
    }

    public void setSampleErrorNewNo(String sampleErrorNewNo) {
        this.sampleErrorNewNo = sampleErrorNewNo;
    }

    public String getErrorDealRemark() {
        return errorDealRemark;
    }

    public void setErrorDealRemark(String errorDealRemark) {
        this.errorDealRemark = errorDealRemark;
    }

    public String getFamilyRelation() {
        return familyRelation;
    }

    public void setFamilyRelation(String familyRelation) {
        this.familyRelation = familyRelation;
    }

    public Integer getSynchronizedStatus() {
        return synchronizedStatus;
    }

    public void setSynchronizedStatus(Integer synchronizedStatus) {
        this.synchronizedStatus = synchronizedStatus;
    }

    public Integer getOwnerPhenotype() {
        return ownerPhenotype;
    }

    public void setOwnerPhenotype(Integer ownerPhenotype) {
        this.ownerPhenotype = ownerPhenotype;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public Short getOwnerAge() {
        return ownerAge;
    }

    public void setOwnerAge(Short ownerAge) {
        this.ownerAge = ownerAge;
    }
}