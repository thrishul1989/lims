package com.todaysoft.lims.order.mybatis.model;

import java.util.Date;

public class OrderScheduleModel
{
    private String scheduleId;
    
    private String verifyKey;
    
    private String productName;
    
    private String sampleCode;
    
    private String sampleName;
    
    private String typeName;
    
    private String methodName;
    
    private String proccessState;
    
    private String endType;
    
    private String activeTask;
    
    private String scheduleStatus;//
    
    private String poolingJsons;
    
    private Date startTime;
    
    private Date updateTime;
    
    private String geneSymbol;
    
    private String chromosomeLocation;
    
    private String samplePurpose; //样本用途
    
    public String getScheduleId()
    {
        return scheduleId;
    }
    
    public void setScheduleId(String scheduleId)
    {
        this.scheduleId = scheduleId;
    }
    
    public String getVerifyKey()
    {
        return verifyKey;
    }
    
    public void setVerifyKey(String verifyKey)
    {
        this.verifyKey = verifyKey;
    }
    
    public String getProductName()
    {
        return productName;
    }
    
    public void setProductName(String productName)
    {
        this.productName = productName;
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
    
    public String getTypeName()
    {
        return typeName;
    }
    
    public void setTypeName(String typeName)
    {
        this.typeName = typeName;
    }
    
    public String getMethodName()
    {
        return methodName;
    }
    
    public void setMethodName(String methodName)
    {
        this.methodName = methodName;
    }
    
    public String getProccessState()
    {
        return proccessState;
    }
    
    public void setProccessState(String proccessState)
    {
        this.proccessState = proccessState;
    }
    
    public String getEndType()
    {
        return endType;
    }
    
    public void setEndType(String endType)
    {
        this.endType = endType;
    }
    
    public String getActiveTask()
    {
        return activeTask;
    }
    
    public void setActiveTask(String activeTask)
    {
        this.activeTask = activeTask;
    }
    
    public String getScheduleStatus()
    {
        return scheduleStatus;
    }
    
    public void setScheduleStatus(String scheduleStatus)
    {
        this.scheduleStatus = scheduleStatus;
    }
    
    public String getPoolingJsons()
    {
        return poolingJsons;
    }
    
    public void setPoolingJsons(String poolingJsons)
    {
        this.poolingJsons = poolingJsons;
    }
    
    public Date getStartTime()
    {
        return startTime;
    }
    
    public void setStartTime(Date startTime)
    {
        this.startTime = startTime;
    }
    
    public Date getUpdateTime()
    {
        return updateTime;
    }
    
    public void setUpdateTime(Date updateTime)
    {
        this.updateTime = updateTime;
    }
    
    public String getGeneSymbol()
    {
        return geneSymbol;
    }
    
    public void setGeneSymbol(String geneSymbol)
    {
        this.geneSymbol = geneSymbol;
    }
    
    public String getChromosomeLocation()
    {
        return chromosomeLocation;
    }
    
    public void setChromosomeLocation(String chromosomeLocation)
    {
        this.chromosomeLocation = chromosomeLocation;
    }
    
    public String getSamplePurpose()
    {
        return samplePurpose;
    }
    
    public void setSamplePurpose(String samplePurpose)
    {
        this.samplePurpose = samplePurpose;
    }
    
}
