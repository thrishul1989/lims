package com.todaysoft.lims.schedule.service.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.todaysoft.lims.schedule.ons.AbstractMessageConsumer;
import com.todaysoft.lims.schedule.service.IOrderService;
import com.todaysoft.lims.schedule.service.ISchedulePlanService;
import com.todaysoft.lims.schedule.service.core.event.OrderSubmitEvent;

@Component
public class OrderSubmitMessageConsumer extends AbstractMessageConsumer<OrderSubmitEvent>
{
    @Autowired
    private IOrderService orderService;
    
    @Autowired
    private ISchedulePlanService schedulePlanService;
    
    @Override
    protected void handle(OrderSubmitEvent event)
    {
        if (log.isDebugEnabled())
        {
            log.debug("Start to handle the order submit event, order id {}", event.getOrderId());
        }
        
        String orderId = event.getOrderId();
        
        synchronized (this)
        {
            if (orderService.isOrderPlanned(orderId))
            {
                if (log.isDebugEnabled())
                {
                    log.debug("Handle the order submit event successful, order already planned", event.getOrderId());
                }
            }
            else
            {
                schedulePlanService.generateOrderSchedulePlan(event.getOrderId());
                
                if (log.isDebugEnabled())
                {
                    log.debug("Generate the order schedule plan successful, order id {}", event.getOrderId());
                }
                
                schedulePlanService.updatePlanForOrderSubmit(event.getOrderId());
                
                if (log.isDebugEnabled())
                {
                    log.debug("Update the order schedule plan successful, order id {}", event.getOrderId());
                }
                
                // 处理APP端订单提交时，直接全免的情况
                boolean paymentConfirmed = orderService.isReducedAsPaymentConfirmed(event.getOrderId());
                
                if (paymentConfirmed)
                {
                    if (log.isDebugEnabled())
                    {
                        log.debug("Order is reduced as payment confirmed, update the plan, order id {}", event.getOrderId());
                    }
                    
                    schedulePlanService.updatePlanForPaymentConfirmFinished(event.getOrderId());
                }
                
                // 处理合同订单自动启动的情况
                boolean autoStartContractOrder = orderService.isAutoStartContractOrder(event.getOrderId());
                
                if (autoStartContractOrder)
                {
                    if (log.isDebugEnabled())
                    {
                        log.debug("Order is auto start contract order, update the plan, order id {}", event.getOrderId());
                    }
                    
                    schedulePlanService.updatePlanForPaymentConfirmFinished(event.getOrderId());
                }
                
                if (log.isDebugEnabled())
                {
                    log.debug("Handle the order submit event successful, order id {}", event.getOrderId());
                }
            }
        }
    }
}
