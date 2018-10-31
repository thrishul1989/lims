package com.todaysoft.lims.smm.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.smm.dao.searcher.UserGroupSearcher;
import com.todaysoft.lims.smm.entity.User;
import com.todaysoft.lims.smm.entity.UserGroup;
import com.todaysoft.lims.smm.entity.UserGroupMember;
import com.todaysoft.lims.smm.request.UserGroupCreateRequest;
import com.todaysoft.lims.smm.request.UserGroupModifyRequest;
import com.todaysoft.lims.smm.request.UserGroupPagingRequest;
import com.todaysoft.lims.smm.service.IUserGroupService;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.StringUtils;

@Service
public class UserGroupService implements IUserGroupService
{
    @Autowired
    private BaseDaoSupport baseDaoSupport;
    
    @Override
    public Pagination<UserGroup> paging(UserGroupPagingRequest request)
    {
        UserGroupSearcher searcher = new UserGroupSearcher();
        if (!StringUtils.isEmpty(request.getName()))
        {
            searcher.setName(request.getName());
            //查询组长名称获取用户组id
            List<String> userGroupIds = new ArrayList<String>();
            NamedQueryer queryer =
                NamedQueryer.builder()
                    .hql("FROM UserGroup u WHERE u.groupLeaderName = :name AND u.deleted = false")
                    .names(Lists.newArrayList("name"))
                    .values(Lists.newArrayList(searcher.getName()))
                    .build();
            List<UserGroup> records = baseDaoSupport.find(queryer, UserGroup.class);
            if (!records.isEmpty())
            {
                for (UserGroup userGroup : records)
                {
                    userGroupIds.add(userGroup.getId());
                }
            }
            //查询组员名称获取用户id
            NamedQueryer queryer2 =
                NamedQueryer.builder()
                    .hql("FROM UserGroupMember u WHERE u.userName = :name ")
                    .names(Lists.newArrayList("name"))
                    .values(Lists.newArrayList(searcher.getName()))
                    .build();
            List<UserGroupMember> userGroupMembers = baseDaoSupport.find(queryer2, UserGroupMember.class);
            if (!userGroupMembers.isEmpty())
            {
                for (UserGroupMember u : userGroupMembers)
                {
                    userGroupIds.add(u.getUserGroup().getId());
                }
            }
            searcher.setUserGroupIds(userGroupIds);
        }
        else
        {
            BeanUtils.copyProperties(request, searcher);
        }
        Pagination<UserGroup> u;
        if (null != request.getPageNo() || null != request.getPageSize())
        {
            u = baseDaoSupport.find(searcher.toQuery(), request.getPageNo(), request.getPageSize(), UserGroup.class);
        }
        else
        {
            u = baseDaoSupport.find(searcher.toQuery(), 0, 10, UserGroup.class);
            
        }
        List<UserGroup> userGroups = u.getRecords();
        if (!StringUtils.isEmpty(request.getName()))
        {
            List<UserGroup> userGroups2 = new ArrayList<UserGroup>();
            for (UserGroup userGroup : userGroups)
            {
                if (request.getName().equals(userGroup.getGroupLeaderName()))
                {
                    userGroups2.add(userGroup);
                }
            }
            for (UserGroup userGroup : userGroups)
            {
                if (!request.getName().equals(userGroup.getGroupLeaderName()))
                {
                    userGroups2.add(userGroup);
                }
            }
            u.setRecords(userGroups2);
        }
        return u;
    }
    
    @Override
    @Transactional
    public void create(UserGroupCreateRequest request)
    {
        //插入小组对象
        UserGroup userGroup = new UserGroup();
        userGroup.setGroupName(request.getGroupName());
        if (StringUtils.isNotEmpty(request.getRemark()))
        {
            userGroup.setRemark(request.getRemark());
        }
        userGroup.setGroupLeader(request.getGroupLeader());
        userGroup.setGroupLeaderName(request.getGroupLeaderName());
        userGroup.setCreateTime(new Date());
        baseDaoSupport.insert(userGroup);
        //组员信息插入成员表
        if (StringUtils.isNotEmpty(request.getUserGroupMemberIds()))
        {
            String[] ids = request.getUserGroupMemberIds().split(",");
            
            for (int i = 0; i < ids.length; i++)
            {
                User u = baseDaoSupport.get(User.class, ids[i]);
                UserGroupMember ugm = new UserGroupMember();
                ugm.setUser(u);
                ugm.setUserGroup(userGroup);
                ugm.setUserName(u.getArchive().getName());
                ugm.setCreateDate(new Date());
                baseDaoSupport.insert(UserGroupMember.class, ugm);
            }
        }
    }
    
    @Override
    @Transactional
    public void modify(UserGroupModifyRequest request)
    {
        UserGroup userGroup = baseDaoSupport.get(UserGroup.class, request.getId());
        baseDaoSupport.execute("delete from UserGroupMember u where u.userGroup.id='" + request.getId() + "'");
        //组员信息插入成员表
        if (StringUtils.isNotEmpty(request.getUserGroupMemberIds()))
        {
            String[] ids = request.getUserGroupMemberIds().split(",");
            
            for (int i = 0; i < ids.length; i++)
            {
                if (StringUtils.isNotEmpty(ids[i]))
                {
                    User u = baseDaoSupport.get(User.class, ids[i]);
                    UserGroupMember ugm = new UserGroupMember();
                    ugm.setUser(u);
                    ugm.setUserGroup(userGroup);
                    ugm.setUserName(u.getArchive().getName());
                    ugm.setCreateDate(new Date());
                    baseDaoSupport.insert(UserGroupMember.class, ugm);
                }
            }
        }
        userGroup.setGroupName(request.getGroupName());
        if (StringUtils.isNotEmpty(request.getRemark()))
        {
            userGroup.setRemark(request.getRemark());
        }
        userGroup.setGroupLeader(request.getGroupLeader());
        userGroup.setGroupLeaderName(request.getGroupLeaderName());
        baseDaoSupport.update(userGroup);
    }
    
    /*   @Override
       @Transactional
       public boolean delete(String id)
       {
           String sql = "select T.ID FROM LIMS_TASK_CONFIG T where T.USERGROUP_ID =?";
           List<?> count = baseDaoSupport.findBySql(sql, id);
           if (Collections3.isNotEmpty(count))
           {
               return false;
           }
           UserGroup userGroup = baseDaoSupport.get(UserGroup.class, id);
           userGroup.setDeleted(true);
           userGroup.setDeleteTime(new Date());
           userGroup.setUserGroupMembers(null);
           baseDaoSupport.update(userGroup);
           return true;
       }*/
    
    @Override
    @Transactional
    public boolean delete(String id)
    {
        String sql = "select T.ID FROM LIMS_TASK_CONFIG T where T.USERGROUP_ID =?";
        List<?> count = baseDaoSupport.findBySql(sql, id);
        if (Collections3.isNotEmpty(count))
        {
            return false;
        }
        else
        {
            UserGroup userGroup = baseDaoSupport.get(UserGroup.class, id);
            userGroup.setDeleted(true);
            userGroup.setDeleteTime(new Date());
            userGroup.setUserGroupMembers(null);
            baseDaoSupport.update(userGroup);
            return true;
        }
        
    }
    
    @Override
    public UserGroup getById(String id)
    {
        return baseDaoSupport.get(UserGroup.class, id);
    }
    
    @Override
    public boolean getByName(String name)
    {
        NamedQueryer queryer =
            NamedQueryer.builder()
                .hql("FROM UserGroup u WHERE u.groupName = :name AND u.deleted = false")
                .names(Lists.newArrayList("name"))
                .values(Lists.newArrayList(name))
                .build();
        List<UserGroup> records = baseDaoSupport.find(queryer, UserGroup.class);
        return records.isEmpty() ? false : true;
    }
    
}
