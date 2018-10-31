package com.todaysoft.lims.sample.model;

import java.util.List;

import com.todaysoft.lims.persist.UuidKeyEntity;
import com.todaysoft.lims.sample.model.enums.UserState;

public class User extends UuidKeyEntity
{
    private static final long serialVersionUID = 7665484135147093640L;
    
    private String username;
    
    private String password;
    
    private UserState state;
    
    private UserArchive archive;
    
    private List<UserRole> roles;
    
    public String getUsername()
    {
        return username;
    }
    
    public void setUsername(String username)
    {
        this.username = username;
    }
    
    public String getPassword()
    {
        return password;
    }
    
    public void setPassword(String password)
    {
        this.password = password;
    }
    
    public UserState getState()
    {
        return state;
    }
    
    public void setState(UserState state)
    {
        this.state = state;
    }
    
    public UserArchive getArchive()
    {
        return archive;
    }
    
    public void setArchive(UserArchive archive)
    {
        this.archive = archive;
    }
    
    public List<UserRole> getRoles()
    {
        return roles;
    }
    
    public void setRoles(List<UserRole> roles)
    {
        this.roles = roles;
    }
}
