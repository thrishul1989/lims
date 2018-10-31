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
import com.todaysoft.lims.system.model.vo.SupplierReagentKit;
import com.todaysoft.lims.system.service.ISupplierReagentKitService;

@Service
public class SupplierReagentKitService extends RestService implements ISupplierReagentKitService{

	@Override
	public Pagination<SupplierReagentKit> paging(SupplierReagentKit searcher, int pageNo, int pageSize) {
		SupplierReagentKit request = new SupplierReagentKit();
        BeanUtils.copyProperties(searcher, request);
        request.setPageNo(pageNo);
        request.setPageSize(pageSize);
        
        String url = GatewayService.getServiceUrl("/supplierReagent/kitPaging");
        ResponseEntity<Pagination<SupplierReagentKit>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<SupplierReagentKit>(request), new ParameterizedTypeReference<Pagination<SupplierReagentKit>>()
            {
            });
        return exchange.getBody();
	}

	@Override
	public void createKit(SupplierReagentKit request) {
		 String url = GatewayService.getServiceUrl("/supplierReagent/createKit");
	        template.postForObject(url, request, String.class);
	}

	@Override
	public void delete(Integer id) {
		 String url = GatewayService.getServiceUrl("/supplierReagent/deleteKit/{kitId}");
	        template.delete(url, Collections.singletonMap("kitId", id));
		
	}

	@Override
	public void updateKitPrice(SupplierReagentKit request) {
		 String url = GatewayService.getServiceUrl("/supplierReagent/updateKitPrice");
		   template.postForObject(url, request, String.class);
		
	}

}
