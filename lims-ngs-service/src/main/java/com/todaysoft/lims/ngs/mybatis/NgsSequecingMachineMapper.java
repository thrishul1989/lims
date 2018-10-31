package com.todaysoft.lims.ngs.mybatis;

import java.util.List;

import com.todaysoft.lims.ngs.model.NgsSequecingMachine;

public interface NgsSequecingMachineMapper {
    int deleteByPrimaryKey(String id);

    int insert(NgsSequecingMachine record);

    int insertSelective(NgsSequecingMachine record);

    NgsSequecingMachine selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(NgsSequecingMachine record);

    int updateByPrimaryKey(NgsSequecingMachine record);

	List<NgsSequecingMachine> machineList(int offset, int limit);

	int countList(NgsSequecingMachine request);

	List<NgsSequecingMachine> allList();
}