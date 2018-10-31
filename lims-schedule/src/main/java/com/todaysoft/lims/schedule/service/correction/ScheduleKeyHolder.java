package com.todaysoft.lims.schedule.service.correction;

public class ScheduleKeyHolder
{
    private String orderId;
    
    private String sampleId;
    
    private String productId;
    
    private String testingMethodId;
    
    public String getOrderId()
    {
        return orderId;
    }
    
    public void setOrderId(String orderId)
    {
        this.orderId = orderId;
    }
    
    public String getSampleId()
    {
        return sampleId;
    }
    
    public void setSampleId(String sampleId)
    {
        this.sampleId = sampleId;
    }
    
    public String getProductId()
    {
        return productId;
    }
    
    public void setProductId(String productId)
    {
        this.productId = productId;
    }
    
    public String getTestingMethodId()
    {
        return testingMethodId;
    }
    
    public void setTestingMethodId(String testingMethodId)
    {
        this.testingMethodId = testingMethodId;
    }
}
