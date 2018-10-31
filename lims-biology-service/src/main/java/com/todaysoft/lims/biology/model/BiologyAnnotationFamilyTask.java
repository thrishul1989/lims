package com.todaysoft.lims.biology.model;

import java.util.Date;

public class BiologyAnnotationFamilyTask {
    private String id;

    private String orderCode;

    private String productCode;

    private String productName;

    private String methodId;

    private Date startTime;

    private Date endTime;

    private Integer status;//10-待提交 0-进行中 1-成功 2-出现错误 3-解析文件中 4-解析文件出错 5-解析文件成功）

    private Boolean resubmit;

    private Integer resubmitCount;

    private Integer ifUrgent;

    private String descrption;

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

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getMethodId() {
        return methodId;
    }

    public void setMethodId(String methodId) {
        this.methodId = methodId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Boolean getResubmit() {
        return resubmit;
    }

    public void setResubmit(Boolean resubmit) {
        this.resubmit = resubmit;
    }

    public Integer getResubmitCount() {
        return resubmitCount;
    }

    public void setResubmitCount(Integer resubmitCount) {
        this.resubmitCount = resubmitCount;
    }

    public Integer getIfUrgent() {
        return ifUrgent;
    }

    public void setIfUrgent(Integer ifUrgent) {
        this.ifUrgent = ifUrgent;
    }

    public String getDescrption() {
        return descrption;
    }

    public void setDescrption(String descrption) {
        this.descrption = descrption;
    }
}