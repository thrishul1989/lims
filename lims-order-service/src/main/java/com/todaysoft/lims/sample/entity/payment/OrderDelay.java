package com.todaysoft.lims.sample.entity.payment;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.OrderBy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.todaysoft.lims.persist.UuidKeyEntity;
import com.todaysoft.lims.sample.entity.order.Order;

@Builder
@AllArgsConstructor
@Entity
@Table(name = "LIMS_ORDER_DELAY")
public class OrderDelay extends UuidKeyEntity
{
    
    private static final long serialVersionUID = 1L;
    
    private Order orderId; //订单ID,
    
    private String delayReason;//'申请延迟原因',
    
    private String remark;// '审核备注',
    
    private Integer status;// '退款申请状态：0：待审核:1：审核通过；2：审核未通过；3：审核中；',
    
    private String creatorId;// '申请人ID',
    
    private String creatorName;// '申请人姓名',
    
    private Date applyTime;//'申请时间',
    
    private Date updateTime;//'更新时间',
    
    private Date planPayTime;//'计划延迟时间',
    
    private Date replyPayTime;//'审批延迟时间',
    
    private List<OrderDelayCheck> orderDelayCheckList;
    
    public OrderDelay()
    {
        
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
    
    @Column(name = "UPDATE_TIME")
    public Date getUpdateTime()
    {
        return updateTime;
    }
    
    public void setUpdateTime(Date updateTime)
    {
        this.updateTime = updateTime;
    }
    
    @Column(name = "PLAN_PAY_TIME")
    public Date getPlanPayTime()
    {
        return planPayTime;
    }
    
    public void setPlanPayTime(Date planPayTime)
    {
        this.planPayTime = planPayTime;
    }
    
    @OneToMany(mappedBy = "delayId", cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
    @OrderBy(clause = "checkerTime asc nulls last")
    public List<OrderDelayCheck> getOrderDelayCheckList()
    {
        return orderDelayCheckList;
    }
    
    public void setOrderDelayCheckList(List<OrderDelayCheck> orderDelayCheckList)
    {
        this.orderDelayCheckList = orderDelayCheckList;
    }
    
    @Column(name = "REPLY_PAY_TIME")
    public Date getReplyPayTime()
    {
        return replyPayTime;
    }
    
    public void setReplyPayTime(Date replyPayTime)
    {
        this.replyPayTime = replyPayTime;
    }
    
    @Column(name = "DELAY_REASON")
    public String getDelayReason()
    {
        return delayReason;
    }
    
    public void setDelayReason(String delayReason)
    {
        this.delayReason = delayReason;
    }
}
