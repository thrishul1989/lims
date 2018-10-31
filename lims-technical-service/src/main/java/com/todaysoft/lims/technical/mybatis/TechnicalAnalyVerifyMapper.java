package com.todaysoft.lims.technical.mybatis;

import java.util.List;

import com.todaysoft.lims.technical.mybatis.entity.TechnicalAnalyVerify;

public interface TechnicalAnalyVerifyMapper
{
    int deleteByPrimaryKey(String id);
    
    int insert(TechnicalAnalyVerify record);
    
    int insertSelective(TechnicalAnalyVerify record);
    
    TechnicalAnalyVerify selectByPrimaryKey(String id);
    
    int updateByPrimaryKeySelective(TechnicalAnalyVerify record);
    
    int updateByPrimaryKey(TechnicalAnalyVerify record);
    
    List<TechnicalAnalyVerify> selectByRecord(String id);
    
    TechnicalAnalyVerify selectBySearcher(TechnicalAnalyVerify searcher);
    
    List<String> selectIdsByRecordId(String recordId);
}