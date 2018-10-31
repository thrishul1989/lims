package com.todaysoft.lims.ngs.action;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.todaysoft.lims.ngs.model.NgsSequecingSheet;
import com.todaysoft.lims.ngs.model.NgsSequencingSubmitModel;
import com.todaysoft.lims.ngs.model.TestingSheetModel;
import com.todaysoft.lims.ngs.model.TestingSheetRequest;
import com.todaysoft.lims.ngs.model.TestingTaskModel;
import com.todaysoft.lims.ngs.service.INgsTaskService;

/**
 * 该类用于其他服务调用
 * */
@RestController
@RequestMapping("/bpm/ngs/testingTask")
public class NgsTesingTaskController {

	@Autowired
	private INgsTaskService ngsTaskService;

	@RequestMapping(value = "/getTestingSheetByTaskId/{taskId}", method = RequestMethod.GET)
	public TestingSheetModel getTestingSheetByTaskId(@PathVariable String taskId) {
		return ngsTaskService.getTestingSheetByTaskId(taskId);
	}

	@RequestMapping(value = "/getTaskById/{testingTaskId}", method = RequestMethod.GET)
	public TestingTaskModel getTaskById(@PathVariable String testingTaskId) {
		return ngsTaskService.getTaskById(testingTaskId);
	}

	@RequestMapping(value = "/completeSheetPaging/{pageNo}/{pageSize}", method = RequestMethod.POST)
	public PageInfo<TestingSheetModel> completeSheetPaging(@RequestBody TestingSheetRequest request, @PathVariable int pageNo,
			@PathVariable int pageSize) {
		PageHelper.startPage(pageNo, 10);

		List<NgsSequecingSheet> list = ngsTaskService.completeSheetPaging(request);
		PageInfo<NgsSequecingSheet> pagination = new PageInfo<NgsSequecingSheet>(list);
		PageInfo<TestingSheetModel> resList = new PageInfo<>();
		List<TestingSheetModel> modelList = new ArrayList<>();
		for (NgsSequecingSheet sheet : pagination.getList()) {
			TestingSheetModel model = new TestingSheetModel();
			model.setId(sheet.getId());
			model.setTaskName(ngsTaskService.NGS_SEQUECING_NAME);
			model.setCode(sheet.getCode());
			model.setAssignerName(sheet.getAssignerName());
			model.setAssignTime(sheet.getAssignTime());
			model.setTesterName(sheet.getTesterName());
			model.setSubmitTime(sheet.getSubmitTime());
			model.setSemantic(ngsTaskService.NGS_SEQUECING_SEMANTIC);
			modelList.add(model);
		}
		resList.setTotal(pagination.getTotal());
		resList.setList(modelList);
		resList.setPageSize(pageSize);
		resList.setPageNum(pageNo);
		return resList;

	}

	@RequestMapping(value = "/getNgsSequencingSheet/{sheetId}", method = RequestMethod.GET)
	public NgsSequencingSubmitModel getNgsSequencingSheet(@PathVariable String sheetId) {
		return ngsTaskService.getNgsSequencingSheet(sheetId);
	}

}
