package com.todaysoft.lims.reagent.dao.searcher;

import java.util.ArrayList;
import java.util.List;

import com.todaysoft.lims.persist.ISearcher;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.reagent.entity.ExperimentProduct;
import com.todaysoft.lims.reagent.entity.StoreContainer;

public class ExperimentProductSearcher implements ISearcher<ExperimentProduct>
{
    private String code;
    
    private String name;
    
    private StoreContainer storeContainer;
    
    @Override
    public NamedQueryer toQuery()
    {
        StringBuffer hql = new StringBuffer(512);
        hql.append("FROM ExperimentProduct e WHERE 1=1");
        List<String> paramNames = new ArrayList<String>();
        List<Object> parameters = new ArrayList<Object>();
        addCodeFilter(hql, paramNames, parameters);
        addNameFilter(hql, paramNames, parameters);
        addStoreContainerFilter(hql, paramNames, parameters);
        NamedQueryer queryer = new NamedQueryer();
        queryer.setHql(hql.toString());
        queryer.setNames(paramNames);
        queryer.setValues(parameters);
        return queryer;
    }
    
    @Override
    public Class<ExperimentProduct> getEntityClass()
    {
        return ExperimentProduct.class;
    }
    
    private void addCodeFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        if (null != code && !"".equals(code))
        {
            hql.append(" AND e.code LIKE :code");
            paramNames.add("code");
            parameters.add("%" + code + "%");
        }
    }
    
    private void addNameFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        if (null != name && !"".equals(name))
        {
            hql.append(" AND e.name LIKE :name");
            paramNames.add("name");
            parameters.add("%" + name + "%");
        }
    }
    
    private void addStoreContainerFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        if (null != storeContainer)
        {
            if (null != storeContainer.getStorageType() && !"".equals(storeContainer.getStorageType()))
            {
                hql.append(" AND e.storeContainer.deviceType = :containerType");
                paramNames.add("containerType");
                parameters.add(storeContainer.getStorageType());
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
    
    public String getCode()
    {
        return code;
    }
    
    public void setCode(String code)
    {
        this.code = code;
    }
    
    public StoreContainer getStoreContainer()
    {
        return storeContainer;
    }
    
    public void setStoreContainer(StoreContainer storeContainer)
    {
        this.storeContainer = storeContainer;
    }
    
}
