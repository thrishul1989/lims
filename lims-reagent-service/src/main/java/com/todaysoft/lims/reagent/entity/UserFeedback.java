package com.todaysoft.lims.reagent.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "APP_USER_FEEDBACK")
public class UserFeedback extends UuidKeyEntity
{
    private static final long serialVersionUID = 1L;
    
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
    
    @Column(name = "USER_TYPE")
    public Integer getUserType()
    {
        return userType;
    }
    
    public void setUserType(Integer userType)
    {
        this.userType = userType;
    }
    
    @Column(name = "FEEDBACK_OPTION")
    public String getFeedbackOption()
    {
        return feedbackOption;
    }
    
    public void setFeedbackOption(String feedbackOption)
    {
        this.feedbackOption = feedbackOption;
    }
    
    @Column(name = "FEEDBACK_DATE")
    public Date getFeedbackDate()
    {
        return feedbackDate;
    }
    
    public void setFeedbackDate(Date feedbackDate)
    {
        this.feedbackDate = feedbackDate;
    }
    
    @Column(name = "USER_ID")
    public String getUserId()
    {
        return userId;
    }
    
    public void setUserId(String userId)
    {
        this.userId = userId;
    }
    
    @Transient
    public Customer getCustomer()
    {
        return customer;
    }
    
    public void setCustomer(Customer customer)
    {
        this.customer = customer;
    }
    
    @Transient
    public BusinessInfo getBusinessInfo()
    {
        return businessInfo;
    }
    
    public void setBusinessInfo(BusinessInfo businessInfo)
    {
        this.businessInfo = businessInfo;
    }
    
    @Transient
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    @Transient
    public String getProvince()
    {
        return province;
    }
    
    public void setProvince(String province)
    {
        this.province = province;
    }
    
    @Transient
    public String getCity()
    {
        return city;
    }
    
    public void setCity(String city)
    {
        this.city = city;
    }
    
    @Transient
    public String getPosition()
    {
        return position;
    }
    
    public void setPosition(String position)
    {
        this.position = position;
    }
    
}
