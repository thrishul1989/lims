package com.todaysoft.lims.product.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;


@Entity
@Table(name = "LIMS_USER_ROLE")
@IdClass(UserRole.class)
public class UserRole implements Serializable
{
    private static final long serialVersionUID = -1876281556189900280L;
    
    private String user;
    
    private String role;
    
    @Id
    @Column(name = "USER_ID")
    public String getUser()
    {
        return user;
    }
    
    public void setUser(String user)
    {
        this.user = user;
    }
    
    @Id
    @Column(name = "ROLE_ID")
    public String getRole()
    {
        return role;
    }
    
    public void setRole(String role)
    {
        this.role = role;
    }
    
}
