package com.todaysoft.lims.maintain.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_TESTING_SCHEDULE")
public class TestingSchedule extends UuidKeyEntity implements Serializable
{
    private static final long serialVersionUID = 2312730608024695169L;
    
    private String orderId;
    
    private String productId;
    
    private String methodId;
    
    private String sampleId;
    
    private String verifyKey;
    
    private String activeTask;
    
    private String scheduleNodes;
    
    private Date endTime;
    
    private String endType;
    
    private String orderProductId;
    
    private String verifyTarget;
    
    private Integer proccessState;//0正常 1已暂停 2已取消
    
    private Date startTime;
    
    @Column(name = "START_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getStartTime()
    {
        return startTime;
    }
    
    public void setStartTime(Date startTime)
    {
        this.startTime = startTime;
    }
    
    @Column(name = "SCHEDULE_NODES")
    public String getScheduleNodes()
    {
        return scheduleNodes;
    }
    
    public void setScheduleNodes(String scheduleNodes)
    {
        this.scheduleNodes = scheduleNodes;
    }
    
    @Column(name = "PROCCESS_STATE", columnDefinition = "tinyint(1)default 0")
    public Integer getProccessState()
    {
        return proccessState;
    }
    
    public void setProccessState(Integer proccessState)
    {
        this.proccessState = proccessState;
    }
    
    @Column(name = "ACTIVE_TASK")
    public String getActiveTask()
    {
        return activeTask;
    }
    
    public void setActiveTask(String activeTask)
    {
        this.activeTask = activeTask;
    }
    
    @Transient
    public String getOrderProductId()
    {
        return orderProductId;
    }
    
    public void setOrderProductId(String orderProductId)
    {
        this.orderProductId = orderProductId;
    }
    
    @Column(name = "END_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getEndTime()
    {
        return endTime;
    }
    
    public void setEndTime(Date endTime)
    {
        this.endTime = endTime;
    }
    
    @Column(name = "END_TYPE")
    public String getEndType()
    {
        return endType;
    }
    
    public void setEndType(String endType)
    {
        this.endType = endType;
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
    
    @Column(name = "PRODUCT_ID")
    public String getProductId()
    {
        return productId;
    }
    
    public void setProductId(String productId)
    {
        this.productId = productId;
    }
    
    @Column(name = "METHOD_ID")
    public String getMethodId()
    {
        return methodId;
    }
    
    public void setMethodId(String methodId)
    {
        this.methodId = methodId;
    }
    
    @Column(name = "SAMPLE_ID")
    public String getSampleId()
    {
        return sampleId;
    }
    
    public void setSampleId(String sampleId)
    {
        this.sampleId = sampleId;
    }
    
    @Column(name = "VERIFY_KEY")
    public String getVerifyKey()
    {
        return verifyKey;
    }
    
    public void setVerifyKey(String verifyKey)
    {
        this.verifyKey = verifyKey;
    }
    
    @Column(name = "VERIFY_TARGET")
    public String getVerifyTarget()
    {
        return verifyTarget;
    }
    
    public void setVerifyTarget(String verifyTarget)
    {
        this.verifyTarget = verifyTarget;
    }
}
