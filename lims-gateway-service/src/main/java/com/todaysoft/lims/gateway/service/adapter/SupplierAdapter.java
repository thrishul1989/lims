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
import com.todaysoft.lims.gateway.model.ReagentKit;
import com.todaysoft.lims.gateway.model.Supplier;
import com.todaysoft.lims.gateway.model.request.ProbeCreateRequest;
import com.todaysoft.lims.gateway.model.request.ProbeListRequest;
import com.todaysoft.lims.gateway.model.request.ProbeModifyRequest;


@Component
public class SupplierAdapter extends AbstractAdapter{
	private static final String SERVICE_NAME = "lims-metadata-service";

	@Autowired
	private RestTemplate template;

	@HystrixCommand(fallbackMethod = "fallback")
	public Pagination<Supplier> paging(Supplier request) {
		String url = getServiceUrl("/supplier/paging");
		ResponseEntity<Pagination<Supplier>> exchange = template.exchange(url,
				HttpMethod.POST, new HttpEntity<Supplier>(request),
				new ParameterizedTypeReference<Pagination<Supplier>>() {
				});
		return exchange.getBody();
	}
	
	@HystrixCommand(fallbackMethod = "fallback")
	public List<Supplier> list(Supplier request) {
		String url = getServiceUrl("/supplier/list");
		ResponseEntity<List<Supplier>> exchange = template.exchange(url,
				HttpMethod.POST, new HttpEntity<Supplier>(request),
				new ParameterizedTypeReference<List<Supplier>>() {
				});
		return exchange.getBody();
	}

	@HystrixCommand(fallbackMethod = "fallback")
	public Supplier get(Integer id){
		String url = getServiceUrl("/supplier/{id}");
		return template.getForObject(url, Supplier.class, Collections.singletonMap("id", id));
	}
	
	@HystrixCommand(fallbackMethod = "fallback")
	public Integer create(Supplier request){
		String url = getServiceUrl("/supplier/create");
		return template.postForObject(url, request, Integer.class);
	}
	
	@HystrixCommand(fallbackMethod = "fallback")
	public void modify(Supplier request){
		String url = getServiceUrl("/supplier/modify");
		template.postForObject(url, request, Integer.class);
	}
	
	@HystrixCommand(fallbackMethod = "fallback")
    public void delete(Integer id){
        String url = getServiceUrl("/supplier/{id}");
        template.delete(url, Collections.singletonMap("id", id));
    }
	
	@HystrixCommand(fallbackMethod = "fallback")
    public boolean validate(Supplier request){
        String url = getServiceUrl("/supplier/validate");
      return   template.postForObject(url, request, boolean.class);
    }
	
	
	 @HystrixCommand(fallbackMethod = "fallback")
	    public Pagination<Supplier> selectSupplier(Supplier request)
	    {
	        String url = getServiceUrl("/supplier/selectSupplier");
	        ResponseEntity<Pagination<Supplier>> exchange =
	            template.exchange(url, HttpMethod.POST, new HttpEntity<Supplier>(request), new ParameterizedTypeReference<Pagination<Supplier>>()
	            {
	            });
	        return exchange.getBody();
	    }
	
	
	@Override
	public String getName() {
		return SERVICE_NAME;
	}

	public List<Supplier> getSupplierList(List<Integer> ids) {
		// TODO Auto-generated method stub
		String url = getServiceUrl("/supplier/getSupplierList");
    	ResponseEntity<List<Supplier>> exchange = template.exchange(url, HttpMethod.POST, new HttpEntity<List<Integer>>(ids), new ParameterizedTypeReference<List<Supplier>>(){
    	});
    	return exchange.getBody();
	}

}
