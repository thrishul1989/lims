package com.todaysoft.lims.system.modules.bmm.model;

import java.util.Date;

public class SampleBackSendInfo
{
    private String id;
    
    private SampleBackApply sampleBackApply;//返样申请
    
    private Date sendDate;//寄件日期
    
    private String transportType;//运输方式 （字典项）
    
    private String expressNo;//快递单号
    
    private String sendGoods;//寄件内容
    
    private Date recieveDate;//自取领取日期
    
    private String recieveName;//领取人
    
    private String recievePhone;//领取人电话
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public SampleBackApply getSampleBackApply()
    {
        return sampleBackApply;
    }
    
    public void setSampleBackApply(SampleBackApply sampleBackApply)
    {
        this.sampleBackApply = sampleBackApply;
    }
    
    public Date getSendDate()
    {
        return sendDate;
    }
    
    public void setSendDate(Date sendDate)
    {
        this.sendDate = sendDate;
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
    
    public String getSendGoods()
    {
        return sendGoods;
    }
    
    public void setSendGoods(String sendGoods)
    {
        this.sendGoods = sendGoods;
    }
    
    public Date getRecieveDate()
    {
        return recieveDate;
    }
    
    public void setRecieveDate(Date recieveDate)
    {
        this.recieveDate = recieveDate;
    }
    
    public String getRecieveName()
    {
        return recieveName;
    }
    
    public void setRecieveName(String recieveName)
    {
        this.recieveName = recieveName;
    }
    
    public String getRecievePhone()
    {
        return recievePhone;
    }
    
    public void setRecievePhone(String recievePhone)
    {
        this.recievePhone = recievePhone;
    }
    
}
