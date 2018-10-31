package com.todaysoft.lims.ngs.mybatis;

import java.util.List;

import com.todaysoft.lims.ngs.model.NgsSequecingLan;

public interface NgsSequecingLanMapper {

	public void deleteByMachine(String sequecingMachineId);

	public void insert(NgsSequecingLan sequecingLan);

	public List<NgsSequecingLan> getByMachineCode(String sequecingMachineCode);
}
