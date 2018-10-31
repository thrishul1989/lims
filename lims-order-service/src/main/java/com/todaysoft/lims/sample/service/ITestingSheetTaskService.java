package com.todaysoft.lims.sample.service;

import java.util.List;

import com.todaysoft.lims.sample.entity.TestingSheetTask;
import com.todaysoft.lims.sample.entity.TestingSheetTaskCatch;
import com.todaysoft.lims.sample.model.request.TestingSheetTaskModifyRequest;

public interface ITestingSheetTaskService {

	List<TestingSheetTask> list(TestingSheetTask request);

	TestingSheetTask get(Integer id);

	void create(TestingSheetTask testingSheetTask);

	void modify(TestingSheetTaskModifyRequest request);
	
	void createCatch(TestingSheetTaskCatch taskCatch);
}
