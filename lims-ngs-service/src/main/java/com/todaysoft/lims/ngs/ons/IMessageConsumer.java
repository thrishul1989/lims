package com.todaysoft.lims.ngs.ons;

import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;

public interface IMessageConsumer
{
    void consume(Message message, ConsumeContext context);
}
