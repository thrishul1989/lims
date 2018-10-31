package com.todaysoft.lims.persist;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name = "LIMS_USER")
public class UserConfig extends UuidKeyEntity
{
    private static final long serialVersionUID = 7665484135147093640L;
    
    private String username;
    
    private String password;
    
    private boolean deleted;
    
    private UserArchiveConfig archive;
    
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
    
   
    
    @JoinColumn(name = "ARCHIVE_ID")
    @OneToOne(targetEntity = UserArchiveConfig.class, fetch = FetchType.EAGER)
    @NotFound(action = NotFoundAction.IGNORE)
    public UserArchiveConfig getArchive()
    {
        return archive;
    }

    public void setArchive(UserArchiveConfig archive)
    {
        this.archive = archive;
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
