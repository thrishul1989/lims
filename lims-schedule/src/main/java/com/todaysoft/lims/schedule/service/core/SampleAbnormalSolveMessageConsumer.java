package com.todaysoft.lims.schedule.service.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.todaysoft.lims.schedule.ons.AbstractMessageConsumer;
import com.todaysoft.lims.schedule.service.IOrderService;
import com.todaysoft.lims.schedule.service.ISchedulePlanService;
import com.todaysoft.lims.schedule.service.core.event.SampleAbnormalSolveEvent;

@Component
public class SampleAbnormalSolveMessageConsumer extends AbstractMessageConsumer<SampleAbnormalSolveEvent>
{
    @Autowired
    private IOrderService orderService;
    
    @Autowired
    private ISchedulePlanService schedulePlanService;
    
    @Override
    protected void handle(SampleAbnormalSolveEvent event)
    {
        if (log.isDebugEnabled())
        {
            log.debug("Start to handle the sample abnormal solve event, abnormal sample {}, strategy {}, resend sample {}.",
                event.getAbnormalSampleId(),
                event.getStrategy(),
                event.getResendSampleId());
        }
        
        String orderId = orderService.getOrderIdBySampleId(event.getAbnormalSampleId());
        
        synchronized (this)
        {
            // 针对未计划的历史订单，创建订单计划
            if (!orderService.isOrderPlanned(orderId))
            {
                log.warn("Order plan is not generated, start to generate the plan first, order id {}", orderId);
                schedulePlanService.generateOrderSchedulePlan(orderId);
            }
            
            if ("1".equals(event.getStrategy()))
            {
                // 重新送样-不更新收样、入库计划
                if (log.isDebugEnabled())
                {
                    log.debug("Sample abnormal solve strategy is resend, ignore the update plan operation, abnormal sample {},", event.getAbnormalSampleId());
                }
            }
            else
            {
                // 不送样
                boolean sampleReceiveFinished = orderService.isSampleReceiveFinished(orderId);
                
                if (sampleReceiveFinished)
                {
                    if (log.isDebugEnabled())
                    {
                        log.debug("Ignore the abnormal sample, other order samples is all received, start to update the plan, order id {}", orderId);
                    }
                    
                    schedulePlanService.updatePlanForSampleReceiveFinished(orderId);
                    
                    boolean sampleStorageFinished = orderService.isSampleStorageFinished(orderId);
                    
                    if (sampleStorageFinished)
                    {
                        if (log.isDebugEnabled())
                        {
                            log.debug("Ignore the abnormal sample, other order samples is all storaged, start to update the plan, order id {}", orderId);
                        }
                        
                        schedulePlanService.updatePlanForSampleStorageFinished(orderId);
                    }
                    else
                    {
                        if (log.isDebugEnabled())
                        {
                            log.debug("Ignore the abnormal sample, other order samples is not all storaged, ignore the update plan operation, order id {}",
                                orderId);
                        }
                    }
                }
                else
                {
                    if (log.isDebugEnabled())
                    {
                        log.debug("Ignore the abnormal sample, other order samples is not all received, ignore the update plan operation, order id {}",
                            orderId);
                    }
                }
            }
            
            if (log.isDebugEnabled())
            {
                log.debug("Handle the sample abnormal solve event, abnormal sample {}, strategy {}, resend sample {}.",
                    event.getAbnormalSampleId(),
                    event.getStrategy(),
                    event.getResendSampleId());
            }
        }
    }
    
}
