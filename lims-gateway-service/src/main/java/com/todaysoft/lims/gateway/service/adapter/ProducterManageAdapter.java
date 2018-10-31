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
import com.todaysoft.lims.gateway.model.DataArea;
import com.todaysoft.lims.gateway.model.Pagination;
import com.todaysoft.lims.gateway.model.ProducterManage;
import com.todaysoft.lims.gateway.model.request.ProducterManageRequest;


@Component
public class ProducterManageAdapter extends AbstractAdapter{
	private static final String SERVICE_NAME = "lims-metadata-service";

	@Autowired
	private RestTemplate template;

	@HystrixCommand(fallbackMethod = "fallback")
	public Pagination<ProducterManage> paging(ProducterManageRequest request) {
		String url = getServiceUrl("/producterManage/paging");
		ResponseEntity<Pagination<ProducterManage>> exchange = template.exchange(url,
				HttpMethod.POST, new HttpEntity<ProducterManageRequest>(request),
				new ParameterizedTypeReference<Pagination<ProducterManage>>() {
				});
		return exchange.getBody();
	}
	
	@HystrixCommand(fallbackMethod = "fallback")
	public List<ProducterManage> list(ProducterManage request) {
		String url = getServiceUrl("/producterManage/list");
		ResponseEntity<List<ProducterManage>> exchange = template.exchange(url,
				HttpMethod.POST, new HttpEntity<ProducterManage>(request),
				new ParameterizedTypeReference<List<ProducterManage>>() {
				});
		return exchange.getBody();
	}

	@HystrixCommand(fallbackMethod = "fallback")
	public ProducterManage get(String id){
		String url = getServiceUrl("/producterManage/get/{id}");
		return template.getForObject(url, ProducterManage.class, Collections.singletonMap("id", id));
	}
	
	@HystrixCommand(fallbackMethod = "fallback")
	public String create(ProducterManage request){
		String url = getServiceUrl("/producterManage/create");
		return template.postForObject(url, request, String.class);
	}
	
	@HystrixCommand(fallbackMethod = "fallback")
	public void modify(ProducterManage request){
		String url = getServiceUrl("/producterManage/modify");
		template.postForObject(url, request, String.class);
	}
	
	@HystrixCommand(fallbackMethod = "fallback")
    public void delete(String id){
        String url = getServiceUrl("/producterManage/{id}");
        template.delete(url, Collections.singletonMap("id", id));
    }
	
	@HystrixCommand(fallbackMethod = "fallback")
    public boolean validate(ProducterManage request){
        String url = getServiceUrl("/producterManage/validate");
      return   template.postForObject(url, request, boolean.class);
    }


	@HystrixCommand(fallbackMethod = "fallback")
    public boolean deleteEmail(ProducterManage data){
        String url = getServiceUrl("/producterManage/deleteEmail");
        return template.postForObject(url, data, boolean.class);
    }
	
	@HystrixCommand(fallbackMethod = "fallback")
	public List<DataArea> findRoots() {
		String url = getServiceUrl("/producterManage/findRoots");
		ResponseEntity<List<DataArea>> exchange = template.exchange(url,
				HttpMethod.GET, null,new ParameterizedTypeReference<List<DataArea>>() {
				});
		return exchange.getBody();
	}

	@HystrixCommand(fallbackMethod = "fallback")
	public DataArea getDataAreaById(String parentId){
		String url = getServiceUrl("/producterManage/getByParentId/{parentId}");
		return template.getForObject(url, DataArea.class, Collections.singletonMap("parentId", parentId));
	}
	
	@Override
	public String getName() {
		return SERVICE_NAME;
	}

	
}
