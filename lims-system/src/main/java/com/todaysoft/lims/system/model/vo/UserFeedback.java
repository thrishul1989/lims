package com.todaysoft.lims.system.model.vo;

import java.util.Date;

public class UserFeedback
{
    
    private String id;
    
    private String userId;
    
    private Integer userType;
    
    private String feedbackOption;
    
    private Date feedbackDate;
    
    private Customer customer;
    
    private BusinessInfo businessInfo;
    
    private String position;
    
    private String name;
    
    private String province;
    
    private String city;
    
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
    
    public String getPosition()
    {
        return position;
    }
    
    public void setPosition(String position)
    {
        this.position = position;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getProvince()
    {
        return province;
    }
    
    public void setProvince(String province)
    {
        this.province = province;
    }
    
    public String getCity()
    {
        return city;
    }
    
    public void setCity(String city)
    {
        this.city = city;
    }
    
}
