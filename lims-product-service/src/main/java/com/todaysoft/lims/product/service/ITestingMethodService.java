package com.todaysoft.lims.product.service;

import java.util.List;

import com.todaysoft.lims.persist.ISearcher;
import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.product.entity.TestingMethod;

public interface ITestingMethodService
{
    Pagination<TestingMethod> paging(ISearcher<TestingMethod> searcher, int pageNo, int pageSize);
    
    List<TestingMethod> list(ISearcher<TestingMethod> searcher);
    
    TestingMethod get(String id);
    
    TestingMethod getByName(TestingMethod method);
    
    List<TestingMethod> listAll();
}
