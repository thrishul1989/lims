package com.todaysoft.lims.system.service;

import java.util.List;

import com.todaysoft.lims.system.model.vo.TestingType;

public interface ITestingTypeService {
	
	List<TestingType> testingTypeList();
	
	List<TestingType> testingSubtypeList(TestingType request);
	
	TestingType get(String id);
	
	
}
