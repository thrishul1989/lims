package com.todaysoft.lims.system.modules.bmm.model;

import java.util.Date;

import com.todaysoft.lims.system.model.vo.BusinessInfo;

public class MeetingApplyCheck
{
    
    private String id;
    
    private MeetingApply meetingApply;//申请ID
    
    private String remark;//审核意见
    
    private Integer status;//审核状态（0：待审核；1：审核通过；2：审核不通过）
    
    private BusinessInfo businessInfo;//审核人ID
    
    private String checkName;//审核人姓名
    
    private Date checkTime;//审核时间
    
    private String statusName;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public MeetingApply getMeetingApply()
    {
        return meetingApply;
    }
    
    public void setMeetingApply(MeetingApply meetingApply)
    {
        this.meetingApply = meetingApply;
    }
    
    public String getRemark()
    {
        return remark;
    }
    
    public void setRemark(String remark)
    {
        this.remark = remark;
    }
    
    public Integer getStatus()
    {
        return status;
    }
    
    public void setStatus(Integer status)
    {
        this.status = status;
    }
    
    public BusinessInfo getBusinessInfo()
    {
        return businessInfo;
    }
    
    public void setBusinessInfo(BusinessInfo businessInfo)
    {
        this.businessInfo = businessInfo;
    }
    
    public String getCheckName()
    {
        return checkName;
    }
    
    public void setCheckName(String checkName)
    {
        this.checkName = checkName;
    }
    
    public Date getCheckTime()
    {
        return checkTime;
    }
    
    public void setCheckTime(Date checkTime)
    {
        this.checkTime = checkTime;
    }
    
    public String getStatusName()
    {
        return statusName;
    }
    
    public void setStatusName(String statusName)
    {
        this.statusName = statusName;
    }
    
}
