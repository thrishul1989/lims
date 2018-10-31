package com.todaysoft.lims.smm.model;

import java.util.List;

import com.todaysoft.lims.smm.model.response.DictAdapter;

public class DictionaryCategoryEntriesCache
{
    private int version;
    
    private String category;
    
    private List<DictAdapter> entries;
    
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
    
    public List<DictAdapter> getEntries()
    {
        return entries;
    }
    
    public void setEntries(List<DictAdapter> entries)
    {
        this.entries = entries;
    }
}
