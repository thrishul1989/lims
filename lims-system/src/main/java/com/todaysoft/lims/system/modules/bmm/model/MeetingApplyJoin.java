package com.todaysoft.lims.system.modules.bmm.model;

public class MeetingApplyJoin
{
    private String id;
    
    private String personType;//参会人员(字典)
    
    private Integer personAmount;//数量
    
    private MeetingApply meetingApply;//会议申请
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getPersonType()
    {
        return personType;
    }
    
    public void setPersonType(String personType)
    {
        this.personType = personType;
    }
    
    public Integer getPersonAmount()
    {
        return personAmount;
    }
    
    public void setPersonAmount(Integer personAmount)
    {
        this.personAmount = personAmount;
    }
    
    public MeetingApply getMeetingApply()
    {
        return meetingApply;
    }
    
    public void setMeetingApply(MeetingApply meetingApply)
    {
        this.meetingApply = meetingApply;
    }
    
}
