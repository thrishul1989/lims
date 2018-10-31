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
import com.todaysoft.lims.gateway.model.ExperimentProduct;
import com.todaysoft.lims.gateway.model.Pagination;
import com.todaysoft.lims.gateway.model.request.ExperimentProductCreateRequest;
import com.todaysoft.lims.gateway.model.request.ExperimentProductListRequest;
import com.todaysoft.lims.gateway.model.request.ExperimentProductModifyRequest;
import com.todaysoft.lims.gateway.model.request.ExperimentProductPagingRequest;
import com.todaysoft.lims.gateway.model.request.PhenotypeRequest;

@Component
public class ExperimentProductAdapter extends AbstractAdapter {
	
	private static final String SERVICE_NAME = "lims-metadata-service";
	
	@Autowired
	private RestTemplate template;
	
	@HystrixCommand(fallbackMethod = "fallback")
	public Pagination<ExperimentProduct> paging(ExperimentProductPagingRequest request){
		String url = getServiceUrl("/experimentProduct/paging");
		ResponseEntity<Pagination<ExperimentProduct>> exchange = template.exchange(url, HttpMethod.POST, new HttpEntity<ExperimentProductPagingRequest>(request), new ParameterizedTypeReference<Pagination<ExperimentProduct>>(){
		});
		return exchange.getBody();
	}
	
	@HystrixCommand(fallbackMethod = "fallback")
	public List<ExperimentProduct> list(ExperimentProductListRequest request){
		String url = getServiceUrl("/experimentProduct/list");
		ResponseEntity<List<ExperimentProduct>> exchange = template.exchange(url, HttpMethod.POST, new HttpEntity<ExperimentProductListRequest>(request), new ParameterizedTypeReference<List<ExperimentProduct>>(){
		});
		return exchange.getBody();
	}
	
	@HystrixCommand(fallbackMethod = "fallback")
	public ExperimentProduct getExperimentProduct(Integer id){
		String url = getServiceUrl("/experimentProduct/{id}");
		return template.getForObject(url, ExperimentProduct.class, Collections.singletonMap("id", id));
	}
	
	@HystrixCommand(fallbackMethod = "fallback")
	public Integer create(ExperimentProductCreateRequest request){
		String url = getServiceUrl("/experimentProduct/create");
		return template.postForObject(url, request, Integer.class);
	}
	
	@HystrixCommand(fallbackMethod = "fallback")
	public void modify(ExperimentProductModifyRequest request){
		String url = getServiceUrl("/experimentProduct/modify");
		template.postForObject(url, request, Integer.class);
	}
	
	@HystrixCommand(fallbackMethod = "fallback")
	public void delete(Integer id){
		String url = getServiceUrl("/experimentProduct/{id}");
        template.delete(url, Collections.singletonMap("id", id));
	}
	
	@HystrixCommand(fallbackMethod = "fallback")
	public Boolean checkedName(ExperimentProductPagingRequest request) {
		String url = getServiceUrl("/experimentProduct/validateName.do");
		 return  template.postForObject(url, request, boolean.class);
		
	}
	
	@Override
	public String getName() {
		return SERVICE_NAME;
	}

}
