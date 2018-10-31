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
import com.todaysoft.lims.gateway.model.Connect;
import com.todaysoft.lims.gateway.model.Pagination;
import com.todaysoft.lims.gateway.model.request.ConnectRequest;

@Component
public class ConnectAdapter extends AbstractAdapter {
	
	private static final String SERVICE_NAME = "lims-metadata-service";
	
	@Autowired
	private RestTemplate template;
	
	@Override
	public String getName() {
		return SERVICE_NAME;
	}
	
	
	@HystrixCommand(fallbackMethod = "fallback")
	public Pagination<Connect> paging(ConnectRequest request) {
		String url = getServiceUrl("/connect/paging");
		ResponseEntity<Pagination<Connect>> exchange = template.exchange(url, HttpMethod.POST, new HttpEntity<ConnectRequest>(request), new ParameterizedTypeReference<Pagination<Connect>>(){
		});
		return exchange.getBody();
	}
	
	
	@HystrixCommand(fallbackMethod = "fallback")
	public Connect getConnectById(String id) {
		String url = getServiceUrl("/connect/{id}");
		return template.getForObject(url, Connect.class, Collections.singletonMap("id", id));
	}
	
	@HystrixCommand(fallbackMethod = "fallback")
	public String create(Connect request) {
		String url = getServiceUrl("/connect/create");
		return template.postForObject(url, request, String.class);
	}
	
	
	@HystrixCommand(fallbackMethod = "fallback")
	public void modify(Connect request) {
		String url = getServiceUrl("/connect/modify");
		template.postForObject(url, request, String.class);
	}
	
	
	@HystrixCommand(fallbackMethod = "fallback")
	public void delete(String id) {
		String url = getServiceUrl("/connect/{id}");
        template.delete(url, Collections.singletonMap("id", id));
	}
	
	@HystrixCommand(fallbackMethod = "fallback")
	public Boolean checkedconnectNum(ConnectRequest connect) {
		String url = getServiceUrl("/connect/chechedConnectNum");
		 return  template.postForObject(url, connect, boolean.class);
		
	}
	@HystrixCommand(fallbackMethod = "fallback")
	public List<Connect> getConnectList(ConnectRequest request) {
			String url = getServiceUrl("/connect/getConnectList");
			  ResponseEntity<List<Connect>> exchange =
			            template.exchange(url, HttpMethod.POST, new HttpEntity<ConnectRequest>(request), new ParameterizedTypeReference<List<Connect>>()
			            {
			            });
			        return exchange.getBody();
	}
	
	@HystrixCommand(fallbackMethod = "fallback")
	public List<Connect> getConnectListById(String ids) {
		String url = getServiceUrl("/connect/getConnectListById/{ids}");
		ResponseEntity<List<Connect>> exchange = template.exchange(url, HttpMethod.GET, new HttpEntity<String>(ids), new ParameterizedTypeReference<List<Connect>>(){
		}, Collections.singletonMap("ids", ids));
		return exchange.getBody();
		
	}
	
	@HystrixCommand(fallbackMethod = "fallback")
	public List<Connect> ConnectListForWkcreate(ConnectRequest request){
		String url = getServiceUrl("/connect/ConnectListForWkcreate");
		 ResponseEntity<List<Connect>> exchange =
		            template.exchange(url, HttpMethod.POST, new HttpEntity<ConnectRequest>(request), new ParameterizedTypeReference<List<Connect>>()
		            {
		            });
		return exchange.getBody();
	}
	
	
}
