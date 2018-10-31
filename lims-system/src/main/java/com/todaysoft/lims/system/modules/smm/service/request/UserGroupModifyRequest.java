package com.todaysoft.lims.system.modules.smm.service.request;

import com.todaysoft.lims.system.model.vo.User;

public class UserGroupModifyRequest
{
    private String id;
    
    private String groupName;
    
    private User groupLeader;
    
    private String groupLeaderName;
    
    private String remark;
    
    private String userGroupMemberIds;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getGroupName()
    {
        return groupName;
    }
    
    public void setGroupName(String groupName)
    {
        this.groupName = groupName;
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
    
    public String getRemark()
    {
        return remark;
    }
    
    public void setRemark(String remark)
    {
        this.remark = remark;
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
