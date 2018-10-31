package com.todaysoft.lims.system.modules.report.model;

import java.util.List;

public class WhetherEmailModel
{
    private List<String> whetherOrderIds;
    
    boolean whether;
    
    private List<String> reportEmailIds;
    
    private boolean emailOrNot;
    
    public boolean isEmailOrNot()
    {
        return emailOrNot;
    }
    
    public void setEmailOrNot(boolean emailOrNot)
    {
        this.emailOrNot = emailOrNot;
    }
    
    public List<String> getWhetherOrderIds()
    {
        return whetherOrderIds;
    }
    
    public void setWhetherOrderIds(List<String> whetherOrderIds)
    {
        this.whetherOrderIds = whetherOrderIds;
    }
    
    public boolean isWhether()
    {
        return whether;
    }
    
    public void setWhether(boolean whether)
    {
        this.whether = whether;
    }
    
    public List<String> getReportEmailIds()
    {
        return reportEmailIds;
    }
    
    public void setReportEmailIds(List<String> reportEmailIds)
    {
        this.reportEmailIds = reportEmailIds;
    }
    
    public List<String> getAllReportEmailIds()
    {
        return allReportEmailIds;
    }
    
    public void setAllReportEmailIds(List<String> allReportEmailIds)
    {
        this.allReportEmailIds = allReportEmailIds;
    }
    
    private List<String> allReportEmailIds;
    
}
