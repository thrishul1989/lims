package com.todaysoft.lims.system.model.vo;


import java.util.Date;

public class TaskFailExportModel
{
    private String taskName;

    private String taskSheetCode;

    private String orderCode;

    private String productName;

    private String methods;

    private String receivedSampleName;

    private String receivedSampleCode;

    private String failRemark;

    private Date submitDate;

    private String operatorName;

    private Date operatorDate;

    private String operateStrategy;

    private String operateRemark;

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskSheetCode() {
        return taskSheetCode;
    }

    public void setTaskSheetCode(String taskSheetCode) {
        this.taskSheetCode = taskSheetCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getMethods() {
        return methods;
    }

    public void setMethods(String methods) {
        this.methods = methods;
    }

    public String getReceivedSampleName() {
        return receivedSampleName;
    }

    public void setReceivedSampleName(String receivedSampleName) {
        this.receivedSampleName = receivedSampleName;
    }

    public String getReceivedSampleCode() {
        return receivedSampleCode;
    }

    public void setReceivedSampleCode(String receivedSampleCode) {
        this.receivedSampleCode = receivedSampleCode;
    }

    public String getFailRemark() {
        return failRemark;
    }

    public void setFailRemark(String failRemark) {
        this.failRemark = failRemark;
    }

    public Date getSubmitDate() {
        return submitDate;
    }

    public void setSubmitDate(Date submitDate) {
        this.submitDate = submitDate;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public Date getOperatorDate() {
        return operatorDate;
    }

    public void setOperatorDate(Date operatorDate) {
        this.operatorDate = operatorDate;
    }

    public String getOperateStrategy() {
        return operateStrategy;
    }

    public void setOperateStrategy(String operateStrategy) {
        this.operateStrategy = operateStrategy;
    }

    public String getOperateRemark() {
        return operateRemark;
    }

    public void setOperateRemark(String operateRemark) {
        this.operateRemark = operateRemark;
    }
}
