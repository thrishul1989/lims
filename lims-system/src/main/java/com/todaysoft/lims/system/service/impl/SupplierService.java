package com.todaysoft.lims.system.service.impl;

import java.util.Collections;

import org.springframework.beans.BeanUtils;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.todaysoft.lims.system.model.searcher.ProbeSeacher;
import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.model.vo.Probe;
import com.todaysoft.lims.system.model.vo.ReagentKit;
import com.todaysoft.lims.system.model.vo.Supplier;
import com.todaysoft.lims.system.service.ISupplierService;

@Service
public class SupplierService extends RestService implements ISupplierService{

	@Override
	public Pagination<Supplier> paging(Supplier request, int pageNo, int pageSize) {
		
        request.setPageNo(pageNo);
        request.setPageSize(pageSize);
        
        String url = GatewayService.getServiceUrl("/supplier/paging");
        ResponseEntity<Pagination<Supplier>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<Supplier>(request), new ParameterizedTypeReference<Pagination<Supplier>>()
            {
            });
        return exchange.getBody();
	}

	@Override
	public void modify(Supplier request) {

        String url = GatewayService.getServiceUrl("/supplier/modify");
        template.postForObject(url, request, String.class);
		
	}

	@Override
	public Supplier getSupplier(Integer id) {
		// TODO Auto-generated method stub
		   String url = GatewayService.getServiceUrl("/supplier/{id}");
	        return template.getForObject(url, Supplier.class, Collections.singletonMap("id", id));
	}

	@Override
	public void delete(Integer id) {
		 String url = GatewayService.getServiceUrl("/supplier/{id}");
	        template.delete(url, Collections.singletonMap("id", id));
		
	}

	@Override
	public void create(Supplier request) {
		 String url = GatewayService.getServiceUrl("/supplier/create");
	        template.postForObject(url, request, String.class);
		
	}

	@Override
	public boolean validate(Supplier request) {
		String url = GatewayService.getServiceUrl("/supplier/validate");
       return  template.postForObject(url, request, boolean.class);
	}

	@Override
	public Pagination<Supplier> selectSupplier(Supplier req, int pageNo,
			int pageSize) {
		Supplier request = new Supplier();
        BeanUtils.copyProperties(req, request);
        request.setPageNo(pageNo);
        request.setPageSize(pageSize);

   return exchange("/supplier/selectSupplier", request,new ParameterizedTypeReference<Pagination<Supplier>>(){});
	}

}
