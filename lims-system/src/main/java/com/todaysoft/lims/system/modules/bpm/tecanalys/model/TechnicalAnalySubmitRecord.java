package com.todaysoft.lims.system.modules.bpm.tecanalys.model;

import java.util.List;

public class TechnicalAnalySubmitRecord
{
    private boolean verify;
    
    private String verifyMethod;
    
    private List<TechnicalAnalyVerifySample> verifySamples;
    
    private TechnicalAnalyRecord record;
    
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
