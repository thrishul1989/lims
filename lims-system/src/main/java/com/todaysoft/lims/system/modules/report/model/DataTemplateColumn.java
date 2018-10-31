package com.todaysoft.lims.system.modules.report.model;

public class DataTemplateColumn
{
    private String id;
    
    private DataTemplate template;
    
    private Integer columnIndex;
    
    private String columnName;
    
    private String semantic;//特殊语义标识
    
    private String dataTemplateId;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
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
    
    public String getSemantic()
    {
        return semantic;
    }
    
    public void setSemantic(String semantic)
    {
        this.semantic = semantic;
    }
    
    public String getDataTemplateId()
    {
        return dataTemplateId;
    }
    
    public void setDataTemplateId(String dataTemplateId)
    {
        this.dataTemplateId = dataTemplateId;
    }
}
