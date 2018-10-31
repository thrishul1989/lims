package com.todaysoft.lims.testing.qt.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.util.StringUtils;

import com.todaysoft.lims.persist.ISearcher;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.testing.base.entity.TestingTask;
import com.todaysoft.lims.testing.base.model.TaskSemantic;

public class QtAssignableTaskSearcher implements ISearcher<TestingTask>
{
    private String sampleCode;
    
    private Set<String> includeKeys;
    
    private Integer ifUrgent;
    
    @Override
    public NamedQueryer toQuery()
    {
        StringBuffer hql = new StringBuffer(512);
        List<String> names = new ArrayList<String>();
        List<Object> values = new ArrayList<Object>();
        
        hql.append("FROM TestingTask t WHERE t.semantic = :semantic and t.status = 1");
      
        
        names.add("semantic");
        values.add(TaskSemantic.QT);
        
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
        
        if (null != ifUrgent)
        {
            if(1 != ifUrgent)
            {
                hql.append(" AND (t.ifUrgent != 1 or t.ifUrgent is null)");
            }
            else
            {
                hql.append(" AND t.ifUrgent = :ifUrgent ");
                names.add("ifUrgent");
                values.add(ifUrgent);
            }
        }
       
        hql.append(" ORDER BY t.ifUrgent DESC, t.resubmitCount DESC, t.startTime");
        
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

    public Integer getIfUrgent()
    {
        return ifUrgent;
    }

    public void setIfUrgent(Integer ifUrgent)
    {
        this.ifUrgent = ifUrgent;
    }
    
}
