package com.todaysoft.lims.system.model.vo.order;

public class TemproaryTestingTaskRequest
{
    private String orderId;
    
    private String productId;
    
    private String sampleId;

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

    public String getSampleId()
    {
        return sampleId;
    }

    public void setSampleId(String sampleId)
    {
        this.sampleId = sampleId;
    }
    
    
}
