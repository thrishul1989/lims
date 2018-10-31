package com.todaysoft.lims.smm.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Where;

import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_ROLE")
public class Role extends UuidKeyEntity
{
    private static final long serialVersionUID = -7319334094141326212L;
    
    private String name;
    
    private boolean deleted;
    
    private Date createTime;
    
    private Date deleteTime;
    
    private Set<User> users;
    
    private Set<Authority> authorities;
    
    @Column(name = "NAME")
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    @Column(name = "DEL_FLAG")
    public boolean isDeleted()
    {
        return deleted;
    }
    
    public void setDeleted(boolean deleted)
    {
        this.deleted = deleted;
    }
    
    @Where(clause="DEL_FLAG=0")
    @ManyToMany(targetEntity = User.class, cascade = {CascadeType.MERGE})
    @JoinTable(name = "LIMS_USER_ROLE", joinColumns = {@JoinColumn(name = "ROLE_ID")}, inverseJoinColumns = {@JoinColumn(name = "USER_ID")})
    public Set<User> getUsers()
    {
        return users;
    }
    
    public void setUsers(Set<User> users)
    {
        this.users = users;
    }
    
    @ManyToMany(targetEntity = Authority.class, cascade = {CascadeType.MERGE})
    @JoinTable(name = "LIMS_ROLE_AUTHORITY", joinColumns = {@JoinColumn(name = "ROLE_ID")}, inverseJoinColumns = {@JoinColumn(name = "AUTHORITY_ID")})
    public Set<Authority> getAuthorities()
    {
        return authorities;
    }
    
    public void setAuthorities(Set<Authority> authorities)
    {
        this.authorities = authorities;
    }
    
    @Column(name = "CREATE_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getCreateTime()
    {
        return createTime;
    }
    
    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }
    
    @Column(name = "DELETE_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getDeleteTime()
    {
        return deleteTime;
    }
    
    public void setDeleteTime(Date deleteTime)
    {
        this.deleteTime = deleteTime;
    }
}
