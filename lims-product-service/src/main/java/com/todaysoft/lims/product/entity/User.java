package com.todaysoft.lims.product.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_USER")
public class User extends UuidKeyEntity
{
    
    /**
     * 
     */
    private static final long serialVersionUID = -6788470776945874563L;
    
    private String username;
    
    private String password;
    
    private UserState state;
    
    private UserArchive archive;
    
    private List<UserRole> roles;
    
    private Integer delFlag;
    
    @Column(name = "DEL_FLAG", columnDefinition = "tinyint default 0")
    public Integer getDelFlag()
    {
        return delFlag;
    }
    
    public void setDelFlag(Integer delFlag)
    {
        this.delFlag = delFlag;
    }
    
    private List<ProductPrincipal> principalList;
    
    @OneToMany(mappedBy = "principal", cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY)
    @Fetch(FetchMode.SUBSELECT)
    @NotFound(action = NotFoundAction.IGNORE)
    @JsonIgnore
    public List<ProductPrincipal> getPrincipalList()
    {
        return principalList;
    }
    
    public void setPrincipalList(List<ProductPrincipal> principalList)
    {
        this.principalList = principalList;
    }
    
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
    
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_ID")
    public List<UserRole> getRoles()
    {
        return roles;
    }
    
    public void setRoles(List<UserRole> roles)
    {
        this.roles = roles;
    }
}
