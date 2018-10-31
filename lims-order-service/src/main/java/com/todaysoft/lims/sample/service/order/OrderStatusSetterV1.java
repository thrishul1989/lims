package com.todaysoft.lims.sample.service.order;

import com.todaysoft.lims.sample.entity.order.Order;
import com.todaysoft.lims.sample.util.Constant;

class OrderStatusSetterV1 implements IOrderStatusSetter
{
    @Override
    public void setForCreated(Order order, Integer settlementType, Integer contractStartType)
    {
        if (Constants.ORDER_SETTLEMENT_SINGLE.equals(settlementType))
        {
            order.setStatus(Constant.ORDER_DEFAULT_STATE);
            order.setSchedulePaymentStatus(Constants.ORDER_PAYMENT_REQUIRMENT_UNMATCHED);
        }
        else
        {
            order.setStatus(Constant.ORDER_RESEARCH_STATE);
            if (Constants.CONTRACT_START_AUTO.equals(contractStartType))
            {
                order.setSchedulePaymentStatus(Constants.ORDER_PAYMENT_REQUIRMENT_MATCHED);
            }
            else
            {
                order.setSchedulePaymentStatus(Constants.ORDER_PAYMENT_REQUIRMENT_UNMATCHED);
            }
        }
        
        order.setDeleted(false);
        order.setPayType(Constants.ORDER_PAYMENT_TYPE_NONE);
        order.setSheduleStatus(Constants.ORDER_TESTING_START_NO);
        order.setReceivedSampleStatus(Constants.ORDER_SAMPLE_REQUIRMENT_UNMATCHED);
        
    }
    
    @Override
    public void setForPaymentConfirmed(Order order)
    {
        order.setStatus(Constant.ORDER_ALREADY_PAYED);
        order.setSchedulePaymentStatus(Constants.ORDER_PAYMENT_REQUIRMENT_MATCHED);
    }
    
    @Override
    public void setForCancelOrder(Order order)
    {
        order.setStatus(Constants.ORDER_CANCEL);
    }
    
    @Override
    public void setForOrderReconciliated(Order order)
    {
        //order.setStatus(Constant.ORDER_ALREADY_PAYED); 一单一结的 默认是付款待确认 状态先不改
    }
}
