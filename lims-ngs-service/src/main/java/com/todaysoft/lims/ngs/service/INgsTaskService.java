package com.todaysoft.lims.ngs.service;

import java.util.List;

import com.todaysoft.lims.ngs.model.NgsSequecingMonitorRequest;
import com.todaysoft.lims.ngs.model.NgsSequecingSheet;
import com.todaysoft.lims.ngs.model.NgsSequecingTask;
import com.todaysoft.lims.ngs.model.NgsSequecingTaskModel;
import com.todaysoft.lims.ngs.model.NgsSequencingAssignModel;
import com.todaysoft.lims.ngs.model.NgsSequencingAssignRequest;
import com.todaysoft.lims.ngs.model.NgsSequencingSheetSubmitRequest;
import com.todaysoft.lims.ngs.model.NgsSequencingSubmitModel;
import com.todaysoft.lims.ngs.model.TestingSheetModel;
import com.todaysoft.lims.ngs.model.TestingSheetRequest;
import com.todaysoft.lims.ngs.model.TestingTaskModel;
import com.todaysoft.lims.ngs.service.core.event.NgsCreateEvent;

public interface INgsTaskService {

	String createNgsSequecingTask(NgsCreateEvent event);

	List<NgsSequecingTask> getNgsSequecingList(NgsSequecingTaskModel request);

	NgsSequencingAssignModel getAssignModel(String id);

	void assign(NgsSequencingAssignRequest request, String token);

	NgsSequencingSubmitModel getSubmitModel(String sheetId);

	void submitSheet(NgsSequencingSheetSubmitRequest request, String token);

	void sequecingCallBack(String monitorTaskId, int fileIntegrity);

	List<NgsSequecingTask> ngsSequecingMonitorList(NgsSequecingMonitorRequest request);

	void erroTaskReSequecing(String taskId);

	TestingSheetModel getTestingSheetByTaskId(String taskId);

	TestingTaskModel getTaskById(String testingTaskId);

	List<NgsSequecingSheet> completeSheetPaging(TestingSheetRequest request);

	NgsSequencingSubmitModel getNgsSequencingSheet(String sheetId);

	static final String NGS_SEQUECING_SEMANTIC = "NGS-SEQUECING";

	static final String NGS_SEQUECING_NAME = "NGS测序";

	void sequecingState();

}
