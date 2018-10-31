package com.todaysoft.lims.system.modules.smm.service.request;

public class UserPasswordResetRequest
{
    private String id;
    
    private String password;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getPassword()
    {
        return password;
    }
    
    public void setPassword(String password)
    {
        this.password = password;
    }
}
