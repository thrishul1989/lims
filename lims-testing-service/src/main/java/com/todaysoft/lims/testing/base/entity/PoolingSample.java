package com.todaysoft.lims.testing.base.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_TESTING_POOLING_SAMPLE")
public class PoolingSample extends UuidKeyEntity
{
    private static final long serialVersionUID = 8735418212335306084L;
    
    private TestingSample mixedSample;
    
    private TestingSample sample;
    
    private BigDecimal inputVolume;
    
    @ManyToOne
    @JoinColumn(name = "MIXED_SAMPLE_ID")
    public TestingSample getMixedSample()
    {
        return mixedSample;
    }
    
    public void setMixedSample(TestingSample mixedSample)
    {
        this.mixedSample = mixedSample;
    }
    
    @ManyToOne
    @JoinColumn(name = "SAMPLE_ID")
    public TestingSample getSample()
    {
        return sample;
    }
    
    public void setSample(TestingSample sample)
    {
        this.sample = sample;
    }
    
    @Column(name = "INPUT_VOLUME")
    public BigDecimal getInputVolume()
    {
        return inputVolume;
    }
    
    public void setInputVolume(BigDecimal inputVolume)
    {
        this.inputVolume = inputVolume;
    }
}
