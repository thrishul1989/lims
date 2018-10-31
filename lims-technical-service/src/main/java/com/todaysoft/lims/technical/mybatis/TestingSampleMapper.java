package com.todaysoft.lims.technical.mybatis;

import java.util.List;

import com.todaysoft.lims.technical.mybatis.entity.TestingSample;

public interface TestingSampleMapper
{
    int deleteByPrimaryKey(String id);
    
    int insert(TestingSample record);
    
    int insertSelective(TestingSample record);
    
    TestingSample selectByPrimaryKey(String id);
    
    List<TestingSample> selectByParentSample(String parentSampleId);
    
    TestingSample selectByPrimarySampleCode(String sampleCode);
    
    int updateByPrimaryKeySelective(TestingSample record);
    
    int updateByPrimaryKeyWithBLOBs(TestingSample record);
    
    int updateByPrimaryKey(TestingSample record);
}