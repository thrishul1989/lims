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
import com.todaysoft.lims.gateway.model.Product;
import com.todaysoft.lims.gateway.model.request.ProbeCreateRequest;
import com.todaysoft.lims.gateway.model.request.ProbeListRequest;
import com.todaysoft.lims.gateway.model.request.ProbeModifyRequest;
import com.todaysoft.lims.gateway.model.request.ProbePagingRequest;

@Component
public class ProbeAdapter extends AbstractAdapter {
	private static final String SERVICE_NAME = "lims-metadata-service";

	@Autowired
	private RestTemplate template;

	@HystrixCommand(fallbackMethod = "fallback")
	public Pagination<Probe> paging(ProbePagingRequest request) {
		String url = getServiceUrl("/probe/paging");
		ResponseEntity<Pagination<Probe>> exchange = template.exchange(url,
				HttpMethod.POST, new HttpEntity<ProbePagingRequest>(request),
				new ParameterizedTypeReference<Pagination<Probe>>() {
				});
		return exchange.getBody();
	}
	
	@HystrixCommand(fallbackMethod = "fallback")
	public List<Probe> list(ProbeListRequest request) {
		String url = getServiceUrl("/probe/list");
		ResponseEntity<List<Probe>> exchange = template.exchange(url,
				HttpMethod.POST, new HttpEntity<ProbeListRequest>(request),
				new ParameterizedTypeReference<List<Probe>>() {
				});
		return exchange.getBody();
	}

	@HystrixCommand(fallbackMethod = "fallback")
	public Probe get(String id){
		String url = getServiceUrl("/probe/{id}");
		return template.getForObject(url, Probe.class, Collections.singletonMap("id", id));
	}
	
	@HystrixCommand(fallbackMethod = "fallback")
	public String create(ProbeCreateRequest request){
		String url = getServiceUrl("/probe/create");
		return template.postForObject(url, request, String.class);
	}
	
	@HystrixCommand(fallbackMethod = "fallback")
	public void modify(ProbeModifyRequest request){
		String url = getServiceUrl("/probe/modify");
		template.postForObject(url, request, String.class);
	}
	
	@HystrixCommand(fallbackMethod = "fallback")
    public void delete(String id){
        String url = getServiceUrl("/probe/{id}");
        template.delete(url, Collections.singletonMap("id", id));
    }
	
	@HystrixCommand(fallbackMethod = "fallback")
    public boolean validate(Probe request){
        String url = getServiceUrl("/probe/validate");
      return   template.postForObject(url, request, boolean.class);
    }
	
	
	
	@Override
	public String getName() {
		return SERVICE_NAME;
	}

	public List<Probe> getProbeList(List<Integer> ids) {
		// TODO Auto-generated method stub
		String url = getServiceUrl("/probe/getProbeList");
    	ResponseEntity<List<Probe>> exchange = template.exchange(url, HttpMethod.POST, new HttpEntity<List<Integer>>(ids), new ParameterizedTypeReference<List<Probe>>(){
    	});
    	return exchange.getBody();
	}

	
	
	public String getProbeNext(){
		String url = getServiceUrl("/probe/getProbeNext");
		return template.postForObject(url,"get",String.class);
	}

}
