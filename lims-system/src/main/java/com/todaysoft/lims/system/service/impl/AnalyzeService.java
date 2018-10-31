package com.todaysoft.lims.system.service.impl;

import java.util.Collections;



import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.model.vo.analyze.AnalyzeMethod;
import com.todaysoft.lims.system.model.vo.analyze.AnalyzeMethodPagingRequest;
import com.todaysoft.lims.system.service.IAnalyzeService;


@Service
public class AnalyzeService extends RestService  implements IAnalyzeService {

	@Override
	public Pagination<AnalyzeMethod> paging(
			AnalyzeMethodPagingRequest searcher, int pageNo, int pagesize) {
		AnalyzeMethodPagingRequest request = new AnalyzeMethodPagingRequest();
        BeanUtils.copyProperties(searcher, request);
        request.setPageNo(pageNo);
        request.setPageSize(pagesize);
        
        String url = GatewayService.getServiceUrl("/analyzeMethod/paging");
        ResponseEntity<Pagination<AnalyzeMethod>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<AnalyzeMethodPagingRequest>(request), new ParameterizedTypeReference<Pagination<AnalyzeMethod>>()
            {
            });

        return exchange.getBody();
	}

	@Override
	public AnalyzeMethod getAnalyzeById(Integer id) {
		String url = GatewayService.getServiceUrl("/analyzeMethod/{id}");
		return template.getForObject(url, AnalyzeMethod.class,Collections.singletonMap("id", id));
	}

	@Override
	public Integer modify(AnalyzeMethod request) {
		String url = GatewayService.getServiceUrl("/analyzeMethod/modify");
		return template.postForObject(url, request, Integer.class);
	}

	@Override
	public Integer create(AnalyzeMethod request) {
		String url = GatewayService.getServiceUrl("/analyzeMethod/create");
		return template.postForObject(url, request, Integer.class);
	}

	@Override
	public void delete(Integer id) {
		String url = GatewayService.getServiceUrl("/analyzeMethod/{id}");
		template.delete(url, Collections.singletonMap("id", id));
	}

	@Override
	public boolean validate(AnalyzeMethod request) {
		String url = GatewayService.getServiceUrl("/analyzeMethod/validate");
	    return  template.postForObject(url, request, boolean.class);
	}

	@Override
	public List<AnalyzeMethod> list(AnalyzeMethod request) {
		String url = GatewayService.getServiceUrl("/analyzeMethod/list");
		ResponseEntity<List<AnalyzeMethod>> exchange = template.exchange(url,
				HttpMethod.POST, new HttpEntity<AnalyzeMethod>(request),
				new ParameterizedTypeReference<List<AnalyzeMethod>>() {
		});
		return exchange.getBody();
	}

}
