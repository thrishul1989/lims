package com.todaysoft.lims.testing.base.service;

public interface IOrderStatusService
{
    String cancelOrderStatus(String orderId, String productId, String sampleId,String tag);
    
    void pauseOrderStatus(String sheduleId, String type);
}
