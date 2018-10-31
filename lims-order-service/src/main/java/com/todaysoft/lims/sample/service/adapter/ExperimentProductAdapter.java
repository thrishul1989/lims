package com.todaysoft.lims.sample.service.adapter;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.todaysoft.lims.sample.model.ExperimentProduct;

@Component
public class ExperimentProductAdapter extends AbstractAdapter {
	
	private static final String SERVICE_NAME = "lims-metadata-service";
	
	@Autowired
	private RestTemplate template;
	
	
	public List<ExperimentProduct> list(ExperimentProduct request){
		String url = getServiceUrl("/experimentProduct/list");
		ResponseEntity<List<ExperimentProduct>> exchange = template.exchange(url, HttpMethod.POST, new HttpEntity<ExperimentProduct>(request), new ParameterizedTypeReference<List<ExperimentProduct>>(){
		});
		return exchange.getBody();
	}
	
	
	@Override
	public String getName() {
		return SERVICE_NAME;
	}

}
