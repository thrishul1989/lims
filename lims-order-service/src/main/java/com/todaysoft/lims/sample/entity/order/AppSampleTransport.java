package com.todaysoft.lims.sample.entity.order;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "APP_SAMPLE_TRANSPORT")
public class AppSampleTransport extends UuidKeyEntity
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private String userId;
    
    private String transportType;
    
    private String expressNo;
    
    private Date sendDate;
    
    private String remark;
    
    private String expressPicture;
    
    private Date packDate;
    
    private String createId;
    
    private String transportName;
    
    private String transportPhone;
    
    @Column(name = "USER_ID")
    public String getUserId()
    {
        return userId;
    }
    
    public void setUserId(String userId)
    {
        this.userId = userId;
    }
    
    @Column(name = "TRANSPORT_TYPE")
    public String getTransportType()
    {
        return transportType;
    }
    
    public void setTransportType(String transportType)
    {
        this.transportType = transportType;
    }
    
    @Column(name = "EXPRESS_NO")
    public String getExpressNo()
    {
        return expressNo;
    }
    
    public void setExpressNo(String expressNo)
    {
        this.expressNo = expressNo;
    }
    
    @Column(name = "SEND_DATE")
    public Date getSendDate()
    {
        return sendDate;
    }
    
    public void setSendDate(Date sendDate)
    {
        this.sendDate = sendDate;
    }
    
    @Column(name = "REMARK")
    public String getRemark()
    {
        return remark;
    }
    
    public void setRemark(String remark)
    {
        this.remark = remark;
    }
    
    @Column(name = "EXPRESS_PICTURE")
    public String getExpressPicture()
    {
        return expressPicture;
    }
    
    public void setExpressPicture(String expressPicture)
    {
        this.expressPicture = expressPicture;
    }
    
    @Column(name = "PACK_DATE")
    public Date getPackDate()
    {
        return packDate;
    }
    
    public void setPackDate(Date packDate)
    {
        this.packDate = packDate;
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
    
    @Column(name = "TRANSPORT_NAME")
    public String getTransportName()
    {
        return transportName;
    }
    
    public void setTransportName(String transportName)
    {
        this.transportName = transportName;
    }
    
    @Column(name = "TRANSPORT_PHONE")
    public String getTransportPhone()
    {
        return transportPhone;
    }
    
    public void setTransportPhone(String transportPhone)
    {
        this.transportPhone = transportPhone;
    }
    
}
