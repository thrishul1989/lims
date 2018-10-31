package com.todaysoft.lims.technical.mybatis;

import java.util.List;

import com.todaysoft.lims.technical.model.request.DataTemplateRequest;
import com.todaysoft.lims.technical.mybatis.entity.DataTemplate;

public interface DataTemplateMapper
{
    int deleteByPrimaryKey(String id);
    
    int insert(DataTemplate record);
    
    DataTemplate selectByPrimaryKey(String id);
    
    int update(DataTemplate record);
    
    DataTemplate selectByName(String name);
    
    int count(DataTemplateRequest searcher);
    
    List<DataTemplate> search(DataTemplateRequest searcher);
}