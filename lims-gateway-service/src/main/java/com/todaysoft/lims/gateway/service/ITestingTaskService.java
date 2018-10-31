package com.todaysoft.lims.gateway.service;

import java.util.List;

import com.todaysoft.lims.gateway.model.request.TestingSheetCreateRequest;
import com.todaysoft.lims.gateway.model.request.testingtask.*;

public interface ITestingTaskService {

	Integer create(TestingTaskCreateRequest request);

	TestingTaskAssign paging(TestingTaskDetailRequest request);

	Integer createRNA(TestingTaskCreateRequest request);

	Integer create(TestingSheetCreateRequest request);

	TestingTaskAssign pagingDnaQc(TestingTaskDetailRequest request);

	TestingTaskAssign pagingLibraryCreation(TestingTaskDetailRequest request);

	TestingTaskAssign pagingWKQC(TestingTaskDetailRequest request);

	TestingTaskAssign pagingLibraryCatch(TestingTaskDetailRequest request);

	TestingTaskAssign pagingXddl(TestingTaskDetailRequest request);
	
	TestingTaskAssign pagingJddl(TestingTaskDetailRequest request);

	TestingTaskAssign pagingOnTest(TestingTaskDetailRequest request);
	
	TestingTaskAssign pagingBioInfo(TestingTaskDetailRequest request);
	
	TestingTaskAssign pagingTecAnalysis(TestingTaskDetailRequest request);
	
	TestingTaskAssign pagingReportCreate(TestingTaskDetailRequest request);
	
	String getSampleLocation(String containerType);

	List<SampleTracing> sampleTraceList(SampleTracingPaging request);

	List<SampleTracing> getOriginalSamples(Integer sampleDetailId);
}
