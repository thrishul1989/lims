package com.todaysoft.lims.system.service;

import com.todaysoft.lims.order.response.CommonOrderResponse;
import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.model.vo.PaymentSearchRequest;
import com.todaysoft.lims.system.model.vo.order.Order;
import com.todaysoft.lims.system.model.vo.payment.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.List;

public interface IPaymentService
{
    
    Pagination<Order> appPaymentConfirm(PaymentSearchRequest searcher, int pageNo, int defaultpagesize);
    
    Pagination<CommonOrderResponse> refundmentList(PaymentSearchRequest searcher, int pageNo, int defaultpagesize,boolean isHandle);
    
    Pagination<CommonOrderResponse> replenishmentList(PaymentSearchRequest searcher, int pageNo, int defaultpagesize);
    
    List<OrderPos> getParmentInfoByOrderId(PaymentSearchRequest id);
    
    void confirmPayment(PaymentFormRequest request);
    
    void backConfirmPayment(PaymentFormRequest request);
    
    void confirmDelayOrder(PaymentFormRequest request);
    
    List<OrderTransfer> getTransInfoByOrderId(PaymentSearchRequest searcher);
    
    void confirmReplenish(PaymentFormRequest request);
    
    Pagination<CommonOrderResponse> paymentHistory(PaymentSearchRequest searcher, int pageNo, int defaultpagesize);
    
    List<OrderPaymentView> getPayMentViewByOrderId(PaymentSearchRequest request);
    
    void confirmRefund(PaymentFormRequest request);
    
    OrderPaymentConfirm getConfirmById(String protoId);
    
    OrderRefundRecord getRefundRecordById(String protoId);
    
    Pagination<CommonOrderResponse> delayConfirmList(PaymentSearchRequest searcher, int pageNo, int defaultpagesize);
    
    void contractPayment(PaymentFormRequest request);
    
    String downloadConfirmList(InputStream is, List<Order> orders);
    
    List<OrderConfirmModel> parse(HttpServletRequest request, HttpServletResponse response);
    
    List<OrderPaymentConfirm> getOrderPaymentConfirmByOrderId(String orderId);
    
    Pagination<Order> cancelOrderList(PaymentSearchRequest searcher, int pageNo, int defaultpagesize);
    
    Pagination<CommonOrderResponse> appPaymentList(PaymentSearchRequest searcher, int pageNo, int defaultpagesize);
    
    Pagination<CommonOrderResponse> backPaymentList(PaymentSearchRequest searcher, int pageNo, int defaultpagesize);
    
}
