package com.todaysoft.lims.system.service.impl;

import java.util.Collections;

import org.springframework.beans.BeanUtils;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.model.vo.SupplierReagent;
import com.todaysoft.lims.system.model.vo.SupplierReagentKit;
import com.todaysoft.lims.system.service.ISupplierReagentKitService;
import com.todaysoft.lims.system.service.ISupplierReagentService;
@Service
public class SupplierReagentService extends RestService implements ISupplierReagentService{

	
	@Override
	public Pagination<SupplierReagent> paging(SupplierReagent searcher, int pageNo, int pageSize) {
		SupplierReagent request = new SupplierReagent();
        BeanUtils.copyProperties(searcher, request);
        request.setPageNo(pageNo);
        request.setPageSize(pageSize);
        
        String url = GatewayService.getServiceUrl("/supplierReagent/reagentPaging");
        ResponseEntity<Pagination<SupplierReagent>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<SupplierReagent>(request), new ParameterizedTypeReference<Pagination<SupplierReagent>>()
            {
            });
        return exchange.getBody();
	}

	@Override
	public void create(SupplierReagent request) {
		 String url = GatewayService.getServiceUrl("/supplierReagent/createReagent");
	        template.postForObject(url, request, String.class);
	}

	@Override
	public void delete(Integer id) {
		 String url = GatewayService.getServiceUrl("/supplierReagent/deleteReagent/{reagentId}");
	        template.delete(url, Collections.singletonMap("reagentId", id));
		
	}

	@Override
	public void updateReagentPrice(SupplierReagent request) {
		 String url = GatewayService.getServiceUrl("/supplierReagent/updateReagentPrice");
		   template.postForObject(url, request, String.class);
		
	}

	@Override
	public boolean validate(SupplierReagent reagent) {
		
		return template.postForObject(GatewayService.getServiceUrl("/supplierReagent/validateReagent.do"), reagent, boolean.class);
	}
	
	@Override
	public boolean validate(SupplierReagentKit reagent) {
		
		return template.postForObject(GatewayService.getServiceUrl("/supplierReagent/validateReagentKit.do"), reagent, boolean.class);
	}
}
