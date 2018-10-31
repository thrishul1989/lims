package com.todaysoft.lims.product.model.response;

import java.util.Date;

public class SimplePhenotype
{
    private String id;
    
    private String name;
    
    private String code;
    
    private String enName;
    
    private Date createTime;
    
    private boolean deleted;
    
    private Date deleteTime;
    
    private String description;
    
    private Double phneotypeScore;
    
    public Double getPhneotypeScore()
    {
        return phneotypeScore;
    }
    
    public void setPhneotypeScore(Double phneotypeScore)
    {
        this.phneotypeScore = phneotypeScore;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getCode()
    {
        return code;
    }
    
    public void setCode(String code)
    {
        this.code = code;
    }
    
    public String getEnName()
    {
        return enName;
    }
    
    public void setEnName(String enName)
    {
        this.enName = enName;
    }
    
    public Date getCreateTime()
    {
        return createTime;
    }
    
    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }
    
    public boolean isDeleted()
    {
        return deleted;
    }
    
    public void setDeleted(boolean deleted)
    {
        this.deleted = deleted;
    }
    
    public Date getDeleteTime()
    {
        return deleteTime;
    }
    
    public void setDeleteTime(Date deleteTime)
    {
        this.deleteTime = deleteTime;
    }
    
    public String getDescription()
    {
        return description;
    }
    
    public void setDescription(String description)
    {
        this.description = description;
    }
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
}
