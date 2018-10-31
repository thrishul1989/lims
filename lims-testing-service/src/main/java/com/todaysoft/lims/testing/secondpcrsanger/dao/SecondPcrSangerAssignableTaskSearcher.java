package com.todaysoft.lims.testing.secondpcrsanger.dao;

import com.todaysoft.lims.persist.ISearcher;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.testing.base.entity.TestingTask;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class SecondPcrSangerAssignableTaskSearcher implements ISearcher<TestingTask>
{
    private String pcrTaskCode;
    
    private Set<String> includeKeys;
    
    @Override
    public NamedQueryer toQuery()
    {
        StringBuffer hql = new StringBuffer(512);
        List<String> names = new ArrayList<String>();
        List<Object> values = new ArrayList<Object>();
        
        hql.append("FROM TestingTask t WHERE t.semantic = 'SANGER-PCR-TWO' AND t.status = :assignableStatus");
        hql.append(" AND EXISTS (SELECT sa.id FROM TestingScheduleActive sa WHERE sa.taskId = t.id and sa.schedule.proccessState = 0)");
        names.add("assignableStatus");
        values.add(TestingTask.STATUS_ASSIGNABLE);
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
        
        hql.append(" ORDER BY t.ifUrgent DESC,t.resubmitCount DESC,t.startTime");
        
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
    
    public Set<String> getIncludeKeys()
    {
        return includeKeys;
    }
    
    public void setIncludeKeys(Set<String> includeKeys)
    {
        this.includeKeys = includeKeys;
    }
    
    public String getPcrTaskCode()
    {
        return pcrTaskCode;
    }
    
    public void setPcrTaskCode(String pcrTaskCode)
    {
        this.pcrTaskCode = pcrTaskCode;
    }
}
