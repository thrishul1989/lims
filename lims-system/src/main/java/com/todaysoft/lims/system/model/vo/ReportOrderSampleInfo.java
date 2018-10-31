package com.todaysoft.lims.system.model.vo;

public class ReportOrderSampleInfo
{
    private String id;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReportTaskId() {
        return reportTaskId;
    }

    public void setReportTaskId(String reportTaskId) {
        this.reportTaskId = reportTaskId;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getSampleCode() {
        return sampleCode;
    }

    public void setSampleCode(String sampleCode) {
        this.sampleCode = sampleCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public String getMarkerCenter() {
        return markerCenter;
    }

    public void setMarkerCenter(String markerCenter) {
        this.markerCenter = markerCenter;
    }

    public String getInspectName() {
        return inspectName;
    }

    public void setInspectName(String inspectName) {
        this.inspectName = inspectName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerCompany() {
        return customerCompany;
    }

    public void setCustomerCompany(String customerCompany) {
        this.customerCompany = customerCompany;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public String getLsLocation() {
        return lsLocation;
    }

    public void setLsLocation(String lsLocation) {
        this.lsLocation = lsLocation;
    }

    public String getTsLocation() {
        return tsLocation;
    }

    public void setTsLocation(String tsLocation) {
        this.tsLocation = tsLocation;
    }
}