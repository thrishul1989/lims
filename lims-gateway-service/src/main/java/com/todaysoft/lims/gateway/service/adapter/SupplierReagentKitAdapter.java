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
import com.todaysoft.lims.gateway.model.request.ProbeCreateRequest;

@Component
public class SupplierReagentKitAdapter extends AbstractAdapter{

	
	private static final String SERVICE_NAME = "lims-metadata-service";

	@Autowired
	private RestTemplate template;

	@HystrixCommand(fallbackMethod = "fallback")
	public Pagination<SupplierReagentKit> paging(SupplierReagentKit request) {
		String url = getServiceUrl("/supplierReagent/kitPaging");
		ResponseEntity<Pagination<SupplierReagentKit>> exchange = template.exchange(url,
				HttpMethod.POST, new HttpEntity<SupplierReagentKit>(request),
				new ParameterizedTypeReference<Pagination<SupplierReagentKit>>() {
				});
		return exchange.getBody();
	}
	

	@HystrixCommand(fallbackMethod = "fallback")
	public Integer createKit(SupplierReagentKit request){
		String url = getServiceUrl("/supplierReagent/createKit");
		return template.postForObject(url, request, Integer.class);
	}
	
	@HystrixCommand(fallbackMethod = "fallback")
    public void delete(Integer id){
        String url = getServiceUrl("/supplierReagent/deleteKit/{kitId}");
        template.delete(url, Collections.singletonMap("kitId", id));
    }
	
	
	@HystrixCommand(fallbackMethod = "fallback")
	public Integer updateKitPrice(SupplierReagentKit supplierReagentKit){
		String url = getServiceUrl("/supplierReagent/updateKitPrice");
		return template.postForObject(url, supplierReagentKit, Integer.class);
	}
	
	

	@Override
	public String getName() {
		return SERVICE_NAME;
	}
}
