package com.todaysoft.lims.product.dao.searcher;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import com.todaysoft.lims.persist.ISearcher;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.product.entity.TestingMethod;

public class TestingMethodSearcher implements ISearcher<TestingMethod>
{
    public Integer getType()
    {
        return type;
    }

    public void setType(Integer type)
    {
        this.type = type;
    }

    private String name;
    
    private Integer type;
    
    @Override
    public Class<TestingMethod> getEntityClass()
    {
        return TestingMethod.class;
    }
    
    @Override
    public NamedQueryer toQuery()
    {
        StringBuffer hql = new StringBuffer(512);
        hql.append("FROM TestingMethod tm WHERE tm.deleted = false");
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
            hql.append(" AND tm.name LIKE :name");
            paramNames.add("name");
            parameters.add("%" + name + "%");
        }
        if (null != type && 0 != type)
        {
            hql.append(" AND tm.type = :type");
            paramNames.add("type");
            parameters.add(type.toString());
        }
        
        
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
