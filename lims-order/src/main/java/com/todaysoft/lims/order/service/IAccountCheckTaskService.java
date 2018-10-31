package com.todaysoft.lims.order.service;

import com.todaysoft.lims.order.model.reconciliation.OrderAccountCheckTaskSearcher;
import com.todaysoft.lims.order.mybatis.model.OrderAccountCheckTask;
import com.todaysoft.lims.order.mybatis.model.OrderAccountStateRecord;
import com.todaysoft.lims.order.mybatis.model.OrderPaymentConfirm;
import com.todaysoft.lims.order.mybatis.parameter.OrderPaymentConfirmSearcher;
import com.todaysoft.lims.order.response.OrderSearchResponse;

public interface IAccountCheckTaskService
{
    
    void saveOrUpdateData(OrderAccountCheckTask batch);
    
    Boolean isChecked(int interfaceCode, String billDate);
    
    OrderAccountCheckTask selectByExample(int interfaceCode, String billdate);
    
    OrderSearchResponse<OrderAccountCheckTask> searchAccountCheckTask(OrderAccountCheckTaskSearcher searcher);
    
    OrderSearchResponse<OrderPaymentConfirm> getPaymentRecordByDate(OrderPaymentConfirmSearcher searcher);
    
    void handleTask(OrderAccountCheckTask request);
    
    OrderAccountStateRecord accountStateRecordDetail(OrderPaymentConfirmSearcher request);
    
}
