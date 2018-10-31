package com.todaysoft.lims.system.service;

import java.util.List;

import com.todaysoft.lims.system.model.vo.Menu;
import com.todaysoft.lims.system.model.vo.TreeNode;
import com.todaysoft.lims.system.model.vo.User;
import com.todaysoft.lims.system.modules.smm.model.ResourceGrantedAuthorityConfig;

public interface IResourceService
{
    List<Menu> getAuthorizedHierarchyMenus();
    
    List<TreeNode> getHierarchyAuthorities();
    
    ResourceGrantedAuthorityConfig getGrantedAuthorityConfig(String resource);
    
    boolean isGrantedResource(String resource);

    List<TreeNode> getHierarchyAuthoritiesByRole(List<String> roleIds);
}
