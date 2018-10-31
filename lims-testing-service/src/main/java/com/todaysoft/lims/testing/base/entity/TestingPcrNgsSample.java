package com.todaysoft.lims.testing.base.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_TESTING_PCRNGS_SAMPLE")
public class TestingPcrNgsSample extends UuidKeyEntity
{
    private static final long serialVersionUID = -7034924517590989966L;
    
    private TestingSchedule testingSchedule;
    
    private TestingSample outputSample;
    
    private TestingSample inputSample;
    
    private String combineCode;
    
    private Integer libraryIndex;
    
    private BigDecimal sampleVolume;
    
    @ManyToOne
    @JoinColumn(name = "OUTPUT_SAMPLE_ID")
    public TestingSample getOutputSample()
    {
        return outputSample;
    }
    
    public void setOutputSample(TestingSample outputSample)
    {
        this.outputSample = outputSample;
    }
    
    @ManyToOne
    @JoinColumn(name = "INPUT_SAMPLE_ID")
    public TestingSample getInputSample()
    {
        return inputSample;
    }
    
    public void setInputSample(TestingSample inputSample)
    {
        this.inputSample = inputSample;
    }
    
    @Column(name = "COMBINE_CODE")
    public String getCombineCode()
    {
        return combineCode;
    }
    
    public void setCombineCode(String combineCode)
    {
        this.combineCode = combineCode;
    }
    
    @Column(name = "LIBRARY_INDEX")
    public Integer getLibraryIndex()
    {
        return libraryIndex;
    }
    
    public void setLibraryIndex(Integer libraryIndex)
    {
        this.libraryIndex = libraryIndex;
    }
    
    @Column(name = "SAMPLE_VOLUME")
    public BigDecimal getSampleVolume()
    {
        return sampleVolume;
    }
    
    public void setSampleVolume(BigDecimal sampleVolume)
    {
        this.sampleVolume = sampleVolume;
    }
    
    @OneToOne
    @JoinColumn(name = "SHEDULE_ID")
    public TestingSchedule getTestingSchedule()
    {
        return testingSchedule;
    }
    
    public void setTestingSchedule(TestingSchedule testingSchedule)
    {
        this.testingSchedule = testingSchedule;
    }
    
}
