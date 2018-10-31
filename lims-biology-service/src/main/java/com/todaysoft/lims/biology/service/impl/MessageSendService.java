package com.todaysoft.lims.biology.service.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.Producer;
import com.google.gson.Gson;
import com.todaysoft.lims.biology.config.Configs;
import com.todaysoft.lims.biology.model.OrderIdModel;
import com.todaysoft.lims.biology.service.IMessageSendService;
import com.todaysoft.lims.biology.service.core.event.ScheduleTaskActiveEvent;
import com.todaysoft.lims.biology.service.core.event.TechnicalCreateEvent;

@Service
public class MessageSendService implements IMessageSendService
{
    
    @Autowired
    private Configs configs;
    
    @Resource(name = "producer")
    private Producer producer;
    
    private static Logger log = LoggerFactory.getLogger(MessageSendService.class);
    
    public static final String TAG_SCHEDULE_TASK_ACTIVE = "SCHEDULE_TASK_ACTIVE";// 流程任务激活
    
    public static final String TAG_BIOLOGY_ANALYSIS_COMMENT = "BIOLOGY_ANALYSIS_COMMENT";// 旧的生信分析任务生成
    
    public static final String TAG_EXTRA_SEND_REPORT = "EXTRA_SEND_REPORT";// 数据发送
    
    public static final String TAG_CONCLUDING_REPORT = "CONCLUDING_REPORT";// 结题报告
    
    public static final String TAG_TECHNICAL_TASK = "TECHNICAL_CREATE_MESSAGE";// 生成技术分析任务
    
    public static final String TAG_PROGRAM_MONITOR_NEW_BIOLOGY = "PROGRAM_MONITOR_NEW_BIOLOGY"; //上机测序完成消息，用于项目监控
    
    @Override
    public void sendActiveTaskMessage(ScheduleTaskActiveEvent activeEvent)
    {
        Message msg = new Message(configs.getAliyunONSTopic(), TAG_SCHEDULE_TASK_ACTIVE, new Gson().toJson(activeEvent).getBytes());
        producer.send(msg);
        log.info(" SCHEDULE_TASK_ACTIVE message.messageId is:" + msg.getMsgID());
    }
    
    @Override
    public void sendBiologyAnalysisCommentMessage(ScheduleTaskActiveEvent event)
    {
        Message msg = new Message(configs.getAliyunONSTopic(), TAG_BIOLOGY_ANALYSIS_COMMENT, new Gson().toJson(event).getBytes());
        producer.send(msg);
        log.info(" BIOLOGY_ANALYSIS_COMMENT message.messageId is:" + msg.getMsgID());
    }
    
    @Override
    public void sendDataSendMessage(OrderIdModel model)
    {
        Message msg = new Message(configs.getAliyunONSTopic(), TAG_EXTRA_SEND_REPORT, new Gson().toJson(model).getBytes());
        producer.send(msg);
        log.info(" EXTRA_SEND_REPORT message.messageId is:" + msg.getMsgID());
    }
    
    @Override
    public void sendConcludingReportMessage(OrderIdModel model)
    {
        Message msg = new Message(configs.getAliyunONSTopic(), TAG_CONCLUDING_REPORT, new Gson().toJson(model).getBytes());
        producer.send(msg);
        log.info(" CONCLUDING_REPORT message.messageId is:" + msg.getMsgID());
    }
    
    @Override
    public void sendGotoTechnicalMessage(TechnicalCreateEvent event)
    {
        Message msg = new Message(configs.getAliyunONSTopic(), TAG_TECHNICAL_TASK, new Gson().toJson(event).getBytes());
        producer.send(msg);
        log.info(" technical task create message.messageId is:" + msg.getMsgID());
    }
    
}
