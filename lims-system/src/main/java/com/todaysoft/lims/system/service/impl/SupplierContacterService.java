package com.todaysoft.lims.system.service.impl;

import java.util.Collections;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.model.vo.Supplier;
import com.todaysoft.lims.system.model.vo.SupplierContacter;
import com.todaysoft.lims.system.service.ISupplierContacterService;


@Service
public class SupplierContacterService extends RestService implements ISupplierContacterService{

	@Override
	public Pagination<SupplierContacter> paging(SupplierContacter request,
			int pageNo, int pageSize) {
		 request.setPageNo(pageNo);
	        request.setPageSize(pageSize);
	        
	        String url = GatewayService.getServiceUrl("/supplierContacter/paging");
	        ResponseEntity<Pagination<SupplierContacter>> exchange =
	            template.exchange(url, HttpMethod.POST, new HttpEntity<SupplierContacter>(request), new ParameterizedTypeReference<Pagination<SupplierContacter>>()
	            {
	            });
	        return exchange.getBody();
	}
	
	@Override
	public void create(SupplierContacter request) {
		 String url = GatewayService.getServiceUrl("/supplierContacter/create");
	        template.postForObject(url, request, String.class);
		
	}

	@Override
	public void modify(SupplierContacter request) {

        String url = GatewayService.getServiceUrl("/supplierContacter/modify");
        template.postForObject(url, request, String.class);
		
	}

	@Override
	public void delete(Integer id) {
		 String url = GatewayService.getServiceUrl("/supplierContacter/{id}");
	        template.delete(url, Collections.singletonMap("id", id));
		
	}
	
	

}
