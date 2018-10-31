package com.todaysoft.lims.testing.libcap.model;

import java.math.BigDecimal;
import java.util.List;

public class LibraryCaptureSheetGroupModel
{
    private String id;
    
    private String testingCode;
    
    private String probeId;
    
    private String probeName;
    
    private BigDecimal probeDatasize;
    
    private List<LibraryCaptureSheetSampleModel> samples;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
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
    
    public String getProbeName()
    {
        return probeName;
    }
    
    public void setProbeName(String probeName)
    {
        this.probeName = probeName;
    }
    
    public BigDecimal getProbeDatasize()
    {
        return probeDatasize;
    }
    
    public void setProbeDatasize(BigDecimal probeDatasize)
    {
        this.probeDatasize = probeDatasize;
    }
    
    public List<LibraryCaptureSheetSampleModel> getSamples()
    {
        return samples;
    }
    
    public void setSamples(List<LibraryCaptureSheetSampleModel> samples)
    {
        this.samples = samples;
    }
}
