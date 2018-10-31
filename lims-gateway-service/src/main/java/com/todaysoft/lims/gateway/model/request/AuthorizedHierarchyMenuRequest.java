package com.todaysoft.lims.gateway.model.request;

import java.util.Set;

public class AuthorizedHierarchyMenuRequest
{
    private Set<String> authorities;
    
    public Set<String> getAuthorities()
    {
        return authorities;
    }
    
    public void setAuthorities(Set<String> authorities)
    {
        this.authorities = authorities;
    }
}
