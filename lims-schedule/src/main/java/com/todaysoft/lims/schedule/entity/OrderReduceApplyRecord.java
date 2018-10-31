package com.todaysoft.lims.schedule.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_ORDER_REDUCE")
public class OrderReduceApplyRecord extends UuidKeyEntity
{
    private static final long serialVersionUID = -7220002629067820407L;
    
    public static final String STATUS_ARGEED = "1";
    
    private String orderId;
    
    private String status;
    
    @Column(name = "ORDER_ID")
    public String getOrderId()
    {
        return orderId;
    }
    
    public void setOrderId(String orderId)
    {
        this.orderId = orderId;
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
}
