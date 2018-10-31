package com.todaysoft.lims.gateway.service.adapter;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.todaysoft.lims.gateway.model.OrderSample;
import com.todaysoft.lims.gateway.model.Pagination;
import com.todaysoft.lims.gateway.model.request.OrderSampleCreateRequest;
import com.todaysoft.lims.gateway.model.request.OrderSampleListRequest;

@Component
public class OrderSampleAdapter extends AbstractAdapter {
	
	private static final String SERVICE_NAME = "lims-sampleReceive-service";
	
	@Autowired
	private RestTemplate template;
	
	@HystrixCommand(fallbackMethod = "fallback")
	public List<OrderSample> list(OrderSampleListRequest request){
		String url = getServiceUrl("/orderSample/list");
		ResponseEntity<List<OrderSample>> exchange = template.exchange(url,
				HttpMethod.POST, new HttpEntity<OrderSampleListRequest>(request),
				new ParameterizedTypeReference<List<OrderSample>>() {
				});
		return exchange.getBody();
	}
	
	@HystrixCommand(fallbackMethod = "fallback")
	public OrderSample get(Integer id){
		String url = getServiceUrl("/orderSample/{id}");
		return template.getForObject(url, OrderSample.class, Collections.singletonMap("id", id));
	}
	
	@HystrixCommand(fallbackMethod = "fallback")
	public Integer create(OrderSampleCreateRequest request){
		String url = getServiceUrl("/orderSample/create");
		return template.postForObject(url, request, Integer.class);
	}

	@Override
	public String getName() {
		return SERVICE_NAME;
	}

}
