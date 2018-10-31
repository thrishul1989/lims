package com.todaysoft.lims.maintain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_TESTING_SAMPLE")
public class TestingSample extends UuidKeyEntity
{
    private static final long serialVersionUID = 3662365048553820674L;
    
    private String sampleCode;
    
    private String sampleTypeName;
    
    private String sampleTypeId;
    
    private ReceivedSample receivedSample;
    
    private String attributes;
    
    @Column(name = "SAMPLE_TYPE_ID")
    public String getSampleTypeId()
    {
        return sampleTypeId;
    }
    
    public void setSampleTypeId(String sampleTypeId)
    {
        this.sampleTypeId = sampleTypeId;
    }
    
    @Column(name = "SAMPLE_CODE")
    public String getSampleCode()
    {
        return sampleCode;
    }
    
    public void setSampleCode(String sampleCode)
    {
        this.sampleCode = sampleCode;
    }
    
    @Column(name = "SAMPLE_TYPE_NAME")
    public String getSampleTypeName()
    {
        return sampleTypeName;
    }
    
    public void setSampleTypeName(String sampleTypeName)
    {
        this.sampleTypeName = sampleTypeName;
    }
    
    @ManyToOne
    @JoinColumn(name = "ORIGINAL_SAMPLE_ID")
    @NotFound(action = NotFoundAction.IGNORE)
    public ReceivedSample getReceivedSample()
    {
        return receivedSample;
    }
    
    public void setReceivedSample(ReceivedSample receivedSample)
    {
        this.receivedSample = receivedSample;
    }
    
    @Column(name = "ATTRIBUTES")
    public String getAttributes()
    {
        return attributes;
    }
    
    public void setAttributes(String attributes)
    {
        this.attributes = attributes;
    }
}
