package com.todaysoft.lims.schedule.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_ORDER_REDUCE_CHECK")
public class OrderReduceApproveRecord extends UuidKeyEntity
{
    private static final long serialVersionUID = -7635160099079068309L;
    
    public static final String STATUS_ARGEED = "1";
    
    private OrderReduceApplyRecord applyRecord;
    
    private String status;
    
    private String approverName;
    
    private Date approveTime;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "APPLY_ID")
    public OrderReduceApplyRecord getApplyRecord()
    {
        return applyRecord;
    }
    
    public void setApplyRecord(OrderReduceApplyRecord applyRecord)
    {
        this.applyRecord = applyRecord;
    }
    
    @Column(name = "STATUS")
    public String getStatus()
    {
        return status;
    }
    
    public void setStatus(String status)
    {
        this.status = status;
    }
    
    @Column(name = "CHECKER_NAME")
    public String getApproverName()
    {
        return approverName;
    }
    
    public void setApproverName(String approverName)
    {
        this.approverName = approverName;
    }
    
    @Column(name = "CHECKER_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getApproveTime()
    {
        return approveTime;
    }
    
    public void setApproveTime(Date approveTime)
    {
        this.approveTime = approveTime;
    }
}
