package com.todaysoft.lims.schedule.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_ORDER_PAYMENT_CONFIRM")
public class OrderPaymentConfirm extends UuidKeyEntity
{
    private static final long serialVersionUID = 672553433430057708L;
    
    private String orderId;
    
    private String confirmerName;
    
    private Date confirmTime;
    
    private Date paymentTime;
    
    @Column(name = "ORDER_ID")
    public String getOrderId()
    {
        return orderId;
    }
    
    public void setOrderId(String orderId)
    {
        this.orderId = orderId;
    }
    
    @Column(name = "CHECK_NAME")
    public String getConfirmerName()
    {
        return confirmerName;
    }
    
    public void setConfirmerName(String confirmerName)
    {
        this.confirmerName = confirmerName;
    }
    
    @Column(name = "CHECK_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getConfirmTime()
    {
        return confirmTime;
    }
    
    public void setConfirmTime(Date confirmTime)
    {
        this.confirmTime = confirmTime;
    }
    
    @Column(name = "PAYMENT_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getPaymentTime()
    {
        return paymentTime;
    }
    
    public void setPaymentTime(Date paymentTime)
    {
        this.paymentTime = paymentTime;
    }
}
