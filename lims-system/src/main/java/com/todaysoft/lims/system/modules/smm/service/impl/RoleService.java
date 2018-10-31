package com.todaysoft.lims.system.modules.smm.service.impl;

import java.util.List;

import com.google.common.collect.Lists;
import com.todaysoft.lims.system.model.vo.Authority;
import com.todaysoft.lims.system.model.vo.Customer;
import com.todaysoft.lims.system.modules.smm.model.*;
import org.springframework.beans.BeanUtils;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.modules.smm.service.IRoleService;
import com.todaysoft.lims.system.modules.smm.service.request.RoleCreateRequest;
import com.todaysoft.lims.system.modules.smm.service.request.RoleListRequest;
import com.todaysoft.lims.system.modules.smm.service.request.RoleModifyRequest;
import com.todaysoft.lims.system.service.SystemServiceLog;
import com.todaysoft.lims.system.service.impl.GatewayService;
import com.todaysoft.lims.system.service.impl.RestService;

@Service
public class RoleService extends RestService implements IRoleService
{
    @Override
    public Pagination<RoleSimpleModel> paging(RoleSearcher searcher, int pageNo, int pageSize)
    {
        RoleListRequest request = new RoleListRequest();
        BeanUtils.copyProperties(searcher, request);
        request.setPageNo(pageNo);
        request.setPageSize(pageSize);
        
        String url = GatewayService.getServiceUrl("/smm/role/paging");
        ResponseEntity<Pagination<RoleSimpleModel>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<RoleListRequest>(request), new ParameterizedTypeReference<Pagination<RoleSimpleModel>>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public List<RoleSimpleModel> list()
    {
        String url = GatewayService.getServiceUrl("/smm/role/list");
        ResponseEntity<List<RoleSimpleModel>> exchange = template.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<RoleSimpleModel>>()
        {
        });
        return exchange.getBody();
    }
    
    @Override
    public RoleDetailsModel get(String id)
    {
        String url = GatewayService.getServiceUrl("/smm/role/{id}");
        return template.getForObject(url, RoleDetailsModel.class, id);
    }
    
    @Override
    public void create(RoleCreateRequest request)
    {
        String url = GatewayService.getServiceUrl("/smm/role/create");
        template.postForLocation(url, request);
    }
    
    @Override
    @SystemServiceLog(description="角色管理-修改",type=10)
    public void modify(RoleModifyRequest request)
    {
        String url = GatewayService.getServiceUrl("/smm/role/modify");
        template.postForLocation(url, request);
    }
    
    @Override
    @SystemServiceLog(description="角色管理-删除",type=10)
    public void delete(String id)
    {
        String url = GatewayService.getServiceUrl("/smm/role/{id}");
        template.delete(url, id);
    }
    
    @Override
    public List<RoleTemplate> roleSelect(RoleSearcher searcher)
    {
        String url = GatewayService.getServiceUrl("/smm/role/roleSelect");
        ResponseEntity<List<RoleTemplate>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<RoleSearcher>(searcher), new ParameterizedTypeReference<List<RoleTemplate>>()
            {
            });
        return exchange.getBody();
    }

    @Override
    public List<UserSimpleModel> getUserListByRole(String id) {
        Role role = new Role();
        role.setId(id);
        String url = GatewayService.getServiceUrl("/smm/role/getUserListByRole");
        ResponseEntity<List<UserSimpleModel>> exchange =
                template.exchange(url, HttpMethod.POST, new HttpEntity<Role>(role), new ParameterizedTypeReference<List<UserSimpleModel>>()
                {
                });
        return exchange.getBody();
    }

    @Override
    public boolean validate(RoleSearcher searcher) {
        String url = GatewayService.getServiceUrl("/smm/role/validate");
        return template.postForObject(url, searcher, boolean.class);
    }

    @Override
    public RoleSimpleModel getRoleByName(RoleSearcher searcher) {
        String url = GatewayService.getServiceUrl("/smm/role/getRoleByName");
        ResponseEntity<RoleSimpleModel> exchange =
                template.exchange(url, HttpMethod.POST, new HttpEntity<RoleSearcher>(searcher), new ParameterizedTypeReference<RoleSimpleModel>()
                {
                });
        return exchange.getBody();
    }
}
