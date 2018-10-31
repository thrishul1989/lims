package com.todaysoft.lims.invoice.entity.order;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_ORDER_REDUCE")
public class OrderReduce extends UuidKeyEntity
{
    private static final long serialVersionUID = -7428718937780097167L;
    
    private String orderId;
    
    private Integer checkAmount; //'申请审批金额',
    
    private Integer status;// '退款申请状态：0：待审核:1：审核通过；2：审核未通过；3：审核中；',
    
    @Column(name = "ORDER_ID")
    public String getOrderId()
    {
        return orderId;
    }
    
    public void setOrderId(String orderId)
    {
        this.orderId = orderId;
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
    
    @Column(name = "STATUS")
    public Integer getStatus()
    {
        return status;
    }
    
    public void setStatus(Integer status)
    {
        this.status = status;
    }
}
