package com.todaysoft.lims.testing.base.adapter.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.todaysoft.lims.testing.base.adapter.IProductAdapter;
import com.todaysoft.lims.testing.base.model.TaskConfig;
import com.todaysoft.lims.testing.base.model.TestingNode;
import com.todaysoft.lims.testing.base.request.TestingNodeSearchRequest;
import com.todaysoft.lims.utils.Collections3;

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
    public TaskConfig getTaskCOnfigBySemantic(String semantic) {
        String url = getServiceUrl("/bcm/task/getBySemantic/{semantic}");
        return template.getForObject(url, TaskConfig.class, Collections.singletonMap("semantic", semantic));
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

    @Override
    public String getAddressConfigByKey(String key)
    {
        String url = getServiceUrl("/bcm/configs/{key}");
        ResponseEntity<List<String>> exchange =
            template.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<String>>(){},Collections.singletonMap("key", key));
        List<String> values = exchange.getBody();
        return Collections3.isNotEmpty(values) ? values.get(0) : null;
    }
}
