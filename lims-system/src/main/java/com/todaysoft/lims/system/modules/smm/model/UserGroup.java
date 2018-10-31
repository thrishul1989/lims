package com.todaysoft.lims.system.modules.smm.model;

import java.util.Date;
import java.util.List;

import com.todaysoft.lims.system.model.vo.User;

public class UserGroup
{
    private String id;
    
    private String groupName;
    
    private User groupLeader;
    
    private String groupLeaderName;
    
    private String remark;
    
    private List<UserGroupMember> userGroupMembers;
    
    private Date createTime;
    
    private String name;
    
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
    
    public List<UserGroupMember> getUserGroupMembers()
    {
        return userGroupMembers;
    }
    
    public void setUserGroupMembers(List<UserGroupMember> userGroupMembers)
    {
        this.userGroupMembers = userGroupMembers;
    }
    
    public Date getCreateTime()
    {
        return createTime;
    }
    
    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
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
