package com.todaysoft.lims.maintain.order.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_CONTRACT_SETTLE_BILL_DETAIL")
public class ContractSettleBillDetail extends UuidKeyEntity
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private String orderId;
    
    private Integer orderAmount;
    
    private String settleBillId; //账单id
    
    @Column(name = "ORDER_ID")
    public String getOrderId()
    {
        return orderId;
    }
    
    public void setOrderId(String orderId)
    {
        this.orderId = orderId;
    }
    
    @Column(name = "ORDER_AMOUNT")
    public Integer getOrderAmount()
    {
        return orderAmount;
    }
    
    public void setOrderAmount(Integer orderAmount)
    {
        this.orderAmount = orderAmount;
    }
    
    @Column(name = "SETTLE_BILL_ID")
    public String getSettleBillId()
    {
        return settleBillId;
    }
    
    public void setSettleBillId(String settleBillId)
    {
        this.settleBillId = settleBillId;
    }
}
