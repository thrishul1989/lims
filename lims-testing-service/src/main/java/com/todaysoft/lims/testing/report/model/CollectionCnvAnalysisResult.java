package com.todaysoft.lims.testing.report.model;

public class CollectionCnvAnalysisResult extends CnvAnalysisResult
{
    private String isFamilyAnalysis; //是否家系分析：是，否
    private String familyMethod;//家系实验方法
    private String verifySample;//验证样本
    public String getIsFamilyAnalysis()
    {
        return isFamilyAnalysis;
    }
    public void setIsFamilyAnalysis(String isFamilyAnalysis)
    {
        this.isFamilyAnalysis = isFamilyAnalysis;
    }
    public String getFamilyMethod()
    {
        return familyMethod;
    }
    public void setFamilyMethod(String familyMethod)
    {
        this.familyMethod = familyMethod;
    }
    public String getVerifySample()
    {
        return verifySample;
    }
    public void setVerifySample(String verifySample)
    {
        this.verifySample = verifySample;
    }
    
    
}
