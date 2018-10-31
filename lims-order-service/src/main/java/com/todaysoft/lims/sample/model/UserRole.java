package com.todaysoft.lims.sample.model;

import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;




public class UserRole implements Serializable
{
    private static final long serialVersionUID = -1876281556189900280L;
    
    private Integer user;
    
    private String role;
    
    public Integer getUser()
    {
        return user;
    }
    
    public void setUser(Integer user)
    {
        this.user = user;
    }
    
    public String getRole()
    {
        return role;
    }
    
    public void setRole(String role)
    {
        this.role = role;
    }
    
    @Override
    public boolean equals(Object o)
    {
        return EqualsBuilder.reflectionEquals(this, o);
    }
    
    @Override
    public int hashCode()
    {
        return HashCodeBuilder.reflectionHashCode(this);
    }
}
