package com.todaysoft.lims.product.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_PROBE")
public class Probe extends UuidKeyEntity
{
    private static final long serialVersionUID = 7553549455820980430L;
    
    private String name;
    
    private Double dataSize;
    
    
    
    @Transient
    public Double getDataSize()
    {
        return dataSize;
    }
    
    public void setDataSize(Double dataSize)
    {
        this.dataSize = dataSize;
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
}
