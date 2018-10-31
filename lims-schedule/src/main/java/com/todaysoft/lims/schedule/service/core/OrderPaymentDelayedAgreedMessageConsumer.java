package com.todaysoft.lims.schedule.service.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.todaysoft.lims.schedule.ons.AbstractMessageConsumer;
import com.todaysoft.lims.schedule.service.IOrderService;
import com.todaysoft.lims.schedule.service.ISchedulePlanService;
import com.todaysoft.lims.schedule.service.core.event.OrderPaymentDelayedAgreedEvent;

@Component
public class OrderPaymentDelayedAgreedMessageConsumer extends AbstractMessageConsumer<OrderPaymentDelayedAgreedEvent>
{
    @Autowired
    private IOrderService orderService;
    
    @Autowired
    private ISchedulePlanService schedulePlanService;
    
    @Override
    protected void handle(OrderPaymentDelayedAgreedEvent event)
    {
        String orderId = event.getOrderId();
        
        if (log.isDebugEnabled())
        {
            log.debug("Start to handle the order payment delayed agreed event, order id {}", orderId);
        }
        
        synchronized (this)
        {
            // 针对未计划的历史订单，创建订单计划
            if (!orderService.isOrderPlanned(orderId))
            {
                log.warn("Order plan is not generated, start to generate the plan first, order id {}", orderId);
                schedulePlanService.generateOrderSchedulePlan(orderId);
            }
            
            schedulePlanService.updatePlanForPaymentConfirmFinished(orderId);
            
            if (log.isDebugEnabled())
            {
                log.debug("Handle the order payment delayed agreed event successful, order id {}", event.getOrderId());
            }
        }
    }
}
