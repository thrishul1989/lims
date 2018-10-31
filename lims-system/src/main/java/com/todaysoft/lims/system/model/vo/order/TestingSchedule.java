package com.todaysoft.lims.system.model.vo.order;

import java.util.Date;

import com.todaysoft.lims.persist.UuidKeyEntity;

public class TestingSchedule extends UuidKeyEntity
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private String orderId;
    
    private String productId;
    
    private String methodId;
    
    private String sampleId;
    
    private Date startTime;
    
    private String activeTask;
    
    private Date endTime;
    
    private String endType;
    
    private String scheduleNodes;
    
    private String scheduleOutPuts;
    
    private String verifyKey;
    
    private String verifyTarget;
    
    private Integer proccessState;
    
    public Integer getProccessState()
    {
        return proccessState;
    }

    public void setProccessState(Integer proccessState)
    {
        this.proccessState = proccessState;
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
    
    public String getScheduleNodes()
    {
        return scheduleNodes;
    }
    
    public void setScheduleNodes(String scheduleNodes)
    {
        this.scheduleNodes = scheduleNodes;
    }
    
    public String getScheduleOutPuts()
    {
        return scheduleOutPuts;
    }
    
    public void setScheduleOutPuts(String scheduleOutPuts)
    {
        this.scheduleOutPuts = scheduleOutPuts;
    }
    
    public String getVerifyKey()
    {
        return verifyKey;
    }
    
    public void setVerifyKey(String verifyKey)
    {
        this.verifyKey = verifyKey;
    }
    
    public String getVerifyTarget()
    {
        return verifyTarget;
    }
    
    public void setVerifyTarget(String verifyTarget)
    {
        this.verifyTarget = verifyTarget;
    }
    
}
