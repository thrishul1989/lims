package com.todaysoft.lims.smm.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Lists;
import com.todaysoft.lims.base.RecordFilter;
import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.smm.dao.searcher.DataAuthorityRoleSearcher;
import com.todaysoft.lims.smm.dao.searcher.DataAuthoritySearcher;
import com.todaysoft.lims.smm.dao.searcher.DataAuthorityUserSearcher;
import com.todaysoft.lims.smm.entity.DataAuthority;
import com.todaysoft.lims.smm.entity.DataAuthorityRole;
import com.todaysoft.lims.smm.entity.DataAuthorityUser;
import com.todaysoft.lims.smm.entity.Department;
import com.todaysoft.lims.smm.entity.User;
import com.todaysoft.lims.smm.response.ResponseState;
import com.todaysoft.lims.smm.service.IDataAuthorityService;
import com.todaysoft.lims.utils.Collections3;

@Service
public class DataAuthorityService implements IDataAuthorityService
{
    
    @Autowired
    private BaseDaoSupport baseDaoSupport;
    
    @Override
    public Pagination<DataAuthority> paging(DataAuthoritySearcher request)
    {
        
        return baseDaoSupport.find(request.toQuery(), request.getPageNo(), request.getPageSize(), DataAuthority.class);
    }
    
    @Override
    public Pagination<DataAuthorityRole> dataAuthorityRolePaging(DataAuthorityRoleSearcher request)
    {
        // TODO Auto-generated method stub
        Pagination<DataAuthorityRole> paging = baseDaoSupport.find(request.toQuery(), request.getPageNo(), request.getPageSize(), DataAuthorityRole.class);
        //       String s= com.alibaba.fastjson.JSONObject.toJSON(paging.getRecords()).toString();
        
        return paging;
    }
    
    @Override
    @Transactional
    public Integer createRoleDataAuthority(DataAuthorityRole request)
    {
        //判断该角色是否已经设置数据权限
        List<DataAuthorityRole> list = baseDaoSupport.find(DataAuthorityRole.class,
            "from DataAuthorityRole dr where dr.resourceKey.resourceKey ='" + request.getResourceKey().getResourceKey() + "' and dr.roleId='"
                + request.getRoleId() + "'");
        if (Collections3.isNotEmpty(list))
        {
            return ResponseState.FAIL.getIndex();//已经存在
            
        }
        else
        {
            baseDaoSupport.insert(DataAuthorityRole.class, request);
            return ResponseState.SUCCESS.getIndex();
        }
        
    }
    
    @Override
    @Transactional
    public Integer deleteRoleAuthority(DataAuthorityRole request)
    {
        // TODO Auto-generated method stub
        baseDaoSupport.deleteByID(DataAuthorityRole.class, request.getId());
        return ResponseState.SUCCESS.getIndex();
    }
    
    @Override
    public Pagination<DataAuthorityUser> dataAuthorityUserPaging(DataAuthorityUserSearcher request)
    {
        Pagination<DataAuthorityUser> paging = baseDaoSupport.find(request.toQuery(), request.getPageNo(), request.getPageSize(), DataAuthorityUser.class);
        
        return paging;
    }
    
    @Override
    @Transactional
    public Integer createUserDataAuthority(DataAuthorityUser request)
    {
        //判断该用户是否已经设置数据权限
        List<DataAuthorityUser> list = baseDaoSupport.find(DataAuthorityUser.class,
            "from DataAuthorityUser du where du.resourceKey.resourceKey ='" + request.getResourceKey().getResourceKey() + "' and du.userId='"
                + request.getUserId() + "'");
        if (Collections3.isNotEmpty(list))
        {
            return ResponseState.FAIL.getIndex();//已经存在
            
        }
        else
        {
            baseDaoSupport.insert(DataAuthorityRole.class, request);
            return ResponseState.SUCCESS.getIndex();
        }
    }
    
    @Override
    @Transactional
    public Integer deleteUserAuthority(DataAuthorityUser request)
    {
        baseDaoSupport.deleteByID(DataAuthorityUser.class, request.getId());
        return ResponseState.SUCCESS.getIndex();
    }
    
    @Override
    public List<DataAuthorityRole> dataAuthorityRoleByRoleId(DataAuthorityRoleSearcher request)
    {
        // TODO Auto-generated method stub
        return baseDaoSupport.find(request);
    }
    
    @Override
    public List<DataAuthorityUser> dataAuthorityUserByUserId(DataAuthorityUserSearcher request)
    {
        // TODO Auto-generated method stub
        return baseDaoSupport.find(request);
    }
    
    @Override
    public List<DataAuthority> list(DataAuthoritySearcher request)
    {
        // TODO Auto-generated method stub
        return baseDaoSupport.find(DataAuthority.class);
    }
    
    @Override
    public List<String> getSubordinates(String leaders)
    {
        List<Object> values = new ArrayList<Object>();
        Set<String> leaderIds = new HashSet<String>(Arrays.asList(leaders.split(",")));
        values.add(leaderIds);
        String hql = "SELECT u.id FROM User u WHERE EXISTS (SELECT ua.id FROM UserArchive ua WHERE ua.leaderId IN (:leaderIds) AND ua.id = u.archive.id)";
        NamedQueryer queryer = NamedQueryer.builder().hql(hql).names(Lists.newArrayList("leaderIds")).values(values).build();
        List<String> userIds = baseDaoSupport.find(queryer, String.class);
        return userIds;
    }
    
    @Override
    public Map<String, RecordFilter> getRecordFilters(String userId)
    {
        List<DataAuthority> categories = baseDaoSupport.find(DataAuthority.class);
        
        if (CollectionUtils.isEmpty(categories))
        {
            return Collections.emptyMap();
        }
        
        User user = baseDaoSupport.get(User.class, userId);
        
        if (null == user)
        {
            return Collections.emptyMap();
        }
        
        String ownerDeptId = null == user.getArchive() ? null : user.getArchive().getDeptId();
        
        List<String> ownerSubordinates = new ArrayList<String>();
        fillUserSubordinates(user, ownerSubordinates);
        
        List<String> ownerDeptSubordinates = new ArrayList<String>();
        fillDeptSubordinates(ownerDeptId, ownerDeptSubordinates);
        
        Map<String, RecordFilter> configs = new HashMap<String, RecordFilter>();
        
        RecordFilter filter;
        
        for (DataAuthority category : categories)
        {
            filter = configs.get(category.getResourceKey());
            
            if (null == filter)
            {
                filter = new RecordFilter();
                configs.put(category.getResourceKey(), filter);
            }
            
            this.updateFilterByConfig(filter,
                category.getDefaultConfig(),
                category.getSpecialDepts(),
                userId,
                ownerSubordinates,
                ownerDeptId,
                ownerDeptSubordinates);
        }
        
        // 用户角色关联配置
        String hql = "FROM DataAuthorityRole dar WHERE EXISTS (SELECT ur.id FROM UserRole ur WHERE ur.user = :userId AND ur.role = dar.roleId)";
        NamedQueryer queryer = NamedQueryer.builder().hql(hql).names(Arrays.asList("userId")).values(Arrays.asList(userId)).build();
        List<DataAuthorityRole> roleAuthorities = baseDaoSupport.find(queryer, DataAuthorityRole.class);
        
        if (!CollectionUtils.isEmpty(roleAuthorities))
        {
            for (DataAuthorityRole record : roleAuthorities)
            {
                filter = configs.get(record.getResourceKey().getResourceKey());
                
                if (null == filter)
                {
                    filter = new RecordFilter();
                    configs.put(record.getResourceKey().getResourceKey(), filter);
                }
                
                this.updateFilterByConfig(filter, record.getConfig(), record.getSpecialDepts(), userId, ownerSubordinates, ownerDeptId, ownerDeptSubordinates);
            }
        }
        
        // 用户账号关联配置
        hql = "FROM DataAuthorityUser dau WHERE dau.userId = :userId";
        queryer = NamedQueryer.builder().hql(hql).names(Arrays.asList("userId")).values(Arrays.asList(userId)).build();
        List<DataAuthorityUser> userAuthorities = baseDaoSupport.find(queryer, DataAuthorityUser.class);
        
        if (!CollectionUtils.isEmpty(userAuthorities))
        {
            for (DataAuthorityUser record : userAuthorities)
            {
                filter = configs.get(record.getResourceKey().getResourceKey());
                
                if (null == filter)
                {
                    filter = new RecordFilter();
                    configs.put(record.getResourceKey().getResourceKey(), filter);
                }
                
                this.updateFilterByConfig(filter, record.getConfig(), record.getSpecialDepts(), userId, ownerSubordinates, ownerDeptId, ownerDeptSubordinates);
            }
        }
        
        for (RecordFilter record : configs.values())
        {
            optimize(record, userId, ownerDeptId);
        }
        
        return configs;
    }
    
    private void fillUserSubordinates(User user, List<String> subordinates)
    {
        String hql = "FROM User u WHERE u.archive.leaderId = :userId";
        NamedQueryer queryer = NamedQueryer.builder().hql(hql).names(Arrays.asList("userId")).values(Arrays.asList(user.getId())).build();
        List<User> records = baseDaoSupport.find(queryer, User.class);
        
        if (!CollectionUtils.isEmpty(records))
        {
            for (User record : records)
            {
                subordinates.add(record.getId());
                fillUserSubordinates(record, subordinates);
            }
        }
    }
    
    private void fillDeptSubordinates(String deptId, List<String> subordinates)
    {
        if (StringUtils.isEmpty(deptId))
        {
            return;
        }
        
        String hql = "FROM Department d WHERE d.parentId = :deptId";
        NamedQueryer queryer = NamedQueryer.builder().hql(hql).names(Arrays.asList("deptId")).values(Arrays.asList(deptId)).build();
        List<Department> records = baseDaoSupport.find(queryer, Department.class);
        
        if (!CollectionUtils.isEmpty(records))
        {
            for (Department record : records)
            {
                subordinates.add(record.getId());
                fillDeptSubordinates(record.getId(), subordinates);
            }
        }
    }
    
    private void updateFilterByConfig(RecordFilter filter, Integer config, String specialDepts, String ownerId, List<String> ownerSubordinates, String ownerDeptId, List<String> ownerDeptSubordinates)
    {
        // 无效配置，不做处理
        if (null == config)
        {
            return;
        }
        
        // 已包含最高数据权限，不做处理
        if (filter.isDisabled())
        {
            return;
        }
        
        switch (config)
        {
            // 本人数据
            case 1:
                updateFilterForOwnerLevel(filter, ownerId);
                break;
            
            // 本人及下属数据
            case 2:
                updateFilterForOwnerAndSubordinatesLevel(filter, ownerId, ownerSubordinates);
                break;
            
            // 所在机构数据    
            case 3:
                updateFilterForOwnerDeptLevel(filter, ownerDeptId);
                break;
            
            // 所在机构及下属机构数据    
            case 4:
                updateFilterForOwnerDeptAndDeptSubordinatesLevel(filter, ownerDeptId, ownerDeptSubordinates);
                break;
            case 5:
                updateFilterForSpecialDeptsLevel(filter, specialDepts);
                break;
            
            // 全部数据
            case 6:
                updateFilterForNoLimit(filter);
                break;
            default:
                break;
        }
    }
    
    private void updateFilterForOwnerLevel(RecordFilter filter, String ownerId)
    {
        if (null == filter.getOwners())
        {
            filter.setOwners(new HashSet<String>());
        }
        
        filter.getOwners().add(ownerId);
    }
    
    private void updateFilterForOwnerAndSubordinatesLevel(RecordFilter filter, String ownerId, List<String> ownerSubordinates)
    {
        if (null == filter.getOwners())
        {
            filter.setOwners(new HashSet<String>());
        }
        
        filter.getOwners().add(ownerId);
        filter.getOwners().addAll(ownerSubordinates);
    }
    
    private void updateFilterForOwnerDeptLevel(RecordFilter filter, String ownerDeptId)
    {
        if (null == filter.getOwnerDepts())
        {
            filter.setOwnerDepts(new HashSet<String>());
        }
        
        if (!StringUtils.isEmpty(ownerDeptId))
        {
            filter.getOwnerDepts().add(ownerDeptId);
        }
    }
    
    private void updateFilterForOwnerDeptAndDeptSubordinatesLevel(RecordFilter filter, String ownerDeptId, List<String> ownerDeptSubordinates)
    {
        if (null == filter.getOwnerDepts())
        {
            filter.setOwnerDepts(new HashSet<String>());
        }
        
        if (!StringUtils.isEmpty(ownerDeptId))
        {
            filter.getOwnerDepts().add(ownerDeptId);
        }
        
        filter.getOwnerDepts().addAll(ownerDeptSubordinates);
    }
    
    private void updateFilterForSpecialDeptsLevel(RecordFilter filter, String specialDepts)
    {
        if (StringUtils.isEmpty(specialDepts))
        {
            return;
        }
        
        if (null == filter.getOwnerDepts())
        {
            filter.setOwnerDepts(new HashSet<String>());
        }
        
        filter.getOwnerDepts().addAll(Arrays.asList(specialDepts.split(",")));
    }
    
    private void updateFilterForNoLimit(RecordFilter filter)
    {
        filter.setDisabled(true);
    }
    
    private void optimize(RecordFilter filter, String ownerId, String ownerDeptId)
    {
        if (filter.isDisabled())
        {
            filter.setOwners(null);
            filter.setOwnerDepts(null);
            return;
        }
        
        // 已包含所在部门数据权限的，可以移除本人数据权限
        if (!CollectionUtils.isEmpty(filter.getOwnerDepts()) && !CollectionUtils.isEmpty(filter.getOwners()) && filter.getOwnerDepts().contains(ownerDeptId))
        {
            filter.getOwners().remove(ownerId);
        }
        
        if (CollectionUtils.isEmpty(filter.getOwners()))
        {
            filter.setOwners(null);
        }
        
        if (CollectionUtils.isEmpty(filter.getOwnerDepts()))
        {
            filter.setOwnerDepts(null);
        }
    }
}
