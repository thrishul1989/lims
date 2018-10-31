package com.todaysoft.lims.smm.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.smm.entity.Authority;
import com.todaysoft.lims.smm.entity.AuthorityResource;
import com.todaysoft.lims.smm.entity.Menu;
import com.todaysoft.lims.smm.entity.Resource;
import com.todaysoft.lims.smm.model.request.AuthorizedHierarchyMenuRequest;
import com.todaysoft.lims.smm.model.response.AuthorityAdapter;
import com.todaysoft.lims.smm.model.response.MenuAdapter;
import com.todaysoft.lims.smm.response.RoleDetailsModel;
import com.todaysoft.lims.smm.response.UserDetailsModel;
import com.todaysoft.lims.smm.service.ICoreService;
import com.todaysoft.lims.smm.service.IRoleService;
import com.todaysoft.lims.smm.service.IUserService;
import com.todaysoft.lims.utils.Collections3;

@Service
public class CoreService implements ICoreService
{
    @Autowired
    private BaseDaoSupport baseDaoSupport;
    
    @Override
    public List<AuthorityAdapter> getHierarchyAuthorities()
    {
        String hql = "FROM Authority a WHERE a.parent IS NULL";
        List<Authority> records = baseDaoSupport.find(Authority.class, hql);
        return adapterAuthorities(records);
    }

    @Override
    public List<AuthorityAdapter> getHierarchyAuthoritiesByRole(List<String> roleIds) {

        NamedQueryer queryer = new NamedQueryer();
        String hql = " FROM Authority a WHERE EXISTS(SELECT ra.authority FROM RoleAuthority ra WHERE a.id=ra.authority AND ra.role in (:list))";
        queryer.setHql(hql);
        queryer.setNames(Arrays.asList("list"));
        queryer.setValues(Arrays.asList(roleIds));
        List<String> ids = Lists.newArrayList();
        List<Authority> results = Lists.newArrayList();
        List<Authority> records = baseDaoSupport.find(queryer, Authority.class);
        for(Authority authority:records)
        {
            Authority temp=null;
            if(null==authority.getParent())
            {
                temp = authority;
            }else{
                temp = getParentId(authority.getParent());
            }
            if(!ids.contains(temp.getId()))
            {
                ids.add(temp.getId());
                results.add(temp);
            }
        }
        return adapterAuthorities(results);
    }

    public Authority getParentId(Authority authority)
    {
        if(null == authority.getParent())
        {
           return authority;
        }
        return getParentId(authority.getParent());

    }

    @Override
    public List<MenuAdapter> getAuthorizedHierarchyMenus(AuthorizedHierarchyMenuRequest request)
    {
        Set<String> authorities = request.getAuthorities();
        //过滤权限菜单
        
        if (null == authorities || authorities.isEmpty())
        {
            return Collections.emptyList();
        }
        NamedQueryer queryer = new NamedQueryer();
        
        String hql = "FROM Menu m WHERE m.parent is null order by m.sort";
        queryer.setHql(hql);
        
        List<Menu> records = baseDaoSupport.find(queryer, Menu.class);
        
        return adapterMenus(records);
    }
    
    protected List<AuthorityAdapter> adapterAuthorities(List<Authority> records)
    {
        if (null == records || records.isEmpty())
        {
            return Collections.emptyList();
        }
        
        AuthorityAdapter authority;
        List<AuthorityAdapter> authorities = new ArrayList<AuthorityAdapter>();
        
        for (Authority record : records)
        {
            authority = new AuthorityAdapter(record);
            authorities.add(authority);
        }
        
        return authorities;
    }
    
    protected List<MenuAdapter> adapterMenus(List<Menu> records)
    {
        if (null == records || records.isEmpty())
        {
            return Collections.emptyList();
        }
        
        MenuAdapter menu;
        List<MenuAdapter> menus = new ArrayList<MenuAdapter>();
        
        for (Menu record : records)
        {
            menu = new MenuAdapter(record);
            menus.add(menu);
        }
        
        return menus;
    }
    
    @Override
    public Map<String, Set<String>> resourceList()
    {
        Map<String, Set<String>> map = new HashMap<String, Set<String>>();
        // TODO Auto-generated method stub
        List<Resource> resourceList = baseDaoSupport.find(Resource.class);
        for (Resource resource : resourceList)
        {
            List<AuthorityResource> arList =
                baseDaoSupport.find(AuthorityResource.class, "from AuthorityResource a " + "where a.resourceId='" + resource.getId() + "'");
            Set<String> set = new HashSet<String>();
            for (AuthorityResource ar : arList)
            {
                set.add(ar.getAuthorityId());
            }
            map.put(resource.getUri(), set);
            
        }
        return map;
        
    }
    
    @Override
    public List<Resource> ResourceByAuthority(Set<String> set)
    {
        NamedQueryer queryer = new NamedQueryer();
        
        String hql = " FROM Resource r WHERE r.id in (select ar.resourceId from AuthorityResource ar where ar.authorityId in (:list) )";
        queryer.setHql(hql);
        queryer.setNames(Arrays.asList("list"));
        queryer.setValues(Arrays.asList(set));
        List<Resource> list = baseDaoSupport.find(queryer, Resource.class);
        return list;
        
    }
}
