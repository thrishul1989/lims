package com.todaysoft.lims.sample.entity.order;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "APP_SAMPLE_BACK_APPLY")
public class AppSampleBackApply extends UuidKeyEntity
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private String userId;
    
    private String createId;
    
    private String applyName;
    
    private Date applyDate;
    
    private String backType;
    
    private String backChannel;
    
    private String backAddress;
    
    private String receiveName;
    
    private String receivePhone;
    
    @Column(name = "USER_ID")
    public String getUserId()
    {
        return userId;
    }
    
    public void setUserId(String userId)
    {
        this.userId = userId;
    }
    
    @Column(name = "CREATE_ID")
    public String getCreateId()
    {
        return createId;
    }
    
    public void setCreateId(String createId)
    {
        this.createId = createId;
    }
    
    @Column(name = "APPLY_NAME")
    public String getApplyName()
    {
        return applyName;
    }
    
    public void setApplyName(String applyName)
    {
        this.applyName = applyName;
    }
    
    @Column(name = "APPLY_DATE")
    public Date getApplyDate()
    {
        return applyDate;
    }
    
    public void setApplyDate(Date applyDate)
    {
        this.applyDate = applyDate;
    }
    
    @Column(name = "BACK_TYPE")
    public String getBackType()
    {
        return backType;
    }
    
    public void setBackType(String backType)
    {
        this.backType = backType;
    }
    
    @Column(name = "BACK_CHANNEL")
    public String getBackChannel()
    {
        return backChannel;
    }
    
    public void setBackChannel(String backChannel)
    {
        this.backChannel = backChannel;
    }
    
    @Column(name = "BACK_ADDRESS")
    public String getBackAddress()
    {
        return backAddress;
    }
    
    public void setBackAddress(String backAddress)
    {
        this.backAddress = backAddress;
    }
    
    @Column(name = "RECEIVE_NAME")
    public String getReceiveName()
    {
        return receiveName;
    }
    
    public void setReceiveName(String receiveName)
    {
        this.receiveName = receiveName;
    }
    
    @Column(name = "RECEIVE_PHONE")
    public String getReceivePhone()
    {
        return receivePhone;
    }
    
    public void setReceivePhone(String receivePhone)
    {
        this.receivePhone = receivePhone;
    }
    
}
