package com.todaysoft.lims.sample.entity.meeting;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_MEETING_APPLY_JOIN")
public class MeetingApplyJoin extends UuidKeyEntity
{
    
    private static final long serialVersionUID = 1L;
    
    private String personType;//参会人员(字典)
    
    private Integer personAmount;//数量
    
    private MeetingApply meetingApply;//会议申请
    
    @Column(name = "PERSON_TYPE")
    public String getPersonType()
    {
        return personType;
    }
    
    public void setPersonType(String personType)
    {
        this.personType = personType;
    }
    
    @Column(name = "PERSON_AMOUNT")
    public Integer getPersonAmount()
    {
        return personAmount;
    }
    
    public void setPersonAmount(Integer personAmount)
    {
        this.personAmount = personAmount;
    }
    
    @ManyToOne(fetch = FetchType.LAZY)
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
    
}
