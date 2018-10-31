package com.todaysoft.lims.testing.report.model;

import java.util.Date;

public class CnvAnalysisResult
{
    private String id;
    
    private String sampleTestId;
    
    private String sampleTestCode; //用于页面显示
    
    private String gene;
    
    private String missingArea;
    
    private String exon;
    
    private String inheritSource;
    
    private String relatedDisease;
    
    private Date createTime;
    
    private Date updateTime;
    
    private String cnvAnalysisPicId;
    
    private String clinicalJudgment;
    private String chromosome;
    private String beginLocus;
    private String  endLocus;
    private String inhert;

    private String pathogenicLevel;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getSampleTestId()
    {
        return sampleTestId;
    }
    
    public void setSampleTestId(String sampleTestId)
    {
        this.sampleTestId = sampleTestId;
    }
    
    public String getGene()
    {
        return gene;
    }
    
    public void setGene(String gene)
    {
        this.gene = gene;
    }
    
    public String getMissingArea()
    {
        return missingArea;
    }
    
    public void setMissingArea(String missingArea)
    {
        this.missingArea = missingArea;
    }
    
    public String getExon()
    {
        return exon;
    }
    
    public void setExon(String exon)
    {
        this.exon = exon;
    }
    
    public String getInheritSource()
    {
        return inheritSource;
    }
    
    public void setInheritSource(String inheritSource)
    {
        this.inheritSource = inheritSource;
    }
    
    public String getRelatedDisease()
    {
        return relatedDisease;
    }
    
    public void setRelatedDisease(String relatedDisease)
    {
        this.relatedDisease = relatedDisease;
    }
    
    public Date getCreateTime()
    {
        return createTime;
    }
    
    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }
    
    public Date getUpdateTime()
    {
        return updateTime;
    }
    
    public void setUpdateTime(Date updateTime)
    {
        this.updateTime = updateTime;
    }
    
    public String getSampleTestCode()
    {
        return sampleTestCode;
    }
    
    public void setSampleTestCode(String sampleTestCode)
    {
        this.sampleTestCode = sampleTestCode;
    }

	public String getCnvAnalysisPicId() {
		return cnvAnalysisPicId;
	}

	public void setCnvAnalysisPicId(String cnvAnalysisPicId) {
		this.cnvAnalysisPicId = cnvAnalysisPicId;
	}

    public String getClinicalJudgment()
    {
        return clinicalJudgment;
    }

    public void setClinicalJudgment(String clinicalJudgment)
    {
        this.clinicalJudgment = clinicalJudgment;
    }

    public String getChromosome()
    {
        return chromosome;
    }

    public void setChromosome(String chromosome)
    {
        this.chromosome = chromosome;
    }

    public String getBeginLocus()
    {
        return beginLocus;
    }

    public void setBeginLocus(String beginLocus)
    {
        this.beginLocus = beginLocus;
    }

    public String getEndLocus()
    {
        return endLocus;
    }

    public void setEndLocus(String endLocus)
    {
        this.endLocus = endLocus;
    }

    public String getInhert()
    {
        return inhert;
    }

    public void setInhert(String inhert)
    {
        this.inhert = inhert;
    }


    public String getPathogenicLevel() {
        return pathogenicLevel;
    }

    public void setPathogenicLevel(String pathogenicLevel) {
        this.pathogenicLevel = pathogenicLevel;
    }
}