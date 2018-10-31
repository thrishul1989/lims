package com.todaysoft.lims.smm.response;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.CollectionUtils;

import com.todaysoft.lims.smm.entity.Authority;
import com.todaysoft.lims.smm.entity.Role;

public class RoleDetailsModel
{
    private String id;
    
    private String name;
    
    private List<String> authorities;
    
    public RoleDetailsModel()
    {
        
    }
    
    public RoleDetailsModel(Role role)
    {
        this.id = role.getId();
        this.name = role.getName();
        
        if (!CollectionUtils.isEmpty(role.getAuthorities()))
        {
            this.authorities = new ArrayList<String>();
            
            for (Authority authority : role.getAuthorities())
            {
                this.authorities.add(authority.getId());
            }
        }
    }
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public List<String> getAuthorities()
    {
        return authorities;
    }
    
    public void setAuthorities(List<String> authorities)
    {
        this.authorities = authorities;
    }
}
