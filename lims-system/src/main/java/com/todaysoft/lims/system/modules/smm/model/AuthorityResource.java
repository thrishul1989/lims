package com.todaysoft.lims.system.modules.smm.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


public class AuthorityResource
{
    
    
    private String authorityId;
    private String resourceId;
    
   
    public String getAuthorityId()
    {
        return authorityId;
    }
    public void setAuthorityId(String authorityId)
    {
        this.authorityId = authorityId;
    }
    
    
    public String getResourceId()
    {
        return resourceId;
    }
    public void setResourceId(String resourceId)
    {
        this.resourceId = resourceId;
    }
}
