package com.todaysoft.lims.sample.service.order;

import com.todaysoft.lims.sample.entity.order.Order;

public interface IOrderStatusMatcher
{
    boolean isDraft(Order order);
    
    boolean isPaid(Order order);
    
    boolean isRefundApplyable(Order order);
    
    boolean isPaymentDelayApplyable(Order order);
    
    boolean isReduceApplyable(Order order);
    
    boolean isReduceAmountModifyable(Order order);
    
    boolean isLogicallyDeletable(Order order);
    
    boolean isPhysicallyDeletable(Order order);
    
    boolean isConfirmOrder(Order order);
}
