package com.todaysoft.lims.invoice.entity.order;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_ORDER_REDUCE_CHECK")
public class OrderReduceCheck extends UuidKeyEntity
{
    
    private static final long serialVersionUID = 1L;
    
    private OrderReduce reduceId;
    
    private String remark;// '审核备注',
    
    private Integer status;// '退款申请状态：0：待审核:1：审核通过；2：审核未通过；3：审核中；',
    
    private Integer replyAmount;
    
    private String checkerId;// '审核人ID',
    
    private String checkerName;// '审核人姓名',
    
    private Date checkerTime;//'审核时间',
    
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
    
    @Column(name = "CHECKER_ID")
    public String getCheckerId()
    {
        return checkerId;
    }
    
    public void setCheckerId(String checkerId)
    {
        this.checkerId = checkerId;
    }
    
    @Column(name = "CHECKER_NAME")
    public String getCheckerName()
    {
        return checkerName;
    }
    
    public void setCheckerName(String checkerName)
    {
        this.checkerName = checkerName;
    }
    
    @Column(name = "CHECKER_TIME")
    public Date getCheckerTime()
    {
        return checkerTime;
    }
    
    public void setCheckerTime(Date checkerTime)
    {
        this.checkerTime = checkerTime;
    }
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "APPLY_ID")
    @JsonIgnore
    public OrderReduce getReduceId()
    {
        return reduceId;
    }
    
    public void setReduceId(OrderReduce reduceId)
    {
        this.reduceId = reduceId;
    }
    
    @Column(name = "REPLY_AMOUNT")
    public Integer getReplyAmount()
    {
        return replyAmount;
    }
    
    public void setReplyAmount(Integer replyAmount)
    {
        this.replyAmount = replyAmount;
    }
    
}
