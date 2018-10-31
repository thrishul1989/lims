package com.todaysoft.lims.schedule.service;

import java.util.Date;
import java.util.List;

import com.todaysoft.lims.schedule.entity.Order;
import com.todaysoft.lims.schedule.entity.OrderSampleView;
import com.todaysoft.lims.schedule.model.OrderSchedules;
import com.todaysoft.lims.schedule.model.OrderTestings;

public interface IOrderService
{
    Order getOrder(String id);
    
    OrderTestings getOrderTestings(String id);
    
    boolean isOrderPlanned(String id);
    
    OrderSchedules getOrderSchedules(String id, List<String> scheduleIds);
    
    OrderSampleView getBeforeSampleBySampleCode(String sampleCode);
    
    String getOrderIdBySampleId(String sampleId);
    
    String getOrderIdBySampleCode(String sampleCode);
    
    boolean isOrderTestingStarted(String id);
    
    boolean isSampleReceiveFinished(String id);
    
    boolean isSampleStorageFinished(String id);
    
    boolean isReducedAsPaymentConfirmed(String id);
    
    boolean isAutoStartContractOrder(String id);
    
    PaymentConfirmType getOrderPaymentConfirmType(String id);
    
    /**
     * 获取订单最早的支付金额确认到账时间
     */
    Date getFirstPaymentAmountConfirmedTime(String id);
    
    /**
     * 获取订单延迟付款终审通过的时间
     */
    Date getDelayPaymentAgreedTime(String id);
    
    /**
     * 获取订单减免申请终审通过的时间
     */
    Date getReduceAgreedTime(String id);
    
    /**
     * 获取订单检测启动的时间
     */
    Date getTestingStartTime(String id);
    
    /**
     * 获取订单检测启动前关联样本最后一次收样时间
     */
    Date getLastSampleReceivedTime(String id, Date testingStartTime);
    
    /**
     * 获取订单检测启动前关联样本最后一次入库时间
     */
    Date getLastSampleStoragedTime(String id, Date testingStartTime);
    
    /**
     * 合同订单没有科研结题报告(状态3),即不写入报告节点  2017.10.27 ww
     * 不是合同订单都可写
     */
    boolean canWriteReportNode(String id);
}
