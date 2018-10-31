package com.todaysoft.lims.system.modules.report.model;

public class ConcludingReportUploadModel
{
    private String concludingReportIds;
    
    private String fileName;
    
    private String filePath;
    
    public String getConcludingReportIds()
    {
        return concludingReportIds;
    }
    
    public void setConcludingReportIds(String concludingReportIds)
    {
        this.concludingReportIds = concludingReportIds;
    }
    
    public String getFileName()
    {
        return fileName;
    }
    
    public void setFileName(String fileName)
    {
        this.fileName = fileName;
    }
    
    public String getFilePath()
    {
        return filePath;
    }
    
    public void setFilePath(String filePath)
    {
        this.filePath = filePath;
    }
    
}
