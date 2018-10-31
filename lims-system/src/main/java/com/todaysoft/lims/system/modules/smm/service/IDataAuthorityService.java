package com.todaysoft.lims.system.modules.smm.service;

import java.util.List;

import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.modules.smm.model.DataAuthority;
import com.todaysoft.lims.system.modules.smm.model.DataAuthorityRole;
import com.todaysoft.lims.system.modules.smm.model.DataAuthorityUser;

public interface IDataAuthorityService
{
    
    Pagination<DataAuthority> paging(DataAuthority searcher, int pageNo, int pageSize);
    
    List<DataAuthority> list();
    
    Pagination<DataAuthorityRole> dataAuthorityRole(DataAuthorityRole searcher, int pageNo, int pageSize);
    
    List<DataAuthorityRole> dataAuthorityRoleByRoleId(DataAuthorityRole searcher);
    
    Pagination<DataAuthorityUser> dataAuthorityUser(DataAuthorityUser searcher, int pageNo, int pageSize);
    
    List<DataAuthorityUser> dataAuthorityUserByUserId(DataAuthorityUser searcher);
    
    Integer createRoleDataAuthority(DataAuthorityRole request);
    
    Integer createUserDataAuthority(DataAuthorityUser request);
    
    Integer deleteRoleAuthority(DataAuthorityRole request);
    
    Integer deleteUserAuthority(DataAuthorityUser request);
    
    List<String> getSubordinates(String leaders);
}
