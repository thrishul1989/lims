package com.todaysoft.lims.system.model.vo.order;

public class OrderProductTestingScheduleRequest
{
    private String productCancelRecordId;
    
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
