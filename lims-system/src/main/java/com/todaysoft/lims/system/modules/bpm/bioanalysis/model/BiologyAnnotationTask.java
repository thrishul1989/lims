package com.todaysoft.lims.system.modules.bpm.bioanalysis.model;

import java.util.Date;

public class BiologyAnnotationTask {

    private String id;

    private String sequencingCode;

    private Integer orderType;

    private String orderCode;

    private String productCode;

    private String productName;

    private String methodId;

    private Integer primarySample;

    private Integer family;

    private Integer familyState;

    private String sampleCode;

    private String dataCode;

    private Date startTime;

    private Date endTime;

    private Integer status;

    private Integer annotationState;

    private Integer statisticsState;

    private Integer resubmit;

    private Integer resubmitCount;

    private Integer ifUrgent;

    private String remark;

    private String scheduleStatus;//流程状态

    private Integer errorState;//异常状态（0-正常 1-手动合格 2-上报 3-重新生信注释

    public Integer getErrorState() {
        return errorState;
    }

    public void setErrorState(Integer errorState) {
        this.errorState = errorState;
    }

    public String getScheduleStatus() {
        return scheduleStatus;
    }

    public void setScheduleStatus(String scheduleStatus) {
        this.scheduleStatus = scheduleStatus;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

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

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getMethodId() {
        return methodId;
    }

    public void setMethodId(String methodId) {
        this.methodId = methodId;
    }

    public String getSampleCode() {
        return sampleCode;
    }

    public void setSampleCode(String sampleCode) {
        this.sampleCode = sampleCode;
    }

    public String getDataCode() {
        return dataCode;
    }

    public void setDataCode(String dataCode) {
        this.dataCode = dataCode;
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

    public Integer getResubmitCount() {
        return resubmitCount;
    }

    public void setResubmitCount(Integer resubmitCount) {
        this.resubmitCount = resubmitCount;
    }

    public Integer getPrimarySample() {
        return primarySample;
    }

    public void setPrimarySample(Integer primarySample) {
        this.primarySample = primarySample;
    }

    public Integer getFamily() {
        return family;
    }

    public void setFamily(Integer family) {
        this.family = family;
    }

    public Integer getFamilyState() {
        return familyState;
    }

    public void setFamilyState(Integer familyState) {
        this.familyState = familyState;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public Integer getResubmit() {
        return resubmit;
    }

    public void setResubmit(Integer resubmit) {
        this.resubmit = resubmit;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public Integer getIfUrgent() {
        return ifUrgent;
    }

    public void setIfUrgent(Integer ifUrgent) {
        this.ifUrgent = ifUrgent;
    }
}