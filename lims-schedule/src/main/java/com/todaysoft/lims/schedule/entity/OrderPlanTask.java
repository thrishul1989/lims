package com.todaysoft.lims.schedule.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_SCHEDULE_PLAN_TASK")
public class OrderPlanTask extends UuidKeyEntity
{
    public static final String SEMANTIC_SAMPLE_RECEIVE = "SAMPLE_RECEIVE";
    
    public static final String SEMANTIC_SAMPLE_STORAGE = "SAMPLE_STORAGE";
    
    public static final String SEMANTIC_PAYMENT_CONFIRM = "PAYMENT_CONFIRM";
    
    public static final String SEMANTIC_REPORT_GENERATE = "REPORT_GENERATE";
    
    public static final String SEMANTIC_REPORT_VERIFY = "REPORT_VERIFY";
    
    public static final String SEMANTIC_REPORT_DELIVER = "REPORT_DELIVER";
    
    public static final String SEMANTIC_PRODUCT_GROUP = "GROUP_PRODUCT";
    
    public static final String SEMANTIC_PRODUCT_GROUP_PRODUCT_SAMPLE_METHOD = "GROUP_PRODUCT_SAMPLE_METHOD";
    
    private static final long serialVersionUID = 1165154035246688447L;
    
    private String parentId;
    
    private String orderId;
    
    private String productId;
    
    private String sampleId;
    
    private String testingMethodId;
    
    private String verifyId;
    
    private String taskSemantic;
    
    private String taskName;
    
    private Date plannedStartDate;
    
    private Date plannedFinishDate;
    
    private Date actualStartDate;
    
    private Date actualFinishDate;
    
    private boolean actived;
    
    private boolean started;
    
    private boolean finished;
    
    private boolean canceled;
    
    private boolean grouped;
    
    private boolean postponed;
    
    private Integer postponedDays;
    
    @Column(name = "PARENT_ID")
    public String getParentId()
    {
        return parentId;
    }
    
    public void setParentId(String parentId)
    {
        this.parentId = parentId;
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
    
    @Column(name = "SAMPLE_ID")
    public String getSampleId()
    {
        return sampleId;
    }
    
    public void setSampleId(String sampleId)
    {
        this.sampleId = sampleId;
    }
    
    @Column(name = "TESTING_METHOD_ID")
    public String getTestingMethodId()
    {
        return testingMethodId;
    }
    
    public void setTestingMethodId(String testingMethodId)
    {
        this.testingMethodId = testingMethodId;
    }
    
    @Column(name = "VERIFY_ID")
    public String getVerifyId()
    {
        return verifyId;
    }
    
    public void setVerifyId(String verifyId)
    {
        this.verifyId = verifyId;
    }
    
    @Column(name = "TASK_SEMANTIC")
    public String getTaskSemantic()
    {
        return taskSemantic;
    }
    
    public void setTaskSemantic(String taskSemantic)
    {
        this.taskSemantic = taskSemantic;
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
    
    @Column(name = "PLANNED_START_DATE")
    @Temporal(TemporalType.DATE)
    public Date getPlannedStartDate()
    {
        return plannedStartDate;
    }
    
    public void setPlannedStartDate(Date plannedStartDate)
    {
        this.plannedStartDate = plannedStartDate;
    }
    
    @Column(name = "PLANNED_FINISH_DATE")
    @Temporal(TemporalType.DATE)
    public Date getPlannedFinishDate()
    {
        return plannedFinishDate;
    }
    
    public void setPlannedFinishDate(Date plannedFinishDate)
    {
        this.plannedFinishDate = plannedFinishDate;
    }
    
    @Column(name = "ACTUAL_START_DATE")
    @Temporal(TemporalType.DATE)
    public Date getActualStartDate()
    {
        return actualStartDate;
    }
    
    public void setActualStartDate(Date actualStartDate)
    {
        this.actualStartDate = actualStartDate;
    }
    
    @Column(name = "ACTUAL_FINISH_DATE")
    @Temporal(TemporalType.DATE)
    public Date getActualFinishDate()
    {
        return actualFinishDate;
    }
    
    public void setActualFinishDate(Date actualFinishDate)
    {
        this.actualFinishDate = actualFinishDate;
    }
    
    @Column(name = "ACTIVED")
    public boolean isActived()
    {
        return actived;
    }
    
    public void setActived(boolean actived)
    {
        this.actived = actived;
    }
    
    @Column(name = "STARTED")
    public boolean isStarted()
    {
        return started;
    }
    
    public void setStarted(boolean started)
    {
        this.started = started;
    }
    
    @Column(name = "FINISHED")
    public boolean isFinished()
    {
        return finished;
    }
    
    public void setFinished(boolean finished)
    {
        this.finished = finished;
    }
    
    @Column(name = "CANCELED")
    public boolean isCanceled()
    {
        return canceled;
    }
    
    public void setCanceled(boolean canceled)
    {
        this.canceled = canceled;
    }
    
    @Column(name = "GROUPED")
    public boolean isGrouped()
    {
        return grouped;
    }
    
    public void setGrouped(boolean grouped)
    {
        this.grouped = grouped;
    }
    
    @Column(name = "POSTPONED")
    public boolean isPostponed()
    {
        return postponed;
    }
    
    public void setPostponed(boolean postponed)
    {
        this.postponed = postponed;
    }
    
    @Column(name = "POSTPONED_DAYS")
    public Integer getPostponedDays()
    {
        return postponedDays;
    }
    
    public void setPostponedDays(Integer postponedDays)
    {
        this.postponedDays = postponedDays;
    }
}
