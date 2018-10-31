package com.todaysoft.lims.gateway.service;

import com.todaysoft.lims.gateway.model.TestingSheet;
import com.todaysoft.lims.gateway.model.TestingSheetJddlModel;
import com.todaysoft.lims.gateway.model.TestingSheetTask;
import com.todaysoft.lims.gateway.model.TestingSheetTaskAbsolute;
import com.todaysoft.lims.gateway.model.request.testingtask.TestingTaskDetailRequest;
import com.todaysoft.lims.persist.Pagination;

import java.util.List;

public interface ITestingSheetService {

	TestingSheet getSheetByActivitiTaskId(String activitiTaskId);

	List<TestingSheetJddlModel> getJddlList(TestingTaskDetailRequest request);
	
	List<TestingSheetTask> getJDDLContrast(TestingSheetTask request);
	
	Pagination<TestingSheetTaskAbsolute> absolutePaging();
}
