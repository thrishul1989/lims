package com.todaysoft.lims.system.service;

import java.util.List;

import com.todaysoft.lims.system.model.vo.Department;
import com.todaysoft.lims.system.model.vo.Pagination;

public interface IDepartmentService
{
    
    Pagination<Department> paging(Department searcher, int pageNo, int pageSize);
    
    List<Department> departmentSelect(Department searcher);
    
    Integer create(Department searcher);
    
    Integer update(Department searcher);
    
    Integer delete(Department searcher);
    
    Department get(String id);
    
    boolean validate(Department request);
    
    List<Department> selectParentD();
    
}
