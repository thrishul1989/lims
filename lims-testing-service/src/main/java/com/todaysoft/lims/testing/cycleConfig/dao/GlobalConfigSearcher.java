package com.todaysoft.lims.testing.cycleConfig.dao;

import java.util.ArrayList;
import java.util.List;

import com.todaysoft.lims.persist.ISearcher;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.testing.base.entity.ScheduleGlobalConfig;
import com.todaysoft.lims.utils.StringUtils;

public class GlobalConfigSearcher implements ISearcher<ScheduleGlobalConfig>
{
    private String eventKey;

    private Integer pageNo;
    
    private Integer pageSize;
    
    @Override
    public NamedQueryer toQuery()
    {
        StringBuffer hql = new StringBuffer(512);
        hql.append("FROM ScheduleGlobalConfig t WHERE t.disabled = 0");
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
        if(StringUtils.isNotEmpty(eventKey))
        {
            hql.append(" AND t.eventKey = :eventKey ");
            paramNames.add("eventKey");
            parameters.add(eventKey);
        }
    }
    
    @Override
    public Class<ScheduleGlobalConfig> getEntityClass()
    {
        return ScheduleGlobalConfig.class;
    }

    public Integer getPageNo()
    {
        return pageNo;
    }

    public void setPageNo(Integer pageNo)
    {
        this.pageNo = pageNo;
    }

    public Integer getPageSize()
    {
        return pageSize;
    }

    public void setPageSize(Integer pageSize)
    {
        this.pageSize = pageSize;
    }

    public String getEventKey()
    {
        return eventKey;
    }

    public void setEventKey(String eventKey)
    {
        this.eventKey = eventKey;
    }
    
}
