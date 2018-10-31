package com.todaysoft.lims.product.dao.searcher;

import java.util.ArrayList;
import java.util.List;

import com.todaysoft.lims.persist.ISearcher;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.product.entity.disease.Gene;
import com.todaysoft.lims.utils.StringUtils;

public class GeneSearcher implements ISearcher<Gene>
{
    
    private String code;
    
    private String name;
    
    private String symbol;
    
    private String key;
    
    @Override
    public NamedQueryer toQuery()
    {
        StringBuffer hql = new StringBuffer(512);
        hql.append("FROM Gene s WHERE 1=1 and s.deleted =false ");
        List<String> paramNames = new ArrayList<String>();
        List<Object> parameters = new ArrayList<Object>();
        addFilter(hql, paramNames, parameters);
        NamedQueryer queryer = new NamedQueryer();
        queryer.setHql(hql.toString());
        queryer.setNames(paramNames);
        queryer.setValues(parameters);
        return queryer;
    }
    
    @Override
    public Class<Gene> getEntityClass()
    {
        return Gene.class;
    }
    
    private void addFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        if (StringUtils.isNotEmpty(code))
        {
            hql.append(" AND s.code LIKE :code");
            paramNames.add("code");
            parameters.add("%" + code + "%");
        }
        if (StringUtils.isNotEmpty(name))
        {
            hql.append(" AND s.name LIKE :name");
            paramNames.add("name");
            parameters.add("%" + name + "%");
        }
        if (StringUtils.isNotEmpty(key))
        {
            hql.append(" AND s.symbol LIKE :symbol order by length(s.symbol) ");
            paramNames.add("symbol");
            parameters.add("%" + key + "%");
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
    
    public String getSymbol()
    {
        return symbol;
    }
    
    public void setSymbol(String symbol)
    {
        this.symbol = symbol;
    }
    
    public String getKey()
    {
        return key;
    }
    
    public void setKey(String key)
    {
        this.key = key;
    }
    
}
