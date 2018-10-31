package com.todaysoft.lims.testing.cycleConfig.dao;

import java.util.ArrayList;
import java.util.List;

import com.todaysoft.lims.persist.ISearcher;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.testing.base.entity.ScheduleTestingConfig;
import com.todaysoft.lims.utils.StringUtils;

public class TestingConfigSearcher implements ISearcher<ScheduleTestingConfig>
{

    private String id;
    
    private String testingMethodId;
    
    private String configName;
    
    private String velidateConfigName;
    
    private Integer pageNo;
    
    private Integer pageSize;
    
    @Override
    public NamedQueryer toQuery()
    {
        StringBuffer hql = new StringBuffer(512);
        hql.append("FROM ScheduleTestingConfig t WHERE t.delFlag = 0");
        List<String> paramNames = new ArrayList<String>();
        List<Object> parameters = new ArrayList<Object>();
        addFilter(hql, paramNames, parameters);
        hql.append("ORDER BY t.testingMethod.type DESC,t.createTime DESC");
        NamedQueryer queryer = new NamedQueryer();
        queryer.setHql(hql.toString());
        queryer.setNames(paramNames);
        queryer.setValues(parameters);
        return queryer;
    }

    private void addFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        if(StringUtils.isNotEmpty(testingMethodId))
        {
            hql.append(" AND t.testingMethod.id = :testingMethodId ");
            paramNames.add("testingMethodId");
            parameters.add(testingMethodId);
        }
        if(StringUtils.isNotEmpty(configName))
        {
            hql.append(" AND t.configName like :configName ");
            paramNames.add("configName");
            parameters.add("%" + configName + "%");
        }
        if(StringUtils.isNotEmpty(velidateConfigName))
        {
            hql.append(" AND t.configName = :velidateConfigName ");
            paramNames.add("velidateConfigName");
            parameters.add(velidateConfigName);
        }
    }
    @Override
    public Class<ScheduleTestingConfig> getEntityClass()
    {
        return ScheduleTestingConfig.class;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getTestingMethodId()
    {
        return testingMethodId;
    }

    public void setTestingMethodId(String testingMethodId)
    {
        this.testingMethodId = testingMethodId;
    }

    public String getConfigName()
    {
        return configName;
    }

    public void setConfigName(String configName)
    {
        this.configName = configName;
    }

    public String getVelidateConfigName()
    {
        return velidateConfigName;
    }

    public void setVelidateConfigName(String velidateConfigName)
    {
        this.velidateConfigName = velidateConfigName;
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
    
}
