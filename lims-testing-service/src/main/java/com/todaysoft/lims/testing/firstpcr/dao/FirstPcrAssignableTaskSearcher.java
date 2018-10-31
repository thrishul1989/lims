package com.todaysoft.lims.testing.firstpcr.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.todaysoft.lims.persist.ISearcher;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.testing.base.entity.TestingTask;
import com.todaysoft.lims.testing.base.model.TaskSemantic;
import com.todaysoft.lims.utils.StringUtils;

public class FirstPcrAssignableTaskSearcher implements ISearcher<TestingTask>
{
    
    private String sampleCode;
    
    private String primerCode;
    
    private String combineCode;
    
    private Set<String> includeKeys;
    
    @Override
    public NamedQueryer toQuery()
    {
        StringBuffer hql = new StringBuffer(512);
        List<String> names = new ArrayList<String>();
        List<Object> values = new ArrayList<Object>();
        
        hql.append("FROM TestingTask t WHERE t.semantic = :semantic AND t.status = :assignableStatus");
        hql.append(" AND EXISTS (SELECT sa.id FROM TestingScheduleActive sa WHERE sa.taskId = t.id and sa.schedule.proccessState=0 ");

        names.add("semantic");
        values.add(TaskSemantic.PCR_ONE);
        
        names.add("assignableStatus");
        values.add(TestingTask.STATUS_ASSIGNABLE);
        
        if (StringUtils.isNotEmpty(primerCode) || StringUtils.isNotEmpty(combineCode))
        {
            hql.append(" AND EXISTS (SELECT svr.id FROM SangerVerifyRecord svr WHERE sa.schedule.verifyKey = svr.id");
            
            if (StringUtils.isNotEmpty(primerCode))
            {
                hql.append(" AND svr.primer.forwardPrimerName LIKE :forwardPrimerName");
                names.add("forwardPrimerName");
                values.add(primerCode + "%");
            }
            
            if (StringUtils.isNotEmpty(combineCode))
            {
                hql.append(" AND svr.code LIKE :combineCode");
                names.add("combineCode");
                values.add(combineCode + "%");
            }
            
            hql.append(")");
        }
        
        hql.append(")");
        
        if (StringUtils.isNotEmpty(sampleCode))
        {
            hql.append(" AND t.inputSample.receivedSample.sampleCode LIKE :sampleCode");
            names.add("sampleCode");
            values.add(sampleCode + "%");
        }
        
        if (null != includeKeys)
        {
            if (includeKeys.isEmpty())
            {
                hql.append(" AND 1 = 2 ");
            }
            else
            {
                hql.append(" AND t.id IN (:includeKeys)");
                names.add("includeKeys");
                values.add(includeKeys);
            }
        }
        
        hql.append(" order by t.ifUrgent DESC,t.resubmitCount DESC");
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
    
    public String getPrimerCode()
    {
        return primerCode;
    }
    
    public void setPrimerCode(String primerCode)
    {
        this.primerCode = primerCode;
    }
    
    public String getCombineCode()
    {
        return combineCode;
    }
    
    public void setCombineCode(String combineCode)
    {
        this.combineCode = combineCode;
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
