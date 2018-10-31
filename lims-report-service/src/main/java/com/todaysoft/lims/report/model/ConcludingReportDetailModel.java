package com.todaysoft.lims.report.model;

import java.util.Date;

public class ConcludingReportDetailModel
{
    private String id;
    
    private String sampleCode;
    
    private String sampleName;
    
    private String methodId;
    
    private String methodName;
    
    private String activeTask;
    
    private String lanCode;
    
    private Integer statu;
    
    private String updateName;
    
    private Date updateTime;
    
    private String concludingReportName;
    
    private String concludingReportCode;
    
    private String concludingReportPath;
    
    private String productName;
    
    public String getProductName()
    {
        return productName;
    }
    
    public void setProductName(String productName)
    {
        this.productName = productName;
    }
    
    public String getConcludingReportPath()
    {
        return concludingReportPath;
    }
    
    public void setConcludingReportPath(String concludingReportPath)
    {
        this.concludingReportPath = concludingReportPath;
    }
    
    public String getConcludingReportName()
    {
        return concludingReportName;
    }
    
    public void setConcludingReportName(String concludingReportName)
    {
        this.concludingReportName = concludingReportName;
    }
    
    public String getConcludingReportCode()
    {
        return concludingReportCode;
    }
    
    public void setConcludingReportCode(String concludingReportCode)
    {
        this.concludingReportCode = concludingReportCode;
    }
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getSampleCode()
    {
        return sampleCode;
    }
    
    public void setSampleCode(String sampleCode)
    {
        this.sampleCode = sampleCode;
    }
    
    public String getSampleName()
    {
        return sampleName;
    }
    
    public void setSampleName(String sampleName)
    {
        this.sampleName = sampleName;
    }
    
    public String getMethodId()
    {
        return methodId;
    }
    
    public void setMethodId(String methodId)
    {
        this.methodId = methodId;
    }
    
    public String getMethodName()
    {
        return methodName;
    }
    
    public void setMethodName(String methodName)
    {
        this.methodName = methodName;
    }
    
    public String getActiveTask()
    {
        return activeTask;
    }
    
    public void setActiveTask(String activeTask)
    {
        this.activeTask = activeTask;
    }
    
    public String getLanCode()
    {
        return lanCode;
    }
    
    public void setLanCode(String lanCode)
    {
        this.lanCode = lanCode;
    }
    
    public Integer getStatu()
    {
        return statu;
    }
    
    public void setStatu(Integer statu)
    {
        this.statu = statu;
    }
    
    public String getUpdateName()
    {
        return updateName;
    }
    
    public void setUpdateName(String updateName)
    {
        this.updateName = updateName;
    }
    
    public Date getUpdateTime()
    {
        return updateTime;
    }
    
    public void setUpdateTime(Date updateTime)
    {
        this.updateTime = updateTime;
    }
}
