package com.todaysoft.lims.sample.model.order;

public class ScheduleQuery
{
    private String orderId;
    
    private String productId;
    
    private String methodId;
    
    private String sampleId;
    
    private Integer type;
    
    public Integer getType()
    {
        return type;
    }
    
    public void setType(Integer type)
    {
        this.type = type;
    }
    
    public ScheduleQuery()
    {
    }
    
    public ScheduleQuery(String orderId, String productId, String methodId, String sampleId)
    {
        super();
        this.orderId = orderId;
        this.productId = productId;
        this.methodId = methodId;
        this.sampleId = sampleId;
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
    
    public String getMethodId()
    {
        return methodId;
    }
    
    public void setMethodId(String methodId)
    {
        this.methodId = methodId;
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
