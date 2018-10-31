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

import com.todaysoft.lims.testing.base.adapter.IOrderAdapter;
import com.todaysoft.lims.testing.base.model.OriginalSample;
import com.todaysoft.lims.testing.base.model.TestingNode;
import com.todaysoft.lims.testing.base.model.TestingOrder;
import com.todaysoft.lims.testing.base.model.TestingProduct;
import com.todaysoft.lims.testing.base.request.SampleSearchRequest;
import com.todaysoft.lims.testing.base.request.TestingNodeSearchRequest;
import com.todaysoft.lims.testing.dnaext.model.DNAExtractTaskVariables;

@Service
public class OrderAdapter extends AbstractAdapter implements IOrderAdapter
{
    private static final String SERVICE_NAME = "lims-sampleReceive-service";
    
    @Autowired
    private RestTemplate template;
    
    @Override
    public String getName()
    {
        return SERVICE_NAME;
    }
    
    @Override
    public TestingOrder getTestingOrder(String orderId)
    {
        String url = getServiceUrl("/bmm/order/orders/{id}");
        return template.getForObject(url, TestingOrder.class, Collections.singletonMap("id", orderId));
    }
    
    @Override
    public List<TestingProduct> getOrderProducts(String orderId)
    {
        String url = getServiceUrl("/bmm/order/getOrderProducts/{id}");
        ResponseEntity<List<TestingProduct>> exchange =
            template.exchange(url, HttpMethod.GET, new HttpEntity<String>(orderId), new ParameterizedTypeReference<List<TestingProduct>>()
            {
            }, Collections.singletonMap("id", orderId));
        return exchange.getBody();
    }
    
    @Override
    public List<OriginalSample> getPrimaryTestingSamples(String orderId)
    {
        SampleSearchRequest request = new SampleSearchRequest();
        request.setOrderId(orderId);
        request.setBusinessType("1");
        String url = getServiceUrl("/bmm/order/getTestingSamples");
        ResponseEntity<List<OriginalSample>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<SampleSearchRequest>(request), new ParameterizedTypeReference<List<OriginalSample>>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public List<OriginalSample> getSubsidiaryTestingSamples(String orderId, String sampleId)
    {
        // TODO 查询非科研订单所有类型为检测和对照的附属样本
        SampleSearchRequest request = new SampleSearchRequest();
        request.setOrderId(orderId);
        request.setBusinessType("2");
        request.setSampleId(sampleId);
        String url = getServiceUrl("/bmm/order/getTestingSamples");
        ResponseEntity<List<OriginalSample>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<SampleSearchRequest>(request), new ParameterizedTypeReference<List<OriginalSample>>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public List<TestingNode> getTestingNodes(String sourceId, String targetId)
    {
        TestingNodeSearchRequest request = new TestingNodeSearchRequest();
        request.setSourceId(sourceId);
        request.setTargetId(targetId);
        String url = getServiceUrl("/bmm/order/getTestingTaskNode");
        ResponseEntity<List<TestingNode>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<TestingNodeSearchRequest>(request), new ParameterizedTypeReference<List<TestingNode>>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public List<OriginalSample> getResearchTestingSamples(String orderId)
    {
        SampleSearchRequest request = new SampleSearchRequest();
        request.setOrderId(orderId);
        request.setBusinessType("3");
        String url = getServiceUrl("/bmm/order/getTestingSamples");
        ResponseEntity<List<OriginalSample>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<SampleSearchRequest>(request), new ParameterizedTypeReference<List<OriginalSample>>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public List<TestingProduct> getResearchTestingProducts(String sampleId)
    {
        String url = getServiceUrl("/bmm/order/getResearchTestingProducts/{id}");
        ResponseEntity<List<TestingProduct>> exchange =
            template.exchange(url, HttpMethod.GET, new HttpEntity<String>(sampleId), new ParameterizedTypeReference<List<TestingProduct>>()
            {
            }, Collections.singletonMap("id", sampleId));
        return exchange.getBody();
    }
    
    @Override
    public DNAExtractTaskVariables getReceivedSample(String sampleId)
    {
        String url = getServiceUrl("/bmm/order/getReceivedSample/{id}");
        return template.getForObject(url, DNAExtractTaskVariables.class, Collections.singletonMap("id", sampleId));
    }
}
