package com.todaysoft.lims.gateway.model;

import java.util.List;

public class UserAuthorizedDetails
{
    private String token;
    
    private String name;
    
    private String username;
    
    private List<String> resources;
    
    public String getToken()
    {
        return token;
    }
    
    public void setToken(String token)
    {
        this.token = token;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getUsername()
    {
        return username;
    }
    
    public void setUsername(String username)
    {
        this.username = username;
    }
    
    public List<String> getResources()
    {
        return resources;
    }
    
    public void setResources(List<String> resources)
    {
        this.resources = resources;
    }
}
