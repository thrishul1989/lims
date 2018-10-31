package com.todaysoft.lims.reagent.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.reagent.dao.searcher.DepartmentSearcher;
import com.todaysoft.lims.reagent.entity.Department;
import com.todaysoft.lims.reagent.service.IDepartmentService;

@RestController
@RequestMapping("/bsm/department")
public class DepartmentController
{
    @Autowired
    private IDepartmentService IdepartmentService;
    
    @RequestMapping(value = "/paging", method = RequestMethod.POST)
    public Pagination<Department> paging(@RequestBody DepartmentSearcher request)
    {
        return IdepartmentService.paging(request);
    }
    
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public List<Department> getList(@RequestBody DepartmentSearcher request)
    {
        return IdepartmentService.getList(request);
    }
    
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Integer create(@RequestBody DepartmentSearcher request)
    {
        return IdepartmentService.create(request);
    }
    
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Integer update(@RequestBody DepartmentSearcher request)
    {
        return IdepartmentService.update(request);
    }
    
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Integer delete(@RequestBody DepartmentSearcher request)
    {
        return IdepartmentService.delete(request.getId());
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Department getDepartment(@PathVariable String id)
    {
        return IdepartmentService.get(id);
    }
    
    @RequestMapping(value = "/validate", method = RequestMethod.POST)
    public boolean validate(@RequestBody DepartmentSearcher request)
    {
        return IdepartmentService.validate(request);
    }
    
    @RequestMapping(value = "/selectParentD", method = RequestMethod.GET)
    public List<Department> selectParentD()
    {
        return IdepartmentService.selectParentD();
    }
    
}
