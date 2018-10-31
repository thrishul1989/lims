package com.todaysoft.lims.system.modules.bpm.bioanalysis.service.impl;

import com.todaysoft.lims.persist.PageInfo;
import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.system.model.vo.TestingTaskRequest;
import com.todaysoft.lims.system.model.vo.testingtask.TaskSemantic;
import com.todaysoft.lims.system.modules.bpm.bioanalysis.model.*;
import com.todaysoft.lims.system.modules.bpm.bioanalysis.model.divisioncallback.BiologyDivisionCallBackModel;
import com.todaysoft.lims.system.modules.bpm.bioanalysis.service.IBiologyAnalyService;
import com.todaysoft.lims.system.modules.bpm.competeTasks.model.TestingSheet;
import com.todaysoft.lims.system.modules.bpm.competeTasks.model.TestingSheetSearcher;
import com.todaysoft.lims.system.modules.bpm.competeTasks.model.division.BiologyAnnotationSheetViewModel;
import com.todaysoft.lims.system.modules.bpm.model.TestTask;
import com.todaysoft.lims.system.modules.bpm.service.ITestingTaskSheetService;
import com.todaysoft.lims.system.service.impl.GatewayService;
import com.todaysoft.lims.system.service.impl.RestService;
import com.todaysoft.lims.utils.Collections3;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BiologyAnnotationService extends RestService implements ITestingTaskSheetService {

	@Override
	public TestingTaskRequest getTTRById(String testingTaskId) {
		String url = GatewayService.getServiceUrl("/biology/annotation/getTaskById/{testingTaskId}");
		ResponseEntity<BiologyAnnotationTask> exchange = template.exchange(url, HttpMethod.GET, null,
				new ParameterizedTypeReference<BiologyAnnotationTask>() {
				}, testingTaskId);
		BiologyAnnotationTask task = exchange.getBody();
		if (null != task) {
			TestingTaskRequest request = new TestingTaskRequest();
			request.setId(task.getId());
			request.setSemantic(TaskSemantic.BIOLOGY_ANNOTATION);
			request.setStatus(task.getStatus());
			request.setName("生信注释");
			request.setAnnotationState(task.getAnnotationState());
			request.setStatisticsState(task.getStatisticsState());
			request.setReceivedSampleCode(task.getSampleCode());
			if (task.getResubmit()==0)//针对同一样本重做：0-不是 1-是
			{
				request.setResubmit(false);
			}
			else if (task.getResubmit()==1)
			{
				request.setResubmit(true);
			}
			request.setResubmitCount(task.getResubmitCount());
			request.setEndTime(task.getEndTime());
			request.setErrorState(task.getErrorState());
			return request;
		}
		return null;
	}
	@Override
	public TestingSheet getTestingSheetByTaskId(String taskId) {
		String url = GatewayService.getServiceUrl("/biology/annotation/getSheetListByTaskId/{taskId}");
		ResponseEntity<List<BiologyAnnotationSheet>> exchange = template.exchange(url, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<BiologyAnnotationSheet>>() {
				}, taskId);
		List<BiologyAnnotationSheet> records = exchange.getBody();
		if (Collections3.isNotEmpty(records)) {
			BiologyAnnotationSheet sheet = records.get(0);
			TestingSheet testingSheet = new TestingSheet();
			testingSheet.setId(sheet.getId());
			testingSheet.setAssignerId(sheet.getTesterId());
			testingSheet.setAssignerName(sheet.getTesterName());
			testingSheet.setAssignTime(sheet.getCreateTime());
			testingSheet.setCode(sheet.getCode());
			testingSheet.setTaskName("生信注释");
			testingSheet.setTesterId(sheet.getTesterId());
			testingSheet.setTesterName(sheet.getTesterName());
			testingSheet.setSemantic(TaskSemantic.BIOLOGY_ANNOTATION);
			return testingSheet;
		}
		return null;
	}

	@Override
	public List<TestTask> getTestingTaskSheet() {
		return null;
	}

	@Override
	public Pagination<TestingSheet> completeSheetpaging(TestingSheetSearcher searcher) {
		String url = GatewayService.getServiceUrl("/biology/annotation/completeSheetPaging/{pageNo}/{pageSize}");
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

}
