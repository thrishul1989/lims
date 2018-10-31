package com.todaysoft.lims.testing.report.dao;

import com.todaysoft.lims.persist.ISearcher;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.testing.base.entity.DataTemplate;
import com.todaysoft.lims.testing.base.entity.DataTemplateColumn;
import com.todaysoft.lims.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class DataTemplateColumnSearcher implements ISearcher<DataTemplateColumn>
{
    private DataTemplate template;
    
    private Integer columnIndex;
    
    private String columnName;
    
    @Override
    public NamedQueryer toQuery()
    {
        StringBuffer hql = new StringBuffer(512);
        hql.append("FROM DataTemplateColumn dtc WHERE 1=1");
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
        if (null != template)
        {
            hql.append(" AND dtc.template.id = :dataTemplateId");
            paramNames.add("dataTemplateId");
            parameters.add(template.getId());
        }
        if (StringUtils.isNotEmpty(columnIndex))
        {
            hql.append(" AND dtc.columnIndex = :columnIndex");
            paramNames.add("columnIndex");
            parameters.add(columnIndex);
        }
        if (StringUtils.isNotEmpty(columnName))
        {
            hql.append(" AND dtc.columnName LIKE :columnName");
            paramNames.add("columnName");
            parameters.add("%" + columnName + "%");
        }
    }
    
    @Override
    public Class<DataTemplateColumn> getEntityClass()
    {
        return DataTemplateColumn.class;
    }
    
    public DataTemplate getTemplate()
    {
        return template;
    }
    
    public void setTemplate(DataTemplate template)
    {
        this.template = template;
    }
    
    public Integer getColumnIndex()
    {
        return columnIndex;
    }
    
    public void setColumnIndex(Integer columnIndex)
    {
        this.columnIndex = columnIndex;
    }
    
    public String getColumnName()
    {
        return columnName;
    }
    
    public void setColumnName(String columnName)
    {
        this.columnName = columnName;
    }
}
