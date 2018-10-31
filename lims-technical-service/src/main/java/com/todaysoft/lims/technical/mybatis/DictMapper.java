package com.todaysoft.lims.technical.mybatis;

import java.util.List;

import com.todaysoft.lims.technical.mybatis.entity.Dict;

public interface DictMapper
{
    int deleteByPrimaryKey(String id);
    
    int insert(Dict record);
    
    int insertSelective(Dict record);
    
    Dict selectByPrimaryKey(String id);
    
    int updateByPrimaryKeySelective(Dict record);
    
    int updateByPrimaryKey(Dict record);
    
    List<Dict> search(Dict record);
    
    Dict selectByText(Dict record);
    
    List<Dict> getEntriesFromDatabase(String category);
}