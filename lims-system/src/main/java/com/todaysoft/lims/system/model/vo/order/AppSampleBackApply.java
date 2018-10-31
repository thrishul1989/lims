package com.todaysoft.lims.system.model.vo.order;

import java.util.Date;

import com.todaysoft.lims.persist.UuidKeyEntity;

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
    
    public String getUserId()
    {
        return userId;
    }
    
    public void setUserId(String userId)
    {
        this.userId = userId;
    }
    
    public String getCreateId()
    {
        return createId;
    }
    
    public void setCreateId(String createId)
    {
        this.createId = createId;
    }
    
    public String getApplyName()
    {
        return applyName;
    }
    
    public void setApplyName(String applyName)
    {
        this.applyName = applyName;
    }
    
    public Date getApplyDate()
    {
        return applyDate;
    }
    
    public void setApplyDate(Date applyDate)
    {
        this.applyDate = applyDate;
    }
    
    public String getBackType()
    {
        return backType;
    }
    
    public void setBackType(String backType)
    {
        this.backType = backType;
    }
    
    public String getBackChannel()
    {
        return backChannel;
    }
    
    public void setBackChannel(String backChannel)
    {
        this.backChannel = backChannel;
    }
    
    public String getBackAddress()
    {
        return backAddress;
    }
    
    public void setBackAddress(String backAddress)
    {
        this.backAddress = backAddress;
    }
    
    public String getReceiveName()
    {
        return receiveName;
    }
    
    public void setReceiveName(String receiveName)
    {
        this.receiveName = receiveName;
    }
    
    public String getReceivePhone()
    {
        return receivePhone;
    }
    
    public void setReceivePhone(String receivePhone)
    {
        this.receivePhone = receivePhone;
    }
    
}
