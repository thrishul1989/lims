package com.todaysoft.lims.system.service.impl;

import java.util.*;

import com.google.common.collect.Lists;
import com.todaysoft.lims.system.model.vo.*;
import com.todaysoft.lims.system.modules.smm.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.todaysoft.lims.persist.DataAuthoritySearcher;
import com.todaysoft.lims.system.modules.smm.service.IDataAuthorityService;
import com.todaysoft.lims.system.modules.smm.service.IRoleService;
import com.todaysoft.lims.system.service.IDepartmentService;
import com.todaysoft.lims.system.service.IResourceService;
import com.todaysoft.lims.system.service.IUserService;
import com.todaysoft.lims.system.service.security.AccountDetails;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.StringUtils;

@Service
public class ResourceService extends RestService implements IResourceService
{
    private Map<String, Set<String>> resourceAuthorizes = new HashMap<String, Set<String>>();
    
    @Autowired
    private IUserService userService;
    
    @Autowired
    private IRoleService roleService;
    
    @Autowired
    private IDataAuthorityService dataAuthorityService;
    
    @Autowired
    private IDepartmentService departmentService;
    
    @Override
    public List<Menu> getAuthorizedHierarchyMenus()
    {
        User user = userService.getUserByToken();
        
        // TODO:获取用户权限集合
        //获取用户角色
        UserDetailsModel userModel = userService.getUserByID(user.getId());
        Set<String> auSet = new HashSet<String>();
        Map req = new HashMap<>();
        if (Collections3.isNotEmpty(userModel.getRoles()))
        {
            List<String> roles = userModel.getRoles();
            //查询每个角色的权限
            
            for (String role : roles)
            {
                RoleDetailsModel roleModel = roleService.get(role);
                if (Collections3.isNotEmpty(roleModel.getAuthorities()))
                {
                    for (String authority : roleModel.getAuthorities())
                    {
                        auSet.add(authority);
                        
                    }
                }
                
            }
            req.put("authorities", auSet);
        }
        
        String url = GatewayService.getServiceUrl("/smm/resource/menus");
        
        ResponseEntity<List<Menu>> exchange = template.exchange(url, HttpMethod.POST, new HttpEntity<Map>(req), new ParameterizedTypeReference<List<Menu>>()
        {
        });
        
        List<Menu> menus = exchange.getBody();
        
        if (CollectionUtils.isEmpty(menus))
        {
            return Collections.emptyList();
        }
        //获取用户拥有的resource
        Map requMap = new HashMap<>();
        requMap.put("authority", auSet);
        String url2 = GatewayService.getServiceUrl("/smm/resource/ResourceByAuthority");
        ResponseEntity<List<Resource>> response =
            template.exchange(url2, HttpMethod.POST, new HttpEntity<Map>(requMap), new ParameterizedTypeReference<List<Resource>>()
            {
            });
        //过滤菜单
        List<Resource> resourceList = response.getBody();
        Set<String> grantSource = new HashSet<String>();
        for (Resource re : resourceList)
        {
            grantSource.add(re.getUri());
        }
        filterMenus(menus, grantSource);
        //再次过滤最外层节点
        Iterator<Menu> it = menus.iterator();
        while (it.hasNext())
        {
            Menu oneMenu = it.next();
            
            //再次过滤二级菜单，没有三级菜单情况下隐藏二级菜单
            Iterator<Menu> itt = oneMenu.getSubmenus().iterator();
            while (itt.hasNext())
            {
                Menu secondMenu = itt.next();
                if (Collections3.isEmpty(secondMenu.getSubmenus()) && StringUtils.isEmpty(secondMenu.getUri()))
                {
                    itt.remove();
                    
                }
                //再次过滤二级菜单，没有三级菜单情况下隐藏二级菜单
                
            }
            if (Collections3.isEmpty(oneMenu.getSubmenus()))
            {
                it.remove();
                
            }
            
        }
        return menus;
    }
    
    @Override
    public List<TreeNode> getHierarchyAuthorities()
    {
        String url = GatewayService.getServiceUrl("/smm/resource/authorities");
        
        ResponseEntity<List<Authority>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<Object>(new HashMap<String, Object>()), new ParameterizedTypeReference<List<Authority>>()
            {
            });
        
        List<Authority> authorities = exchange.getBody();
        
        if (null == authorities || authorities.isEmpty())
        {
            return Collections.emptyList();
        }
        
        List<TreeNode> nodes = new ArrayList<TreeNode>();
        
        for (Authority authority : authorities)
        {
            nodes.add(asTreeNode(authority));
        }
        
        return nodes;
    }

    @Override
    public List<TreeNode> getHierarchyAuthoritiesByRole(List<String> roleIds) {
        Set<String> set = new HashSet<>(roleIds);
        Map<String, Object> req = new HashMap<String, Object>();
        req.put("roleSet",set);
        String url = GatewayService.getServiceUrl("/smm/resource/authoritiesByRole");

        ResponseEntity<List<Authority>> exchange =
                template.exchange(url, HttpMethod.POST, new HttpEntity<Map>(req), new ParameterizedTypeReference<List<Authority>>()
                {
                });

        List<Authority> authorities = exchange.getBody();

        if (null == authorities || authorities.isEmpty())
        {
            return Collections.emptyList();
        }

        List<TreeNode> nodes = new ArrayList<TreeNode>();

        for (Authority authority : authorities)
        {
            nodes.add(asTreeNode(authority));
        }

        for(TreeNode node:nodes)
        {
            setState(node);
        }

        return nodes;
    }

    public void setState(TreeNode node)
    {
        TreeNodeState state = new TreeNodeState();
        node.setState(state);
        if(Collections3.isNotEmpty(node.getChildren()))
        {
            for(TreeNode nodeTemp:node.getChildren())
            {
                setState(nodeTemp);
            }
        }
    }

    @Override
    public boolean isGrantedResource(String resource)
    
    {
        
        if (resourceAuthorizes.isEmpty())
        {
            //初始化权限资源
            initResourceAuthorizes();
            
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (null == authentication)
        {
            return false;
        }
        
        ResourceGrantedAuthorityConfig config = getGrantedAuthorityConfig(resource);
        
        if (config.isIgnore())
        {
            return true;
        }
        
        Set<String> grantedAuthorities = config.getAuthorities();
        
        if (grantedAuthorities.isEmpty())
        {
            return false;
        }
        
        Object principal = authentication.getPrincipal();
        AccountDetails account = (AccountDetails)principal;
        
        Set<String> assignedAuthorities = account.getSimpleAuthorities();
        
        for (String grantedAuthority : grantedAuthorities)
        {
            if (assignedAuthorities.contains(grantedAuthority))
            {
                return true;
            }
        }
        
        return false;
    }
    
    @Override
    public ResourceGrantedAuthorityConfig getGrantedAuthorityConfig(String resource)
    {
        ResourceGrantedAuthorityConfig config = new ResourceGrantedAuthorityConfig();
        
        Set<String> authorities = resourceAuthorizes.get(resource);
        
        // 缓存中为NULL表示该资源不需要权限控制
        if (null == authorities)
        {
            config.setIgnore(true);
            return config;
        }
        
        config.setIgnore(false);
        config.setAuthorities(authorities);
        return config;
    }
    
    protected void initResourceAuthorizes()
    {
        // TODO：设置系统中所有资源的权限设置(LIMS_RESOURCE\LIMS_AUTHORITY\LIMS_AUTHORITY_RESOURCE)
        
        String url = GatewayService.getServiceUrl("/smm/resource/list");
        ResponseEntity<Map<String, Set<String>>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<Resource>(new Resource()), new ParameterizedTypeReference<Map<String, Set<String>>>()
            {
            });
        resourceAuthorizes = exchange.getBody();
        
    }
    
    private TreeNode asTreeNode(Authority authority)
    {
        TreeNode node = new TreeNode();
        node.setId(authority.getId());
        node.setText(authority.getName());
        
        if (authority.getSubauthorities() != null && !authority.getSubauthorities().isEmpty())
        {
            node.setChildren(new ArrayList<TreeNode>());
            
            for (Authority subauthority : authority.getSubauthorities())
            {
                node.getChildren().add(asTreeNode(subauthority));
            }
        }
        
        return node;
    }
    
    void filterMenus(List<Menu> dep, Set<String> list)
    {
        
        Iterator<Menu> it = dep.iterator();
        
        while (it.hasNext())
        {
            Menu menu = it.next();
            if (Collections3.isNotEmpty(menu.getSubmenus()))
            {
                filterMenus(menu.getSubmenus(), list);
            }
            
            //比对
            if (!list.contains(menu.getUri()) && StringUtils.isNotEmpty(menu.getUri()))
            {
                it.remove();
            }
            
        }
        
    }
    
}
