package com.todaysoft.lims.report.entity;

import com.todaysoft.lims.persist.UuidKeyEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "REPORT_TASK_FAIL_SOLVE")
public class ReportTaskFailSolve extends UuidKeyEntity {

    private String reportTaskId;

    private String taskName;

    private String taskSemantic;

    private String orderCode;

    private String productName;

    private String methodName;

    private String receivedSampleName;

    private String receivedSampleCode;

    private String taskResultRemark;

    private String taskDetail;

    private String taskResult;

    private String taskResultStrategy;

    private Date endTime;

    private Date submitTime;

    private String submitName;

    private Date solveTime;

    private String solveName;

    private String solveRemark;

    private String solveStrage;


    @Column(name="REPORT_TASK_ID")
    public String getReportTaskId() {
        return reportTaskId;
    }

    public void setReportTaskId(String reportTaskId) {
        this.reportTaskId = reportTaskId;
    }

    @Column(name="TASK_NAME")
    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    @Column(name="TASK_SEMANTIC")
    public String getTaskSemantic() {
        return taskSemantic;
    }

    public void setTaskSemantic(String taskSemantic) {
        this.taskSemantic = taskSemantic;
    }

    @Column(name="ORDER_CODE")
    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    @Column(name="PRODUCT_NAME")
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    @Column(name="METHOD_NAME")
    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    @Column(name="RECEIVED_SAMPLE_NAME")
    public String getReceivedSampleName() {
        return receivedSampleName;
    }

    public void setReceivedSampleName(String receivedSampleName) {
        this.receivedSampleName = receivedSampleName;
    }

    @Column(name="RECEIVED_SAMPLE_CODE")
    public String getReceivedSampleCode() {
        return receivedSampleCode;
    }

    public void setReceivedSampleCode(String receivedSampleCode) {
        this.receivedSampleCode = receivedSampleCode;
    }

    @Column(name="TASK_RESULT_REMARK")
    public String getTaskResultRemark() {
        return taskResultRemark;
    }

    public void setTaskResultRemark(String taskResultRemark) {
        this.taskResultRemark = taskResultRemark;
    }

    @Column(name="TASK_DETAIL")
    public String getTaskDetail() {
        return taskDetail;
    }

    public void setTaskDetail(String taskDetail) {
        this.taskDetail = taskDetail;
    }

    @Column(name="TASK_RESULT")
    public String getTaskResult() {
        return taskResult;
    }

    public void setTaskResult(String taskResult) {
        this.taskResult = taskResult;
    }

    @Column(name="TASK_RESULT_STRATEGY")
    public String getTaskResultStrategy() {
        return taskResultStrategy;
    }

    public void setTaskResultStrategy(String taskResultStrategy) {
        this.taskResultStrategy = taskResultStrategy;
    }

    @Column(name="END_TIME")
    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Column(name="SUBMIT_TIME")
    public Date getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(Date submitTime) {
        this.submitTime = submitTime;
    }

    @Column(name="SUBMIT_NAME")
    public String getSubmitName() {
        return submitName;
    }

    public void setSubmitName(String submitName) {
        this.submitName = submitName;
    }

    @Column(name="SOLVE_TIME")
    public Date getSolveTime() {
        return solveTime;
    }

    public void setSolveTime(Date solveTime) {
        this.solveTime = solveTime;
    }

    @Column(name="SOLVE_NAME")
    public String getSolveName() {
        return solveName;
    }

    public void setSolveName(String solveName) {
        this.solveName = solveName;
    }

    @Column(name="SOLVE_REMARK")
    public String getSolveRemark() {
        return solveRemark;
    }

    public void setSolveRemark(String solveRemark) {
        this.solveRemark = solveRemark;
    }

    @Column(name="SOLVE_STRAGE")
    public String getSolveStrage() {
        return solveStrage;
    }

    public void setSolveStrage(String solveStrage) {
        this.solveStrage = solveStrage;
    }
}
