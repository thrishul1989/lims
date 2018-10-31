package com.todaysoft.lims.sample.entity.materialsApply;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.todaysoft.lims.persist.UuidKeyEntity;
import com.todaysoft.lims.sample.model.enums.MaterialsStatus;

@Entity
@Table(name = "LIMS_MATERIALS_APPLY")
public class MaterialsApply extends UuidKeyEntity
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private Integer materialsNum;//物资明细数量
    
    private MaterialsStatus status;//状态：0-未处理，1-处理中，2-已完成
    
    private String recipientName;//收件人姓名
    
    private String recipientTel;//收件人电话
    
    private String recipientAddress;//收件人地址（省市区）,按照逗号隔开
    
    private String recipientAddressDetail;//收件人详细地址
    
    private String creatorId;//申请人ID
    
    private Date applyTime;//申请时间 
    
    private String code;//物资申请编号
    
    private Date sendTime;
    
    @Column(name = "SEND_TIME")
    public Date getSendTime()
    {
        return sendTime;
    }
    
    public void setSendTime(Date sendTime)
    {
        this.sendTime = sendTime;
    }
    
    @Column(name = "CODE")
    public String getCode()
    {
        return code;
    }
    
    public void setCode(String code)
    {
        this.code = code;
    }
    
    @Column(name = "MATERIALS_NUM")
    public Integer getMaterialsNum()
    {
        return materialsNum;
    }
    
    public void setMaterialsNum(Integer materialsNum)
    {
        this.materialsNum = materialsNum;
    }
    
    @Column(name = "STATUS")
    public MaterialsStatus getStatus()
    {
        return status;
    }
    
    public void setStatus(MaterialsStatus status)
    {
        this.status = status;
    }
    
    @Column(name = "RECIPIENT_NAME")
    public String getRecipientName()
    {
        return recipientName;
    }
    
    public void setRecipientName(String recipientName)
    {
        this.recipientName = recipientName;
    }
    
    @Column(name = "RECIPIENT_TEL")
    public String getRecipientTel()
    {
        return recipientTel;
    }
    
    public void setRecipientTel(String recipientTel)
    {
        this.recipientTel = recipientTel;
    }
    
    @Column(name = "RECIPIENT_ADDRESS")
    public String getRecipientAddress()
    {
        return recipientAddress;
    }
    
    public void setRecipientAddress(String recipientAddress)
    {
        this.recipientAddress = recipientAddress;
    }
    
    @Column(name = "RECIPIENT_ADDRESS_DETAIL")
    public String getRecipientAddressDetail()
    {
        return recipientAddressDetail;
    }
    
    public void setRecipientAddressDetail(String recipientAddressDetail)
    {
        this.recipientAddressDetail = recipientAddressDetail;
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
    
    @Column(name = "APPLY_TIME")
    public Date getApplyTime()
    {
        return applyTime;
    }
    
    public void setApplyTime(Date applyTime)
    {
        this.applyTime = applyTime;
    }
    
}
