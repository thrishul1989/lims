package com.todaysoft.lims.smm.request;

import com.todaysoft.lims.smm.entity.User;

public class UserGroupCreateRequest
{
    private String groupName;
    
    private String remark;
    
    private User groupLeader;
    
    private String groupLeaderName;
    
    private String userGroupMemberIds;
    
    public String getGroupName()
    {
        return groupName;
    }
    
    public void setGroupName(String groupName)
    {
        this.groupName = groupName;
    }
    
    public String getRemark()
    {
        return remark;
    }
    
    public void setRemark(String remark)
    {
        this.remark = remark;
    }
    
    public User getGroupLeader()
    {
        return groupLeader;
    }
    
    public void setGroupLeader(User groupLeader)
    {
        this.groupLeader = groupLeader;
    }
    
    public String getGroupLeaderName()
    {
        return groupLeaderName;
    }
    
    public void setGroupLeaderName(String groupLeaderName)
    {
        this.groupLeaderName = groupLeaderName;
    }
    
    public String getUserGroupMemberIds()
    {
        return userGroupMemberIds;
    }
    
    public void setUserGroupMemberIds(String userGroupMemberIds)
    {
        this.userGroupMemberIds = userGroupMemberIds;
    }
    
}
