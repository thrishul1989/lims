package com.todaysoft.lims.system.modules.bpm.report.model;

import java.util.List;

public class VerifyDataModel
{
    private String verifyMethod;
    
    private List<VerifySampleModel> verifySamples;

    public String getVerifyMethod()
    {
        return verifyMethod;
    }

    public void setVerifyMethod(String verifyMethod)
    {
        this.verifyMethod = verifyMethod;
    }

    public List<VerifySampleModel> getVerifySamples()
    {
        return verifySamples;
    }

    public void setVerifySamples(List<VerifySampleModel> verifySamples)
    {
        this.verifySamples = verifySamples;
    }
    
    
    
}
