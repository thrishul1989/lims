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
import com.todaysoft.lims.gateway.model.analyze.AnalyzeMethod;
import com.todaysoft.lims.gateway.model.analyze.AnalyzeMethodPagingRequest;

@Component
public class AnalyzeAdapter extends AbstractAdapter {
	
	private static final String SERVICE_NAME = "lims-metadata-service";
	
	@Autowired
	private RestTemplate template;
	
	@Override
	public String getName() {
		return SERVICE_NAME;
	}
	
	
	@HystrixCommand(fallbackMethod = "fallback")
	public Pagination<AnalyzeMethod> paging(AnalyzeMethodPagingRequest request) {
		String url = getServiceUrl("/analyzeMethod/paging");
		ResponseEntity<Pagination<AnalyzeMethod>> exchange = template.exchange(url, HttpMethod.POST, 
				new HttpEntity<AnalyzeMethodPagingRequest>(request), new ParameterizedTypeReference<Pagination<AnalyzeMethod>>(){
		});
		return exchange.getBody();
	}
	
	
	@HystrixCommand(fallbackMethod = "fallback")
	public AnalyzeMethod getMethodById(Integer id) {
		String url = getServiceUrl("/analyzeMethod/{id}");
		return template.getForObject(url, AnalyzeMethod.class, Collections.singletonMap("id", id));
	}
	
	@HystrixCommand(fallbackMethod = "fallback")
	public Integer create(AnalyzeMethod request) {
		String url = getServiceUrl("/analyzeMethod/create");
		return template.postForObject(url, request, Integer.class);
	}
	
	
	@HystrixCommand(fallbackMethod = "fallback")
	public Integer modify(AnalyzeMethod request) {
		String url = getServiceUrl("/analyzeMethod/modify");
		return template.postForObject(url, request, Integer.class);
	}
	
	
	@HystrixCommand(fallbackMethod = "fallback")
	public void delete(Integer id) {
		String url = getServiceUrl("/analyzeMethod/{id}");
        template.delete(url, Collections.singletonMap("id", id));
	}


	public boolean validate(AnalyzeMethod request) {
		 String url = getServiceUrl("/analyzeMethod/validate");
	     return  template.postForObject(url, request, boolean.class);
	}


	public List<AnalyzeMethod> list(AnalyzeMethod request) {
		String url = getServiceUrl("/analyzeMethod/list");
		ResponseEntity<List<AnalyzeMethod>> exchange = template.exchange(url,
				HttpMethod.POST, new HttpEntity<AnalyzeMethod>(request),
				new ParameterizedTypeReference<List<AnalyzeMethod>>() {
		});
		return exchange.getBody();
	}

}
