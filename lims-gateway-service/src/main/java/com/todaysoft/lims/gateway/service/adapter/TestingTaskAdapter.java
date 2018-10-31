package com.todaysoft.lims.gateway.service.adapter;

import java.util.Collections;
import java.util.List;

import com.todaysoft.lims.gateway.model.request.testingtask.*;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.todaysoft.lims.gateway.model.Pagination;
import com.todaysoft.lims.gateway.model.request.TestingSheetCreateRequest;

@Component
public class TestingTaskAdapter extends AbstractAdapter{
	
	private static final String SERVICE_NAME = "lims-sampleReceive-service";
	@Override
	public String getName() {
		return SERVICE_NAME;
	}
	
	@HystrixCommand(fallbackMethod = "fallback")
	public Integer create(TestingTaskCreateRequest request) {
		String url = getServiceUrl("/testingTask/create");
        return template.postForObject(url, request, Integer.class);
	}

	
	@HystrixCommand(fallbackMethod = "fallback")
	public TestingTaskAssign paging(TestingTaskDetailRequest request) {
		String url = getServiceUrl("/testingTask/paging");
		ResponseEntity<TestingTaskAssign> exchange = template.exchange(url, HttpMethod.POST, new HttpEntity<TestingTaskDetailRequest>(request),
				new ParameterizedTypeReference<TestingTaskAssign>(){
		});
		return exchange.getBody();
	}


	public Integer createRNA(TestingTaskCreateRequest request) {
		String url = getServiceUrl("/testingTask/createRNA");
        return template.postForObject(url, request, Integer.class);
	}


	public Integer create(TestingSheetCreateRequest request) {
		String url = getServiceUrl("/testing/assign");
        return template.postForObject(url, request, Integer.class);
	}

	
	@HystrixCommand(fallbackMethod = "fallback")
	public TestingTaskAssign pagingDnaQc(TestingTaskDetailRequest request) {
		String url = getServiceUrl("/testingTask/pagingDnaQc");
		ResponseEntity<TestingTaskAssign> exchange = template.exchange(url, HttpMethod.POST, new HttpEntity<TestingTaskDetailRequest>(request),
				new ParameterizedTypeReference<TestingTaskAssign>(){
		});
		return exchange.getBody();
	}
	
	@HystrixCommand(fallbackMethod = "fallback")
	public TestingTaskAssign pagingLibraryCreation(TestingTaskDetailRequest request) {
		String url = getServiceUrl("/testingTask/pagingLibraryCreation");
		ResponseEntity<TestingTaskAssign> exchange = template.exchange(url, HttpMethod.POST, new HttpEntity<TestingTaskDetailRequest>(request),
				new ParameterizedTypeReference<TestingTaskAssign>(){
		});
		return exchange.getBody();
	}
	
	@HystrixCommand(fallbackMethod = "fallback")
	public TestingTaskAssign pagingWKQC(TestingTaskDetailRequest request) {
		String url = getServiceUrl("/testingTask/pagingWKQC");
		ResponseEntity<TestingTaskAssign> exchange = template.exchange(url, HttpMethod.POST, new HttpEntity<TestingTaskDetailRequest>(request),
				new ParameterizedTypeReference<TestingTaskAssign>(){
		});
		return exchange.getBody();
	}
	
	@HystrixCommand(fallbackMethod = "fallback")
	public TestingTaskAssign pagingLibraryCatch(TestingTaskDetailRequest request) {
		String url = getServiceUrl("/testingTask/pagingLibraryCatch");
		ResponseEntity<TestingTaskAssign> exchange = template.exchange(url, HttpMethod.POST, new HttpEntity<TestingTaskDetailRequest>(request),
				new ParameterizedTypeReference<TestingTaskAssign>(){
		});
		return exchange.getBody();
	}

	@HystrixCommand(fallbackMethod = "fallback")
	public TestingTaskAssign pagingXddl(TestingTaskDetailRequest request) {
		String url = getServiceUrl("/testingTask/pagingXddl");
		ResponseEntity<TestingTaskAssign> exchange = template.exchange(url, HttpMethod.POST, new HttpEntity<TestingTaskDetailRequest>(request),
				new ParameterizedTypeReference<TestingTaskAssign>(){
				});
		return exchange.getBody();
	}
	
	@HystrixCommand(fallbackMethod = "fallback")
	public TestingTaskAssign pagingJddl(TestingTaskDetailRequest request) {
		String url = getServiceUrl("/testingTask/pagingJddl");
		ResponseEntity<TestingTaskAssign> exchange = template.exchange(url, HttpMethod.POST, new HttpEntity<TestingTaskDetailRequest>(request),
				new ParameterizedTypeReference<TestingTaskAssign>(){
				});
		return exchange.getBody();
	}

	@HystrixCommand(fallbackMethod = "fallback")
	public TestingTaskAssign pagingOnTest(TestingTaskDetailRequest request) {
		String url = getServiceUrl("/testingTask/pagingOnTest");
		ResponseEntity<TestingTaskAssign> exchange = template.exchange(url, HttpMethod.POST, new HttpEntity<TestingTaskDetailRequest>(request),
				new ParameterizedTypeReference<TestingTaskAssign>(){
				});
		return exchange.getBody();
	}
	
	@HystrixCommand(fallbackMethod = "fallback")
	public TestingTaskAssign pagingBioInfo(TestingTaskDetailRequest request) {
		String url = getServiceUrl("/testingTask/pagingBioInfo");
		ResponseEntity<TestingTaskAssign> exchange = template.exchange(url, HttpMethod.POST, new HttpEntity<TestingTaskDetailRequest>(request),
				new ParameterizedTypeReference<TestingTaskAssign>(){
				});
		return exchange.getBody();
	}
	
	@HystrixCommand(fallbackMethod = "fallback")
	public TestingTaskAssign pagingTecAnalysis(TestingTaskDetailRequest request) {
		String url = getServiceUrl("/testingTask/pagingTecAnalysis");
		ResponseEntity<TestingTaskAssign> exchange = template.exchange(url, HttpMethod.POST, new HttpEntity<TestingTaskDetailRequest>(request),
				new ParameterizedTypeReference<TestingTaskAssign>(){
				});
		return exchange.getBody();
	}
	
	@HystrixCommand(fallbackMethod = "fallback")
	public TestingTaskAssign pagingReportCreate(TestingTaskDetailRequest request) {
		String url = getServiceUrl("/testingTask/pagingReportCreate");
		ResponseEntity<TestingTaskAssign> exchange = template.exchange(url, HttpMethod.POST, new HttpEntity<TestingTaskDetailRequest>(request),
				new ParameterizedTypeReference<TestingTaskAssign>(){
				});
		return exchange.getBody();
	}
	
	@HystrixCommand(fallbackMethod = "fallback")
	public String getSampleLocation(String containerType) {
		String url = getServiceUrl("/testingTask/getSampleLocation/{containerType}");
		return template.getForObject(url, String.class, Collections.singletonMap("containerType",containerType));
	}

	@HystrixCommand(fallbackMethod = "fallback")
	public List<SampleTracing> sampleTraceList(SampleTracingPaging request) {
		String url = getServiceUrl("/testingTask/sampleTraceList");
		ResponseEntity<List<SampleTracing>> exchange = template.exchange(url, HttpMethod.POST, new HttpEntity<SampleTracingPaging>(request), 
				new ParameterizedTypeReference<List<SampleTracing>>(){
		});
		return exchange.getBody();
	}
	
	@HystrixCommand(fallbackMethod = "fallback")
	public List<SampleTracing> getOriginalSamples(Integer sampleDetailId){
		String url = getServiceUrl("/testingTask/sampleTracePaging/{sampleDetailId}");
		ResponseEntity<List<SampleTracing>> exchange = template.exchange(url, HttpMethod.GET, new HttpEntity<Integer>(sampleDetailId), new ParameterizedTypeReference<List<SampleTracing>>(){
		}, Collections.singletonMap("sampleDetailId", sampleDetailId));
		return exchange.getBody();
	}
}
