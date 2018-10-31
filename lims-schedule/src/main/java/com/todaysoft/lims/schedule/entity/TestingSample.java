package com.todaysoft.lims.schedule.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_TESTING_SAMPLE")
public class TestingSample extends UuidKeyEntity
{
    private static final long serialVersionUID = 8893875832929722805L;
    
    private String sampleCode;
    
    private ReceivedSample receivedSample;
    
    private String parentSampleId;

    private String sampleTypeId;
    
    @Column(name = "SAMPLE_CODE")
    public String getSampleCode()
    {
        return sampleCode;
    }
    
    public void setSampleCode(String sampleCode)
    {
        this.sampleCode = sampleCode;
    }
    
    @ManyToOne
    @JoinColumn(name = "ORIGINAL_SAMPLE_ID")
    @NotFound(action = NotFoundAction.IGNORE)
    @JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler", "fieldHandler"})
    public ReceivedSample getReceivedSample()
    {
        return receivedSample;
    }
    
    public void setReceivedSample(ReceivedSample receivedSample)
    {
        this.receivedSample = receivedSample;
    }

    @Column(name = "PARENT_SAMPLE_ID")
    public String getParentSampleId()
    {
        return parentSampleId;
    }

    public void setParentSampleId(String parentSampleId)
    {
        this.parentSampleId = parentSampleId;
    }

    @Column(name = "SAMPLE_TYPE_ID")
    public String getSampleTypeId() {
        return sampleTypeId;
    }

    public void setSampleTypeId(String sampleTypeId) {
        this.sampleTypeId = sampleTypeId;
    }
}
