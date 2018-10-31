package com.todaysoft.lims.testing.technicalanaly.model;

import java.util.List;

public class TechnicalAnalySheetReceivedSample
{
    private String id;
    
    private String sampleCode;
    
    private String derivedDNASampleId;
    
    private List<TechnicalAnalyVerifySample> verifySamples;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getSampleCode()
    {
        return sampleCode;
    }
    
    public void setSampleCode(String sampleCode)
    {
        this.sampleCode = sampleCode;
    }
    
    public String getDerivedDNASampleId()
    {
        return derivedDNASampleId;
    }
    
    public void setDerivedDNASampleId(String derivedDNASampleId)
    {
        this.derivedDNASampleId = derivedDNASampleId;
    }
    
    public List<TechnicalAnalyVerifySample> getVerifySamples()
    {
        return verifySamples;
    }
    
    public void setVerifySamples(List<TechnicalAnalyVerifySample> verifySamples)
    {
        this.verifySamples = verifySamples;
    }
}
