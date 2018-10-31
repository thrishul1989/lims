package com.todaysoft.lims.system.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.model.vo.User;
import com.todaysoft.lims.system.model.vo.UserInform;
import com.todaysoft.lims.system.modules.smm.model.UserDetailsModel;
import com.todaysoft.lims.system.modules.smm.model.UserSearcher;
import com.todaysoft.lims.system.modules.smm.service.request.UserListRequest;
import com.todaysoft.lims.system.service.IUserService;

@Service("undesiredUserService")
public class UserService extends RestService implements IUserService
{
    @Override
    public UserDetailsModel getUserByID(String id)
    {
        return getById("/smm/user/{id}", UserDetailsModel.class, id);
    }
    
    @Override
    public Pagination<UserDetailsModel> selectUser(UserListRequest key, int pageNo, int pageSize)
    {
        UserListRequest request = new UserListRequest();
        BeanUtils.copyProperties(key, request);
        request.setPageNo(pageNo);
        request.setPageSize(pageSize);
        
        return exchange("/smm/user/selectUser", request, new ParameterizedTypeReference<Pagination<UserDetailsModel>>()
        {
        });
    }
    
    @Override
    public List<UserDetailsModel> getUserDetailsModelList(UserListRequest request)
    {
        return exchange("/smm/user/list", request, new ParameterizedTypeReference<List<UserDetailsModel>>()
        {
        });
    }
    
    @Override
    public User getUserByToken()
    {
        return get("/smm/authorize/user", User.class);
    }
    
    @Override
    public Pagination<UserInform> informPaging(UserSearcher searcher, int pageNo, int pageSize)
    {
        UserListRequest request = new UserListRequest(); //传用户id
        BeanUtils.copyProperties(searcher, request);
        request.setPageNo(pageNo);
        request.setPageSize(pageSize);
        return page("/smm/user/informPaging", request);
    }
}
