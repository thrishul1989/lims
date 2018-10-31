package com.todaysoft.lims.testing.libqc.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.util.StringUtils;

import com.todaysoft.lims.persist.ISearcher;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.testing.base.entity.TestingTask;

public class LibQcTaskSearcher implements ISearcher<TestingTask>
{
    private String orderType;
    
    private String sampleType;
    
    private String sampleCode;
    
    private Set<String> includeKeys;
    
    @Override
    public NamedQueryer toQuery()
    {
        StringBuffer hql = new StringBuffer(512);
        List<String> names = new ArrayList<String>();
        List<Object> values = new ArrayList<Object>();
        
        hql.append("FROM TestingTask t WHERE t.semantic = 'LIBRARY-QC'");
        hql.append(" AND EXISTS (SELECT id FROM TestingScheduleActiveTask sat WHERE sat.taskId = t.id");
        
        if (!StringUtils.isEmpty(orderType))
        {
            hql.append(" AND sat.order.type = :orderType");
            names.add("orderType");
            values.add(orderType);
        }
        
        if (!StringUtils.isEmpty(sampleCode))
        {
            hql.append(" AND t.inputSample.sampleCode LIKE :sampleCode");
            names.add("sampleCode");
            values.add("%" + sampleCode + "%");
        }
        
        if (null != includeKeys)
        {
            if (includeKeys.isEmpty())
            {
                hql.append(" AND 1 = 2");
            }
            else
            {
                hql.append(" AND t.id IN (:includeKeys)");
                names.add("includeKeys");
                values.add(includeKeys);
            }
        }
        
        hql.append(")");
        hql.append(" ORDER BY t.inputSample.sampleTypeId, t.resubmitCount DESC");
        
        NamedQueryer queryer = new NamedQueryer();
        queryer.setHql(hql.toString());
        queryer.setNames(names);
        queryer.setValues(values);
        return queryer;
    }
    
    @Override
    public Class<TestingTask> getEntityClass()
    {
        return TestingTask.class;
    }
    
    public String getOrderType()
    {
        return orderType;
    }
    
    public void setOrderType(String orderType)
    {
        this.orderType = orderType;
    }
    
    public String getSampleType()
    {
        return sampleType;
    }
    
    public void setSampleType(String sampleType)
    {
        this.sampleType = sampleType;
    }
    
    public String getSampleCode()
    {
        return sampleCode;
    }
    
    public void setSampleCode(String sampleCode)
    {
        this.sampleCode = sampleCode;
    }
    
    public Set<String> getIncludeKeys()
    {
        return includeKeys;
    }
    
    public void setIncludeKeys(Set<String> includeKeys)
    {
        this.includeKeys = includeKeys;
    }
}
