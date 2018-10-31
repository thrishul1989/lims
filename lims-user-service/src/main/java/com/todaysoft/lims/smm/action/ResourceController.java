package com.todaysoft.lims.smm.action;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.lims.smm.entity.Resource;
import com.todaysoft.lims.smm.model.request.AuthorizedHierarchyMenuRequest;
import com.todaysoft.lims.smm.model.response.AuthorityAdapter;
import com.todaysoft.lims.smm.model.response.MenuAdapter;
import com.todaysoft.lims.smm.service.ICoreService;

@RestController
@RequestMapping("/smm/resource")
public class ResourceController
{
    @Autowired
    private ICoreService service;
    
    @RequestMapping("/menus")
    public List<MenuAdapter> menus(@RequestBody AuthorizedHierarchyMenuRequest request)
    {
     
        return service.getAuthorizedHierarchyMenus(request);
    }
    
    @RequestMapping("/authorities")
    public List<AuthorityAdapter> authorities()
    {
        return service.getHierarchyAuthorities();
    }
    
    @RequestMapping("/list")
    public Map<String,Set<String>> resourceList()
    {
        return service.resourceList();
    }
    
    @RequestMapping("/ResourceByAuthority")
    public List<Resource> ResourceByAuthority(@RequestBody Map set)
    {
        return service.ResourceByAuthority(new HashSet((List)set.get("authority")));
    }

    @RequestMapping("/authoritiesByRole")
    public List<AuthorityAdapter> authoritiesByRole(@RequestBody Map set)
    {
        return service.getHierarchyAuthoritiesByRole((List)set.get("roleSet"));
    }
    
    
    
    
}
