package com.todaysoft.lims.smm.request;

import java.util.List;

public class RoleCreateRequest
{
    private String name;
    
    private List<String> authorities;
    
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
