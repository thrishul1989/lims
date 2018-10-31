package com.todaysoft.lims.schedule.adapter.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;


public abstract class AbstractAdapter implements MicroserviceAdapter {
    @Autowired
    protected RestTemplate template;

    public <T> T post(String url, Object entity) {
        return template.exchange(url, HttpMethod.POST, new HttpEntity(entity),
                new ParameterizedTypeReference<T>() {
                }).getBody();

    }

    protected String getServiceUrl(String uri) {
        if (null == getName()) {
            throw new IllegalStateException();
        }

        return "http://" + getName() + uri;
    }

    public void fallback() {
        throw new RuntimeException("TODO:FALLBACK");
    }
}
