package com.todaysoft.lims.report.model;

import java.util.Date;

public class TestingDataSendFormModel
{
    private String id;
    
    private String methodId;
    
    private String sampleCode;
    
    private String sampleName;
    
    private String methodName;
    
    private String activeTask;
    
    private String lanCode;
    
    private Integer statu;
    
    private String sendName;
    
    private Date sendTime;
    
    private String productName;
    
    public String getProductName()
    {
        return productName;
    }
    
    public void setProductName(String productName)
    {
        this.productName = productName;
    }
    
    public String getMethodId()
    {
        return methodId;
    }
    
    public void setMethodId(String methodId)
    {
        this.methodId = methodId;
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
    
    public String getSendName()
    {
        return sendName;
    }
    
    public void setSendName(String sendName)
    {
        this.sendName = sendName;
    }
    
    public Date getSendTime()
    {
        return sendTime;
    }
    
    public void setSendTime(Date sendTime)
    {
        this.sendTime = sendTime;
    }
}
