package com.todaysoft.lims.system.modules.bmm.model.response;

import java.math.BigDecimal;
import java.util.Date;

public class OrderInvoiceImportDetails
{
    private String taskId;
    
    private String invoiceNo;
    
    private BigDecimal invoiceAmount;
    
    private String receiverName;
    
    private Date operateTime;
    
    private String operatorId;
    
    private String operatorName;
    
    public String getTaskId()
    {
        return taskId;
    }
    
    public void setTaskId(String taskId)
    {
        this.taskId = taskId;
    }
    
    public String getInvoiceNo()
    {
        return invoiceNo;
    }
    
    public void setInvoiceNo(String invoiceNo)
    {
        this.invoiceNo = invoiceNo;
    }
    
    public BigDecimal getInvoiceAmount()
    {
        return invoiceAmount;
    }
    
    public void setInvoiceAmount(BigDecimal invoiceAmount)
    {
        this.invoiceAmount = invoiceAmount;
    }
    
    public String getReceiverName()
    {
        return receiverName;
    }
    
    public void setReceiverName(String receiverName)
    {
        this.receiverName = receiverName;
    }
    
    public Date getOperateTime()
    {
        return operateTime;
    }
    
    public void setOperateTime(Date operateTime)
    {
        this.operateTime = operateTime;
    }
    
    public String getOperatorId()
    {
        return operatorId;
    }
    
    public void setOperatorId(String operatorId)
    {
        this.operatorId = operatorId;
    }
    
    public String getOperatorName()
    {
        return operatorName;
    }
    
    public void setOperatorName(String operatorName)
    {
        this.operatorName = operatorName;
    }
}
