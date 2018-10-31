package com.todaysoft.lims.sample.entity.contract;

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
@Table(name = "LIMS_CONTRACT_SETTLE_BILL_DETAIL")
public class ContractSettleBillDetail extends UuidKeyEntity
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private ContractSettleBill settleBill;
    
    private String orderId;
    
    private Integer orderAmount;
    
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "SETTLE_BILL_ID")
    @NotFound(action = NotFoundAction.IGNORE)
    @JsonIgnore
    public ContractSettleBill getSettleBill()
    {
        return settleBill;
    }
    
    public void setSettleBill(ContractSettleBill settleBill)
    {
        this.settleBill = settleBill;
    }
    
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
    
}
