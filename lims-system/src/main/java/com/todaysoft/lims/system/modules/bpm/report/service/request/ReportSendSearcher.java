package com.todaysoft.lims.system.modules.bpm.report.service.request;


public class ReportSendSearcher
{

    private String reportCode;
    
    private Integer status; 
    
    private int pageNo;
    
    private int pageSize;
    
    public String getReportCode()
    {
        return reportCode;
    }

    public void setReportCode(String reportCode)
    {
        this.reportCode = reportCode;
    }

    public Integer getStatus()
    {
        return status;
    }

    public void setStatus(Integer status)
    {
        this.status = status;
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
    
}
