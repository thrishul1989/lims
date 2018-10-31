package com.todaysoft.lims.system.modules.bpm.tecanalys.model.request;

import java.util.List;

public class SubmitCnvVerifyRequest
{
    private String technicalFamilyGroupId;//BIOLOGY_DIVISION_FASTQ_DATA的id,与页面的sampleAnalysisId值一样
    private String cnvAnalysisResultId;    //cnv保存位点结果id
    private String isFamilyTestAnalysis;   //是否家系分析:验证，不验证
    private List<String> mLPAverifySampleRelation;    //MLPA分析样本关系
    private List<String> qPCRVerifySampleRelation;     //QPCR分析样本关系
    private String locusType;              //位点类型：当是否家系分析选择是（验证）时，该值为验证位点；当选择否时（不验证），该值可以是：报告位点，纯阴性报告，参考位点
    public String getCnvAnalysisResultId()
    {
        return cnvAnalysisResultId;
    }
    public void setCnvAnalysisResultId(String cnvAnalysisResultId)
    {
        this.cnvAnalysisResultId = cnvAnalysisResultId;
    }
    public List<String> getmLPAverifySampleRelation()
    {
        return mLPAverifySampleRelation;
    }
    public void setmLPAverifySampleRelation(List<String> mLPAverifySampleRelation)
    {
        this.mLPAverifySampleRelation = mLPAverifySampleRelation;
    }
    public List<String> getqPCRVerifySampleRelation()
    {
        return qPCRVerifySampleRelation;
    }
    public void setqPCRVerifySampleRelation(List<String> qPCRVerifySampleRelation)
    {
        this.qPCRVerifySampleRelation = qPCRVerifySampleRelation;
    }
    public String getIsFamilyTestAnalysis()
    {
        return isFamilyTestAnalysis;
    }
    public void setIsFamilyTestAnalysis(String isFamilyTestAnalysis)
    {
        this.isFamilyTestAnalysis = isFamilyTestAnalysis;
    }
    public String getTechnicalFamilyGroupId()
    {
        return technicalFamilyGroupId;
    }
    public void setTechnicalFamilyGroupId(String technicalFamilyGroupId)
    {
        this.technicalFamilyGroupId = technicalFamilyGroupId;
    }
    public String getLocusType()
    {
        return locusType;
    }
    public void setLocusType(String locusType)
    {
        this.locusType = locusType;
    }
   
    
}
