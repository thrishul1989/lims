package com.todaysoft.lims.system.modules.bpm.ontest.service;

import java.util.List;

import com.todaysoft.lims.persist.PageInfo;
import com.todaysoft.lims.system.modules.bpm.ontest.model.NgsSequecingMachine;
import com.todaysoft.lims.system.modules.bpm.ontest.model.NgsSequecingMachineMdReq;
import com.todaysoft.lims.system.modules.bpm.ontest.model.NgsSequecingMonitorRequest;
import com.todaysoft.lims.system.modules.bpm.ontest.model.NgsSequecingTask;
import com.todaysoft.lims.system.modules.bpm.ontest.model.SequencingAssignModel;
import com.todaysoft.lims.system.modules.bpm.ontest.model.SequencingAssignRequest;
import com.todaysoft.lims.system.modules.bpm.ontest.model.SequencingAssignableTaskSearcher;
import com.todaysoft.lims.system.modules.bpm.ontest.model.SequencingSubmitModel;
import com.todaysoft.lims.system.modules.bpm.ontest.model.SequencingSubmitRequest;
import com.todaysoft.lims.system.modules.bpm.ontest.model.SequencingTask;

public interface ISequencingService 
{
    List<SequencingTask> getAssignableList(SequencingAssignableTaskSearcher searcher);

    SequencingAssignModel getAssignModel(String id);

    void assign(SequencingAssignRequest request);

    SequencingSubmitModel getSubmitModel(String id);

    void submit(SequencingSubmitRequest request);

    PageInfo<NgsSequecingMachine> machineList(NgsSequecingMachine searcher, int pageNo, int pageSize);

	NgsSequecingMachine getSequecingMachine(String id);

	void modifySequecingMachine(NgsSequecingMachineMdReq request);

	PageInfo<NgsSequecingTask> getNgsSequecingList(NgsSequecingTask searcher,int pageNo, int pageSize);

	SequencingAssignModel getNgsSequecingAssignModel(String id);

	void assignNgsSequecing(SequencingAssignRequest request);

	SequencingSubmitModel getNgsSequecingSubmitModel(String sheetId);

	void submitNgsSequecing(SequencingSubmitRequest request);

	void ngsSequecingCallBack(String monitorTaskId, Integer fileIntegrity);

	PageInfo<NgsSequecingTask> ngsSequecingMonitorList(NgsSequecingMonitorRequest searcher, int pageNo, int i);

	void erroTaskReSequecing(String id);
}
