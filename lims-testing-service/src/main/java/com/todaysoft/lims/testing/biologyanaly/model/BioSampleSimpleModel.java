package com.todaysoft.lims.testing.biologyanaly.model;

public class BioSampleSimpleModel
{
    private String orderId;
    
    private String productId;
    
    private String sampleCode;
    
    public BioSampleSimpleModel()
    {
        super();
    }
    
    public BioSampleSimpleModel(String orderId, String productId, String sampleCode)
    {
        super();
        this.orderId = orderId;
        this.productId = productId;
        this.sampleCode = sampleCode;
    }
    
    public String getOrderId()
    {
        return orderId;
    }
    
    public void setOrderId(String orderId)
    {
        this.orderId = orderId;
    }
    
    public String getProductId()
    {
        return productId;
    }
    
    public void setProductId(String productId)
    {
        this.productId = productId;
    }
    
    public String getSampleCode()
    {
        return sampleCode;
    }
    
    public void setSampleCode(String sampleCode)
    {
        this.sampleCode = sampleCode;
    }
}
