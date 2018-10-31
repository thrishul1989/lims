package com.todaysoft.lims.ngs.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.todaysoft.lims.ngs.model.NgsSequecingMachine;
import com.todaysoft.lims.ngs.model.NgsSequecingMonitorRequest;
import com.todaysoft.lims.ngs.model.NgsSequecingTask;
import com.todaysoft.lims.ngs.model.NgsSequecingTaskModel;
import com.todaysoft.lims.ngs.model.NgsSequencingAssignModel;
import com.todaysoft.lims.ngs.model.NgsSequencingAssignRequest;
import com.todaysoft.lims.ngs.model.NgsSequencingSheetSubmitRequest;
import com.todaysoft.lims.ngs.model.NgsSequencingSubmitModel;
import com.todaysoft.lims.ngs.model.TestTask;
import com.todaysoft.lims.ngs.model.param.NgsSequecingMachineMdReq;
import com.todaysoft.lims.ngs.service.INgsSequecingMachineService;
import com.todaysoft.lims.ngs.service.INgsSequecingTaskSheetService;
import com.todaysoft.lims.ngs.service.INgsTaskService;
import com.todaysoft.lims.ngs.utils.RequestHeaders;

@RestController
@RequestMapping("/bpm/ngs")
public class NgsController {
	@Autowired
	private INgsSequecingMachineService service;

	@Autowired
	private INgsTaskService ngsTaskService;

	@Autowired
	private INgsSequecingTaskSheetService sheetService;

	@RequestMapping(value = "/testList", method = RequestMethod.POST)
	public List<TestTask> testList(@RequestHeader(RequestHeaders.MEMBER_TOKEN) String token) {

		List<TestTask> list = sheetService.testList(token);

		return list;
	}

	@RequestMapping(value = "/getNgsSequecingList/{pageNo}/{pageSize}", method = RequestMethod.POST)
	public PageInfo<NgsSequecingTask> getNgsSequecingList(@RequestBody NgsSequecingTaskModel request, @PathVariable int pageNo, @PathVariable int pageSize) {
		PageHelper.startPage(pageNo, 10);

		List<NgsSequecingTask> list = ngsTaskService.getNgsSequecingList(request);
		PageInfo<NgsSequecingTask> pagination = new PageInfo<NgsSequecingTask>(list);

		return pagination;
	}
	
	@RequestMapping(value = "/ngsSequecingMonitorList/{pageNo}/{pageSize}", method = RequestMethod.POST)
	public PageInfo<NgsSequecingTask> ngsSequecingMonitorList(@RequestBody NgsSequecingMonitorRequest request, @PathVariable int pageNo, @PathVariable int pageSize) {
		PageHelper.startPage(pageNo, 10);

		List<NgsSequecingTask> list = ngsTaskService.ngsSequecingMonitorList(request);
		PageInfo<NgsSequecingTask> pagination = new PageInfo<NgsSequecingTask>(list);

		return pagination;
	}
	
	

	@RequestMapping(value = "/NgsSequencing/assigning/{id}", method = RequestMethod.GET)
	public NgsSequencingAssignModel assigning(@PathVariable String id) {
		return ngsTaskService.getAssignModel(id);
	}

	@RequestMapping(value = "/NgsSequencing/assign", method = RequestMethod.POST)
	public void assign(@RequestBody NgsSequencingAssignRequest request, @RequestHeader(RequestHeaders.MEMBER_TOKEN) String token) {
		ngsTaskService.assign(request, token);
	}

	@RequestMapping(value = "/getNgsSequecingSubmitModel/{sheetId}", method = RequestMethod.GET)
	public NgsSequencingSubmitModel getSubmitModel(@PathVariable String sheetId) {
		return ngsTaskService.getSubmitModel(sheetId);
	}
	
	@RequestMapping(value = "/sheets/submit", method = RequestMethod.POST)
	public void submitSheet(@RequestBody NgsSequencingSheetSubmitRequest request, @RequestHeader(RequestHeaders.MEMBER_TOKEN) String token) {
		ngsTaskService.submitSheet(request, token);
	}
	
	@RequestMapping(value = "/erroTaskReSequecing/{taskId}", method = RequestMethod.POST)
	public void erroTaskReSequecing(@PathVariable String taskId) {
		ngsTaskService.erroTaskReSequecing(taskId);
	}
	
	

	@RequestMapping(value = "/machineList/{pageNo}/{pageSize}", method = RequestMethod.POST)
	public PageInfo<NgsSequecingMachine> machineList(@RequestBody NgsSequecingMachine request, @PathVariable int pageNo, @PathVariable int pageSize) {

		int count = service.countList(request);
		List<NgsSequecingMachine> list = service.machineList((pageNo - 1) * pageSize, pageSize);
		PageInfo<NgsSequecingMachine> pagination = new PageInfo<NgsSequecingMachine>(list);
		pagination.setPageSize(pageSize);
		pagination.setPageNum(pageNo);
		pagination.setTotal(count);
		return pagination;
	}

	@RequestMapping(value = "/getSequecingMachine/{id}", method = RequestMethod.GET)
	public NgsSequecingMachine getSequecingMachine(@PathVariable String id) {
		NgsSequecingMachine sequecingMachine = service.get(id);
		return sequecingMachine;
	}

	@RequestMapping(value = "/modifySequecingMachine", method = RequestMethod.POST)
	public void modifySequecingMachine(@RequestBody NgsSequecingMachineMdReq request) {
		service.modifySequecingMachine(request);

	}
	
	@RequestMapping(value = "/sequecingCallBack/{monitorTaskId}/{fileIntegrity}", method = RequestMethod.POST)
	public void sequecingCallBack(@PathVariable String monitorTaskId, @PathVariable int fileIntegrity){
		ngsTaskService.sequecingCallBack(monitorTaskId,fileIntegrity);
	}

}
