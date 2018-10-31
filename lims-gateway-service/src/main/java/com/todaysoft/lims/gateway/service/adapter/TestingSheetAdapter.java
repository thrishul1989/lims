package com.todaysoft.lims.gateway.service.adapter;

import java.util.Collections;
import java.util.List;

import com.todaysoft.lims.gateway.model.TestingSheetJddlModel;
import com.todaysoft.lims.gateway.model.TestingSheetTask;
import com.todaysoft.lims.gateway.model.TestingSheetTaskAbsolute;
import com.todaysoft.lims.gateway.model.request.TaskListRequest;
import com.todaysoft.lims.gateway.model.request.testingtask.SampleTracing;
import com.todaysoft.lims.gateway.model.request.testingtask.TestingTaskDetailRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.todaysoft.lims.gateway.model.TestingSheet;
import com.todaysoft.lims.persist.Pagination;

@Component
public class TestingSheetAdapter extends AbstractAdapter {

	private static final String SERVICE_NAME = "lims-sampleReceive-service";
	
	@Autowired
	private RestTemplate template;
	
	@HystrixCommand(fallbackMethod = "fallback")
	public TestingSheet getSheetByActivitiTaskId(String activitiTaskId){
		String url = getServiceUrl("/testingSheet/activitiTaskId/{activitiTaskId}");
		return template.getForObject(url, TestingSheet.class, Collections.singletonMap("activitiTaskId", activitiTaskId));
	}

	@HystrixCommand(fallbackMethod = "fallback")
	public List<TestingSheetJddlModel> getJddlList(TestingTaskDetailRequest request){
		String url = getServiceUrl("/testingSheet/getJddlList");
		ResponseEntity<List<TestingSheetJddlModel>> exchange = template.exchange(url, HttpMethod.POST ,new HttpEntity<TestingTaskDetailRequest>(request),new ParameterizedTypeReference<List<TestingSheetJddlModel>>(){
		});
		return exchange.getBody();
	}

	@HystrixCommand(fallbackMethod = "fallback")
	public List<TestingSheetTask> getJDDLContrast(TestingSheetTask request){
		String url = getServiceUrl("/testingSheet/jddl/contrast");
		ResponseEntity<List<TestingSheetTask>> exchange = template.exchange(url, HttpMethod.POST ,new HttpEntity<TestingSheetTask>(request),new ParameterizedTypeReference<List<TestingSheetTask>>(){
		});
		return exchange.getBody();
	}


	@HystrixCommand(fallbackMethod = "fallback")
	public Pagination<TestingSheetTaskAbsolute> absolutePaging(){
		String url = getServiceUrl("/testingSheet/absolute/paging");
		ResponseEntity<Pagination<TestingSheetTaskAbsolute>> exchange = template.exchange(url, HttpMethod.GET ,null ,new ParameterizedTypeReference<Pagination<TestingSheetTaskAbsolute>>(){
		});
		return exchange.getBody();
	}

	@Override
	public String getName() {
		return SERVICE_NAME;
	}

}
