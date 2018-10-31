package com.todaysoft.lims.testing.base.dao.searcher;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import com.todaysoft.lims.persist.ISearcher;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.testing.base.entity.TestingSample;

public class TestingSampleSearcher implements ISearcher<TestingSample>
{
    private String sampleCode;
    
    private String sampleTypeId;
    
    @Override
    public NamedQueryer toQuery()
    {
        StringBuffer hql = new StringBuffer(512);
        List<String> names = new ArrayList<String>();
        List<Object> values = new ArrayList<Object>();
        
        hql.append("FROM TestingSample s WHERE 1 = 1");
        addSampleCodeFilter(hql, names, values);
        addSampleTypeIdFilter(hql, names, values);
        
        NamedQueryer queryer = new NamedQueryer();
        queryer.setHql(hql.toString());
        queryer.setNames(names);
        queryer.setValues(values);
        return queryer;
    }
    
    private void addSampleCodeFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        if (!StringUtils.isEmpty(sampleCode))
        {
            hql.append(" AND s.sampleCode = :sampleCode");
            paramNames.add("sampleCode");
            parameters.add(sampleCode);
        }
    }
    
    private void addSampleTypeIdFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        if (!StringUtils.isEmpty(sampleTypeId))
        {
            hql.append(" AND s.sampleTypeId = :sampleTypeId");
            paramNames.add("sampleTypeId");
            parameters.add(sampleTypeId);
        }
    }
    
    @Override
    public Class<TestingSample> getEntityClass()
    {
        return TestingSample.class;
    }
    
    public String getSampleCode()
    {
        return sampleCode;
    }
    
    public void setSampleCode(String sampleCode)
    {
        this.sampleCode = sampleCode;
    }
    
    public String getSampleTypeId()
    {
        return sampleTypeId;
    }
    
    public void setSampleTypeId(String sampleTypeId)
    {
        this.sampleTypeId = sampleTypeId;
    }
}
