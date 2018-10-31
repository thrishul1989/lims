package com.todaysoft.lims.technical.mybatis;

import com.todaysoft.lims.technical.mybatis.entity.TestingScheduleHistory;

import java.util.List;

public interface TestingScheduleHistoryMapper {
    int deleteByPrimaryKey(String id);

    int insert(TestingScheduleHistory record);

    int insertSelective(TestingScheduleHistory record);

    TestingScheduleHistory selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(TestingScheduleHistory record);

    int updateByPrimaryKey(TestingScheduleHistory record);

    List<TestingScheduleHistory> getByTaskId(String id);
}