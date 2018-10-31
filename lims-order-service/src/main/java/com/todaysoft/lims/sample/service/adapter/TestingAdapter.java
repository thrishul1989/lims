package com.todaysoft.lims.sample.service.adapter;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.todaysoft.lims.sample.entity.User;
import com.todaysoft.lims.sample.model.order.SampleAbnormalSolvedEvent;
import com.todaysoft.lims.sample.model.request.StartOrderTestingRequest;

@Component
public class TestingAdapter extends AbstractAdapter
{
    private static final String SERVICE_NAME = "lims-testing-service";
    
    @Autowired
    private RestTemplate template;
    
    public void autoStartOrderTesting(String orderId)
    {
        
        StartOrderTestingRequest startOrderTestingRequest = new StartOrderTestingRequest();
        startOrderTestingRequest.setId(orderId);
        String url = getServiceUrl("/bpm/testing/order/autoStart");
        template.postForObject(url, startOrderTestingRequest, Integer.class);
    }
    
    public void solvedSample(String abnormalSampleId, String strategy, String newSampleId)
    {
        SampleAbnormalSolvedEvent event = new SampleAbnormalSolvedEvent();
        event.setAbnormalSampleId(abnormalSampleId);
        event.setSolvedStrategy(strategy);
        event.setResendSampleId(newSampleId);
        String url = getServiceUrl("/bpm/testing/listener/samples/abnormal/solved");
        template.postForObject(url, event, Integer.class);
    }
    
    public void cancelOrderSchedule(StartOrderTestingRequest startOrderTestingRequest)
    {
        //StartOrderTestingRequest startOrderTestingRequest = new StartOrderTestingRequest();
        //startOrderTestingRequest.setId(orderId);
        String url = getServiceUrl("/bpm/testing/order/cancelOrderSchedule");
        template.postForObject(url, startOrderTestingRequest, Integer.class);
    }
    
    public String getLane(String scheduleId)
    {
        String url = getServiceUrl("/bpm/testing/order/getSequenceCode/{scheduleId}");
        return template.getForObject(url, String.class, Collections.singletonMap("scheduleId", scheduleId));
    }
    
    @Override
    public String getName()
    {
        return SERVICE_NAME;
    }
}
