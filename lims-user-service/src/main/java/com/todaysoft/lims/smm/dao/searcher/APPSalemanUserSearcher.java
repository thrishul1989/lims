package com.todaysoft.lims.smm.dao.searcher;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.util.StringUtils;

import com.todaysoft.lims.persist.ISearcher;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.smm.entity.User;
import com.todaysoft.lims.smm.entity.UserArchive;
import com.todaysoft.lims.utils.Collections3;

public class APPSalemanUserSearcher implements ISearcher<User>
{
    private Set<String> deptIds;

    private String username;
    
    private String name;
    
    private UserArchive archive;
    
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
        hql.append("FROM User u WHERE u.deleted = false AND u.state!=1");
        List<String> names = new ArrayList<String>();
        List<Object> values = new ArrayList<Object>();
        addUsernameFilter(hql, names, values);
        addNameFilter(hql, names, values);
        addListFilter(hql, names, values);
        addDeptFilter(hql, names, values);
        addUseridFilter(hql, names, values);
        addDeptIdFilter(hql, names, values);
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
    
    private void addListFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        if (Collections3.isNotEmpty(list))
        {
            hql.append(" and u.id not in (:list)");
            paramNames.add("list");
            parameters.add(list);
        }
    }
    
    private void addDeptFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        if (null != archive)
        {
            
            if (!StringUtils.isEmpty(archive.getDeptId()))
            {
                hql.append(" AND u.archive.deptId = :deptId");
                paramNames.add("deptId");
                parameters.add(archive.getDeptId());
            }
        }
    }

    private void addDeptIdFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        if (null != deptIds) {
            hql.append(" AND u.archive.deptId in (:deptIds) ");
            paramNames.add("deptIds");
            parameters.add(deptIds);
        }
    }
    
    private void addUseridFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        
        if (!StringUtils.isEmpty(userId))
        {
            hql.append(" AND u.id != :userId");
            paramNames.add("userId");
            parameters.add(userId);
        }
    }
    
    private List<String> list;
    
    public List<String> getList()
    {
        return list;
    }
    
    public void setList(List<String> list)
    {
        this.list = list;
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
    
    public UserArchive getArchive()
    {
        return archive;
    }
    
    public void setArchive(UserArchive archive)
    {
        this.archive = archive;
    }
    
    public String getUserId()
    {
        return userId;
    }
    
    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    public Set<String> getDeptIds() {
        return deptIds;
    }

    public void setDeptIds(Set<String> deptIds) {
        this.deptIds = deptIds;
    }
}
