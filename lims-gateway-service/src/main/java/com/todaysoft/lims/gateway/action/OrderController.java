package com.todaysoft.lims.gateway.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.lims.gateway.model.Pagination;
import com.todaysoft.lims.gateway.model.request.order.Order;
import com.todaysoft.lims.gateway.model.request.order.OrderFormRequest;
import com.todaysoft.lims.gateway.model.request.order.OrderSearchRequest;
import com.todaysoft.lims.gateway.service.IOrderService;

@RestController
@RequestMapping("/order")
public class OrderController
{
    
    @Autowired
    private IOrderService service;
    
    @RequestMapping(value = "/paging")
    public Pagination<Order> paging(@RequestBody OrderSearchRequest request)
    {
        Pagination<Order> list = service.paging(request);
        return list;
    }
    
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public List<Order> list(@RequestBody OrderSearchRequest request)
    {
        return service.list(request);
    }
    
    @RequestMapping(value = "/getOrderById/{id}", method = RequestMethod.GET)
    public Order getOrderById(@PathVariable String id)
    {
        return service.getOrderById(id);
    }
    
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(@RequestBody OrderFormRequest request)
    {
        return service.create(request);
    }
    
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(@RequestBody OrderFormRequest request)
    {
        return service.update(request);
    }
    
    @RequestMapping(value = "/deleteOrderById/{id}", method = RequestMethod.DELETE)
    public void deleteOrderById(@PathVariable String id)
    {
        service.deleteOrderById(id);
    }
    
}
