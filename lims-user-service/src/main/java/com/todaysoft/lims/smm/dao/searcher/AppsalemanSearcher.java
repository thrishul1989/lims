package com.todaysoft.lims.smm.dao.searcher;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import com.todaysoft.lims.persist.ISearcher;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.smm.entity.User;
import com.todaysoft.lims.smm.entity.enums.UserState;

public class AppsalemanSearcher implements ISearcher<User>
{
    private String username;
    
    private String name;
    
    private String phone;

    private String projectManager;

    private Integer belongArea;
    
    @Override
    public Class<User> getEntityClass()
    {
        return User.class;
    }
    
    @Override
    public NamedQueryer toQuery()
    {
        StringBuffer hql = new StringBuffer(256);
        hql.append("FROM BusinessInfo b where b.delFlag = 0");
        List<String> names = new ArrayList<String>();
        List<Object> values = new ArrayList<Object>();
        addUsernameFilter(hql, names, values);
        addNameFilter(hql, names, values);
        addPhoneFilter(hql, names, values);
        addStateFilter(hql, names, values);
        addRoleTypeFilter(hql, names, values);
        addTestingTypeFilter(hql, names, values);
        addPrjManagerFilter(hql, names, values);
        addBelongAreaFilter(hql, names, values);
        hql.append(" ORDER BY b.disableStatus, b.createDate DESC");
        
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
            hql.append(" AND b.userName LIKE :username");
            paramNames.add("username");
            parameters.add("%" + username + "%");
        }
    }

    private void addPrjManagerFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        if (com.todaysoft.lims.utils.StringUtils.isNotEmpty(projectManager))
        {
            hql.append(" AND b.projectManager = :projectManager");
            paramNames.add("projectManager");
            parameters.add(projectManager);
        }

    }
    private void addBelongAreaFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        if (com.todaysoft.lims.utils.StringUtils.isNotEmpty(belongArea))
        {
            hql.append(" AND b.belongArea = :belongArea");
            paramNames.add("belongArea");
            parameters.add(belongArea);
        }

    }
    
    private void addNameFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        if (!StringUtils.isEmpty(name))
        {
            hql.append(" AND b.realName LIKE :name");
            paramNames.add("name");
            parameters.add("%" + name + "%");
        }
    }
    
    private void addPhoneFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        if (!StringUtils.isEmpty(phone))
        {
            hql.append(" AND b.phoneNum LIKE :phone");
            paramNames.add("phone");
            parameters.add("%" + phone + "%");
        }
    }
    
    private void addStateFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        if (!StringUtils.isEmpty(status))
        {
            hql.append(" AND b.disableStatus = :status");
            paramNames.add("status");
            if (status == UserState.DISABLED)
            {
                parameters.add(1);
            }
            else
            {
                parameters.add(0);
            }
        }
    }
    
    private void addTestingTypeFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        if (!StringUtils.isEmpty(testingType))
        {
            hql.append(" AND b.testingType = :testingType");
            paramNames.add("testingType");
            parameters.add(testingType);
        }
    }
    
    private void addRoleTypeFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        if (!StringUtils.isEmpty(roleType))
        {
            hql.append(" AND b.roleType = :roleType");
            paramNames.add("roleType");
            parameters.add(roleType);
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
    
    private String roleType;
    
    private String testingType;
    
    public String getRoleType()
    {
        return roleType;
    }
    
    public void setRoleType(String roleType)
    {
        this.roleType = roleType;
    }
    
    public String getTestingType()
    {
        return testingType;
    }
    
    public void setTestingType(String testingType)
    {
        this.testingType = testingType;
    }

    public String getProjectManager() {
        return projectManager;
    }

    public void setProjectManager(String projectManager) {
        this.projectManager = projectManager;
    }

    public Integer getBelongArea() {
        return belongArea;
    }

    public void setBelongArea(Integer belongArea) {
        this.belongArea = belongArea;
    }
}
