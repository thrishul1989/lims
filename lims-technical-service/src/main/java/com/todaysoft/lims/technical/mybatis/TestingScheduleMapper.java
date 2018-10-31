package com.todaysoft.lims.technical.mybatis;

import java.util.List;

import com.todaysoft.lims.technical.mybatis.entity.TestingSchedule;

public interface TestingScheduleMapper {
    int deleteByPrimaryKey(String id);

    int insert(TestingSchedule record);

    int insertSelective(TestingSchedule record);

    TestingSchedule selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(TestingSchedule record);

    int updateByPrimaryKey(TestingSchedule record);
    
    List<TestingSchedule> selectBySearcher(TestingSchedule searcher);
}