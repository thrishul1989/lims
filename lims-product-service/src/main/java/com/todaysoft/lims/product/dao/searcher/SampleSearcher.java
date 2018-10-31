package com.todaysoft.lims.product.dao.searcher;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.util.StringUtils;

import com.todaysoft.lims.persist.ISearcher;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.product.entity.MetadataSample;

public class SampleSearcher implements ISearcher<MetadataSample>
{
    private String name;
    
    private Integer type;
    
    private Set<String> includeKeys;
    
    @Override
    public Class<MetadataSample> getEntityClass()
    {
        return MetadataSample.class;
    }
    
    @Override
    public NamedQueryer toQuery()
    {
        StringBuffer hql = new StringBuffer(512);
        hql.append("FROM MetadataSample s WHERE s.delFlag = 0");
        List<String> names = new ArrayList<String>();
        List<Object> values = new ArrayList<Object>();
        addNameFilter(hql, names, values);
        addTypeFilter(hql, names, values);
        addIncludeKeysFilter(hql, names, values);
        NamedQueryer queryer = new NamedQueryer();
        queryer.setHql(hql.toString());
        queryer.setNames(names);
        queryer.setValues(values);
        return queryer;
    }
    
    private void addNameFilter(StringBuffer hql, List<String> names, List<Object> values)
    {
        if (!StringUtils.isEmpty(name))
        {
            hql.append(" AND s.name LIKE :name");
            names.add("name");
            values.add("%" + name + "%");
        }
    }
    
    private void addTypeFilter(StringBuffer hql, List<String> names, List<Object> values)
    {
        if (!StringUtils.isEmpty(type))
        {
            hql.append(" AND s.intermediate = :type");
            names.add("type");
            values.add(type);
        }
    }
    
    private void addIncludeKeysFilter(StringBuffer hql, List<String> names, List<Object> values)
    {
        if (null != includeKeys)
        {
            if (!includeKeys.isEmpty())
            {
                hql.append(" AND s.id IN :includeKeys");
                names.add("includeKeys");
                values.add(includeKeys);
            }
            else
            {
                hql.append(" AND 1 = 2");
            }
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
    
    public Integer getType()
    {
        return type;
    }
    
    public void setType(Integer type)
    {
        this.type = type;
    }
    
    public Set<String> getIncludeKeys()
    {
        return includeKeys;
    }
    
    public void setIncludeKeys(Set<String> includeKeys)
    {
        this.includeKeys = includeKeys;
    }
}