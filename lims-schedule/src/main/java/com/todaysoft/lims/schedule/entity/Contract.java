package com.todaysoft.lims.schedule.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_CONTRACT")
public class Contract extends UuidKeyEntity
{
    private static final long serialVersionUID = 2487372086522828803L;
    
    public static final String START_AUTO = "1";
    
    private String startType;
    
    @Column(name = "START_MODE")
    public String getStartType()
    {
        return startType;
    }
    
    public void setStartType(String startType)
    {
        this.startType = startType;
    }
}
