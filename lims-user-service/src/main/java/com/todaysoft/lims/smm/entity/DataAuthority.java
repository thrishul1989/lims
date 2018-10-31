package com.todaysoft.lims.smm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.todaysoft.lims.persist.UuidKeyEntity;
@Entity
@Table(name = "LIMS_DATA_AUTHORITY")
public class DataAuthority 

{
    
    private String resourceKey;
    private String resourceName;
    private Integer defaultConfig;
    private String specialDepts;
    
    @Id
    @Column(name = "RESOURCE_KEY")
    public String getResourceKey()
    {
        return resourceKey;
    }
    public void setResourceKey(String resourceKey)
    {
        this.resourceKey = resourceKey;
    }
    
    @Column(name = "RESOURCE_NAME")
    public String getResourceName()
    {
        return resourceName;
    }
    public void setResourceName(String resourceName)
    {
        this.resourceName = resourceName;
    }
    @Column(name = "DEFAULT_CONFIG")
    public Integer getDefaultConfig()
    {
        return defaultConfig;
    }
    public void setDefaultConfig(Integer defaultConfig)
    {
        this.defaultConfig = defaultConfig;
    }
    
    @Column(name = "SPECIAL_DEPTS")
    public String getSpecialDepts()
    {
        return specialDepts;
    }
    public void setSpecialDepts(String specialDepts)
    {
        this.specialDepts = specialDepts;
    }
    
}
