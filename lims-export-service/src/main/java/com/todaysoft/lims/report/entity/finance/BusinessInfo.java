package com.todaysoft.lims.report.entity.finance;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "BUSINESS_INFO")
public class BusinessInfo extends UuidKeyEntity

{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private String realName;
    
    @Column(name = "REAL_NAME")
    public String getRealName()
    {
        return realName;
    }
    
    public void setRealName(String realName)
    {
        this.realName = realName;
    }
    
}
