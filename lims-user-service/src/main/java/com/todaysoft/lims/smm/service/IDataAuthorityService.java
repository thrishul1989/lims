package com.todaysoft.lims.smm.service;

import java.util.List;
import java.util.Map;

import com.todaysoft.lims.base.RecordFilter;
import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.smm.dao.searcher.DataAuthorityRoleSearcher;
import com.todaysoft.lims.smm.dao.searcher.DataAuthoritySearcher;
import com.todaysoft.lims.smm.dao.searcher.DataAuthorityUserSearcher;
import com.todaysoft.lims.smm.entity.DataAuthority;
import com.todaysoft.lims.smm.entity.DataAuthorityRole;
import com.todaysoft.lims.smm.entity.DataAuthorityUser;

public interface IDataAuthorityService
{
    Pagination<DataAuthority> paging(DataAuthoritySearcher request);
    
    List<DataAuthority> list(DataAuthoritySearcher request);
    
    Pagination<DataAuthorityRole> dataAuthorityRolePaging(DataAuthorityRoleSearcher request);
    
    List<DataAuthorityRole> dataAuthorityRoleByRoleId(DataAuthorityRoleSearcher request);
    
    Pagination<DataAuthorityUser> dataAuthorityUserPaging(DataAuthorityUserSearcher request);
    
    List<DataAuthorityUser> dataAuthorityUserByUserId(DataAuthorityUserSearcher request);
    
    Integer createRoleDataAuthority(DataAuthorityRole request);
    
    Integer createUserDataAuthority(DataAuthorityUser request);
    
    Integer deleteRoleAuthority(DataAuthorityRole request);
    
    Integer deleteUserAuthority(DataAuthorityUser request);
    
    List<String> getSubordinates(String leaders);
    
    Map<String, RecordFilter> getRecordFilters(String userId);
}
