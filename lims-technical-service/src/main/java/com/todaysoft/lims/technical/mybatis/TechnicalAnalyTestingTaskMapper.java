package com.todaysoft.lims.technical.mybatis;

import com.todaysoft.lims.technical.mybatis.entity.TechnicalAnalyTestingTask;

public interface TechnicalAnalyTestingTaskMapper {
    int deleteByPrimaryKey(String taskId);

    int insert(TechnicalAnalyTestingTask record);

    int insertSelective(TechnicalAnalyTestingTask record);

    TechnicalAnalyTestingTask selectByPrimaryKey(String taskId);

    int updateByPrimaryKeySelective(TechnicalAnalyTestingTask record);

    int updateByPrimaryKey(TechnicalAnalyTestingTask record);
}