package com.todaysoft.lims.system.modules.report.model;

public class ReportEmailForOrderStatusModel
{
    private String reportNo;
    private String status;
    private String emailNo;
    private String emailName;
    private String emailTime;
    private String assignedName;
    private String emailContent;
    public String getReportNo()
    {
        return reportNo;
    }
    public void setReportNo(String reportNo)
    {
        this.reportNo = reportNo;
    }
    public String getStatus()
    {
        return status;
    }
    public void setStatus(String status)
    {
        this.status = status;
    }
    public String getEmailNo()
    {
        return emailNo;
    }
    public void setEmailNo(String emailNo)
    {
        this.emailNo = emailNo;
    }
    public String getEmailName()
    {
        return emailName;
    }
    public void setEmailName(String emailName)
    {
        this.emailName = emailName;
    }
    public String getEmailTime()
    {
        return emailTime;
    }
    public void setEmailTime(String emailTime)
    {
        this.emailTime = emailTime;
    }
    public String getAssignedName()
    {
        return assignedName;
    }
    public void setAssignedName(String assignedName)
    {
        this.assignedName = assignedName;
    }
    public String getEmailContent()
    {
        return emailContent;
    }
    public void setEmailContent(String emailContent)
    {
        this.emailContent = emailContent;
    }
    
}
