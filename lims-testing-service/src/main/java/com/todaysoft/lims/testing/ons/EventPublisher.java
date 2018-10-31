package com.todaysoft.lims.testing.ons;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.Producer;
import com.google.gson.Gson;
import com.todaysoft.lims.testing.base.config.Configs;
import com.todaysoft.lims.testing.listener.model.OrderCosumerEvent;
import com.todaysoft.lims.utils.StringUtils;

@Component
@Lazy(false)
public class EventPublisher
{
    private static Logger log = LoggerFactory.getLogger(EventPublisher.class);
    
    private static final String EVENT_ORDER_CANCEL = "ORDER_STATUS_CANCEL";
    
    private static final String EVENT_ORDER_FINISH = "ORDER_STATUS_FINISH";
    
    @Autowired
    private Configs configs;
    
    @Resource(name = "producer")
    private Producer producer;
    
    /**
     * 订单--产品--取消
     */
    public void fireOrderIsCancel(String orderId)
    {
        if (StringUtils.isEmpty(orderId))
        {
            throw new IllegalArgumentException();
        }
        
        OrderCosumerEvent event = new OrderCosumerEvent();
        event.setOrderId(orderId);
        
        Message msg = new Message(configs.getAliyunONSTopic(), EVENT_ORDER_CANCEL, new Gson().toJson(event).getBytes());
        producer.send(msg);
        log.info("发送ORDER_STATUS_CANCEL消息...");
        
    }
    
    /**
     * 订单--产品--完成
     */
    public void fireOrderIsFinish(String orderId)
    {
        if (StringUtils.isEmpty(orderId))
        {
            throw new IllegalArgumentException();
        }
        
        OrderCosumerEvent event = new OrderCosumerEvent();
        event.setOrderId(orderId);
        
        Message msg = new Message(configs.getAliyunONSTopic(), EVENT_ORDER_FINISH, new Gson().toJson(event).getBytes());
        producer.send(msg);
        
    }
    
}
