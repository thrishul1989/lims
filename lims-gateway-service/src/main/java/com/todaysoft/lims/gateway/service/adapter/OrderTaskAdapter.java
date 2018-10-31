package com.todaysoft.lims.gateway.service.adapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.todaysoft.lims.gateway.model.request.OrderTaskCreateRequest;

@Component
public class OrderTaskAdapter extends AbstractAdapter {
	
	private static final String SERVICE_NAME = "lims-sampleReceive-service";
	
	@Autowired
	private RestTemplate template;
	
	@HystrixCommand(fallbackMethod = "fallback")
	public Integer create(OrderTaskCreateRequest request){
		String url = getServiceUrl("/orderTask/create");
		return template.postForObject(url, request, Integer.class);
	}

	@Override
	public String getName() {
		return SERVICE_NAME;
	}

}
