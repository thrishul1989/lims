package com.todaysoft.lims.sample.ons;

public interface IMessageProducer
{
    void sendOrderSubmitMessage(String orderId);
    
    void sendOrderCancelMessage(String orderId);
    
    void sendOrderModifyMessage(String orderId);
    
    void sendSampleAbnormalSolvedMessage(String abnormalSampleId, String strategy, String resendSampleId);
    
    void sendSampleReceiveMessage(String orderId);
    
    void sendPaymentConfirmedMessage(String orderId);
    
    void sendContractOrderTestingConfirmedMessage(String orderId);
    
    void sendOrderAutoStartMessage(String orderId);
    
}
