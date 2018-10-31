package com.todaysoft.lims.maintain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "BIOLOGY_DIVISION_TASK")
public class BiologyDivisionTask extends UuidKeyEntity
{
    
    private String sequecingCode;
    
    @Column(name = "SEQUENCING_CODE")
    public String getSequecingCode()
    {
        return sequecingCode;
    }
    
    public void setSequecingCode(String sequecingCode)
    {
        this.sequecingCode = sequecingCode;
    }
    
}
