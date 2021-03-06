package com.todaysoft.lims.order.mybatis.mapper.entity;

/**
 * @Author dinghairong
 * @Date 2018/10/22 10:32
 * @Description
 */
public class OrderProductModel {

    private String orderFormKey;

    private String productKey;

    private String productName;

    private String productCode;

    private String productStatus;

    private String reportPath;

    private String generateReportTime;

    private String postTime;

    private String leader;

    private Integer productCycle;

    private String planReportDate;

    private String errorStatus;

    public String getOrderFormKey() {
        return orderFormKey;
    }

    public void setOrderFormKey(String orderFormKey) {
        this.orderFormKey = orderFormKey;
    }

    public String getProductKey() {
        return productKey;
    }

    public void setProductKey(String productKey) {
        this.productKey = productKey;
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

    public String getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(String productStatus) {
        this.productStatus = productStatus;
    }

    public String getReportPath() {
        return reportPath;
    }

    public void setReportPath(String reportPath) {
        this.reportPath = reportPath;
    }

    public String getGenerateReportTime() {
        return generateReportTime;
    }

    public void setGenerateReportTime(String generateReportTime) {
        this.generateReportTime = generateReportTime;
    }

    public String getPostTime() {
        return postTime;
    }

    public void setPostTime(String postTime) {
        this.postTime = postTime;
    }

    public String getLeader() {
        return leader;
    }

    public void setLeader(String leader) {
        this.leader = leader;
    }

    public Integer getProductCycle() {
        return productCycle;
    }

    public void setProductCycle(Integer productCycle) {
        this.productCycle = productCycle;
    }

    public String getPlanReportDate() {
        return planReportDate;
    }

    public void setPlanReportDate(String planReportDate) {
        this.planReportDate = planReportDate;
    }

    public String getErrorStatus() {
        return errorStatus;
    }

    public void setErrorStatus(String errorStatus) {
        this.errorStatus = errorStatus;
    }
}
