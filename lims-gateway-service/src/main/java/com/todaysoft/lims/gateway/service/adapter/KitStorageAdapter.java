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
import com.todaysoft.lims.gateway.model.KitStorage;
import com.todaysoft.lims.gateway.model.Pagination;
import com.todaysoft.lims.gateway.model.Probe;
import com.todaysoft.lims.gateway.model.request.ProbeCreateRequest;
import com.todaysoft.lims.gateway.model.request.ProbeListRequest;
import com.todaysoft.lims.gateway.model.request.ProbeModifyRequest;

@Component
public class KitStorageAdapter extends AbstractAdapter{
	
	private static final String SERVICE_NAME = "lims-metadata-service";
	
	
	@Autowired
	private RestTemplate template;
	
	@HystrixCommand(fallbackMethod = "fallback")
	public Pagination<KitStorage> paging(KitStorage request) {
		String url = getServiceUrl("/kitStorage/paging");
		ResponseEntity<Pagination<KitStorage>> exchange = template.exchange(url,
				HttpMethod.POST, new HttpEntity<KitStorage>(request),
				new ParameterizedTypeReference<Pagination<KitStorage>>() {
				});
		return exchange.getBody();
	}
	
	
	@HystrixCommand(fallbackMethod = "fallback")
	public KitStorage get(Integer id){
		String url = getServiceUrl("/kitStorage/{id}");
		return template.getForObject(url, KitStorage.class, Collections.singletonMap("id", id));
	}
	
	@HystrixCommand(fallbackMethod = "fallback")
	public Integer create(KitStorage request){
		String url = getServiceUrl("/kitStorage/create");
		return template.postForObject(url, request, Integer.class);
	}
	
	@HystrixCommand(fallbackMethod = "fallback")
	public void modify(KitStorage request){
		String url = getServiceUrl("/kitStorage/modifyReaction");
		template.postForObject(url, request, Integer.class);
	}
	
	@HystrixCommand(fallbackMethod = "fallback")
    public void delete(Integer id){
        String url = getServiceUrl("/kitStorage/{id}");
        template.delete(url, Collections.singletonMap("id", id));
    }
	
	@HystrixCommand(fallbackMethod = "fallback")
	public List<KitStorage> list(KitStorage request) {
		String url = getServiceUrl("/kitStorage/list");
		ResponseEntity<List<KitStorage>> exchange = template.exchange(url,
				HttpMethod.POST, new HttpEntity<KitStorage>(request),
				new ParameterizedTypeReference<List<KitStorage>>() {
				});
		return exchange.getBody();
	}
	
	
	@HystrixCommand(fallbackMethod = "fallback")
	public Integer countByKitId(KitStorage request){
		String url = getServiceUrl("/kitStorage/countByKitId");
		return template.postForObject(url, request, Integer.class);
	}
	
	
	
	
	@Override
	public String getName() {
		return SERVICE_NAME;
	}
}
