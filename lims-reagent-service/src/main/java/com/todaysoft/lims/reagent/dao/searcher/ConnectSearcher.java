package com.todaysoft.lims.reagent.dao.searcher;

import java.util.ArrayList;
import java.util.List;

import com.todaysoft.lims.persist.ISearcher;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.reagent.entity.Connect;

public class ConnectSearcher implements ISearcher<Connect>
{
    
    Integer connectNum;
    
    @Override
    public NamedQueryer toQuery()
    {
        StringBuffer hql = new StringBuffer(512);
        hql.append("FROM Connect c WHERE c.deleted = false");
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
    public Class<Connect> getEntityClass()
    {
        return Connect.class;
    }
    
    private void addCodeFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        if (null != connectNum && !"".equals(connectNum))
        {
            hql.append(" AND c.connectNum = :connectNum");
            paramNames.add("connectNum");
            parameters.add(connectNum);
            
        }
        hql.append(" order by c.connectNum");
    }
    
    public Integer getConnectNum()
    {
        return connectNum;
    }
    
    public void setConnectNum(Integer connectNum)
    {
        this.connectNum = connectNum;
    }
    
}
