package com.todaysoft.lims.sample.service;

import com.todaysoft.lims.sample.entity.order.Order;

public interface IOrderStatusService
{
    void setForCreated(Order order, Integer settlementType, Integer contractStartType);
    
    void setForPaymentConfirmed(Order order);
    
    boolean isDraft(Order o);
    
    boolean isLogicallyDeletable(Order o);
    
    void setForCancelOrder(Order order);
    
    boolean isConfirmOrder(Order order);
    
    void setForOrderReconciliated(Order order);
    
}
