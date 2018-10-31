package com.todaysoft.lims.order.request;

import java.util.Date;

import com.todaysoft.lims.base.RecordFilter;

public class OrderSearchRequest
{
    private Integer pageNo;
    
    private Integer pageSize;
    
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
    
    private String plannedFinishStartDate;
    
    private String plannedFinishEndDate;
    
    private Integer status;
    
    private Integer paymentStatus;
    
    private String sampleCode;
    
    private String postponedNode;
    
    private RecordFilter filter;
    
    private Integer priorityStatus;

    private String projectManager;//项目管理人

    private String prjManagerName;//项目管理人名称

    public String getProjectManager() {
        return projectManager;
    }

    public void setProjectManager(String projectManager) {
        this.projectManager = projectManager;
    }

    public String getPrjManagerName() {
        return prjManagerName;
    }

    public void setPrjManagerName(String prjManagerName) {
        this.prjManagerName = prjManagerName;
    }

    public Integer getPageNo()
    {
        return pageNo;
    }
    
    public void setPageNo(Integer pageNo)
    {
        this.pageNo = pageNo;
    }
    
    public Integer getPageSize()
    {
        return pageSize;
    }
    
    public void setPageSize(Integer pageSize)
    {
        this.pageSize = pageSize;
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
    
    public String getPlannedFinishStartDate()
    {
        return plannedFinishStartDate;
    }
    
    public void setPlannedFinishStartDate(String plannedFinishStartDate)
    {
        this.plannedFinishStartDate = plannedFinishStartDate;
    }
    
    public String getPlannedFinishEndDate()
    {
        return plannedFinishEndDate;
    }
    
    public void setPlannedFinishEndDate(String plannedFinishEndDate)
    {
        this.plannedFinishEndDate = plannedFinishEndDate;
    }
    
    public Integer getStatus()
    {
        return status;
    }
    
    public void setStatus(Integer status)
    {
        this.status = status;
    }
    
    public Integer getPaymentStatus()
    {
        return paymentStatus;
    }
    
    public void setPaymentStatus(Integer paymentStatus)
    {
        this.paymentStatus = paymentStatus;
    }
    
    public String getSampleCode()
    {
        return sampleCode;
    }
    
    public void setSampleCode(String sampleCode)
    {
        this.sampleCode = sampleCode;
    }
    
    public String getPostponedNode()
    {
        return postponedNode;
    }
    
    public void setPostponedNode(String postponedNode)
    {
        this.postponedNode = postponedNode;
    }
    
    public RecordFilter getFilter()
    {
        return filter;
    }
    
    public void setFilter(RecordFilter filter)
    {
        this.filter = filter;
    }

    public Integer getPriorityStatus()
    {
        return priorityStatus;
    }

    public void setPriorityStatus(Integer priorityStatus)
    {
        this.priorityStatus = priorityStatus;
    }
    
}
