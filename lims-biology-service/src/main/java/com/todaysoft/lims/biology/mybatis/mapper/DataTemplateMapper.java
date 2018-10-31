package com.todaysoft.lims.biology.mybatis.mapper;

import java.util.List;

import com.todaysoft.lims.biology.model.entity.DataTemplate;
import com.todaysoft.lims.biology.model.request.DataTemplateRequest;

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