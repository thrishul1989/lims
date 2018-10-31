package com.todaysoft.lims.system.modules.smm.service.request;

import java.util.List;

import com.todaysoft.lims.system.modules.smm.model.UserArchive;

public class UserModifyRequest
{
    private String id;
    
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
