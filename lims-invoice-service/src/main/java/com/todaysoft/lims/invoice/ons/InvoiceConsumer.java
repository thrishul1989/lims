package com.todaysoft.lims.invoice.ons;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.MessageListener;
import com.google.gson.Gson;
import com.todaysoft.lims.invoice.service.IInvoiceService;

public class InvoiceConsumer implements MessageListener
{
    public static final String TAG_APPLY = "INVOICE_APPLY";
    
    private static Logger log = LoggerFactory.getLogger(InvoiceConsumer.class);
    
    private IInvoiceService service;
    
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
            
            if (TAG_APPLY.equalsIgnoreCase(tag))
            {
                if (log.isDebugEnabled())
                {
                    log.debug("Start to consume the message as invoice apply.");
                }
                
                InvoiceApplyEvent event = new Gson().fromJson(json, InvoiceApplyEvent.class);
                service.apply(event);
                
                if (log.isDebugEnabled())
                {
                    log.debug("Consume the message as invoice apply successful.");
                }
                
                return Action.CommitMessage;
            }
            else
            {
                return Action.ReconsumeLater;
            }
        }
        catch (Exception e)
        {
            log.error("Consume message error.", e);
            return Action.ReconsumeLater;
        }
    }
}
