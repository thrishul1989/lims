package com.todaysoft.lims.system.model.vo.order;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "REPORT_CLINICAL_ORDER_INVOICE")
public class ReportClinicalOrderInvoice extends UuidKeyEntity
{
    private static final long serialVersionUID = 1L;
    
    private String orderId;
    
    private String taskId;
    
    private String drawerNo;
    
    private Integer amount;
    
    private String name;
    
    private Date invoiceTime;
    
    private String trackNumber;
    
    @Column(name = "ORDER_ID")
    public String getOrderId()
    {
        return orderId;
    }
    
    public void setOrderId(String orderId)
    {
        this.orderId = orderId;
    }
    
    @Column(name = "TASK_ID")
    public String getTaskId()
    {
        return taskId;
    }
    
    public void setTaskId(String taskId)
    {
        this.taskId = taskId;
    }
    
    @Column(name = "DRAWER_NO")
    public String getDrawerNo()
    {
        return drawerNo;
    }
    
    public void setDrawerNo(String drawerNo)
    {
        this.drawerNo = drawerNo;
    }
    
    @Column(name = "AMOUNT")
    public Integer getAmount()
    {
        return amount;
    }
    
    public void setAmount(Integer amount)
    {
        this.amount = amount;
    }
    
    @Column(name = "NAME")
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    @Column(name = "INVOICE_TIME")
    public Date getInvoiceTime()
    {
        return invoiceTime;
    }
    
    public void setInvoiceTime(Date invoiceTime)
    {
        this.invoiceTime = invoiceTime;
    }
    
    @Column(name = "TRACK_NUMBER")
    public String getTrackNumber()
    {
        return trackNumber;
    }
    
    public void setTrackNumber(String trackNumber)
    {
        this.trackNumber = trackNumber;
    }
    
}
