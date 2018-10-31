package com.todaysoft.lims.system.modules.bpm.ontest.service.impl;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.todaysoft.lims.persist.PageInfo;
import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.system.model.vo.TestingTaskRequest;
import com.todaysoft.lims.system.modules.bpm.competeTasks.model.TestingSheet;
import com.todaysoft.lims.system.modules.bpm.competeTasks.model.TestingSheetSearcher;
import com.todaysoft.lims.system.modules.bpm.model.TestTask;
import com.todaysoft.lims.system.modules.bpm.model.TestTaskSearcher;
import com.todaysoft.lims.system.modules.bpm.ontest.model.NgsSequecingMachine;
import com.todaysoft.lims.system.modules.bpm.ontest.model.NgsSequecingMachineMdReq;
import com.todaysoft.lims.system.modules.bpm.ontest.model.NgsSequecingMonitorRequest;
import com.todaysoft.lims.system.modules.bpm.ontest.model.NgsSequecingTask;
import com.todaysoft.lims.system.modules.bpm.ontest.model.SequencingAssignModel;
import com.todaysoft.lims.system.modules.bpm.ontest.model.SequencingAssignRequest;
import com.todaysoft.lims.system.modules.bpm.ontest.model.SequencingAssignableTaskSearcher;
import com.todaysoft.lims.system.modules.bpm.ontest.model.SequencingSubmitModel;
import com.todaysoft.lims.system.modules.bpm.ontest.model.SequencingSubmitRequest;
import com.todaysoft.lims.system.modules.bpm.ontest.model.SequencingTask;
import com.todaysoft.lims.system.modules.bpm.ontest.service.ISequencingService;
import com.todaysoft.lims.system.modules.bpm.service.ITestingTaskSheetService;
import com.todaysoft.lims.system.service.impl.GatewayService;
import com.todaysoft.lims.system.service.impl.RestService;

@Service
public class SequencingService extends RestService implements ISequencingService, ITestingTaskSheetService {
	@Override
	public List<SequencingTask> getAssignableList(SequencingAssignableTaskSearcher searcher) {
		String url = GatewayService.getServiceUrl("/bpm/testing/sequencing/tasks/assignable");
		ResponseEntity<List<SequencingTask>> exchange = template.exchange(url, HttpMethod.POST, new HttpEntity<SequencingAssignableTaskSearcher>(
				searcher), new ParameterizedTypeReference<List<SequencingTask>>() {
		});
		return exchange.getBody();
	}

	@Override
	public SequencingAssignModel getAssignModel(String id) {
		String url = GatewayService.getServiceUrl("/bpm/testing/sequencing/tasks/assigning/{id}");
		return template.getForObject(url, SequencingAssignModel.class, id);
	}

	@Override
	public void assign(SequencingAssignRequest request) {
		String url = GatewayService.getServiceUrl("/bpm/testing/sequencing/tasks/assign");
		template.postForLocation(url, request);
	}

	@Override
	public SequencingSubmitModel getSubmitModel(String id) {
		String url = GatewayService.getServiceUrl("/bpm/testing/sequencing/sheets/{id}");
		return template.getForObject(url, SequencingSubmitModel.class, id);
	}

	@Override
	public void submit(SequencingSubmitRequest request) {
		String url = GatewayService.getServiceUrl("/bpm/testing/sequencing/sheets/submit");
		template.postForLocation(url, request);
	}

	@Override
	public PageInfo<NgsSequecingMachine> machineList(NgsSequecingMachine searcher, int pageNo, int pageSize) {

		String url = GatewayService.getServiceUrl("/bpm/ngs/machineList/{pageNo}/{pageSize}");
		ResponseEntity<PageInfo<NgsSequecingMachine>> exchange = template.exchange(url, HttpMethod.POST,
				new HttpEntity<NgsSequecingMachine>(searcher), new ParameterizedTypeReference<PageInfo<NgsSequecingMachine>>() {
				}, pageNo, pageSize);
		return exchange.getBody();
	}

	@Override
	public NgsSequecingMachine getSequecingMachine(String id) {
		String url = GatewayService.getServiceUrl("/bpm/ngs/getSequecingMachine/{id}");
		return template.getForObject(url, NgsSequecingMachine.class, id);
	}

	@Override
	public void modifySequecingMachine(NgsSequecingMachineMdReq request) {
		String url = GatewayService.getServiceUrl("/bpm/ngs/modifySequecingMachine");
		template.postForObject(url, request, String.class);

	}

	@Override
	public PageInfo<NgsSequecingTask> getNgsSequecingList(NgsSequecingTask searcher, int pageNo, int pageSize) {
		String url = GatewayService.getServiceUrl("/bpm/ngs/getNgsSequecingList/{pageNo}/{pageSize}");
		ResponseEntity<PageInfo<NgsSequecingTask>> exchange = template.exchange(url, HttpMethod.POST, new HttpEntity<NgsSequecingTask>(searcher),
				new ParameterizedTypeReference<PageInfo<NgsSequecingTask>>() {
				}, pageNo, pageSize);
		return exchange.getBody();
	}

	@Override
	public SequencingAssignModel getNgsSequecingAssignModel(String id) {
		String url = GatewayService.getServiceUrl("/bpm/ngs/NgsSequencing/assigning/{id}");
		return template.getForObject(url, SequencingAssignModel.class, id);
	}

	@Override
	public void assignNgsSequecing(SequencingAssignRequest request) {
		String url = GatewayService.getServiceUrl("/bpm/ngs/NgsSequencing/assign");
		template.postForLocation(url, request);

	}

	@Override
	public List<TestTask> getTestingTaskSheet() {
		String url = GatewayService.getServiceUrl("/bpm/ngs/testList");
		ResponseEntity<List<TestTask>> exchange = template.exchange(url, HttpMethod.POST, new HttpEntity<TestTaskSearcher>(new TestTaskSearcher()),
				new ParameterizedTypeReference<List<TestTask>>() {
				});
		return exchange.getBody();
	}

	@Override
	public SequencingSubmitModel getNgsSequecingSubmitModel(String sheetId) {
		String url = GatewayService.getServiceUrl("/bpm/ngs/getNgsSequecingSubmitModel/{sheetId}");
		return template.getForObject(url, SequencingSubmitModel.class, sheetId);
	}

	@Override
	public void submitNgsSequecing(SequencingSubmitRequest request) {
		String url = GatewayService.getServiceUrl("/bpm/ngs/sheets/submit");
		template.postForLocation(url, request);

	}

	@Override
	public void ngsSequecingCallBack(String monitorTaskId, Integer fileIntegrity) {
		String url = GatewayService.getServiceUrl("/bpm/ngs/sequecingCallBack/{monitorTaskId}/{fileIntegrity}");
		template.postForLocation(url, new SequencingSubmitModel(), monitorTaskId, fileIntegrity);

	}

	@Override
	public PageInfo<NgsSequecingTask> ngsSequecingMonitorList(NgsSequecingMonitorRequest searcher, int pageNo, int pageSize) {
		String url = GatewayService.getServiceUrl("/bpm/ngs/ngsSequecingMonitorList/{pageNo}/{pageSize}");
		ResponseEntity<PageInfo<NgsSequecingTask>> exchange = template.exchange(url, HttpMethod.POST, new HttpEntity<NgsSequecingMonitorRequest>(
				searcher), new ParameterizedTypeReference<PageInfo<NgsSequecingTask>>() {
		}, pageNo, pageSize);
		return exchange.getBody();
	}

	@Override
	public void erroTaskReSequecing(String id) {
		String url = GatewayService.getServiceUrl("/bpm/ngs/erroTaskReSequecing/{taskId}");
		template.postForLocation(url, new NgsSequecingTask(), id);

	}

	@Override
	public TestingSheet getTestingSheetByTaskId(String taskId) {
		String url = GatewayService.getServiceUrl("/bpm/ngs/testingTask/getTestingSheetByTaskId/{taskId}");
		return template.getForObject(url, TestingSheet.class, taskId);
	}

	@Override
	public TestingTaskRequest getTTRById(String testingTaskId) {
		String url = GatewayService.getServiceUrl("/bpm/ngs/testingTask/getTaskById/{testingTaskId}");
		return template.getForObject(url, TestingTaskRequest.class, testingTaskId);
	}

	@Override
	public Pagination<TestingSheet> completeSheetpaging(TestingSheetSearcher searcher) {
		Pagination<TestingSheet> pagination = new Pagination<>();
		String url = GatewayService.getServiceUrl("/bpm/ngs/testingTask/completeSheetPaging/{pageNo}/{pageSize}");
		ResponseEntity<PageInfo<TestingSheet>> exchange = template.exchange(url, HttpMethod.POST, new HttpEntity<TestingSheetSearcher>(searcher),
				new ParameterizedTypeReference<PageInfo<TestingSheet>>() {
				}, searcher.getPageNo(), searcher.getPageSize());
		pagination.setRecords(exchange.getBody().getList());
		pagination.setTotalCount((int) exchange.getBody().getTotal());
		pagination.setPageSize(searcher.getPageSize());
		pagination.setPageNo(searcher.getPageNo());
		return pagination;
	}
}
