package com.todaysoft.lims.sample.service.order;

import com.todaysoft.lims.sample.entity.order.Order;

public class OrderStatusMatcher implements IOrderStatusMatcher
{
    private OrderStatusMatcherV1 v1matcher = new OrderStatusMatcherV1();
    
    private OrderStatusMatcherV2 v2matcher = new OrderStatusMatcherV2();
    
    @Override
    public boolean isDraft(Order order)
    {
        return v1matcher.isDraft(order) || v2matcher.isDraft(order);
    }
    
    @Override
    public boolean isPaid(Order order)
    {
        return v1matcher.isPaid(order) || v2matcher.isPaid(order);
    }
    
    @Override
    public boolean isRefundApplyable(Order order)
    {
        return v1matcher.isRefundApplyable(order) || v2matcher.isRefundApplyable(order);
    }
    
    @Override
    public boolean isPaymentDelayApplyable(Order order)
    {
        return v1matcher.isPaymentDelayApplyable(order) || v2matcher.isPaymentDelayApplyable(order);
    }
    
    @Override
    public boolean isReduceApplyable(Order order)
    {
        return v1matcher.isReduceApplyable(order) || v2matcher.isReduceApplyable(order);
    }
    
    @Override
    public boolean isReduceAmountModifyable(Order order)
    {
        return v1matcher.isReduceAmountModifyable(order) || v2matcher.isReduceAmountModifyable(order);
    }
    
    @Override
    public boolean isLogicallyDeletable(Order order)
    {
        return v1matcher.isLogicallyDeletable(order) || v2matcher.isLogicallyDeletable(order);
    }
    
    @Override
    public boolean isPhysicallyDeletable(Order order)
    {
        return v1matcher.isPhysicallyDeletable(order) || v2matcher.isPhysicallyDeletable(order);
    }
    
    @Override
    public boolean isConfirmOrder(Order order)
    {
        return v1matcher.isConfirmOrder(order) || v2matcher.isConfirmOrder(order);
    }
}
