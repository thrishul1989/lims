package com.todaysoft.lims.system.modules.bmm.model;

public class MeetingDispatchUser
{
    
    private String id;
    
    private MeetingApply meetingApply;//会议
    
    private String userId;//派遣人员
    
    private String name;//派遣人员姓名
    
    public MeetingApply getMeetingApply()
    {
        return meetingApply;
    }
    
    public void setMeetingApply(MeetingApply meetingApply)
    {
        this.meetingApply = meetingApply;
    }
    
    public String getUserId()
    {
        return userId;
    }
    
    public void setUserId(String userId)
    {
        this.userId = userId;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
}
