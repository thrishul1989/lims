package com.todaysoft.lims.technical.mybatis;

import java.util.List;

import com.todaysoft.lims.technical.model.request.ClaimTemplateRequest;
import com.todaysoft.lims.technical.mybatis.entity.ClaimTemplate;

public interface ClaimTemplateMapper
{
    int deleteByPrimaryKey(String id);
    
    int insert(ClaimTemplate record);
    
    ClaimTemplate selectByPrimaryKey(String id);
    
    int update(ClaimTemplate record);
    
    ClaimTemplate selectByName(String name);
    
    int count(ClaimTemplateRequest searcher);
    
    List<ClaimTemplate> search(ClaimTemplateRequest searcher);
    
    void updatePriFlagFalse();
    
    List<ClaimTemplate> selectForPriFlag();
    
    //分析要求列表
    List<ClaimTemplate> claimTemplateList();
}