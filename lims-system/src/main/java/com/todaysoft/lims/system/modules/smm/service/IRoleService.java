package com.todaysoft.lims.system.modules.smm.service;

import java.util.List;

import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.modules.smm.model.*;
import com.todaysoft.lims.system.modules.smm.service.request.RoleCreateRequest;
import com.todaysoft.lims.system.modules.smm.service.request.RoleModifyRequest;

public interface IRoleService
{
    Pagination<RoleSimpleModel> paging(RoleSearcher searcher, int pageNo, int pageSize);
    
    List<RoleSimpleModel> list();
    
    RoleDetailsModel get(String id);
    
    void create(RoleCreateRequest request);
    
    void modify(RoleModifyRequest request);
    
    void delete(String id);
    
    List<RoleTemplate> roleSelect(RoleSearcher searcher);

    RoleSimpleModel getRoleByName(RoleSearcher searcher);

    List<UserSimpleModel> getUserListByRole(String id);

    boolean validate(RoleSearcher searcher);

}
