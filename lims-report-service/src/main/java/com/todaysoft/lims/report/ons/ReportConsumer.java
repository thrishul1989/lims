package com.todaysoft.lims.report.ons;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Consumer;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.MessageListener;
import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.PropertyKeyConst;
import com.todaysoft.lims.report.config.Configs;
import com.todaysoft.lims.report.service.MessageConsumerFactory;
import com.todaysoft.lims.utils.StringUtils;

@Component
@Lazy(false)
public class ReportConsumer implements MessageListener
{
    private static Logger log = LoggerFactory.getLogger(ReportConsumer.class);
    
    public static final String TAG_DATA_SENDING = "DATA_SENDING";
    
    public static final String TAG_CONCLUDING_REPORT = "CONCLUDING_REPORT";
    
    public static final String TAG_EXTRA_SEND_REPORT = "EXTRA_SEND_REPORT";
    
    @Autowired
    private Configs configs;
    
    @Autowired
    private MessageConsumerFactory messageConsumerFactory;
    
    private Consumer consumer;
    
    @Override
    public Action consume(Message message, ConsumeContext context)
    {
        try
        {
            IMessageConsumer consumer = messageConsumerFactory.getConsumer(message, context);
            consumer.consume(message, context);
            return Action.CommitMessage;
        }
        catch (Exception e)
        {
            log.error("Consume message error.", e);
            return Action.ReconsumeLater;
        }
    }
    
    @PostConstruct
    private void register()
    {
        Properties properties = new Properties();
        properties.put(PropertyKeyConst.AccessKey, configs.getAliyunONSAccessKey());
        properties.put(PropertyKeyConst.SecretKey, configs.getAliyunONSSecretKey());
        properties.put(PropertyKeyConst.ConsumerId, configs.getAliyunONSReportConsumer());
        consumer = ONSFactory.createConsumer(properties);
        
        List<String> tags = new ArrayList<String>();
        tags.add(TAG_DATA_SENDING);
        tags.add(TAG_CONCLUDING_REPORT);
        tags.add(TAG_EXTRA_SEND_REPORT);
        String expression = StringUtils.join(tags, "||");
        
        consumer.subscribe(configs.getAliyunONSTopic(), expression, this);
        consumer.start();
        
        if (log.isDebugEnabled())
        {
            log.debug("Start aliyun ons consumer success, subscribe topic {}, expression {}, consumer id {}",
                configs.getAliyunONSTopic(),
                tags,
                configs.getAliyunONSReportConsumer());
        }
    }
    
    public Consumer getConsumer()
    {
        return consumer;
    }
}
