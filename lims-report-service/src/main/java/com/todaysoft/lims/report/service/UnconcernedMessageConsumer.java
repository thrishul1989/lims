package com.todaysoft.lims.report.service;

import org.springframework.stereotype.Component;

import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;
import com.todaysoft.lims.report.ons.IMessageConsumer;


@Component
public class UnconcernedMessageConsumer implements IMessageConsumer
{
    @Override
    public void consume(Message message, ConsumeContext context)
    {
        // do nothing
    }
}
