package com.todaysoft.lims.schedule.service.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.todaysoft.lims.schedule.ons.AbstractMessageConsumer;
import com.todaysoft.lims.schedule.service.IProgramMonitorNewBiologyService;
import com.todaysoft.lims.schedule.service.core.event.ProgramMonitorNewBiologyEvent;
@Component
public class ProgramMonitorNewBiologyConsumer extends AbstractMessageConsumer<ProgramMonitorNewBiologyEvent>
{

    @Autowired
    private IProgramMonitorNewBiologyService programMonitorNewBiologyService;
    @Override
    protected void handle(ProgramMonitorNewBiologyEvent event)
    {
        if (log.isDebugEnabled())
        {
            log.debug("Start to handle the ProgramMonitorNewBiologyEvent {}", event);
        }

        synchronized (this)
        {
            log.info("Received handle the ProgramMonitorNewBiology, {}", event);

            programMonitorNewBiologyService.updatePlan(event);

            if (log.isDebugEnabled())
            {
                log.debug("Finish handle the ProgramMonitorNewBiologyEvent, {}", event);
            }
        }
    }
}
