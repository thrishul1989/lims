package com.todaysoft.lims.sample.entity.materialsApply;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_MATERIALS_APPLY_TRANSPORT")
public class MaterialsApplyTransport extends UuidKeyEntity
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private String applyId;//申请ID
    
    private String transportType;//运输方式（字典：0:人工运输、1:快递、...）
    
    private String transportName;//运输人姓名
    
    private String transportPhone;//运输人电话
    
    private String courierNumber;//快递号
    
    private Date sendTime;//寄送时间
    
    private String creatorId;//操作人id
    
    private String creatorName;//操作人姓名
    
    private Date createTime;//操作时间
    
    @Column(name = "APPLY_ID")
    public String getApplyId()
    {
        return applyId;
    }
    
    public void setApplyId(String applyId)
    {
        this.applyId = applyId;
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
    
    @Column(name = "COURIER_NUMBER")
    public String getCourierNumber()
    {
        return courierNumber;
    }
    
    public void setCourierNumber(String courierNumber)
    {
        this.courierNumber = courierNumber;
    }
    
    @Column(name = "SEND_TIME")
    public Date getSendTime()
    {
        return sendTime;
    }
    
    public void setSendTime(Date sendTime)
    {
        this.sendTime = sendTime;
    }
    
    @Column(name = "CREATOR_ID")
    public String getCreatorId()
    {
        return creatorId;
    }
    
    public void setCreatorId(String creatorId)
    {
        this.creatorId = creatorId;
    }
    
    @Column(name = "CREATOR_NAME")
    public String getCreatorName()
    {
        return creatorName;
    }
    
    public void setCreatorName(String creatorName)
    {
        this.creatorName = creatorName;
    }
    
    @Column(name = "CREATE_TIME")
    public Date getCreateTime()
    {
        return createTime;
    }
    
    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }
    
}
