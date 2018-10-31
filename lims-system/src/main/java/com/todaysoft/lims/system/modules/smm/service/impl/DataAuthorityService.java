package com.todaysoft.lims.system.modules.smm.service.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.modules.smm.model.DataAuthority;
import com.todaysoft.lims.system.modules.smm.model.DataAuthorityRole;
import com.todaysoft.lims.system.modules.smm.model.DataAuthorityUser;
import com.todaysoft.lims.system.modules.smm.service.IDataAuthorityService;
import com.todaysoft.lims.system.service.impl.GatewayService;
import com.todaysoft.lims.system.service.impl.RestService;

@Service
public class DataAuthorityService extends RestService implements IDataAuthorityService
{
    
    @Override
    public Pagination<DataAuthority> paging(DataAuthority searcher, int pageNo, int pageSize)
    {
        searcher.setPageNo(pageNo);
        searcher.setPageSize(pageSize);
        String url = GatewayService.getServiceUrl("/smm/dataAuthority/paging");
        ResponseEntity<Pagination<DataAuthority>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<DataAuthority>(searcher), new ParameterizedTypeReference<Pagination<DataAuthority>>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public Pagination<DataAuthorityRole> dataAuthorityRole(DataAuthorityRole searcher, int pageNo, int pageSize)
    {
        searcher.setPageNo(pageNo);
        searcher.setPageSize(pageSize);
        String url = GatewayService.getServiceUrl("/smm/dataAuthority/dataAuthorityRolePaging");
        ResponseEntity<Pagination<DataAuthorityRole>> exchange =
            template.exchange(url,
                HttpMethod.POST,
                new HttpEntity<DataAuthorityRole>(searcher),
                new ParameterizedTypeReference<Pagination<DataAuthorityRole>>()
                {
                });
        return exchange.getBody();
    }
    
    @Override
    public Integer createRoleDataAuthority(DataAuthorityRole request)
    {
        String url = GatewayService.getServiceUrl("/smm/dataAuthority/createRoleDataAuthority");
        return template.postForObject(url, request, Integer.class);
    }
    
    @Override
    public Integer deleteRoleAuthority(DataAuthorityRole request)
    {
        String url = GatewayService.getServiceUrl("/smm/dataAuthority/deleteRoleAuthority");
        return template.postForObject(url, request, Integer.class);
    }
    
    @Override
    public Pagination<DataAuthorityUser> dataAuthorityUser(DataAuthorityUser searcher, int pageNo, int pageSize)
    {
        searcher.setPageNo(pageNo);
        searcher.setPageSize(pageSize);
        String url = GatewayService.getServiceUrl("/smm/dataAuthority/dataAuthorityUserPaging");
        ResponseEntity<Pagination<DataAuthorityUser>> exchange =
            template.exchange(url,
                HttpMethod.POST,
                new HttpEntity<DataAuthorityUser>(searcher),
                new ParameterizedTypeReference<Pagination<DataAuthorityUser>>()
                {
                });
        return exchange.getBody();
    }
    
    @Override
    public Integer createUserDataAuthority(DataAuthorityUser request)
    {
        String url = GatewayService.getServiceUrl("/smm/dataAuthority/createUserDataAuthority");
        return template.postForObject(url, request, Integer.class);
    }
    
    @Override
    public Integer deleteUserAuthority(DataAuthorityUser request)
    {
        String url = GatewayService.getServiceUrl("/smm/dataAuthority/deleteUserAuthority");
        return template.postForObject(url, request, Integer.class);
    }
    
    @Override
    public List<DataAuthorityRole> dataAuthorityRoleByRoleId(DataAuthorityRole searcher)
    {
        String url = GatewayService.getServiceUrl("/smm/dataAuthority/dataAuthorityRoleByRoleId");
        ResponseEntity<List<DataAuthorityRole>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<DataAuthorityRole>(searcher), new ParameterizedTypeReference<List<DataAuthorityRole>>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public List<DataAuthorityUser> dataAuthorityUserByUserId(DataAuthorityUser searcher)
    {
        String url = GatewayService.getServiceUrl("/smm/dataAuthority/dataAuthorityUserByUserId");
        ResponseEntity<List<DataAuthorityUser>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<DataAuthorityUser>(searcher), new ParameterizedTypeReference<List<DataAuthorityUser>>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public List<DataAuthority> list()
    {
        String url = GatewayService.getServiceUrl("/smm/dataAuthority/list");
        ResponseEntity<List<DataAuthority>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<DataAuthority>(new DataAuthority()), new ParameterizedTypeReference<List<DataAuthority>>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public List<String> getSubordinates(String leaders)
    {
        String url = GatewayService.getServiceUrl("/smm/dataAuthority/getSubordinates/{leaders}");
        return template.getForObject(url, List.class, Collections.singletonMap("leaders", leaders));
    }
    
}
