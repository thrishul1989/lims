package com.todaysoft.lims.ngs.service;

import java.util.List;

import com.todaysoft.lims.ngs.model.TestTask;

public interface INgsSequecingTaskSheetService {

	List<TestTask> testList(String token);

}
