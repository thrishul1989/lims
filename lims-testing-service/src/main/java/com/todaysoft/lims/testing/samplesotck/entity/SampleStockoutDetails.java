package com.todaysoft.lims.testing.samplesotck.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.todaysoft.lims.persist.UuidKeyEntity;
import com.todaysoft.lims.testing.base.entity.TestingSample;

@Entity
@Table(name = "LIMS_SAMPLE_STOCKOUT_DETAILS")
public class SampleStockoutDetails extends UuidKeyEntity
{
    private SampleStockout record;
    
    private TestingSample sampleId;
    
    private String sampleLocation;
    
    @ManyToOne
    @JoinColumn(name = "RECORD_ID")
    @NotFound(action = NotFoundAction.IGNORE)
    @JsonIgnore
    public SampleStockout getRecord()
    {
        return record;
    }
    
    public void setRecord(SampleStockout record)
    {
        this.record = record;
    }
    
    @ManyToOne
    @JoinColumn(name = "SAMPLE_ID")
    public TestingSample getSampleId()
    {
        return sampleId;
    }
    
    public void setSampleId(TestingSample sampleId)
    {
        this.sampleId = sampleId;
    }
    
    @Column(name = "SAMPLE_LOCATION")
    public String getSampleLocation()
    {
        return sampleLocation;
    }
    
    public void setSampleLocation(String sampleLocation)
    {
        this.sampleLocation = sampleLocation;
    }
}
