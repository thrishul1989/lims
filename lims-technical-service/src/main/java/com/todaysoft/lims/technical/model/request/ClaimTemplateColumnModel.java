package com.todaysoft.lims.technical.model.request;

import java.util.List;

import com.todaysoft.lims.technical.mybatis.entity.FilterItems;

public class ClaimTemplateColumnModel
{
    private String id;
    
    private String templateId;
    
    private String dataColumnId;
    
    private String filterName;
    
    private String defaultValue;
    
    private Integer columnIndex;
    
    private String dataTemplateColumnId;
    
    private String rangeValue1;
    
    private String rangeValue2;
    
    private String radioValue;
    
    private String type;
    
    private String semantic;
    
    private List<FilterItems> filterItemsList;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getTemplateId()
    {
        return templateId;
    }
    
    public void setTemplateId(String templateId)
    {
        this.templateId = templateId;
    }
    
    public String getFilterName()
    {
        return filterName;
    }
    
    public void setFilterName(String filterName)
    {
        this.filterName = filterName;
    }
    
    public String getDefaultValue()
    {
        return defaultValue;
    }
    
    public void setDefaultValue(String defaultValue)
    {
        this.defaultValue = defaultValue;
    }
    
    public Integer getColumnIndex()
    {
        return columnIndex;
    }
    
    public void setColumnIndex(Integer columnIndex)
    {
        this.columnIndex = columnIndex;
    }
    
    public String getDataTemplateColumnId()
    {
        return dataTemplateColumnId;
    }
    
    public void setDataTemplateColumnId(String dataTemplateColumnId)
    {
        this.dataTemplateColumnId = dataTemplateColumnId;
    }
    
    public String getRangeValue1()
    {
        return rangeValue1;
    }
    
    public void setRangeValue1(String rangeValue1)
    {
        this.rangeValue1 = rangeValue1;
    }
    
    public String getRangeValue2()
    {
        return rangeValue2;
    }
    
    public void setRangeValue2(String rangeValue2)
    {
        this.rangeValue2 = rangeValue2;
    }
    
    public String getRadioValue()
    {
        return radioValue;
    }
    
    public void setRadioValue(String radioValue)
    {
        this.radioValue = radioValue;
    }
    
    public String getDataColumnId()
    {
        return dataColumnId;
    }
    
    public void setDataColumnId(String dataColumnId)
    {
        this.dataColumnId = dataColumnId;
    }
    
    public String getType()
    {
        return type;
    }
    
    public void setType(String type)
    {
        this.type = type;
    }
    
    public String getSemantic()
    {
        return semantic;
    }
    
    public void setSemantic(String semantic)
    {
        this.semantic = semantic;
    }
    
    public List<FilterItems> getFilterItemsList()
    {
        return filterItemsList;
    }
    
    public void setFilterItemsList(List<FilterItems> filterItemsList)
    {
        this.filterItemsList = filterItemsList;
    }
    
}