package com.todaysoft.lims.system.modules.bsm.model;

import java.util.Date;

public class UserFeedbackSearcher
{
    
    private Date feedStartbackDate;
    
    private Date feedEndbackDate;
    
    private String feedbackOption;
    
    private String name;
    
    private String position;
    
    private Integer pageNo;
    
    private Integer pageSize;
    
    public Date getFeedStartbackDate()
    {
        return feedStartbackDate;
    }
    
    public void setFeedStartbackDate(Date feedStartbackDate)
    {
        this.feedStartbackDate = feedStartbackDate;
    }
    
    public Date getFeedEndbackDate()
    {
        return feedEndbackDate;
    }
    
    public void setFeedEndbackDate(Date feedEndbackDate)
    {
        this.feedEndbackDate = feedEndbackDate;
    }
    
    public String getFeedbackOption()
    {
        return feedbackOption;
    }
    
    public void setFeedbackOption(String feedbackOption)
    {
        this.feedbackOption = feedbackOption;
    }
    
    public Integer getPageNo()
    {
        return pageNo;
    }
    
    public void setPageNo(Integer pageNo)
    {
        this.pageNo = pageNo;
    }
    
    public Integer getPageSize()
    {
        return pageSize;
    }
    
    public void setPageSize(Integer pageSize)
    {
        this.pageSize = pageSize;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getPosition()
    {
        return position;
    }
    
    public void setPosition(String position)
    {
        this.position = position;
    }
    
}
