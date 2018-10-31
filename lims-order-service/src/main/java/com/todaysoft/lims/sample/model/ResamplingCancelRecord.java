package com.todaysoft.lims.sample.model;

public class ResamplingCancelRecord
{
    private String id;
    
    private String orderCode;
    
    private String sampleTypeName;
    
    private String sampleCode;
    
    private String sampleName;
    
    private String sampleBusinessType;
    
    private String samplePurpose;

    private String errorDealRemark;

    private String projectManager;//项目管理人

    public String getProjectManager() {
        return projectManager;
    }

    public void setProjectManager(String projectManager) {
        this.projectManager = projectManager;
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
    
    public String getSampleTypeName()
    {
        return sampleTypeName;
    }
    
    public void setSampleTypeName(String sampleTypeName)
    {
        this.sampleTypeName = sampleTypeName;
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
}
