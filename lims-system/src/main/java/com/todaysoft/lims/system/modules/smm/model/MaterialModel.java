package com.todaysoft.lims.system.modules.smm.model;

import java.util.Date;

public class MaterialModel
{
    private String id;
    
    private Integer type;//1类别、2名称
    
    private String name;
    
    private MaterialModel materialSort;
    
    private String description;
    
    private Date createTime;
    
    private boolean state;//0启用   1禁用
    
    private boolean delFlag;//0未删除  1删除
    
    private Date deleteTime;
    
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
    
    public Date getCreateTime()
    {
        return createTime;
    }
    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }
    public boolean isState()
    {
        return state;
    }
    public void setState(boolean state)
    {
        this.state = state;
    }
    public boolean isDelFlag()
    {
        return delFlag;
    }
    public void setDelFlag(boolean delFlag)
    {
        this.delFlag = delFlag;
    }
    public Date getDeleteTime()
    {
        return deleteTime;
    }
    public void setDeleteTime(Date deleteTime)
    {
        this.deleteTime = deleteTime;
    }
    
    
}
