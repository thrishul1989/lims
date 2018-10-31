package com.todaysoft.lims.system.modules.smm.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.todaysoft.lims.persist.UuidKeyEntity;

public class DataAuthority 

{
    
    private String resourceKey;
    private String resourceName;
    private Integer defaultConfig;
    private String specialDepts;
    
    private Integer pageSize;
    private Integer pageNo;
    public Integer getPageSize()
    {
        return pageSize;
    }
    public void setPageSize(Integer pageSize)
    {
        this.pageSize = pageSize;
    }
    public Integer getPageNo()
    {
        return pageNo;
    }
    public void setPageNo(Integer pageNo)
    {
        this.pageNo = pageNo;
    }
    public String getResourceKey()
    {
        return resourceKey;
    }
    public void setResourceKey(String resourceKey)
    {
        this.resourceKey = resourceKey;
    }
    public String getResourceName()
    {
        return resourceName;
    }
    public void setResourceName(String resourceName)
    {
        this.resourceName = resourceName;
    }
    public Integer getDefaultConfig()
    {
        return defaultConfig;
    }
    public void setDefaultConfig(Integer defaultConfig)
    {
        this.defaultConfig = defaultConfig;
    }
    public String getSpecialDepts()
    {
        return specialDepts;
    }
    public void setSpecialDepts(String specialDepts)
    {
        this.specialDepts = specialDepts;
    }
   
    
}
