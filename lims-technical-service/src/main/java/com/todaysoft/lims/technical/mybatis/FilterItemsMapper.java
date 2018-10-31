package com.todaysoft.lims.technical.mybatis;

import java.util.List;

import com.todaysoft.lims.technical.mybatis.entity.FilterItems;

import com.todaysoft.lims.technical.mybatis.entity.FilterItems;

public interface FilterItemsMapper
{
    int deleteByPrimaryKey(String id);
    
    int insert(FilterItems record);
    
    FilterItems selectByPrimaryKey(String id);
    
    int update(FilterItems record);
    
    List<FilterItems> select(FilterItems record);
}