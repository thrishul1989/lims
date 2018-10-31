package com.todaysoft.lims.sample.entity.meeting;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.*;

import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_MEETING_APPLY")
public class MeetingApply extends UuidKeyEntity
{
    private static final long serialVersionUID = 1L;
    
    private String meetingType; //会议类型,
    
    private String supportDept;//支持部门,
    
    private Date meetingTime;//会议时间
    
    private String meetingLocation; //会议地点,
    
    private String meetingHost;// 主持人,
    
    private String meetingTheme;// 会议主题,
    
    private String carryingMaterial;// 携带材料,
    
    private String remark;//备注,
    
    private String intentionCustomer;//意向客户,
    
    private String presentationContent;// 建议宣讲内容及时间,
    
    private Integer status;//审核状态
    
    private String createId;//申请人ID
    
    private String createName;//申请人姓名
    
    private String createDept;//申请人部门
    
    private Date applyTime;//申请时间
    
    private Date updateTime;//更新时间
    
    private String feedbackContent;//反馈内容
    
    private List<MeetingApplyJoin> meetingApplyJoin;//
    
    private List<MeetingApplyCheck> meetingApplyCheck; //
    
    private List<MeetingDispatchUser> meetingDispatchUsers;//
    
    private String businessName;
    
    private String checkName;
    
    private String statusName;
    
    private String testingType;
    
    private String preachContent;//会议响应宣讲内容
    
    private String code;
    
    private String roleType;//0非经理，1经理；
    
    @Column(name = "CODE")
    public String getCode()
    {
        return code;
    }
    
    public void setCode(String code)
    {
        this.code = code;
    }
    
    @Column(name = "MEETING_TYPE")
    public String getMeetingType()
    {
        return meetingType;
    }
    
    public void setMeetingType(String meetingType)
    {
        this.meetingType = meetingType;
    }
    
    @Column(name = "SUPPORT_DEPT")
    public String getSupportDept()
    {
        return supportDept;
    }
    
    public void setSupportDept(String supportDept)
    {
        this.supportDept = supportDept;
    }
    
    @Column(name = "MEETING_TIME")
    public Date getMeetingTime()
    {
        return meetingTime;
    }
    
    public void setMeetingTime(Date meetingTime)
    {
        this.meetingTime = meetingTime;
    }
    
    @Column(name = "MEETING_LOCATION")
    public String getMeetingLocation()
    {
        return meetingLocation;
    }
    
    public void setMeetingLocation(String meetingLocation)
    {
        this.meetingLocation = meetingLocation;
    }
    
    @Column(name = "MEETING_HOST")
    public String getMeetingHost()
    {
        return meetingHost;
    }
    
    public void setMeetingHost(String meetingHost)
    {
        this.meetingHost = meetingHost;
    }
    
    @Column(name = "MEETING_THEME")
    public String getMeetingTheme()
    {
        return meetingTheme;
    }
    
    public void setMeetingTheme(String meetingTheme)
    {
        this.meetingTheme = meetingTheme;
    }
    
    @Column(name = "CARRYING_MATERIAL")
    public String getCarryingMaterial()
    {
        return carryingMaterial;
    }
    
    public void setCarryingMaterial(String carryingMaterial)
    {
        this.carryingMaterial = carryingMaterial;
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
    
    @Column(name = "INTENTION_CUSTOMER")
    public String getIntentionCustomer()
    {
        return intentionCustomer;
    }
    
    public void setIntentionCustomer(String intentionCustomer)
    {
        this.intentionCustomer = intentionCustomer;
    }
    
    @Column(name = "PRESENTATION_CONTENT")
    public String getPresentationContent()
    {
        return presentationContent;
    }
    
    public void setPresentationContent(String presentationContent)
    {
        this.presentationContent = presentationContent;
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
    
    @Column(name = "CREATOR_ID")
    public String getCreateId()
    {
        return createId;
    }
    
    public void setCreateId(String createId)
    {
        this.createId = createId;
    }
    
    @Column(name = "CREATOR_NAME")
    public String getCreateName()
    {
        return createName;
    }
    
    public void setCreateName(String createName)
    {
        this.createName = createName;
    }
    
    @Column(name = "CREATOR_DEPT")
    public String getCreateDept()
    {
        return createDept;
    }
    
    public void setCreateDept(String createDept)
    {
        this.createDept = createDept;
    }
    
    @Column(name = "APPLY_TIME")
    public Date getApplyTime()
    {
        return applyTime;
    }
    
    public void setApplyTime(Date applyTime)
    {
        this.applyTime = applyTime;
    }
    
    @Column(name = "UPDATE_TIME")
    public Date getUpdateTime()
    {
        return updateTime;
    }
    
    public void setUpdateTime(Date updateTime)
    {
        this.updateTime = updateTime;
    }
    
    @Column(name = "FEEDBACK_CONTENT")
    public String getFeedbackContent()
    {
        return feedbackContent;
    }
    
    public void setFeedbackContent(String feedbackContent)
    {
        this.feedbackContent = feedbackContent;
    }
    
    @OneToMany(mappedBy = "meetingApply", cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
    @Fetch(FetchMode.SUBSELECT)
    public List<MeetingApplyJoin> getMeetingApplyJoin()
    {
        return meetingApplyJoin;
    }
    
    public void setMeetingApplyJoin(List<MeetingApplyJoin> meetingApplyJoin)
    {
        this.meetingApplyJoin = meetingApplyJoin;
    }
    
    @OneToMany(mappedBy = "meetingApply", cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
    @Fetch(FetchMode.SUBSELECT)
    @OrderBy(clause = "checkTime asc")
    public List<MeetingApplyCheck> getMeetingApplyCheck()
    {
        return meetingApplyCheck;
    }
    
    public void setMeetingApplyCheck(List<MeetingApplyCheck> meetingApplyCheck)
    {
        this.meetingApplyCheck = meetingApplyCheck;
    }
    
    @Transient
    public String getBusinessName()
    {
        return businessName;
    }
    
    public void setBusinessName(String businessName)
    {
        this.businessName = businessName;
    }
    
    @Transient
    public String getCheckName()
    {
        return checkName;
    }
    
    public void setCheckName(String checkName)
    {
        this.checkName = checkName;
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
    
    @Transient
    public String getTestingType()
    {
        return testingType;
    }
    
    public void setTestingType(String testingType)
    {
        this.testingType = testingType;
    }
    
    @OneToMany(mappedBy = "meetingApply", cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
    @Fetch(FetchMode.SUBSELECT)
    public List<MeetingDispatchUser> getMeetingDispatchUsers()
    {
        return meetingDispatchUsers;
    }
    
    public void setMeetingDispatchUsers(List<MeetingDispatchUser> meetingDispatchUsers)
    {
        this.meetingDispatchUsers = meetingDispatchUsers;
    }
    
    @Column(name = "RSP_PREACH_CONTENT")
    public String getPreachContent()
    {
        return preachContent;
    }
    
    public void setPreachContent(String preachContent)
    {
        this.preachContent = preachContent;
    }
    
    @Transient
    public String getRoleType()
    {
        return roleType;
    }
    
    public void setRoleType(String roleType)
    {
        this.roleType = roleType;
    }
    
}
