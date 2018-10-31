package com.todaysoft.lims.system.modules.bpm.extrasample.model;

import java.util.Date;

public class ExtraSampleTask
{
    private String id;

    private String marketingCenter;

    private String orderCode;

    private String sampleCode;

    private String purpose;

    private Date orderCreateTime;

    private Integer status;

    private String prjManagerName;

    public String getPrjManagerName() {
        return prjManagerName;
    }

    public void setPrjManagerName(String prjManagerName) {
        this.prjManagerName = prjManagerName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMarketingCenter() {
        return marketingCenter;
    }

    public void setMarketingCenter(String marketingCenter) {
        this.marketingCenter = marketingCenter;
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

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public Date getOrderCreateTime() {
        return orderCreateTime;
    }

    public void setOrderCreateTime(Date orderCreateTime) {
        this.orderCreateTime = orderCreateTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
