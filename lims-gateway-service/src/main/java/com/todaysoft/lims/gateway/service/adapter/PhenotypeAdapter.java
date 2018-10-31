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
import com.todaysoft.lims.gateway.model.Phenotype;
import com.todaysoft.lims.gateway.model.request.PhenotypeRequest;

@Component
public class PhenotypeAdapter extends AbstractAdapter {

	 private static final String SERVICE_NAME = "lims-product-service";
	
	@Autowired
	private RestTemplate template;
	
	@Override
	public String getName() {
		return SERVICE_NAME;
	}
	
	
	@HystrixCommand(fallbackMethod = "fallback")
	public Pagination<Phenotype> paging(PhenotypeRequest request) {
		String url = getServiceUrl("/phenotype/list.do");
		ResponseEntity<Pagination<Phenotype>> exchange = template.exchange(url, HttpMethod.POST, new HttpEntity<PhenotypeRequest>(request), new ParameterizedTypeReference<Pagination<Phenotype>>(){
		});
		return exchange.getBody();
	}
	
	
	@HystrixCommand(fallbackMethod = "fallback")
	public Phenotype getPhenotypeById(String id) {
		String url = getServiceUrl("/phenotype/getPhenotype/{id}");
		return template.getForObject(url, Phenotype.class, Collections.singletonMap("id", id));
	}
	
	@HystrixCommand(fallbackMethod = "fallback")
	public Integer create(Phenotype request) {
		String url = getServiceUrl("/phenotype/create.do");
		return template.postForObject(url, request, Integer.class);
	}
	
	
	@HystrixCommand(fallbackMethod = "fallback")
	public void modify(Phenotype request) {
		String url = getServiceUrl("/phenotype/modify.do");
		template.postForObject(url, request, Integer.class);
	}
	
	
	@HystrixCommand(fallbackMethod = "fallback")
	public void delete(String id) {
		String url = getServiceUrl("/phenotype/delete/{id}");
        template.delete(url, Collections.singletonMap("id", id));
	}
	
	@HystrixCommand(fallbackMethod = "fallback")
	public Boolean checkedName(PhenotypeRequest phenotype) {
		String url = getServiceUrl("/phenotype/validate.do");
		 return  template.postForObject(url, phenotype, boolean.class);
		
	}
	@HystrixCommand(fallbackMethod = "fallback")
	public List<Phenotype> getPhenotypeList(PhenotypeRequest request) {
			String url = getServiceUrl("/phenotype/getPhenotypes.do");
			  ResponseEntity<List<Phenotype>> exchange =
			            template.exchange(url, HttpMethod.POST, new HttpEntity<PhenotypeRequest>(request), new ParameterizedTypeReference<List<Phenotype>>()
			            {
			            });
			        return exchange.getBody();
	}

}
