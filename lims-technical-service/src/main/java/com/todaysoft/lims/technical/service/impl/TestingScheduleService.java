package com.todaysoft.lims.technical.service.impl;

import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.Producer;
import com.aliyun.openservices.ons.api.SendResult;
import com.google.gson.Gson;
import com.todaysoft.lims.technical.config.Configs;
import com.todaysoft.lims.technical.mybatis.entity.TestingSample;
import com.todaysoft.lims.technical.mybatis.entity.TestingTask;
import com.todaysoft.lims.technical.service.ITestingScheduleService;
import com.todaysoft.lims.technical.service.core.event.OrderTestingStartEvent;
import com.todaysoft.lims.technical.service.core.event.UpdateRedundantEvent;

@Service
public class TestingScheduleService implements ITestingScheduleService
{
    @Autowired
    private Configs configs;
    
    @Resource(name = "producer")
    private Producer producer;
    
    private static final Logger logger = LoggerFactory.getLogger(TestingScheduleService.class);
    
    @Override
    public void sendOrderVerifyTestingStartMessage(List<String> scheduleIds, Set<String> orderIds)
    {
        
        OrderTestingStartEvent event = new OrderTestingStartEvent();
        event.setOrderIds(orderIds);
        event.setScheduleIds(scheduleIds);
        Message msg = new Message(configs.getAliyunONSTopic(), "ORDER_VERIFY_SCHEDULE_START", new Gson().toJson(event).getBytes());
        SendResult sendResult = producer.send(msg);
        
    }
    
    @Override
    public void updateRedundantTask(List<TestingTask> tasks)
    {
        
        for (TestingTask task : tasks)
        {
            TestingSample sample = new TestingSample();
            sample.setId(task.getInputSampleId());
            task.setInputSample(sample);
            logger.info("需要更新的任务：" + task);
        }
        UpdateRedundantEvent event = new UpdateRedundantEvent();
        event.setList(tasks);
        Message msg = new Message(configs.getAliyunONSTopic(), "TASK_REDUNDANT", new Gson().toJson(event).getBytes());
        SendResult sendResult = producer.send(msg);
        logger.info("step2:更新冗余字段成功！！！" + sendResult.getMessageId());
        
    }
    
}
