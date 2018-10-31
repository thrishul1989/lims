package com.todaysoft.lims.sample.service.order;

import com.todaysoft.lims.sample.entity.order.Order;

public class OrderStatusSetter implements IOrderStatusSetter
{
    private OrderStatusSetterV1 v1setter = new OrderStatusSetterV1();
    
    private OrderStatusSetterV2 v2setter = new OrderStatusSetterV2();
    
    @Override
    public void setForCreated(Order order, Integer settlementType, Integer contractStartType)
    {
        v1setter.setForCreated(order, settlementType, contractStartType);
        v2setter.setForCreated(order, settlementType, contractStartType);
    }
    
    @Override
    public void setForPaymentConfirmed(Order order)
    {
        v1setter.setForPaymentConfirmed(order);
        v2setter.setForPaymentConfirmed(order);
    }
    
    @Override
    public void setForCancelOrder(Order order)
    {
        v1setter.setForCancelOrder(order);
        v2setter.setForCancelOrder(order);
        
    }
    
    @Override
    public void setForOrderReconciliated(Order order)
    {
        v1setter.setForOrderReconciliated(order);
        v2setter.setForOrderReconciliated(order);
    }
}
