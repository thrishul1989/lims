package com.todaysoft.lims.gateway.model;

import java.util.List;

public class Authority
{
    private String id;
    
    private String name;
    
    private Integer sort;
    
    private List<Authority> subauthorities;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public Integer getSort()
    {
        return sort;
    }
    
    public void setSort(Integer sort)
    {
        this.sort = sort;
    }
    
    public List<Authority> getSubauthorities()
    {
        return subauthorities;
    }
    
    public void setSubauthorities(List<Authority> subauthorities)
    {
        this.subauthorities = subauthorities;
    }
}
