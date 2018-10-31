package com.todaysoft.lims.testing.base.model;


import java.util.Date;

public class SampleInfoProcessModel {

    private String sampleId;

    private String orderId;

    private Integer orderType;//订单类型

    private String sampleTypeName;//样本类型

    private String sampleTypeId;//样本id

    private String sampleCode;//样本编号

    private String inspectName;//受检人姓名

    private Date simplingDate;//采样时间

    private String  lsLocation;// '长期存储位置编号',

    private double  lsSize;//'长期存储量',

    private Integer lsStatus;//'长期存储状态：1-入库 2-出库',

    private String  tsLocation;//'临时存储位置编号',

    private double  tsSize;//'临时存储量',

    private Integer tsStatus;// '临时存储状态：1-入库 2-出库',

    private Integer purpose;// '1-检测 2-验证 3-对照',

    private String products;//产品

    private String dnaCode;

    private Double inputSize;

    private Double extractSize;

    public String getSampleId() {
        return sampleId;
    }

    public void setSampleId(String sampleId) {
        this.sampleId = sampleId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public String getSampleTypeName() {
        return sampleTypeName;
    }

    public void setSampleTypeName(String sampleTypeName) {
        this.sampleTypeName = sampleTypeName;
    }

    public String getSampleCode() {
        return sampleCode;
    }

    public void setSampleCode(String sampleCode) {
        this.sampleCode = sampleCode;
    }

    public String getInspectName() {
        return inspectName;
    }

    public void setInspectName(String inspectName) {
        this.inspectName = inspectName;
    }

    public Date getSimplingDate() {
        return simplingDate;
    }

    public void setSimplingDate(Date simplingDate) {
        this.simplingDate = simplingDate;
    }

    public String getLsLocation() {
        return lsLocation;
    }

    public void setLsLocation(String lsLocation) {
        this.lsLocation = lsLocation;
    }

    public double getLsSize() {
        return lsSize;
    }

    public void setLsSize(double lsSize) {
        this.lsSize = lsSize;
    }

    public Integer getLsStatus() {
        return lsStatus;
    }

    public void setLsStatus(Integer lsStatus) {
        this.lsStatus = lsStatus;
    }

    public String getTsLocation() {
        return tsLocation;
    }

    public void setTsLocation(String tsLocation) {
        this.tsLocation = tsLocation;
    }

    public double getTsSize() {
        return tsSize;
    }

    public void setTsSize(double tsSize) {
        this.tsSize = tsSize;
    }

    public Integer getTsStatus() {
        return tsStatus;
    }

    public void setTsStatus(Integer tsStatus) {
        this.tsStatus = tsStatus;
    }

    public Integer getPurpose() {
        return purpose;
    }

    public void setPurpose(Integer purpose) {
        this.purpose = purpose;
    }

    public String getProducts() {
        return products;
    }

    public void setProducts(String products) {
        this.products = products;
    }

    public String getSampleTypeId() {
        return sampleTypeId;
    }

    public void setSampleTypeId(String sampleTypeId) {
        this.sampleTypeId = sampleTypeId;
    }

    public String getDnaCode() {
        return dnaCode;
    }

    public void setDnaCode(String dnaCode) {
        this.dnaCode = dnaCode;
    }

    public Double getInputSize() {
        return inputSize;
    }

    public void setInputSize(Double inputSize) {
        this.inputSize = inputSize;
    }

    public Double getExtractSize() {
        return extractSize;
    }

    public void setExtractSize(Double extractSize) {
        this.extractSize = extractSize;
    }
}
