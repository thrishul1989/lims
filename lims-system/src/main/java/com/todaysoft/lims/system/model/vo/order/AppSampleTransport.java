package com.todaysoft.lims.system.model.vo.order;

import java.util.Date;

import com.todaysoft.lims.persist.UuidKeyEntity;

public class AppSampleTransport extends UuidKeyEntity
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * 
     */
    
    private String userId;
    
    private String transportType;
    
    private String expressNo;
    
    private Date sendDate;
    
    private String remark;
    
    private String expressPicture;
    
    private String expressPictureShow;
    
    private Date packDate;
    
    private String createId;
    
    private String createName;
    
    private String transportName;
    
    private String transportPhone;
    
    private Integer sampleCount; //样本数量
    
    public Integer getSampleCount()
    {
        return sampleCount;
    }
    
    public void setSampleCount(Integer sampleCount)
    {
        this.sampleCount = sampleCount;
    }
    
    public String getCreateName()
    {
        return createName;
    }
    
    public void setCreateName(String createName)
    {
        this.createName = createName;
    }
    
    public String getExpressPictureShow()
    {
        return expressPictureShow;
    }
    
    public void setExpressPictureShow(String expressPictureShow)
    {
        this.expressPictureShow = expressPictureShow;
    }
    
    public String getUserId()
    {
        return userId;
    }
    
    public void setUserId(String userId)
    {
        this.userId = userId;
    }
    
    public String getTransportType()
    {
        return transportType;
    }
    
    public void setTransportType(String transportType)
    {
        this.transportType = transportType;
    }
    
    public String getExpressNo()
    {
        return expressNo;
    }
    
    public void setExpressNo(String expressNo)
    {
        this.expressNo = expressNo;
    }
    
    public Date getSendDate()
    {
        return sendDate;
    }
    
    public void setSendDate(Date sendDate)
    {
        this.sendDate = sendDate;
    }
    
    public String getRemark()
    {
        return remark;
    }
    
    public void setRemark(String remark)
    {
        this.remark = remark;
    }
    
    public String getExpressPicture()
    {
        return expressPicture;
    }
    
    public void setExpressPicture(String expressPicture)
    {
        this.expressPicture = expressPicture;
    }
    
    public Date getPackDate()
    {
        return packDate;
    }
    
    public void setPackDate(Date packDate)
    {
        this.packDate = packDate;
    }
    
    public String getCreateId()
    {
        return createId;
    }
    
    public void setCreateId(String createId)
    {
        this.createId = createId;
    }
    
    public String getTransportName()
    {
        return transportName;
    }
    
    public void setTransportName(String transportName)
    {
        this.transportName = transportName;
    }
    
    public String getTransportPhone()
    {
        return transportPhone;
    }
    
    public void setTransportPhone(String transportPhone)
    {
        this.transportPhone = transportPhone;
    }
    
}
