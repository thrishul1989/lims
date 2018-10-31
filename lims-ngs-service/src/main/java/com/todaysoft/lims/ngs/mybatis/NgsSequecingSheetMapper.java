package com.todaysoft.lims.ngs.mybatis;

import java.util.List;

import com.todaysoft.lims.ngs.model.NgsSequecingSheet;
import com.todaysoft.lims.ngs.model.TestTask;
import com.todaysoft.lims.ngs.model.TestingSheetModel;
import com.todaysoft.lims.ngs.model.TestingSheetRequest;

public interface NgsSequecingSheetMapper {
	int deleteByPrimaryKey(String id);

	int insert(NgsSequecingSheet record);

	int insertSelective(NgsSequecingSheet record);

	NgsSequecingSheet selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(NgsSequecingSheet record);

	int updateByPrimaryKey(NgsSequecingSheet record);

	List<NgsSequecingSheet> testList(String token);

	NgsSequecingSheet selectByTaskId(String taskId);

	List<NgsSequecingSheet> completeSheetPaging(TestingSheetRequest request);
}