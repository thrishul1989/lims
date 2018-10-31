package com.todaysoft.lims.schedule.adapter.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.todaysoft.lims.schedule.adapter.IProductAdapter;
import com.todaysoft.lims.schedule.model.TestingNode;
import com.todaysoft.lims.schedule.model.TestingNodeSearchRequest;

@Service
public class ProductAdapter extends AbstractAdapter implements IProductAdapter
{

    private static final String SERVICE_NAME = "lims-product-service";

    @Autowired
    private RestTemplate template;

    @Override
    public String getName()
    {
        return SERVICE_NAME;
    }

    @Override
    public List<TestingNode> getTestingNodes(String sourceSampleType, String targetSampleType) {
        TestingNodeSearchRequest request = new TestingNodeSearchRequest();
        request.setSourceId(sourceSampleType);
        request.setTargetId(targetSampleType);
        String url = getServiceUrl("/bcm/sample/getPreTestingTaskNode");
        ResponseEntity<List<TestingNode>> exchange =
                template.exchange(url, HttpMethod.POST, new HttpEntity<TestingNodeSearchRequest>(request), new ParameterizedTypeReference<List<TestingNode>>(){});
        return exchange.getBody();
    }
}
