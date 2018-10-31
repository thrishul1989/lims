package com.todaysoft.lims.sample.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import com.todaysoft.lims.persist.BaseDaoSupport;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todaysoft.lims.sample.dao.searcher.TestingSheetTaskSearcher;
import com.todaysoft.lims.sample.entity.TestingSheetTask;
import com.todaysoft.lims.sample.entity.TestingSheetTaskCatch;
import com.todaysoft.lims.sample.entity.TestingSheetTaskResult;
import com.todaysoft.lims.sample.model.request.TestingSheetTaskModifyRequest;
import com.todaysoft.lims.sample.service.ITestingSheetTaskService;

@Service
public class TestingSheetTaskService implements ITestingSheetTaskService {

	@Autowired
	private BaseDaoSupport baseDaoSupport;
	
	@Override
	public List<TestingSheetTask> list(TestingSheetTask request) {
		TestingSheetTaskSearcher searcher = new TestingSheetTaskSearcher();
		BeanUtils.copyProperties(request, searcher);
		return baseDaoSupport.find(searcher);
	}

	@Override
	public TestingSheetTask get(Integer id) {
		return baseDaoSupport.get(TestingSheetTask.class, id);
	}

	@Override
	@Transactional
	public void create(TestingSheetTask testingSheetTask) {
		baseDaoSupport.insert(testingSheetTask);
	}

	@Override
	@Transactional
	public void modify(TestingSheetTaskModifyRequest request) {
		TestingSheetTask entity = get(request.getId());
		TestingSheetTaskResult taskResult = request.getTaskResult();
		if(null == entity.getTaskResult()){
			baseDaoSupport.insert(taskResult);
			entity.setTaskResult(taskResult);
			baseDaoSupport.merge(entity);
		}else{
			baseDaoSupport.merge(taskResult);
		}
	}

	@Override
	@Transactional
	public void createCatch(TestingSheetTaskCatch taskCatch) {
		baseDaoSupport.insert(taskCatch);
	}
	
}
