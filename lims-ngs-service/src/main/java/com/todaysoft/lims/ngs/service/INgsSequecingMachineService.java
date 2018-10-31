package com.todaysoft.lims.ngs.service;

import java.util.List;
import java.util.Map;

import com.todaysoft.lims.ngs.model.NgsSequecingMachine;

import com.todaysoft.lims.ngs.model.param.NgsSequecingMachineMdReq;


public interface INgsSequecingMachineService {

	List<NgsSequecingMachine> machineList(int offset, int limit);

	int countList(NgsSequecingMachine request);

	NgsSequecingMachine get(String id);

	void modifySequecingMachine(NgsSequecingMachineMdReq request);



}
