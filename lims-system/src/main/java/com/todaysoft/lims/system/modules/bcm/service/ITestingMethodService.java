package com.todaysoft.lims.system.modules.bcm.service;

import java.util.List;

import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.modules.bcm.model.TestingMethod;
import com.todaysoft.lims.system.modules.bcm.model.TestingMethodSearcher;

public interface ITestingMethodService
{
    Pagination<TestingMethod> paging(TestingMethodSearcher searcher, int pageNo, int pageSize);
    
    List<TestingMethod> list(TestingMethodSearcher searcher);
    
    TestingMethod get(String id);
    
    TestingMethod getByName(TestingMethod method);
    
    List<TestingMethod> listAll();
}
