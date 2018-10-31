package com.todaysoft.lims.system.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.model.vo.order.Order;
import com.todaysoft.lims.system.model.vo.payment.OrderDelay;
import com.todaysoft.lims.system.model.vo.payment.OrderReduce;
import com.todaysoft.lims.system.model.vo.payment.OrderRefund;
import com.todaysoft.lims.system.service.IOrderApplyService;

@Service
public class OrderApplyService extends RestService implements IOrderApplyService
{
    
    @Override
    public Pagination<OrderRefund> refundList(OrderRefund searcher, int pageNo, int pageSize)
    {
        searcher.setPageNo(pageNo);
        searcher.setPageSize(pageSize);
        String url = GatewayService.getServiceUrl("/bmm/orderApply/refundList");
        ResponseEntity<Pagination<OrderRefund>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<OrderRefund>(searcher), new ParameterizedTypeReference<Pagination<OrderRefund>>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public OrderRefund getRefund(String id)
    {
        String url = GatewayService.getServiceUrl("/bmm/orderApply/getRefund/{id}");
        return template.getForObject(url, OrderRefund.class, id);
        
    }
    
    @Override
    public Order getOrderByRefund(String id)
    {
        String url = GatewayService.getServiceUrl("/bmm/orderApply/getOrderByRefund/{id}");
        return template.getForObject(url, Order.class, id);
    }
    
    @Override
    public Pagination<OrderReduce> reduceList(OrderReduce searcher, int pageNo, int pageSize)
    {
        searcher.setPageNo(pageNo);
        searcher.setPageSize(pageSize);
        String url = GatewayService.getServiceUrl("/bmm/orderApply/reduceList");
        ResponseEntity<Pagination<OrderReduce>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<OrderReduce>(searcher), new ParameterizedTypeReference<Pagination<OrderReduce>>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public OrderReduce getReduce(String id)
    {
        String url = GatewayService.getServiceUrl("/bmm/orderApply/getReduce/{id}");
        return template.getForObject(url, OrderReduce.class, id);
    }
    
    @Override
    public Order getOrderByReduce(String id)
    {
        String url = GatewayService.getServiceUrl("/bmm/orderApply/getOrderByReduce/{id}");
        return template.getForObject(url, Order.class, id);
    }
    
    @Override
    public Pagination<OrderDelay> delayList(OrderDelay searcher, int pageNo, int pageSize)
    {
        searcher.setPageNo(pageNo);
        searcher.setPageSize(pageSize);
        String url = GatewayService.getServiceUrl("/bmm/orderApply/delayList");
        ResponseEntity<Pagination<OrderDelay>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<OrderDelay>(searcher), new ParameterizedTypeReference<Pagination<OrderDelay>>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public OrderDelay getDelay(String id)
    {
        String url = GatewayService.getServiceUrl("/bmm/orderApply/getDelay/{id}");
        return template.getForObject(url, OrderDelay.class, id);
    }
    
    @Override
    public Order getOrderByDelay(String id)
    {
        String url = GatewayService.getServiceUrl("/bmm/orderApply/getOrderByDelay/{id}");
        return template.getForObject(url, Order.class, id);
    }
    
    @Override
    public List<OrderRefund> getRefunds(String orderId)
    {
        
        String url = GatewayService.getServiceUrl("/bmm/orderApply/getRefundsByOrder/" + orderId);
        ResponseEntity<List<OrderRefund>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<Map>(Collections.singletonMap("", "")), new ParameterizedTypeReference<List<OrderRefund>>()
            {
            });
        return exchange.getBody();
    }
    
}
