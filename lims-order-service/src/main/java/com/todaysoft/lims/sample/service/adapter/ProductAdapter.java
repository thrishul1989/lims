package com.todaysoft.lims.sample.service.adapter;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.todaysoft.lims.sample.entity.Product;
import com.todaysoft.lims.sample.entity.TestingType;
import com.todaysoft.lims.sample.model.CheckManagerProbe;
import com.todaysoft.lims.sample.model.request.ProductCheckManagerRequest;

@Component
public class ProductAdapter extends AbstractAdapter
{
    private static final String SERVICE_NAME = "lims-product-service";
    
    @Autowired
    private RestTemplate template;
    
    public Product getProduct(String id)
    {
        String url = getServiceUrl("/product/{id}");
        return template.getForObject(url, Product.class, Collections.singletonMap("id", id));
    }
    
    public List<CheckManagerProbe> getProbeInfoByProAndMethod(ProductCheckManagerRequest request)
    {
        String url = getServiceUrl("/product/getProbeInfoByProAndMethod");
        ResponseEntity<List<CheckManagerProbe>> exchange =
            template.exchange(url,
                HttpMethod.POST,
                new HttpEntity<ProductCheckManagerRequest>(request),
                new ParameterizedTypeReference<List<CheckManagerProbe>>()
                {
                });
        return exchange.getBody();
    }
    
    public List<TestingType> testingTypeList()
    {
        String url = getServiceUrl("/bcm/testingType/testingTypeList");
        ResponseEntity<List<TestingType>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<TestingType>(new TestingType()), new ParameterizedTypeReference<List<TestingType>>()
            {
            });
        return exchange.getBody();
    }
    
    public Product get(String id)
    {
        String url = getServiceUrl("/bcm/product/{id}");
        return template.getForObject(url, Product.class, Collections.singletonMap("id", id));
    }
    
    @Override
    public String getName()
    {
        return SERVICE_NAME;
    }
}
