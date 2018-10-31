package com.todaysoft.lims.product.dao.searcher;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import com.todaysoft.lims.persist.ISearcher;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.product.entity.TaskInputConfig;

public class TestingNodeReagentKitConfigSearcher implements ISearcher<TaskInputConfig>
{
    private String testingNode;
    
    private String testingSample;
    
    @Override
    public Class<TaskInputConfig> getEntityClass()
    {
        return TaskInputConfig.class;
    }
    
    @Override
    public NamedQueryer toQuery()
    {
        StringBuffer hql = new StringBuffer(512);
        List<String> names = new ArrayList<String>();
        List<Object> values = new ArrayList<Object>();
        
        hql.append("FROM TaskInputConfig ic WHERE 1 = 1");
        addTestingNodeFilter(hql, names, values);
        addTestingSampleFilter(hql, names, values);
        
        NamedQueryer queryer = new NamedQueryer();
        queryer.setHql(hql.toString());
        queryer.setNames(names);
        queryer.setValues(values);
        return queryer;
    }
    
    private void addTestingNodeFilter(StringBuffer hql, List<String> names, List<Object> values)
    {
        if (!StringUtils.isEmpty(testingNode))
        {
            hql.append(" AND ic.task.semantic = :semantic");
            names.add("semantic");
            values.add(testingNode);
        }
    }
    
    private void addTestingSampleFilter(StringBuffer hql, List<String> names, List<Object> values)
    {
        if (!StringUtils.isEmpty(testingSample))
        {
            hql.append(" AND ic.inputSampleId = :sampleId");
            names.add("sampleId");
            values.add(testingSample);
        }
    }
    
    public String getTestingNode()
    {
        return testingNode;
    }
    
    public void setTestingNode(String testingNode)
    {
        this.testingNode = testingNode;
    }
    
    public String getTestingSample()
    {
        return testingSample;
    }
    
    public void setTestingSample(String testingSample)
    {
        this.testingSample = testingSample;
    }
}
