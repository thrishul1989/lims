package com.todaysoft.lims.schedule.service.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.todaysoft.lims.schedule.ons.AbstractMessageConsumer;
import com.todaysoft.lims.schedule.service.ITestingScheduleService;
import com.todaysoft.lims.schedule.service.core.event.StatusSearchCancelEvent;

@Component
public class StatusSearchCancelMessageConsumer extends AbstractMessageConsumer<StatusSearchCancelEvent>
{

    @Autowired
    private ITestingScheduleService testingScheduleService;
    
    @Override
    protected void handle(StatusSearchCancelEvent event)
    {
        if (log.isDebugEnabled())
        {
            log.debug("Start to handle the status search cancel event, scheduleId {}", event.getScheduleId());
        }
        String scheduleId = event.getScheduleId();

        synchronized (this)
        {
            log.info("Received handle the status search cancel plan, scheduleId {}", event.getScheduleId());

            testingScheduleService.updateStatusSearchCancel(scheduleId);

            if (log.isDebugEnabled())
            {
                log.debug("Handle the status search cancel event successful, scheduleId {}", scheduleId);
            }
        }
        
    }
    
}
