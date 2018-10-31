package com.todaysoft.lims.system.service.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.todaysoft.lims.system.model.vo.Department;
import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.service.IDepartmentService;

@Service
public class DepartmentService extends RestService implements IDepartmentService
{
    
    @Override
    public Pagination<Department> paging(Department searcher, int pageNo, int pageSize)
    {
        
        searcher.setPageNo(pageNo);
        searcher.setPageSize(pageSize);
        
        String url = GatewayService.getServiceUrl("/bsm/department/paging");
        ResponseEntity<Pagination<Department>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<Department>(searcher), new ParameterizedTypeReference<Pagination<Department>>()
            {
            });
        Pagination<Department> paging = exchange.getBody();
        return paging;
    }
    
    @Override
    public List<Department> departmentSelect(Department searcher)
    {
        
        String url = GatewayService.getServiceUrl("/bsm/department/list");
        ResponseEntity<List<Department>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<Department>(searcher), new ParameterizedTypeReference<List<Department>>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public Integer create(Department searcher)
    {
        String url = GatewayService.getServiceUrl("/bsm/department/create");
        return template.postForObject(url, searcher, Integer.class);
    }
    
    @Override
    public Integer update(Department searcher)
    {
        // TODO Auto-generated method stub
        String url = GatewayService.getServiceUrl("/bsm/department/update");
        return template.postForObject(url, searcher, Integer.class);
    }
    
    @Override
    public Integer delete(Department searcher)
    {
        String url = GatewayService.getServiceUrl("/bsm/department/delete");
        return template.postForObject(url, searcher, Integer.class);
    }
    
    @Override
    public Department get(String id)
    {
        String url = GatewayService.getServiceUrl("/bsm/department/{id}");
        return template.getForObject(url, Department.class, Collections.singletonMap("id", id));
    }
    
    @Override
    public boolean validate(Department request)
    {
        String url = GatewayService.getServiceUrl("/bsm/department/validate");
        boolean res = template.postForObject(url, request, boolean.class);
        return res;
    }
    
    @Override
    public List<Department> selectParentD()
    {
        String url = GatewayService.getServiceUrl("/bsm/department/selectParentD");
        ResponseEntity<List<Department>> exchange = template.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<Department>>()
        {
        });
        return exchange.getBody();
    }
}
