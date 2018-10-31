package com.todaysoft.lims.system.modules.smm.service.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.modules.smm.model.AuthorizedUser;
import com.todaysoft.lims.system.modules.smm.model.UserArchive;
import com.todaysoft.lims.system.modules.smm.model.UserDetailsModel;
import com.todaysoft.lims.system.modules.smm.model.UserSearcher;
import com.todaysoft.lims.system.modules.smm.model.UserSimpleModel;
import com.todaysoft.lims.system.modules.smm.service.IUserService;
import com.todaysoft.lims.system.modules.smm.service.request.UserCreateRequest;
import com.todaysoft.lims.system.modules.smm.service.request.UserListRequest;
import com.todaysoft.lims.system.modules.smm.service.request.UserModifyRequest;
import com.todaysoft.lims.system.modules.smm.service.request.UserPasswordResetRequest;
import com.todaysoft.lims.system.service.SystemServiceLog;
import com.todaysoft.lims.system.service.impl.GatewayService;
import com.todaysoft.lims.system.service.impl.RestService;

@Service
public class UserService extends RestService implements IUserService
{
    @Autowired
    private RestTemplate template;
    
    @Override
    public Pagination<UserSimpleModel> paging(UserSearcher searcher, int pageNo, int pageSize)
    {
        UserListRequest request = new UserListRequest();
        BeanUtils.copyProperties(searcher, request);
        request.setPageNo(pageNo);
        request.setPageSize(pageSize);
        return page("/smm/user/paging", request);
    }
    
    @Override
    public List<UserSimpleModel> list(UserSearcher searcher)
    {
        UserListRequest request = new UserListRequest();
        BeanUtils.copyProperties(searcher, request);
        String url = GatewayService.getServiceUrl("/smm/user/list");
        ResponseEntity<List<UserSimpleModel>> exchange =
            template.exchange(url,
                HttpMethod.POST,
                new HttpEntity<UserListRequest>(request),
                new ParameterizedTypeReference<List<UserSimpleModel>>()
                {
                });
        return exchange.getBody();
    }
    
    @Override
    public UserDetailsModel getUserByID(String id)
    {
        return getById("/smm/user/{id}", UserDetailsModel.class, id);
    }
    
    @Override
    public boolean unique(String username)
    {
        UserSearcher request = new UserSearcher();
        request.setUsername(username);
        String url = GatewayService.getServiceUrl("/smm/user/unique");
        return template.postForObject(url, request, boolean.class);
    }
    
    @Override
    public void create(UserCreateRequest request)
    {
        post("/smm/user/create", request, String.class);
    }
    
    @Override
    @SystemServiceLog(description="用户管理-修改",type=8)
    public void modify(UserModifyRequest request)
    {
        post("/smm/user/modify", request, String.class);
    }
    
    @Override
    @SystemServiceLog(description="用户管理-删除",type=8)
    public Integer delete(String id)
    {
        String url = GatewayService.getServiceUrl("/smm/user/delete/{id}");
        return template.getForObject(url, Integer.class, id);
    }
    
    @Override
    public void resetPassword(UserPasswordResetRequest request)
    {
        String url = GatewayService.getServiceUrl("/smm/user/password/reset");
        template.postForObject(url, request, String.class);
    }
    
    @Override
    @SystemServiceLog(description="用户管理-启用",type=8)
    public void setEnable(String id)
    {
        String url = GatewayService.getServiceUrl("/smm/user/enable/{id}");
        template.getForObject(url, String.class, Collections.singletonMap("id", id));
    }
    
    @Override
    @SystemServiceLog(description="用户管理-禁用",type=8)
    public void setDisable(String id)
    {
        String url = GatewayService.getServiceUrl("/smm/user/disable/{id}");
        template.getForObject(url, String.class, Collections.singletonMap("id", id));
        
    }
    
    @Override
    public AuthorizedUser getByToken()
    {
        String url = GatewayService.getServiceUrl("/smm/authorize/user");
        ResponseEntity<AuthorizedUser> exchange =
            template.exchange(url,
                HttpMethod.GET,
                new HttpEntity<Object>(null),
                new ParameterizedTypeReference<AuthorizedUser>()
                {
                });
        return exchange.getBody();
    }
    
    @Override
    public List<UserSimpleModel> userSelect(UserListRequest request)
    {
        String url = GatewayService.getServiceUrl("/smm/user/userSelect");
        ResponseEntity<List<UserSimpleModel>> exchange =
            template.exchange(url,
                HttpMethod.POST,
                new HttpEntity<UserListRequest>(request),
                new ParameterizedTypeReference<List<UserSimpleModel>>()
                {
                });
        return exchange.getBody();
    }
    
    @Override
    public boolean validate(UserSearcher searcher)
    {
        String url = GatewayService.getServiceUrl("/smm/user/validate");
        return template.postForObject(url, searcher, boolean.class);
    }
    
    @Override
    public List<UserSimpleModel> selectList(UserSearcher searcher)
    {
        UserListRequest request = new UserListRequest();
        BeanUtils.copyProperties(searcher, request);
        String url = GatewayService.getServiceUrl("/smm/user/selectList");
        ResponseEntity<List<UserSimpleModel>> exchange =
            template.exchange(url,
                HttpMethod.POST,
                new HttpEntity<UserListRequest>(request),
                new ParameterizedTypeReference<List<UserSimpleModel>>()
                {
                });
        return exchange.getBody();
    }
    
    @Override
    public List<UserSimpleModel> noSalesManList(UserSearcher searcher)
    {
        UserListRequest request = new UserListRequest();
        BeanUtils.copyProperties(searcher, request);
        String url = GatewayService.getServiceUrl("/smm/user/no_salesman_list");
        ResponseEntity<List<UserSimpleModel>> exchange =
            template.exchange(url,
                HttpMethod.POST,
                new HttpEntity<UserListRequest>(request),
                new ParameterizedTypeReference<List<UserSimpleModel>>()
                {
                });
        return exchange.getBody();
    }
    
    @Override
    public boolean validate(String id)
    {
        String url = GatewayService.getServiceUrl("/smm/user/validate/{id}");
        return template.getForObject(url, boolean.class, id);
    }
    
    @Override
    public boolean uniqueNum(String employeeNo, String id)
    {
        UserSearcher request = new UserSearcher();
        request.setId(id);
        UserArchive ua = new UserArchive();
        ua.setEmployeeNo(employeeNo);
        request.setArchive(ua);
        String url = GatewayService.getServiceUrl("/smm/user/uniqueNum");
        return template.postForObject(url, request, boolean.class);
    }
}
