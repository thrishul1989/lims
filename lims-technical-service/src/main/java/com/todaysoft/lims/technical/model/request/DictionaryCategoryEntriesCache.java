package com.todaysoft.lims.technical.model.request;

import java.util.List;

import com.todaysoft.lims.technical.mybatis.entity.Dict;

public class DictionaryCategoryEntriesCache
{
    private int version;
    
    private String category;
    
    private List<Dict> entries;
    
    public int getVersion()
    {
        return version;
    }
    
    public void setVersion(int version)
    {
        this.version = version;
    }
    
    public String getCategory()
    {
        return category;
    }
    
    public void setCategory(String category)
    {
        this.category = category;
    }
    
    public List<Dict> getEntries()
    {
        return entries;
    }
    
    public void setEntries(List<Dict> entries)
    {
        this.entries = entries;
    }
}
