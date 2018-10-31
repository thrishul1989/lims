package com.todaysoft.lims.system.modules.bpm.bioanalysis.service.impl;

import java.util.List;

import com.todaysoft.lims.system.model.vo.order.TestingSchedule;
import com.todaysoft.lims.system.model.vo.testingtask.TaskSemantic;
import com.todaysoft.lims.system.modules.bpm.bioanalysis.model.*;
import com.todaysoft.lims.system.modules.bpm.bioanalysis.model.divisioncallback.BiologyReAnalysisDataResponse;
import com.todaysoft.lims.system.modules.bpm.pcrngsdata.model.SangerVerifyRecordModel;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.analysis.BiologyDivisionFastqData;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.analysis.BiologyFamilyRelationAnnotate;
import com.todaysoft.lims.utils.Collections3;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.todaysoft.lims.persist.PageInfo;
import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.system.model.vo.TestingTaskRequest;
import com.todaysoft.lims.system.modules.bpm.bioanalysis.model.divisioncallback.BiologyDivisionCallBackModel;
import com.todaysoft.lims.system.modules.bpm.bioanalysis.service.IBiologyAnalyService;
import com.todaysoft.lims.system.modules.bpm.competeTasks.model.TestingSheet;
import com.todaysoft.lims.system.modules.bpm.competeTasks.model.TestingSheetSearcher;
import com.todaysoft.lims.system.modules.bpm.model.TestTask;
import com.todaysoft.lims.system.modules.bpm.service.ITestingTaskSheetService;
import com.todaysoft.lims.system.service.impl.GatewayService;
import com.todaysoft.lims.system.service.impl.RestService;

@Service
public class BiologyAnalyService extends RestService implements IBiologyAnalyService, ITestingTaskSheetService {
	@Override
	public List<BiologyAnalyTask> getTaskList(BiologyAnalyTaskSearcher searcher) {
		String url = GatewayService.getServiceUrl("/bpm/testing/biology-analy/tasks");
		ResponseEntity<List<BiologyAnalyTask>> exchange = template.exchange(url, HttpMethod.POST, new HttpEntity<BiologyAnalyTaskSearcher>(searcher),
				new ParameterizedTypeReference<List<BiologyAnalyTask>>() {
				});
		return exchange.getBody();
	}

	@Override
	public BiologyAnalySubmitModel getSubmitModel(String id) {
		String url = GatewayService.getServiceUrl("/bpm/testing/biology-analy/tasks/{id}");
		return template.getForObject(url, BiologyAnalySubmitModel.class, id);
	}

	@Override
	public List<BiologyAnalySampleRecord> getSampleRecords(String id) {
		String url = GatewayService.getServiceUrl("/bpm/testing/biology-analy/tasks/{id}/samples");
		ResponseEntity<List<BiologyAnalySampleRecord>> exchange = template.exchange(url, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<BiologyAnalySampleRecord>>() {
				}, id);
		return exchange.getBody();
	}

	@Override
	public List<BiologyAnalyDetailsRecord> getDetailsRecords(String id) {
		String url = GatewayService.getServiceUrl("/bpm/testing/biology-analy/tasks/{id}/details");
		ResponseEntity<List<BiologyAnalyDetailsRecord>> exchange = template.exchange(url, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<BiologyAnalyDetailsRecord>>() {
				}, id);
		return exchange.getBody();
	}

	@Override
	public void submitBioanalysis(BiologyAnalySubmitRequest request) {
		String url = GatewayService.getServiceUrl("/bpm/testing/biology-analy/tasks/submit");
		template.postForLocation(url, request);
	}

	@Override
	public PageInfo<BiologyDivisionTask> getBiologyDivisionList(BioDivisionListRequest searcher, int pageNo, int pageSize) {
		searcher.setPageNo(pageNo);
		searcher.setPageSize(pageSize);
		String url = GatewayService.getServiceUrl("/biology/division/taskPaging");
		ResponseEntity<PageInfo<BiologyDivisionTask>> exchange = template.exchange(url, HttpMethod.POST, new HttpEntity<BioDivisionListRequest>(
				searcher), new ParameterizedTypeReference<PageInfo<BiologyDivisionTask>>() {
		});
		return exchange.getBody();
	}

	@Override
	public PageInfo<BiologyAnnotationTask> getBiologyAnnotationList(BiologyAnnotatioListRequest searcher, int pageNum, int pageSize) {
		searcher.setPageNo(pageNum);
		searcher.setPageSize(pageSize);
		String url = GatewayService.getServiceUrl("/biology/annotation/taskPaging");
		ResponseEntity<PageInfo<BiologyAnnotationTask>> exchange = template.exchange(url, HttpMethod.POST, new HttpEntity<BiologyAnnotatioListRequest>(
				searcher), new ParameterizedTypeReference<PageInfo<BiologyAnnotationTask>>() {
		});
		return exchange.getBody();
	}

	@Override
	public PageInfo<BiologyAnnotationFamilyTask> getBiologyAnnotationFamilyList(BiologyFamilyAnnotatioListRequest searcher, int pageNum, int pageSize) {
		searcher.setPageNo(pageNum);
		searcher.setPageSize(pageSize);
		String url = GatewayService.getServiceUrl("/biology/annotation/familyTaskPaging");
		ResponseEntity<PageInfo<BiologyAnnotationFamilyTask>> exchange = template.exchange(url, HttpMethod.POST, new HttpEntity<BiologyFamilyAnnotatioListRequest>(
				searcher), new ParameterizedTypeReference<PageInfo<BiologyAnnotationFamilyTask>>() {
		});
		return exchange.getBody();
	}

	@Override
	public List<BiologyAnnotationTask> getTaskInfoByFamily(String id) {
		String url = GatewayService.getServiceUrl("/biology/annotation/familyTaskInfo/{id}");
		ResponseEntity<List<BiologyAnnotationTask>> exchange = template.exchange(url, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<BiologyAnnotationTask>>() {
				}, id);
		return exchange.getBody();
	}

	@Override
	public void startFamilyAnnotation(String ids,String familyAnnotationId) {
		BiologyFamilyStartAnalyRequest request = new BiologyFamilyStartAnalyRequest();
		request.setIds(ids);
		request.setFamilyAnnotationId(familyAnnotationId);
		String url = GatewayService.getServiceUrl("/biology/annotation/startFamilyAnnotation");
		template.postForLocation(url, request);
	}

	@Override
	public void biologyAnnotationOperate(BiologyReAnnotationRequest request) {
		String url = GatewayService.getServiceUrl("/biology/annotation/reStartAnnotation");
		template.postForLocation(url, request);
	}

	@Override
	public BiologyDivisionStartModel getSamplesByTaskId(String id) {
		String url = GatewayService.getServiceUrl("/biology/division/getTaskOperateInfo/{id}");
		ResponseEntity<BiologyDivisionStartModel> exchange = template.exchange(url, HttpMethod.GET, null,
				new ParameterizedTypeReference<BiologyDivisionStartModel>() {
				}, id);
		return exchange.getBody();
	}

	@Override
	public BiologyDivisionStartModel getSampleListBySquencingCode(String code) {
		String url = GatewayService.getServiceUrl("/biology/division/getSampleListBySquencingCode/{code}");
		ResponseEntity<BiologyDivisionStartModel> exchange = template.exchange(url, HttpMethod.GET, null,
				new ParameterizedTypeReference<BiologyDivisionStartModel>() {
				}, code);
		return exchange.getBody();
	}

	@Override
	public void startBiologyDivision(BiologyDivisionStartModel data) {
		String url = GatewayService.getServiceUrl("/biology/division/startBiologyDivision");
		template.postForLocation(url, data);
	}

	@Override
	public void biologyDivisionCallBack(BiologyDivisionCallBackModel data) {
		// 回调 修改任务状态
		String url = GatewayService.getServiceUrl("/biology/division/resultCallBack");
		template.postForLocation(url, data);
	}

	@Override
	public void biologyAnnotationCallBack(BiologyReAnalysisDataResponse data) {
		//注释回调
		String url = GatewayService.getServiceUrl("/biology/annotation/resultCallBack");
		template.postForLocation(url, data);
	}

	@Override
	public void reAnalysisAnnotaionFile(String taskId) {
		BiologyFamilyStartAnalyRequest request = new BiologyFamilyStartAnalyRequest();
		request.setIds(taskId);
		String url = GatewayService.getServiceUrl("/biology/annotation/reAnalysisAnnotaionFile");
		template.postForLocation(url, request);


	}

	@Override
	public void reFamilyAnalysisAnnotaionFile(String taskId) {
		BiologyFamilyStartAnalyRequest request = new BiologyFamilyStartAnalyRequest();
		request.setIds(taskId);
		String url = GatewayService.getServiceUrl("/biology/annotation/reFamilyAnalysisAnnotaionFile");
		template.postForLocation(url, request);

	}


	@Override
	public List<BiologyDivisionMonitor> getBiologyDivisionFailRecords(String taskId) {
		String url = GatewayService.getServiceUrl("/biology/division/getBiologyDivisionFailRecords/{taskId}");
		ResponseEntity<List<BiologyDivisionMonitor>> exchange = template.exchange(url, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<BiologyDivisionMonitor>>() {
				}, taskId);
		return exchange.getBody();
	}

	@Override
	public void updateFamilyTaskState(String taskId) {
		BiologyFamilyStartAnalyRequest request = new BiologyFamilyStartAnalyRequest();
		request.setIds(taskId);
		String url = GatewayService.getServiceUrl("/biology/annotation/updateFamilyTaskState");
		template.postForLocation(url, request);
	}

	@Override
	public void updateTaskState(String taskId) {
		BiologyFamilyStartAnalyRequest request = new BiologyFamilyStartAnalyRequest();
		request.setIds(taskId);
		String url = GatewayService.getServiceUrl("/biology/annotation/updateTaskState");
		template.postForLocation(url, request);
	}

	@Override
	/**
	 * 此接口不需要
	 * */
	public List<TestTask> getTestingTaskSheet() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TestingSheet getTestingSheetByTaskId(String taskId) {
		String url = GatewayService.getServiceUrl("/biology/division/getSheetListByTaskId/{taskId}");
		ResponseEntity<List<BiologyDivisionSheet>> exchange = template.exchange(url, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<BiologyDivisionSheet>>() {
				}, taskId);
		List<BiologyDivisionSheet> records = exchange.getBody();
		if (Collections3.isNotEmpty(records)) {
			BiologyDivisionSheet sheet = records.get(0);
			TestingSheet testingSheet = new TestingSheet();
			testingSheet.setId(sheet.getId());
			testingSheet.setAssignerId(sheet.getTesterId());
			testingSheet.setAssignerName(sheet.getTesterName());
			testingSheet.setAssignTime(sheet.getCreateTime());
			testingSheet.setCode(sheet.getCode());
			testingSheet.setTaskName("数据拆分");
			testingSheet.setTesterId(sheet.getTesterId());
			testingSheet.setTesterName(sheet.getTesterName());
			testingSheet.setSemantic(TaskSemantic.DATA_DIVISION);
			return testingSheet;
		}
		return null;
	}

	@Override
	public TestingTaskRequest getTTRById(String testingTaskId) {
		String url = GatewayService.getServiceUrl("/biology/division/getTaskInfoById/{taskId}");
		ResponseEntity<BiologyDivisionTask> exchange = template.exchange(url, HttpMethod.GET, null,
				new ParameterizedTypeReference<BiologyDivisionTask>() {
				}, testingTaskId);
		BiologyDivisionTask task = exchange.getBody();
		if (null != task) {
			TestingTaskRequest request = new TestingTaskRequest();
			request.setId(task.getId());
			request.setSemantic(TaskSemantic.DATA_DIVISION);
			request.setStatus(task.getStatus());
			request.setName("数据拆分");
			request.setResubmit(task.getResubmit());
			request.setResubmitCount(task.getResubmitCount());
			request.setEndTime(task.getEndTime());
			return request;
		}
		return null;
	}

	@Override
	public Pagination<TestingSheet> completeSheetpaging(TestingSheetSearcher searcher) {
		String url = GatewayService.getServiceUrl("/biology/division/completeSheetPaging/{pageNo}/{pageSize}");
		Pagination<TestingSheet> pagination = new Pagination<>();
		ResponseEntity<PageInfo<TestingSheet>> exchange = template.exchange(url, HttpMethod.POST, new HttpEntity<TestingSheetSearcher>(searcher),
				new ParameterizedTypeReference<PageInfo<TestingSheet>>() {
				}, searcher.getPageNo(), searcher.getPageSize());
		PageInfo<TestingSheet> page = exchange.getBody();
		pagination.setRecords(page.getList());
		pagination.setTotalCount((int) page.getTotal());
		pagination.setPageSize(searcher.getPageSize());
		pagination.setPageNo(searcher.getPageNo());
		return pagination;
	}

	@Override
	public List<BiologyAnalySampleRecord> getSampleRecordsBySampleCode(String sampleCode) {
		String url = GatewayService.getServiceUrl("/bpm/testing/biology-analy/tasks/{sampleCode}/samplesByCode");
		ResponseEntity<List<BiologyAnalySampleRecord>> exchange = template.exchange(url, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<BiologyAnalySampleRecord>>() {
				}, sampleCode);
		return exchange.getBody();
	}

	@Override
	public List<BiologyAnalyDetailsRecord> getDetailsRecordsBySampleCode(String sampleCode) {
		String url = GatewayService.getServiceUrl("/bpm/testing/biology-analy/tasks/{sampleCode}/detailsByCode");
		ResponseEntity<List<BiologyAnalyDetailsRecord>> exchange = template.exchange(url, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<BiologyAnalyDetailsRecord>>() {
				}, sampleCode);
		return exchange.getBody();
	}

    @Override
	public BioInfoAnnotate getByAnalaysisSampleId(String analysisSampleId) {
		String url = GatewayService.getServiceUrl("/biology/annotation/getByAnalaysisSampleId/{analysisSample}");
		ResponseEntity<BioInfoAnnotate> exchange = template.exchange(url, HttpMethod.GET, null,
				new ParameterizedTypeReference<BioInfoAnnotate>() {
				}, analysisSampleId);
		return exchange.getBody();

	}

	@Override
	public BiologyDivisionFastqData getDataById(String id) {
		String url = GatewayService.getServiceUrl("/biology/division/getDataById/{id}");
		ResponseEntity<BiologyDivisionFastqData> exchange = template.exchange(url, HttpMethod.GET, null,
				new ParameterizedTypeReference<BiologyDivisionFastqData>() {
				}, id);
		return exchange.getBody();
	}

	@Override
	public BiologyFamilyRelationAnnotate getFamilyRelationAnnotate(String analysisSampleId) {
		String url = GatewayService.getServiceUrl("/biology/annotation/getFamilyRelationAnnotate/{analysisSample}");
		ResponseEntity<BiologyFamilyRelationAnnotate> exchange = template.exchange(url, HttpMethod.GET, null,
				new ParameterizedTypeReference<BiologyFamilyRelationAnnotate>() {
				}, analysisSampleId);
		return exchange.getBody();
	}

	@Override
	public List<TestingSchedule> getSchedulesByTaskId(String taskId) {
		String url = GatewayService.getServiceUrl("/bpm/testing/getSchedulesByTaskId/{taskId}");
		ResponseEntity<List<TestingSchedule>> exchange = template.exchange(url, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<TestingSchedule>>() {
				}, taskId);
		return exchange.getBody();
	}

	@Override
	public SangerVerifyRecordModel getSangerRecordByVerifyKey(String verifyKey)
	{
		String url = GatewayService.getServiceUrl("/bpm/testingTask/getSangerRecordByVerifyKey/{verifyKey}");
		return template.getForObject(url, SangerVerifyRecordModel.class, verifyKey);
	}
}
