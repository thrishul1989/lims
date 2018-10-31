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
import com.todaysoft.lims.gateway.model.Probe;
import com.todaysoft.lims.gateway.model.Sysconfig;
import com.todaysoft.lims.gateway.model.request.ProbeCreateRequest;
import com.todaysoft.lims.gateway.model.request.ProbeListRequest;
import com.todaysoft.lims.gateway.model.request.ProbeModifyRequest;
import com.todaysoft.lims.gateway.model.request.ProbePagingRequest;

@Component
public class SysconfigAdapter extends AbstractAdapter{

	
	private static final String SERVICE_NAME = "lims-sysconfig-service";

	@Autowired
	private RestTemplate template;

	@HystrixCommand(fallbackMethod = "fallback")
	public Pagination<Sysconfig> paging(Sysconfig request) {
		String url = getServiceUrl("/sysconfig/paging");
		ResponseEntity<Pagination<Sysconfig>> exchange = template.exchange(url,
				HttpMethod.POST, new HttpEntity<Sysconfig>(request),
				new ParameterizedTypeReference<Pagination<Sysconfig>>() {
				});
		return exchange.getBody();
	}
	
	@HystrixCommand(fallbackMethod = "fallback")
	public List<Sysconfig> list(Sysconfig request) {
		String url = getServiceUrl("/sysconfig/list");
		ResponseEntity<List<Sysconfig>> exchange = template.exchange(url,
				HttpMethod.POST, new HttpEntity<Sysconfig>(request),
				new ParameterizedTypeReference<List<Sysconfig>>() {
				});
		return exchange.getBody();
	}

	@HystrixCommand(fallbackMethod = "fallback")
	public Sysconfig get(Integer id){
		String url = getServiceUrl("/sysconfig/{id}");
		return template.getForObject(url, Sysconfig.class, Collections.singletonMap("id", id));
	}
	
	@HystrixCommand(fallbackMethod = "fallback")
	public  Sysconfig getByName(String name){
		String url = getServiceUrl("/sysconfig/getByName/{name}");
		
		return template.getForObject(url, Sysconfig.class, Collections.singletonMap("name", name));
	}
	
	
	
	@HystrixCommand(fallbackMethod = "fallback")
	public Integer create(Sysconfig request){
		String url = getServiceUrl("/sysconfig/save");
		return template.postForObject(url, request, Integer.class);
	}
	
	@HystrixCommand(fallbackMethod = "fallback")
	public void modify(Sysconfig request){
		String url = getServiceUrl("/sysconfig/save");
		template.postForObject(url, request, Integer.class);
	}
	
	@HystrixCommand(fallbackMethod = "fallback")
    public void delete(Integer id){
        String url = getServiceUrl("/sysconfig/{id}");
        template.delete(url, Collections.singletonMap("id", id));
    }
	
	@HystrixCommand(fallbackMethod = "fallback")
    public boolean validate(Sysconfig request){
        String url = getServiceUrl("/sysconfig/validate");
      return   template.postForObject(url, request, boolean.class);
    }
	
	
	
	@Override
	public String getName() {
		return SERVICE_NAME;
	}
	
	
}
