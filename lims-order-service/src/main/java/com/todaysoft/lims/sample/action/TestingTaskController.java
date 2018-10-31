package com.todaysoft.lims.sample.action;

import java.util.List;

import javax.transaction.Transactional;

import com.todaysoft.lims.sample.model.TestingTaskAssignModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.lims.sample.entity.SampleReceiveDetail;
import com.todaysoft.lims.sample.entity.SampleTracing;
import com.todaysoft.lims.sample.entity.StorageLocation;
import com.todaysoft.lims.sample.entity.TestingTask;
import com.todaysoft.lims.sample.model.testingtask.SampleTracingPaging;
import com.todaysoft.lims.sample.model.testingtask.TestingTaskCreateRequest;
import com.todaysoft.lims.sample.model.testingtask.TestingTaskDetailRequest;
import com.todaysoft.lims.sample.service.ITestingTaskService;
import com.todaysoft.lims.sample.util.Constant;

@RestController
@RequestMapping("/testingTask")
public class TestingTaskController {

	@Autowired
	private ITestingTaskService service;

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public Integer create(@RequestBody TestingTaskCreateRequest request) {
		return service.create(request);
	}

	@RequestMapping(value = "/paging")
	public TestingTaskAssignModel paging(
			@RequestBody TestingTaskDetailRequest request) {
		return service.paging(request);
	}

	
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	 public List<TestingTask>  list(@RequestBody TestingTask request){
	    return service.list(request); 
	}
	 
    @Transactional
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable Integer id) {
		service.delete(id);
	}
    @Transactional
	@RequestMapping(value = "/modify")
	public void modify(@RequestBody TestingTask request) {
		service.modify(request);
	}

	@RequestMapping(value = "/pagingDnaQc")
	public TestingTaskAssignModel paginDnaQc(
			@RequestBody TestingTaskDetailRequest request) {
		return service.paginDnaQc(request);
	}

	@RequestMapping(value = "/pagingLibraryCreation")
	public TestingTaskAssignModel pagingLibraryCreation(
			@RequestBody TestingTaskDetailRequest request) {
		return service.pagingLibraryCreation(request);
	}

	@RequestMapping(value = "/pagingWKQC")
	public TestingTaskAssignModel pagingWKQC(
			@RequestBody TestingTaskDetailRequest request) {
		return service.pagingWKQC(request);
	}

	/**
	 * 文库捕获展现页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/pagingLibraryCatch")
	public TestingTaskAssignModel pagingLibraryCatch(
			@RequestBody TestingTaskDetailRequest request) {
		return service.pagingLibraryCatch(request);
	}

	@RequestMapping(value = "/pagingXddl")
	public TestingTaskAssignModel pagingXddl(
			@RequestBody TestingTaskDetailRequest request) {
		return service.pagingXddl(request);
	}

	@RequestMapping(value = "/pagingJddl")
	public TestingTaskAssignModel pagingJddl(
			@RequestBody TestingTaskDetailRequest request) {
		return service.pagingJddl(request);
	}

	@RequestMapping(value = "/pagingOnTest")
	public TestingTaskAssignModel pagingOnTest(
			@RequestBody TestingTaskDetailRequest request) {
		return service.pagingOnTest(request);
	}

	@RequestMapping(value = "/pagingBioInfo")
	public TestingTaskAssignModel pagingBioInfo(
			@RequestBody TestingTaskDetailRequest request) {
		return service.pagingBioInfo(request);
	}

	@RequestMapping(value = "/pagingTecAnalysis")
	public TestingTaskAssignModel pagingTecAnalysis(
			@RequestBody TestingTaskDetailRequest request) {
		return service.pagingTecAnalysis(request);
	}

	@RequestMapping(value = "/pagingReportCreate")
	public TestingTaskAssignModel pagingReportCreate(
			@RequestBody TestingTaskDetailRequest request) {
		return service.pagingReportCreate(request);
	}

	@RequestMapping(value = "/getDetailBySampleCode")
	public List<SampleReceiveDetail> getDetailBySampleCode(
			@PathVariable String id) {
		return service.getDetailBySampleCode(id);
	}

	@RequestMapping(value = "/searchFreeLocationByContainerType/{containerType}")
	public String searchFreeLocationByContainerType(
			@PathVariable String containerType, int index) {
		return service.searchFreeLocationByContainerType(containerType,
				Constant.Temporary_Storage_Location, index);
	}

	@RequestMapping(value = "/searchFreeLocationBySampleId")
	public List<StorageLocation> searchFreeLocationBySampleId(
			@PathVariable Integer sampleId) {
		return service.searchFreeLocationBySampleId(sampleId);
	}

	@RequestMapping(value = "/sampleTraceList")
	public List<SampleTracing> sampleTraceList(
			@RequestBody SampleTracingPaging request) {
		return service.sampleTraceList(request);
	}

	@RequestMapping(value = "/sampleTracePaging/{sampleDetailId}")
	public List<SampleTracing> getOriginalSamples(
			@PathVariable Integer sampleDetailId) {
		return service.getOriginalSamples(sampleDetailId);
	}
}
