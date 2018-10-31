package com.todaysoft.lims.biology.service;

import java.util.List;

import com.todaysoft.lims.biology.model.entity.FilterItems;

public interface IFilterItemsService
{
    void save(FilterItems item);
    
    List<FilterItems> search(FilterItems item);
    
    FilterItems get(String id);
}
