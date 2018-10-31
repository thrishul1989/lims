package com.todaysoft.lims.system.model.vo;


import java.util.Set;

import java.io.Serializable;


import com.todaysoft.lims.system.model.vo.enums.UserState;
import com.todaysoft.lims.system.modules.smm.model.Role;
import com.todaysoft.lims.system.modules.smm.model.UserArchive;

public class User implements Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private String id;
    
    private String username;
    
    private UserState state;
    
    private UserArchive archive;
    
   private boolean deleted;
   private String password;
   
   private Set<Role> roles;
   
   
   
    
    public boolean isDeleted()
{
    return deleted;
}

public void setDeleted(boolean deleted)
{
    this.deleted = deleted;
}

public String getPassword()
{
    return password;
}

public void setPassword(String password)
{
    this.password = password;
}

public Set<Role> getRoles()
{
    return roles;
}

public void setRoles(Set<Role> roles)
{
    this.roles = roles;
}

  
    
    
    
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
    
    public UserArchive getArchive()
    {
        return archive;
    }
    
    public void setArchive(UserArchive archive)
    {
        this.archive = archive;
    }
}
