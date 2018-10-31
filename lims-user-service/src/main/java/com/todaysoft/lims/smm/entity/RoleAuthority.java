package com.todaysoft.lims.smm.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

@Entity
@Table(name = "LIMS_ROLE_AUTHORITY")
@IdClass(RoleAuthority.class)
public class RoleAuthority implements Serializable
{
    private static final long serialVersionUID = 3343844099886992264L;
    
    private String role;
    
    private String authority;
    
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
    
    @Id
    @Column(name = "AUTHORITY_ID")
    public String getAuthority()
    {
        return authority;
    }
    
    public void setAuthority(String authority)
    {
        this.authority = authority;
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
