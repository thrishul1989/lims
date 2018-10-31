package com.todaysoft.lims.reagent.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.reagent.dao.searcher.DepartmentSearcher;
import com.todaysoft.lims.reagent.entity.Department;
import com.todaysoft.lims.reagent.service.IDepartmentService;
import com.todaysoft.lims.utils.Collections3;

@Service
public class DepartmentService implements IDepartmentService
{
    
    @Autowired
    private BaseDaoSupport baseDaoSupport;
    
    @Override
    public Pagination<Department> paging(DepartmentSearcher request)
    {
        Pagination<Department> paging = baseDaoSupport.find(request.toQuery(), request.getPageNo(), request.getPageSize(), Department.class);
        return paging;
    }
    
    @Override
    public List<Department> getList(DepartmentSearcher request)
    {
        
        return baseDaoSupport.find(Department.class, "from Department d where d.delFlag = 0 and (d.text like '%" + request.getText() + "%') ");
        
    }
    
    @Override
    @Transactional
    public Integer create(DepartmentSearcher request)
    {
        Department dep = new Department();
        BeanUtils.copyProperties(request, dep, "createTime", "deleteTime", "delFlag");
        baseDaoSupport.insert(Department.class, dep);
        return 1;
    }
    
    @Override
    @Transactional
    public Integer update(DepartmentSearcher request)
    {
        Department department = get(request.getId());
        if (null != request.getParentId())
        {
            if (request.getParentId().getId().equals(department.getId()))
            {
                return 3;//不能选择自身为上级
            }
        }
        BeanUtils.copyProperties(request, department, "createTime", "deleteTime", "delFlag");
        // TODO Auto-generated method stub
        baseDaoSupport.update(department);
        return 1;
    }
    
    @Override
    public Department get(String id)
    {
        // TODO Auto-generated method stub
        return baseDaoSupport.get(Department.class, id);
    }
    
    @Override
    @Transactional
    public Integer delete(String id)
    {
        // TODO Auto-generated method stub
        Department department = get(id);
        department.setDelFlag(1);
        baseDaoSupport.update(department);
        return 1;
    }
    
    @Override
    public boolean validate(DepartmentSearcher request)
    {
        if (Collections3.isNotEmpty(baseDaoSupport.vaildateUniquen(Department.class, request, "text")))
        {
            
            return false;
        }
        return true;
    }
    
    @Override
    public List<Department> selectParentD()
    {
        return baseDaoSupport.find(Department.class, "from Department d where d.delFlag = 0 and d.parentId = null");
    }
}
