package com.todaysoft.lims.system.modules.bpm.dnaqc.model;

public class DNAQcTaskSearcher
{
    private String orderType;
    
    private String sampleCode;
    
    private String sampleName;
    
    private String products;
    
    private String resubmit;
    
    private String originalSampleTypeName;
    
    private Integer ifUrgent;
    
    public String getOrderType()
    {
        return orderType;
    }
    
    public void setOrderType(String orderType)
    {
        this.orderType = orderType;
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
    
    public String getProducts()
    {
        return products;
    }
    
    public void setProducts(String products)
    {
        this.products = products;
    }
    
    public String getResubmit()
    {
        return resubmit;
    }
    
    public void setResubmit(String resubmit)
    {
        this.resubmit = resubmit;
    }
    
    public String getOriginalSampleTypeName()
    {
        return originalSampleTypeName;
    }
    
    public void setOriginalSampleTypeName(String originalSampleTypeName)
    {
        this.originalSampleTypeName = originalSampleTypeName;
    }

    public Integer getIfUrgent()
    {
        return ifUrgent;
    }

    public void setIfUrgent(Integer ifUrgent)
    {
        this.ifUrgent = ifUrgent;
    }
    
}
