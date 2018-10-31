package com.todaysoft.lims.smm.model;

import com.todaysoft.lims.smm.model.response.DictAdapter;

public class DictionaryCategoryEntryCache
{
    private int version;
    
    private String category;
    
    private String value;
    
    private DictAdapter entry;
    
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
    
    public String getValue()
    {
        return value;
    }
    
    public void setValue(String value)
    {
        this.value = value;
    }
    
    public DictAdapter getEntry()
    {
        return entry;
    }
    
    public void setEntry(DictAdapter entry)
    {
        this.entry = entry;
    }
}
