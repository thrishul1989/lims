package com.todaysoft.lims.persist;

import java.util.Set;

public class DataAuthoritySearcher
{
    
    public Integer getConfig()
    {
        return config;
    }
    
    public void setConfig(Integer config)
    {
        this.config = config;
    }
    
    public Set<String> getDeptAndSons()
    {
        return deptAndSons;
    }
    
    public void setDeptAndSons(Set<String> deptAndSons)
    {
        this.deptAndSons = deptAndSons;
    }
    
    public Set<String> getDepts()
    {
        return depts;
    }
    
    public void setDepts(Set<String> depts)
    {
        this.depts = depts;
    }
    
    private Integer config;
    
    private Set<String> depts;
    
    private Set<String> deptAndSons;//当前用户所在机构及下属机构
    
    private Set<String> userAndSons;//当前用户及下属用户
    
    private String userId;
    
    public String getUserId()
    {
        return userId;
    }
    
    public void setUserId(String userId)
    {
        this.userId = userId;
    }
    
    public Set<String> getUserAndSons()
    {
        return userAndSons;
    }
    
    public void setUserAndSons(Set<String> userAndSons)
    {
        this.userAndSons = userAndSons;
    }
}
