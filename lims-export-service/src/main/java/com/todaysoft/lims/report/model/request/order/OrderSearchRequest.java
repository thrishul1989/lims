package com.todaysoft.lims.report.model.request.order;

public class OrderSearchRequest
{
    
    private String taskId;
    
    private String testingStatus;
    
    private String start;
    
    private String end;
    
    private String paymentStatus;
    
    private int pageNo;
    
    private int pageSize;
    
    private String invoiceStatus; //发票状态
    
    private String userId;
    
    private String userName;
    
    public String getTaskId()
    {
        return taskId;
    }
    
    public void setTaskId(String taskId)
    {
        this.taskId = taskId;
    }
    
    public String getStart()
    {
        return start;
    }
    
    public void setStart(String start)
    {
        this.start = start;
    }
    
    public String getEnd()
    {
        return end;
    }
    
    public void setEnd(String end)
    {
        this.end = end;
    }
    
    public int getPageNo()
    {
        return pageNo;
    }
    
    public void setPageNo(int pageNo)
    {
        this.pageNo = pageNo;
    }
    
    public int getPageSize()
    {
        return pageSize;
    }
    
    public void setPageSize(int pageSize)
    {
        this.pageSize = pageSize;
    }
    
    public String getTestingStatus()
    {
        return testingStatus;
    }
    
    public void setTestingStatus(String testingStatus)
    {
        this.testingStatus = testingStatus;
    }
    
    public String getPaymentStatus()
    {
        return paymentStatus;
    }
    
    public void setPaymentStatus(String paymentStatus)
    {
        this.paymentStatus = paymentStatus;
    }
    
    public String getInvoiceStatus()
    {
        return invoiceStatus;
    }
    
    public void setInvoiceStatus(String invoiceStatus)
    {
        this.invoiceStatus = invoiceStatus;
    }
    
    public String getUserId()
    {
        return userId;
    }
    
    public void setUserId(String userId)
    {
        this.userId = userId;
    }
    
    public String getUserName()
    {
        return userName;
    }
    
    public void setUserName(String userName)
    {
        this.userName = userName;
    }
    
}
