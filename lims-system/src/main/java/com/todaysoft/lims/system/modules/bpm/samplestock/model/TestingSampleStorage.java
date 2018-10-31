package com.todaysoft.lims.system.modules.bpm.samplestock.model;

import com.todaysoft.lims.persist.UuidKeyEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
public class TestingSampleStorage
{
    
    private String sampleCode;
    
    private String locationCode;
    
    private BigDecimal sampleSize;
    
    private Integer status;//存储状态 1-入库 2-出库
    
    public TestingSampleStorage()
    {
        
    }
    
    public String getSampleCode()
    {
        return sampleCode;
    }
    
    public void setSampleCode(String sampleCode)
    {
        this.sampleCode = sampleCode;
    }
    
    public String getLocationCode()
    {
        return locationCode;
    }
    
    public void setLocationCode(String locationCode)
    {
        this.locationCode = locationCode;
    }
    
    public BigDecimal getSampleSize()
    {
        return sampleSize;
    }
    
    public void setSampleSize(BigDecimal sampleSize)
    {
        this.sampleSize = sampleSize;
    }
    
    public Integer getStatus()
    {
        return status;
    }
    
    public void setStatus(Integer status)
    {
        this.status = status;
    }
}
