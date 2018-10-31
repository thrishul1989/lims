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
import com.todaysoft.lims.gateway.model.OrderExperimentProduct;
import com.todaysoft.lims.gateway.model.request.OrderExperimentProductCreateRequest;
import com.todaysoft.lims.gateway.model.request.OrderExperimentProductListRequest;

@Component
public class OrderExperimentProductAdapter extends AbstractAdapter {

	private static final String SERVICE_NAME = "lims-sampleReceive-service";
	
	@Autowired
	private RestTemplate template;
	
	@HystrixCommand(fallbackMethod = "fallback")
    public List<OrderExperimentProduct> list(OrderExperimentProductListRequest request)
    {
        String url = getServiceUrl("/orderExperimentProduct/list");
        ResponseEntity<List<OrderExperimentProduct>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<OrderExperimentProductListRequest>(request), new ParameterizedTypeReference<List<OrderExperimentProduct>>()
            {
            });
        return exchange.getBody();
    }
	
	@HystrixCommand(fallbackMethod = "fallback")
	public OrderExperimentProduct get(Integer id){
		String url = getServiceUrl("/orderExperimentProduct/{id}");
		return template.getForObject(url, OrderExperimentProduct.class, Collections.singletonMap("id", id));
	}
	
	@HystrixCommand(fallbackMethod = "fallback")
	public Integer create(OrderExperimentProductCreateRequest request){
		String url = getServiceUrl("/orderExperimentProduct/create");
		return template.postForObject(url, request, Integer.class);
	}
	
	@Override
	public String getName() {
		return SERVICE_NAME;
	}

}
