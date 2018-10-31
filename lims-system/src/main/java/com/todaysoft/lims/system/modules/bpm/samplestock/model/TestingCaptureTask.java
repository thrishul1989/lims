package com.todaysoft.lims.system.modules.bpm.samplestock.model;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.todaysoft.lims.persist.UuidKeyEntity;

public class TestingCaptureTask
{
    public static final String RESULT_SUCCESS = "0";
    
    private String batchCode;
    
    private String testingCode;
    
    private String probeId;
    
    private BigDecimal probeDatasize;
    
    private String result;
    
    private String remark;
    
    private List<TestingCaptureSample> samples;
    
    public String getBatchCode()
    {
        return batchCode;
    }
    
    public void setBatchCode(String batchCode)
    {
        this.batchCode = batchCode;
    }
    
    public String getTestingCode()
    {
        return testingCode;
    }
    
    public void setTestingCode(String testingCode)
    {
        this.testingCode = testingCode;
    }
    
    public String getProbeId()
    {
        return probeId;
    }
    
    public void setProbeId(String probeId)
    {
        this.probeId = probeId;
    }
    
    public BigDecimal getProbeDatasize()
    {
        return probeDatasize;
    }
    
    public void setProbeDatasize(BigDecimal probeDatasize)
    {
        this.probeDatasize = probeDatasize;
    }
    
    public String getResult()
    {
        return result;
    }
    
    public void setResult(String result)
    {
        this.result = result;
    }
    
    public String getRemark()
    {
        return remark;
    }
    
    public void setRemark(String remark)
    {
        this.remark = remark;
    }
    
    public List<TestingCaptureSample> getSamples()
    {
        return samples;
    }
    
    public void setSamples(List<TestingCaptureSample> samples)
    {
        this.samples = samples;
    }
}
