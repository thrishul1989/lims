package com.todaysoft.lims.system.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.todaysoft.lims.system.model.searcher.LogSearcher;
import com.todaysoft.lims.system.model.vo.Log;
import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.service.ILogService;
import com.todaysoft.lims.system.service.adapter.request.LogCreateRequest;
import com.todaysoft.lims.system.service.adapter.request.LogPagingRequest;

@Service
public class LogService implements ILogService{
    @Autowired
    private RestTemplate template;
    
    @Override
    public Pagination<Log> paging(LogSearcher searcher, int pageNo, int pageSize)
    {
    	LogPagingRequest request = new LogPagingRequest();
        BeanUtils.copyProperties(searcher, request);
        request.setPageNo(pageNo);
        request.setPageSize(pageSize);
        
        String url = GatewayService.getServiceUrl("/log/paging");
        ResponseEntity<Pagination<Log>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<LogPagingRequest>(request), new ParameterizedTypeReference<Pagination<Log>>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
	public void create(Log data) {
    	LogCreateRequest request = new LogCreateRequest();
		BeanUtils.copyProperties(data, request);
		String url = GatewayService.getServiceUrl("/log/create");
		template.postForObject(url, request, String.class);
	}
    	
}
