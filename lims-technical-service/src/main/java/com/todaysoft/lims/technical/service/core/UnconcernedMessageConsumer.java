package com.todaysoft.lims.technical.service.core;

import org.springframework.stereotype.Component;

import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;
import com.todaysoft.lims.technical.ons.IMessageConsumer;


@Component
public class UnconcernedMessageConsumer implements IMessageConsumer
{
    @Override
    public void consume(Message message, ConsumeContext context)
    {
        // do nothing
    }
}
