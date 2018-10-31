package com.todaysoft.lims.schedule.service.correction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.todaysoft.lims.schedule.service.IOrderService;
import com.todaysoft.lims.schedule.service.ISchedulePlanService;
import com.todaysoft.lims.utils.StringUtils;

@Component
public class OrderPrimaryPlanMissingCorrectionHandler extends AbstractCorrectionHandler
{
    private static Logger log = LoggerFactory.getLogger(OrderPrimaryPlanMissingCorrectionHandler.class);
    
    @Autowired
    private IOrderService orderService;
    
    @Autowired
    private ISchedulePlanService schedulePlanService;
    
    @Override
    public boolean isCorrectRequired(ScheduleKeyHolder keyHolder)
    {
        if (StringUtils.isEmpty(keyHolder.getOrderId()))
        {
            log.error("Schedule key holder is invalid, order id is empty, ignore the operation.");
            return false;
        }
        
        return !orderService.isOrderPlanned(keyHolder.getOrderId());
    }
    
    @Override
    protected void doCorrect(ScheduleKeyHolder keyHolder)
    {
        schedulePlanService.generateOrderSchedulePlan(keyHolder.getOrderId());
    }
}
