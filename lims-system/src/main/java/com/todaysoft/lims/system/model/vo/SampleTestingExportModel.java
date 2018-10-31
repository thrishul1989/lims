package com.todaysoft.lims.system.model.vo;


import com.google.common.collect.Lists;

import java.util.List;

public class SampleTestingExportModel
{
    private String orderId;

    private String orderCode;

    private String productName;

    private String contractName;

    private String marketCenter;

    private String inspectMan;

    private String customerName;

    private String customerCompany;

    private String salesManName;

    private List<OrderSampleExportModel> samples = Lists.newArrayList();

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
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

    public String getMarketCenter() {
        return marketCenter;
    }

    public void setMarketCenter(String marketCenter) {
        this.marketCenter = marketCenter;
    }

    public String getInspectMan() {
        return inspectMan;
    }

    public void setInspectMan(String inspectMan) {
        this.inspectMan = inspectMan;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public List<OrderSampleExportModel> getSamples() {
        return samples;
    }

    public void setSamples(List<OrderSampleExportModel> samples) {
        this.samples = samples;
    }

    public String getSalesManName() {
        return salesManName;
    }

    public void setSalesManName(String salesManName) {
        this.salesManName = salesManName;
    }

    public String getCustomerCompany() {
        return customerCompany;
    }

    public void setCustomerCompany(String customerCompany) {
        this.customerCompany = customerCompany;
    }
}
