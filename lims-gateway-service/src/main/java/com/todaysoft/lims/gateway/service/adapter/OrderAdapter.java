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
import com.todaysoft.lims.gateway.model.Pagination;
import com.todaysoft.lims.gateway.model.request.disease.Disease;
import com.todaysoft.lims.gateway.model.request.disease.DiseasePagingRequest;
import com.todaysoft.lims.gateway.model.request.disease.Gene;
import com.todaysoft.lims.gateway.model.request.order.Order;
import com.todaysoft.lims.gateway.model.request.order.OrderFormRequest;
import com.todaysoft.lims.gateway.model.request.order.OrderSearchRequest;


@Component
public class OrderAdapter extends AbstractAdapter {
	
	private static final String SERVICE_NAME = "lims-sampleReceive-service";
	
	@Autowired
	private RestTemplate template;
	
	@Override
	public String getName() {
		return SERVICE_NAME;
	}

	@HystrixCommand(fallbackMethod = "fallback")
	public String create(OrderFormRequest request) {
		String url = getServiceUrl("/order/createOrder");
		return template.postForObject(url, request,String.class);
	}

	@HystrixCommand(fallbackMethod = "fallback")
	public Order getOrderById(String id) {
		String url = getServiceUrl("/order/getOrder/{id}");
		return template.getForObject(url, Order.class, Collections.singletonMap("id", id));
	}

	@HystrixCommand(fallbackMethod = "fallback")
	public List<Order> list(OrderSearchRequest request) {
		String url = getServiceUrl("/order/orderList");
		ResponseEntity<List<Order>> exchange =
		template.exchange(url, HttpMethod.POST, new HttpEntity<OrderSearchRequest>(request), 
			new ParameterizedTypeReference<List<Order>>()
		{
		});
		return exchange.getBody();
	}
	@HystrixCommand(fallbackMethod = "fallback")
	public Pagination<Order> paging(OrderSearchRequest request) {
		String url = getServiceUrl("/order/pagingOrder");
        ResponseEntity<Pagination<Order>> exchange =
        template.exchange(url, HttpMethod.POST, new HttpEntity<OrderSearchRequest>(request), 
        		new ParameterizedTypeReference<Pagination<Order>>()
        {
        });
        return exchange.getBody();
	}

	@HystrixCommand(fallbackMethod = "fallback")
	public String update(OrderFormRequest request) {
		String url = getServiceUrl("/order/updateOrder");
		return template.postForObject(url, request,String.class);
	}

	@HystrixCommand(fallbackMethod = "fallback")
	public void delete(String id) {
	   String url = getServiceUrl("/order/deleteOrderById/{id}");
       template.delete(url, Collections.singletonMap("id", id));
	}

}
