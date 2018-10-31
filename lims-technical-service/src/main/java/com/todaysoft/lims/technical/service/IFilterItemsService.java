package com.todaysoft.lims.technical.service;

import java.util.List;

import com.todaysoft.lims.technical.mybatis.entity.FilterItems;

public interface IFilterItemsService
{
    void save(FilterItems item);
    
    List<FilterItems> search(FilterItems item);
    
    FilterItems get(String id);
}
