package com.todaysoft.lims.sample.ons;

import com.aliyun.openservices.ons.api.SendResult;
import com.todaysoft.lims.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.Producer;
import com.google.gson.Gson;
import com.todaysoft.lims.sample.config.Configs;
import com.todaysoft.lims.sample.ons.event.ContractOrderTestingConfirmedEvent;
import com.todaysoft.lims.sample.ons.event.OrderCancelEvent;
import com.todaysoft.lims.sample.ons.event.OrderModifyEvent;
import com.todaysoft.lims.sample.ons.event.OrderPaymentConfirmedEvent;
import com.todaysoft.lims.sample.ons.event.OrderSubmitEvent;
import com.todaysoft.lims.sample.ons.event.SampleAbnormalSolveEvent;
import com.todaysoft.lims.sample.ons.event.SampleReceiveEvent;

@Component
public class MessageProducer implements IMessageProducer
{
    private static final String TAG_ORDER_SUBMIT = "ORDER_SUBMIT";
    
    private static final String TAG_ORDER_CANCEL = "ORDER_CANCEL";
    
    private static final String TAG_ORDER_MODIFY = "ORDER_MODIFY";
    
    private static final String TAG_SAMPLE_RECEIVE = "SAMPLE_RECEIVE";
    
    private static final String TAG_SAMPLE_ABNORMAL_SOLVE = "SAMPLE_ABNORMAL_SOLVE";
    
    private static final String TAG_ORDER_PAYMENT_CONFIRM = "ORDER_PAYMENT_CONFIRM";
    
    private static final String TAG_CONTRACT_ORDER_TESTING_CONFIRM = "CONTRACT_ORDER_TESTING_CONFIRM";
    
    private static final String TAG_ORDER_START = "orderTag";

    private static Logger log = LoggerFactory.getLogger(MessageProducer.class);
    
    @Autowired
    private Configs configs;
    
    @Autowired
    private Producer producer;
    
    @Override
    public void sendOrderSubmitMessage(String orderId)
    {
        OrderSubmitEvent event = new OrderSubmitEvent();
        event.setOrderId(orderId);
        Message msg = new Message(configs.getAliyunONSTopic(), TAG_ORDER_SUBMIT, new Gson().toJson(event).getBytes());
        producer.send(msg);
    }
    
    @Override
    public void sendOrderCancelMessage(String orderId)
    {
        OrderCancelEvent event = new OrderCancelEvent();
        event.setOrderId(orderId);
        Message msg = new Message(configs.getAliyunONSTopic(), TAG_ORDER_CANCEL, new Gson().toJson(event).getBytes());
        producer.send(msg);
    }
    
    @Override
    public void sendOrderModifyMessage(String orderId)
    {
        OrderModifyEvent event = new OrderModifyEvent();
        event.setOrderId(orderId);
        Message msg = new Message(configs.getAliyunONSTopic(), TAG_ORDER_MODIFY, new Gson().toJson(event).getBytes());
        producer.send(msg);
    }
    
    @Override
    public void sendSampleReceiveMessage(String orderId)
    {
        SampleReceiveEvent event = new SampleReceiveEvent();
        event.setOrderId(orderId);
        Message msg = new Message(configs.getAliyunONSTopic(), TAG_SAMPLE_RECEIVE, new Gson().toJson(event).getBytes());
        producer.send(msg);
    }
    
    @Override
    public void sendSampleAbnormalSolvedMessage(String abnormalSampleId, String strategy, String resendSampleId)
    {
        SampleAbnormalSolveEvent event = new SampleAbnormalSolveEvent();
        event.setAbnormalSampleId(abnormalSampleId);
        event.setStrategy(strategy);
        event.setResendSampleId(resendSampleId);
        Message msg = new Message(configs.getAliyunONSTopic(), TAG_SAMPLE_ABNORMAL_SOLVE, new Gson().toJson(event).getBytes());
        producer.send(msg);
    }
    
    @Override
    public void sendPaymentConfirmedMessage(String orderId)
    {
        OrderPaymentConfirmedEvent event = new OrderPaymentConfirmedEvent();
        event.setOrderId(orderId);
        Message msg = new Message(configs.getAliyunONSTopic(), TAG_ORDER_PAYMENT_CONFIRM, new Gson().toJson(event).getBytes());
        producer.send(msg);
        
    }
    
    @Override
    public void sendContractOrderTestingConfirmedMessage(String orderId)
    {
        ContractOrderTestingConfirmedEvent event = new ContractOrderTestingConfirmedEvent();
        event.setOrderId(orderId);
        Message msg = new Message(configs.getAliyunONSTopic(), TAG_CONTRACT_ORDER_TESTING_CONFIRM, new Gson().toJson(event).getBytes());
        producer.send(msg);
    }
    
    @Override
    public void sendOrderAutoStartMessage(String orderId)
    {
        if (StringUtils.isEmpty(orderId))
        {
            throw new IllegalArgumentException();
        }
        
        OrderPaymentConfirmedEvent event = new OrderPaymentConfirmedEvent();
        event.setOrderId(orderId);
        Message msg = new Message(configs.getAliyunONSTopic(), TAG_ORDER_START, new Gson().toJson(event).getBytes());
        msg.setKey("paymentConfirmOrderId"+orderId);
        SendResult sendResult = producer.send(msg);
        log.info("paymentConfirmOrderId log:"+"paymentConfirmOrderId"+orderId+"--MessageId:"+sendResult.getMessageId());
        
    }
}
