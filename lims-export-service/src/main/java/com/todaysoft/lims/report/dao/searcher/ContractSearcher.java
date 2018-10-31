package com.todaysoft.lims.report.dao.searcher;

import java.util.ArrayList;
import java.util.List;

import com.todaysoft.lims.persist.ISearcher;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.report.entity.system.Contract;
import com.todaysoft.lims.utils.StringUtils;

public class ContractSearcher implements ISearcher<Contract>
{
    private String id;
    
    private String code;
    
    private String name;
    
    private String status;
    
    private Integer marketingCenter;
    
    private String signUser;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    @Override
    public Class<Contract> getEntityClass()
    {
        return Contract.class;
    }
    
    @Override
    public NamedQueryer toQuery()
    {
        StringBuffer hql = new StringBuffer(256);
        hql.append("FROM Contract s  WHERE s.deleted=0");
        List<String> paramNames = new ArrayList<String>();
        List<Object> parameters = new ArrayList<Object>();
        addCodeFilter(hql, paramNames, parameters);
        addNameFilter(hql, paramNames, parameters);
        addStatusFilter(hql, paramNames, parameters);
        addMarketingCenterFilter(hql, paramNames, parameters);
        NamedQueryer queryer = new NamedQueryer();
        queryer.setHql(hql.toString());
        queryer.setNames(paramNames);
        queryer.setValues(parameters);
        return queryer;
    }
    
    private void addCodeFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        if (!StringUtils.isEmpty(code))
        {
            hql.append(" AND s.code LIKE :code");
            paramNames.add("code");
            parameters.add("%" + code + "%");
        }
        
    }
    
    private void addStatusFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        if (!StringUtils.isEmpty(status))
        {
            hql.append(" AND s.status = :status");
            paramNames.add("status");
            parameters.add(status);
        }
    }
    
    private void addMarketingCenterFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        if (null != marketingCenter)
        {
            hql.append(" AND s.marketingCenter LIKE :marketingCenter");
            paramNames.add("marketingCenter");
            parameters.add(marketingCenter);
        }
    }
    
    private void addNameFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        if (!StringUtils.isEmpty(name))
        {
            hql.append(" AND s.name LIKE :name");
            paramNames.add("name");
            parameters.add("%" + name + "%");
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
    
    public Integer getMarketingCenter()
    {
        return marketingCenter;
    }
    
    public void setMarketingCenter(Integer marketingCenter)
    {
        this.marketingCenter = marketingCenter;
    }
    
    public String getSignUser()
    {
        return signUser;
    }
    
    public void setSignUser(String signUser)
    {
        this.signUser = signUser;
    }
    
    public String getStatus()
    {
        return status;
    }
    
    public void setStatus(String status)
    {
        this.status = status;
    }
    
}
