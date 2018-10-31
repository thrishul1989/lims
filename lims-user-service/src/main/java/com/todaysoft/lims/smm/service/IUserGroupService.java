package com.todaysoft.lims.smm.service;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.smm.entity.UserGroup;
import com.todaysoft.lims.smm.request.UserGroupCreateRequest;
import com.todaysoft.lims.smm.request.UserGroupModifyRequest;
import com.todaysoft.lims.smm.request.UserGroupPagingRequest;

public interface IUserGroupService
{
    Pagination<UserGroup> paging(UserGroupPagingRequest request);
    
    void create(UserGroupCreateRequest request);
    
    void modify(UserGroupModifyRequest request);
    
    boolean delete(String id);
    
    UserGroup getById(String id);
    
    boolean getByName(String name);
    
}
