package com.todaysoft.lims.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.todaysoft.lims.system.model.searcher.ProcessSearcher;
import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.model.vo.ProcessTaskModel;
import com.todaysoft.lims.system.service.IProcessService;
import com.todaysoft.lims.system.service.adapter.request.ProcessTaskPagingRequest;



@Service
public class ProcessService implements IProcessService
{
    
    @Autowired
    private RestTemplate template;

	@Override
	public Pagination<ProcessTaskModel> paging(ProcessSearcher searcher, int pageNo, int pageSize) {
		return null;
	}

	@Override
	public Pagination<ProcessTaskModel> paging(ProcessTaskPagingRequest request,
			int pageNo, int pageSize) {
		request.setPageNo(pageNo);
		request.setPageSize(pageSize);

		String url = GatewayService.getServiceUrl("/process/todo");
		ResponseEntity<Pagination<ProcessTaskModel>> exchange = template.exchange(url, HttpMethod.POST,
				new HttpEntity<ProcessTaskPagingRequest>(request), new ParameterizedTypeReference<Pagination<ProcessTaskModel>>() {
				});
		return exchange.getBody();
	}
}
