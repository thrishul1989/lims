package com.todaysoft.lims.schedule.service.core;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.todaysoft.lims.schedule.ons.AbstractMessageConsumer;
import com.todaysoft.lims.schedule.service.IOrderService;
import com.todaysoft.lims.schedule.service.IReportGenerateService;
import com.todaysoft.lims.schedule.service.ISchedulePlanService;
import com.todaysoft.lims.schedule.service.core.event.ReportGenerateEvent;
import com.todaysoft.lims.utils.Collections3;

@Component
public class ReportGenerateMessageConsumer extends AbstractMessageConsumer<ReportGenerateEvent>
{
    
    @Autowired
    private IOrderService orderService;
    
    @Autowired
    private IReportGenerateService service;
    
    @Autowired
    private ISchedulePlanService schedulePlanService;
    
    @Override
    protected void handle(ReportGenerateEvent event)
    {
        List<Map<String,String>> orderProductIds = event.getOrderProductIds();
        if (Collections3.isNotEmpty(orderProductIds))
        {
            for (Map<String,String> orderProductId : orderProductIds)
            {
                String orderId = orderProductId.get("orderId");
                String productId = orderProductId.get("productId");
                if (log.isDebugEnabled())
                {
                    log.debug("Start to handle the order report generate event, order id {}, product id {}", orderId,productId);
                }
                
                synchronized (this)
                {
                    // 针对未计划的历史订单，创建订单计划
                    if (!orderService.isOrderPlanned(orderId))
                    {
                        log.warn("Order plan is not generated, start to generate the plan first, order id {}", orderId);
                        schedulePlanService.generateOrderSchedulePlan(orderId);
                    }
                    
                    schedulePlanService.updatePlanForReportGenerateFinished(orderId,productId);
                    
                    if (log.isDebugEnabled())
                    {
                        log.debug("Handle the order report generate event successful, order id {}, product id {}", orderId,productId);
                    }
                }
            }
        }
    }
    
}
