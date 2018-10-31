package com.todaysoft.lims.report.entity.system;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_CONTRACT")
public class Contract extends UuidKeyEntity
{
    
    /**
     * 业务库-合同主表
     */
    private static final long serialVersionUID = 1L;
    
    private String code;//合同编号
    
    private String name;//合同名称
    
    private String status;//合同状态：0-草稿 1-待确认 2-已确认
    
    private Date signDate;//签订日期
    
    private Date effectiveEnd;//有效期-结束日期
    
    private Integer marketingCenter;//营销中心，对应原有的检测类型
    
    private String creatorId;//创建人ID
    
    private String creatorName;//创建人姓名
    
    private String signUser;//业务员
    
    private BigDecimal amount;
    
    private Integer incomingAmount;
    
    private boolean deleted;//删除标记，0：未删除；1：已删除
    
    private Date createTime;//创建时间
    
    @Column(name = "CREATE_TIME")
    public Date getCreateTime()
    {
        return createTime;
    }
    
    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }
    
    @Column(name = "DEL_FLAG")
    public boolean isDeleted()
    {
        return deleted;
    }
    
    public void setDeleted(boolean deleted)
    {
        this.deleted = deleted;
    }
    
    @Column(name = "SIGN_USER")
    public String getSignUser()
    {
        return signUser;
    }
    
    public void setSignUser(String signUser)
    {
        this.signUser = signUser;
    }
    
    @Column(name = "AMOUNT")
    public BigDecimal getAmount()
    {
        return amount;
    }
    
    public void setAmount(BigDecimal amount)
    {
        this.amount = amount;
    }
    
    @Column(name = "INCOMING_AMOUNT")
    public Integer getIncomingAmount()
    {
        return incomingAmount;
    }
    
    public void setIncomingAmount(Integer incomingAmount)
    {
        this.incomingAmount = incomingAmount;
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
    
    @Column(name = "CREATOR_ID")
    public String getCreatorId()
    {
        return creatorId;
    }
    
    public void setCreatorId(String creatorId)
    {
        this.creatorId = creatorId;
    }
    
    @Column(name = "MARKETING_CENTER")
    public Integer getMarketingCenter()
    {
        return marketingCenter;
    }
    
    public void setMarketingCenter(Integer marketingCenter)
    {
        this.marketingCenter = marketingCenter;
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
    
    @Column(name = "NAME")
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    @Column(name = "STATUS")
    public String getStatus()
    {
        return status;
    }
    
    public void setStatus(String status)
    {
        this.status = status;
    }
    
    @Column(name = "SIGN_DATE")
    public Date getSignDate()
    {
        return signDate;
    }
    
    public void setSignDate(Date signDate)
    {
        this.signDate = signDate;
    }
    
    @Column(name = "EFFECTIVE_END")
    public Date getEffectiveEnd()
    {
        return effectiveEnd;
    }
    
    public void setEffectiveEnd(Date effectiveEnd)
    {
        this.effectiveEnd = effectiveEnd;
    }
    
}
