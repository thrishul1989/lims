package com.todaysoft.lims.smm.dao.searcher;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import com.todaysoft.lims.persist.ISearcher;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.smm.entity.Role;

public class RoleSearcher implements ISearcher<Role>
{
    private String id;

    private String name;
    
    @Override
    public NamedQueryer toQuery()
    {
        StringBuffer hql = new StringBuffer(256);
        hql.append("FROM Role r WHERE r.deleted = false");
        List<String> names = new ArrayList<String>();
        List<Object> values = new ArrayList<Object>();
        addNameFilter(hql, names, values);
        NamedQueryer queryer = new NamedQueryer();
        queryer.setHql(hql.toString());
        queryer.setNames(names);
        queryer.setValues(values);
        return queryer;
    }
    
    private void addNameFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        if (!StringUtils.isEmpty(name))
        {
            hql.append(" AND r.name LIKE :name");
            paramNames.add("name");
            parameters.add("%" + name + "%");
        }
    }
    
    @Override
    public Class<Role> getEntityClass()
    {
        return Role.class;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
