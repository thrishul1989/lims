package com.todaysoft.lims.invoice.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_TESTING_SCHEDULE")
public class TestingSchedule extends UuidKeyEntity implements Serializable
{
    private static final long serialVersionUID = 2312730608024695169L;
    
    private String orderId;
    
    @Column(name = "ORDER_ID")
    public String getOrderId()
    {
        return orderId;
    }
    
    public void setOrderId(String orderId)
    {
        this.orderId = orderId;
    }
}
