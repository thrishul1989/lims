package com.todaysoft.lims.smm.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.todaysoft.lims.smm.entity.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.google.common.collect.Lists;
import com.todaysoft.lims.exception.ServiceException;
import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.persist.IEntityWrapper;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.smm.dao.searcher.APPSalemanUserSearcher;
import com.todaysoft.lims.smm.dao.searcher.UserSearcher;
import com.todaysoft.lims.smm.dao.searcher.UserSelectSearcher;
import com.todaysoft.lims.smm.entity.enums.UserState;
import com.todaysoft.lims.smm.request.APPSalemanRequest;
import com.todaysoft.lims.smm.request.UserCreateRequest;
import com.todaysoft.lims.smm.request.UserGroupPagingRequest;
import com.todaysoft.lims.smm.request.UserListRequest;
import com.todaysoft.lims.smm.request.UserModifyRequest;
import com.todaysoft.lims.smm.request.UserPasswordModifyRequest;
import com.todaysoft.lims.smm.request.UserPasswordResetRequest;
import com.todaysoft.lims.smm.response.UserDetailsModel;
import com.todaysoft.lims.smm.response.UserMinimalModel;
import com.todaysoft.lims.smm.response.UserSimpleModel;
import com.todaysoft.lims.smm.service.IAPPSalemanService;
import com.todaysoft.lims.smm.service.IUserGroupService;
import com.todaysoft.lims.smm.service.IUserService;
import com.todaysoft.lims.smm.utils.AuthenticateUtil;
import com.todaysoft.lims.utils.Collections3;

@Service
public class UserService implements IUserService, IEntityWrapper<User, UserSimpleModel>
{
    @Autowired
    private BaseDaoSupport baseDaoSupport;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private IAPPSalemanService appsaleservice;
    
    @Autowired
    private IUserGroupService userGroupService;
    
    @Override
    public Pagination<UserSimpleModel> paging(UserListRequest request)
    {
        UserSearcher searcher = new UserSearcher();
        searcher.setName(request.getName());
        searcher.setPhone(request.getPhone());
        searcher.setUsername(request.getUsername());
        searcher.setStatus(request.getStatus());
        searcher.setArchive(request.getArchive());
        searcher.setRoleId(request.getRoleId());
        Pagination<UserSimpleModel> p = baseDaoSupport.find(searcher.toQuery(), request.getPageNo(), request.getPageSize(), this);
        return p;
    }
    
    @Override
    public List<UserSimpleModel> list(UserListRequest request)
    {
        UserSearcher searcher = new UserSearcher();
        searcher.setName(request.getName());
        searcher.setPhone(request.getPhone());
        searcher.setUsername(request.getUsername());
        searcher.setState(request.getState());
        return baseDaoSupport.find(searcher, this);
    }
    
    @Override
    public boolean unique(String username)
    {
        return null == getUserByUsername(username);
    }
    
    @Override
    public User getUserByUsername(String username)
    {
        NamedQueryer queryer =
            NamedQueryer.builder()
                .hql("FROM User u WHERE u.username = :username AND u.deleted = false")
                .names(Lists.newArrayList("username"))
                .values(Lists.newArrayList(username))
                .build();
        List<User> records = baseDaoSupport.find(queryer, User.class);
        return records.isEmpty() ? null : records.get(0);
    }
    
    @Override
    public UserDetailsModel getUserByID(String id)
    {
        NamedQueryer queryer =
            NamedQueryer.builder()
                .hql("from User u where u.id = :id  and u.deleted = false")
                .names(Lists.newArrayList("id"))
                .values(Lists.newArrayList(id))
                .build();
        List<User> records = baseDaoSupport.find(queryer, User.class);
        
        User entity = records.isEmpty() ? null : records.get(0);
        
        return entity == null ? null : new UserDetailsModel(entity);
        /*User entity = baseDaoSupport.get(User.class, id);
        return new UserDetailsModel(entity);*/
    }
    
    @Override
    public UserMinimalModel getMinimalUserByID(String id)
    {
        User entity = baseDaoSupport.get(User.class, id);
        
        if (null == entity)
        {
            return null;
        }
        
        UserMinimalModel model = new UserMinimalModel();
        model.setId(entity.getId());
        model.setName(null == entity.getArchive() ? null : entity.getArchive().getName());
        model.setUsername(entity.getUsername());
        return model;
    }
    
    @Override
    @Transactional
    public String create(UserCreateRequest request)
    {
        if (StringUtils.isEmpty(request.getUsername()) || StringUtils.isEmpty(request.getPassword()))
        {
            throw new ServiceException(ErrorCode.ILLEGAL_ARGUMENT);
        }
        
        if (getUserByUsername(request.getUsername()) != null)
        {
            throw new ServiceException(ErrorCode.USERNAME_EXISTS);
        }

        UserArchive archive = request.getArchive();
        baseDaoSupport.insert(archive);
        User user = new User();
        BeanUtils.copyProperties(request, user, "roles");
        user.setDeleted(false);

        user.setSalt(AuthenticateUtil.generateSalt());
        user.setPassword(AuthenticateUtil.salt(request.getPassword(), user.getSalt()));
        
        user.setState(UserState.ENABLED);
        user.setCreateTime(new Date());
        
        if (!CollectionUtils.isEmpty(request.getRoles()))
        {
            Role role;
            Set<Role> roles = new HashSet<Role>();
            
            for (String roleId : request.getRoles())
            {
                role = new Role();
                role.setId(roleId);
                roles.add(role);
            }
            
            user.setRoles(roles);
        }
        
        baseDaoSupport.insert(user);
        return user.getId();
    }
    
    @Override
    @Transactional
    public void modify(UserModifyRequest request)
    {
        User user = getTargetRecord(request.getId());
        
        if (null != request.getArchive())
        {
            BeanUtils.copyProperties(request.getArchive(), user.getArchive(), "id");
        }
        
        BeanUtils.copyProperties(request, user, "roles", "archive");
        
        if (!CollectionUtils.isEmpty(request.getRoles()))
        {
            if (!CollectionUtils.isEmpty(request.getRoles()))
            {
                Role role;
                Set<Role> roles = new HashSet<Role>();
                
                for (String roleId : request.getRoles())
                {
                    role = new Role();
                    role.setId(roleId);
                    roles.add(role);
                }
                
                user.setRoles(roles);
            }
        }
        else
        {
            user.setRoles(null);
        }
        
        baseDaoSupport.update(user);
    }
    
    @Override
    @Transactional
    public void enable(String id)
    {
        User entity = getTargetRecord(id);
        entity.setState(UserState.ENABLED);
        baseDaoSupport.update(entity);
    }
    
    @Override
    @Transactional
    public void disable(String id)
    {
        
        User entity = getTargetRecord(id);
        entity.setState(UserState.DISABLED);
        baseDaoSupport.update(entity);
    }
    
    @Override
    @Transactional
    public void lock(String id)
    {
        User entity = getTargetRecord(id);
        entity.setState(UserState.LOCKED);
        baseDaoSupport.update(entity);
    }
    
    @Override
    @Transactional
    public Integer delete(String id)
    {
        Integer flag = 2;//初始值不与其他值冲突
        String sql = "select UR.id FROM APP_USER_RELATION UR where UR.BUSINESS_USER_ID=?";
        List<?> count = baseDaoSupport.findBySql(sql, id);
        if (Collections3.isNotEmpty(count))
        {
            flag = 0;//该用户是业务员
        }
        
        User entity1 = getTargetRecord(id);
        UserGroupPagingRequest groupPagingRequest = new UserGroupPagingRequest();
        groupPagingRequest.setName(entity1.getArchive().getName());
        Pagination<UserGroup> pagination = userGroupService.paging(groupPagingRequest);
        if (pagination.getRecords().size() != 0)
        {
            flag = 1;//该用户被用户组关联
        }
        if (flag == 2)
        {
            User entity = getTargetRecord(id);
            entity.setRoles(null);
            entity.setDeleted(true);
            entity.setDeleteTime(new Date());
            baseDaoSupport.update(entity);
        }
        return flag;
    }
    
    @Override
    @Transactional
    public void resetPassword(UserPasswordResetRequest request)
    {
        User entity = getTargetRecord(request.getId());
        entity.setSalt(AuthenticateUtil.generateSalt());
        entity.setPassword(AuthenticateUtil.salt(request.getPassword(), entity.getSalt()));
        //entity.setPassword(passwordEncoder.encode(request.getPassword()));
        // baseDaoSupport.update(entity);
    }
    
    @Override
    @Transactional
    public void modifyPassword(UserPasswordModifyRequest request)
    {
        User entity = getTargetRecord(request.getId());
        
        if (StringUtils.isEmpty(request.getOldPassword()) || !request.getOldPassword().equals(entity.getPassword()))
        {
            throw new ServiceException(ErrorCode.OLD_PASSWORD_UNMATCHED);
        }
        
        entity.setPassword(request.getNewPassword());
        baseDaoSupport.update(entity);
    }
    
    private User getTargetRecord(String id)
    {
        User user = baseDaoSupport.get(User.class, id);
        
        if (null == user)
        {
            throw new ServiceException(ErrorCode.RECORD_NOT_EXISTS);
        }
        
        return user;
    }
    
    @Override
    public List<UserDetailsModel> getContactUsers(List<Integer> userIds)
    {
        if (Collections3.isEmpty(userIds))
        {
            return null;
        }
        
        String hql = "FROM User u WHERE u.id IN :userIds";
        NamedQueryer queryer = NamedQueryer.builder().hql(hql).names(Lists.newArrayList("userIds")).values(Arrays.asList((Object)userIds)).build();
        List<User> users = baseDaoSupport.find(queryer, User.class);
        List<UserDetailsModel> records = new ArrayList<UserDetailsModel>();
        for (User entity : users)
        {
            UserDetailsModel record = new UserDetailsModel(entity);
            records.add(record);
        }
        return records;
    }
    
    @Override
    public UserMinimalModel getByToken(String token)
    {
        UserMinimalModel authorizedUser = new UserMinimalModel();
        NamedQueryer queryer =
            NamedQueryer.builder().hql("FROM UserToken u WHERE u.token = :token ").names(Lists.newArrayList("token")).values(Lists.newArrayList(token)).build();
        List<UserToken> records = baseDaoSupport.find(queryer, UserToken.class);
        UserToken userToken = Collections3.getFirst(records);
        User user = null;
        if (null != userToken)
        {
            user = baseDaoSupport.get(User.class, userToken.getUserId());
            authorizedUser.setId(user.getId());
            authorizedUser.setUsername(user.getUsername());
            authorizedUser.setName(user.getArchive() == null ? "" : user.getArchive().getName());
        }
        return authorizedUser;
    }
    
    @Override
    public UserSimpleModel wrap(User entity)
    {
        return new UserSimpleModel(entity);
    }
    
    @Override
    public List<UserSimpleModel> userSelect(UserListRequest request)
    {
        APPSalemanUserSearcher searcher = new APPSalemanUserSearcher();
        searcher.setName(request.getName());
        if (null != request.getArchive())
        {
            Set<String> deptIds = new HashSet<String>();
            deptIds.add(request.getArchive().getDeptId());
            Department department = baseDaoSupport.get(Department.class,request.getArchive().getDeptId());
            deptIds.add(department.getParentId());
//            searcher.setArchive(request.getArchive());
            searcher.setDeptIds(deptIds);
            searcher.setUserId(request.getTemplateId());
            return baseDaoSupport.find(searcher, this);
        }
        List<String> list = appsaleservice.list(new APPSalemanRequest());
        searcher.setList(list);
        return baseDaoSupport.find(searcher, this);
    }
    
    @Override
    public boolean validate(UserListRequest request)
    {
        
        return null == getUserByPhone(request);
    }
    
    public User getUserByPhone(UserListRequest request)
    {
        NamedQueryer queryer = null;
        if (StringUtils.isEmpty(request.getId()))
        {
            
            queryer =
                NamedQueryer.builder()
                    .hql("FROM User u WHERE u.archive.phone = :phone AND u.deleted = false ")
                    .names(Lists.newArrayList("phone"))
                    .values(Lists.newArrayList(request.getPhone()))
                    .build();
        }
        else
        {
            queryer =
                NamedQueryer.builder()
                    .hql("FROM User u WHERE u.archive.phone = :phone AND u.deleted = false AND u.id != :userId")
                    .names(Lists.newArrayList("phone", "userId"))
                    .values(Lists.newArrayList(request.getPhone(), request.getId()))
                    .build();
        }
        List<User> records = baseDaoSupport.find(queryer, User.class);
        return records.isEmpty() ? null : records.get(0);
    }
    
    @Override
    public List<UserSimpleModel> userSelectForTest(UserListRequest request)
    {
        UserSelectSearcher searcher = new UserSelectSearcher();
        searcher.setState(request.getState());
        return baseDaoSupport.find(searcher, this);
    }
    
    @Override
    public Customer getCustomer(String id)
    {
        return baseDaoSupport.get(Customer.class, id);
    }
    
    @Override
    public List<UserSimpleModel> noSalesmanList(UserListRequest request)
    {
        UserSearcher searcher = new UserSearcher();
        searcher.setName(request.getName());
        searcher.setPhone(request.getPhone());
        searcher.setUsername(request.getUsername());
        searcher.setState(request.getState());
        List<UserSimpleModel> list = baseDaoSupport.find(searcher, this);
        Iterator<UserSimpleModel> it = list.iterator();
        
        while (it.hasNext())
        {
            UserSimpleModel u = it.next();
            UserState uState = u.getState();
            switch (uState)
            {
                case DISABLED:
                    it.remove();
                    break;
                default:
                    APPSaleman entity = baseDaoSupport.get(APPSaleman.class, u.getId());
                    if (entity != null)
                    {
                        it.remove();
                    }
                    break;
            }
        }
        return list;
    }
    
    @Override
    public boolean validate(String id)
    {
        
        User entity = getTargetRecord(id);
        UserGroupPagingRequest groupPagingRequest = new UserGroupPagingRequest();
        groupPagingRequest.setName(entity.getArchive().getName());
        Pagination<UserGroup> pagination = userGroupService.paging(groupPagingRequest);
        if (pagination.getRecords().size() == 0)
        {
            return false;
        }
        else
        {
            return true;
        }
        
    }
    
    @Override
    public boolean uniqueNum(String employeeNo, String id)
    {
        
        NamedQueryer queryer = null;
        if (StringUtils.isEmpty(id))
        {
            
            queryer =
                NamedQueryer.builder()
                    .hql("FROM User u WHERE u.archive.employeeNo = :employeeNo AND u.deleted = false")
                    .names(Lists.newArrayList("employeeNo"))
                    .values(Lists.newArrayList(employeeNo))
                    .build();
        }
        else
        {
            queryer =
                NamedQueryer.builder()
                    .hql("FROM User u WHERE u.archive.employeeNo = :employeeNo AND u.deleted = false AND u.id != :userId")
                    .names(Lists.newArrayList("employeeNo", "userId"))
                    .values(Lists.newArrayList(employeeNo, id))
                    .build();
        }
        
        List<User> records = baseDaoSupport.find(queryer, User.class);
        return records.isEmpty();
    }
}
