package com.todaysoft.lims.smm.dao.searcher;

import java.util.ArrayList;
import java.util.List;

import com.todaysoft.lims.persist.ISearcher;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.smm.entity.DataAuthority;
import com.todaysoft.lims.smm.entity.DataAuthorityRole;
import com.todaysoft.lims.smm.entity.DataAuthorityUser;
import com.todaysoft.lims.smm.entity.Role;
import com.todaysoft.lims.smm.entity.User;

public class DataAuthorityUserSearcher implements ISearcher<DataAuthorityUser>
{

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

    public DataAuthority getResourceKey()
    {
        return resourceKey;
    }

    public void setResourceKey(DataAuthority resourceKey)
    {
        this.resourceKey = resourceKey;
    }

   


    public String getUserId()
    {
        return userId;
    }

    public void setUserId(String userId)
    {
        this.userId = userId;
    }




    private Integer pageSize;
    private Integer pageNo;
    
    private DataAuthority resourceKey;
    private String userId;
    
    @Override
    public NamedQueryer toQuery()
    {
        StringBuffer hql = new StringBuffer(256);
        hql.append(" FROM DataAuthorityUser a WHERE 1=1 and a.userId in(select u.id from User u where u.deleted =false )");
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
    public Class<DataAuthorityUser> getEntityClass()
    {
        // TODO Auto-generated method stub
        return DataAuthorityUser.class;
    }
    

    private void addFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        if (null != resourceKey && com.todaysoft.lims.utils.StringUtils.isNotEmpty(resourceKey.getResourceKey()))
        {
            hql.append(" AND a.resourceKey.resourceKey = :resourceKey");
            paramNames.add("resourceKey");
            parameters.add(resourceKey.getResourceKey());
        }
        
        if (com.todaysoft.lims.utils.StringUtils.isNotEmpty(userId))
        {
            hql.append(" AND a.userId = :userId");
            paramNames.add("userId");
            parameters.add(userId);
        }
        
        
     
        
        
    }
}
