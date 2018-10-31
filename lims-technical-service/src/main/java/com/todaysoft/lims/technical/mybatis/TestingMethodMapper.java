package com.todaysoft.lims.technical.mybatis;

import java.util.List;

import com.todaysoft.lims.technical.mybatis.entity.TestingMethod;

public interface TestingMethodMapper {
    int deleteByPrimaryKey(String id);

	int insert(TestingMethod record);

	int insertSelective(TestingMethod record);

	TestingMethod selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(TestingMethod record);

	int updateByPrimaryKey(TestingMethod record);

    List<TestingMethod> selectBySearcher(TestingMethod methodSearcher);

	
}