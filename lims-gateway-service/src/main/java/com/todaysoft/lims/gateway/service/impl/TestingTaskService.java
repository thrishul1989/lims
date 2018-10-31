package com.todaysoft.lims.gateway.service.impl;

import java.util.List;

import com.todaysoft.lims.gateway.model.request.testingtask.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todaysoft.lims.gateway.model.Pagination;
import com.todaysoft.lims.gateway.model.request.TestingSheetCreateRequest;
import com.todaysoft.lims.gateway.service.ITestingTaskService;
import com.todaysoft.lims.gateway.service.adapter.TestingTaskAdapter;

@Service
public class TestingTaskService implements ITestingTaskService {

	@Autowired
	private TestingTaskAdapter testingTaskAdapter;

	@Override
	public Integer create(TestingTaskCreateRequest request) {
		return testingTaskAdapter.create(request);

	}

	@Override
	public TestingTaskAssign paging(TestingTaskDetailRequest request) {
		return testingTaskAdapter.paging(request);
	}

	@Override
	public Integer createRNA(TestingTaskCreateRequest request) {
		return testingTaskAdapter.createRNA(request);
	}

	@Override
	public Integer create(TestingSheetCreateRequest request) {
		return testingTaskAdapter.create(request);
	}

	@Override
	public TestingTaskAssign pagingDnaQc(TestingTaskDetailRequest request) {
		return testingTaskAdapter.pagingDnaQc(request);
	}

	@Override
	public TestingTaskAssign pagingLibraryCreation(
			TestingTaskDetailRequest request) {
		return testingTaskAdapter.pagingLibraryCreation(request);
	}

	@Override
	public TestingTaskAssign pagingWKQC(TestingTaskDetailRequest request) {
		return testingTaskAdapter.pagingWKQC(request);
	}

	@Override
	public TestingTaskAssign pagingLibraryCatch(TestingTaskDetailRequest request) {
		return testingTaskAdapter.pagingLibraryCatch(request);
	}

	@Override
	public TestingTaskAssign pagingXddl(TestingTaskDetailRequest request) {
		return testingTaskAdapter.pagingXddl(request);
	}

	@Override
	public TestingTaskAssign pagingJddl(TestingTaskDetailRequest request) {
		return testingTaskAdapter.pagingJddl(request);
	}

	@Override
	public TestingTaskAssign pagingOnTest(TestingTaskDetailRequest request) {
		return testingTaskAdapter.pagingOnTest(request);
	}

	@Override
	public TestingTaskAssign pagingBioInfo(TestingTaskDetailRequest request) {
		return testingTaskAdapter.pagingBioInfo(request);
	}

	@Override
	public TestingTaskAssign pagingTecAnalysis(TestingTaskDetailRequest request) {
		return testingTaskAdapter.pagingTecAnalysis(request);
	}
	
	@Override
	public TestingTaskAssign pagingReportCreate(TestingTaskDetailRequest request) {
		return testingTaskAdapter.pagingReportCreate(request);
	}
	
	@Override
	public String getSampleLocation(String containerType) {
		return testingTaskAdapter.getSampleLocation(containerType);
	}

	@Override
	public List<SampleTracing> sampleTraceList(SampleTracingPaging request) {
		return testingTaskAdapter.sampleTraceList(request);
	}

	@Override
	public List<SampleTracing> getOriginalSamples(Integer sampleDetailId) {
		return testingTaskAdapter.getOriginalSamples(sampleDetailId);
	}
}
