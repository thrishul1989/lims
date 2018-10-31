package com.todaysoft.lims.reagent.service;

import java.util.List;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.reagent.entity.AnalyzeMethod;
import com.todaysoft.lims.reagent.model.request.AnalyzeMethodPagingRequest;

public interface IAnalyzeService
{
    
    Pagination<AnalyzeMethod> paging(AnalyzeMethodPagingRequest request);
    
    AnalyzeMethod getMethod(Integer id);
    
    Integer create(AnalyzeMethod request);
    
    Integer modify(AnalyzeMethod request);
    
    void delete(Integer id);
    
    boolean validate(AnalyzeMethod request);
    
    List<AnalyzeMethod> list(AnalyzeMethod request);
    
}
