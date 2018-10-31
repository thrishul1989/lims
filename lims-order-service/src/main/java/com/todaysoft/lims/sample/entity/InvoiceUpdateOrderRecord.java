package com.todaysoft.lims.sample.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_INVOICE_ORDERUPDATE_RECORD")
public class InvoiceUpdateOrderRecord extends UuidKeyEntity
{
    /**
     * 
     */
    private static final long serialVersionUID = -5325764759732783882L;
    
    private String orderId;
    
    private String invoiceTaskId;
    
    private String updateId;
    
    private String updateName;
    
    private Date updateTime;
    
    private String orderProductId;
    
    private Integer orderProductAmount;
    
    @Column(name = "ORDER_PRODUCT_ID")
    public String getOrderProductId()
    {
        return orderProductId;
    }
    
    public void setOrderProductId(String orderProductId)
    {
        this.orderProductId = orderProductId;
    }
    
    @Column(name = "ORDER_PRODUCT_AMOUNT")
    public Integer getOrderProductAmount()
    {
        return orderProductAmount;
    }
    
    public void setOrderProductAmount(Integer orderProductAmount)
    {
        this.orderProductAmount = orderProductAmount;
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
    
    @Column(name = "INVOICE_TASK_ID")
    public String getInvoiceTaskId()
    {
        return invoiceTaskId;
    }
    
    public void setInvoiceTaskId(String invoiceTaskId)
    {
        this.invoiceTaskId = invoiceTaskId;
    }
    
    @Column(name = "UPDATE_ID")
    public String getUpdateId()
    {
        return updateId;
    }
    
    public void setUpdateId(String updateId)
    {
        this.updateId = updateId;
    }
    
    @Column(name = "UPDATE_NAME")
    public String getUpdateName()
    {
        return updateName;
    }
    
    public void setUpdateName(String updateName)
    {
        this.updateName = updateName;
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
    
}
