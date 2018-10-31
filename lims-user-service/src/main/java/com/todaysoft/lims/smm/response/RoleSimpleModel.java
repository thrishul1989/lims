package com.todaysoft.lims.smm.response;

import com.todaysoft.lims.smm.entity.Role;

public class RoleSimpleModel
{
    private String id;
    
    private String name;
    
    private Integer grantUserCount;
    
    public RoleSimpleModel()
    {
        
    }
    
    public RoleSimpleModel(Role role)
    {
        this.id = role.getId();
        this.name = role.getName();
        this.grantUserCount = role.getUsers().size();
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
    
    public Integer getGrantUserCount()
    {
        return grantUserCount;
    }
    
    public void setGrantUserCount(Integer grantUserCount)
    {
        this.grantUserCount = grantUserCount;
    }
}
