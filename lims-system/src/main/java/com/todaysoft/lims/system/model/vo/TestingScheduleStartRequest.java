package com.todaysoft.lims.system.model.vo;

public class TestingScheduleStartRequest
{
    private String sampleInstanceId;
    
    private String productId;
    
    private String starter;
    
    public String getSampleInstanceId()
    {
        return sampleInstanceId;
    }
    
    public void setSampleInstanceId(String sampleInstanceId)
    {
        this.sampleInstanceId = sampleInstanceId;
    }
    
    public String getProductId()
    {
        return productId;
    }
    
    public void setProductId(String productId)
    {
        this.productId = productId;
    }
    
    public String getStarter()
    {
        return starter;
    }
    
    public void setStarter(String starter)
    {
        this.starter = starter;
    }
}
