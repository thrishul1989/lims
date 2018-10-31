package com.todaysoft.lims.testing.ons;

import java.util.List;
import java.util.Map;

public interface IMessageProducer
{
    void sendReportGenerateMessage(List<Map<String, String>> orderProductIds);
    
    void sendReportVerifyMessage(Map<String, String> orderProductId,String reviewResult);
    
    void sendReportDeliverMessage(List<Map<String, String>> orderProductIds);
    
    void sendStatusSearchCancelMessage(String scheduleId);
    
    void sendOpenReportGenerateEvent(String orderId,String productId);
}
