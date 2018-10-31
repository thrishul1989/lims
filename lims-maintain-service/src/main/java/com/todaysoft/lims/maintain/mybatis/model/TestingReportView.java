package com.todaysoft.lims.maintain.mybatis.model;

import java.util.Date;

public class TestingReportView {
    private String id;

    private String orderId;

    private String productId;

    private String methodId;

    private String sampleId;

    private Date startTime;

    private String activeTask;

    private Date endTime;

    private Byte endType;

    private String verifyTarget;

    private String scheduleNodes;

    private String scheduleOutputs;

    private String verifyKey;

    private Byte proccessState;

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

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
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

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public String getActiveTask() {
        return activeTask;
    }

    public void setActiveTask(String activeTask) {
        this.activeTask = activeTask;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Byte getEndType() {
        return endType;
    }

    public void setEndType(Byte endType) {
        this.endType = endType;
    }

    public String getVerifyTarget() {
        return verifyTarget;
    }

    public void setVerifyTarget(String verifyTarget) {
        this.verifyTarget = verifyTarget;
    }

    public String getScheduleNodes() {
        return scheduleNodes;
    }

    public void setScheduleNodes(String scheduleNodes) {
        this.scheduleNodes = scheduleNodes;
    }

    public String getScheduleOutputs() {
        return scheduleOutputs;
    }

    public void setScheduleOutputs(String scheduleOutputs) {
        this.scheduleOutputs = scheduleOutputs;
    }

    public String getVerifyKey() {
        return verifyKey;
    }

    public void setVerifyKey(String verifyKey) {
        this.verifyKey = verifyKey;
    }

    public Byte getProccessState() {
        return proccessState;
    }

    public void setProccessState(Byte proccessState) {
        this.proccessState = proccessState;
    }
}