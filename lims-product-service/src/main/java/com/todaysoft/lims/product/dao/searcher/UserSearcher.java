package com.todaysoft.lims.product.dao.searcher;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import com.todaysoft.lims.persist.ISearcher;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.product.entity.User;
import com.todaysoft.lims.product.entity.UserArchive;
import com.todaysoft.lims.product.entity.UserState;

public class UserSearcher implements ISearcher<User>
{
    private String username;
    
    private String name;
    
    private String phone;
    
    private UserState state;
    
    private UserArchive archive;
    
    private String roleId;
    
    private String userId;
    
    @Override
    public Class<User> getEntityClass()
    {
        return User.class;
    }
    
    @Override
    public NamedQueryer toQuery()
    {
        StringBuffer hql = new StringBuffer(256);
        if (!StringUtils.isEmpty(roleId))
        {
            hql.append("select u FROM User u left join u.roles as roles WHERE u.deleted = false ");
        }
        else
        {
            hql.append("FROM User u WHERE u.deleted = false ");
        }
        List<String> names = new ArrayList<String>();
        List<Object> values = new ArrayList<Object>();
        addUsernameFilter(hql, names, values);
        addNameFilter(hql, names, values);
        addPhoneFilter(hql, names, values);
        addStateFilter(hql, names, values);
        addDeptFilter(hql, names, values);
        addRoleFilter(hql, names, values);
        hql.append(" ORDER BY u.state,u.createTime DESC");
        NamedQueryer queryer = new NamedQueryer();
        queryer.setHql(hql.toString());
        queryer.setNames(names);
        queryer.setValues(values);
        return queryer;
    }
    
    private void addUsernameFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        if (!StringUtils.isEmpty(username))
        {
            hql.append(" AND u.username LIKE :username");
            paramNames.add("username");
            parameters.add("%" + username + "%");
        }
    }
    
    private void addNameFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        if (!StringUtils.isEmpty(name))
        {
            hql.append(" AND u.archive.name LIKE :name");
            paramNames.add("name");
            parameters.add("%" + name + "%");
        }
    }
    
    private void addPhoneFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        if (!StringUtils.isEmpty(phone))
        {
            hql.append(" AND u.archive.phone LIKE :phone");
            paramNames.add("phone");
            parameters.add("%" + phone + "%");
        }
    }
    
    private void addStateFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        if (!StringUtils.isEmpty(status))
        {
            hql.append(" AND u.state = :state");
            paramNames.add("state");
            parameters.add(status);
        }
    }
    
    private void addDeptFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        if (null != archive)
        {
            
        }
    }
    
    private void addRoleFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        if (!StringUtils.isEmpty(roleId))
        {
            hql.append(" AND roles.id =:roleId");
            
            paramNames.add("roleId");
            parameters.add(roleId);
        }
    }
    
    private UserState status;
    
    public UserState getStatus()
    {
        return status;
    }
    
    public void setStatus(UserState status)
    {
        this.status = status;
    }
    
    public String getUsername()
    {
        return username;
    }
    
    public void setUsername(String username)
    {
        this.username = username;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getPhone()
    {
        return phone;
    }
    
    public void setPhone(String phone)
    {
        this.phone = phone;
    }
    
    public UserState getState()
    {
        return state;
    }
    
    public void setState(UserState state)
    {
        this.state = state;
    }
    
    public UserArchive getArchive()
    {
        return archive;
    }
    
    public void setArchive(UserArchive archive)
    {
        this.archive = archive;
    }
    
    public String getRoleId()
    {
        return roleId;
    }
    
    public void setRoleId(String roleId)
    {
        this.roleId = roleId;
    }
    
    public String getUserId()
    {
        return userId;
    }
    
    public void setUserId(String userId)
    {
        this.userId = userId;
    }
    
}
