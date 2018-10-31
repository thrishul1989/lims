package com.todaysoft.lims.smm.service;

import java.util.List;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.smm.dao.searcher.RoleSearcher;
import com.todaysoft.lims.smm.entity.Role;
import com.todaysoft.lims.smm.entity.RoleTemplate;
import com.todaysoft.lims.smm.request.RoleCreateRequest;
import com.todaysoft.lims.smm.request.RoleListRequest;
import com.todaysoft.lims.smm.request.RoleModifyRequest;
import com.todaysoft.lims.smm.response.RoleDetailsModel;
import com.todaysoft.lims.smm.response.RoleSimpleModel;
import com.todaysoft.lims.smm.response.UserSimpleModel;

public interface IRoleService
{
    Pagination<RoleSimpleModel> paging(RoleListRequest request);
    
    List<RoleSimpleModel> list();
    
    RoleDetailsModel get(String id);
    
    String create(RoleCreateRequest request);
    
    void modify(RoleModifyRequest request);
    
    void delete(String id);
    
    List<RoleTemplate> roleSelect(RoleListRequest request);

    List<UserSimpleModel> getUserListByRole(String id);

    RoleSimpleModel getRoleByName(RoleSearcher searcher);

    boolean validate(RoleSearcher request);
}
