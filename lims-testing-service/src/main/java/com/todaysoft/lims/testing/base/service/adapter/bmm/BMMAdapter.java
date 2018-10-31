package com.todaysoft.lims.testing.base.service.adapter.bmm;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.todaysoft.lims.testing.biologyanaly.model.BioSampleSimpleModel;

@Component
public class BMMAdapter
{
    private static final String SERVICE_NAME = "lims-sampleReceive-service";
    
    @Autowired
    private RestTemplate template;
    
    public OrderSimpleModel getOrder(BioSampleSimpleModel assm)
    {
        String url = "http://" + SERVICE_NAME + "/bmm/order/simple";
        return template.postForObject(url, assm, OrderSimpleModel.class);
    }
    
    public List<OrderVerifySampleModel> getOrderVerifySamples(String id)
    {
        String url = "http://" + SERVICE_NAME + "/bmm/order/{id}/samples/verify";
        return template.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<OrderVerifySampleModel>>()
        {
        }, id).getBody();
    }
    
    public OrderVerifySampleModel getOrderVerifySampleById(String sampleId)
    {
        String url = "http://" + SERVICE_NAME + "/bmm/order/verifySample/{sampleId}";
        return template.getForObject(url, OrderVerifySampleModel.class, sampleId);
    }
    
    public void setSampleAbnormal(SetSampleAbnormalRequest request)
    {
        String url = "http://" + SERVICE_NAME + "/bmm/order/set_sample_abnormal";
        template.postForLocation(url, request);
    }
    
    public OrderSampleDetailsResponse getOrderSampleDetails(String orderId)
    {
        String url = "http://" + SERVICE_NAME + "/bmm/order/{id}/samples/details";
        return template.getForObject(url, OrderSampleDetailsResponse.class, orderId);
    }
    
    public OrderSimpleModel getOrderBySampleId(String sampleId)
    {
        String url = "http://" + SERVICE_NAME + "/orderSample/{sampleId}/order";
        return template.getForObject(url, OrderSimpleModel.class, sampleId);
    }
    
    public boolean checkOrderFinish(String orderId)
    {
        String url = "http://" + SERVICE_NAME + "/bmm/payment/checkOrderFinish/{orderId}";
        return template.getForObject(url, boolean.class, Collections.singletonMap("orderId", orderId));
    }
    
}
