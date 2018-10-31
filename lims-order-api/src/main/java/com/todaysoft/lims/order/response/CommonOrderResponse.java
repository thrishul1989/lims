package com.todaysoft.lims.order.response;

import java.math.BigDecimal;
import java.util.Date;

public class CommonOrderResponse
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
    
    private String creatorName; //业务员

    private String prjManagerName;//项目管理人
    
    private String payType;
    
    private BigDecimal needPay;
    
    private Integer sheduleStatus;
    
    private String remark;
    
    private Integer submitSource;
    
    private String orderType;
    
    private Integer testingStatus;
    
    private String delayId;
    
    private String planPayTime; //计划缴费时间
    
    private Integer delayCheckStatus;//延迟审核状态
    
    private BigDecimal incomingAmount;
    
    private String reduceId;
    
    private BigDecimal reduceApplyAmount;
    
    private BigDecimal reduceCheckAmount;
    
    private BigDecimal replenishAmount;
    
    private String contractPartB;
    
    private String contractsettlementMode;

    public String getPrjManagerName() {
        return prjManagerName;
    }

    public void setPrjManagerName(String prjManagerName) {
        this.prjManagerName = prjManagerName;
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
    
    public String getPayType()
    {
        return payType;
    }
    
    public void setPayType(String payType)
    {
        this.payType = payType;
    }
    
    public Integer getSheduleStatus()
    {
        return sheduleStatus;
    }
    
    public void setSheduleStatus(Integer sheduleStatus)
    {
        this.sheduleStatus = sheduleStatus;
    }
    
    public String getRemark()
    {
        return remark;
    }
    
    public void setRemark(String remark)
    {
        this.remark = remark;
    }
    
    public Integer getSubmitSource()
    {
        return submitSource;
    }
    
    public void setSubmitSource(Integer submitSource)
    {
        this.submitSource = submitSource;
    }
    
    public String getCreatorName()
    {
        return creatorName;
    }
    
    public void setCreatorName(String creatorName)
    {
        this.creatorName = creatorName;
    }
    
    public Integer getReceivedSampleStatus()
    {
        return receivedSampleStatus;
    }
    
    public void setReceivedSampleStatus(Integer receivedSampleStatus)
    {
        this.receivedSampleStatus = receivedSampleStatus;
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
    
    public String getDelayId()
    {
        return delayId;
    }
    
    public void setDelayId(String delayId)
    {
        this.delayId = delayId;
    }
    
    public String getOrderType()
    {
        return orderType;
    }
    
    public void setOrderType(String orderType)
    {
        this.orderType = orderType;
    }
    
    public Integer getTestingStatus()
    {
        return testingStatus;
    }
    
    public void setTestingStatus(Integer testingStatus)
    {
        this.testingStatus = testingStatus;
    }
    
    public String getPlanPayTime()
    {
        return planPayTime;
    }
    
    public void setPlanPayTime(String planPayTime)
    {
        this.planPayTime = planPayTime;
    }
    
    public Integer getDelayCheckStatus()
    {
        return delayCheckStatus;
    }
    
    public void setDelayCheckStatus(Integer delayCheckStatus)
    {
        this.delayCheckStatus = delayCheckStatus;
    }
    
    public String getReduceId()
    {
        return reduceId;
    }
    
    public void setReduceId(String reduceId)
    {
        this.reduceId = reduceId;
    }
    
    public BigDecimal getNeedPay()
    {
        return needPay;
    }
    
    public void setNeedPay(BigDecimal needPay)
    {
        this.needPay = needPay;
    }
    
    public BigDecimal getIncomingAmount()
    {
        return incomingAmount;
    }
    
    public void setIncomingAmount(BigDecimal incomingAmount)
    {
        this.incomingAmount = incomingAmount;
    }
    
    public BigDecimal getReduceApplyAmount()
    {
        return reduceApplyAmount;
    }
    
    public void setReduceApplyAmount(BigDecimal reduceApplyAmount)
    {
        this.reduceApplyAmount = reduceApplyAmount;
    }
    
    public BigDecimal getReduceCheckAmount()
    {
        return reduceCheckAmount;
    }
    
    public void setReduceCheckAmount(BigDecimal reduceCheckAmount)
    {
        this.reduceCheckAmount = reduceCheckAmount;
    }
    
    public BigDecimal getReplenishAmount()
    {
        if (needPay != null && incomingAmount != null && reduceCheckAmount != null)
        {
            return needPay.subtract(incomingAmount).subtract(reduceCheckAmount);
        }
        else
        {
            return replenishAmount;
        }
        
    }
    
    public void setReplenishAmount(BigDecimal replenishAmount)
    {
        this.replenishAmount = replenishAmount;
    }
    
    public String getContractPartB()
    {
        return contractPartB;
    }
    
    public void setContractPartB(String contractPartB)
    {
        this.contractPartB = contractPartB;
    }
    
    public String getContractsettlementMode()
    {
        return contractsettlementMode;
    }
    
    public void setContractsettlementMode(String contractsettlementMode)
    {
        this.contractsettlementMode = contractsettlementMode;
    }
    
}
