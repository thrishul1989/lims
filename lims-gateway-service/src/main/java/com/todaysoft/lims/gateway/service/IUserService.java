package com.todaysoft.lims.gateway.service;

import java.util.List;

import com.todaysoft.lims.gateway.model.UserDetailsModel;

public interface IUserService
{
    List<UserDetailsModel> getContactUsers(List<Integer> userIds);
}
