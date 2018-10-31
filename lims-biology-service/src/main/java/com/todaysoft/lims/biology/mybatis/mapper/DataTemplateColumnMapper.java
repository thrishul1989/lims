package com.todaysoft.lims.biology.mybatis.mapper;

import java.util.List;

import com.todaysoft.lims.biology.model.entity.DataTemplateColumn;

public interface DataTemplateColumnMapper
{
    int deleteByPrimaryKey(String id);
    
    int insert(DataTemplateColumn record);
    
    DataTemplateColumn selectByPrimaryKey(String id);
    
    int update(DataTemplateColumn record);
    
    List<DataTemplateColumn> selectByTemplateId(String templateId);
    
    int deleteByTemplateId(String templateId);
    
    List<DataTemplateColumn> selectByGroupName(DataTemplateColumn record);
}