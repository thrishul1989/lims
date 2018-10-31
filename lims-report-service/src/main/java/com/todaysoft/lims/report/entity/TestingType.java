package com.todaysoft.lims.report.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_TESTING_TYPE")
public class TestingType extends UuidKeyEntity
{
    private static final long serialVersionUID = -6619506565260175322L;
    
    private String name;
    
    @Column(name = "NAME")
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
}
