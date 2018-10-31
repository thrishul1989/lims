package com.todaysoft.lims.system.modules.bpm.report.model;

import java.util.List;

import com.todaysoft.lims.system.modules.bpm.tecanalys.model.TestingDataPic;

public class VerifySampleModel
{
    private String id;
    
    private String mutationSource;
    
    private String verifySample;
    
    private String combineCode;
    
    private String familyRelation;
    
    private String sampleName;
    
    private String sampleCode;
    
    private String gene;
    
    private String exon;
    
    private String chrLocation;
    
    private String combineType;
    
    private String remark;
    
    private String scheduleId;
    
    private boolean qualified;
    
    private String unqualifiedRemark;
    
    private String unqualifiedStrategy;
    
    private boolean endFlag;
    
    private List<TestingDataPic> pictures;
    
    private String upstreamGene;
    
    private String downstreamGene;
    
    private String refSample;
    
    private String testResult;//阴/阳性
    
    private String conclusion;//结论
    
    private String nucleotideChanges;//核苷酸变化
    
    private int sort;//对应家系关系 排序用 1-本人 2-父亲 3-母亲
    
    private int isResampling;//是否重新送样  0不是、1是
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getMutationSource()
    {
        return mutationSource;
    }
    
    public void setMutationSource(String mutationSource)
    {
        this.mutationSource = mutationSource;
    }
    
    public String getVerifySample()
    {
        return verifySample;
    }
    
    public void setVerifySample(String verifySample)
    {
        this.verifySample = verifySample;
    }
    
    public String getCombineCode()
    {
        return combineCode;
    }
    
    public void setCombineCode(String combineCode)
    {
        this.combineCode = combineCode;
    }
    
    public String getFamilyRelation()
    {
        return familyRelation;
    }
    
    public void setFamilyRelation(String familyRelation)
    {
        this.familyRelation = familyRelation;
    }
    
    public String getSampleName()
    {
        return sampleName;
    }
    
    public void setSampleName(String sampleName)
    {
        this.sampleName = sampleName;
    }
    
    public String getSampleCode()
    {
        return sampleCode;
    }
    
    public void setSampleCode(String sampleCode)
    {
        this.sampleCode = sampleCode;
    }
    
    public String getGene()
    {
        return gene;
    }
    
    public void setGene(String gene)
    {
        this.gene = gene;
    }
    
    public String getExon()
    {
        return exon;
    }
    
    public void setExon(String exon)
    {
        this.exon = exon;
    }
    
    public String getChrLocation()
    {
        return chrLocation;
    }
    
    public void setChrLocation(String chrLocation)
    {
        this.chrLocation = chrLocation;
    }
    
    public String getCombineType()
    {
        return combineType;
    }
    
    public void setCombineType(String combineType)
    {
        this.combineType = combineType;
    }
    
    public String getRemark()
    {
        return remark;
    }
    
    public void setRemark(String remark)
    {
        this.remark = remark;
    }
    
    public String getScheduleId()
    {
        return scheduleId;
    }
    
    public void setScheduleId(String scheduleId)
    {
        this.scheduleId = scheduleId;
    }
    
    public boolean isQualified()
    {
        return qualified;
    }
    
    public void setQualified(boolean qualified)
    {
        this.qualified = qualified;
    }
    
    public String getUnqualifiedRemark()
    {
        return unqualifiedRemark;
    }
    
    public void setUnqualifiedRemark(String unqualifiedRemark)
    {
        this.unqualifiedRemark = unqualifiedRemark;
    }
    
    public String getUnqualifiedStrategy()
    {
        return unqualifiedStrategy;
    }
    
    public void setUnqualifiedStrategy(String unqualifiedStrategy)
    {
        this.unqualifiedStrategy = unqualifiedStrategy;
    }
    
    public boolean isEndFlag()
    {
        return endFlag;
    }
    
    public void setEndFlag(boolean endFlag)
    {
        this.endFlag = endFlag;
    }
    
    public List<TestingDataPic> getPictures()
    {
        return pictures;
    }
    
    public void setPictures(List<TestingDataPic> pictures)
    {
        this.pictures = pictures;
    }
    
    public String getUpstreamGene()
    {
        return upstreamGene;
    }
    
    public void setUpstreamGene(String upstreamGene)
    {
        this.upstreamGene = upstreamGene;
    }
    
    public String getDownstreamGene()
    {
        return downstreamGene;
    }
    
    public void setDownstreamGene(String downstreamGene)
    {
        this.downstreamGene = downstreamGene;
    }
    
    public String getRefSample()
    {
        return refSample;
    }
    
    public void setRefSample(String refSample)
    {
        this.refSample = refSample;
    }
    
    public String getTestResult()
    {
        return testResult;
    }
    
    public void setTestResult(String testResult)
    {
        this.testResult = testResult;
    }
    
    public String getConclusion()
    {
        return conclusion;
    }
    
    public void setConclusion(String conclusion)
    {
        this.conclusion = conclusion;
    }
    
    public String getNucleotideChanges()
    {
        return nucleotideChanges;
    }
    
    public void setNucleotideChanges(String nucleotideChanges)
    {
        this.nucleotideChanges = nucleotideChanges;
    }
    
    public int getSort()
    {
        return sort;
    }
    
    public void setSort(int sort)
    {
        this.sort = sort;
    }

    public int getIsResampling()
    {
        return isResampling;
    }

    public void setIsResampling(int isResampling)
    {
        this.isResampling = isResampling;
    }
}
