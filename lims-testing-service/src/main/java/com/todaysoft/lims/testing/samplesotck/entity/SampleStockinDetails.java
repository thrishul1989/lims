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
@Table(name="LIMS_SAMPLE_STOCKIN_DETAILS")
public class SampleStockinDetails extends UuidKeyEntity
{
    
    private SampleStockin record;
    private TestingSample sampleId;
    private String sampleLocation;
    
    @ManyToOne
    @JoinColumn(name = "RECORD_ID")
    @NotFound(action=NotFoundAction.IGNORE)
    @JsonIgnore
    public SampleStockin getRecord()
    {
        return record;
    }
    public void setRecord(SampleStockin record)
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
    @Column(name="SAMPLE_LOCATION")
    public String getSampleLocation()
    {
        return sampleLocation;
    }
    public void setSampleLocation(String sampleLocation)
    {
        this.sampleLocation = sampleLocation;
    }
}
