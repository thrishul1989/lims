package com.todaysoft.lims.order.mybatis.model;

import java.math.BigDecimal;
import java.util.Date;

public abstract class BaseOrderModel
{
    private String id;
    
    private String orderCode;
    
    private String orderMarketingCenterId;
    
    private String orderMarketingCenterName;
    
    private Integer orderIfUrgent;
    
    private String contractId;
    
    private String contractCode;
    
    private String contractName;
    
    private String customerId;
    
    private String customerName;
    
    private String customerCompanyId;
    
    private String customerCompanyName;
    
    private String salesmanName;
    
    private Date orderSubmitTime;
    
    private Integer orderStatus;
    
    private String examineeName;
    
    private Integer schedulePaymentStatus;
    
    private Integer paymentStatus;
    
    private Integer receivedSampleStatus;
    
    private Integer sheduleStatus;
    
    private Integer submitSource;
    
    private String payType;
    
    private BigDecimal incomingAmount;

    private String projectManager;//项目管理人

    public String getProjectManager() {
        return projectManager;
    }

    public void setProjectManager(String projectManager) {
        this.projectManager = projectManager;
    }

    public Integer getOrderIfUrgent()
    {
        return orderIfUrgent;
    }
    
    public void setOrderIfUrgent(Integer orderIfUrgent)
    {
        this.orderIfUrgent = orderIfUrgent;
    }
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getOrderCode()
    {
        return orderCode;
    }
    
    public void setOrderCode(String orderCode)
    {
        this.orderCode = orderCode;
    }
    
    public String getOrderMarketingCenterId()
    {
        return orderMarketingCenterId;
    }
    
    public void setOrderMarketingCenterId(String orderMarketingCenterId)
    {
        this.orderMarketingCenterId = orderMarketingCenterId;
    }
    
    public String getOrderMarketingCenterName()
    {
        return orderMarketingCenterName;
    }
    
    public void setOrderMarketingCenterName(String orderMarketingCenterName)
    {
        this.orderMarketingCenterName = orderMarketingCenterName;
    }
    
    public String getContractId()
    {
        return contractId;
    }
    
    public void setContractId(String contractId)
    {
        this.contractId = contractId;
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
    
    public String getCustomerId()
    {
        return customerId;
    }
    
    public void setCustomerId(String customerId)
    {
        this.customerId = customerId;
    }
    
    public String getCustomerName()
    {
        return customerName;
    }
    
    public void setCustomerName(String customerName)
    {
        this.customerName = customerName;
    }
    
    public String getCustomerCompanyId()
    {
        return customerCompanyId;
    }
    
    public void setCustomerCompanyId(String customerCompanyId)
    {
        this.customerCompanyId = customerCompanyId;
    }
    
    public String getCustomerCompanyName()
    {
        return customerCompanyName;
    }
    
    public void setCustomerCompanyName(String customerCompanyName)
    {
        this.customerCompanyName = customerCompanyName;
    }
    
    public String getSalesmanName()
    {
        return salesmanName;
    }
    
    public void setSalesmanName(String salesmanName)
    {
        this.salesmanName = salesmanName;
    }
    
    public Date getOrderSubmitTime()
    {
        return orderSubmitTime;
    }
    
    public void setOrderSubmitTime(Date orderSubmitTime)
    {
        this.orderSubmitTime = orderSubmitTime;
    }
    
    public Integer getOrderStatus()
    {
        return orderStatus;
    }
    
    public void setOrderStatus(Integer orderStatus)
    {
        this.orderStatus = orderStatus;
    }
    
    public String getExamineeName()
    {
        return examineeName;
    }
    
    public void setExamineeName(String examineeName)
    {
        this.examineeName = examineeName;
    }
    
    public Integer getSchedulePaymentStatus()
    {
        return schedulePaymentStatus;
    }
    
    public void setSchedulePaymentStatus(Integer schedulePaymentStatus)
    {
        this.schedulePaymentStatus = schedulePaymentStatus;
    }
    
    public Integer getPaymentStatus()
    {
        return paymentStatus;
    }
    
    public void setPaymentStatus(Integer paymentStatus)
    {
        this.paymentStatus = paymentStatus;
    }
    
    public Integer getReceivedSampleStatus()
    {
        return receivedSampleStatus;
    }
    
    public void setReceivedSampleStatus(Integer receivedSampleStatus)
    {
        this.receivedSampleStatus = receivedSampleStatus;
    }
    
    public Integer getSheduleStatus()
    {
        return sheduleStatus;
    }
    
    public void setSheduleStatus(Integer sheduleStatus)
    {
        this.sheduleStatus = sheduleStatus;
    }
    
    public Integer getSubmitSource()
    {
        return submitSource;
    }
    
    public void setSubmitSource(Integer submitSource)
    {
        this.submitSource = submitSource;
    }
    
    public String getPayType()
    {
        return payType;
    }
    
    public void setPayType(String payType)
    {
        this.payType = payType;
    }
    
    public BigDecimal getIncomingAmount()
    {
        return incomingAmount;
    }
    
    public void setIncomingAmount(BigDecimal incomingAmount)
    {
        this.incomingAmount = incomingAmount;
    }
    
}
