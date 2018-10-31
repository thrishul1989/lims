package com.todaysoft.lims.system.modules.smm.model;

import java.util.Set;

public class ResourceGrantedAuthorityConfig
{
    private boolean ignore;
    
    private Set<String> authorities;
    
    public boolean isIgnore()
    {
        return ignore;
    }
    
    public void setIgnore(boolean ignore)
    {
        this.ignore = ignore;
    }
    
    public Set<String> getAuthorities()
    {
        return authorities;
    }
    
    public void setAuthorities(Set<String> authorities)
    {
        this.authorities = authorities;
    }
}
