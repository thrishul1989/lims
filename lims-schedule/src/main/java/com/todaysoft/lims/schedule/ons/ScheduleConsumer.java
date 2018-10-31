package com.todaysoft.lims.schedule.ons;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
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
import com.todaysoft.lims.schedule.config.Configs;
import com.todaysoft.lims.schedule.service.MessageConsumerFactory;

@Component
@Lazy(false)
public class ScheduleConsumer implements MessageListener
{
    private static Logger log = LoggerFactory.getLogger(ScheduleConsumer.class);
    
    public static final String TAG_ORDER_SUBMIT = "ORDER_SUBMIT";
    
    public static final String TAG_ORDER_CANCEL = "ORDER_CANCEL";
    
    public static final String TAG_ORDER_MODIFY = "ORDER_MODIFY";
    
    public static final String TAG_ORDER_TESTING_START = "ORDER_TESTING_START";
    
    public static final String TAG_SAMPLE_RECEIVE = "SAMPLE_RECEIVE";
    
    public static final String TAG_SAMPLE_STORAGE = "SAMPLE_STORAGED";
    
    public static final String TAG_SAMPLE_ABNORMAL_SOLVE = "SAMPLE_ABNORMAL_SOLVE";
    
    public static final String TAG_ORDER_PAYMENT_CONFIRM = "ORDER_PAYMENT_CONFIRM";
    
    public static final String TAG_ORDER_PAYMENT_DELAY_AGREED = "ORDER_PAYMENT_DELAY_AGREED";
    
    public static final String TAG_ORDER_PAYMENT_REDUCE_AGREED = "ORDER_PAYMENT_REDUCE_AGREED";
    
    public static final String TAG_CONTRACT_ORDER_TESTING_CONFIRM = "CONTRACT_ORDER_TESTING_CONFIRM";
    
    public static final String TAG_ORDER_REPORT_GENERATE = "REPORT_GENERATE";
    
    public static final String TAG_ORDER_REPORT_VERIFY = "REPORT_VERIFY";
    
    public static final String TAG_ORDER_REPORT_DELIVER = "REPORT_DELIVER";
    
    public static final String TAG_SUBMIT_SHEET = "SUBMIT_SHEET";
    
    public static final String TAG_ORDER_VERIFY_SCHEDULE_START = "ORDER_VERIFY_SCHEDULE_START";
    
    public static final String TAG_ABNORMAL_SOLVE = "ABNORMAL_SOLVE";
    
    public static final String TAG_STATUS_SEARCH_CANCEL = "STATUS_SEARCH_CANCEL";
    
    public static final String TAG_OPEN_REPORT_GENERATE = "OPEN_REPORT_GENERATE";
    
    public static final String TAG_SCHEDULE_TASK_ACTIVE = "SCHEDULE_TASK_ACTIVE";// 流程任务激活消费
    
    public static final String TAG_PROGRAM_MONITOR_NEW_BIOLOGY = "PROGRAM_MONITOR_NEW_BIOLOGY";  //上机实验后项目监控
    
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
        properties.put(PropertyKeyConst.ConsumerId, configs.getAliyunONSScheduleConsumer());
        consumer = ONSFactory.createConsumer(properties);
        
        List<String> tags = new ArrayList<String>();
        tags.add(TAG_ORDER_SUBMIT);
        tags.add(TAG_ORDER_CANCEL);
        tags.add(TAG_ORDER_MODIFY);
        tags.add(TAG_ORDER_TESTING_START);
        tags.add(TAG_SAMPLE_RECEIVE);
        tags.add(TAG_SAMPLE_STORAGE);
        tags.add(TAG_SAMPLE_ABNORMAL_SOLVE);
        tags.add(TAG_ORDER_PAYMENT_CONFIRM);
        tags.add(TAG_ORDER_PAYMENT_DELAY_AGREED);
        tags.add(TAG_ORDER_PAYMENT_REDUCE_AGREED);
        tags.add(TAG_CONTRACT_ORDER_TESTING_CONFIRM);
        tags.add(TAG_ORDER_REPORT_GENERATE);
        tags.add(TAG_ORDER_REPORT_VERIFY);
        tags.add(TAG_ORDER_REPORT_DELIVER);
        tags.add(TAG_SUBMIT_SHEET);
        tags.add(TAG_ORDER_VERIFY_SCHEDULE_START);
        tags.add(TAG_ABNORMAL_SOLVE);
        tags.add(TAG_STATUS_SEARCH_CANCEL);
        tags.add(TAG_OPEN_REPORT_GENERATE);
        tags.add(TAG_SCHEDULE_TASK_ACTIVE);
        tags.add(TAG_PROGRAM_MONITOR_NEW_BIOLOGY);
        String expression = StringUtils.join(tags, "||");
        
        consumer.subscribe(configs.getAliyunONSTopic(), expression, this);
        consumer.start();
        
        if (log.isDebugEnabled())
        {
            log.debug("Start aliyun ons consumer success, subscribe topic {}, expression {}, consumer id {}",
                configs.getAliyunONSTopic(),
                tags,
                configs.getAliyunONSScheduleConsumer());
        }
    }
    
    public Consumer getConsumer()
    {
        return consumer;
    }
}
