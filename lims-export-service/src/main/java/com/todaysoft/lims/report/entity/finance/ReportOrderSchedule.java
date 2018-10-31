package com.todaysoft.lims.report.entity.finance;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "REPORT_ORDER_SCHEDULE")
public class ReportOrderSchedule extends UuidKeyEntity
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
    
    private Date assignTime;
    
    private Date endTime;
    
    private String code;
    
    private String details;
    
    private Integer status;
    
    private Integer endType;
    
    private String pauseName;
    
    private Date pauseTime;
    
    private String restartName;
    
    private Date restartTime;
    
    private String remark;
    
    private String verifyChrPosition;
    
    @Column(name = "SCHEDULE_ID")
    public String getScheduleId()
    {
        return scheduleId;
    }
    
    public void setScheduleId(String scheduleId)
    {
        this.scheduleId = scheduleId;
    }
    
    @Column(name = "ORDER_ID")
    public String getOrderId()
    {
        return orderId;
    }
    
    public void setOrderId(String orderId)
    {
        this.orderId = orderId;
    }
    
    @Column(name = "TASK_ID")
    public String getTaskId()
    {
        return taskId;
    }
    
    public void setTaskId(String taskId)
    {
        this.taskId = taskId;
    }
    
    @Column(name = "PRODUCT_NAME")
    public String getProductName()
    {
        return productName;
    }
    
    public void setProductName(String productName)
    {
        this.productName = productName;
    }
    
    @Column(name = "REFUND_STATUS")
    public Integer getRefundStatus()
    {
        return refundStatus;
    }
    
    public void setRefundStatus(Integer refundStatus)
    {
        this.refundStatus = refundStatus;
    }
    
    @Column(name = "SAMPLE_CODE")
    public String getSampleCode()
    {
        return sampleCode;
    }
    
    public void setSampleCode(String sampleCode)
    {
        this.sampleCode = sampleCode;
    }
    
    @Column(name = "SAMPLE_TYPE_NAME")
    public String getSampleTypeName()
    {
        return sampleTypeName;
    }
    
    public void setSampleTypeName(String sampleTypeName)
    {
        this.sampleTypeName = sampleTypeName;
    }
    
    @Column(name = "PURPOSE")
    public String getPurpose()
    {
        return purpose;
    }
    
    public void setPurpose(String purpose)
    {
        this.purpose = purpose;
    }
    
    @Column(name = "CHECK_NAME")
    public String getCheckName()
    {
        return checkName;
    }
    
    public void setCheckName(String checkName)
    {
        this.checkName = checkName;
    }
    
    @Column(name = "TASK_NAME")
    public String getTaskName()
    {
        return taskName;
    }
    
    public void setTaskName(String taskName)
    {
        this.taskName = taskName;
    }
    
    @Column(name = "TESTER_NAME")
    public String getTesterName()
    {
        return testerName;
    }
    
    public void setTesterName(String testerName)
    {
        this.testerName = testerName;
    }
    
    @Column(name = "ASSIGN_TIME")
    public Date getAssignTime()
    {
        return assignTime;
    }
    
    public void setAssignTime(Date assignTime)
    {
        this.assignTime = assignTime;
    }
    
    @Column(name = "END_TIME")
    public Date getEndTime()
    {
        return endTime;
    }
    
    public void setEndTime(Date endTime)
    {
        this.endTime = endTime;
    }
    
    @Column(name = "CODE")
    public String getCode()
    {
        return code;
    }
    
    public void setCode(String code)
    {
        this.code = code;
    }
    
    @Column(name = "DETAILS")
    public String getDetails()
    {
        return details;
    }
    
    public void setDetails(String details)
    {
        this.details = details;
    }
    
    @Column(name = "STATUS")
    public Integer getStatus()
    {
        return status;
    }
    
    public void setStatus(Integer status)
    {
        this.status = status;
    }
    
    @Column(name = "END_TYPE")
    public Integer getEndType()
    {
        return endType;
    }
    
    public void setEndType(Integer endType)
    {
        this.endType = endType;
    }
    
    @Column(name = "PAUSE_NAME")
    public String getPauseName()
    {
        return pauseName;
    }
    
    public void setPauseName(String pauseName)
    {
        this.pauseName = pauseName;
    }
    
    @Column(name = "PAUSE_TIME")
    public Date getPauseTime()
    {
        return pauseTime;
    }
    
    public void setPauseTime(Date pauseTime)
    {
        this.pauseTime = pauseTime;
    }
    
    @Column(name = "RESTART_NAME")
    public String getRestartName()
    {
        return restartName;
    }
    
    public void setRestartName(String restartName)
    {
        this.restartName = restartName;
    }
    
    @Column(name = "RESTART_TIME")
    public Date getRestartTime()
    {
        return restartTime;
    }
    
    public void setRestartTime(Date restartTime)
    {
        this.restartTime = restartTime;
    }
    
    @Column(name = "REMARK")
    public String getRemark()
    {
        return remark;
    }
    
    public void setRemark(String remark)
    {
        this.remark = remark;
    }
    
    @Column(name = "VERIFY_CHR_POSITION")
    public String getVerifyChrPosition()
    {
        return verifyChrPosition;
    }
    
    public void setVerifyChrPosition(String verifyChrPosition)
    {
        this.verifyChrPosition = verifyChrPosition;
    }
}
