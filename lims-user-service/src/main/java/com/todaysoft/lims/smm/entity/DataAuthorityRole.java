package com.todaysoft.lims.smm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_DATA_AUTHORITY_ROLE")
public class DataAuthorityRole extends UuidKeyEntity
{
    
    private DataAuthority resourceKey;
    
    private String roleId;
    
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

   
    @Column(name = "ROLE_ID")
    
    public String getRoleId()
    {
        return roleId;
    }

    public void setRoleId(String roleId)
    {
        this.roleId = roleId;
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
