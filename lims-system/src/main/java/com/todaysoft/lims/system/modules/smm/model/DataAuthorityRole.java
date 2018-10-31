package com.todaysoft.lims.system.modules.smm.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.todaysoft.lims.persist.UuidKeyEntity;

public class DataAuthorityRole 
{
    public RoleDetailsModel getRole()
    {
        return role;
    }

    public void setRole(RoleDetailsModel role)
    {
        this.role = role;
    }

    private String id;
    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public DataAuthority getResourceKey()
    {
        return resourceKey;
    }

    public void setResourceKey(DataAuthority resourceKey)
    {
        this.resourceKey = resourceKey;
    }

    public String getRoleId()
    {
        return roleId;
    }

    public void setRoleId(String roleId)
    {
        this.roleId = roleId;
    }

    public Integer getConfig()
    {
        return config;
    }

    public void setConfig(Integer config)
    {
        this.config = config;
    }

    public String getSpecialDepts()
    {
        return specialDepts;
    }

    public void setSpecialDepts(String specialDepts)
    {
        this.specialDepts = specialDepts;
    }

    private DataAuthority resourceKey;
    
    private String roleId;
    
    private Integer config;
    
    private String specialDepts;
    
    private Integer pageNo;
    private Integer pageSize;
    
    private RoleDetailsModel role;//展示字段
    
    private String deptNames;//展示字段
    
    public String getDeptNames()
    {
        return deptNames;
    }

    public void setDeptNames(String deptNames)
    {
        this.deptNames = deptNames;
    }

    public Integer getPageNo()
    {
        return pageNo;
    }

    public void setPageNo(Integer pageNo)
    {
        this.pageNo = pageNo;
    }

    public Integer getPageSize()
    {
        return pageSize;
    }

    public void setPageSize(Integer pageSize)
    {
        this.pageSize = pageSize;
    }

   
}
