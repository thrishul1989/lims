package com.todaysoft.lims.invoice.model;

public class UserModel
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
