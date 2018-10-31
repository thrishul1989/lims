package com.todaysoft.lims.gateway.action;

import com.todaysoft.lims.gateway.model.TestingSheetJddlModel;
import com.todaysoft.lims.gateway.model.TestingSheetTask;
import com.todaysoft.lims.gateway.model.TestingSheetTaskAbsolute;
import com.todaysoft.lims.gateway.model.request.testingtask.TestingTaskDetailRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.todaysoft.lims.gateway.model.TestingSheet;
import com.todaysoft.lims.gateway.service.ITestingSheetService;
import com.todaysoft.lims.persist.Pagination;

import java.util.List;

@RestController
@RequestMapping("/testingSheet")
public class TestingSheetController {

	@Autowired
	private ITestingSheetService service;
	
	@RequestMapping(value = "/activitiTaskId/{activitiTaskId}", method = RequestMethod.GET)
	private TestingSheet getSheetByActivitiTaskId(@PathVariable String activitiTaskId){
		 return service.getSheetByActivitiTaskId(activitiTaskId);
	}

	@RequestMapping(value = "/getJddlList", method = RequestMethod.POST)
	private List<TestingSheetJddlModel> getJddlList(@RequestBody TestingTaskDetailRequest request){
		return service.getJddlList(request);
	}

	@RequestMapping(value = "/jddl/contrast", method = RequestMethod.POST)
	private List<TestingSheetTask> getJDDLContrast(TestingSheetTask request){
		return service.getJDDLContrast(request);
	}

	@RequestMapping(value = "/absolute/paging", method = RequestMethod.GET)
	private Pagination<TestingSheetTaskAbsolute> absolutePaging(){
		return service.absolutePaging();
	}
}
