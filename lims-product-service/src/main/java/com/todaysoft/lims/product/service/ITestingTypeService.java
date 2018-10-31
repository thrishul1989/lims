package com.todaysoft.lims.product.service;

import java.util.List;

import com.todaysoft.lims.product.entity.TestingType;

public interface ITestingTypeService {
	
	List<TestingType> testingTypeList();
	
	List<TestingType> testingSubtypeList(String parentId);
	
	TestingType get(String id);
	
	
}
