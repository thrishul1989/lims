package com.todaysoft.lims.system.model.vo;

import java.util.List;



public class Authority
{
    public Authority getParent()
    {
        return parent;
    }

    public void setParent(Authority parent)
    {
        this.parent = parent;
    }

    private String id;
    
    private String name;
    
    private Integer sort;
    
    private List<Authority> subauthorities;
    private Authority parent;
    
    
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
