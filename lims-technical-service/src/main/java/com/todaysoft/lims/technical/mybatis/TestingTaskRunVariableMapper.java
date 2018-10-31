package com.todaysoft.lims.technical.mybatis;

import com.todaysoft.lims.technical.mybatis.entity.TestingTaskRunVariable;

public interface TestingTaskRunVariableMapper {
    int deleteByPrimaryKey(String taskId);

    int insert(TestingTaskRunVariable record);

    int insertSelective(TestingTaskRunVariable record);

    TestingTaskRunVariable selectByPrimaryKey(String taskId);

    int updateByPrimaryKeySelective(TestingTaskRunVariable record);

    int updateByPrimaryKey(TestingTaskRunVariable record);
}