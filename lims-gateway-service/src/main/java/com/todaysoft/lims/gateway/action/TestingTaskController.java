package com.todaysoft.lims.gateway.action;

import java.util.List;

import com.todaysoft.lims.gateway.model.request.testingtask.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.lims.gateway.model.request.TestingSheetCreateRequest;
import com.todaysoft.lims.gateway.service.ITestingTaskService;

@RestController
@RequestMapping("/testingTask")
public class TestingTaskController {
	
	@Autowired
	private ITestingTaskService service;

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public Integer create(@RequestBody TestingSheetCreateRequest request){
	return service.create(request);
	}
	
	
	@RequestMapping(value = "/paging")
	public TestingTaskAssign paging(@RequestBody TestingTaskDetailRequest request){
		return service.paging(request);
	}
	
	@RequestMapping(value = "/pagingDnaQc")
	public TestingTaskAssign pagingDnaQc(@RequestBody TestingTaskDetailRequest request){
		return service.pagingDnaQc(request);
	}
	
	@RequestMapping(value = "/pagingLibraryCreation")
	public TestingTaskAssign pagingLibraryCreation(@RequestBody TestingTaskDetailRequest request){
		return service.pagingLibraryCreation(request);
	}
	
	@RequestMapping(value = "/pagingWKQC")
	public TestingTaskAssign pagingWKQC(@RequestBody TestingTaskDetailRequest request){
		return service.pagingWKQC(request);
	}
	
	@RequestMapping(value = "/pagingLibraryCatch")
	public TestingTaskAssign pagingLibraryCatch(@RequestBody TestingTaskDetailRequest request){
		return service.pagingLibraryCatch(request);
	}

	@RequestMapping(value = "/pagingXddl")
	public TestingTaskAssign pagingXddl(@RequestBody TestingTaskDetailRequest request){
		return service.pagingXddl(request);
	}
	
	@RequestMapping(value = "/pagingJddl")
	public TestingTaskAssign pagingJddl(@RequestBody TestingTaskDetailRequest request){
		return service.pagingJddl(request);
	}

	@RequestMapping(value = "/pagingOnTest")
	public TestingTaskAssign pagingOnTest(@RequestBody TestingTaskDetailRequest request){
		return service.pagingOnTest(request);
	}
	
	@RequestMapping(value = "/pagingBioInfo")
	public TestingTaskAssign pagingBioInfo(@RequestBody TestingTaskDetailRequest request){
		return service.pagingBioInfo(request);
	}
	
	@RequestMapping(value = "/pagingTecAnalysis")
	public TestingTaskAssign pagingTecAnalysis(@RequestBody TestingTaskDetailRequest request){
		return service.pagingTecAnalysis(request);
	}
	
	@RequestMapping(value = "/pagingReportCreate")
	public TestingTaskAssign pagingReportCreate(@RequestBody TestingTaskDetailRequest request){
		return service.pagingReportCreate(request);
	}
	
	@RequestMapping(value = "/createRNA", method = RequestMethod.POST)
	public Integer createRNA(@RequestBody TestingTaskCreateRequest request){
		return service.createRNA(request);
	}
	
	@RequestMapping(value="/getSampleLocation/{containerType}")
	public String getSampleLocation(@PathVariable String containerType){
		return service.getSampleLocation(containerType);
	}
	
	@RequestMapping(value="/sampleTraceList")
	public List<SampleTracing> sampleTraceList(@RequestBody SampleTracingPaging request){
		 return service.sampleTraceList(request);
	}
	
	@RequestMapping(value="/sampleTracePaging/{sampleDetailId}")
	public List<SampleTracing> getOriginalSamples(@PathVariable Integer sampleDetailId) {
		return service.getOriginalSamples(sampleDetailId);
	}
}
