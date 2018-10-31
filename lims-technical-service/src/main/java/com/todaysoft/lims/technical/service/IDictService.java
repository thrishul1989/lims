package com.todaysoft.lims.technical.service;

import java.util.List;

import com.todaysoft.lims.technical.mybatis.entity.Dict;

public interface IDictService
{
    List<Dict> getDictList(Dict record);
    
    Dict getByText(String category, String text);
    
    List<Dict> getEntries(String category);
}
