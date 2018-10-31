package com.todaysoft.lims.report.service.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.todaysoft.lims.report.ons.AbstractMessageConsumer;
import com.todaysoft.lims.report.service.ITestingDataSendService;
import com.todaysoft.lims.report.service.core.event.DataSendingEvent;

@Component
public class DataSendingMessageConsumer extends AbstractMessageConsumer<DataSendingEvent>
{
    
    @Autowired
    private ITestingDataSendService dataSendService;
    
    @Override
    protected void handle(DataSendingEvent event)
    {
        if (log.isDebugEnabled())
        {
            log.debug("Start to handle the data sending, order id {}", event.getOrderId());
        }
        
        synchronized (this)
        {
            
            dataSendService.createDataSend(event.getOrderId());
            
            if (log.isDebugEnabled())
            {
                log.debug("Handle the data sending successful, order id {}", event.getOrderId());
            }
            
        }
    }
    
}
