package com.todaysoft.lims.testing.pcrngsdata.dao;

import com.todaysoft.lims.persist.ISearcher;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.testing.base.entity.TestingTask;
import com.todaysoft.lims.testing.base.model.TaskSemantic;
import com.todaysoft.lims.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class PcrNgsDataAssignableTaskSearcher implements ISearcher<Object[]>
{
    
    private Set<String> includeKeys;
    
    private String sequencingCode;
    
    @Override
    public NamedQueryer toQuery()
    {
        StringBuffer hql = new StringBuffer(512);
        List<String> names = new ArrayList<String>();
        List<Object> values = new ArrayList<Object>();
        
        hql.append("FROM TestingTask t, TechnicalAnalyTestingTask tatt WHERE t.semantic = :semantic AND t.id = tatt.taskId AND t.status = :assignableStatus ");
        hql.append(" AND EXISTS (SELECT sa.id FROM TestingScheduleActive sa WHERE sa.taskId = t.id and sa.schedule.proccessState=0 ");
        names.add("assignableStatus");
        values.add(TestingTask.STATUS_ASSIGNABLE);
        names.add("semantic");
        values.add(TaskSemantic.PCR_NGS_DATA_ANALYSIS);
        
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
        
        if (StringUtils.isNotEmpty(sequencingCode))
        {
            hql.append(" AND tatt.sequencingCode LIKE :sequencingCode");
            names.add("sequencingCode");
            values.add("%" + sequencingCode + "%");
        }
        
        hql.append(" ORDER BY t.ifUrgent DESC, t.startTime");
        
        NamedQueryer queryer = new NamedQueryer();
        queryer.setHql(hql.toString());
        queryer.setNames(names);
        queryer.setValues(values);
        return queryer;
    }
    
    @Override
    public Class<Object[]> getEntityClass()
    {
        return Object[].class;
    }
    
    public Set<String> getIncludeKeys()
    {
        return includeKeys;
    }
    
    public void setIncludeKeys(Set<String> includeKeys)
    {
        this.includeKeys = includeKeys;
    }
    
    public String getSequencingCode()
    {
        return sequencingCode;
    }
    
    public void setSequencingCode(String sequencingCode)
    {
        this.sequencingCode = sequencingCode;
    }
}
