package com.todaysoft.lims.system.modules.smm.model;

import java.util.List;

import com.todaysoft.lims.system.model.vo.enums.UserState;

public class UserDetailsModel
{
    private String id;
    
    private String username;
    
    private UserState state;
    
    private String name;
    
    private String userName_name;
    
    private List<String> roles;
    
    private UserArchive archive;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getUsername()
    {
        return username;
    }
    
    public void setUsername(String username)
    {
        this.username = username;
    }
    
    public UserState getState()
    {
        return state;
    }
    
    public void setState(UserState state)
    {
        this.state = state;
    }
    
    public List<String> getRoles()
    {
        return roles;
    }
    
    public void setRoles(List<String> roles)
    {
        this.roles = roles;
    }
    
    public UserArchive getArchive()
    {
        return archive;
    }
    
    public void setArchive(UserArchive archive)
    {
        this.archive = archive;
    }
    
    private List<RoleDetailsModel> roleModels;
    
    public List<RoleDetailsModel> getRoleModels()
    {
        return roleModels;
    }
    
    public void setRoleModels(List<RoleDetailsModel> roleModels)
    {
        this.roleModels = roleModels;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getUserName_name()
    {
        return userName_name;
    }
    
    public void setUserName_name(String userName_name)
    {
        this.userName_name = userName_name;
    }
    
}
