package com.todaysoft.lims.system.modules.smm.service.request;

import com.todaysoft.lims.system.modules.smm.model.MaterialModel;

public class MaterialCreateRequest
{
    private String id;
    private Integer type;
    private String name;
    private MaterialModel materialSort;
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
    
    public MaterialModel getMaterialSort()
    {
        return materialSort;
    }
    public void setMaterialSort(MaterialModel materialSort)
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
