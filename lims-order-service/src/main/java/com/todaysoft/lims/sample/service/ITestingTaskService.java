package com.todaysoft.lims.sample.service;

import java.util.List;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.sample.entity.SampleReceiveDetail;
import com.todaysoft.lims.sample.entity.SampleTracing;
import com.todaysoft.lims.sample.entity.StorageLocation;
import com.todaysoft.lims.sample.entity.TestingTask;
import com.todaysoft.lims.sample.entity.TestingTaskDetail;
import com.todaysoft.lims.sample.entity.TestingTaskResult;
import com.todaysoft.lims.sample.model.TestingTaskAssignModel;
import com.todaysoft.lims.sample.model.testingtask.SampleTracingPaging;
import com.todaysoft.lims.sample.model.testingtask.TestingTaskCreateRequest;
import com.todaysoft.lims.sample.model.testingtask.TestingTaskDetailRequest;

public interface ITestingTaskService {

	Integer create(TestingTaskCreateRequest request);
	
	Integer createTestingTask(TestingTask request);

	TestingTaskAssignModel paging(TestingTaskDetailRequest request);

	List<SampleReceiveDetail> getDetailBySampleCode(String id);

	SampleReceiveDetail getDetailById(Integer id);

	TestingTaskAssignModel paginDnaQc(TestingTaskDetailRequest request);

	List<StorageLocation> searchFreeLocationBySampleId(Integer sampleId);

	TestingTaskAssignModel pagingLibraryCreation(TestingTaskDetailRequest request);

	TestingTaskAssignModel pagingWKQC(TestingTaskDetailRequest request);

	TestingTaskAssignModel pagingLibraryCatch(TestingTaskDetailRequest request);

	TestingTaskAssignModel pagingXddl(TestingTaskDetailRequest request);
	
	TestingTaskAssignModel pagingJddl(TestingTaskDetailRequest request);

	TestingTaskAssignModel pagingOnTest(TestingTaskDetailRequest request);
	
	TestingTaskAssignModel pagingBioInfo(TestingTaskDetailRequest request);
	
	TestingTaskAssignModel pagingTecAnalysis(TestingTaskDetailRequest request);
	
	TestingTaskAssignModel pagingReportCreate(TestingTaskDetailRequest request);

	/*String getSampleLocation(String containerType,String type);*/
	
	String searchFreeLocationByContainerType(String containerType,String type,int index);

	List<SampleTracing> sampleTraceList(SampleTracingPaging request);

	List<SampleTracing> getTracingListByParms(SampleTracing request);
	
	List<SampleTracing> getOriginalSamples(Integer sampleDetailId);
	
	List<TestingTask> list(TestingTask request);
	
	TestingTask get(Integer id);
	
	void delete(Integer id);
	
	void  modify(TestingTask request);

	void createTaskResult(TestingTaskResult entity);

	TestingTask getTestingTaskById(Integer id);
}
