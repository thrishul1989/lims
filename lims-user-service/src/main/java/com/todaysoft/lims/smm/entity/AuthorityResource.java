package com.todaysoft.lims.smm.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "LIMS_AUTHORITY_RESOURCE")
public class AuthorityResource implements Serializable
{
    
    private String authorityId;
    private String resourceId;
    
    @Id
    @Column(name = "AUTHORITY_ID")
    public String getAuthorityId()
    {
        return authorityId;
    }
    public void setAuthorityId(String authorityId)
    {
        this.authorityId = authorityId;
    }
    
    @Column(name = "RESOURCE_ID")
    public String getResourceId()
    {
        return resourceId;
    }
    public void setResourceId(String resourceId)
    {
        this.resourceId = resourceId;
    }
}
