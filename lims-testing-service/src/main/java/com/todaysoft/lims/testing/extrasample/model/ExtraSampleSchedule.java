package com.todaysoft.lims.testing.extrasample.model;


import java.util.Date;

public class ExtraSampleSchedule
{
    private String id;

    private String orderId;

    private String orderCode;

    private String sampleId;

    private String sampleCode;

    private String sampleName;

    private String productId;

    private String productName;

    private String productCode;

    private String testingMethodId;

    private String testingMethodName;

    private String activeTaskName;

    private Date startTime;

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

    public String getSampleId() {
        return sampleId;
    }

    public void setSampleId(String sampleId) {
        this.sampleId = sampleId;
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

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getTestingMethodId() {
        return testingMethodId;
    }

    public void setTestingMethodId(String testingMethodId) {
        this.testingMethodId = testingMethodId;
    }

    public String getTestingMethodName() {
        return testingMethodName;
    }

    public void setTestingMethodName(String testingMethodName) {
        this.testingMethodName = testingMethodName;
    }

    public String getActiveTaskName() {
        return activeTaskName;
    }

    public void setActiveTaskName(String activeTaskName) {
        this.activeTaskName = activeTaskName;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }
}
