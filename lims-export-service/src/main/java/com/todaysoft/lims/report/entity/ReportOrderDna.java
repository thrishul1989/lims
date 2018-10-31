package com.todaysoft.lims.report.entity;

import com.google.common.collect.Lists;
import com.todaysoft.lims.persist.UuidKeyEntity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "REPORT_ORDER_DNA")
public class ReportOrderDna extends UuidKeyEntity
{
    private String reportTaskId;

    private String dnaSheetCode;

    private String dnaAssignName;

    private Date dnaAssignDate;

    private String dnaTestorName;

    private Date dnaCompleteDate;

    private String dnaSampleId;

    private String dnaSampleCode;

    private String attribution;

    private String reagentVariables;

    private String qualification;

    private String remark;

    private String dnaLocation;

    private ReportOrderSampleInfo reportOrderSampleInfo;

    private List<ReportOrderLib> libList = Lists.newArrayList();

    @Column(name="REPORT_TASK_ID")
    public String getReportTaskId() {
        return reportTaskId;
    }

    public void setReportTaskId(String reportTaskId) {
        this.reportTaskId = reportTaskId;
    }

    @Column(name="DNA_SHEET_CODE")
    public String getDnaSheetCode() {
        return dnaSheetCode;
    }

    public void setDnaSheetCode(String dnaSheetCode) {
        this.dnaSheetCode = dnaSheetCode;
    }

    @Column(name="DNA_ASSIGN_NAME")
    public String getDnaAssignName() {
        return dnaAssignName;
    }

    public void setDnaAssignName(String dnaAssignName) {
        this.dnaAssignName = dnaAssignName;
    }

    @Column(name="DNA_ASSIGN_DATE")
    public Date getDnaAssignDate() {
        return dnaAssignDate;
    }

    public void setDnaAssignDate(Date dnaAssignDate) {
        this.dnaAssignDate = dnaAssignDate;
    }

    @Column(name="DNA_TESTOR_NAME")
    public String getDnaTestorName() {
        return dnaTestorName;
    }

    public void setDnaTestorName(String dnaTestorName) {
        this.dnaTestorName = dnaTestorName;
    }

    @Column(name="DNA_COMPLETE_DATE")
    public Date getDnaCompleteDate() {
        return dnaCompleteDate;
    }

    public void setDnaCompleteDate(Date dnaCompleteDate) {
        this.dnaCompleteDate = dnaCompleteDate;
    }

    @Column(name="DNA_SAMPLE_ID")
    public String getDnaSampleId() {
        return dnaSampleId;
    }

    public void setDnaSampleId(String dnaSampleId) {
        this.dnaSampleId = dnaSampleId;
    }

    @Column(name="DNA_SAMPLE_CODE")
    public String getDnaSampleCode() {
        return dnaSampleCode;
    }

    public void setDnaSampleCode(String dnaSampleCode) {
        this.dnaSampleCode = dnaSampleCode;
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

    @Column(name="DNA_LOCATION")
    public String getDnaLocation() {
        return dnaLocation;
    }

    public void setDnaLocation(String dnaLocation) {
        this.dnaLocation = dnaLocation;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDER_SAMPLE_ID")
    public ReportOrderSampleInfo getReportOrderSampleInfo() {
        return reportOrderSampleInfo;
    }

    public void setReportOrderSampleInfo(ReportOrderSampleInfo reportOrderSampleInfo) {
        this.reportOrderSampleInfo = reportOrderSampleInfo;
    }

    @OneToMany(mappedBy = "reportOrderDna", fetch = FetchType.LAZY)
    @OrderBy(" libCompleteDate ASC")
    public List<ReportOrderLib> getLibList() {
        return libList;
    }

    public void setLibList(List<ReportOrderLib> libList) {
        this.libList = libList;
    }
}