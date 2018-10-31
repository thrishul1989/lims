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
import com.todaysoft.lims.system.model.vo.Sysconfig;
import com.todaysoft.lims.system.service.IProbeService;
import com.todaysoft.lims.system.service.ISysconfigService;

@Service
public class SysconfigService extends RestService implements ISysconfigService{

	@Override
	public Pagination<Sysconfig> paging(Sysconfig searcher, int pageNo,
			int pageSize) {
		Sysconfig request = new Sysconfig();
        BeanUtils.copyProperties(searcher, request);
        request.setPageNo(pageNo);
        request.setPageSize(pageSize);
        
        String url = GatewayService.getServiceUrl("/sysconfig/paging");
        ResponseEntity<Pagination<Sysconfig>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<Sysconfig>(request), new ParameterizedTypeReference<Pagination<Sysconfig>>()
            {
            });
        return exchange.getBody();
	}

	@Override
	public void modify(Sysconfig request) {
		  String url = GatewayService.getServiceUrl("/sysconfig/modify");
	        template.postForObject(url, request, String.class);
		
	}

	@Override
	public Sysconfig getSysconfig(Integer id) {
		String url = GatewayService.getServiceUrl("/sysconfig/{id}");
        return template.getForObject(url, Sysconfig.class, Collections.singletonMap("id", id));
	}

	@Override
	public void delete(Integer id) {
		String url = GatewayService.getServiceUrl("/sysconfig/{id}");
        template.delete(url, Collections.singletonMap("id", id));
		
	}

	@Override
	public void create(Sysconfig request) {
		String url = GatewayService.getServiceUrl("/sysconfig/create");
        template.postForObject(url, request, String.class);
		
	}

	@Override
	public boolean validate(Sysconfig request) {
		String url = GatewayService.getServiceUrl("/sysconfig/validate");
	       return  template.postForObject(url, request, boolean.class);
	}

}
