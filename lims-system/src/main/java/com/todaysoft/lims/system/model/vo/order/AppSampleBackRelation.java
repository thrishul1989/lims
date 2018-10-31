package com.todaysoft.lims.system.model.vo.order;

import java.util.Date;

import com.todaysoft.lims.persist.UuidKeyEntity;

public class AppSampleBackRelation extends UuidKeyEntity
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private String userId;
    
    private String applyId;
    
    private String sampleId;
    
    private String sampleType;
    
    private String orderNo;
    
    private String receiveName;
    
    private String receivePhone;
    
    private Date receiveDate;
    
    public String getUserId()
    {
        return userId;
    }
    
    public void setUserId(String userId)
    {
        this.userId = userId;
    }
    
    public String getApplyId()
    {
        return applyId;
    }
    
    public void setApplyId(String applyId)
    {
        this.applyId = applyId;
    }
    
    public String getSampleId()
    {
        return sampleId;
    }
    
    public void setSampleId(String sampleId)
    {
        this.sampleId = sampleId;
    }
    
    public String getSampleType()
    {
        return sampleType;
    }
    
    public void setSampleType(String sampleType)
    {
        this.sampleType = sampleType;
    }
    
    public String getOrderNo()
    {
        return orderNo;
    }
    
    public void setOrderNo(String orderNo)
    {
        this.orderNo = orderNo;
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
    
    public Date getReceiveDate()
    {
        return receiveDate;
    }
    
    public void setReceiveDate(Date receiveDate)
    {
        this.receiveDate = receiveDate;
    }
    
}
