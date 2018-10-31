package com.todaysoft.lims.testing.report.model;

import java.util.Date;

public class ReportAndVerifyModel
{
    private String reportId;
    
    private String reportCode;
    
    private String reportName;
    
    private String sampleCode;
    
    private String productName;
    
    private Date reportTime;
    
    private String receiverName;
    
    private Integer reportStatus;

    public String getReportId()
    {
        return reportId;
    }

    public void setReportId(String reportId)
    {
        this.reportId = reportId;
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

    public String getSampleCode()
    {
        return sampleCode;
    }

    public void setSampleCode(String sampleCode)
    {
        this.sampleCode = sampleCode;
    }

    public String getProductName()
    {
        return productName;
    }

    public void setProductName(String productName)
    {
        this.productName = productName;
    }

    public Date getReportTime()
    {
        return reportTime;
    }

    public void setReportTime(Date reportTime)
    {
        this.reportTime = reportTime;
    }

    public String getReceiverName()
    {
        return receiverName;
    }

    public void setReceiverName(String receiverName)
    {
        this.receiverName = receiverName;
    }

    public Integer getReportStatus()
    {
        return reportStatus;
    }

    public void setReportStatus(Integer reportStatus)
    {
        this.reportStatus = reportStatus;
    }
    
}
