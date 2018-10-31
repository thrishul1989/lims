package com.todaysoft.lims.maintain.order.service;

import java.util.List;

public interface IOrderService
{
    
    void changeStatus();
    
    List<String> changeOrderProduct();
    
    int changePaymentConfirmStatus();
    
    void searchReceiveStatus();
    
    void synchronizeOrderPayTime();
    
}
