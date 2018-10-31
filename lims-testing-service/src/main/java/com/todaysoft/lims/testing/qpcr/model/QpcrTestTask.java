package com.todaysoft.lims.testing.qpcr.model;

import java.util.Date;

public class QpcrTestTask
{
    private String id;
    
    private String orderId;
    
    private String testingCode;//实验编号
    
    private String combineCode;
    
    private String sampleCode;
    
    private String sampleName;
    
    private String geneSymbol;//突变基因
    
    private String chrLocation;//染色体位置
    
    private String chromosome;
    
    private String beginLocus;//位置1
    
    private String endLocus;//位置2
    
    private String exon;
    
    private String upRefGene;
    
    private String downRefGene;
    
    private String refSample;
    
    private boolean resubmit;
    
    private Integer resubmitCount;
    
    private String inputSampleFamilyRelation;//样本关系
    
    private String orderCode;
    
    private String productName;
    
    private String sequencingCode;
    
    private String familyRelation;//家系关系
    
    private Integer storageStatus;
    
    private Integer ifUrgent;
    
    private Date urgentUpdateTime;
    
    private String urgentName;
    
    private String dataTemplateId;
    
    private Date startTime;
    
    private String location;
    
    private Date plannedFinishDate;
    
    public String getDataTemplateId()
    {
        return dataTemplateId;
    }
    
    public void setDataTemplateId(String dataTemplateId)
    {
        this.dataTemplateId = dataTemplateId;
    }
    
    public String getSampleName()
    {
        return sampleName;
    }
    
    public void setSampleName(String sampleName)
    {
        this.sampleName = sampleName;
    }
    
    public String getOrderId()
    {
        return orderId;
    }
    
    public void setOrderId(String orderId)
    {
        this.orderId = orderId;
    }
    
    public String getTestingCode()
    {
        return testingCode;
    }
    
    public void setTestingCode(String testingCode)
    {
        this.testingCode = testingCode;
    }
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getCombineCode()
    {
        return combineCode;
    }
    
    public void setCombineCode(String combineCode)
    {
        this.combineCode = combineCode;
    }
    
    public String getSampleCode()
    {
        return sampleCode;
    }
    
    public void setSampleCode(String sampleCode)
    {
        this.sampleCode = sampleCode;
    }
    
    public String getGeneSymbol()
    {
        return geneSymbol;
    }
    
    public void setGeneSymbol(String geneSymbol)
    {
        this.geneSymbol = geneSymbol;
    }
    
    public String getChrLocation()
    {
        return chrLocation;
    }
    
    public void setChrLocation(String chrLocation)
    {
        this.chrLocation = chrLocation;
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
    
    public String getExon()
    {
        return exon;
    }
    
    public void setExon(String exon)
    {
        this.exon = exon;
    }
    
    public String getUpRefGene()
    {
        return upRefGene;
    }
    
    public void setUpRefGene(String upRefGene)
    {
        this.upRefGene = upRefGene;
    }
    
    public String getDownRefGene()
    {
        return downRefGene;
    }
    
    public void setDownRefGene(String downRefGene)
    {
        this.downRefGene = downRefGene;
    }
    
    public String getRefSample()
    {
        return refSample;
    }
    
    public void setRefSample(String refSample)
    {
        this.refSample = refSample;
    }
    
    public boolean isResubmit()
    {
        return resubmit;
    }
    
    public void setResubmit(boolean resubmit)
    {
        this.resubmit = resubmit;
    }
    
    public Integer getResubmitCount()
    {
        return resubmitCount;
    }
    
    public void setResubmitCount(Integer resubmitCount)
    {
        this.resubmitCount = resubmitCount;
    }
    
    public String getInputSampleFamilyRelation()
    {
        return inputSampleFamilyRelation;
    }
    
    public void setInputSampleFamilyRelation(String inputSampleFamilyRelation)
    {
        this.inputSampleFamilyRelation = inputSampleFamilyRelation;
    }
    
    public String getOrderCode()
    {
        return orderCode;
    }
    
    public void setOrderCode(String orderCode)
    {
        this.orderCode = orderCode;
    }
    
    public String getProductName()
    {
        return productName;
    }
    
    public void setProductName(String productName)
    {
        this.productName = productName;
    }
    
    public String getSequencingCode()
    {
        return sequencingCode;
    }
    
    public void setSequencingCode(String sequencingCode)
    {
        this.sequencingCode = sequencingCode;
    }
    
    public String getFamilyRelation()
    {
        return familyRelation;
    }
    
    public void setFamilyRelation(String familyRelation)
    {
        this.familyRelation = familyRelation;
    }
    
    public Integer getStorageStatus()
    {
        return storageStatus;
    }
    
    public void setStorageStatus(Integer storageStatus)
    {
        this.storageStatus = storageStatus;
    }
    
    public Integer getIfUrgent()
    {
        return ifUrgent;
    }
    
    public void setIfUrgent(Integer ifUrgent)
    {
        this.ifUrgent = ifUrgent;
    }
    
    public Date getUrgentUpdateTime()
    {
        return urgentUpdateTime;
    }
    
    public void setUrgentUpdateTime(Date urgentUpdateTime)
    {
        this.urgentUpdateTime = urgentUpdateTime;
    }
    
    public String getUrgentName()
    {
        return urgentName;
    }
    
    public void setUrgentName(String urgentName)
    {
        this.urgentName = urgentName;
    }

    public Date getStartTime()
    {
        return startTime;
    }

    public void setStartTime(Date startTime)
    {
        this.startTime = startTime;
    }

    public String getLocation()
    {
        return location;
    }

    public void setLocation(String location)
    {
        this.location = location;
    }

    public Date getPlannedFinishDate()
    {
        return plannedFinishDate;
    }

    public void setPlannedFinishDate(Date plannedFinishDate)
    {
        this.plannedFinishDate = plannedFinishDate;
    }
    
}
