package com.todaysoft.lims.system.modules.report.model;

public class ContentTableColumn
{
    private String id;
    
    private Integer columnIndex;
    
    private String variableType;
    
    private BuiltinVariable builtinVariable;
    
    private String dataType;
    
    private DataTemplateColumn dataTemplateColumn;
    
    private String imgUrl;
    
    private String builtinId;
    
    private String dataTemplateColumnId;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public Integer getColumnIndex()
    {
        return columnIndex;
    }
    
    public void setColumnIndex(Integer columnIndex)
    {
        this.columnIndex = columnIndex;
    }
    
    public String getVariableType()
    {
        return variableType;
    }
    
    public void setVariableType(String variableType)
    {
        this.variableType = variableType;
    }
    
    public BuiltinVariable getBuiltinVariable()
    {
        return builtinVariable;
    }
    
    public void setBuiltinVariable(BuiltinVariable builtinVariable)
    {
        this.builtinVariable = builtinVariable;
    }
    
    public String getDataType()
    {
        return dataType;
    }
    
    public void setDataType(String dataType)
    {
        this.dataType = dataType;
    }
    
    public DataTemplateColumn getDataTemplateColumn()
    {
        return dataTemplateColumn;
    }
    
    public void setDataTemplateColumn(DataTemplateColumn dataTemplateColumn)
    {
        this.dataTemplateColumn = dataTemplateColumn;
    }
    
    public String getImgUrl()
    {
        return imgUrl;
    }
    
    public void setImgUrl(String imgUrl)
    {
        this.imgUrl = imgUrl;
    }
    
    public String getBuiltinId()
    {
        return builtinId;
    }
    
    public void setBuiltinId(String builtinId)
    {
        this.builtinId = builtinId;
    }
    
    public String getDataTemplateColumnId()
    {
        return dataTemplateColumnId;
    }
    
    public void setDataTemplateColumnId(String dataTemplateColumnId)
    {
        this.dataTemplateColumnId = dataTemplateColumnId;
    }
}
