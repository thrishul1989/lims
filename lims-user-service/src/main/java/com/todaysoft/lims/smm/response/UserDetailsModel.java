package com.todaysoft.lims.smm.response;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.CollectionUtils;

import com.todaysoft.lims.smm.entity.Role;
import com.todaysoft.lims.smm.entity.User;
import com.todaysoft.lims.smm.entity.UserArchive;
import com.todaysoft.lims.smm.entity.enums.UserState;

public class UserDetailsModel
{
    private String id;
    
    private String username;
    
    private UserState state;
    
    private List<String> roles;
    
    private UserArchive archive;
    
    public UserDetailsModel(User user)
    {
        this.id = user.getId();
        this.username = user.getUsername();
        this.state = user.getState();
        this.archive = user.getArchive();
        
        if (!CollectionUtils.isEmpty(user.getRoles()))
        {
            this.roles = new ArrayList<String>();
            
            for (Role role : user.getRoles())
            {
                this.roles.add(role.getId());
            }
        }
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
