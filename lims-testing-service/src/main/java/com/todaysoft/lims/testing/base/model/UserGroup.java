package com.todaysoft.lims.testing.base.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.todaysoft.lims.testing.base.entity.User;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_USER_GROUP")
public class UserGroup extends UuidKeyEntity
{
    private static final long serialVersionUID = 1L;
    
    private String groupName;
    
    private User groupLeader;//组长
    
    private String groupLeaderName;
    
    private String remark;
    
    private Date createTime;//创建时间
    
    private Date deleteTime;//删除时间
    
    private List<UserGroupMember> userGroupMembers;
    
    private boolean deleted;
    
    @Column(name = "GROUP_NAME")
    public String getGroupName()
    {
        return groupName;
    }
    
    public void setGroupName(String groupName)
    {
        this.groupName = groupName;
    }
    
    @JoinColumn(name = "GROUP_LEADER_ID")
    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @NotFound(action = NotFoundAction.IGNORE)
    public User getGroupLeader()
    {
        return groupLeader;
    }
    
    public void setGroupLeader(User groupLeader)
    {
        this.groupLeader = groupLeader;
    }
    
    @Column(name = "GROUP_LEADER_NAME")
    public String getGroupLeaderName()
    {
        return groupLeaderName;
    }
    
    public void setGroupLeaderName(String groupLeaderName)
    {
        this.groupLeaderName = groupLeaderName;
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
    
    @Column(name = "CREATE_TIME")
    public Date getCreateTime()
    {
        return createTime;
    }
    
    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }
    
    @Column(name = "DELETE_TIME")
    public Date getDeleteTime()
    {
        return deleteTime;
    }
    
    public void setDeleteTime(Date deleteTime)
    {
        this.deleteTime = deleteTime;
    }
    
    @Column(name = "DEL_FLAG")
    public boolean isDeleted()
    {
        return deleted;
    }
    
    public void setDeleted(boolean deleted)
    {
        this.deleted = deleted;
    }
    
    @OneToMany(mappedBy = "userGroup", fetch = FetchType.LAZY)
    public List<UserGroupMember> getUserGroupMembers()
    {
        return userGroupMembers;
    }
    
    public void setUserGroupMembers(List<UserGroupMember> userGroupMembers)
    {
        this.userGroupMembers = userGroupMembers;
    }
    
}
