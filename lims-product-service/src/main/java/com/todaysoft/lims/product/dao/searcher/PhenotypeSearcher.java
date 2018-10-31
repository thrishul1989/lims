package com.todaysoft.lims.product.dao.searcher;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import com.todaysoft.lims.persist.ISearcher;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.product.entity.Phenotype;

public class PhenotypeSearcher implements ISearcher<Phenotype>
{
    private String code;
    
    private String name;
    
    @Override
    public Class<Phenotype> getEntityClass()
    {
        return Phenotype.class;
    }
    
    @Override
    public NamedQueryer toQuery()
    {
        StringBuffer hql = new StringBuffer(256);
        hql.append("FROM Phenotype p WHERE 1=1 and p.deleted =false ");
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
        if (!StringUtils.isEmpty(code))
        {
            hql.append(" AND p.code LIKE :code");
            paramNames.add("code");
            parameters.add("%" + code + "%");
        }
        
        if (!StringUtils.isEmpty(name))
        {
            hql.append(" AND p.name LIKE :name");
            paramNames.add("name");
            parameters.add("%" + name + "%");
        }
        
        hql.append(" order by p.createTime desc");
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
    
}
