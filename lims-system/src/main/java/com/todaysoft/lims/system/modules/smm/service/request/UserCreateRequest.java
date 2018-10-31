package com.todaysoft.lims.system.modules.smm.service.request;

import java.util.List;

import com.todaysoft.lims.system.modules.smm.model.UserArchive;

public class UserCreateRequest
{
    private String username;
    
    private String password;
    
    private List<String> roles;
    
    private UserArchive archive;
    
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
}
