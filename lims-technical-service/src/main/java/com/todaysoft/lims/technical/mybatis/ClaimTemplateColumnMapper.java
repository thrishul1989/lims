package com.todaysoft.lims.technical.mybatis;

import java.util.List;

import com.todaysoft.lims.technical.mybatis.entity.ClaimTemplateColumn;

import com.todaysoft.lims.technical.mybatis.entity.ClaimTemplateColumn;

public interface ClaimTemplateColumnMapper
{
    int deleteByPrimaryKey(String id);
    
    int insert(ClaimTemplateColumn record);
    
    ClaimTemplateColumn selectByPrimaryKey(String id);
    
    int update(ClaimTemplateColumn record);
    
    List<ClaimTemplateColumn> selectByTemplateId(String templateId);
    
    int deleteByTemplateId(String templateId);
    
}