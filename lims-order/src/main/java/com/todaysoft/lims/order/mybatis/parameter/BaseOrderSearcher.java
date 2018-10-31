package com.todaysoft.lims.order.mybatis.parameter;

import java.util.Date;

import com.todaysoft.lims.base.RecordFilterSearcher;

public class BaseOrderSearcher extends RecordFilterSearcher
{
    private Integer offset;
    
    private Integer limit;
    
    private String orderCode;
    
    private String contractCode;
    
    private String contractName;
    
    private String marketingCenterId;
    
    private String examineeName;
    
    private String customerName;
    
    private String customerCompanyName;
    
    private String salesmanName;
    
    private Date submitStartDate;
    
    private Date submitEndDate;
    
    private Integer status;
    
    private Integer paymentStatus; //支付状态
    
    private String recipientPhone;//收件人电话
    
    private Integer schedulePaymentStatus; //合同订单检测状态
    
    private Integer settlementType; //1 一单一结 2 集中
    
    private Integer testingStatus;
    
    private Integer submitSource; //1--app  2--业务后台
    
    private Integer sheduleStatus;
    
    private String ownerId;
    
    private String contactPhone;
    
    public Integer getPaymentStatus()
    {
        return paymentStatus;
    }
    
    public void setPaymentStatus(Integer paymentStatus)
    {
        this.paymentStatus = paymentStatus;
    }
    
    public Integer getOffset()
    {
        return offset;
    }
    
    public void setOffset(Integer offset)
    {
        this.offset = offset;
    }
    
    public Integer getLimit()
    {
        return limit;
    }
    
    public void setLimit(Integer limit)
    {
        this.limit = limit;
    }
    
    public String getOrderCode()
    {
        return orderCode;
    }
    
    public void setOrderCode(String orderCode)
    {
        this.orderCode = orderCode;
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
    
    public String getMarketingCenterId()
    {
        return marketingCenterId;
    }
    
    public void setMarketingCenterId(String marketingCenterId)
    {
        this.marketingCenterId = marketingCenterId;
    }
    
    public String getExamineeName()
    {
        return examineeName;
    }
    
    public void setExamineeName(String examineeName)
    {
        this.examineeName = examineeName;
    }
    
    public String getCustomerName()
    {
        return customerName;
    }
    
    public void setCustomerName(String customerName)
    {
        this.customerName = customerName;
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
    
    public Date getSubmitStartDate()
    {
        return submitStartDate;
    }
    
    public void setSubmitStartDate(Date submitStartDate)
    {
        this.submitStartDate = submitStartDate;
    }
    
    public Date getSubmitEndDate()
    {
        return submitEndDate;
    }
    
    public void setSubmitEndDate(Date submitEndDate)
    {
        this.submitEndDate = submitEndDate;
    }
    
    public Integer getStatus()
    {
        return status;
    }
    
    public void setStatus(Integer status)
    {
        this.status = status;
    }
    
    public String getRecipientPhone()
    {
        return recipientPhone;
    }
    
    public void setRecipientPhone(String recipientPhone)
    {
        this.recipientPhone = recipientPhone;
    }
    
    public Integer getSchedulePaymentStatus()
    {
        return schedulePaymentStatus;
    }
    
    public void setSchedulePaymentStatus(Integer schedulePaymentStatus)
    {
        this.schedulePaymentStatus = schedulePaymentStatus;
    }
    
    public Integer getSettlementType()
    {
        return settlementType;
    }
    
    public void setSettlementType(Integer settlementType)
    {
        this.settlementType = settlementType;
    }
    
    public Integer getSubmitSource()
    {
        return submitSource;
    }
    
    public void setSubmitSource(Integer submitSource)
    {
        this.submitSource = submitSource;
    }
    
    public Integer getSheduleStatus()
    {
        return sheduleStatus;
    }
    
    public void setSheduleStatus(Integer sheduleStatus)
    {
        this.sheduleStatus = sheduleStatus;
    }
    
    public Integer getTestingStatus()
    {
        return testingStatus;
    }
    
    public void setTestingStatus(Integer testingStatus)
    {
        this.testingStatus = testingStatus;
    }
    
    public String getOwnerId()
    {
        return ownerId;
    }
    
    public void setOwnerId(String ownerId)
    {
        this.ownerId = ownerId;
    }
    
    public String getContactPhone()
    {
        return contactPhone;
    }
    
    public void setContactPhone(String contactPhone)
    {
        this.contactPhone = contactPhone;
    }
}
