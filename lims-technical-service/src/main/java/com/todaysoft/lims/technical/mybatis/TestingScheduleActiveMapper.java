package com.todaysoft.lims.technical.mybatis;

import java.util.List;

import com.todaysoft.lims.technical.mybatis.entity.TestingScheduleActive;

public interface TestingScheduleActiveMapper {
	int deleteByPrimaryKey(String id);

	int insert(TestingScheduleActive record);

	int insertSelective(TestingScheduleActive record);

	TestingScheduleActive selectByPrimaryKey(String id);

	List<TestingScheduleActive> selectBySearcher(TestingScheduleActive searcher);

	int updateByPrimaryKeySelective(TestingScheduleActive record);

	int updateByPrimaryKey(TestingScheduleActive record);
}