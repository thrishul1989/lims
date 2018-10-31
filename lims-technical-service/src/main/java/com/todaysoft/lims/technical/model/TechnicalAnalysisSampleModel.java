package com.todaysoft.lims.technical.model;

public class TechnicalAnalysisSampleModel
{
    private String testingSampleId;
    
    private String sampleCode;
    
    private String familyRelation;
    
    private Integer samplePurpose;
    
    private Integer ifSelf;// 是否本人
    
    public Integer getIfSelf()
    {
        return ifSelf;
    }

    public void setIfSelf(Integer ifSelf)
    {
        this.ifSelf = ifSelf;
    }

    public String getTestingSampleId()
    {
        return testingSampleId;
    }
    
    public void setTestingSampleId(String testingSampleId)
    {
        this.testingSampleId = testingSampleId;
    }
    
    public String getSampleCode()
    {
        return sampleCode;
    }
    
    public void setSampleCode(String sampleCode)
    {
        this.sampleCode = sampleCode;
    }
    
    public String getFamilyRelation()
    {
        return familyRelation;
    }
    
    public void setFamilyRelation(String familyRelation)
    {
        this.familyRelation = familyRelation;
    }
    
    public Integer getSamplePurpose()
    {
        return samplePurpose;
    }
    
    public void setSamplePurpose(Integer samplePurpose)
    {
        this.samplePurpose = samplePurpose;
    }
}