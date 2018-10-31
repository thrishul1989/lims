package com.todaysoft.lims.schedule.service.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.todaysoft.lims.schedule.ons.AbstractMessageConsumer;
import com.todaysoft.lims.schedule.service.IOrderService;
import com.todaysoft.lims.schedule.service.ISchedulePlanService;
import com.todaysoft.lims.schedule.service.core.event.SampleReceivedEvent;

@Component
public class SampleReceiveMessageConsumer extends AbstractMessageConsumer<SampleReceivedEvent>
{
    @Autowired
    private IOrderService orderService;
    
    @Autowired
    private ISchedulePlanService schedulePlanService;
    
    @Override
    protected void handle(SampleReceivedEvent event)
    {
        if (log.isDebugEnabled())
        {
            log.debug("Start to handle the sample received event, order id {}", event.getOrderId());
        }
        
        synchronized (this)
        {
            String orderId = event.getOrderId();
            
            // 针对未计划的历史订单，创建订单计划
            if (!orderService.isOrderPlanned(orderId))
            {
                log.warn("Order plan is not generated, start to generate the plan first, order id {}", orderId);
                schedulePlanService.generateOrderSchedulePlan(orderId);
            }
            
            boolean finished = orderService.isSampleReceiveFinished(orderId);
            
            if (finished)
            {
                if (log.isDebugEnabled())
                {
                    log.debug("Order samples is all received, start to update the plan, order id {}", orderId);
                }
                
                schedulePlanService.updatePlanForSampleReceiveFinished(orderId);
            }
            else
            {
                if (log.isDebugEnabled())
                {
                    log.debug("Order samples is not all received, ignore the update plan operation, order id {}", orderId);
                }
            }
            
            if (log.isDebugEnabled())
            {
                log.debug("Handle the sample received event successful, order id {}", event.getOrderId());
            }
        }
    }
}
