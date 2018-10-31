package com.todaysoft.lims.biology.ons;

import java.lang.reflect.ParameterizedType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;

public abstract class AbstractMessageConsumer<T> implements IMessageConsumer
{
    protected static Logger log = LoggerFactory.getLogger(AbstractMessageConsumer.class);
    
    @Override
    public void consume(Message message, ConsumeContext context)
    {
        String tag = message.getTag();
        String body = new String(message.getBody());
        
        if (log.isDebugEnabled())
        {
            log.debug("Start to consume message, topic {}, tag {}, id {}, key {}, body {}.",
                message.getTopic(),
                tag,
                message.getMsgID(),
                message.getKey(),
                body);
        }
        
        T event = parseEvent(body);
        
        if (null == event)
        {
            log.error("Can not parse event object, event body {}", body);
            return;
        }
        
        handle(event);
    }
    
    protected abstract void handle(T event);
    
    @SuppressWarnings("unchecked")
    protected Class<T> getEventClass()
    {
        Class<T> entityClass = (Class<T>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        return entityClass;
    }
    
    protected T parseEvent(String body)
    {
        return ObjectMapperUtils.fromJson(body, getEventClass());
    }
}
