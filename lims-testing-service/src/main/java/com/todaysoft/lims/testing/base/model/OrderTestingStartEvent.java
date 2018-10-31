package com.todaysoft.lims.testing.base.model;

import java.util.List;

public class OrderTestingStartEvent
{
    private String orderId;
    
    private List<String> scheduleIds;
    
    private java.util.Set<String> orderIds;
    
    public List<String> getScheduleIds()
    {
        return scheduleIds;
    }
    
    public void setScheduleIds(List<String> scheduleIds)
    {
        this.scheduleIds = scheduleIds;
    }
    
    public java.util.Set<String> getOrderIds()
    {
        return orderIds;
    }
    
    public void setOrderIds(java.util.Set<String> orderIds)
    {
        this.orderIds = orderIds;
    }
    
    public String getOrderId()
    {
        return orderId;
    }
    
    public void setOrderId(String orderId)
    {
        this.orderId = orderId;
    }
}
