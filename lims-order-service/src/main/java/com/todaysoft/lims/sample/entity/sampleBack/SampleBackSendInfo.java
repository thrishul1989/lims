package com.todaysoft.lims.sample.entity.sampleBack;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_SAMPLE_BACK_SEND")
public class SampleBackSendInfo extends UuidKeyEntity
{
    
    private static final long serialVersionUID = 3464267165041192193L;
    
    private SampleBackApply sampleBackApply;//返样申请
    
    private Date sendDate;//寄件日期
    
    private String transportType;//运输方式 （字典项）
    
    private String expressNo;//快递单号
    
    private String sendGoods;//寄件内容
    
    private Date recieveDate;//自取领取日期
    
    private String recieveName;//领取人
    
    private String recievePhone;//取件人电话
    
    @ManyToOne
    @JoinColumn(name = "SAMPLE_BACK_APPLY_ID")
    @JsonIgnore
    public SampleBackApply getSampleBackApply()
    {
        return sampleBackApply;
    }
    
    public void setSampleBackApply(SampleBackApply sampleBackApply)
    {
        this.sampleBackApply = sampleBackApply;
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
    
    @Column(name = "SEND_GOODS")
    public String getSendGoods()
    {
        return sendGoods;
    }
    
    public void setSendGoods(String sendGoods)
    {
        this.sendGoods = sendGoods;
    }
    
    @Column(name = "RECEIVE_NAME")
    public String getRecieveName()
    {
        return recieveName;
    }
    
    public void setRecieveName(String recieveName)
    {
        this.recieveName = recieveName;
    }
    
    @Column(name = "RECEIVE_DATE")
    public Date getRecieveDate()
    {
        return recieveDate;
    }
    
    public void setRecieveDate(Date recieveDate)
    {
        this.recieveDate = recieveDate;
    }
    
    @Column(name = "RECEIVE_PHONE")
    public String getRecievePhone()
    {
        return recievePhone;
    }
    
    public void setRecievePhone(String recievePhone)
    {
        this.recievePhone = recievePhone;
    }
    
}
