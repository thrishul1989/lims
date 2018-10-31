package com.todaysoft.lims.product.dao.searcher;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import com.todaysoft.lims.persist.ISearcher;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.product.entity.TaskConfig;

public class TaskConfigSearcher implements ISearcher<TaskConfig>
{
    private String semantic;
    
    private String name;
    
    private String type;
    
    private String outputSampleId;
    
    private String inputSampleId;
    
    @Override
    public NamedQueryer toQuery()
    {
        StringBuffer hql = new StringBuffer(512);
        
        hql.append("SELECT distinct c FROM TaskConfig c LEFT JOIN c.inputConfigs as ic WHERE c.status = 0");
        List<String> names = new ArrayList<String>();
        List<Object> values = new ArrayList<Object>();
        addSemanticFilter(hql, names, values);
        addNameFilter(hql, names, values);
        addTypeFilter(hql, names, values);
        addOutputSampleFilter(hql, names, values);
        addInputSampleFilter(hql, names, values);
        hql.append(" ORDER BY c.id");
        
        NamedQueryer queryer = new NamedQueryer();
        queryer.setHql(hql.toString());
        queryer.setNames(names);
        queryer.setValues(values);
        return queryer;
    }
    
    private void addSemanticFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        if (!StringUtils.isEmpty(semantic))
        {
            hql.append(" AND c.semantic = :semantic");
            paramNames.add("semantic");
            parameters.add(semantic);
        }
    }
    
    private void addNameFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        if (!StringUtils.isEmpty(name))
        {
            hql.append(" AND c.name LIKE :name");
            paramNames.add("name");
            parameters.add("%" + name + "%");
        }
    }
    
    private void addTypeFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        if (!StringUtils.isEmpty(type))
        {
            hql.append(" AND c.type = :type");
            paramNames.add("type");
            parameters.add(type);
        }
    }
    
    private void addOutputSampleFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        if (!StringUtils.isEmpty(outputSampleId))
        {
            hql.append(" AND c.outputSampleId = :outputSampleId");
            paramNames.add("outputSampleId");
            parameters.add(outputSampleId);
        }
    }
    
    private void addInputSampleFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        if (!StringUtils.isEmpty(inputSampleId))
        {
            hql.append(" AND ic.inputSampleId = :inputSampleId");
            paramNames.add("inputSampleId");
            parameters.add(inputSampleId);
        }
    }
    
    @Override
    public Class<TaskConfig> getEntityClass()
    {
        return TaskConfig.class;
    }
    
    public String getSemantic()
    {
        return semantic;
    }
    
    public void setSemantic(String semantic)
    {
        this.semantic = semantic;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getType()
    {
        return type;
    }
    
    public void setType(String type)
    {
        this.type = type;
    }
    
    public String getOutputSampleId()
    {
        return outputSampleId;
    }
    
    public void setOutputSampleId(String outputSampleId)
    {
        this.outputSampleId = outputSampleId;
    }
    
    public String getInputSampleId()
    {
        return inputSampleId;
    }
    
    public void setInputSampleId(String inputSampleId)
    {
        this.inputSampleId = inputSampleId;
    }
}
