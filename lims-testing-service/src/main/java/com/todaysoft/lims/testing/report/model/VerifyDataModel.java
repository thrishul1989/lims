package com.todaysoft.lims.testing.report.model;

import java.util.List;

public class VerifyDataModel
{
    private String verifyMethod;//验证名称
    
    private List<VerifySampleModel> verifySamples;//数据

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
