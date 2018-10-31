package com.todaysoft.lims.system.modules.bmm.model.request;

import java.util.Date;

public class MeetingApplySearcher
{
    private int pageNo;
    
    private int pageSize;
    
    private Integer status;
    
    private String supportDept;
    
    private String createName;
    
    private Date meetingTimeStart;
    
    private Date meetingTimeEnd;
    
    private Date applyTime;//'申请时间',
    
    private Date applyEndTime;//'额外字段',
    
    public int getPageNo()
    {
        return pageNo;
    }
    
    public void setPageNo(int pageNo)
    {
        this.pageNo = pageNo;
    }
    
    public int getPageSize()
    {
        return pageSize;
    }
    
    public void setPageSize(int pageSize)
    {
        this.pageSize = pageSize;
    }
    
    public Integer getStatus()
    {
        return status;
    }
    
    public void setStatus(Integer status)
    {
        this.status = status;
    }
    
    public String getSupportDept()
    {
        return supportDept;
    }
    
    public void setSupportDept(String supportDept)
    {
        this.supportDept = supportDept;
    }
    
    public String getCreateName()
    {
        return createName;
    }
    
    public void setCreateName(String createName)
    {
        this.createName = createName;
    }
    
    public Date getApplyTime()
    {
        return applyTime;
    }
    
    public void setApplyTime(Date applyTime)
    {
        this.applyTime = applyTime;
    }
    
    public Date getApplyEndTime()
    {
        return applyEndTime;
    }
    
    public void setApplyEndTime(Date applyEndTime)
    {
        this.applyEndTime = applyEndTime;
    }
    
    public Date getMeetingTimeStart()
    {
        return meetingTimeStart;
    }
    
    public void setMeetingTimeStart(Date meetingTimeStart)
    {
        this.meetingTimeStart = meetingTimeStart;
    }
    
    public Date getMeetingTimeEnd()
    {
        return meetingTimeEnd;
    }
    
    public void setMeetingTimeEnd(Date meetingTimeEnd)
    {
        this.meetingTimeEnd = meetingTimeEnd;
    }
    
}
