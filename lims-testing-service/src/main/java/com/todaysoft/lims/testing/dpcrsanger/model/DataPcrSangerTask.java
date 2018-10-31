package com.todaysoft.lims.testing.dpcrsanger.model;

import java.util.Date;

public class DataPcrSangerTask
{
    private String id;
    
    private String sampleName;
    
    private String sampleCode;
    
    private String pcrTaskCode;//pcr任务编号
    
    private String pcrTestCode;//pcr试验编号
    
    private String combineCode;//合并号
    
    private String gene;
    
    private String chromosomeLocation;
    
    private String testorName;//实验员
    
    private Date testDate;//实验时间
    
    private String primerId;
    
    private Integer runningStatus;//0-正常 3-已取消
    
    private String clinicalRecommend;//临床推荐理由
    
    private Integer ifUrgent;
    
    private Date urgentUpdateTime;
    
    private String urgentName;
    
    private String dataTemplateId;
    
    private String productName;
    
    private Date plannedFinishDate;

    private Date startTime;
    public Date getStartTime()
    {
        return startTime;
    }

    public void setStartTime(Date startTime)
    {
        this.startTime = startTime;
    }
    
    public String getDataTemplateId()
    {
        return dataTemplateId;
    }
    
    public void setDataTemplateId(String dataTemplateId)
    {
        this.dataTemplateId = dataTemplateId;
    }
    
    public String getProductName()
    {
        return productName;
    }
    
    public void setProductName(String productName)
    {
        this.productName = productName;
    }
    
    public String getClinicalRecommend()
    {
        return clinicalRecommend;
    }
    
    public void setClinicalRecommend(String clinicalRecommend)
    {
        this.clinicalRecommend = clinicalRecommend;
    }
    
    public Integer getRunningStatus()
    {
        return runningStatus;
    }
    
    public void setRunningStatus(Integer runningStatus)
    {
        this.runningStatus = runningStatus;
    }
    
    public String getPrimerId()
    {
        return primerId;
    }
    
    public void setPrimerId(String primerId)
    {
        this.primerId = primerId;
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

    public Date getPlannedFinishDate()
    {
        return plannedFinishDate;
    }

    public void setPlannedFinishDate(Date plannedFinishDate)
    {
        this.plannedFinishDate = plannedFinishDate;
    }
    
}
