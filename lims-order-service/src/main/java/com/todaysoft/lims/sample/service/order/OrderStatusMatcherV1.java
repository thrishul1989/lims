package com.todaysoft.lims.sample.service.order;

import com.todaysoft.lims.sample.entity.order.Order;
import com.todaysoft.lims.sample.util.Constant;

class OrderStatusMatcherV1 implements IOrderStatusMatcher
{
    @Override
    public boolean isDraft(Order order)
    {
        return Constants.ORDER_TEMPORARY.equals(order.getStatus());
    }
    
    @Override
    public boolean isPaid(Order order)
    {
        if (null == order.getStatus())
        {
            return false;
        }
        
        return Constants.ORDER_PAYED.equals(order.getStatus()) || Constants.ORDER_FINISHED.equals(order.getStatus())
            || Constants.ORDER_TESTING.equals(order.getStatus());
    }
    
    @Override
    public boolean isRefundApplyable(Order order)
    {
        return Constants.ORDER_PAYED.equals(order.getStatus());
    }
    
    @Override
    public boolean isPaymentDelayApplyable(Order order)
    {
        return Constants.ORDER_PAY_PENDING.equals(order.getStatus());
    }
    
    @Override
    public boolean isReduceApplyable(Order order)
    {
        return Constants.ORDER_PAY_PENDING.equals(order.getStatus());
    }
    
    @Override
    public boolean isReduceAmountModifyable(Order order)
    {
        return (order.getStatus() != Constants.ORDER_PAYED && order.getStatus() != Constants.ORDER_FINISHED
            && order.getStatus() != Constants.ORDER_PAY_TO_BE_CONFIRMED && order.getStatus() != Constants.ORDER_UNTESTED && order.getStatus() != Constants.ORDER_TESTING);
    }
    
    @Override
    public boolean isLogicallyDeletable(Order order)
    {
        return order.getStatus().equals(Constants.ORDER_TEMPORARY) || order.getStatus().equals(Constants.ORDER_PAY_PENDING)
            || order.getStatus().equals(Constants.ORDER_UNTESTED);
    }
    
    @Override
    public boolean isPhysicallyDeletable(Order order)
    {
        return order.getStatus().equals(Constants.ORDER_TEMPORARY);
    }
    
    @Override
    public boolean isConfirmOrder(Order order)
    {
        return Constant.ORDER_STATUS_RECEIVED_SAMPLE == order.getStatus();
    }
}
