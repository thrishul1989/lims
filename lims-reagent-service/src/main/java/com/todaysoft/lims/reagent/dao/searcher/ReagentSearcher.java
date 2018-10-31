package com.todaysoft.lims.reagent.dao.searcher;

import java.util.ArrayList;
import java.util.List;

import com.todaysoft.lims.persist.ISearcher;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.reagent.entity.Reagent;

public class ReagentSearcher implements ISearcher<Reagent>
{
    private String code;
    
    private String name;
    
    private String abbr;
    
    @Override
    public NamedQueryer toQuery()
    {
        StringBuffer hql = new StringBuffer(512);
        hql.append("FROM Reagent r WHERE r.deleted = false");
        List<String> names = new ArrayList<String>();
        List<Object> values = new ArrayList<Object>();
        addCodeFilter(hql, names, values);
        addNameFilter(hql, names, values);
        addAbbrFilter(hql, names, values);
        hql.append(" ORDER BY r.createTime DESC");
        
        NamedQueryer queryer = new NamedQueryer();
        queryer.setHql(hql.toString());
        queryer.setNames(names);
        queryer.setValues(values);
        return queryer;
    }
    
    @Override
    public Class<Reagent> getEntityClass()
    {
        return Reagent.class;
    }
    
    private void addCodeFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        if (null != code && !"".equals(code))
        {
            hql.append(" AND r.code LIKE :code");
            paramNames.add("code");
            parameters.add("%" + code + "%");
        }
    }
    
    private void addNameFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        if (null != name && !"".equals(name))
        {
            hql.append(" AND r.name LIKE :name");
            paramNames.add("name");
            parameters.add("%" + name + "%");
        }
    }
    
    private void addAbbrFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        if (null != abbr && !"".equals(abbr))
        {
            hql.append(" AND r.abbr LIKE :abbr");
            paramNames.add("abbr");
            parameters.add("%" + abbr + "%");
        }
    }
    
    public String getCode()
    {
        return code;
    }
    
    public void setCode(String code)
    {
        this.code = code;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getAbbr()
    {
        return abbr;
    }
    
    public void setAbbr(String abbr)
    {
        this.abbr = abbr;
    }
}
