package com.todaysoft.lims.smm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_DATA_AUTHORITY_USER")
public class DataAuthorityUser extends UuidKeyEntity
{
    
    
private DataAuthority resourceKey;
    
    private String userId;
    
    private Integer config;
    
    private String specialDepts;

    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "RESOURCE_KEY")
   
    public DataAuthority getResourceKey()
    {
        return resourceKey;
    }

    public void setResourceKey(DataAuthority resourceKey)
    {
        this.resourceKey = resourceKey;
    }



    @Column(name = "USER_ID")
    public String getUserId()
    {
        return userId;
    }

    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    @Column(name = "CONFIG")
    public Integer getConfig()
    {
        return config;
    }

    public void setConfig(Integer config)
    {
        this.config = config;
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
