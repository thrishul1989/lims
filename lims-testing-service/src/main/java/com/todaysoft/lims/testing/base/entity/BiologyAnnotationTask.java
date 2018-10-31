package com.todaysoft.lims.testing.base.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "BIOLOGY_ANNOTATION_TASK")
public class BiologyAnnotationTask implements Serializable
{
    private String id;

    private String sequencingCode;

    private Integer orderType;

    private String orderCode;

    private String productCode;

    private String productName;

    private String methodId;

    private String sampleCode;

    private String dataCode;

    private Date startTime;

    private Date endTime;

    private Integer resubmit;

    private Integer resubmitCount;

    private Integer primarySample;//是否主样本（0-不是 1-是）

    private Integer status;

    private Integer family;

    private Integer annotationState;

    @Id
    @Column(name = "ID")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "SEQUENCING_CODE")
    public String getSequencingCode() {
        return sequencingCode;
    }

    public void setSequencingCode(String sequencingCode) {
        this.sequencingCode = sequencingCode;
    }

    @Column(name = "ORDER_TYPE")
    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    @Column(name = "ORDER_CODE")
    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    @Column(name = "PRODUCT_CODE")
    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    @Column(name = "PRODUCT_NAME")
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    @Column(name = "METHOD_ID")
    public String getMethodId() {
        return methodId;
    }

    public void setMethodId(String methodId) {
        this.methodId = methodId;
    }

    @Column(name = "SAMPLE_CODE")
    public String getSampleCode() {
        return sampleCode;
    }

    public void setSampleCode(String sampleCode) {
        this.sampleCode = sampleCode;
    }

    @Column(name = "DATA_CODE")
    public String getDataCode() {
        return dataCode;
    }

    public void setDataCode(String dataCode) {
        this.dataCode = dataCode;
    }

    @Column(name = "START_TIME")
    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    @Column(name = "END_TIME")
    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Column(name = "RESUBMIT")
    public Integer getResubmit() {
        return resubmit;
    }

    public void setResubmit(Integer resubmit) {
        this.resubmit = resubmit;
    }

    @Column(name = "RESUBMIT_COUNT")
    public Integer getResubmitCount() {
        return resubmitCount;
    }

    public void setResubmitCount(Integer resubmitCount) {
        this.resubmitCount = resubmitCount;
    }

    @Column(name = "PRIMARY_SAMPLE")
    public Integer getPrimarySample() {
        return primarySample;
    }

    public void setPrimarySample(Integer primarySample) {
        this.primarySample = primarySample;
    }

    @Column(name = "STATUS")
    public Integer getStatus() {
        return status;
    }
    public void setStatus(Integer status) {
        this.status = status;
    }

    @Column(name = "FAMILY")
    public Integer getFamily() {
        return family;
    }
    public void setFamily(Integer family) {
        this.family = family;
    }

    @Column(name = "ANNOTATION_STATE")
    public Integer getAnnotationState() {
        return annotationState;
    }
    public void setAnnotationState(Integer annotationState) {
        this.annotationState = annotationState;
    }
}
