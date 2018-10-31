package com.todaysoft.lims.sample.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.sample.entity.TestingScheduleActive;
import com.todaysoft.lims.sample.entity.TestingScheduleHistory;
import com.todaysoft.lims.sample.entity.TestingTaskRunVariable;
import com.todaysoft.lims.sample.service.ITestingScheduleRunService;
import com.todaysoft.lims.utils.Collections3;

@Service
public class TestingScheduleRunService implements ITestingScheduleRunService {
	
	private Logger log = Logger.getLogger(TestingScheduleRunService.class);
	
	@Autowired
	private BaseDaoSupport baseDaoSupport;

	@Override
	@Transactional
	public void createScheduleActive(TestingScheduleActive request) {
		baseDaoSupport.insert(request);
	}

	@Override
	@Transactional
	public void deleteScheduleActive(Integer testingTaskId) {
		String hql ="DELETE TestingScheduleActive tsa WHERE tsa.testingTaskId =" + testingTaskId;
		baseDaoSupport.execute(hql);
	}

	@Override
	public Integer scheduleActiveList(String orderId, String sampleDetailId, String semantic) {
		String sql = "select distinct sa.TASK_ID from LIMS_TESTING_SCHEDULE_ACTIVE sa left join LIMS_TESTING_SCHEDULE s on sa.SCHEDULE_ID = s.ID left join LIMS_TESTING_TASK t on sa.TASK_ID = t.id where s.ORDER_ID = ? and s.SAMPLE_ID = ? and t.SEMANTIC = ?";
		Object[] parms = new Object[]{orderId, sampleDetailId, semantic};
		List<Integer> list = (List<Integer>)baseDaoSupport.findBySql(sql, parms);
		return Collections3.isNotEmpty(list) == true ? list.get(0) : null;
	}

	@Override
	@Transactional
	public void createScheduleHistory(TestingScheduleHistory request) {
		baseDaoSupport.insert(request);
	}

	@Override
	public Integer scheduleHistoryList(String orderId, String sampleDetailId, String semantic) {
		String sql = "select distinct sa.TASK_ID from LIMS_TESTING_SCHEDULE_HISTORY sa left join LIMS_TESTING_SCHEDULE s on sa.SCHEDULE_ID = s.ID left join LIMS_TESTING_TASK t on sa.TASK_ID = t.id where s.ORDER_ID = ? and s.SAMPLE_ID = ? and t.SEMANTIC = ?";
		Object[] parms = new Object[]{orderId, sampleDetailId, semantic};
		List<Integer> list = (List<Integer>)baseDaoSupport.findBySql(sql, parms);
		return Collections3.isNotEmpty(list) == true ? list.get(0) : null;
	}

	@Override
	@Transactional
	public void createTaskRunVariable(TestingTaskRunVariable request) {
		baseDaoSupport.insert(request);
	}

	@Override
	@Transactional
	public void deleteTaskRunVariable(Integer testingTaskId) {
		TestingTaskRunVariable entity = getTaskRunVariable(testingTaskId);
		baseDaoSupport.delete(entity);
	}

	@Override
	public List<TestingTaskRunVariable> taskRunVariableList(
			TestingTaskRunVariable request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TestingTaskRunVariable getTaskRunVariable(Integer testingTaskId) {
		return baseDaoSupport.get(TestingTaskRunVariable.class, testingTaskId);
	}

}
