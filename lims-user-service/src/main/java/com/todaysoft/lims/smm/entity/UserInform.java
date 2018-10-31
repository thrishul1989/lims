package com.todaysoft.lims.smm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.todaysoft.lims.persist.AutoKeyEntity;

@Entity
@Table(name = "LIMS_USER_INFORM")
public class UserInform extends AutoKeyEntity
{
    private static final long serialVersionUID = 1L;
    
    private String title;
    
    private String content;
    
    private String state;
    
    private String infoTime;
    
    private String userId; //通知对象
    
    @Column(name = "TITLE")
    public String getTitle()
    {
        return title;
    }
    
    public void setTitle(String title)
    {
        this.title = title;
    }
    
    @Column(name = "CONTENT")
    public String getContent()
    {
        return content;
    }
    
    public void setContent(String content)
    {
        this.content = content;
    }
    
    @Column(name = "STATE")
    public String getState()
    {
        return state;
    }
    
    public void setState(String state)
    {
        this.state = state;
    }
    
    @Column(name = "INFOTIME")
    public String getInfoTime()
    {
        return infoTime;
    }
    
    public void setInfoTime(String infoTime)
    {
        this.infoTime = infoTime;
    }
    
    @Column(name = "USERID")
    public String getUserId()
    {
        return userId;
    }
    
    public void setUserId(String userId)
    {
        this.userId = userId;
    }
    
}
