package com.todaysoft.lims.system.model.vo;

import java.util.Date;

public class Notice
{
    
    public Notice(String id, String title, String content, String state, Date noticeTime)
    {
        super();
        this.id = id;
        this.title = title;
        this.content = content;
        this.state = state;
        this.noticeTime = noticeTime;
    }
    
    private String id;
    
    private String title;
    
    private String content;
    
    private String state;
    
    private Date noticeTime;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getTitle()
    {
        return title;
    }
    
    public void setTitle(String title)
    {
        this.title = title;
    }
    
    public String getContent()
    {
        return content;
    }
    
    public void setContent(String content)
    {
        this.content = content;
    }
    
    public String getState()
    {
        return state;
    }
    
    public void setState(String state)
    {
        this.state = state;
    }
    
    public Date getNoticeTime()
    {
        return noticeTime;
    }
    
    public void setNoticeTime(Date noticeTime)
    {
        this.noticeTime = noticeTime;
    }
    
}
