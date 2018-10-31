package com.todaysoft.lims.gateway.service.adapter;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.todaysoft.lims.gateway.model.Pagination;
import com.todaysoft.lims.gateway.model.SupplierReagent;
import com.todaysoft.lims.gateway.model.SupplierReagentKit;
import com.todaysoft.lims.gateway.model.request.PhenotypeRequest;


@Component
public class SupplierReagentAdapter extends AbstractAdapter{

	

	private static final String SERVICE_NAME = "lims-metadata-service";

	@Autowired
	private RestTemplate template;

	@HystrixCommand(fallbackMethod = "fallback")
	public Pagination<SupplierReagent> paging(SupplierReagent request) {
		String url = getServiceUrl("/supplierReagent/reagentPaging");
		ResponseEntity<Pagination<SupplierReagent>> exchange = template.exchange(url,
				HttpMethod.POST, new HttpEntity<SupplierReagent>(request),
				new ParameterizedTypeReference<Pagination<SupplierReagent>>() {
				});
		return exchange.getBody();
	}
	

	@HystrixCommand(fallbackMethod = "fallback")
	public Integer create(SupplierReagent request){
		String url = getServiceUrl("/supplierReagent/createReagent");
		return template.postForObject(url, request, Integer.class);
	}
	
	@HystrixCommand(fallbackMethod = "fallback")
    public void delete(Integer id){
        String url = getServiceUrl("/supplierReagent/deleteReagent/{reagentId}");
        template.delete(url, Collections.singletonMap("reagentId", id));
    }
	@HystrixCommand(fallbackMethod = "fallback")
	public Integer updateReagentPrice(SupplierReagent supplierReagent){
		String url = getServiceUrl("/supplierReagent/updateReagentPrice");
		return template.postForObject(url, supplierReagent, Integer.class);
	}
	

	@HystrixCommand(fallbackMethod = "fallback")
	public Boolean validate(SupplierReagent supplierReagent) {
		
		String url = getServiceUrl("/supplierReagent/validateReagent.do");
		return  template.postForObject(url, supplierReagent, boolean.class);
	}
	
	@HystrixCommand(fallbackMethod = "fallback")
	public Boolean validate(SupplierReagentKit supplierReagent) {
		
		String url = getServiceUrl("/supplierReagent/validateReagentKit.do");
		return  template.postForObject(url, supplierReagent, boolean.class);
	}
	
	
	

	@Override
	public String getName() {
		return SERVICE_NAME;
	}
}
