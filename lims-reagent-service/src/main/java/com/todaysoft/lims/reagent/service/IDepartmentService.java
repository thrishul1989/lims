package com.todaysoft.lims.reagent.service;

import java.util.List;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.reagent.dao.searcher.DepartmentSearcher;
import com.todaysoft.lims.reagent.entity.Department;

public interface IDepartmentService
{
    
    Pagination<Department> paging(DepartmentSearcher request);
    
    List<Department> getList(DepartmentSearcher request);
    
    Integer create(DepartmentSearcher request);
    
    Integer update(DepartmentSearcher request);
    
    Department get(String id);
    
    Integer delete(String id);
    
    boolean validate(DepartmentSearcher request);
    
    List<Department> selectParentD();
}
