package com.todaysoft.lims.sample.entity.meeting;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.todaysoft.lims.persist.UuidKeyEntity;
import com.todaysoft.lims.sample.entity.contract.BusinessInfo;

@Entity
@Table(name = "LIMS_MEETING_APPLY_CHECK")
public class MeetingApplyCheck extends UuidKeyEntity
{
    
    private static final long serialVersionUID = 1L;
    
    private MeetingApply meetingApply;//申请ID
    
    private String remark;//审核意见
    
    private Integer status;//审核状态（0：待审核；1：审核通过；2：审核不通过）
    
    private BusinessInfo businessInfo;//审核人ID
    
    private String checkName;//审核人姓名
    
    private Date checkTime;//审核时间
    
    private String statusName;
    
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "APPLY_ID")
    @JsonIgnore
    public MeetingApply getMeetingApply()
    {
        return meetingApply;
    }
    
    public void setMeetingApply(MeetingApply meetingApply)
    {
        this.meetingApply = meetingApply;
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
    
    @Column(name = "STATUS")
    public Integer getStatus()
    {
        return status;
    }
    
    public void setStatus(Integer status)
    {
        this.status = status;
    }
    
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CHECKER_ID")
    @JsonIgnore
    public BusinessInfo getBusinessInfo()
    {
        return businessInfo;
    }
    
    public void setBusinessInfo(BusinessInfo businessInfo)
    {
        this.businessInfo = businessInfo;
    }
    
    @Column(name = "CHECKER_NAME")
    public String getCheckName()
    {
        return checkName;
    }
    
    public void setCheckName(String checkName)
    {
        this.checkName = checkName;
    }
    
    @Column(name = "CHECKER_TIME")
    public Date getCheckTime()
    {
        return checkTime;
    }
    
    public void setCheckTime(Date checkTime)
    {
        this.checkTime = checkTime;
    }
    
    @Transient
    public String getStatusName()
    {
        return statusName;
    }
    
    public void setStatusName(String statusName)
    {
        this.statusName = statusName;
    }
    
}
