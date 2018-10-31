package com.todaysoft.lims.smm.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "LIMS_USER_TOKEN")
public class UserToken implements Serializable
{
    private static final long serialVersionUID = -5568593995466976532L;
    
    private String userId;
    
    private String token;
    
    private Date createTime;
    
    private Date expireTime;
    
    @Id
    @Column(name = "USER_ID")
    public String getUserId()
    {
        return userId;
    }
    
    public void setUserId(String userId)
    {
        this.userId = userId;
    }
    
    @Column(name = "TOKEN")
    public String getToken()
    {
        return token;
    }
    
    public void setToken(String token)
    {
        this.token = token;
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
    
    @Column(name = "EXPIRE_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getExpireTime()
    {
        return expireTime;
    }
    
    public void setExpireTime(Date expireTime)
    {
        this.expireTime = expireTime;
    }
}
