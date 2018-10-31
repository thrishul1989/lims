package com.todaysoft.lims.ngs.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todaysoft.lims.ngs.adapter.smm.SMMAdapter;
import com.todaysoft.lims.ngs.model.NgsSequecingSheet;
import com.todaysoft.lims.ngs.model.TestTask;
import com.todaysoft.lims.ngs.model.UserMinimalModel;
import com.todaysoft.lims.ngs.mybatis.NgsSequecingSheetMapper;
import com.todaysoft.lims.ngs.service.INgsSequecingTaskSheetService;

@Service
public class NgsSequecingTaskSheetService implements INgsSequecingTaskSheetService {
	@Autowired
	private SMMAdapter smmAdapter;

	@Autowired
	private NgsSequecingSheetMapper sheetMapper;

	@Override
	public List<TestTask> testList(String token) {
		UserMinimalModel loginer = smmAdapter.getLoginUser(token);
		List<NgsSequecingSheet> sheetList = sheetMapper.testList(loginer.getId());
		List<TestTask> list = new ArrayList<>();
		for (NgsSequecingSheet sheet : sheetList) {
			TestTask task = new TestTask();
			task.setId(sheet.getId());
			task.setTestingName(NgsTaskService.NGS_SEQUECING_NAME);
			task.setTestingCode(sheet.getCode());
			task.setRemark(sheet.getDescription());
			task.setAssignerName(sheet.getAssignerName());
			task.setSemantic(NgsTaskService.NGS_SEQUECING_SEMANTIC);
			task.setAssignTime(sheet.getAssignTime());
			list.add(task);

		}
		return list;
	}
}
