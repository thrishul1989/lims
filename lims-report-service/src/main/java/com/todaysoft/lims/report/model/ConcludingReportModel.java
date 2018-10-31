package com.todaysoft.lims.report.model;

import java.util.Date;

public class ConcludingReportModel
{
    private String id;
    
    private String orderId;
    
    private String productId;
    
    private String methodId;
    
    private String sampleId;
    
    private String sampleCode;
    
    private String sampleName;
    
    private String orderCode;
    
    private String examineeName;
    
    private String contractCode;
    
    private String contractName;
    
    private String customerName;
    
    private String customerCompany;
    
    private String createName;//订单创建人业务员
    
    private String deliveryMode;
    
    private String deliveryResult;
    
    private Date createTime;
    
    private Date reportDate;
    
    private String sendName;
    
    private Date sendTime;//0-未上传报告 1-已上传报告
    
    private Integer statu;
    
    private String complete;
    
    private Integer ifUrgent;
    
    private Date urgentUpdateTime;
    
    private String urgentName;
    
    private Integer ifReported;//是否上传过报告

    private String projectManager;

    public String getProjectManager() {
        return projectManager;
    }

    public void setProjectManager(String projectManager) {
        this.projectManager = projectManager;
    }

    public Integer getIfReported()
    {
        return ifReported;
    }
    
    public void setIfReported(Integer ifReported)
    {
        this.ifReported = ifReported;
    }
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getOrderId()
    {
        return orderId;
    }
    
    public void setOrderId(String orderId)
    {
        this.orderId = orderId;
    }
    
    public String getProductId()
    {
        return productId;
    }
    
    public void setProductId(String productId)
    {
        this.productId = productId;
    }
    
    public String getMethodId()
    {
        return methodId;
    }
    
    public void setMethodId(String methodId)
    {
        this.methodId = methodId;
    }
    
    public String getSampleId()
    {
        return sampleId;
    }
    
    public void setSampleId(String sampleId)
    {
        this.sampleId = sampleId;
    }
    
    public String getSampleCode()
    {
        return sampleCode;
    }
    
    public void setSampleCode(String sampleCode)
    {
        this.sampleCode = sampleCode;
    }
    
    public String getSampleName()
    {
        return sampleName;
    }
    
    public void setSampleName(String sampleName)
    {
        this.sampleName = sampleName;
    }
    
    public String getOrderCode()
    {
        return orderCode;
    }
    
    public void setOrderCode(String orderCode)
    {
        this.orderCode = orderCode;
    }
    
    public String getExamineeName()
    {
        return examineeName;
    }
    
    public void setExamineeName(String examineeName)
    {
        this.examineeName = examineeName;
    }
    
    public String getContractCode()
    {
        return contractCode;
    }
    
    public void setContractCode(String contractCode)
    {
        this.contractCode = contractCode;
    }
    
    public String getContractName()
    {
        return contractName;
    }
    
    public void setContractName(String contractName)
    {
        this.contractName = contractName;
    }
    
    public String getCustomerName()
    {
        return customerName;
    }
    
    public void setCustomerName(String customerName)
    {
        this.customerName = customerName;
    }
    
    public String getCustomerCompany()
    {
        return customerCompany;
    }
    
    public void setCustomerCompany(String customerCompany)
    {
        this.customerCompany = customerCompany;
    }
    
    public String getCreateName()
    {
        return createName;
    }
    
    public void setCreateName(String createName)
    {
        this.createName = createName;
    }
    
    public String getDeliveryMode()
    {
        return deliveryMode;
    }
    
    public void setDeliveryMode(String deliveryMode)
    {
        this.deliveryMode = deliveryMode;
    }
    
    public String getDeliveryResult()
    {
        return deliveryResult;
    }
    
    public void setDeliveryResult(String deliveryResult)
    {
        this.deliveryResult = deliveryResult;
    }
    
    public Date getCreateTime()
    {
        return createTime;
    }
    
    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }
    
    public Date getReportDate()
    {
        return reportDate;
    }
    
    public void setReportDate(Date reportDate)
    {
        this.reportDate = reportDate;
    }
    
    public String getSendName()
    {
        return sendName;
    }
    
    public void setSendName(String sendName)
    {
        this.sendName = sendName;
    }
    
    public Date getSendTime()
    {
        return sendTime;
    }
    
    public void setSendTime(Date sendTime)
    {
        this.sendTime = sendTime;
    }
    
    public Integer getStatu()
    {
        return statu;
    }
    
    public void setStatu(Integer statu)
    {
        this.statu = statu;
    }
    
    public String getComplete()
    {
        return complete;
    }
    
    public void setComplete(String complete)
    {
        this.complete = complete;
    }
    
    public Integer getIfUrgent()
    {
        return ifUrgent;
    }
    
    public void setIfUrgent(Integer ifUrgent)
    {
        this.ifUrgent = ifUrgent;
    }
    
    public Date getUrgentUpdateTime()
    {
        return urgentUpdateTime;
    }
    
    public void setUrgentUpdateTime(Date urgentUpdateTime)
    {
        this.urgentUpdateTime = urgentUpdateTime;
    }
    
    public String getUrgentName()
    {
        return urgentName;
    }
    
    public void setUrgentName(String urgentName)
    {
        this.urgentName = urgentName;
    }
    
}
