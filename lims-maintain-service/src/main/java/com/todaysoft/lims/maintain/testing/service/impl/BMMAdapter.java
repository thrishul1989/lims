package com.todaysoft.lims.maintain.testing.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.todaysoft.lims.maintain.entity.BioSampleSimpleModel;
import com.todaysoft.lims.maintain.entity.OrderSimpleModel;
import com.todaysoft.lims.maintain.entity.ProductSimpleModel;
import com.todaysoft.lims.maintain.order.entity.OrderSampleDetailsResponse;

@Component
public class BMMAdapter
{
    private static final String SERVICE_NAME_ORDER = "lims-sampleReceive-service";
    
    private static final String SERVICE_NAME_PRODUCT = "lims-product-service";
    
    @Autowired
    private RestTemplate template;
    
    public OrderSimpleModel getOrder(BioSampleSimpleModel assm)
    {
        String url = "http://" + SERVICE_NAME_ORDER + "/bmm/order/simple";
        return template.postForObject(url, assm, OrderSimpleModel.class);
    }
    
    public ProductSimpleModel getProduct(String id)
    {
        String url = "http://" + SERVICE_NAME_PRODUCT + "/bcm/product/{id}/simple";
        return template.getForObject(url, ProductSimpleModel.class, id);
    }
    
    public OrderSampleDetailsResponse getOrderSampleDetails(String orderId)
    {
        String url = "http://" + SERVICE_NAME_ORDER + "/bmm/order/{id}/samples/details";
        return template.getForObject(url, OrderSampleDetailsResponse.class, orderId);
    }
    
}
