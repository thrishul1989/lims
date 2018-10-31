package com.todaysoft.lims.system.modules.bpm.mlpa.model;

import java.math.BigDecimal;

public class MlpaTestAssignTask
{
    private String id;
    
    private String testCode;
    
    private String sampleName;
    
    private String originalSampleCode;
    
    private BigDecimal dnaConcn;
    
    private BigDecimal index260280;
    
    private BigDecimal index260230;
    
    private BigDecimal inputSize; //投入量
    
    private BigDecimal inputVolume; //加样体积
    
    private BigDecimal supplementVolume; //补水体积
    
    private String dnaLocation;
    
    private String mlpaProbe;
    
    private String type;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getTestCode()
    {
        return testCode;
    }
    
    public void setTestCode(String testCode)
    {
        this.testCode = testCode;
    }
    
    public String getSampleName()
    {
        return sampleName;
    }
    
    public void setSampleName(String sampleName)
    {
        this.sampleName = sampleName;
    }
    
    public String getOriginalSampleCode()
    {
        return originalSampleCode;
    }
    
    public void setOriginalSampleCode(String originalSampleCode)
    {
        this.originalSampleCode = originalSampleCode;
    }
    
    public BigDecimal getDnaConcn()
    {
        return dnaConcn;
    }
    
    public void setDnaConcn(BigDecimal dnaConcn)
    {
        this.dnaConcn = dnaConcn;
    }
    
    public BigDecimal getIndex260280()
    {
        return index260280;
    }
    
    public void setIndex260280(BigDecimal index260280)
    {
        this.index260280 = index260280;
    }
    
    public BigDecimal getIndex260230()
    {
        return index260230;
    }
    
    public void setIndex260230(BigDecimal index260230)
    {
        this.index260230 = index260230;
    }
    
    public BigDecimal getInputSize()
    {
        return inputSize;
    }
    
    public void setInputSize(BigDecimal inputSize)
    {
        this.inputSize = inputSize;
    }
    
    public BigDecimal getInputVolume()
    {
        return inputVolume;
    }
    
    public void setInputVolume(BigDecimal inputVolume)
    {
        this.inputVolume = inputVolume;
    }
    
    public BigDecimal getSupplementVolume()
    {
        return supplementVolume;
    }
    
    public void setSupplementVolume(BigDecimal supplementVolume)
    {
        this.supplementVolume = supplementVolume;
    }
    
    public String getDnaLocation()
    {
        return dnaLocation;
    }
    
    public void setDnaLocation(String dnaLocation)
    {
        this.dnaLocation = dnaLocation;
    }
    
    public String getMlpaProbe()
    {
        return mlpaProbe;
    }
    
    public void setMlpaProbe(String mlpaProbe)
    {
        this.mlpaProbe = mlpaProbe;
    }
    
    public String getType()
    {
        return type;
    }
    
    public void setType(String type)
    {
        this.type = type;
    }
}
