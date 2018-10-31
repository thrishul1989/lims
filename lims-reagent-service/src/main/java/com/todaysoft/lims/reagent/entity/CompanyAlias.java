package com.todaysoft.lims.reagent.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "APP_COMPANY_ALIAS")
public class CompanyAlias extends UuidKeyEntity
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private String companyId;
    
    private String otherName;
    
    @Column(name = "COMPANY_ID")
    public String getCompanyId()
    {
        return companyId;
    }
    
    public void setCompanyId(String companyId)
    {
        this.companyId = companyId;
    }
    
    @Column(name = "ALIAS")
    public String getOtherName()
    {
        return otherName;
    }
    
    public void setOtherName(String otherName)
    {
        this.otherName = otherName;
    }
    
}
