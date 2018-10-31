package com.todaysoft.lims.system.service.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.todaysoft.lims.system.model.searcher.ProbeSeacher;
import com.todaysoft.lims.system.model.vo.KitStorage;
import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.model.vo.Probe;
import com.todaysoft.lims.system.service.IKitStorageService;
@Service
public class KitStorageService extends RestService implements IKitStorageService{
	@Override
	public Pagination<KitStorage> paging(KitStorage searcher, int pageNo, int pageSize) {
		
		searcher.setPageNo(pageNo);
		searcher.setPageSize(pageSize);
        
        String url = GatewayService.getServiceUrl("/kitStorage/paging");
        ResponseEntity<Pagination<KitStorage>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<KitStorage>(searcher), new ParameterizedTypeReference<Pagination<KitStorage>>()
            {
            });
        return exchange.getBody();
	}
	
	
	@Override
	public void modifyReaction(KitStorage request) {

        String url = GatewayService.getServiceUrl("/kitStorage/modify");
        template.postForObject(url, request, String.class);
		
	}

	

	@Override
	public void delete(Integer id) {
		 String url = GatewayService.getServiceUrl("/kitStorage/{id}");
	        template.delete(url, Collections.singletonMap("id", id));
		
	}

	@Override
	public void create(KitStorage request) {
		 String url = GatewayService.getServiceUrl("/kitStorage/create");
	        template.postForObject(url, request, String.class);
		
	}


	@Override
	public List<KitStorage> list(KitStorage request) {
		String url =  GatewayService.getServiceUrl("/kitStorage/list");
		ResponseEntity<List<KitStorage>> exchange = template.exchange(url,
				HttpMethod.POST, new HttpEntity<KitStorage>(request),
				new ParameterizedTypeReference<List<KitStorage>>() {
				});
		return exchange.getBody();
	}


	@Override
	public Integer countByKitId(KitStorage request) {
		String url = GatewayService.getServiceUrl("/kitStorage/countByKitId");
		return template.postForObject(url, request, Integer.class);
	}
	
	
	
}
