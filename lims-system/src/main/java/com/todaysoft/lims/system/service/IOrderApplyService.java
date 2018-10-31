package com.todaysoft.lims.system.service;

import java.util.List;

import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.model.vo.order.Order;
import com.todaysoft.lims.system.model.vo.payment.OrderDelay;
import com.todaysoft.lims.system.model.vo.payment.OrderReduce;
import com.todaysoft.lims.system.model.vo.payment.OrderRefund;

public interface IOrderApplyService
{
    
    Pagination<OrderRefund> refundList(OrderRefund searcher, int pageNo, int pageSize);
    
    OrderRefund getRefund(String id);
    
    List<OrderRefund> getRefunds(String orderId);
    
    Order getOrderByRefund(String id);
    
    Pagination<OrderReduce> reduceList(OrderReduce searcher, int pageNo, int pageSize);
    
    OrderReduce getReduce(String id);
    
    Order getOrderByReduce(String id);
    
    Pagination<OrderDelay> delayList(OrderDelay searcher, int pageNo, int pageSize);
    
    OrderDelay getDelay(String id);
    
    Order getOrderByDelay(String id);
    
}
