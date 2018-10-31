package com.todaysoft.lims.sample.model;

import com.todaysoft.lims.persist.UuidKeyEntity;
import com.todaysoft.lims.sample.entity.UserArchive;

public class UserDetailsModel extends UuidKeyEntity
{
    
    private String username;
    
    private UserArchive archive;
    
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
}
