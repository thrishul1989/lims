package com.todaysoft.lims.schedule.service.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.todaysoft.lims.schedule.ons.AbstractMessageConsumer;
import com.todaysoft.lims.schedule.service.IOrderService;
import com.todaysoft.lims.schedule.service.ISchedulePlanService;
import com.todaysoft.lims.schedule.service.core.event.OrderPaymentReducedAgreedEvent;

@Component
public class OrderPaymentReducedAgreedMessageConsumer extends AbstractMessageConsumer<OrderPaymentReducedAgreedEvent>
{
    @Autowired
    private IOrderService orderService;
    
    @Autowired
    private ISchedulePlanService schedulePlanService;
    
    @Override
    protected void handle(OrderPaymentReducedAgreedEvent event)
    {
        String orderId = event.getOrderId();
        
        if (log.isDebugEnabled())
        {
            log.debug("Start to handle the order payment reduced agreed event, order id {}", orderId);
        }
        
        synchronized (this)
        {
            // 针对未计划的历史订单，创建订单计划
            if (!orderService.isOrderPlanned(orderId))
            {
                log.warn("Order plan is not generated, start to generate the plan first, order id {}", orderId);
                schedulePlanService.generateOrderSchedulePlan(orderId);
            }
            
            boolean reducedAsFree = orderService.isReducedAsPaymentConfirmed(orderId);
            
            if (reducedAsFree)
            {
                if (log.isDebugEnabled())
                {
                    log.debug("Order reduced as free, start to update the plan, order id {}", orderId);
                }
                
                schedulePlanService.updatePlanForPaymentConfirmFinished(orderId);
            }
            else
            {
                if (log.isDebugEnabled())
                {
                    log.debug("Order is not reduced as free, ignore the update plan operation, order id {}", orderId);
                }
            }
            
            if (log.isDebugEnabled())
            {
                log.debug("Handle the order payment reduced agreed event successful, order id {}", orderId);
            }
        }
    }
}
