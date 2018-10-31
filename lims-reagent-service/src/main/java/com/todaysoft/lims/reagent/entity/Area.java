package com.todaysoft.lims.reagent.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SYS_AREA")
public class Area
{
    private String id;
    
    private Integer orders;
    
    private String fullName;
    
    private String name;
    
    private String py;
    
    private String pinyin;
    
    private String parentIds;
    
    private String parentId;
    
    private String type;
    
    private Date createDate;
    
    private String createBy;
    
    private Date updateDate;
    
    private String updateBy;
    
    private String regionId;
    
    @Id
    @Column(name = "ID")
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    @Column(name = "ORDERS")
    public Integer getOrders()
    {
        return orders;
    }
    
    public void setOrders(Integer orders)
    {
        this.orders = orders;
    }
    
    @Column(name = "FULL_NAME")
    public String getFullName()
    {
        return fullName;
    }
    
    public void setFullName(String fullName)
    {
        this.fullName = fullName;
    }
    
    @Column(name = "NAME")
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    @Column(name = "PY")
    public String getPy()
    {
        return py;
    }
    
    public void setPy(String py)
    {
        this.py = py;
    }
    
    @Column(name = "PINYIN")
    public String getPinyin()
    {
        return pinyin;
    }
    
    public void setPinyin(String pinyin)
    {
        this.pinyin = pinyin;
    }
    
    @Column(name = "PARENT_IDS")
    public String getParentIds()
    {
        return parentIds;
    }
    
    public void setParentIds(String parentIds)
    {
        this.parentIds = parentIds;
    }
    
    @Column(name = "PARENT_ID")
    public String getParentId()
    {
        return parentId;
    }
    
    public void setParentId(String parentId)
    {
        this.parentId = parentId;
    }
    
    @Column(name = "TYPE")
    public String getType()
    {
        return type;
    }
    
    public void setType(String type)
    {
        this.type = type;
    }
    
    @Column(name = "CREATE_DATE")
    public Date getCreateDate()
    {
        return createDate;
    }
    
    public void setCreateDate(Date createDate)
    {
        this.createDate = createDate;
    }
    
    @Column(name = "CREATE_BY")
    public String getCreateBy()
    {
        return createBy;
    }
    
    public void setCreateBy(String createBy)
    {
        this.createBy = createBy;
    }
    
    @Column(name = "UPDATE_DATE")
    public Date getUpdateDate()
    {
        return updateDate;
    }
    
    public void setUpdateDate(Date updateDate)
    {
        this.updateDate = updateDate;
    }
    
    @Column(name = "UPDATE_BY")
    public String getUpdateBy()
    {
        return updateBy;
    }
    
    public void setUpdateBy(String updateBy)
    {
        this.updateBy = updateBy;
    }
    
    @Column(name = "REGION_ID")
    public String getRegionId()
    {
        return regionId;
    }
    
    public void setRegionId(String regionId)
    {
        this.regionId = regionId;
    }
    
}
