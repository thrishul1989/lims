package com.todaysoft.lims.schedule.service.core.event;

public class OpenReportGenerateEvent
{
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
    
}
