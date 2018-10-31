package com.todaysoft.lims.smm.model.response;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;

import com.todaysoft.lims.smm.entity.Dict;

public class DictAdapter
{
    private String id;
    
    private String category;
    
    private String text;
    
    private String value;
    
    private Integer sort;
    
    private Boolean editable;
    
    private List<DictAdapter> entries;
    
    public DictAdapter(Dict dict)
    {
        BeanUtils.copyProperties(dict, this, "entries");
        
        if (null != dict.getEntries() && !dict.getEntries().isEmpty())
        {
            this.entries = new ArrayList<DictAdapter>();
            
            this.entries.addAll(dict.getEntries().stream().map(DictAdapter::new).collect(Collectors.toList()));
        }
    }
    
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
    
    public List<DictAdapter> getEntries()
    {
        return entries;
    }
    
    public void setEntries(List<DictAdapter> entries)
    {
        this.entries = entries;
    }
}
