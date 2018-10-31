package com.todaysoft.lims.testing.report.model;


import com.google.common.collect.Lists;

import java.util.Date;
import java.util.List;

public class ReportTaskDetail
{
    private String orderCode;

    private String testUnit;

    private String customer;

    private String marketingCenter;

    private Date shouldReportDate;

    private int status;

    private Integer totalNum;

    private Integer completeNum;

    private List<ReportTaskSchedule> list = Lists.newArrayList();

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getTestUnit() {
        return testUnit;
    }

    public void setTestUnit(String testUnit) {
        this.testUnit = testUnit;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getMarketingCenter() {
        return marketingCenter;
    }

    public void setMarketingCenter(String marketingCenter) {
        this.marketingCenter = marketingCenter;
    }

    public Date getShouldReportDate() {
        return shouldReportDate;
    }

    public void setShouldReportDate(Date shouldReportDate) {
        this.shouldReportDate = shouldReportDate;
    }

    public List<ReportTaskSchedule> getList() {
        return list;
    }

    public void setList(List<ReportTaskSchedule> list) {
        this.list = list;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Integer getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }

    public Integer getCompleteNum() {
        return completeNum;
    }

    public void setCompleteNum(Integer completeNum) {
        this.completeNum = completeNum;
    }
}
