package com.todaysoft.lims.system.modules.smm.service;

import java.util.List;

import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.modules.smm.model.UserGroup;
import com.todaysoft.lims.system.modules.smm.service.request.UserGroupCreateRequest;
import com.todaysoft.lims.system.modules.smm.service.request.UserGroupModifyRequest;
import com.todaysoft.lims.system.modules.smm.service.request.UserGroupPagingRequest;

public interface IUserGroupService
{
    Pagination<UserGroup> paging(UserGroupPagingRequest request, int pageNo, int pageSize);
    
    UserGroup getById(String id);
    
    void create(UserGroupCreateRequest request);
    
    void modify(UserGroupModifyRequest request);
    
    boolean delete(String id);
    
    List<UserGroup> list(UserGroupPagingRequest request);
    
    boolean validate(UserGroupCreateRequest request);
}
