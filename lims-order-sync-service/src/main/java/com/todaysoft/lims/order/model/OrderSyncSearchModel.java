package com.todaysoft.lims.order.model;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * @Author dinghairong
 * @Date 2018/10/18 10:37
 * @Description
 */
public class OrderSyncSearchModel {

    private String orderFormCode;

    private String beginTime;

    private String endTime;

    private String customers;//客户id，多个逗号隔开

    private List<String> customerIdList = Lists.newArrayList();

    private String contracts;//合同号，多个逗号隔开

    private List<String> contractIdList = Lists.newArrayList();

    public String getOrderFormCode() {
        return orderFormCode;
    }

    public void setOrderFormCode(String orderFormCode) {
        this.orderFormCode = orderFormCode;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getCustomers() {
        return customers;
    }

    public void setCustomers(String customers) {
        this.customers = customers;
    }

    public String getContracts() {
        return contracts;
    }

    public void setContracts(String contracts) {
        this.contracts = contracts;
    }

    public List<String> getCustomerIdList() {
        return customerIdList;
    }

    public void setCustomerIdList(List<String> customerIdList) {
        this.customerIdList = customerIdList;
    }

    public List<String> getContractIdList() {
        return contractIdList;
    }

    public void setContractIdList(List<String> contractIdList) {
        this.contractIdList = contractIdList;
    }
}
