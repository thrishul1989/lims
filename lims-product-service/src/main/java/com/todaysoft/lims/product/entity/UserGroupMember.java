package com.todaysoft.lims.product.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_USER_GROUP_MEMBER")
public class UserGroupMember extends UuidKeyEntity
{
    private static final long serialVersionUID = -457532996090754270L;
    
    private UserGroup userGroup;
    
    private User user;
    
    private String userName;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GROUP_ID")
    @JsonIgnore
    public UserGroup getUserGroup()
    {
        return userGroup;
    }
    
    public void setUserGroup(UserGroup userGroup)
    {
        this.userGroup = userGroup;
    }
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    @NotFound(action = NotFoundAction.IGNORE)
    public User getUser()
    {
        return user;
    }
    
    public void setUser(User user)
    {
        this.user = user;
    }
    
    @Column(name = "USER_NAME")
    public String getUserName()
    {
        return userName;
    }
    
    public void setUserName(String userName)
    {
        this.userName = userName;
    }
    
}
