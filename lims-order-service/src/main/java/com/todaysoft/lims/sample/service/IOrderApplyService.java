package com.todaysoft.lims.sample.service;

import java.util.List;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.sample.dao.searcher.OrderDelaySearcher;
import com.todaysoft.lims.sample.dao.searcher.OrderReduceSearcher;
import com.todaysoft.lims.sample.dao.searcher.OrderRefundSearcher;
import com.todaysoft.lims.sample.entity.order.Order;
import com.todaysoft.lims.sample.entity.payment.OrderDelay;
import com.todaysoft.lims.sample.entity.payment.OrderReduce;
import com.todaysoft.lims.sample.entity.payment.OrderRefund;
import com.todaysoft.lims.sample.model.order.OrderSearchRequest;

public interface IOrderApplyService
{
    
    Pagination<OrderRefund> refundList(OrderRefundSearcher request);
    
    OrderRefund getRefund(String id);
    
    Order getOrderByRefund(String id);
    
    Pagination<OrderReduce> reduceList(OrderReduceSearcher request);
    
    OrderReduce getReduce(String id);
    
    Order getOrderByReduce(String id);
    
    Pagination<OrderDelay> delayList(OrderDelaySearcher request);
    
    OrderDelay getDelay(String id);
    
    Order getOrderByDelay(String id);

    List<OrderRefund> getRefundsByOrder(String orderId);
    
}
