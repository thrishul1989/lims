package com.todaysoft.lims.report.entity;

import com.todaysoft.lims.persist.UuidKeyEntity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "REPORT_ORDER_LIB")
public class ReportOrderLib extends UuidKeyEntity
{
    private String reportTaskId;

    private ReportOrderDna reportOrderDna;

    private String libSheetCode;

    private String libAssignName;

    private Date libAssignDate;

    private String libTestorName;

    private Date libCompleteDate;

    private String libSampleCode;

    private String attribution;

    private String reagentVariables;

    private String qualification;

    private String remark;

    private String libLocation;

    @Column(name="REPORT_TASK_ID")
    public String getReportTaskId() {
        return reportTaskId;
    }

    public void setReportTaskId(String reportTaskId) {
        this.reportTaskId = reportTaskId;
    }


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDER_DNA_ID")
    public ReportOrderDna getReportOrderDna() {
        return reportOrderDna;
    }

    public void setReportOrderDna(ReportOrderDna reportOrderDna) {
        this.reportOrderDna = reportOrderDna;
    }

    @Column(name="LIB_SHEET_CODE")
    public String getLibSheetCode() {
        return libSheetCode;
    }

    public void setLibSheetCode(String libSheetCode) {
        this.libSheetCode = libSheetCode;
    }

    @Column(name="LIB_ASSIGN_NAME")
    public String getLibAssignName() {
        return libAssignName;
    }

    public void setLibAssignName(String libAssignName) {
        this.libAssignName = libAssignName;
    }

    @Column(name="LIB_ASSIGN_DATE")
    public Date getLibAssignDate() {
        return libAssignDate;
    }

    public void setLibAssignDate(Date libAssignDate) {
        this.libAssignDate = libAssignDate;
    }

    @Column(name="LIB_TESTOR_NAME")
    public String getLibTestorName() {
        return libTestorName;
    }

    public void setLibTestorName(String libTestorName) {
        this.libTestorName = libTestorName;
    }

    @Column(name="LIB_COMPLETE_DATE")
    public Date getLibCompleteDate() {
        return libCompleteDate;
    }

    public void setLibCompleteDate(Date libCompleteDate) {
        this.libCompleteDate = libCompleteDate;
    }

    @Column(name="LIB_SAMPLE_CODE")
    public String getLibSampleCode() {
        return libSampleCode;
    }

    public void setLibSampleCode(String libSampleCode) {
        this.libSampleCode = libSampleCode;
    }

    @Column(name="ATTRIBUTION")
    public String getAttribution() {
        return attribution;
    }

    public void setAttribution(String attribution) {
        this.attribution = attribution;
    }

    @Column(name="REAGENT_VARIABLES")
    public String getReagentVariables() {
        return reagentVariables;
    }

    public void setReagentVariables(String reagentVariables) {
        this.reagentVariables = reagentVariables;
    }

    @Column(name="QUALIFICATION")
    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    @Column(name="REMARK")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Column(name="LIB_LOCATION")
    public String getLibLocation() {
        return libLocation;
    }

    public void setLibLocation(String libLocation) {
        this.libLocation = libLocation;
    }
}