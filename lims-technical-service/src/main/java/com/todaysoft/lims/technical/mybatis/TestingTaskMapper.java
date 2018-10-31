package com.todaysoft.lims.technical.mybatis;

import java.util.List;

import com.todaysoft.lims.technical.mybatis.entity.TestingTask;

public interface TestingTaskMapper
{
    int deleteByPrimaryKey(String id);
    
    int insert(TestingTask record);
    
    int insertSelective(TestingTask record);
    
    TestingTask selectByPrimaryKey(String id);
    
    int updateByPrimaryKeySelective(TestingTask record);
    
    int updateByPrimaryKey(TestingTask record);
    
    List<TestingTask> selectBySearcher(TestingTask searcher);
}