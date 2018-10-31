package com.todaysoft.lims.schedule.service.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.todaysoft.lims.schedule.ons.AbstractMessageConsumer;
import com.todaysoft.lims.schedule.service.IOrderService;
import com.todaysoft.lims.schedule.service.ISchedulePlanService;
import com.todaysoft.lims.schedule.service.core.event.OrderModifyEvent;

@Component
public class OrderModifyMessageConsumer extends AbstractMessageConsumer<OrderModifyEvent>
{
    @Autowired
    private IOrderService orderService;
    
    @Autowired
    private ISchedulePlanService schedulePlanService;
    
    @Override
    protected void handle(OrderModifyEvent event)
    {
        if (log.isDebugEnabled())
        {
            log.debug("Start to handle the order modify event, order id {}", event.getOrderId());
        }
        
        String orderId = event.getOrderId();
        
        // 订单已启动检测流程，不做处理
        if (orderService.isOrderTestingStarted(orderId))
        {
            if (log.isDebugEnabled())
            {
                log.debug("Order testing is started, ignore the update plan operation, order id {}", orderId);
            }
            
            return;
        }
        
        synchronized (this)
        {
            // 针对未计划的历史订单，创建订单计划
            if (!orderService.isOrderPlanned(orderId))
            {
                log.warn("Order plan is not generated, start to generate the plan first, order id {}", orderId);
                schedulePlanService.generateOrderSchedulePlan(orderId);
            }
            else
            {
                schedulePlanService.updatePlanForOrderModify(orderId);
            }
            
            if (log.isDebugEnabled())
            {
                log.debug("Handle the order modify event successful, order id {}", event.getOrderId());
            }
        }
    }
}
