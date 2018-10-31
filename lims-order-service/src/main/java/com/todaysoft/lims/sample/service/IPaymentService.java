package com.todaysoft.lims.sample.service;

import java.util.List;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.sample.dao.searcher.PaymentSearchRequest;
import com.todaysoft.lims.sample.entity.order.Order;
import com.todaysoft.lims.sample.entity.payment.OrderPaymentConfirm;
import com.todaysoft.lims.sample.entity.payment.OrderPaymentView;
import com.todaysoft.lims.sample.entity.payment.OrderPos;
import com.todaysoft.lims.sample.entity.payment.OrderRefundRecord;
import com.todaysoft.lims.sample.entity.payment.OrderTransfer;
import com.todaysoft.lims.sample.model.payment.PaymentFormRequest;

public interface IPaymentService
{
    
    Pagination<Order> appPaymentConfirm(PaymentSearchRequest request);
    
    Pagination<Order> refundmentList(PaymentSearchRequest request);
    
    Pagination<Order> replenishmentList(PaymentSearchRequest request);
    
    List<OrderPos> getParmentInfoByOrderId(PaymentSearchRequest request);
    
    boolean confirmPayment(PaymentFormRequest request);
    
    Boolean backConfirmPayment(PaymentFormRequest request);
    
    void confirmDelayOrder(PaymentFormRequest request);
    
    List<OrderTransfer> getTransInfoByOrderId(PaymentSearchRequest request);
    
    void confirmReplenish(PaymentFormRequest request);
    
    Pagination<Order> paymentHistory(PaymentSearchRequest request);
    
    List<OrderPaymentView> getPayMentViewByOrderId(PaymentSearchRequest request);
    
    void confirmRefund(PaymentFormRequest request);
    
    OrderPaymentConfirm getPayMentById(String request);
    
    OrderRefundRecord getRefundRecordById(String id);
    
    Pagination<Order> delayConfirmList(PaymentSearchRequest request);
    
    void contractPayment(PaymentFormRequest request);
    
    List<OrderPaymentConfirm> getOrderPaymentConfirmByOrderId(String orderId);
    
    Pagination<Order> cancelOrderList(PaymentSearchRequest request);
    
    boolean checkOrderFinish(String orderId);
    
    boolean checkOrderIsCancel(String orderId);
    
    Integer recaclOrderProduct(String orderId);
    
    Integer recaclOrderProductInvoice(String id);
    
}
