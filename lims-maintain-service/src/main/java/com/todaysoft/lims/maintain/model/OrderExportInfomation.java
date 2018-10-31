package com.todaysoft.lims.maintain.model;

public class OrderExportInfomation
{
    private String orderCode;
    
    private String productName;
    
    private String methodName;
    
    private String sampleCode;
    
    private String reciveTime;
    
    private String scheduleStartTime;
    
    private String bioTaskTime;
    
    private String reviewTime;
    
    private String reportDataSendTime;
    
    public String getSampleCode()
    {
        return sampleCode;
    }
    
    public void setSampleCode(String sampleCode)
    {
        this.sampleCode = sampleCode;
    }
    
    public String getOrderCode()
    {
        return orderCode;
    }
    
    public void setOrderCode(String orderCode)
    {
        this.orderCode = orderCode;
    }
    
    public String getProductName()
    {
        return productName;
    }
    
    public void setProductName(String productName)
    {
        this.productName = productName;
    }
    
    public String getMethodName()
    {
        return methodName;
    }
    
    public void setMethodName(String methodName)
    {
        this.methodName = methodName;
    }
    
    public String getReciveTime()
    {
        return reciveTime;
    }
    
    public void setReciveTime(String reciveTime)
    {
        this.reciveTime = reciveTime;
    }
    
    public String getScheduleStartTime()
    {
        return scheduleStartTime;
    }
    
    public void setScheduleStartTime(String scheduleStartTime)
    {
        this.scheduleStartTime = scheduleStartTime;
    }
    
    public String getBioTaskTime()
    {
        return bioTaskTime;
    }
    
    public void setBioTaskTime(String bioTaskTime)
    {
        this.bioTaskTime = bioTaskTime;
    }
    
    public String getReviewTime()
    {
        return reviewTime;
    }
    
    public void setReviewTime(String reviewTime)
    {
        this.reviewTime = reviewTime;
    }
    
    public String getReportDataSendTime()
    {
        return reportDataSendTime;
    }
    
    public void setReportDataSendTime(String reportDataSendTime)
    {
        this.reportDataSendTime = reportDataSendTime;
    }
}
