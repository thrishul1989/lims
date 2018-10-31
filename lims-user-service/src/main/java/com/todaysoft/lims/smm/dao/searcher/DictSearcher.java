package com.todaysoft.lims.smm.dao.searcher;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.todaysoft.lims.persist.ISearcher;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.smm.entity.Dict;

@Service
public class DictSearcher implements ISearcher<Dict>
{
    private String category;
    
    private Boolean categoryOnly;
    
    @Override
    public Class<Dict> getEntityClass()
    {
        return Dict.class;
    }
    
    @Override
    public NamedQueryer toQuery()
    {
        StringBuffer hql = new StringBuffer(256);
        hql.append("FROM Dict d WHERE 1 = 1");
        List<String> paramNames = new ArrayList<String>();
        List<Object> parameters = new ArrayList<Object>();
        
        addCategoryFilter(hql, paramNames, parameters);
        
        NamedQueryer queryer = new NamedQueryer();
        queryer.setHql(hql.toString());
        queryer.setNames(paramNames);
        queryer.setValues(parameters);
        return queryer;
    }
    
    private void addCategoryFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        if (null != categoryOnly && categoryOnly.booleanValue())
        {
            hql.append(" AND (d.parent IS NULL) AND d.id IS NOT NULL AND d.id!=''");
        }
        
        if (!StringUtils.isEmpty(category))
        {
            hql.append(" AND d.category IS NOT NULL AND (d.text LIKE :category OR d.category LIKE :category)");
            paramNames.add("category");
            parameters.add("%" + category + "%");
        }
    }
    
    public String getCategory()
    {
        return category;
    }
    
    public void setCategory(String category)
    {
        this.category = category;
    }
    
    public Boolean getCategoryOnly()
    {
        return categoryOnly;
    }
    
    public void setCategoryOnly(Boolean categoryOnly)
    {
        this.categoryOnly = categoryOnly;
    }
}
