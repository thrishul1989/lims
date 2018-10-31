package com.todaysoft.lims.sample.service.adapter;

import com.todaysoft.lims.sample.model.Task;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;


@Component
public class TaskAdapter extends AbstractAdapter
{
    private static final String SERVICE_NAME = "lims-product-service";
    
    @Autowired
    private RestTemplate template;
    

    public Task get(Integer id){
        String url = getServiceUrl("/bcm/task/{id}");
        return template.getForObject(url,Task.class,Collections.singletonMap("id",id));
    }

    public Task getBySemantic(String semantic){
        String url = getServiceUrl("/bcm/task/getBySemantic/{semantic}");
        return template.getForObject(url,Task.class,Collections.singletonMap("semantic",semantic));
    }

    @Override
    public String getName()
    {
        return SERVICE_NAME;
    }
}
