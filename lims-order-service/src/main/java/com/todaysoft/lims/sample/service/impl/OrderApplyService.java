package com.todaysoft.lims.sample.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.sample.dao.searcher.OrderDelaySearcher;
import com.todaysoft.lims.sample.dao.searcher.OrderReduceSearcher;
import com.todaysoft.lims.sample.dao.searcher.OrderRefundSearcher;
import com.todaysoft.lims.sample.entity.order.Order;
import com.todaysoft.lims.sample.entity.payment.OrderDelay;
import com.todaysoft.lims.sample.entity.payment.OrderReduce;
import com.todaysoft.lims.sample.entity.payment.OrderRefund;
import com.todaysoft.lims.sample.service.IOrderApplyService;
import com.todaysoft.lims.utils.Collections3;

@Service
public class OrderApplyService implements IOrderApplyService
{
    @Autowired
    private BaseDaoSupport baseDaoSupport;
    
    @Override
    public Pagination<OrderRefund> refundList(OrderRefundSearcher request)
    {
        
        Pagination<OrderRefund> paging = baseDaoSupport.find(request.toQuery(), request.getPageNo(), request.getPageSize(), OrderRefund.class);
        return paging;
    }
    
    @Override
    public OrderRefund getRefund(String id)
    {
        // TODO Auto-generated method stub
        OrderRefund refund = baseDaoSupport.get(OrderRefund.class, id);
        return refund;
    }
    
    @Override
    public Order getOrderByRefund(String id)
    {
        // TODO Auto-generated method stub
        List<Order> list = baseDaoSupport.find(Order.class, "select o from Order o left join o.orderRefund oo where oo.id='" + id + "'");
        if (Collections3.isNotEmpty(list))
        {
            return list.get(0);
        }
        return null;
    }
    
    @Override
    public Pagination<OrderReduce> reduceList(OrderReduceSearcher request)
    {
        Pagination<OrderReduce> paging = baseDaoSupport.find(request.toQuery(), request.getPageNo(), request.getPageSize(), OrderReduce.class);
        return paging;
    }
    
    @Override
    public OrderReduce getReduce(String id)
    {
        OrderReduce reduce = baseDaoSupport.get(OrderReduce.class, id);
        return reduce;
    }
    
    @Override
    public Order getOrderByReduce(String id)
    {
        // TODO Auto-generated method stub
        List<Order> list = baseDaoSupport.find(Order.class, "select o from Order o left join o.orderReduce oo where oo.id='" + id + "'");
        if (Collections3.isNotEmpty(list))
        {
            return list.get(0);
        }
        return null;
    }
    
    @Override
    public Pagination<OrderDelay> delayList(OrderDelaySearcher request)
    {
        Pagination<OrderDelay> paging = baseDaoSupport.find(request.toQuery(), request.getPageNo(), request.getPageSize(), OrderDelay.class);
        return paging;
    }
    
    @Override
    public OrderDelay getDelay(String id)
    {
        // TODO Auto-generated method stub
        OrderDelay delay = baseDaoSupport.get(OrderDelay.class, id);
        return delay;
    }
    
    @Override
    public Order getOrderByDelay(String id)
    {
        List<Order> list = baseDaoSupport.find(Order.class, "select o from Order o left join o.orderDelay oo where oo.id='" + id + "'");
        if (Collections3.isNotEmpty(list))
        {
            return list.get(0);
        }
        return null;
    }
    
    @Override
    public List<OrderRefund> getRefundsByOrder(String orderId)
    {
        // TODO Auto-generated method stub
        return baseDaoSupport.find(OrderRefund.class, "from OrderRefund o where o.orderId='" + orderId + "'");
    }
    
}
