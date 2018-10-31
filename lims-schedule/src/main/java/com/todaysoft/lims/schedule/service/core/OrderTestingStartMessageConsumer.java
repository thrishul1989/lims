package com.todaysoft.lims.schedule.service.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.todaysoft.lims.schedule.entity.OrderPlanTask;
import com.todaysoft.lims.schedule.ons.AbstractMessageConsumer;
import com.todaysoft.lims.schedule.service.ISchedulePlanService;
import com.todaysoft.lims.schedule.service.core.event.OrderTestingStartEvent;

@Component
public class OrderTestingStartMessageConsumer extends AbstractMessageConsumer<OrderTestingStartEvent>
{
    @Autowired
    private ISchedulePlanService schedulePlanService;
    
    @Override
    protected void handle(OrderTestingStartEvent event)
    {
        if (log.isDebugEnabled())
        {
            log.debug("Start to handle the order testing start event, order id {}", event.getOrderId());
        }
        
        synchronized (this)
        {
            if (!schedulePlanService.getIsFinished(event.getOrderId(), OrderPlanTask.SEMANTIC_SAMPLE_STORAGE)
                || !schedulePlanService.getIsFinished(event.getOrderId(), OrderPlanTask.SEMANTIC_PAYMENT_CONFIRM))
            {
                log.debug("The order samples storage  or order payment is not finished, order id {}", event.getOrderId());
                throw new RuntimeException("转存入库或者财务下放没完成！");
            }
            
            schedulePlanService.refineOrderSchedulePlan(event.getOrderId(), event.getScheduleIds()); //增加流程IDS ，对指定的流程创建监控任务
            
            if (log.isDebugEnabled())
            {
                log.debug("Refine the order schedule plan successful, order is {}", event.getOrderId());
            }
            
            schedulePlanService.updatePlanForOrderTestingStart(event.getOrderId());
            
            //判断orderId下所有的任务是否已经填写计划完成时间，如果计划完成时间没有则补充下
            log.info("订单id：" + event.getOrderId() + "，回填task的计划完成时间，开始");
            schedulePlanService.updateTaskPlannedFinishDate(event.getOrderId());
            log.info("订单id：" + event.getOrderId() + "，回填task的计划完成时间，结束");
            if (log.isDebugEnabled())
            {
                log.debug("Handle the order testing start event successful, order id {}", event.getOrderId());
            }
            
        }
    }
}
