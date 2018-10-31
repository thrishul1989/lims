package com.todaysoft.lims.biology.mybatis.mapper;

import java.util.List;

import com.todaysoft.lims.biology.model.entity.FilterItems;

public interface FilterItemsMapper
{
    int deleteByPrimaryKey(String id);
    
    int insert(FilterItems record);
    
    FilterItems selectByPrimaryKey(String id);
    
    int update(FilterItems record);
    
    List<FilterItems> select(FilterItems record);
}