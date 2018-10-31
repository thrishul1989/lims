package com.todaysoft.lims.testing.technicalanaly.model;

import java.util.List;

public class TechnicalAnalySubmitRecord
{
    private String temporaryId;
    
    private boolean verify;
    
    private String verifyMethod;
    
    private List<TechnicalAnalyVerifySample> verifySamples;
    
    private TechnicalAnalyRecord record;
    
    public String getTemporaryId()
    {
        return temporaryId;
    }
    
    public void setTemporaryId(String temporaryId)
    {
        this.temporaryId = temporaryId;
    }
    
    public boolean isVerify()
    {
        return verify;
    }
    
    public void setVerify(boolean verify)
    {
        this.verify = verify;
    }
    
    public String getVerifyMethod()
    {
        return verifyMethod;
    }
    
    public void setVerifyMethod(String verifyMethod)
    {
        this.verifyMethod = verifyMethod;
    }
    
    public List<TechnicalAnalyVerifySample> getVerifySamples()
    {
        return verifySamples;
    }
    
    public void setVerifySamples(List<TechnicalAnalyVerifySample> verifySamples)
    {
        this.verifySamples = verifySamples;
    }
    
    public TechnicalAnalyRecord getRecord()
    {
        return record;
    }
    
    public void setRecord(TechnicalAnalyRecord record)
    {
        this.record = record;
    }
}
