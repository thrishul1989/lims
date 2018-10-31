package com.todaysoft.lims.sample.service;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.sample.entity.TestingSheetTaskAbsolute;
import com.todaysoft.lims.sample.entity.TestingSheetTaskAbsoluteData;

public interface IAbsoluteService {
	
	Pagination<TestingSheetTaskAbsolute> paging();
	
	TestingSheetTaskAbsolute get(Integer id);

	void create(TestingSheetTaskAbsolute request);

	void createAbsData(TestingSheetTaskAbsoluteData request);
}
