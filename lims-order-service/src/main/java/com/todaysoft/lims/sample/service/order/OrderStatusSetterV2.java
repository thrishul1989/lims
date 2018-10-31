package com.todaysoft.lims.sample.service.order;

import com.todaysoft.lims.sample.entity.order.Order;

class OrderStatusSetterV2 implements IOrderStatusSetter
{
    @Override
    public void setForCreated(Order order, Integer settlementType, Integer contractStartType)
    {
        order.setDeleted(false);
        order.setDraft(Constants.ORDER_DRAFT_NO);
        order.setPaymentStatus(Constants.ORDER_PAYMENT_UNPAID);
        order.setFullPaid(Constants.ORDER_FULL_PAID_NO);
        order.setPaymentDelayStatus(Constants.ORDER_PAYMENT_DELAY_UNAPPLIED);
        order.setTestingStatus(Constants.ORDER_TESTING_DELEGATED);
        order.setSettlementType(settlementType);
        order.setPayType(Constants.ORDER_PAYMENT_TYPE_NONE);
        order.setSheduleStatus(Constants.ORDER_TESTING_START_NO);
        order.setReceivedSampleStatus(Constants.ORDER_SAMPLE_REQUIRMENT_UNMATCHED);
        
        if (Constants.ORDER_SETTLEMENT_CENTRAL.equals(settlementType))
        { //集中结算考虑合同是否自启
            if (Constants.CONTRACT_START_AUTO.equals(contractStartType))
            {
                order.setSchedulePaymentStatus(Constants.ORDER_PAYMENT_REQUIRMENT_MATCHED);
            }
            else
            {
                order.setSchedulePaymentStatus(Constants.ORDER_PAYMENT_REQUIRMENT_UNMATCHED);
            }
        }
    }
    
    @Override
    public void setForPaymentConfirmed(Order order)
    {
        order.setPaymentStatus(Constants.ORDER_PAYMENT_PAID);
        order.setSchedulePaymentStatus(Constants.ORDER_PAYMENT_REQUIRMENT_MATCHED);
    }
    
    @Override
    public void setForCancelOrder(Order order)
    {
        order.setTestingStatus(Constants.ORDER_TESTING_CANCELED);
        
    }
    
    @Override
    public void setForOrderReconciliated(Order order)
    {
        order.setPaymentStatus(Constants.ORDER_PAYMENT_PAID);
        order.setSchedulePaymentStatus(Constants.ORDER_PAYMENT_REQUIRMENT_MATCHED);
    }
}
