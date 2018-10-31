package com.todaysoft.lims.smm.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.todaysoft.lims.persist.UuidKeyEntity;
import com.todaysoft.lims.smm.entity.enums.UserState;

@Entity
@Table(name = "LIMS_USER")
public class User extends UuidKeyEntity
{
    private static final long serialVersionUID = 7665484135147093640L;
    
    private String username;
    
    private String password;
    
    private UserState state;
    
    private boolean deleted;
    
    private UserArchive archive;
    
    private Set<Role> roles;
    
    @Column(name = "SALT")
    public String getSalt()
    {
        return salt;
    }
    
    public void setSalt(String salt)
    {
        this.salt = salt;
    }
    
    private Date createTime;
    
    private Date deleteTime;
    
    private String salt;
    
    @Column(name = "USERNAME")
    public String getUsername()
    {
        return username;
    }
    
    public void setUsername(String username)
    {
        this.username = username;
    }
    
    @Column(name = "PASSWORD")
    public String getPassword()
    {
        return password;
    }
    
    public void setPassword(String password)
    {
        this.password = password;
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
    
    @Column(name = "STATE")
    public UserState getState()
    {
        return state;
    }
    
    public void setState(UserState state)
    {
        this.state = state;
    }
    
    @JoinColumn(name = "ARCHIVE_ID")
    @OneToOne(targetEntity = UserArchive.class, fetch = FetchType.EAGER)
    @NotFound(action = NotFoundAction.IGNORE)
    public UserArchive getArchive()
    {
        return archive;
    }
    
    public void setArchive(UserArchive archive)
    {
        this.archive = archive;
    }
    
    @ManyToMany(targetEntity = Role.class, cascade = {CascadeType.MERGE})
    @JoinTable(name = "LIMS_USER_ROLE", joinColumns = {@JoinColumn(name = "USER_ID")}, inverseJoinColumns = {@JoinColumn(name = "ROLE_ID")})
    @JsonIgnore
    public Set<Role> getRoles()
    {
        return roles;
    }
    
    public void setRoles(Set<Role> roles)
    {
        this.roles = roles;
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
