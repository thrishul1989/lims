package com.todaysoft.lims.system.modules.bsm.service.request;

import java.util.Date;

import com.todaysoft.lims.system.model.vo.BusinessInfo;
import com.todaysoft.lims.system.model.vo.Customer;

public class UserFeedbackListRequest
{
    private String id;
    
    private String userId;
    
    private Integer userType;
    
    private String name;
    
    private String feedbackOption;
    
    private Date feedStartbackDate;
    
    private Date feedEndbackDate;
    
    private String position;
    
    private Date feedbackDate;
    
    private Customer customer;
    
    private BusinessInfo businessInfo;
    
    private Integer pageNo;
    
    private Integer pageSize;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getUserId()
    {
        return userId;
    }
    
    public void setUserId(String userId)
    {
        this.userId = userId;
    }
    
    public Integer getUserType()
    {
        return userType;
    }
    
    public void setUserType(Integer userType)
    {
        this.userType = userType;
    }
    
    public String getFeedbackOption()
    {
        return feedbackOption;
    }
    
    public void setFeedbackOption(String feedbackOption)
    {
        this.feedbackOption = feedbackOption;
    }
    
    public Date getFeedbackDate()
    {
        return feedbackDate;
    }
    
    public void setFeedbackDate(Date feedbackDate)
    {
        this.feedbackDate = feedbackDate;
    }
    
    public Customer getCustomer()
    {
        return customer;
    }
    
    public void setCustomer(Customer customer)
    {
        this.customer = customer;
    }
    
    public BusinessInfo getBusinessInfo()
    {
        return businessInfo;
    }
    
    public void setBusinessInfo(BusinessInfo businessInfo)
    {
        this.businessInfo = businessInfo;
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
    
    public String getPosition()
    {
        return position;
    }
    
    public void setPosition(String position)
    {
        this.position = position;
    }
    
}
