package com.todaysoft.lims.report.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;
import com.todaysoft.lims.report.ons.IMessageConsumer;
import com.todaysoft.lims.report.ons.ReportConsumer;
import com.todaysoft.lims.report.service.core.ConcludingReportMessageConsumer;
import com.todaysoft.lims.report.service.core.DataSendingMessageConsumer;
import com.todaysoft.lims.report.service.core.ExtraSendReportMessageConsumer;

@Component
public class MessageConsumerFactory
{
    private static Logger log = LoggerFactory.getLogger(MessageConsumerFactory.class);
    
    @Autowired
    private DataSendingMessageConsumer dataSendingEventHandler;
    
    @Autowired
    private ConcludingReportMessageConsumer concludingReportEventHandler;
    
    @Autowired
    private ExtraSendReportMessageConsumer extraSendReportEventHandler;
    
    @Autowired
    private UnconcernedMessageConsumer unconcernedEventHandler;
    
    public IMessageConsumer getConsumer(Message message, ConsumeContext context)
    {
        String tag = message.getTag();
        
        if (ReportConsumer.TAG_DATA_SENDING.equals(tag))
        {
            return dataSendingEventHandler;
        }
        else if (ReportConsumer.TAG_CONCLUDING_REPORT.equals(tag))
        {
            return concludingReportEventHandler;
        }
        else if (ReportConsumer.TAG_EXTRA_SEND_REPORT.equals(tag))
        {
            return extraSendReportEventHandler;
        }
        
        else
        {
            String body = new String(message.getBody());
            log.warn("Message is unconcerned, topic {}, tag {}, id {}, key {}, body {}.", message.getTopic(), tag, message.getMsgID(), message.getKey(), body);
            return unconcernedEventHandler;
        }
    }
}
