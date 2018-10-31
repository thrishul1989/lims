package com.todaysoft.lims.smm.service;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.smm.entity.Dict;
import com.todaysoft.lims.smm.model.request.DictPagingRequest;
import com.todaysoft.lims.smm.model.response.DictAdapter;

public interface IDictService
{
    Pagination<DictAdapter> paging(DictPagingRequest request);
    
    List<DictAdapter> getEntries(String category);
    
    DictAdapter getEntry(String category, String value);
    
    DictAdapter get(String id);
    
    DictAdapter getDict(String category, String value);
    
    void modify(Dict request);
    
    DictAdapter getDictByText(String category, @PathVariable String text);
}