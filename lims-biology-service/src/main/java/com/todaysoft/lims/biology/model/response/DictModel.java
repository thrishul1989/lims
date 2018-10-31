package com.todaysoft.lims.biology.model.response;

import java.util.List;

public class DictModel
{
    private String id;
    
    private String category;
    
    private String text;
    
    private String value;
    
    private Integer sort;
    
    private Boolean editable;
    
    private List<DictModel> entries;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getCategory()
    {
        return category;
    }
    
    public void setCategory(String category)
    {
        this.category = category;
    }
    
    public String getText()
    {
        return text;
    }
    
    public void setText(String text)
    {
        this.text = text;
    }
    
    public String getValue()
    {
        return value;
    }
    
    public void setValue(String value)
    {
        this.value = value;
    }
    
    public Integer getSort()
    {
        return sort;
    }
    
    public void setSort(Integer sort)
    {
        this.sort = sort;
    }
    
    public Boolean getEditable()
    {
        return editable;
    }
    
    public void setEditable(Boolean editable)
    {
        this.editable = editable;
    }
    
    public List<DictModel> getEntries()
    {
        return entries;
    }
    
    public void setEntries(List<DictModel> entries)
    {
        this.entries = entries;
    }
}
