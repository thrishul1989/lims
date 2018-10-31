package com.todaysoft.lims.gateway.service.adapter;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import com.todaysoft.lims.gateway.service.impl.RestfulResponseErrorHandler;

public abstract class AbstractAdapter implements MicroserviceAdapter
{
    @Autowired
    protected RestTemplate template;
    
    @PostConstruct
    protected void setRestTemplateErrorHandler()
    {
        template.setErrorHandler(new RestfulResponseErrorHandler(template.getMessageConverters()));
    }
    
    protected String getServiceUrl(String uri)
    {
        if (null == getName())
        {
            throw new IllegalStateException();
        }
        
        return "http://" + getName() + uri;
    }
}
