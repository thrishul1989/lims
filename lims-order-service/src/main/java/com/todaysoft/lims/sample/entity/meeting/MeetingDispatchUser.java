package com.todaysoft.lims.sample.entity.meeting;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_MEETING_DISPATCH_USER")
public class MeetingDispatchUser extends UuidKeyEntity
{
    
    private static final long serialVersionUID = 1L;
    
    private MeetingApply meetingApply;//会议
    
    private String userId;//派遣人员
    
    private String name;//派遣人员姓名
    
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
    
    @Column(name = "USER_NAME")
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    @Column(name = "USER_ID")
    public String getUserId()
    {
        return userId;
    }
    
    public void setUserId(String userId)
    {
        this.userId = userId;
    }
    
}
