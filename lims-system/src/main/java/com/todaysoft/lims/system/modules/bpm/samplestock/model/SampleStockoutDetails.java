package com.todaysoft.lims.system.modules.bpm.samplestock.model;

import java.sql.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.todaysoft.lims.persist.UuidKeyEntity;



@Builder
@AllArgsConstructor
public class SampleStockoutDetails
{
    private SampleStockout record;
    
    private TestingSample sampleId;
    
    private String sampleLocation;
    
    private String id;
    
    private Integer pageNo;
    
    private Integer pageSize;
    
    public SampleStockoutDetails()
    {
        
    }
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public Integer getPageNo()
    {
        return pageNo;
    }
    
    public void setPageNo(Integer pageNo)
    {
        this.pageNo = pageNo;
    }
    
    public Integer getPageSize()
    {
        return pageSize;
    }
    
    public void setPageSize(Integer pageSize)
    {
        this.pageSize = pageSize;
    }
    
    public SampleStockout getRecord()
    {
        return record;
    }
    
    public void setRecord(SampleStockout record)
    {
        this.record = record;
    }
    
    
    
    public String getSampleLocation()
    {
        return sampleLocation;
    }
    
    public void setSampleLocation(String sampleLocation)
    {
        this.sampleLocation = sampleLocation;
    }

    public TestingSample getSampleId()
    {
        return sampleId;
    }

    public void setSampleId(TestingSample sampleId)
    {
        this.sampleId = sampleId;
    }
}
