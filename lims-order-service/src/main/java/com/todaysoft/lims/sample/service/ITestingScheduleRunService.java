package com.todaysoft.lims.sample.service;

import java.util.List;

import com.todaysoft.lims.sample.entity.TestingScheduleActive;
import com.todaysoft.lims.sample.entity.TestingScheduleHistory;
import com.todaysoft.lims.sample.entity.TestingTaskRunVariable;

public interface ITestingScheduleRunService {

	void createScheduleActive(TestingScheduleActive request);
	
	void deleteScheduleActive(Integer testingTaskId);
	
	Integer scheduleActiveList(String orderId, String sampleDetailId, String semantic);
	
	void createScheduleHistory(TestingScheduleHistory request);
	
	Integer scheduleHistoryList(String orderId, String sampleDetailId, String semantic);
	
	void createTaskRunVariable(TestingTaskRunVariable request);
	
	void deleteTaskRunVariable(Integer testingTaskId);
	
	List<TestingTaskRunVariable> taskRunVariableList(TestingTaskRunVariable request);
	
	TestingTaskRunVariable getTaskRunVariable(Integer testingTaskId);
}
