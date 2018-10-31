package com.todaysoft.lims.report.entity;

import com.google.common.collect.Lists;
import com.todaysoft.lims.persist.UuidKeyEntity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "REPORT_ORDER_SAMPLE_INFO")
public class ReportOrderSampleInfo extends UuidKeyEntity
{
    private String reportTaskId;

    private String orderCode;

    private String sampleCode;

    private String productName;

    private String contractName;

    private String markerCenter;

    private String inspectName;

    private String customerName;

    private String customerCompany;

    private String creatorName;

    private String lsLocation;// '长期存储位置编号',

    private String tsLocation;//'临时存储位置编号',

    private List<ReportOrderDna> dnaList = Lists.newArrayList();

    @Column(name="REPORT_TASK_ID")
    public String getReportTaskId() {
        return reportTaskId;
    }

    public void setReportTaskId(String reportTaskId) {
        this.reportTaskId = reportTaskId;
    }

    @Column(name="ORDER_CODE")
    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    @Column(name="SAMPLE_CODE")
    public String getSampleCode() {
        return sampleCode;
    }

    public void setSampleCode(String sampleCode) {
        this.sampleCode = sampleCode;
    }

    @Column(name="PRODUCT_NAME")
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    @Column(name="CONTRACT_NAME")
    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    @Column(name="MARKET_CENTER")
    public String getMarkerCenter() {
        return markerCenter;
    }

    public void setMarkerCenter(String markerCenter) {
        this.markerCenter = markerCenter;
    }

    @Column(name="INSPECT_NAME")
    public String getInspectName() {
        return inspectName;
    }

    public void setInspectName(String inspectName) {
        this.inspectName = inspectName;
    }

    @Column(name="CUSTOMER_NAME")
    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    @Column(name="CUSTOMER_COMPANY")
    public String getCustomerCompany() {
        return customerCompany;
    }

    public void setCustomerCompany(String customerCompany) {
        this.customerCompany = customerCompany;
    }

    @Column(name="CREATOR_NAME")
    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    @Column(name="LS_LOCATION")
    public String getLsLocation() {
        return lsLocation;
    }

    public void setLsLocation(String lsLocation) {
        this.lsLocation = lsLocation;
    }

    @Column(name="TS_LOCATION")
    public String getTsLocation() {
        return tsLocation;
    }

    public void setTsLocation(String tsLocation) {
        this.tsLocation = tsLocation;
    }

    @OneToMany(mappedBy = "reportOrderSampleInfo", fetch = FetchType.LAZY)
    @OrderBy("dnaCompleteDate ASC")
    public List<ReportOrderDna> getDnaList() {
        return dnaList;
    }

    public void setDnaList(List<ReportOrderDna> dnaList) {
        this.dnaList = dnaList;
    }
}