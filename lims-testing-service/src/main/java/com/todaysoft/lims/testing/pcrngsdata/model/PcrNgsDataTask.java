package com.todaysoft.lims.testing.pcrngsdata.model;

import java.util.Date;

public class PcrNgsDataTask
{
    private String id;
    
    private String sampleName;
    
    private String sampleCode;
    
    private String pcrTaskCode;//pcr任务编号
    
    private String pcrTestCode;//pcr试验编号
    
    private String combineCode;//合并号
    
    private String gene;
    
    private boolean resubmit;
    
    private Integer resubmitCount;
    
    private String chromosomeLocation;
    
    private String testorName;//实验员
    
    private Date testDate;//实验时间
    
    private String bioTestCode;
    
    private String orderId;
    
    private String inputSampleFamilyRelation;//样本关系
    
    private String orderCode;
    
    private String productName;
    
    private String sequencingCode;
    
    private String familyRelation;//家系关系
    
    private Integer ifUrgent;
    
    private Date urgentUpdateTime;
    
    private String urgentName;
    
    private String dataTemplateId;
    
    private Date startTime;
    
    private Date plannedFinishDate;
    
    public String getDataTemplateId()
    {
        return dataTemplateId;
    }
    
    public void setDataTemplateId(String dataTemplateId)
    {
        this.dataTemplateId = dataTemplateId;
    }
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
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
    
    public String getPcrTaskCode()
    {
        return pcrTaskCode;
    }
    
    public void setPcrTaskCode(String pcrTaskCode)
    {
        this.pcrTaskCode = pcrTaskCode;
    }
    
    public String getPcrTestCode()
    {
        return pcrTestCode;
    }
    
    public void setPcrTestCode(String pcrTestCode)
    {
        this.pcrTestCode = pcrTestCode;
    }
    
    public String getCombineCode()
    {
        return combineCode;
    }
    
    public void setCombineCode(String combineCode)
    {
        this.combineCode = combineCode;
    }
    
    public String getGene()
    {
        return gene;
    }
    
    public void setGene(String gene)
    {
        this.gene = gene;
    }
    
    public String getChromosomeLocation()
    {
        return chromosomeLocation;
    }
    
    public void setChromosomeLocation(String chromosomeLocation)
    {
        this.chromosomeLocation = chromosomeLocation;
    }
    
    public String getTestorName()
    {
        return testorName;
    }
    
    public void setTestorName(String testorName)
    {
        this.testorName = testorName;
    }
    
    public Date getTestDate()
    {
        return testDate;
    }
    
    public void setTestDate(Date testDate)
    {
        this.testDate = testDate;
    }
    
    public String getBioTestCode()
    {
        return bioTestCode;
    }
    
    public void setBioTestCode(String bioTestCode)
    {
        this.bioTestCode = bioTestCode;
    }
    
    public String getOrderId()
    {
        return orderId;
    }
    
    public void setOrderId(String orderId)
    {
        this.orderId = orderId;
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

    public Date getPlannedFinishDate()
    {
        return plannedFinishDate;
    }

    public void setPlannedFinishDate(Date plannedFinishDate)
    {
        this.plannedFinishDate = plannedFinishDate;
    }
    
}
