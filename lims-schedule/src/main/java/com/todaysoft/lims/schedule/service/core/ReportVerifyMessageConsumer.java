package com.todaysoft.lims.schedule.service.core;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.todaysoft.lims.schedule.ons.AbstractMessageConsumer;
import com.todaysoft.lims.schedule.service.IOrderService;
import com.todaysoft.lims.schedule.service.IReportVerifyService;
import com.todaysoft.lims.schedule.service.ISchedulePlanService;
import com.todaysoft.lims.schedule.service.core.event.ReportVerifyEvent;

@Component
public class ReportVerifyMessageConsumer extends AbstractMessageConsumer<ReportVerifyEvent>
{
    
    @Autowired
    private IOrderService orderService;
    
    @Autowired
    private IReportVerifyService service;
    
    @Autowired
    private ISchedulePlanService schedulePlanService;
    
    @Override
    protected void handle(ReportVerifyEvent event)
    {
        Map<String,String> orderProductId = event.getOrderProductId();
        String orderId = orderProductId.get("orderId");
        String productId = orderProductId.get("productId");
        String reviewResult = event.getReviewResult();
        if (log.isDebugEnabled())
        {
            log.debug("Start to handle the order report verify event, order id {} , product id {}", orderId);
        }
        
        synchronized (this)
        {
            // 针对未计划的历史订单，创建订单计划
            if (!orderService.isOrderPlanned(orderId))
            {
                log.warn("Order plan is not generated, start to generate the plan first, order id {}", orderId);
                schedulePlanService.generateOrderSchedulePlan(orderId);
            }
            
            schedulePlanService.updatePlanForReportVerifyFinished(orderId,productId,reviewResult);
            
            if (log.isDebugEnabled())
            {
                log.debug("Handle the order report verify event successful, order id {} , product id {}", orderId,productId);
            }
        }
        
    }
    
}
