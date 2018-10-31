package com.todaysoft.lims.testing.extrasample.model;


import com.google.common.collect.Lists;

import java.util.Date;
import java.util.List;

public class ExtraSampleTaskDetails
{
    private String id;

    private String orderCode;

    private String marketingCenter;

    private Date orderCreateTime;

    private String primarySampleType;

    private String primarySampleCode;

    private String primarySampleName;

    private String extraSampleType;

    private String extraSampleCode;

    private String extraSampleName;

    private Integer purpose;

    private String operator;

    private Date operatorTime;

    List<ExtraSampleSchedule> testSchedules = Lists.newArrayList();

    List<ExtraSampleSchedule> extraSampleTestSchedules = Lists.newArrayList();

    List<ExtraSampleVerifyDetail> validSchedules = Lists.newArrayList();

    List<ExtraSampleVerifyDetail> extraSampleValidSchedules = Lists.newArrayList();

    List<ExtraSampleShowProducts> extraSampleShowProducts = Lists.newArrayList();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getMarketingCenter() {
        return marketingCenter;
    }

    public void setMarketingCenter(String marketingCenter) {
        this.marketingCenter = marketingCenter;
    }

    public Date getOrderCreateTime() {
        return orderCreateTime;
    }

    public void setOrderCreateTime(Date orderCreateTime) {
        this.orderCreateTime = orderCreateTime;
    }

    public String getPrimarySampleType() {
        return primarySampleType;
    }

    public void setPrimarySampleType(String primarySampleType) {
        this.primarySampleType = primarySampleType;
    }

    public String getPrimarySampleCode() {
        return primarySampleCode;
    }

    public void setPrimarySampleCode(String primarySampleCode) {
        this.primarySampleCode = primarySampleCode;
    }

    public String getPrimarySampleName() {
        return primarySampleName;
    }

    public void setPrimarySampleName(String primarySampleName) {
        this.primarySampleName = primarySampleName;
    }

    public String getExtraSampleType() {
        return extraSampleType;
    }

    public void setExtraSampleType(String extraSampleType) {
        this.extraSampleType = extraSampleType;
    }

    public String getExtraSampleCode() {
        return extraSampleCode;
    }

    public void setExtraSampleCode(String extraSampleCode) {
        this.extraSampleCode = extraSampleCode;
    }

    public String getExtraSampleName() {
        return extraSampleName;
    }

    public void setExtraSampleName(String extraSampleName) {
        this.extraSampleName = extraSampleName;
    }

    public Integer getPurpose() {
        return purpose;
    }

    public void setPurpose(Integer purpose) {
        this.purpose = purpose;
    }

    public List<ExtraSampleSchedule> getTestSchedules() {
        return testSchedules;
    }

    public void setTestSchedules(List<ExtraSampleSchedule> testSchedules) {
        this.testSchedules = testSchedules;
    }

    public List<ExtraSampleVerifyDetail> getValidSchedules() {
        return validSchedules;
    }

    public void setValidSchedules(List<ExtraSampleVerifyDetail> validSchedules) {
        this.validSchedules = validSchedules;
    }

    public List<ExtraSampleSchedule> getExtraSampleTestSchedules() {
        return extraSampleTestSchedules;
    }

    public void setExtraSampleTestSchedules(List<ExtraSampleSchedule> extraSampleTestSchedules) {
        this.extraSampleTestSchedules = extraSampleTestSchedules;
    }

    public List<ExtraSampleVerifyDetail> getExtraSampleValidSchedules() {
        return extraSampleValidSchedules;
    }

    public void setExtraSampleValidSchedules(List<ExtraSampleVerifyDetail> extraSampleValidSchedules) {
        this.extraSampleValidSchedules = extraSampleValidSchedules;
    }

    public List<ExtraSampleShowProducts> getExtraSampleShowProducts() {
        return extraSampleShowProducts;
    }

    public void setExtraSampleShowProducts(List<ExtraSampleShowProducts> extraSampleShowProducts) {
        this.extraSampleShowProducts = extraSampleShowProducts;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Date getOperatorTime() {
        return operatorTime;
    }

    public void setOperatorTime(Date operatorTime) {
        this.operatorTime = operatorTime;
    }
}
