package com.todaysoft.lims.ngs.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todaysoft.lims.ngs.adapter.bcm.BCMAdapter;
import com.todaysoft.lims.ngs.model.NgsSequecingLan;
import com.todaysoft.lims.ngs.model.NgsSequecingMachine;
import com.todaysoft.lims.ngs.model.NgsSequecingSheet;
import com.todaysoft.lims.ngs.model.NgsSequecingTask;
import com.todaysoft.lims.ngs.model.NgsSequencingAssignModel;
import com.todaysoft.lims.ngs.model.NgsSequencingAssignRequest;
import com.todaysoft.lims.ngs.model.NgsSequencingSubmitModel;
import com.todaysoft.lims.ngs.model.param.NgsSequecingMachineMdReq;
import com.todaysoft.lims.ngs.mybatis.NgsSequecingLanMapper;
import com.todaysoft.lims.ngs.mybatis.NgsSequecingMachineMapper;
import com.todaysoft.lims.ngs.mybatis.NgsSequecingTaskMapper;
import com.todaysoft.lims.ngs.service.INgsSequecingMachineService;
import com.todaysoft.lims.ngs.utils.StringUtils;

@Service
public class NgsSequecingMachineService implements INgsSequecingMachineService {

	@Autowired
	private NgsSequecingMachineMapper mapper;

	@Autowired
	private NgsSequecingLanMapper ngsSequecingLanMapper;

	@Autowired
	private NgsSequecingMachineMapper ngsSequecingMachineMapper;

	

	@Override
	public List<NgsSequecingMachine> machineList(int offset, int limit) {
		// TODO Auto-generated method stub
		return mapper.machineList(offset, limit);
	}

	@Override
	public int countList(NgsSequecingMachine request) {
		// TODO Auto-generated method stub
		return mapper.countList(request);
	}

	@Override
	public NgsSequecingMachine get(String id) {
		// TODO Auto-generated method stub
		return mapper.selectByPrimaryKey(id);
	}

	@Override
	public void modifySequecingMachine(NgsSequecingMachineMdReq request) {
		// 删除关联
		ngsSequecingLanMapper.deleteByMachine(request.getSequecingMachineId());

		// 增加
		for (String lanCode : request.getLanCode()) {
			NgsSequecingLan sequecingLan = new NgsSequecingLan();
			sequecingLan.setMachineId(request.getSequecingMachineId());
			sequecingLan.setLanCode(lanCode);
			ngsSequecingLanMapper.insert(sequecingLan);
		}

	}







	

}
