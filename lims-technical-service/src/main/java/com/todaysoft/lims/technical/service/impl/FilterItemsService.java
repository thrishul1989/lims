package com.todaysoft.lims.technical.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.todaysoft.lims.technical.mybatis.FilterItemsMapper;
import com.todaysoft.lims.technical.mybatis.entity.FilterItems;
import com.todaysoft.lims.technical.service.IFilterItemsService;
import com.todaysoft.lims.technical.utils.IdGen;

@Service
public class FilterItemsService implements IFilterItemsService
{
    @Autowired
    private FilterItemsMapper filterItemsmapper;
    
    @Override
    @Transactional
    public void save(FilterItems item)
    {
        item.setId(IdGen.uuid());
        item.setDelFlag(false);
        filterItemsmapper.insert(item);
    }
    
    @Override
    public List<FilterItems> search(FilterItems item)
    {
        List<FilterItems> list = new ArrayList<>();
        if (item.getSemantic() == null || item.getSemantic() == "")
        {
            return list;
        }
        return filterItemsmapper.select(item);
    }
    
    @Override
    public FilterItems get(String id)
    {
        return filterItemsmapper.selectByPrimaryKey(id);
    }
    
}
