package com.todaysoft.lims.reagent.entity;

import java.util.List;

import com.todaysoft.lims.reagent.entity.enums.UserState;

public class UserDetailsModel
{
    private String id;
    
    private String username;
    
    private UserArchive archive;
    
    private UserState state;
    
    private List<String> roles;
    
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
    
    public UserArchive getArchive()
    {
        return archive;
    }
    
    public void setArchive(UserArchive archive)
    {
        this.archive = archive;
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
}
