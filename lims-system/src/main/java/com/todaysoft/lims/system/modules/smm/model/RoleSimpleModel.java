package com.todaysoft.lims.system.modules.smm.model;

public class RoleSimpleModel
{
    private String id;
    
    private String name;
    
    private Integer grantUserCount;
    
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
