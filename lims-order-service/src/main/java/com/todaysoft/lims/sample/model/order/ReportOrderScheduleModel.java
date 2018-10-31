package com.todaysoft.lims.sample.model.order;

import com.todaysoft.lims.persist.UuidKeyEntity;

public class ReportOrderScheduleModel extends UuidKeyEntity
{
    private static final long serialVersionUID = 1L;
    
    private String scheduleId;
    
    private String orderId;
    
    private String taskId;
    
    private String productName;
    
    private Integer refundStatus;
    
    private String sampleCode;
    
    private String sampleTypeName;
    
    private String purpose;
    
    private String checkName;
    
    private String taskName;
    
    private String testerName;
    
    private String assignTime;
    
    private String endTime;
    
    private String code;
    
    private String details;
    
    private Integer status;
    
    private Integer endType;
    
    private String pauseName;
    
    private String pauseTime;
    
    private String restartName;
    
    private String restartTime;
    
    public String getScheduleId()
    {
        return scheduleId;
    }
    
    public void setScheduleId(String scheduleId)
    {
        this.scheduleId = scheduleId;
    }
    
    public String getOrderId()
    {
        return orderId;
    }
    
    public void setOrderId(String orderId)
    {
        this.orderId = orderId;
    }
    
    public String getTaskId()
    {
        return taskId;
    }
    
    public void setTaskId(String taskId)
    {
        this.taskId = taskId;
    }
    
    public String getProductName()
    {
        return productName;
    }
    
    public void setProductName(String productName)
    {
        this.productName = productName;
    }
    
    public Integer getRefundStatus()
    {
        return refundStatus;
    }
    
    public void setRefundStatus(Integer refundStatus)
    {
        this.refundStatus = refundStatus;
    }
    
    public String getSampleCode()
    {
        return sampleCode;
    }
    
    public void setSampleCode(String sampleCode)
    {
        this.sampleCode = sampleCode;
    }
    
    public String getSampleTypeName()
    {
        return sampleTypeName;
    }
    
    public void setSampleTypeName(String sampleTypeName)
    {
        this.sampleTypeName = sampleTypeName;
    }
    
    public String getPurpose()
    {
        return purpose;
    }
    
    public void setPurpose(String purpose)
    {
        this.purpose = purpose;
    }
    
    public String getCheckName()
    {
        return checkName;
    }
    
    public void setCheckName(String checkName)
    {
        this.checkName = checkName;
    }
    
    public String getTaskName()
    {
        return taskName;
    }
    
    public void setTaskName(String taskName)
    {
        this.taskName = taskName;
    }
    
    public String getTesterName()
    {
        return testerName;
    }
    
    public void setTesterName(String testerName)
    {
        this.testerName = testerName;
    }
    
    public String getCode()
    {
        return code;
    }
    
    public void setCode(String code)
    {
        this.code = code;
    }
    
    public String getDetails()
    {
        return details;
    }
    
    public void setDetails(String details)
    {
        this.details = details;
    }
    
    public Integer getStatus()
    {
        return status;
    }
    
    public void setStatus(Integer status)
    {
        this.status = status;
    }
    
    public Integer getEndType()
    {
        return endType;
    }
    
    public void setEndType(Integer endType)
    {
        this.endType = endType;
    }
    
    public String getPauseName()
    {
        return pauseName;
    }
    
    public void setPauseName(String pauseName)
    {
        this.pauseName = pauseName;
    }
    
    public String getRestartName()
    {
        return restartName;
    }
    
    public void setRestartName(String restartName)
    {
        this.restartName = restartName;
    }
    
    public String getAssignTime()
    {
        return assignTime;
    }
    
    public void setAssignTime(String assignTime)
    {
        this.assignTime = assignTime;
    }
    
    public String getEndTime()
    {
        return endTime;
    }
    
    public void setEndTime(String endTime)
    {
        this.endTime = endTime;
    }
    
    public String getPauseTime()
    {
        return pauseTime;
    }
    
    public void setPauseTime(String pauseTime)
    {
        this.pauseTime = pauseTime;
    }
    
    public String getRestartTime()
    {
        return restartTime;
    }
    
    public void setRestartTime(String restartTime)
    {
        this.restartTime = restartTime;
    }
    
}
