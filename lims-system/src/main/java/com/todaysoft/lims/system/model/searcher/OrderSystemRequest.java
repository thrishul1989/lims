package com.todaysoft.lims.system.model.searcher;

public class OrderSystemRequest
{
    private String taskId;

    private String userId;

    private String userName;
    
    private Integer pageNo;

    private Integer pageSize;
    
    private String productCode;
    
    private String sampleCode;
    
    private String examineeName;
    
    private String examineePhone;
    
    private String contractName;
    
    private String contractCode;
    //------------存储过程参数--------------
    private String orderCode;
    private String orderType;
    private String testingStatus;
    private String paymentStatus;
    private String customerId;
    private String createName;
    private String startTime;
    private String endTime;
    private String recipientPhone;
    
    private String colNames;
    
    private String productFlag;//是否查询报告标记
    private String sampleFlag;//是否查询样本标记
    private String invoiceFlag;//是否查询发票标记
    
    public String getColNames()
    {
        return colNames;
    }
    public void setColNames(String colNames)
    {
        this.colNames = colNames;
    }
    public String getTaskId()
    {
        return taskId;
    }
    public void setTaskId(String taskId)
    {
        this.taskId = taskId;
    }
    public String getUserId()
    {
        return userId;
    }
    public void setUserId(String userId)
    {
        this.userId = userId;
    }
    public String getUserName()
    {
        return userName;
    }
    public void setUserName(String userName)
    {
        this.userName = userName;
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
    public String getProductCode()
    {
        return productCode;
    }
    public void setProductCode(String productCode)
    {
        this.productCode = productCode;
    }
    public String getSampleCode()
    {
        return sampleCode;
    }
    public void setSampleCode(String sampleCode)
    {
        this.sampleCode = sampleCode;
    }
    public String getExamineeName()
    {
        return examineeName;
    }
    public void setExamineeName(String examineeName)
    {
        this.examineeName = examineeName;
    }
    public String getExamineePhone()
    {
        return examineePhone;
    }
    public void setExamineePhone(String examineePhone)
    {
        this.examineePhone = examineePhone;
    }
    public String getContractName()
    {
        return contractName;
    }
    public void setContractName(String contractName)
    {
        this.contractName = contractName;
    }
    public String getContractCode()
    {
        return contractCode;
    }
    public void setContractCode(String contractCode)
    {
        this.contractCode = contractCode;
    }
    public String getOrderCode()
    {
        return orderCode;
    }
    public void setOrderCode(String orderCode)
    {
        this.orderCode = orderCode;
    }
    public String getOrderType()
    {
        return orderType;
    }
    public void setOrderType(String orderType)
    {
        this.orderType = orderType;
    }
    public String getTestingStatus()
    {
        return testingStatus;
    }
    public void setTestingStatus(String testingStatus)
    {
        this.testingStatus = testingStatus;
    }
    public String getPaymentStatus()
    {
        return paymentStatus;
    }
    public void setPaymentStatus(String paymentStatus)
    {
        this.paymentStatus = paymentStatus;
    }
    public String getCustomerId()
    {
        return customerId;
    }
    public void setCustomerId(String customerId)
    {
        this.customerId = customerId;
    }
    public String getCreateName()
    {
        return createName;
    }
    public void setCreateName(String createName)
    {
        this.createName = createName;
    }
    public String getStartTime()
    {
        return startTime;
    }
    public void setStartTime(String startTime)
    {
        this.startTime = startTime;
    }
    public String getEndTime()
    {
        return endTime;
    }
    public void setEndTime(String endTime)
    {
        this.endTime = endTime;
    }
    public String getRecipientPhone()
    {
        return recipientPhone;
    }
    public void setRecipientPhone(String recipientPhone)
    {
        this.recipientPhone = recipientPhone;
    }
    public String getProductFlag()
    {
        return productFlag;
    }
    public void setProductFlag(String productFlag)
    {
        this.productFlag = productFlag;
    }
    public String getSampleFlag()
    {
        return sampleFlag;
    }
    public void setSampleFlag(String sampleFlag)
    {
        this.sampleFlag = sampleFlag;
    }
    public String getInvoiceFlag()
    {
        return invoiceFlag;
    }
    public void setInvoiceFlag(String invoiceFlag)
    {
        this.invoiceFlag = invoiceFlag;
    }
    
}
