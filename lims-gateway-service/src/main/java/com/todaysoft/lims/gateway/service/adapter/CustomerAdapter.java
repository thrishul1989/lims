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
import com.todaysoft.lims.gateway.model.Customer;
import com.todaysoft.lims.gateway.model.Firm;
import com.todaysoft.lims.gateway.model.Pagination;
import com.todaysoft.lims.gateway.model.UserDetailsModel;


@Component
public class CustomerAdapter extends AbstractAdapter {
	private static final String SERVICE_NAME = "lims-metadata-service";

	@Autowired
	private RestTemplate template;
	


	@HystrixCommand(fallbackMethod = "fallback")
	public Pagination<Customer> paging(Customer request) {
		String url = getServiceUrl("/customer/paging");
		ResponseEntity<Pagination<Customer>> exchange = template.exchange(url,
				HttpMethod.POST, new HttpEntity<Customer>(request),
				new ParameterizedTypeReference<Pagination<Customer>>() {
				});
		return exchange.getBody();
	}
	
	@HystrixCommand(fallbackMethod = "fallback")
	public List<Customer> list(Customer request) {
		String url = getServiceUrl("/customer/list");
		ResponseEntity<List<Customer>> exchange = template.exchange(url,
				HttpMethod.POST, new HttpEntity<Customer>(request),
				new ParameterizedTypeReference<List<Customer>>() {
				});
		return exchange.getBody();
	}

	@HystrixCommand(fallbackMethod = "fallback")
	public Customer get(Integer id){
		String url = getServiceUrl("/customer/{id}");
		return template.getForObject(url, Customer.class, Collections.singletonMap("id", id));
	}
	
	@HystrixCommand(fallbackMethod = "fallback")
	public Integer create(Customer request){
		String url = getServiceUrl("/customer/create");
		return template.postForObject(url, request, Integer.class);
	}
	
	@HystrixCommand(fallbackMethod = "fallback")
	public void modify(Customer request){
		String url = getServiceUrl("/customer/modify");
		template.postForObject(url, request, Integer.class);
	}
	
	@HystrixCommand(fallbackMethod = "fallback")
    public void delete(Integer id){
        String url = getServiceUrl("/customer/{id}");
        template.delete(url, Collections.singletonMap("id", id));
    }
	
	@HystrixCommand(fallbackMethod = "fallback")
    public boolean validate(Customer request){
        String url = getServiceUrl("/customer/validate");
      return   template.postForObject(url, request, boolean.class);
    }
	

	@HystrixCommand(fallbackMethod = "fallback")
	public void enableCustomer(Customer request){
		String url = getServiceUrl("/customer/enableCustomer");
		template.postForObject(url, request, Integer.class);
	}
	
	
	
	@Override
	public String getName() {
		return SERVICE_NAME;
	}

	public List<Customer> getCustomers(List<Integer> customerIds) {
		String url = getServiceUrl("/customer/getCustomers");
	    ResponseEntity<List<Customer>> exchange =
        template.exchange(url, HttpMethod.POST, new HttpEntity<List<Integer>>(customerIds), new ParameterizedTypeReference<List<Customer>>()
        {
        });
	    return exchange.getBody();
	}

}
