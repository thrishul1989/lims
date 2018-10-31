package com.todaysoft.lims.order.response;

import java.util.Date;

public abstract class BaseOrder
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

    private String projectManager;//项目管理人

    private String prjManagerName;

    public String getPrjManagerName() {
        return prjManagerName;
    }

    public void setPrjManagerName(String prjManagerName) {
        this.prjManagerName = prjManagerName;
    }

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
}
