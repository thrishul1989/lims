package com.todaysoft.lims.schedule.entity;

import com.todaysoft.lims.persist.UuidKeyEntity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "LIMS_TESTING_TASK")
public class TestingTask extends UuidKeyEntity
{
    public static final int STATUS_ASSIGNABLE = 1;
    
    public static final int STATUS_ASSIGNING = 2;
    
    public static final int STATUS_END = 3;
    
    public static final int END_FAILURE = 0;
    
    public static final int END_SUCCESS = 1;
    
    private static final long serialVersionUID = 1611626719186985427L;
    
    private String name;
    
    private String semantic;
    
    private Date startTime;
    
    private Integer status;//任务状态：1-待分配 2-待实验 3-已结束
    
    private Date endTime;
    
    private Integer endType;
    
    private boolean resubmit;
    
    private Integer resubmitCount;//重做次数RESUBMIT_COUNT
    
    private Integer ifUrgent;
    
    private Date urgentUpdateTime;
    
    private String urgentName;
    
    private Date plannedFinishDate;
    
    // 结构优化-字段冗余

    @Column(name = "NAME")
    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    @Column(name = "SEMANTIC")
    public String getSemantic()
    {
        return semantic;
    }

    public void setSemantic(String semantic)
    {
        this.semantic = semantic;
    }

    @Column(name = "START_TIME")
    public Date getStartTime()
    {
        return startTime;
    }

    public void setStartTime(Date startTime)
    {
        this.startTime = startTime;
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

    @Column(name = "END_TIME")
    public Date getEndTime()
    {
        return endTime;
    }

    public void setEndTime(Date endTime)
    {
        this.endTime = endTime;
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

    @Column(name = "RESUBMIT")
    public boolean isResubmit()
    {
        return resubmit;
    }

    public void setResubmit(boolean resubmit)
    {
        this.resubmit = resubmit;
    }

    @Column(name = "RESUBMIT_COUNT")
    public Integer getResubmitCount()
    {
        return resubmitCount;
    }

    public void setResubmitCount(Integer resubmitCount)
    {
        this.resubmitCount = resubmitCount;
    }

    @Column(name="IF_URGENT")
    public Integer getIfUrgent()
    {
        return ifUrgent;
    }

    public void setIfUrgent(Integer ifUrgent)
    {
        this.ifUrgent = ifUrgent;
    }

    @Column(name="URGENT_UPDATE_TIME")
    public Date getUrgentUpdateTime()
    {
        return urgentUpdateTime;
    }

    public void setUrgentUpdateTime(Date urgentUpdateTime)
    {
        this.urgentUpdateTime = urgentUpdateTime;
    }

    @Column(name="URGENT_NAME")
    public String getUrgentName()
    {
        return urgentName;
    }

    public void setUrgentName(String urgentName)
    {
        this.urgentName = urgentName;
    }
    
    @Column(name = "PLANNED_FINISH_DATE")
    public Date getPlannedFinishDate()
    {
        return plannedFinishDate;
    }

    public void setPlannedFinishDate(Date plannedFinishDate)
    {
        this.plannedFinishDate = plannedFinishDate;
    }
}
