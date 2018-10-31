package com.todaysoft.lims.sample.ons;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.MessageListener;
import com.google.gson.Gson;
import com.todaysoft.lims.sample.config.RootContext;
import com.todaysoft.lims.sample.ons.event.OrderStatusCosumerEvent;
import com.todaysoft.lims.sample.service.IPaymentService;
import com.todaysoft.lims.utils.StringUtils;

public class OrderStatusConsumer implements MessageListener
{
    private static Logger log = LoggerFactory.getLogger(OrderStatusConsumer.class);
    
    private IPaymentService paymentService = RootContext.getBean(IPaymentService.class);
    
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
            
            OrderStatusCosumerEvent event = new Gson().fromJson(json, OrderStatusCosumerEvent.class);
            
            String orderId = event.getOrderId();
            
            if (StringUtils.isEmpty(orderId))
            {
                log.debug("event id {}.", orderId);
                
                return Action.CommitMessage;
            }
            
            if (log.isDebugEnabled())
            {
                log.debug("order comuser id {}.", orderId);
            }
            String tag = message.getTag();
            
            if ("ORDER_STATUS_CANCEL".equalsIgnoreCase(tag))
            {
                paymentService.checkOrderIsCancel(orderId);
                
            }
            else if ("ORDER_STATUS_FINISH".equalsIgnoreCase(tag))
            {
                paymentService.checkOrderFinish(orderId);
            }
            
        }
        catch (Exception e)
        {
            log.error("Consume message error.", e);
            return Action.ReconsumeLater;
        }
        return Action.CommitMessage;
        
    }
}
