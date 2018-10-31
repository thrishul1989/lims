package com.todaysoft.lims.product.dao.searcher;

import java.util.ArrayList;
import java.util.List;

import com.todaysoft.lims.persist.ISearcher;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.product.entity.Config;
import com.todaysoft.lims.utils.StringUtils;

public class ConfigSearcher implements ISearcher<Config>
{
    private String key;
    
    @Override
    public NamedQueryer toQuery()
    {
        StringBuffer hql = new StringBuffer(512);
        hql.append("FROM Config c WHERE c.maintainType = 1");
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
    public Class<Config> getEntityClass()
    {
        // TODO Auto-generated method stub
        return Config.class;
    }
    
    private void addFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        if (StringUtils.isNotEmpty(key))
        {
            hql.append(" AND c.key LIKE :key");
            paramNames.add("key");
            parameters.add("%" + key + "%");
        }
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
