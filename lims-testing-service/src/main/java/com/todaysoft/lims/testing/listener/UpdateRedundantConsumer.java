package com.todaysoft.lims.testing.listener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.druid.support.json.JSONUtils;
import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.MessageListener;
import com.google.gson.Gson;
import com.todaysoft.lims.testing.base.config.RootContext;
import com.todaysoft.lims.testing.base.entity.TestingTask;
import com.todaysoft.lims.testing.base.service.ITestingTaskService;
import com.todaysoft.lims.testing.base.utils.JsonUtils;
import com.todaysoft.lims.testing.listener.model.SampleStoragedEvent;
import com.todaysoft.lims.testing.listener.service.ISampleEventService;
import com.todaysoft.lims.testing.ons.event.UpdateRedundantEvent;

@Component("UpdateRedundantListener")
public class UpdateRedundantConsumer implements MessageListener
{
    
    private static Logger log = LoggerFactory.getLogger(UpdateRedundantConsumer.class);
    
    @Autowired
    
    private ITestingTaskService testingTaskService = RootContext.getBean(ITestingTaskService.class);
    
    @Override
    public Action consume(Message message, ConsumeContext context)
    {
        if (log.isDebugEnabled())
        {
            log.debug("Start to consume UpdateRedundantListener message.");
        }
        
        String json = new String(message.getBody());
        UpdateRedundantEvent event = new Gson().fromJson(json, UpdateRedundantEvent.class);
        
        testingTaskService.updateTaskRedundantColumn(event.getList(), 0);
        
        if (log.isDebugEnabled())
        {
            log.debug("Consume sample storaged message successful.");
        }
        
        return Action.CommitMessage;
    }
}
