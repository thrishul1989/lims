package com.todaysoft.lims.system.modules.smm.service;

import java.util.List;

import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.modules.smm.model.Dict;
import com.todaysoft.lims.system.modules.smm.model.DictSearcher;

public interface IDictService
{
    Pagination<Dict> paging(DictSearcher searcher, int pageNo, int pageSize);
    
    List<Dict> getEntries(String category);
    
    Dict getEntry(String category, String value);
    
    Dict get(String id);
    
    Dict getDict(String category, String value);
    
    void modify(Dict request);
    
    Dict getDictByText(String category, String text);
}
