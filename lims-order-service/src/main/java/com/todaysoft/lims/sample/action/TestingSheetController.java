package com.todaysoft.lims.sample.action;

import com.google.common.collect.Lists;
import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.sample.model.TestingSheetJddlModel;
import com.todaysoft.lims.sample.model.testingtask.TestingTaskDetailRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.todaysoft.lims.sample.entity.TestingSheet;
import com.todaysoft.lims.sample.entity.TestingSheetTask;
import com.todaysoft.lims.sample.entity.TestingSheetTaskAbsolute;
import com.todaysoft.lims.sample.service.IAbsoluteService;
import com.todaysoft.lims.sample.service.ITestingSheetService;
import com.todaysoft.lims.sample.service.ITestingSheetTaskService;

import java.util.List;

@RestController
@RequestMapping("/testingSheet")
public class TestingSheetController {

	@Autowired
	private ITestingSheetService service;
	
	@Autowired
	private ITestingSheetTaskService sheetTaskService;
	
	@Autowired
	private IAbsoluteService absoluteService;
	
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
		List<TestingSheetTask> taskList = sheetTaskService.list(request);
		List<TestingSheetTask> contrastList = Lists.newArrayList();
		taskList.stream().filter(task -> null != task).forEach(task ->{
			if(!contrastList.contains(task)){
				contrastList.add(task);
			}
		});
		return contrastList;
	}
	
	@RequestMapping(value = "/absolute/paging", method = RequestMethod.GET)
	private Pagination<TestingSheetTaskAbsolute> absolutePaging(){
		return absoluteService.paging();
	}
}
