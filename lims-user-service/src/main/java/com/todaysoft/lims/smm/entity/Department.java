package com.todaysoft.lims.smm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_DEPARTMENT")
public class Department extends UuidKeyEntity
{
    private static final long serialVersionUID = 1699497607061558435L;
    
    private String parentId;
    
    private boolean deleted;
    
    @Column(name = "PARENT_ID")
    public String getParentId()
    {
        return parentId;
    }
    
    public void setParentId(String parentId)
    {
        this.parentId = parentId;
    }
    
    @Column(name = "DEL_FLAG")
    public boolean isDeleted()
    {
        return deleted;
    }
    
    public void setDeleted(boolean deleted)
    {
        this.deleted = deleted;
    }
}
