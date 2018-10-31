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
import com.todaysoft.lims.gateway.model.request.disease.DiseaseFormRequest;
import com.todaysoft.lims.gateway.model.request.disease.DiseasePagingRequest;
import com.todaysoft.lims.gateway.model.request.disease.Gene;
import com.todaysoft.lims.gateway.model.request.disease.DiseaseGeneFormRequest;
import com.todaysoft.lims.gateway.model.request.disease.DiseaseGenePagingRequest;

@Component
public class DiseaseAdapter extends AbstractAdapter {
	
	private static final String SERVICE_NAME = "lims-product-service";
	
	@Autowired
	private RestTemplate template;
	
	@Override
	public String getName() {
		return SERVICE_NAME;
	}
	
	@HystrixCommand(fallbackMethod = "fallback")
	public String create(DiseaseGeneFormRequest request) {
		String url = getServiceUrl("/disease/createGene");
		return template.postForObject(url, request, String.class);
	}

	public Pagination<Gene> paging(DiseaseGenePagingRequest request) {
		String url = getServiceUrl("/disease/pagingGene");
        ResponseEntity<Pagination<Gene>> exchange =
        template.exchange(url, HttpMethod.POST, new HttpEntity<DiseaseGenePagingRequest>(request), 
        		new ParameterizedTypeReference<Pagination<Gene>>()
        {
        });

        return exchange.getBody();
	}

	public void deleteGene(String id) {
		String url = getServiceUrl("/disease/deleteGene/{id}");
        template.delete(url, Collections.singletonMap("id", id));		
	}

	public Pagination<Disease> pagingDisease(DiseasePagingRequest request) {
		String url = getServiceUrl("/disease/pagingDisease");
        ResponseEntity<Pagination<Disease>> exchange =
        template.exchange(url, HttpMethod.POST, new HttpEntity<DiseasePagingRequest>(request), 
        		new ParameterizedTypeReference<Pagination<Disease>>()
        {
        });

        return exchange.getBody();
	}

	public String createDisease(DiseaseFormRequest request) {
		String url = getServiceUrl("/disease/createDisease");
		return template.postForObject(url, request, String.class);
	}

	public void deleteDisease(String id) {
		String url = getServiceUrl("/disease/deleteDisease/{id}");
        template.delete(url, Collections.singletonMap("id", id));
	}

	public List<Gene> geneList(DiseaseGenePagingRequest searcher) {
		String url = getServiceUrl("/disease/geneSelect");
		ResponseEntity<List<Gene>> exchange =
		template.exchange(url, HttpMethod.POST, new HttpEntity<DiseaseGenePagingRequest>(searcher), new ParameterizedTypeReference<List<Gene>>()
		{
		});
		return exchange.getBody();
	}

	public Gene getGeneById(String id) {
		String url = getServiceUrl("/disease/getGeneById/{id}");
		return template.getForObject(url, Gene.class, Collections.singletonMap("id", id));
	}
	
	public Gene getGeneByCode(String code) {
		String url = getServiceUrl("/disease/getGeneByCode/{code}");
		return template.getForObject(url, Gene.class, Collections.singletonMap("code", code));
	}
	
	

	public boolean validateDiseaseName(DiseaseFormRequest request) {
		 String url = getServiceUrl("/disease/validateDiseaseName");
	     return  template.postForObject(url, request, boolean.class);
	}

	public boolean validateGeneName(DiseaseGeneFormRequest request) {
		 String url = getServiceUrl("/disease/validateGeneName");
	     return  template.postForObject(url, request, boolean.class);
	}

	public String updateGene(DiseaseGeneFormRequest request) {
		String url = getServiceUrl("/disease/updateGene");
		return template.postForObject(url, request, String.class);
	}

	public Disease getDiseaseById(String id) {
		String url = getServiceUrl("/disease/getDiseaseById/{id}");
		return template.getForObject(url, Disease.class, Collections.singletonMap("id", id));
	}

	public String updateDisease(DiseaseFormRequest request) {
		String url = getServiceUrl("/disease/updateDisease");
		return template.postForObject(url, request, String.class);
	}

	public boolean validateDiseaseCode(DiseaseGeneFormRequest request) {
		 String url = getServiceUrl("/disease/validateDiseaseCode");
	     return  template.postForObject(url, request, boolean.class);
	}

	public boolean validateGeneCode(DiseaseGeneFormRequest request) {
		 String url = getServiceUrl("/disease/validateGeneCode");
	     return  template.postForObject(url, request, boolean.class);
	}

	public List<Disease> diseaseSelect(DiseasePagingRequest request) {
		String url = getServiceUrl("/disease/diseaseSelect");
		ResponseEntity<List<Disease>> exchange =
		template.exchange(url, HttpMethod.POST, new HttpEntity<DiseasePagingRequest>(request), 
				new ParameterizedTypeReference<List<Disease>>()
		{
		});
		return exchange.getBody();
	}
	
	public Disease getDiseaseByCode(String code) {
		String url = getServiceUrl("/disease/getDiseaseByCode/{code}");
		return template.getForObject(url, Disease.class, Collections.singletonMap("code", code));
	}
	
	
	
}
