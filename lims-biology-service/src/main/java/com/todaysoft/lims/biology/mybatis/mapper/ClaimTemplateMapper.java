package com.todaysoft.lims.biology.mybatis.mapper;

import com.todaysoft.lims.biology.model.entity.ClaimTemplate;
import com.todaysoft.lims.biology.model.request.ClaimTemplateRequest;

import java.util.List;

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