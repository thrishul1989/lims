package com.todaysoft.lims.sample.service.order;

import com.todaysoft.lims.sample.entity.order.Order;

class OrderStatusMatcherV2 implements IOrderStatusMatcher
{
    @Override
    public boolean isDraft(Order order)
    {
        //return Constants.ORDER_DRAFT_YES.equals(order.getDraft());
        return Constants.ORDER_TESTING_UNSTARTED.equals(order.getTestingStatus());
    }
    
    @Override
    public boolean isPaid(Order order)
    {
        return Constants.ORDER_PAYMENT_PAID.equals(order.getPaymentStatus());
    }
    
    @Override
    public boolean isRefundApplyable(Order order)
    {
        // 一单一结、已付款的订单可申请退款
        return Constants.ORDER_SETTLEMENT_SINGLE.equals(order.getSettlementType()) && Constants.ORDER_PAYMENT_PAID.equals(order.getPaymentStatus());
    }
    
    @Override
    public boolean isPaymentDelayApplyable(Order order)
    {
        // 一单一结、待付款、未申请延迟付款的订单可申请延迟付款
        return Constants.ORDER_SETTLEMENT_SINGLE.equals(order.getSettlementType()) && Constants.ORDER_PAYMENT_UNPAID.equals(order.getPaymentStatus())
            && Constants.ORDER_PAYMENT_DELAY_UNAPPLIED.equals(order.getPaymentDelayStatus());
    }
    
    @Override
    public boolean isReduceApplyable(Order order)
    {
        // 一单一结、待付款的订单可申请减免
        return Constants.ORDER_SETTLEMENT_SINGLE.equals(order.getSettlementType()) && Constants.ORDER_PAYMENT_UNPAID.equals(order.getPaymentStatus());
    }
    
    @Override
    public boolean isReduceAmountModifyable(Order order)
    {
        // 待付款的订单可更新减免额
        return Constants.ORDER_PAYMENT_UNPAID.equals(order.getPaymentStatus());
    }
    
    @Override
    public boolean isLogicallyDeletable(Order order)
    {
        // 一单一结-待付款、集中付款-待检测确认的订单可逻辑删除
        
        if (Constants.ORDER_SETTLEMENT_SINGLE.equals(order.getSettlementType()) && Constants.ORDER_PAYMENT_UNPAID.equals(order.getPaymentStatus()))
        {
            return true;
        }
        
        if (Constants.ORDER_SETTLEMENT_CENTRAL.equals(order.getSettlementType())
            && Constants.SCHEDULE_PAYMENT_STATUS_UNCONFIRMED.equals(order.getSchedulePaymentStatus()))
        {
            return true;
        }
        
        return false;
    }
    
    @Override
    public boolean isPhysicallyDeletable(Order order)
    {
        // 暂存的订单可物理删除
        return isDraft(order);
    }
    
    @Override
    public boolean isConfirmOrder(Order order)
    {
        return null != order.getPaymentStatus() && Constants.ORDER_PAYMENT_CONFIRMING == order.getPaymentStatus();
    }
}
