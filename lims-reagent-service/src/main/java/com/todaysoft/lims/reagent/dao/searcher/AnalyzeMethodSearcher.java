package com.todaysoft.lims.reagent.dao.searcher;

import java.util.ArrayList;
import java.util.List;

import com.todaysoft.lims.persist.ISearcher;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.reagent.entity.AnalyzeMethod;
import com.todaysoft.lims.utils.StringUtils;

public class AnalyzeMethodSearcher implements ISearcher<AnalyzeMethod>
{
    
    private String name;
    
    private String coordinate; //坐标
    
    private Integer id;
    
    public Integer getId()
    {
        return id;
    }
    
    public void setId(Integer id)
    {
        this.id = id;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getCoordinate()
    {
        return coordinate;
    }
    
    public void setCoordinate(String coordinate)
    {
        this.coordinate = coordinate;
    }
    
    @Override
    public NamedQueryer toQuery()
    {
        StringBuffer hql = new StringBuffer(512);
        hql.append("FROM AnalyzeMethod s WHERE 1=1");
        List<String> paramNames = new ArrayList<String>();
        List<Object> parameters = new ArrayList<Object>();
        addFilter(hql, paramNames, parameters);
        NamedQueryer queryer = new NamedQueryer();
        queryer.setHql(hql.toString());
        queryer.setNames(paramNames);
        queryer.setValues(parameters);
        return queryer;
    }
    
    private void addFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        
        if (StringUtils.isNotEmpty(id))
        {
            hql.append(" AND s.id =:id");
            paramNames.add("id");
            parameters.add(id);
        }
        
        if (StringUtils.isNotEmpty(name))
        {
            hql.append(" AND s.name LIKE :name");
            paramNames.add("name");
            parameters.add("%" + name + "%");
        }
        
        if (StringUtils.isNotEmpty(coordinate))
        {
            hql.append(" AND s.coordinate LIKE :coordinate");
            paramNames.add("coordinate");
            parameters.add("%" + coordinate + "%");
        }
        
    }
    
    @Override
    public Class<AnalyzeMethod> getEntityClass()
    {
        return AnalyzeMethod.class;
    }
    
}
