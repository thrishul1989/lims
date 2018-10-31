package com.todaysoft.lims.gateway.service.adapter;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.todaysoft.lims.gateway.model.Pagination;
import com.todaysoft.lims.gateway.model.Supplier;
import com.todaysoft.lims.gateway.model.SupplierContacter;
import com.todaysoft.lims.gateway.model.request.ProbeModifyRequest;


@Component
public class SupplierContacterAdapter extends AbstractAdapter{

	
	private static final String SERVICE_NAME = "lims-metadata-service";

	@Autowired
	private RestTemplate template;
	
	@HystrixCommand(fallbackMethod = "fallback")
	public Pagination<SupplierContacter> paging(SupplierContacter request) {
		String url = getServiceUrl("/supplierContacter/paging");
		ResponseEntity<Pagination<SupplierContacter>> exchange = template.exchange(url,
				HttpMethod.POST, new HttpEntity<SupplierContacter>(request),
				new ParameterizedTypeReference<Pagination<SupplierContacter>>() {
				});
		return exchange.getBody();
	}
	
	@HystrixCommand(fallbackMethod = "fallback")
	public Integer create(SupplierContacter request){
		String url = getServiceUrl("/supplierContacter/create");
		System.out.println(JSON.toJSONString(request));
		SupplierContacter c = JSON.parseObject(JSON.toJSONString(request),SupplierContacter.class);
		return template.postForObject(url, request, Integer.class);
	}
	
	

	@HystrixCommand(fallbackMethod = "fallback")
	public void modify(SupplierContacter request){
		String url = getServiceUrl("/supplierContacter/modify");
		template.postForObject(url, request, Integer.class);
	}
	
	
	@HystrixCommand(fallbackMethod = "fallback")
    public void delete(Integer id){
        String url = getServiceUrl("/supplierContacter/{id}");
        template.delete(url, Collections.singletonMap("id", id));
    }
	
	
	
	

	@Override
	public String getName() {
		return SERVICE_NAME;
	}
	
	
}
