package com.todaysoft.lims.testing.report.model;

import java.util.List;

import com.todaysoft.lims.testing.base.entity.TestingDataPic;
import com.todaysoft.lims.utils.StringUtils;

public class VerifySampleModel
{
    private String id;
    
    private String mutationSource;//突变来源
    
    private String verifySample;//验证样本
    
    private String combineCode;//合并编号
    
    private String familyRelation;//家系关系
    
    private String sampleName;//样本名称
    
    private String sampleCode;//样本编号
    
    private String gene;//基因
    
    private String exon;//外显子区域
    
    private String chrLocation;//染色体位置
    
    private String combineType;//杂合/纯合
    
    private String remark;//备注
    
    private String scheduleId;//
    
    private boolean qualified;//
    
    private String unqualifiedRemark;//备注原因
    
    private String unqualifiedStrategy;//1 上报
    
    private boolean endFlag;//
    
    private List<TestingDataPic> pictures;//图片
    
    private String upstreamGene;//上游内参基因
    
    private String downstreamGene;//下游内参基因
    
    private String refSample;//对照样本
    
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
        if (StringUtils.isEmpty(gene))
        {
            return "";
        }
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
        if (StringUtils.isEmpty(chrLocation))
        {
            return "";
        }
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
