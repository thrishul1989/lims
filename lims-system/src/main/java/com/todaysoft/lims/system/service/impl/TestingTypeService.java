package com.todaysoft.lims.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.todaysoft.lims.system.model.vo.Product;
import com.todaysoft.lims.system.model.vo.TestingType;
import com.todaysoft.lims.system.service.ITestingTypeService;

@Service
public class TestingTypeService implements ITestingTypeService{
	
    @Autowired
    private RestTemplate template;
    

	@Override
	public List<TestingType> testingTypeList() {
		  String url = GatewayService.getServiceUrl("/bcm/testingType/testingTypeList");
	        ResponseEntity<List<TestingType>> exchange =
	            template.exchange(url, HttpMethod.POST, new HttpEntity<TestingType>(new TestingType()), new ParameterizedTypeReference<List<TestingType>>()
	            {
	            });
	        return exchange.getBody();
	}


	@Override
	public List<TestingType> testingSubtypeList(TestingType request) {
		 String url = GatewayService.getServiceUrl("/bcm/testingType/testingSubtypeList");
	        ResponseEntity<List<TestingType>> exchange =
	            template.exchange(url, HttpMethod.POST, new HttpEntity<TestingType>(request), new ParameterizedTypeReference<List<TestingType>>()
	            {
	            });
	        return exchange.getBody();
	}


    @Override
    public TestingType get(String id)
    {
        String url = GatewayService.getServiceUrl("/bcm/testingType/{id}");
        TestingType testingType = template.getForObject(url, TestingType.class, id);
        return testingType;
    }
}
