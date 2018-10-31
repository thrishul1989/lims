package com.todaysoft.lims.system.modules.bmm.model;

import java.util.Date;
import java.util.List;

public class MeetingApply
{
    private String id;
    
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
    
    private String preachContent;//
    
    private List<MeetingApplyJoin> meetingApplyJoin;//
    
    private List<MeetingApplyCheck> meetingApplyCheck;
    
    private List<MeetingDispatchUser> meetingDispatchUsers;//
    
    private String businessName;
    
    private String checkName;
    
    private String statusName;
    
    private String testingType;
    
    private String code;
    
    private String roleType;//0非经理，1经理；
    
    public String getCode()
    {
        return code;
    }
    
    public void setCode(String code)
    {
        this.code = code;
    }
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getMeetingType()
    {
        return meetingType;
    }
    
    public void setMeetingType(String meetingType)
    {
        this.meetingType = meetingType;
    }
    
    public String getSupportDept()
    {
        return supportDept;
    }
    
    public void setSupportDept(String supportDept)
    {
        this.supportDept = supportDept;
    }
    
    public Date getMeetingTime()
    {
        return meetingTime;
    }
    
    public void setMeetingTime(Date meetingTime)
    {
        this.meetingTime = meetingTime;
    }
    
    public String getMeetingLocation()
    {
        return meetingLocation;
    }
    
    public void setMeetingLocation(String meetingLocation)
    {
        this.meetingLocation = meetingLocation;
    }
    
    public String getMeetingHost()
    {
        return meetingHost;
    }
    
    public void setMeetingHost(String meetingHost)
    {
        this.meetingHost = meetingHost;
    }
    
    public String getMeetingTheme()
    {
        return meetingTheme;
    }
    
    public void setMeetingTheme(String meetingTheme)
    {
        this.meetingTheme = meetingTheme;
    }
    
    public String getCarryingMaterial()
    {
        return carryingMaterial;
    }
    
    public void setCarryingMaterial(String carryingMaterial)
    {
        this.carryingMaterial = carryingMaterial;
    }
    
    public String getRemark()
    {
        return remark;
    }
    
    public void setRemark(String remark)
    {
        this.remark = remark;
    }
    
    public String getIntentionCustomer()
    {
        return intentionCustomer;
    }
    
    public void setIntentionCustomer(String intentionCustomer)
    {
        this.intentionCustomer = intentionCustomer;
    }
    
    public String getPresentationContent()
    {
        return presentationContent;
    }
    
    public void setPresentationContent(String presentationContent)
    {
        this.presentationContent = presentationContent;
    }
    
    public Integer getStatus()
    {
        return status;
    }
    
    public void setStatus(Integer status)
    {
        this.status = status;
    }
    
    public String getCreateId()
    {
        return createId;
    }
    
    public void setCreateId(String createId)
    {
        this.createId = createId;
    }
    
    public String getCreateName()
    {
        return createName;
    }
    
    public void setCreateName(String createName)
    {
        this.createName = createName;
    }
    
    public String getCreateDept()
    {
        return createDept;
    }
    
    public void setCreateDept(String createDept)
    {
        this.createDept = createDept;
    }
    
    public Date getApplyTime()
    {
        return applyTime;
    }
    
    public void setApplyTime(Date applyTime)
    {
        this.applyTime = applyTime;
    }
    
    public Date getUpdateTime()
    {
        return updateTime;
    }
    
    public void setUpdateTime(Date updateTime)
    {
        this.updateTime = updateTime;
    }
    
    public String getFeedbackContent()
    {
        return feedbackContent;
    }
    
    public void setFeedbackContent(String feedbackContent)
    {
        this.feedbackContent = feedbackContent;
    }
    
    public List<MeetingApplyJoin> getMeetingApplyJoin()
    {
        return meetingApplyJoin;
    }
    
    public void setMeetingApplyJoin(List<MeetingApplyJoin> meetingApplyJoin)
    {
        this.meetingApplyJoin = meetingApplyJoin;
    }
    
    public List<MeetingApplyCheck> getMeetingApplyCheck()
    {
        return meetingApplyCheck;
    }
    
    public void setMeetingApplyCheck(List<MeetingApplyCheck> meetingApplyCheck)
    {
        this.meetingApplyCheck = meetingApplyCheck;
    }
    
    public String getBusinessName()
    {
        return businessName;
    }
    
    public void setBusinessName(String businessName)
    {
        this.businessName = businessName;
    }
    
    public String getCheckName()
    {
        return checkName;
    }
    
    public void setCheckName(String checkName)
    {
        this.checkName = checkName;
    }
    
    public String getStatusName()
    {
        return statusName;
    }
    
    public void setStatusName(String statusName)
    {
        this.statusName = statusName;
    }
    
    public String getTestingType()
    {
        return testingType;
    }
    
    public void setTestingType(String testingType)
    {
        this.testingType = testingType;
    }
    
    public List<MeetingDispatchUser> getMeetingDispatchUsers()
    {
        return meetingDispatchUsers;
    }
    
    public void setMeetingDispatchUsers(List<MeetingDispatchUser> meetingDispatchUsers)
    {
        this.meetingDispatchUsers = meetingDispatchUsers;
    }
    
    public String getPreachContent()
    {
        return preachContent;
    }
    
    public void setPreachContent(String preachContent)
    {
        this.preachContent = preachContent;
    }
    
    public String getRoleType()
    {
        return roleType;
    }
    
    public void setRoleType(String roleType)
    {
        this.roleType = roleType;
    }
    
}
