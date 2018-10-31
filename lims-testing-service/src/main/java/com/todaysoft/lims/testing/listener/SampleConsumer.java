package com.todaysoft.lims.testing.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.MessageListener;

public class SampleConsumer implements MessageListener
{
    private static Logger log = LoggerFactory.getLogger(SampleConsumer.class);
    
    private SampleReceivedListener receivedListener = new SampleReceivedListener();
    
    private SampleStoragedListener storagedListener = new SampleStoragedListener();
    
    private UpdateRedundantConsumer updateRedundantListener = new UpdateRedundantConsumer();
    
    @Override
    public Action consume(Message message, ConsumeContext context)
    {
        try
        {
            if (log.isDebugEnabled())
            {
                log.debug("Start to consume message, topic {}, tag {}, id {}, key {}.",
                    message.getTopic(),
                    message.getTag(),
                    message.getMsgID(),
                    message.getKey());
            }
            
            String json = new String(message.getBody());
            
            if (log.isDebugEnabled())
            {
                log.debug("Message body as json {}.", json);
            }
            
            String tag = message.getTag();
            
            if ("SAMPLE_RECEIVED".equalsIgnoreCase(tag))
            {
                return receivedListener.consume(message, context);
            }
            else if ("SAMPLE_STORAGED".equalsIgnoreCase(tag))
            {
                return storagedListener.consume(message, context);
            }
            else if ("TASK_REDUNDANT".equalsIgnoreCase(tag))
            {
                return updateRedundantListener.consume(message, context);
            }
            else
            {
                return Action.CommitMessage;
            }
        }
        catch (Exception e)
        {
            log.error("Consume message error.", e);
            return Action.ReconsumeLater;
        }
    }
}
