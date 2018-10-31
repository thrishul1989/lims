package com.todaysoft.lims.sample.entity.order;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "APP_SAMPLE_BACK_RELATION")
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
    
    @Column(name = "USER_ID")
    public String getUserId()
    {
        return userId;
    }
    
    public void setUserId(String userId)
    {
        this.userId = userId;
    }
    
    @Column(name = "APPLY_ID")
    public String getApplyId()
    {
        return applyId;
    }
    
    public void setApplyId(String applyId)
    {
        this.applyId = applyId;
    }
    
    @Column(name = "SAMPLE_ID")
    public String getSampleId()
    {
        return sampleId;
    }
    
    public void setSampleId(String sampleId)
    {
        this.sampleId = sampleId;
    }
    
    @Column(name = "SAMPLE_TYPE")
    public String getSampleType()
    {
        return sampleType;
    }
    
    public void setSampleType(String sampleType)
    {
        this.sampleType = sampleType;
    }
    
    @Column(name = "ORDER_NO")
    public String getOrderNo()
    {
        return orderNo;
    }
    
    public void setOrderNo(String orderNo)
    {
        this.orderNo = orderNo;
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
    
    @Column(name = "RECEIVE_DATE")
    public Date getReceiveDate()
    {
        return receiveDate;
    }
    
    public void setReceiveDate(Date receiveDate)
    {
        this.receiveDate = receiveDate;
    }
    
}
