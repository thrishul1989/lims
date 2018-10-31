package com.todaysoft.lims.system.model.vo.order;

import java.util.Date;

import com.todaysoft.lims.persist.UuidKeyEntity;

public class ReportFinancialOrderInvoice extends UuidKeyEntity
{
    private static final long serialVersionUID = 1L;
    
    private String orderId;
    
    private String taskId;
    
    private String drawerNo;
    
    private Integer amount;
    
    private String name;
    
    private Date invoiceTime;
    
    public String getOrderId()
    {
        return orderId;
    }
    
    public void setOrderId(String orderId)
    {
        this.orderId = orderId;
    }
    
    public String getTaskId()
    {
        return taskId;
    }
    
    public void setTaskId(String taskId)
    {
        this.taskId = taskId;
    }
    
    public String getDrawerNo()
    {
        return drawerNo;
    }
    
    public void setDrawerNo(String drawerNo)
    {
        this.drawerNo = drawerNo;
    }
    
    public Integer getAmount()
    {
        return amount;
    }
    
    public void setAmount(Integer amount)
    {
        this.amount = amount;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public Date getInvoiceTime()
    {
        return invoiceTime;
    }
    
    public void setInvoiceTime(Date invoiceTime)
    {
        this.invoiceTime = invoiceTime;
    }
    
}
