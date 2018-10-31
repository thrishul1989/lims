package com.todaysoft.lims.schedule.service.core;

import com.todaysoft.lims.schedule.service.ITestingScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.todaysoft.lims.schedule.ons.AbstractMessageConsumer;
import com.todaysoft.lims.schedule.service.core.event.OrderTestingStartEvent;
import com.todaysoft.lims.schedule.service.core.event.SubmitSheetEvent;
@Component
public class SubmitSheetMessageConsumer extends AbstractMessageConsumer<SubmitSheetEvent>
{

    @Autowired
    private ITestingScheduleService testingScheduleService;
    @Override
    protected void handle(SubmitSheetEvent event)
    {
        if (log.isDebugEnabled())
        {
            log.debug("Start to handle the sheet submit event, sheetId {}", event.getSheetId());
        }
        String sheetId = event.getSheetId();

        synchronized (this)
        {
            log.info("Received handle the testing plan, sheetId {}", event.getSheetId());

            testingScheduleService.updatePlanWithSheetSubmit(sheetId);

            if (log.isDebugEnabled())
            {
                log.debug("Handle the sheet submit event successful, sheetId {}", sheetId);
            }
        }
    }
}
