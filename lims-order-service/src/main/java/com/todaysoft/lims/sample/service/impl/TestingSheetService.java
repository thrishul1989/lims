package com.todaysoft.lims.sample.service.impl;

import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

import com.google.common.collect.Lists;
import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.sample.dao.TestingSheetDao;
import com.todaysoft.lims.sample.entity.SampleReceiveDetail;
import com.todaysoft.lims.sample.entity.TestingSheetTask;
import com.todaysoft.lims.sample.entity.TestingSheetTaskAbsolute;
import com.todaysoft.lims.sample.entity.TestingSheetTaskResult;
import com.todaysoft.lims.sample.model.TestingSheetJddlModel;
import com.todaysoft.lims.sample.model.testingtask.TestingTaskDetailRequest;
import com.todaysoft.lims.utils.Collections3;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todaysoft.lims.sample.dao.searcher.TestingSheetSearcher;
import com.todaysoft.lims.sample.entity.TestingSheet;
import com.todaysoft.lims.sample.model.request.TestingSheetListRequest;
import com.todaysoft.lims.sample.service.IAbsoluteService;
import com.todaysoft.lims.sample.service.ISampleReceiveService;
import com.todaysoft.lims.sample.service.ITestingSheetService;
import com.todaysoft.lims.sample.service.ITestingSheetTaskService;

@Service
public class TestingSheetService implements ITestingSheetService {

	@Autowired
	private BaseDaoSupport baseDaoSupport;

	@Autowired
	private TestingSheetDao testingSheetDao;
	
	@Autowired
	private ISampleReceiveService sampleReceiveService;
	
	@Autowired
	private ITestingSheetTaskService sheetTaskService;
	
	@Autowired
	private IAbsoluteService absoluteService;
	
	@Override
	public List<TestingSheet> list(TestingSheetListRequest request) {
		TestingSheetSearcher searcher = new TestingSheetSearcher();
		BeanUtils.copyProperties(request, searcher);
		return baseDaoSupport.find(searcher);
	}

	@Override
	public TestingSheet get(Integer id) {
		return baseDaoSupport.get(TestingSheet.class, id);
	}


	@Override
	@Transactional
	public void create(TestingSheet testingSheet) {
		baseDaoSupport.insert(testingSheet);
	}

	@Override
	@Transactional
	public void modify(TestingSheet testingSheet) {
		baseDaoSupport.update(testingSheet);
	}

	@Override
	public TestingSheet getSheetByActivitiTaskId(String activitiTaskId) {
		TestingSheetListRequest listRequest = new TestingSheetListRequest();
		listRequest.setActivitiTaskId(activitiTaskId);
		
		if(list(listRequest).size() > 0){
			TestingSheet testingSheet = list(listRequest).get(0);
			for(TestingSheetTask tst : testingSheet.getSheetTaskList()){
				if(null != tst.getAbsoluteId()){
					TestingSheetTaskAbsolute absolute = absoluteService.get(tst.getAbsoluteId());
					tst.setInputSamplecode(absolute.getSourceSampleCode());
					TestingSheetTaskResult result = new TestingSheetTaskResult();
					result.setFragmentLength(absolute.getFragmentLength());
					result.setConcentrationPC(absolute.getConcentrationPC());
					result.setCluster(absolute.getCluster());
					result.setAbsoluteDataList(absolute.getAbsoluteDataList());
					tst.setTaskResult(result);
				}
			}
			return testingSheet;
		}
		return null;
	}

	@Override
	@Transactional
	public List<TestingSheetJddlModel> getJddlList(TestingTaskDetailRequest request) {
		List<TestingSheetJddlModel> sheetJddlModelList = Lists.newArrayList();
		List<TestingSheet> lists = Lists.newArrayList();
		NamedQueryer queryer = new NamedQueryer();
		queryer.setHql("FROM TestingSheet s WHERE s.semantic = :semantic");
		queryer.setNames(Arrays.asList("semantic"));
		queryer.setValues(Arrays.asList((Object) request.getSemantic()));
		lists = baseDaoSupport.find(queryer, TestingSheet.class);
		TestingSheetJddlModel testingSheetJddlModel = null;
		for(TestingSheet testingSheet : lists){
			for(TestingSheetTask testingSheetTask : testingSheet.getSheetTaskList()){
				TestingSheetTaskResult tsr = testingSheetTask.getTaskResult();
				if(null == tsr) continue;
				testingSheetJddlModel = new TestingSheetJddlModel();
				testingSheetJddlModel.setId(testingSheet.getId());
				testingSheetJddlModel.setTestCode(testingSheet.getCode());
				testingSheetJddlModel.setCreateTime(testingSheet.getCreateTime());
				testingSheetJddlModel.setFragmentLength(tsr.getFragmentLength());
				testingSheetJddlModel.setConcentrationPc(tsr.getConcentrationPC());
				testingSheetJddlModel.setCluster(tsr.getCluster());
				sheetJddlModelList.add(testingSheetJddlModel);
			}
		}
		return sheetJddlModelList;
	}
}
