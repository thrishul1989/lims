package com.todaysoft.lims.sample.service.order;

import com.todaysoft.lims.sample.entity.order.Order;

public interface IOrderStatusSetter
{
    void setForCreated(Order order, Integer settlementType, Integer contractStartType);
    
    void setForPaymentConfirmed(Order order);
    
    void setForCancelOrder(Order order);
    
    void setForOrderReconciliated(Order order);
}
