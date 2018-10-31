package com.todaysoft.lims.ngs.mybatis;

import java.util.List;

import com.todaysoft.lims.ngs.model.NgsSequecingMonitorRequest;
import com.todaysoft.lims.ngs.model.NgsSequecingTask;
import com.todaysoft.lims.ngs.model.NgsSequecingTaskModel;

public interface NgsSequecingTaskMapper {

	public void insertSelective(NgsSequecingTask task);

	public List<NgsSequecingTask> getNgsSequecingList(NgsSequecingTaskModel request);

	public NgsSequecingTask selectByPrimaryKey(String id);

	public void updateByPrimaryKeySelective(NgsSequecingTask task);

	public List<NgsSequecingTask> ngsSequecingMonitorList(NgsSequecingMonitorRequest request);
}
