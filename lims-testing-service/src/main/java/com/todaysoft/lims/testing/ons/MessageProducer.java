package com.todaysoft.lims.testing.ons;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.Producer;
import com.google.gson.Gson;
import com.todaysoft.lims.testing.base.config.Configs;
import com.todaysoft.lims.testing.ons.event.OpenReportGenerateEvent;
import com.todaysoft.lims.testing.ons.event.ReportDeliverEvent;
import com.todaysoft.lims.testing.ons.event.ReportGenerateEvent;
import com.todaysoft.lims.testing.ons.event.ReportVerifyEvent;
import com.todaysoft.lims.testing.ons.event.StatusSearchCancelEvent;

@Component
public class MessageProducer implements IMessageProducer
{
    private static final String EVENT_REPORT_GENERATE = "REPORT_GENERATE";
    
    private static final String EVENT_REPORT_VERIFY = "REPORT_VERIFY";
    
    private static final String EVENT_REPORT_DELIVER = "REPORT_DELIVER";
    
    private static final String EVENT_STATUS_SEARCH_CANCEL = "STATUS_SEARCH_CANCEL";
    
    private static final String EVENT_OPEN_REPORT_GENERATE = "OPEN_REPORT_GENERATE";
    
    @Autowired
    private Configs configs;
    
    @Autowired
    private Producer producer;
    
    @Override
    public void sendReportGenerateMessage(List<Map<String, String>> orderProductIds)
    {
        ReportGenerateEvent event = new ReportGenerateEvent();
        event.setOrderProductIds(orderProductIds);
        Message msg = new Message(configs.getAliyunONSTopic(), EVENT_REPORT_GENERATE, new Gson().toJson(event).getBytes());
        producer.send(msg);
        
    }
    
    @Override
    public void sendReportVerifyMessage(Map<String, String> orderProductId, String reviewResult)
    {
        ReportVerifyEvent event = new ReportVerifyEvent();
        event.setOrderProductId(orderProductId);
        event.setReviewResult(reviewResult);
        Message msg = new Message(configs.getAliyunONSTopic(), EVENT_REPORT_VERIFY, new Gson().toJson(event).getBytes());
        producer.send(msg);
        
    }
    
    @Override
    public void sendReportDeliverMessage(List<Map<String, String>> orderProductIds)
    {
        ReportDeliverEvent event = new ReportDeliverEvent();
        event.setOrderProductIds(orderProductIds);
        Message msg = new Message(configs.getAliyunONSTopic(), EVENT_REPORT_DELIVER, new Gson().toJson(event).getBytes());
        producer.send(msg);
    }
    
    @Override
    public void sendStatusSearchCancelMessage(String scheduleId)
    {
        StatusSearchCancelEvent event = new StatusSearchCancelEvent();
        event.setScheduleId(scheduleId);
        Message msg = new Message(configs.getAliyunONSTopic(), EVENT_STATUS_SEARCH_CANCEL, new Gson().toJson(event).getBytes());
        producer.send(msg);
    }
    
    @Override
    public void sendOpenReportGenerateEvent(String orderId, String productId)
    {
        OpenReportGenerateEvent event = new OpenReportGenerateEvent();
        event.setOrderId(orderId);
        event.setProductId(productId);
        Message msg = new Message(configs.getAliyunONSTopic(), EVENT_OPEN_REPORT_GENERATE, new Gson().toJson(event).getBytes());
        producer.send(msg);
    }
    
}
