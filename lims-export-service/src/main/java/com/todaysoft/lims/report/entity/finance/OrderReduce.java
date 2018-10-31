package com.todaysoft.lims.report.entity.finance;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_ORDER_REDUCE")
public class OrderReduce extends UuidKeyEntity
{
    
    private static final long serialVersionUID = 1L;
    
    private Order orderId; //订单ID,
    
    private String applyReason;//'申请退款原因',
    
    private Integer applyAmount; //'申请减免金额',
    
    private Integer checkAmount; //'申请审批金额',
    
    private String remark;// '审核备注',
    
    private Integer status;// '退款申请状态：0：待审核:1：审核通过；2：审核未通过；3：审核中；',
    
    private String creatorId;// '申请人ID',
    
    private String creatorName;// '申请人姓名',
    
    private Date applyTime;//'申请时间',
    
    private Date updateTime;// '更新时间',
    
    public OrderReduce()
    {
        
    }
    
    @Column(name = "UPDATE_TIME")
    public Date getUpdateTime()
    {
        return updateTime;
    }
    
    public void setUpdateTime(Date updateTime)
    {
        this.updateTime = updateTime;
    }
    
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ORDER_ID")
    @JsonIgnore
    @NotFound(action = NotFoundAction.IGNORE)
    public Order getOrderId()
    {
        return orderId;
    }
    
    public void setOrderId(Order orderId)
    {
        this.orderId = orderId;
    }
    
    @Column(name = "APPLY_REASON")
    public String getApplyReason()
    {
        return applyReason;
    }
    
    public void setApplyReason(String applyReason)
    {
        this.applyReason = applyReason;
    }
    
    @Column(name = "APPLY_AMOUNT")
    public Integer getApplyAmount()
    {
        return applyAmount;
    }
    
    public void setApplyAmount(Integer applyAmount)
    {
        this.applyAmount = applyAmount;
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
    
    @Column(name = "STATUS")
    public Integer getStatus()
    {
        return status;
    }
    
    public void setStatus(Integer status)
    {
        this.status = status;
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
    
    @Column(name = "APPLY_TIME")
    public Date getApplyTime()
    {
        return applyTime;
    }
    
    public void setApplyTime(Date applyTime)
    {
        this.applyTime = applyTime;
    }
    
    @Column(name = "CHECK_AMOUNT")
    public Integer getCheckAmount()
    {
        return checkAmount;
    }
    
    public void setCheckAmount(Integer checkAmount)
    {
        this.checkAmount = checkAmount;
    }
    
}
