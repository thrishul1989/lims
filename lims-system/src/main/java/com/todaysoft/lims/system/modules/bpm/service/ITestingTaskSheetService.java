package com.todaysoft.lims.system.modules.bpm.service;

import java.util.List;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.system.model.vo.TestingTaskRequest;
import com.todaysoft.lims.system.modules.bpm.competeTasks.model.TestingSheet;
import com.todaysoft.lims.system.modules.bpm.competeTasks.model.TestingSheetSearcher;
import com.todaysoft.lims.system.modules.bpm.model.TestTask;

public interface ITestingTaskSheetService {

	List<TestTask> getTestingTaskSheet();

	public TestingSheet getTestingSheetByTaskId(String taskId);
	
	public TestingTaskRequest getTTRById(String testingTaskId);
	
	Pagination<TestingSheet> completeSheetpaging(TestingSheetSearcher searcher);
}
