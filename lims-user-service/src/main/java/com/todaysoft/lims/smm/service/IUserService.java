package com.todaysoft.lims.smm.service;

import java.util.List;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.smm.entity.Customer;
import com.todaysoft.lims.smm.entity.User;
import com.todaysoft.lims.smm.request.UserCreateRequest;
import com.todaysoft.lims.smm.request.UserListRequest;
import com.todaysoft.lims.smm.request.UserModifyRequest;
import com.todaysoft.lims.smm.request.UserPasswordModifyRequest;
import com.todaysoft.lims.smm.request.UserPasswordResetRequest;
import com.todaysoft.lims.smm.response.UserDetailsModel;
import com.todaysoft.lims.smm.response.UserMinimalModel;
import com.todaysoft.lims.smm.response.UserSimpleModel;

public interface IUserService
{
    Pagination<UserSimpleModel> paging(UserListRequest request);
    
    List<UserSimpleModel> list(UserListRequest request);
    
    List<UserSimpleModel> noSalesmanList(UserListRequest request);
    
    boolean unique(String username);
    
    User getUserByUsername(String username);
    
    UserDetailsModel getUserByID(String id);
    
    UserMinimalModel getMinimalUserByID(String id);
    
    String create(UserCreateRequest request);
    
    void modify(UserModifyRequest request);
    
    Integer delete(String id);
    
    void enable(String id);
    
    void disable(String id);
    
    boolean validate(String id);
    
    void lock(String id);
    
    void resetPassword(UserPasswordResetRequest request);
    
    void modifyPassword(UserPasswordModifyRequest request);
    
    List<UserDetailsModel> getContactUsers(List<Integer> userIds);
    
    UserMinimalModel getByToken(String token);
    
    List<UserSimpleModel> userSelect(UserListRequest request);
    
    boolean validate(UserListRequest request);
    
    List<UserSimpleModel> userSelectForTest(UserListRequest request);
    
    Customer getCustomer(String id);
    
    boolean uniqueNum(String employeeNo, String id);
}
