package com.todaysoft.lims.sample.service;

import java.util.List;

import com.todaysoft.lims.sample.entity.OrderSample;
import com.todaysoft.lims.sample.model.order.OrderSimpleModel;
import com.todaysoft.lims.sample.model.request.OrderSampleCreateRequest;
import com.todaysoft.lims.sample.model.request.OrderSampleListRequest;

public interface IOrderSampleService
{
    List<OrderSample> list(OrderSampleListRequest request);
    
    Integer create(OrderSampleCreateRequest request);
    
    OrderSample get(Integer id);
    
    OrderSimpleModel getSampleOrder(String id);
}
