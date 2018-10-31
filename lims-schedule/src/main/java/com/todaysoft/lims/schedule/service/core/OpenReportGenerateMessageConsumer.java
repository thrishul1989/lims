package com.todaysoft.lims.schedule.service.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.todaysoft.lims.schedule.ons.AbstractMessageConsumer;
import com.todaysoft.lims.schedule.service.IReportGenerateService;
import com.todaysoft.lims.schedule.service.core.event.OpenReportGenerateEvent;

@Component
public class OpenReportGenerateMessageConsumer extends AbstractMessageConsumer<OpenReportGenerateEvent>
{
    @Autowired
    private IReportGenerateService reportGenerateService;
    
    @Override
    protected void handle(OpenReportGenerateEvent event)
    {
        if (log.isDebugEnabled())
        {
            log.debug("Start to handle the open report generate event, orderId {} ,productId {}", event.getOrderId(),event.getProductId());
        }
        synchronized (this)
        {
            log.info("Received handle the open report generate plan, orderId {} ,productId {}", event.getOrderId(),event.getProductId());

            reportGenerateService.openReportGenerate(event.getOrderId(),event.getProductId());

            if (log.isDebugEnabled())
            {
                log.debug("Handle the open report generate event successful, orderId {} ,productId {}", event.getOrderId(),event.getProductId());
            }
        }
    }
    
}
