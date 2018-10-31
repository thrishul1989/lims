package com.todaysoft.lims.sample.model;

public class ResamplingCancelRecordDetails
{
    private String id;
    
    private String orderCode;
    
    private String orderAgentName;
    
    private String orderCustomerName;
    
    private String orderMarketingCenter;
    
    private String sampleCode;
    
    private String sampleName;
    
    private String sampleTypeName;
    
    private String sampleBusinessType;
    
    private String samplePurpose;

    private String errorDealRemark;
    
    private ResamplingCancelSolveRecordModel solveRecord;
    
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
    
    public String getOrderAgentName()
    {
        return orderAgentName;
    }
    
    public void setOrderAgentName(String orderAgentName)
    {
        this.orderAgentName = orderAgentName;
    }
    
    public String getOrderCustomerName()
    {
        return orderCustomerName;
    }
    
    public void setOrderCustomerName(String orderCustomerName)
    {
        this.orderCustomerName = orderCustomerName;
    }
    
    public String getOrderMarketingCenter()
    {
        return orderMarketingCenter;
    }
    
    public void setOrderMarketingCenter(String orderMarketingCenter)
    {
        this.orderMarketingCenter = orderMarketingCenter;
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
    
    public String getSampleTypeName()
    {
        return sampleTypeName;
    }
    
    public void setSampleTypeName(String sampleTypeName)
    {
        this.sampleTypeName = sampleTypeName;
    }
    
    public String getSampleBusinessType()
    {
        return sampleBusinessType;
    }
    
    public void setSampleBusinessType(String sampleBusinessType)
    {
        this.sampleBusinessType = sampleBusinessType;
    }
    
    public String getSamplePurpose()
    {
        return samplePurpose;
    }
    
    public void setSamplePurpose(String samplePurpose)
    {
        this.samplePurpose = samplePurpose;
    }

    public String getErrorDealRemark() {
        return errorDealRemark;
    }

    public void setErrorDealRemark(String errorDealRemark) {
        this.errorDealRemark = errorDealRemark;
    }

    public ResamplingCancelSolveRecordModel getSolveRecord()
    {
        return solveRecord;
    }

    public void setSolveRecord(ResamplingCancelSolveRecordModel solveRecord)
    {
        this.solveRecord = solveRecord;
    }
}
