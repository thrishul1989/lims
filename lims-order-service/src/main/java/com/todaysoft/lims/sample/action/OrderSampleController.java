package com.todaysoft.lims.sample.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.lims.sample.entity.OrderSample;
import com.todaysoft.lims.sample.model.order.OrderSimpleModel;
import com.todaysoft.lims.sample.model.request.OrderSampleCreateRequest;
import com.todaysoft.lims.sample.model.request.OrderSampleListRequest;
import com.todaysoft.lims.sample.service.IOrderSampleService;

@RestController
@RequestMapping("/orderSample")
public class OrderSampleController
{
    
    @Autowired
    private IOrderSampleService service;
    
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public List<OrderSample> list(@RequestBody OrderSampleListRequest request)
    {
        return service.list(request);
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public OrderSample get(@PathVariable Integer id)
    {
        return service.get(id);
    }
    
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Integer create(@RequestBody OrderSampleCreateRequest request)
    {
        return service.create(request);
    }
    
    @RequestMapping(value = "/{id}/order", method = RequestMethod.GET)
    public OrderSimpleModel getSampleOrder(@PathVariable String id)
    {
        return service.getSampleOrder(id);
    }
}
