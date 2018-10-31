package com.todaysoft.lims.system.modules.report.model;

import java.util.List;

public class ConcludingReportFormModel
{
    private List<ConcludingReportDetailModel> doList;
    
    private List<ConcludingReportDetailModel> unDoList;
    
    private List<ConcludingReportDetailModel> reportList;
    
    public List<ConcludingReportDetailModel> getDoList()
    {
        return doList;
    }
    
    public void setDoList(List<ConcludingReportDetailModel> doList)
    {
        this.doList = doList;
    }
    
    public List<ConcludingReportDetailModel> getUnDoList()
    {
        return unDoList;
    }
    
    public void setUnDoList(List<ConcludingReportDetailModel> unDoList)
    {
        this.unDoList = unDoList;
    }
    
    public List<ConcludingReportDetailModel> getReportList()
    {
        return reportList;
    }
    
    public void setReportList(List<ConcludingReportDetailModel> reportList)
    {
        this.reportList = reportList;
    }
}
