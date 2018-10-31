package com.todaysoft.lims.invoice.adapter.impl;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.todaysoft.lims.invoice.entity.Order;

@Component
public class OrderAdapter extends AbstractAdapter
{
    private static final String SERVICE_NAME = "lims-sampleReceive-service";
    
    @Autowired
    private RestTemplate template;
    
    public Order getOrderByCode(String code)
    {
        String url = getServiceUrl("/bmm/order/getOrderByCode/{code}");
        return template.getForObject(url, Order.class, Collections.singletonMap("code", code));
    }
    
    @Override
    public String getName()
    {
        return SERVICE_NAME;
    }
    
}
