package com.todaysoft.lims.reagent.dao.searcher;

import java.util.ArrayList;
import java.util.List;

import com.todaysoft.lims.persist.ISearcher;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.reagent.entity.Supplier;
import com.todaysoft.lims.utils.StringUtils;

public class ProducterManageSearcher implements ISearcher<Supplier>
{
    
    private String producterNo;
    
    private String state;
    
    public String getProducterNo()
    {
        return producterNo;
    }
    
    public void setProducterNo(String producterNo)
    {
        this.producterNo = producterNo;
    }
    
    public String getState()
    {
        return state;
    }
    
    public void setState(String state)
    {
        this.state = state;
    }
    
    @Override
    public NamedQueryer toQuery()
    {
        StringBuffer hql = new StringBuffer(512);
        hql.append("FROM ProducterManage p WHERE 1=1");
        List<String> paramNames = new ArrayList<String>();
        List<Object> parameters = new ArrayList<Object>();
        addCodeFilter(hql, paramNames, parameters);
        NamedQueryer queryer = new NamedQueryer();
        queryer.setHql(hql.toString());
        queryer.setNames(paramNames);
        queryer.setValues(parameters);
        return queryer;
    }
    
    @Override
    public Class<Supplier> getEntityClass()
    {
        return Supplier.class;
    }
    
    private void addCodeFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        if (StringUtils.isNotEmpty(producterNo))
        {
            hql.append(" AND p.producterNo LIKE :producterNo");
            paramNames.add("producterNo");
            parameters.add("%" + producterNo + "%");
        }
        
        if (StringUtils.isNotEmpty(state))
        {
            hql.append(" AND p.state =:state");
            paramNames.add("state");
            parameters.add(state);
        }
    }
    
}
