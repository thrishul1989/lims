package com.todaysoft.lims.schedule.service.core;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.todaysoft.lims.schedule.ons.AbstractMessageConsumer;
import com.todaysoft.lims.schedule.service.ISchedulePlanService;
import com.todaysoft.lims.schedule.service.core.event.OrderTestingStartEvent;
import com.todaysoft.lims.utils.Collections3;

@Component
public class OrderVerifyStartMessageConsumer extends AbstractMessageConsumer<OrderTestingStartEvent>
{
    @Autowired
    private ISchedulePlanService schedulePlanService;
    
    /**
     * tag :ORDER_VERIFY_SCHEDULE_START
     */
    @Override
    protected void handle(OrderTestingStartEvent event)
    {
        if (log.isDebugEnabled())
        {
            log.debug("Start to handle the verify schedule start event, order id {}", event.getOrderId());
        }
        
        synchronized (this)
        {
            
            schedulePlanService.refineVerifySchedulePlan(event.getScheduleIds());
            
            if (log.isDebugEnabled())
            {
                log.debug("Refine the order schedule plan successful, order is {}", event.getOrderId());
            }
            
            if (Collections3.isNotEmpty(event.getScheduleIds()))
            {//验证流程
                for (String scheduleId : event.getScheduleIds())
                {
                    schedulePlanService.updateVerifyTestingStart(scheduleId);
                }
                
            }
            
            log.info("订单id："+event.getOrderId()+"，回填task的计划完成时间，开始");
            schedulePlanService.updateTaskPlannedFinishDate(event.getScheduleIds());
            log.info("订单id："+event.getOrderId()+"，回填task的计划完成时间，结束");
            
            if (log.isDebugEnabled())
            {
                log.debug("Update the order schedule plan for testing start successful, order id {}", event.getOrderId());
            }
            
            if (log.isDebugEnabled())
            {
                log.debug("Handle the order testing start event successful, order id {}", event.getOrderId());
            }
        }
        
    }
    
}
