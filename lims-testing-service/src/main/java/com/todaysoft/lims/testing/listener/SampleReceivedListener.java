package com.todaysoft.lims.testing.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.MessageListener;
import com.google.gson.Gson;
import com.todaysoft.lims.testing.base.config.RootContext;
import com.todaysoft.lims.testing.listener.model.SampleReceivedEvent;
import com.todaysoft.lims.testing.listener.service.ISampleEventService;

public class SampleReceivedListener implements MessageListener
{
    private static Logger log = LoggerFactory.getLogger(SampleReceivedListener.class);
    
    private ISampleEventService service = RootContext.getBean(ISampleEventService.class);
    
    @Override
    public Action consume(Message message, ConsumeContext context)
    {
        if (log.isDebugEnabled())
        {
            log.debug("Start to consume sample received message.");
        }
        
        String json = new String(message.getBody());
        SampleReceivedEvent event = new Gson().fromJson(json, SampleReceivedEvent.class);
        service.received(event);
        
        if (log.isDebugEnabled())
        {
            log.debug("Consume sample received message successful.");
        }
        
        return Action.CommitMessage;
    }
}
