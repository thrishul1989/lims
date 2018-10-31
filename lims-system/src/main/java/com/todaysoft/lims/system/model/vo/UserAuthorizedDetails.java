package com.todaysoft.lims.system.model.vo;

import java.util.Map;
import java.util.Set;

import com.todaysoft.lims.base.RecordFilter;

public class UserAuthorizedDetails
{
    private String userId;
    
    private String token;
    
    private String name;
    
    private String username;
    
    private String erroCode;
    
    private Map<String, RecordFilter> recordFilters;
    
    public String getErroCode()
    {
        return erroCode;
    }
    
    public void setErroCode(String erroCode)
    {
        this.erroCode = erroCode;
    }
    
    public String getUserId()
    {
        return userId;
    }
    
    public void setUserId(String userId)
    {
        this.userId = userId;
    }
    
    private Set<String> authorities;
    
    public String getToken()
    {
        return token;
    }
    
    public void setToken(String token)
    {
        this.token = token;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getUsername()
    {
        return username;
    }
    
    public void setUsername(String username)
    {
        this.username = username;
    }
    
    public Set<String> getAuthorities()
    {
        return authorities;
    }
    
    public void setAuthorities(Set<String> authorities)
    {
        this.authorities = authorities;
    }
    
    public Map<String, RecordFilter> getRecordFilters()
    {
        return recordFilters;
    }
    
    public void setRecordFilters(Map<String, RecordFilter> recordFilters)
    {
        this.recordFilters = recordFilters;
    }
}
