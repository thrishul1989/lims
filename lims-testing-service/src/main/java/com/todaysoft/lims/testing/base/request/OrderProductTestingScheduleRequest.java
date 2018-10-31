package com.todaysoft.lims.testing.base.request;

public class OrderProductTestingScheduleRequest
{
    private String productCancelRecordId; //产品取消任务
    
    private String orderId;
    
    private String productId;
    
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
    
    public String getProductCancelRecordId()
    {
        return productCancelRecordId;
    }
    
    public void setProductCancelRecordId(String productCancelRecordId)
    {
        this.productCancelRecordId = productCancelRecordId;
    }
    
}
