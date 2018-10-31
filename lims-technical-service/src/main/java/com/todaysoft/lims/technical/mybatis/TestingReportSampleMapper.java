package com.todaysoft.lims.technical.mybatis;

import java.util.List;

import com.todaysoft.lims.technical.mybatis.entity.TestingReportSample;

public interface TestingReportSampleMapper {
    int deleteByPrimaryKey(String id);

    int insert(TestingReportSample record);

    int insertSelective(TestingReportSample record);

    TestingReportSample selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(TestingReportSample record);

    int updateByPrimaryKey(TestingReportSample record);

    List<TestingReportSample> selectByScheduleId(String id);
}