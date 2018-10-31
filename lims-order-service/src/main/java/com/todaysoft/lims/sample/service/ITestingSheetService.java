package com.todaysoft.lims.sample.service;

import java.util.List;

import com.todaysoft.lims.sample.entity.TestingSheet;
import com.todaysoft.lims.sample.model.TestingSheetJddlModel;
import com.todaysoft.lims.sample.model.request.TestingSheetListRequest;
import com.todaysoft.lims.sample.model.testingtask.TestingTaskDetailRequest;

public interface ITestingSheetService {

	List<TestingSheet> list(TestingSheetListRequest request);

	TestingSheet get(Integer id);

	void create(TestingSheet testingSheet);

	void modify(TestingSheet testingSheet);
	
	TestingSheet getSheetByActivitiTaskId(String activitiTaskId);

	List<TestingSheetJddlModel> getJddlList(TestingTaskDetailRequest request);
}
