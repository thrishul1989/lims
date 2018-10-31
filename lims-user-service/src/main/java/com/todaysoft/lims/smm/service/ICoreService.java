package com.todaysoft.lims.smm.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.todaysoft.lims.smm.entity.Resource;
import com.todaysoft.lims.smm.model.request.AuthorizedHierarchyMenuRequest;
import com.todaysoft.lims.smm.model.response.AuthorityAdapter;
import com.todaysoft.lims.smm.model.response.MenuAdapter;

public interface ICoreService
{
    List<AuthorityAdapter> getHierarchyAuthorities();
    
    List<MenuAdapter> getAuthorizedHierarchyMenus(AuthorizedHierarchyMenuRequest request);
    
    Map<String,Set<String>> resourceList();
    
    List<Resource> ResourceByAuthority(Set<String> set);

    List<AuthorityAdapter> getHierarchyAuthoritiesByRole(List<String> roleIds);
}
