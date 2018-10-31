package com.todaysoft.lims.schedule.request;

import java.util.Set;

public class OrderPostponedDetailsBatchRequest
{
    private Set<String> orderIds;
    
    public Set<String> getOrderIds()
    {
        return orderIds;
    }
    
    public void setOrderIds(Set<String> orderIds)
    {
        this.orderIds = orderIds;
    }
}
