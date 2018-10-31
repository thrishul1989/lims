package com.todaysoft.lims.schedule.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_PRODUCT")
public class Product extends UuidKeyEntity
{
    private static final long serialVersionUID = -4418555675523609311L;
    
    private String code;
    
    private String name;
    
    private String testingSampleTypeId;

    private Integer delFlag;
    
    @Column(name = "CODE")
    public String getCode()
    {
        return code;
    }
    
    public void setCode(String code)
    {
        this.code = code;
    }
    
    @Column(name = "NAME")
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    @Column(name = "TESTING_START_SAMPLE")
    public String getTestingSampleTypeId()
    {
        return testingSampleTypeId;
    }
    
    public void setTestingSampleTypeId(String testingSampleTypeId)
    {
        this.testingSampleTypeId = testingSampleTypeId;
    }

    @Column(name = "DEL_FLAG")
    public Integer getDelFlag()
    {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag)
    {
        this.delFlag = delFlag;
    }
}
