package com.todaysoft.lims.sample.service.impl;

import org.springframework.stereotype.Service;

import com.todaysoft.lims.sample.entity.order.Order;
import com.todaysoft.lims.sample.service.IOrderStatusService;
import com.todaysoft.lims.sample.service.order.IOrderStatusMatcher;
import com.todaysoft.lims.sample.service.order.IOrderStatusSetter;
import com.todaysoft.lims.sample.service.order.OrderStatusMatcher;
import com.todaysoft.lims.sample.service.order.OrderStatusSetter;

@Service
public class OrderStatusService implements IOrderStatusService
{
    private IOrderStatusSetter setter = new OrderStatusSetter();
    
    private IOrderStatusMatcher matcher = new OrderStatusMatcher();
    
    @Override
    public boolean isDraft(Order order)
    {
        return matcher.isDraft(order);
    }
    
    @Override
    public void setForCreated(Order order, Integer settlementType, Integer contractStartType)
    {
        setter.setForCreated(order, settlementType, contractStartType);
    }
    
    @Override
    public void setForPaymentConfirmed(Order order)
    {
        setter.setForPaymentConfirmed(order);
    }
    
    @Override
    public boolean isLogicallyDeletable(Order o)
    {
        return matcher.isLogicallyDeletable(o);
    }
    
    @Override
    public void setForCancelOrder(Order order)
    {
        setter.setForCancelOrder(order);
    }
    
    @Override
    public boolean isConfirmOrder(Order order)
    {
        return matcher.isConfirmOrder(order);
    }
    
    @Override
    public void setForOrderReconciliated(Order order)
    {
        setter.setForOrderReconciliated(order);
    }
}
