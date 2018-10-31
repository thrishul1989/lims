package com.todaysoft.lims.report.entity;

import com.todaysoft.lims.persist.UuidKeyEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "REPORT_SHEET_SAMPLE_SUCCESS_RATE")
public class ReportSheetSampleSuccessRate extends UuidKeyEntity {

    private String reportTaskId;

    private String semantic;

    private String taskName;

    private String sheetCode;

    private String assignName;

    private Date assignDate;

    private String testorName;

    private Date CompletedDate;

    private Integer sampleNum;

    private Integer totalTaskNum;

    private Integer successTaskNum;

    @Column(name="REPORT_TASK_ID")
    public String getReportTaskId() {
        return reportTaskId;
    }

    public void setReportTaskId(String reportTaskId) {
        this.reportTaskId = reportTaskId;
    }

    @Column(name="SEMANTIC")
    public String getSemantic() {
        return semantic;
    }

    public void setSemantic(String semantic) {
        this.semantic = semantic;
    }

    @Column(name="TASK_NAME")
    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    @Column(name="SHEET_CODE")
    public String getSheetCode() {
        return sheetCode;
    }

    public void setSheetCode(String sheetCode) {
        this.sheetCode = sheetCode;
    }

    @Column(name="ASSIGN_NAME")
    public String getAssignName() {
        return assignName;
    }

    public void setAssignName(String assignName) {
        this.assignName = assignName;
    }

    @Column(name="ASSIGN_DATE")
    public Date getAssignDate() {
        return assignDate;
    }

    public void setAssignDate(Date assignDate) {
        this.assignDate = assignDate;
    }

    @Column(name="TESTOR_NAME")
    public String getTestorName() {
        return testorName;
    }

    public void setTestorName(String testorName) {
        this.testorName = testorName;
    }

    @Column(name="COMPLETED_DATE")
    public Date getCompletedDate() {
        return CompletedDate;
    }

    public void setCompletedDate(Date completedDate) {
        CompletedDate = completedDate;
    }

    @Column(name="SAMPLE_NUM")
    public Integer getSampleNum() {
        return sampleNum;
    }

    public void setSampleNum(Integer sampleNum) {
        this.sampleNum = sampleNum;
    }

    @Column(name="TOTAL_TASK_NUM")
    public Integer getTotalTaskNum() {
        return totalTaskNum;
    }

    public void setTotalTaskNum(Integer totalTaskNum) {
        this.totalTaskNum = totalTaskNum;
    }

    @Column(name="SUCCESS_TASK_NUM")
    public Integer getSuccessTaskNum() {
        return successTaskNum;
    }

    public void setSuccessTaskNum(Integer successTaskNum) {
        this.successTaskNum = successTaskNum;
    }
}
