package com.todaysoft.lims.testing.listener;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.MessageListener;
import com.google.gson.Gson;
import com.todaysoft.lims.testing.base.config.RootContext;
import com.todaysoft.lims.testing.base.model.TestingStartRecord;
import com.todaysoft.lims.testing.base.request.StartOrderTestingRequest;
import com.todaysoft.lims.testing.base.service.ITestingResolveService;
import com.todaysoft.lims.testing.base.service.ITestingScheduleService;
import com.todaysoft.lims.testing.listener.model.OrderCosumerEvent;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.StringUtils;

public class OrderAutoStartConsumer implements MessageListener
{
    private static Logger log = LoggerFactory.getLogger(OrderAutoStartConsumer.class);
    
    /* @Autowired
     private ITestingResolveService resolveService;*/
    
    /* @Autowired
     private ITestingScheduleService scheduleService;*/
    
    private ITestingResolveService resolveService = RootContext.getBean(ITestingResolveService.class);
    
    private ITestingScheduleService scheduleService = RootContext.getBean(ITestingScheduleService.class);
    
    @Override
    public Action consume(Message message, ConsumeContext context)
    {
        
        if (log.isDebugEnabled())
        {
            log.debug("Start to consume message, topic {}, tag {}, id {}, key {}.", message.getTopic(), message.getTag(), message.getMsgID(), message.getKey());
        }
        
        String json = new String(message.getBody());
        
        if (log.isDebugEnabled())
        {
            log.debug("Message body as json {}.", json);
        }
        
        OrderCosumerEvent event = new Gson().fromJson(json, OrderCosumerEvent.class);
        
        String orderId = event.getOrderId();
        
        if (StringUtils.isEmpty(orderId))
        {
            log.debug("event id {}.", orderId);
            
            return Action.CommitMessage;
        }
        
        if (log.isDebugEnabled())
        {
            log.debug("订单消费主键 {}.", orderId);
        }
        
        StartOrderTestingRequest request = new StartOrderTestingRequest();
        request.setId(orderId);
        
        List<TestingStartRecord> records = resolveService.resolve(request, 1);
        if (Collections3.isNotEmpty(records))
        {
            scheduleService.start(records, 1);
        }
        else
        {
            log.warn("Order {} start record is empty.", request.getId());
        }
        
        return Action.CommitMessage;
    }
}
