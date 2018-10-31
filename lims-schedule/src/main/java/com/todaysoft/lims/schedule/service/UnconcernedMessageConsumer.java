package com.todaysoft.lims.schedule.service;

import org.springframework.stereotype.Component;

import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;
import com.todaysoft.lims.schedule.ons.IMessageConsumer;

@Component
public class UnconcernedMessageConsumer implements IMessageConsumer
{
    @Override
    public void consume(Message message, ConsumeContext context)
    {
        // do nothing
    }
}
