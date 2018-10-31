package com.todaysoft.lims.technical.mybatis;

import java.util.List;

import com.todaysoft.lims.technical.mybatis.entity.SchedulePlanTask;

public interface SchedulePlanTaskMapper {
	int deleteByPrimaryKey(String id);

	int insert(SchedulePlanTask record);

	int insertSelective(SchedulePlanTask record);

	SchedulePlanTask selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(SchedulePlanTask record);

	int updateByPrimaryKey(SchedulePlanTask record);

	List<SchedulePlanTask> selectBySearcher(SchedulePlanTask searcher);
}