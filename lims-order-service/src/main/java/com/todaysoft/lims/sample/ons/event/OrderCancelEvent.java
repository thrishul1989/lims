package com.todaysoft.lims.sample.ons.event;

public class OrderCancelEvent
{
    private String orderId;
    
    public String getOrderId()
    {
        return orderId;
    }
    
    public void setOrderId(String orderId)
    {
        this.orderId = orderId;
    }
}
