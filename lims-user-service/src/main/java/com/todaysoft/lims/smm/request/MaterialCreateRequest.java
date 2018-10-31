package com.todaysoft.lims.smm.request;

import com.todaysoft.lims.smm.entity.Material;

public class MaterialCreateRequest
{
    private String id;
    private Integer type;
    private String name;
    private Material materialSort;
    private String description;
    
    public String getId()
    {
        return id;
    }
    public void setId(String id)
    {
        this.id = id;
    }
    public Integer getType()
    {
        return type;
    }
    public void setType(Integer type)
    {
        this.type = type;
    }
    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    public Material getMaterialSort()
    {
        return materialSort;
    }
    public void setMaterialSort(Material materialSort)
    {
        this.materialSort = materialSort;
    }
    public String getDescription()
    {
        return description;
    }
    public void setDescription(String description)
    {
        this.description = description;
    }
    
    
}
