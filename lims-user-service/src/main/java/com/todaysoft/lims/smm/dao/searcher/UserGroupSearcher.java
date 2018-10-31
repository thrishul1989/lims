package com.todaysoft.lims.smm.dao.searcher;

import java.util.ArrayList;
import java.util.List;

import com.todaysoft.lims.persist.ISearcher;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.smm.entity.UserGroup;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.StringUtils;

public class UserGroupSearcher implements ISearcher<UserGroup>
{
    
    private String groupName;
    
    private String name;
    
    private List<String> userGroupIds;
    
    @Override
    public NamedQueryer toQuery()
    {
        StringBuffer hql = new StringBuffer(256);
        hql.append(" FROM UserGroup u WHERE u.deleted = false");
        List<String> names = new ArrayList<String>();
        List<Object> values = new ArrayList<Object>();
        addGroupNameFilter(hql, names, values);
        addUserGroupIdsFilter(hql, names, values);
        hql.append(" ORDER BY u.createTime DESC,  u.groupName");
        NamedQueryer queryer = new NamedQueryer();
        queryer.setHql(hql.toString());
        queryer.setNames(names);
        queryer.setValues(values);
        return queryer;
    }
    
    private void addGroupNameFilter(StringBuffer hql, List<String> names, List<Object> values)
    {
        if (StringUtils.isNotEmpty(groupName))
        {
            hql.append(" AND u.groupName like :groupName");
            names.add("groupName");
            values.add("%" + groupName + "%");
        }
    }
    
    private void addUserGroupIdsFilter(StringBuffer hql, List<String> names, List<Object> values)
    {
        if (!Collections3.isEmpty(userGroupIds) && !StringUtils.isEmpty(name))
        {
            hql.append(" AND u.id in (");
            for (int i = 0; i < userGroupIds.size(); i++)
            {
                if (i != (userGroupIds.size() - 1) && userGroupIds.size() > 1)
                {
                    hql.append(" '" + userGroupIds.get(i) + "',");
                }
                else if (i == (userGroupIds.size() - 1) && userGroupIds.size() > 1)
                {
                    hql.append(" '" + userGroupIds.get(i) + "')");
                }
                else
                {
                    hql.append(" '" + userGroupIds.get(i) + "')");
                }
            }
        }
        else if (!StringUtils.isEmpty(name))
        {
            hql.append(" AND u.groupName like :groupName");
            names.add("groupName");
            values.add("%" + name + "%");
        }
    }
    
    @Override
    public Class<UserGroup> getEntityClass()
    {
        // TODO Auto-generated method stub
        return UserGroup.class;
    }
    
    public String getGroupName()
    {
        return groupName;
    }
    
    public void setGroupName(String groupName)
    {
        this.groupName = groupName;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public List<String> getUserGroupIds()
    {
        return userGroupIds;
    }
    
    public void setUserGroupIds(List<String> userGroupIds)
    {
        this.userGroupIds = userGroupIds;
    }
    
}
