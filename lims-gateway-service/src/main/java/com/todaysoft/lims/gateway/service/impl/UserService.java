package com.todaysoft.lims.gateway.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todaysoft.lims.gateway.model.UserDetailsModel;
import com.todaysoft.lims.gateway.service.IUserService;
import com.todaysoft.lims.gateway.service.adapter.UserAdapter;

@Service
public class UserService implements IUserService
{
    @Autowired
    private UserAdapter adapter;
    
    @Override
    public List<UserDetailsModel> getContactUsers(List<Integer> userIds)
    {
        return adapter.getContactUsers(userIds);
    }
}
