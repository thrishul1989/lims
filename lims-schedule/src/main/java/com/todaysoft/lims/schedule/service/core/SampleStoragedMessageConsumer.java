package com.todaysoft.lims.schedule.service.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.todaysoft.lims.schedule.ons.AbstractMessageConsumer;
import com.todaysoft.lims.schedule.service.IOrderService;
import com.todaysoft.lims.schedule.service.ISchedulePlanService;
import com.todaysoft.lims.schedule.service.core.event.SampleStoragedEvent;
import com.todaysoft.lims.utils.StringUtils;

@Component
public class SampleStoragedMessageConsumer extends AbstractMessageConsumer<SampleStoragedEvent>
{
    @Autowired
    private IOrderService orderService;
    
    @Autowired
    private ISchedulePlanService schedulePlanService;
    
    @Override
    protected void handle(SampleStoragedEvent event)
    {
        if (log.isDebugEnabled())
        {
            log.debug("Start to handle the sample storaged event, sample code {}, storaging type {}.", event.getSampleCode(), event.getStoragingType());
        }
        
        // 临存入库不做处理
        if ("1".equals(event.getStoragingType()))
        {
            if (log.isDebugEnabled())
            {
                log.debug("Storaged type is temporary, ignore the event.");
            }
            
            return;
        }
        
        String orderId = orderService.getOrderIdBySampleCode(event.getSampleCode());
        
        if (StringUtils.isEmpty(orderId))
        {
            log.error("Can not found order id by sample code {}", event.getSampleCode());
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
            
            boolean finished = orderService.isSampleStorageFinished(orderId);
            
            if (finished)
            {
                if (log.isDebugEnabled())
                {
                    log.debug("Order samples is all storaged, start to update the plan, order id {}", orderId);
                }
                
                schedulePlanService.updatePlanForSampleStorageFinished(orderId);
            }
            else
            {
                if (log.isDebugEnabled())
                {
                    log.debug("Order samples is not all storaged, ignore the update plan operation, order id {}", orderId);
                }
            }
            
            if (log.isDebugEnabled())
            {
                log.debug("Handle the sample storaged event successful, sample code {}", event.getSampleCode());
            }
        }
    }
}
