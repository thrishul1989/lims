package com.todaysoft.lims.system.modules.invoice.model;

public class TestingReport
{
    private String orderId;
    
    private String orderCode;
    
    private String productId;
    
    private String productCode;
    
    private String productName;
    
    private int status;
    
    private String reportCode;
    
    private String reportName;
    
    private String firstReviewStatus;
    
    private String secondReviewStatus;
    
    private String emailStatus;//报告寄送状态

    public String getOrderId()
    {
        return orderId;
    }

    public void setOrderId(String orderId)
    {
        this.orderId = orderId;
    }

    public String getOrderCode()
    {
        return orderCode;
    }

    public void setOrderCode(String orderCode)
    {
        this.orderCode = orderCode;
    }

    public String getProductId()
    {
        return productId;
    }

    public void setProductId(String productId)
    {
        this.productId = productId;
    }

    public String getProductCode()
    {
        return productCode;
    }

    public void setProductCode(String productCode)
    {
        this.productCode = productCode;
    }

    public String getProductName()
    {
        return productName;
    }

    public void setProductName(String productName)
    {
        this.productName = productName;
    }

    public int getStatus()
    {
        return status;
    }

    public void setStatus(int status)
    {
        this.status = status;
    }

    public String getReportCode()
    {
        return reportCode;
    }

    public void setReportCode(String reportCode)
    {
        this.reportCode = reportCode;
    }

    public String getReportName()
    {
        return reportName;
    }

    public void setReportName(String reportName)
    {
        this.reportName = reportName;
    }

    public String getFirstReviewStatus()
    {
        return firstReviewStatus;
    }

    public void setFirstReviewStatus(String firstReviewStatus)
    {
        this.firstReviewStatus = firstReviewStatus;
    }

    public String getSecondReviewStatus()
    {
        return secondReviewStatus;
    }

    public void setSecondReviewStatus(String secondReviewStatus)
    {
        this.secondReviewStatus = secondReviewStatus;
    }

    public String getEmailStatus()
    {
        return emailStatus;
    }

    public void setEmailStatus(String emailStatus)
    {
        this.emailStatus = emailStatus;
    }
    
}
