package com.todaysoft.lims.technical.mybatis.entity;

import java.util.Date;

public class TestingSchedule
{
    
    public static final String END_FAILURE = "0";
    
    public static final String END_SUCCESS = "1";
    
    public static final Integer PAUSE = 1;
    
    public static final Integer CANCEL = 2;
    
    public static final Integer RESTART = 0;
    
    public static final String PROCCESSING = "5";
    
    private String id;
    
    private String orderId;
    
    private String productId;
    
    private String methodId;
    
    private String sampleId;
    
    private Date startTime;
    
    private String activeTask;
    
    private Date endTime;
    
    private String endType;
    
    private String verifyTarget;
    
    private String scheduleNodes;
    
    private String scheduleOutputs;
    
    private String verifyKey;
    
    private Boolean proccessState;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getOrderId()
    {
        return orderId;
    }
    
    public void setOrderId(String orderId)
    {
        this.orderId = orderId;
    }
    
    public String getProductId()
    {
        return productId;
    }
    
    public void setProductId(String productId)
    {
        this.productId = productId;
    }
    
    public String getMethodId()
    {
        return methodId;
    }
    
    public void setMethodId(String methodId)
    {
        this.methodId = methodId;
    }
    
    public String getSampleId()
    {
        return sampleId;
    }
    
    public void setSampleId(String sampleId)
    {
        this.sampleId = sampleId;
    }
    
    public Date getStartTime()
    {
        return startTime;
    }
    
    public void setStartTime(Date startTime)
    {
        this.startTime = startTime;
    }
    
    public String getActiveTask()
    {
        return activeTask;
    }
    
    public void setActiveTask(String activeTask)
    {
        this.activeTask = activeTask;
    }
    
    public Date getEndTime()
    {
        return endTime;
    }
    
    public void setEndTime(Date endTime)
    {
        this.endTime = endTime;
    }
    
    public String getEndType()
    {
        return endType;
    }
    
    public void setEndType(String endType)
    {
        this.endType = endType;
    }
    
    public String getVerifyTarget()
    {
        return verifyTarget;
    }
    
    public void setVerifyTarget(String verifyTarget)
    {
        this.verifyTarget = verifyTarget;
    }
    
    public String getScheduleNodes()
    {
        return scheduleNodes;
    }
    
    public void setScheduleNodes(String scheduleNodes)
    {
        this.scheduleNodes = scheduleNodes;
    }
    
    public String getScheduleOutputs()
    {
        return scheduleOutputs;
    }
    
    public void setScheduleOutputs(String scheduleOutputs)
    {
        this.scheduleOutputs = scheduleOutputs;
    }
    
    public String getVerifyKey()
    {
        return verifyKey;
    }
    
    public void setVerifyKey(String verifyKey)
    {
        this.verifyKey = verifyKey;
    }
    
    public Boolean getProccessState()
    {
        return proccessState;
    }
    
    public void setProccessState(Boolean proccessState)
    {
        this.proccessState = proccessState;
    }
}