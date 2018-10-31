package com.todaysoft.lims.report.service.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.todaysoft.lims.report.ons.AbstractMessageConsumer;
import com.todaysoft.lims.report.service.IConcludingReportService;
import com.todaysoft.lims.report.service.core.event.ConcludingReportEvent;

@Component
public class ConcludingReportMessageConsumer extends AbstractMessageConsumer<ConcludingReportEvent>
{
    
    @Autowired
    private IConcludingReportService service;
    
    @Override
    protected void handle(ConcludingReportEvent event)
    {
        if (log.isDebugEnabled())
        {
            log.debug("Start to handle the concluding report, order id {}", event.getOrderId());
        }
        
        synchronized (this)
        {
            
            service.createConcludingReport(event.getOrderId());
            
            if (log.isDebugEnabled())
            {
                log.debug("Handle the concluding report successful, order id {}", event.getOrderId());
            }
            
        }
        
    }
    
}
