package com.todaysoft.lims.testing.base.request;

public class TestingScheduleQueryRequest
{
    private String orderId;
    
    private String sampleId;
    
    private String productId;
    
    private String methodId;
    
    public TestingScheduleQueryRequest(String orderId, String sampleId)
    {
        super();
        this.orderId = orderId;
        this.sampleId = sampleId;
    }
    
    public TestingScheduleQueryRequest(String orderId, String sampleId, String productId, String methodId)
    {
        super();
        this.orderId = orderId;
        this.sampleId = sampleId;
        this.productId = productId;
        this.methodId = methodId;
    }
    
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
    
    public String getMethodId()
    {
        return methodId;
    }
    
    public void setMethodId(String methodId)
    {
        this.methodId = methodId;
    }
    
}
