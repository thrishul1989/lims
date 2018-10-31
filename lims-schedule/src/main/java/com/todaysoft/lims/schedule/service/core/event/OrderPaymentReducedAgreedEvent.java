package com.todaysoft.lims.schedule.service.core.event;

public class OrderPaymentReducedAgreedEvent
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
