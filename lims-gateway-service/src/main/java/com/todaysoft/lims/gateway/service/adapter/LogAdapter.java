package com.todaysoft.lims.gateway.service.adapter;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.todaysoft.lims.gateway.model.Log;
import com.todaysoft.lims.gateway.model.Pagination;
import com.todaysoft.lims.gateway.model.request.LogCreateRequest;
import com.todaysoft.lims.gateway.model.request.LogPagingRequest;

/**
 * @author admin
 *
 */
@Component
public class LogAdapter extends AbstractAdapter {
	private static final String SERVICE_NAME = "lims-log-service";

	@Autowired
	private RestTemplate template;

	@HystrixCommand(fallbackMethod = "fallback")
	public Pagination<Log> paging(LogPagingRequest request) {
		String url = getServiceUrl("/log/paging");
		ResponseEntity<Pagination<Log>> exchange = template.exchange(url, HttpMethod.POST,
				new HttpEntity<LogPagingRequest>(request), new ParameterizedTypeReference<Pagination<Log>>() {
				});
		return exchange.getBody();
	}

	 @HystrixCommand(fallbackMethod = "fallback")
	    public Log getLog(String operator)
	    {
	        String url = getServiceUrl("/log/operator/{operator}");
	        return template.getForObject(url, Log.class, Collections.singletonMap("operator", operator));
	    }

	@HystrixCommand(fallbackMethod = "fallback")
	public Integer create(LogCreateRequest request) {
		String url = getServiceUrl("/log/create");
		return template.postForObject(url, request, Integer.class);
	}

	@Override
	public String getName() {
		return SERVICE_NAME;
	}
}
