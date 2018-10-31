package com.todaysoft.lims.biology.adapter;


import com.todaysoft.lims.biology.model.UserMinimalModel;

public interface IUserAdapter{

    UserMinimalModel getLoginUser(final String token);
}
