package com.todaysoft.lims.testing.base.adapter.impl;

import com.todaysoft.lims.testing.base.adapter.IBiologyAdapter;
import com.todaysoft.lims.testing.base.entity.BiologyAnnotationTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;


@Service
public class BiologyAdapter extends AbstractAdapter implements IBiologyAdapter
{

    private static final String SERVICE_NAME = "lims-biology-service";

    private static final String TECHNICAL_SERVICE_NAME = "lims-technical-service";

    @Autowired
    private RestTemplate template;

    @Override
    public String getName()
    {
        return SERVICE_NAME;
    }

    @Override
    public void reTodoBiologyAnnotation(BiologyAnnotationTask request) {
        String url = "http://" + SERVICE_NAME + "/biology/annotation/reTodoBiologyAnnotation";
        template.postForObject(url, request,Integer.class);
    }

    @Override
    public void reTodoTechnicalAnalysis(String id) {
        String url = "http://" + TECHNICAL_SERVICE_NAME + "/technicalanaly/reTodoTechnicalAnalysis/{id}";
        template.getForObject(url, String.class, id);
    }
}
