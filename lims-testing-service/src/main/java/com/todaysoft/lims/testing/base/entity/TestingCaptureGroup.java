package com.todaysoft.lims.testing.base.entity;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_TESTING_CAPTURE_TASK")
public class TestingCaptureGroup extends UuidKeyEntity
{
    public static final String RESULT_SUCCESS = "0";
    
    private static final long serialVersionUID = -4912566469808769986L;
    
    private String sheetId;
    
    private String batchCode;
    
    private String testingCode;
    
    private String probeId;
    
    private BigDecimal probeDatasize;
    
    private String result;
    
    private String remark;
    
    private List<TestingCaptureSample> samples;
    
    @Column(name = "SHEET_ID")
    public String getSheetId()
    {
        return sheetId;
    }
    
    public void setSheetId(String sheetId)
    {
        this.sheetId = sheetId;
    }
    
    @Column(name = "BATCH_CODE")
    public String getBatchCode()
    {
        return batchCode;
    }
    
    public void setBatchCode(String batchCode)
    {
        this.batchCode = batchCode;
    }
    
    @Column(name = "TESTING_CODE")
    public String getTestingCode()
    {
        return testingCode;
    }
    
    public void setTestingCode(String testingCode)
    {
        this.testingCode = testingCode;
    }
    
    @Column(name = "PROBE_ID")
    public String getProbeId()
    {
        return probeId;
    }
    
    public void setProbeId(String probeId)
    {
        this.probeId = probeId;
    }
    
    @Column(name = "PROBE_DATASIZE")
    public BigDecimal getProbeDatasize()
    {
        return probeDatasize;
    }
    
    public void setProbeDatasize(BigDecimal probeDatasize)
    {
        this.probeDatasize = probeDatasize;
    }
    
    @Column(name = "RESULT")
    public String getResult()
    {
        return result;
    }
    
    public void setResult(String result)
    {
        this.result = result;
    }
    
    @Column(name = "REMARK")
    public String getRemark()
    {
        return remark;
    }
    
    public void setRemark(String remark)
    {
        this.remark = remark;
    }
    
    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<TestingCaptureSample> getSamples()
    {
        return samples;
    }
    
    public void setSamples(List<TestingCaptureSample> samples)
    {
        this.samples = samples;
    }
}
