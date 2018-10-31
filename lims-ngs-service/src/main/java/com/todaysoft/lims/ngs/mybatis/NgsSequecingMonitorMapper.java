package com.todaysoft.lims.ngs.mybatis;

import java.util.List;

import com.todaysoft.lims.ngs.model.NgsSequecingMonitor;

public interface NgsSequecingMonitorMapper {
	int insert(NgsSequecingMonitor record);

	int insertSelective(NgsSequecingMonitor record);

	NgsSequecingMonitor selectByMonitorId(String monitorId);

	void updateByPrimaryKeySelective(NgsSequecingMonitor monitor);
	
	List<NgsSequecingMonitor> unFinishList();
}