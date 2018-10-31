package com.todaysoft.lims.smm.dao.searcher;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import com.todaysoft.lims.persist.ISearcher;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.smm.entity.Customer;
import com.todaysoft.lims.smm.entity.DataAuthority;
import com.todaysoft.lims.smm.entity.Dict;

public class DataAuthoritySearcher implements ISearcher<DataAuthority>
{
    
    private String resourceName;
    
    private Integer pageSize;
    private Integer pageNo;
    

    public Integer getPageSize()
    {
        return pageSize;
    }

    public void setPageSize(Integer pageSize)
    {
        this.pageSize = pageSize;
    }

    public Integer getPageNo()
    {
        return pageNo;
    }

    public void setPageNo(Integer pageNo)
    {
        this.pageNo = pageNo;
    }

    public String getResourceName()
    {
        return resourceName;
    }

    public void setResourceName(String resourceName)
    {
        this.resourceName = resourceName;
    }

    @Override
    public NamedQueryer toQuery()
    {
        StringBuffer hql = new StringBuffer(256);
        hql.append("FROM DataAuthority d WHERE 1=1");
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
    public Class<DataAuthority> getEntityClass()
    {
        // TODO Auto-generated method stub
        return DataAuthority.class;
    }
    
    private void addFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        if (!StringUtils.isEmpty(resourceName))
        {
            hql.append(" AND d.resourceName LIKE :resourceName");
            paramNames.add("resourceName");
            parameters.add("%" + resourceName + "%");
        }
    }
    
    
    
}
