package com.todaysoft.lims.sample.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.sample.dao.searcher.OrderDelaySearcher;
import com.todaysoft.lims.sample.dao.searcher.OrderReduceSearcher;
import com.todaysoft.lims.sample.dao.searcher.OrderRefundSearcher;
import com.todaysoft.lims.sample.entity.SampleOrder;
import com.todaysoft.lims.sample.entity.order.Order;
import com.todaysoft.lims.sample.entity.payment.OrderDelay;
import com.todaysoft.lims.sample.entity.payment.OrderReduce;
import com.todaysoft.lims.sample.entity.payment.OrderRefund;
import com.todaysoft.lims.sample.model.request.OrderListRequest;
import com.todaysoft.lims.sample.service.IOrderApplyService;

@RestController
@RequestMapping("/bmm/orderApply")
public class OrderApplyController
{
    @Autowired
    private IOrderApplyService orderApplyService;
    
    @RequestMapping(value = "/refundList")
    public Pagination<OrderRefund> refundList(@RequestBody OrderRefundSearcher request)
    {
        return orderApplyService.refundList(request);
    }
    
    
    @RequestMapping(value = "/getRefund/{id}")
    public OrderRefund getRefund(@PathVariable String id)
    {
        return orderApplyService.getRefund(id);
    }
    
    @RequestMapping(value = "/getRefundsByOrder/{orderId}")
    public List<OrderRefund> getRefundsByOrder(@PathVariable String orderId)
    {
        return orderApplyService.getRefundsByOrder(orderId);
    }
    
    
    
    @RequestMapping(value = "/getOrderByRefund/{id}")
    public Order getOrderByRefund(@PathVariable String id)
    {
        return orderApplyService.getOrderByRefund(id);
    }
    

    
    @RequestMapping(value = "/reduceList")
    public Pagination<OrderReduce> reduceList(@RequestBody OrderReduceSearcher request)
    {
        return orderApplyService.reduceList(request);
    }
    
    
    @RequestMapping(value = "/getReduce/{id}")
    public OrderReduce getReduce(@PathVariable String id)
    {
        return orderApplyService.getReduce(id);
    }
    
    @RequestMapping(value = "/getOrderByReduce/{id}")
    public Order getOrderByReduce(@PathVariable String id)
    {
        return orderApplyService.getOrderByReduce(id);
    }
    
    
    @RequestMapping(value = "/delayList")
    public Pagination<OrderDelay> delayList(@RequestBody OrderDelaySearcher request)
    {
        return orderApplyService.delayList(request);
    }
    
    
    @RequestMapping(value = "/getDelay/{id}")
    public OrderDelay getDelay(@PathVariable String id)
    {
        return orderApplyService.getDelay(id);
    }
    
    @RequestMapping(value = "/getOrderByDelay/{id}")
    public Order getOrderByDelay(@PathVariable String id)
    {
        return orderApplyService.getOrderByDelay(id);
    }
    
    
    
    
}
