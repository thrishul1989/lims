package com.todaysoft.lims.technical.model.request;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;

import com.todaysoft.lims.technical.mybatis.entity.Dict;

public class DictModel
{
    private String id;
    
    private String category;
    
    private String text;
    
    private String value;
    
    private Integer sort;
    
    private Boolean editable;
    
    private List<DictModel> entries;
    
    public DictModel(Dict dict)
    {
        BeanUtils.copyProperties(dict, this, "entries");
        
        if (null != dict.getEntries() && !dict.getEntries().isEmpty())
        {
            this.entries = new ArrayList<DictModel>();
            
            this.entries.addAll(dict.getEntries().stream().map(DictModel::new).collect(Collectors.toList()));
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
    
    public List<DictModel> getEntries()
    {
        return entries;
    }
    
    public void setEntries(List<DictModel> entries)
    {
        this.entries = entries;
    }
}
