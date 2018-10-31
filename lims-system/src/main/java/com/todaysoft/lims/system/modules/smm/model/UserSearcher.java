package com.todaysoft.lims.system.modules.smm.model;

public class UserSearcher
{
    private String username;
    
    private String name;
    
    private String phone;
    
    private Integer state;
    
    private String roleId;
    
    private UserArchive archive;
    
    private UserState status;
    
    private String id;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public UserState getStatus()
    {
        return status;
    }
    
    public void setStatus(UserState status)
    {
        this.status = status;
    }
    
    public String getRoleId()
    {
        return roleId;
    }
    
    public void setRoleId(String roleId)
    {
        this.roleId = roleId;
    }
    
    public UserArchive getArchive()
    {
        return archive;
    }
    
    public void setArchive(UserArchive archive)
    {
        this.archive = archive;
    }
    
    public String getUsername()
    {
        return username;
    }
    
    public void setUsername(String username)
    {
        this.username = username;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getPhone()
    {
        return phone;
    }
    
    public void setPhone(String phone)
    {
        this.phone = phone;
    }
    
    public Integer getState()
    {
        return state;
    }
    
    public void setState(Integer state)
    {
        this.state = state;
    }
    
    private String roleType;
    
    private String testingType;
    
    public String getRoleType()
    {
        return roleType;
    }
    
    public void setRoleType(String roleType)
    {
        this.roleType = roleType;
    }
    
    public String getTestingType()
    {
        return testingType;
    }
    
    public void setTestingType(String testingType)
    {
        this.testingType = testingType;
    }
    
}
