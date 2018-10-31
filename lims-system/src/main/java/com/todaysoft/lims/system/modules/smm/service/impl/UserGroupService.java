package com.todaysoft.lims.system.modules.smm.service.impl;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.modules.smm.model.UserGroup;
import com.todaysoft.lims.system.modules.smm.service.IUserGroupService;
import com.todaysoft.lims.system.modules.smm.service.request.UserGroupCreateRequest;
import com.todaysoft.lims.system.modules.smm.service.request.UserGroupModifyRequest;
import com.todaysoft.lims.system.modules.smm.service.request.UserGroupPagingRequest;
import com.todaysoft.lims.system.service.impl.GatewayService;
import com.todaysoft.lims.system.service.impl.RestService;

@Service
public class UserGroupService extends RestService implements IUserGroupService
{
    
    @Override
    public UserGroup getById(String id)
    {
        String url = GatewayService.getServiceUrl("/smm/userGroup/{id}");
        return template.getForObject(url, UserGroup.class, id);
    }
    
    @Override
    public boolean delete(String id)
    {
        String url = GatewayService.getServiceUrl("/smm/userGroup/delete/{id}");
        return template.getForObject(url, boolean.class, id);
    }
    
    @Override
    public Pagination<UserGroup> paging(UserGroupPagingRequest request, int pageNo, int pageSize)
    {
        request.setPageNo(pageNo);
        request.setPageSize(pageSize);
        String url = GatewayService.getServiceUrl("/smm/userGroup/paging");
        ResponseEntity<Pagination<UserGroup>> exchange =
            template.exchange(url,
                HttpMethod.POST,
                new HttpEntity<UserGroupPagingRequest>(request),
                new ParameterizedTypeReference<Pagination<UserGroup>>()
                {
                });
        return exchange.getBody();
    }
    
    @Override
    public void create(UserGroupCreateRequest request)
    {
        post("/smm/userGroup/create", request, UserGroup.class);
        
    }
    
    @Override
    public void modify(UserGroupModifyRequest request)
    {
        post("/smm/userGroup/modify", request, UserGroup.class);
        
    }
    
    @Override
    public List<UserGroup> list(UserGroupPagingRequest request)
    {
        request.setPageNo(1);
        request.setPageSize(10);
        String url = GatewayService.getServiceUrl("/smm/userGroup/paging");
        ResponseEntity<Pagination<UserGroup>> exchange =
            template.exchange(url,
                HttpMethod.POST,
                new HttpEntity<UserGroupPagingRequest>(request),
                new ParameterizedTypeReference<Pagination<UserGroup>>()
                {
                });
        return exchange.getBody().getRecords();
    }
    
    @Override
    public boolean validate(UserGroupCreateRequest request)
    {
        String url = GatewayService.getServiceUrl("/smm/userGroup/validate");
        return template.postForObject(url, request, boolean.class);
    }
    
}
