package com.todaysoft.lims.testing.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.MessageListener;
import com.google.gson.Gson;
import com.todaysoft.lims.testing.base.config.RootContext;
import com.todaysoft.lims.testing.listener.model.SampleStoragedEvent;
import com.todaysoft.lims.testing.listener.service.ISampleEventService;

@Component("sampleStoragedListener")
public class SampleStoragedListener implements MessageListener
{
    private static Logger log = LoggerFactory.getLogger(SampleStoragedListener.class);
    
    private ISampleEventService service = RootContext.getBean(ISampleEventService.class);
    
    @Override
    public Action consume(Message message, ConsumeContext context)
    {
        if (log.isDebugEnabled())
        {
            log.debug("Start to consume sample storaged message.");
        }
        
        String json = new String(message.getBody());
        SampleStoragedEvent event = new Gson().fromJson(json, SampleStoragedEvent.class);
        service.storaged(event);
        
        if (log.isDebugEnabled())
        {
            log.debug("Consume sample storaged message successful.");
        }
        
        return Action.CommitMessage;
    }
}
