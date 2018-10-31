package com.todaysoft.lims.gateway.service.impl;

import com.google.common.collect.Lists;
import com.todaysoft.lims.gateway.model.TestingSheet;
import com.todaysoft.lims.gateway.model.TestingSheetJddlModel;
import com.todaysoft.lims.gateway.model.TestingSheetTask;
import com.todaysoft.lims.gateway.model.TestingSheetTaskAbsolute;
import com.todaysoft.lims.gateway.model.request.samplereceive.SampleReceiveDetail;
import com.todaysoft.lims.gateway.model.request.testingtask.TestingTaskDetailRequest;
import com.todaysoft.lims.gateway.service.IProbeService;
import com.todaysoft.lims.gateway.service.ISampleReceiveService;
import com.todaysoft.lims.gateway.service.ITestingScheduleService;
import com.todaysoft.lims.gateway.service.ITestingSheetService;
import com.todaysoft.lims.gateway.service.adapter.TestingSheetAdapter;
import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestingSheetService implements ITestingSheetService {
	
	@Autowired
	private TestingSheetAdapter adapter;
	
	@Autowired
	private ITestingScheduleService testingScheduleService;
	
	@Autowired
	private ISampleReceiveService sampleReceiveService;
	
	@Autowired
	private IProbeService probeService;

	@Override
	public TestingSheet getSheetByActivitiTaskId(String activitiTaskId) {
		TestingSheet testingSheet = adapter.getSheetByActivitiTaskId(activitiTaskId);
		/*if(null != testingSheet){
			List<String> semantic = Lists.newArrayList("LIBRARY_CATCH","XDDL","JDDL","ON_TEST","BIOINFORMATICS_ANALYZE","ANALYSIS","REPORT_CREATE","REPORT_CHECK","REPORT_MAIL");
			if(Collections3.isNotEmpty(testingSheet.getSheetTaskList())){
				if(!semantic.contains(testingSheet.getSemantic())){
					for(TestingSheetTask sheetTask : testingSheet.getSheetTaskList()){
						Integer sampleId = sheetTask.getSampleDetailId();
						SampleReceiveDetail sampleDetail = sampleReceiveService.getSampleReceiveDetail(sampleId);
						sheetTask.setSampleDetail(sampleDetail);
					}
				}else{
					testingSheet.getSheetTaskList().stream().filter(sheetTask -> null != sheetTask.getProbeId()).forEach(sheetTask -> {
						Probe probe = probeService.get(sheetTask.getProbeId());
						sheetTask.setProbe(probe);
					});
				}
			}
		}*/
		return testingSheet;
	}

	@Override
	public List<TestingSheetJddlModel> getJddlList(TestingTaskDetailRequest request) {
		return adapter.getJddlList(request);
	}

	@Override
	public List<TestingSheetTask> getJDDLContrast(TestingSheetTask request) {
		return adapter.getJDDLContrast(request);
	}

	@Override
	public Pagination<TestingSheetTaskAbsolute> absolutePaging() {
		return adapter.absolutePaging();
	}
}
