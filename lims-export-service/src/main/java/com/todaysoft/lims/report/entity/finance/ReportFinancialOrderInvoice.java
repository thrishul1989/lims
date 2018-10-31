package com.todaysoft.lims.report.entity.finance;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "REPORT_FINANCIAL_ORDER_INVOICE")
public class ReportFinancialOrderInvoice extends UuidKeyEntity
{
    private static final long serialVersionUID = 1L;
    
    private String orderId;
    
    private String taskId;
    
    private String drawerNo;
    
    private Integer amount;
    
    private String name;
    
    private Date invoiceTime;
    
    //private ReportFinancialOrder financialOrder; 
    
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
    
    /* @OneToOne(fetch = FetchType.EAGER)
     @JoinColumn(name = "ORDER_ID")
     @JsonIgnore
     @NotFound(action = NotFoundAction.IGNORE)
     public ReportFinancialOrder getFinancialOrder()
     {
         return financialOrder;
     }
     
     public void setFinancialOrder(ReportFinancialOrder financialOrder)
     {
         this.financialOrder = financialOrder;
     }
     */
}
