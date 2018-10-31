package com.todaysoft.lims.system.modules.bpm.mlpadata.model;

import java.util.Date;

import com.todaysoft.lims.system.modules.bpm.samplestock.model.TestingTask;

public class MlpaDataTask
{
    
    private TestingTask testingTask;
    
    private Integer type;//类型 1-检测 2-验证
    
    private String productName;
    
    private String probe;
    
    private String gene;//突变基因
    
    private String chromosomeLocation;//染色体位置
    
    private String sheetCode;
    
    private String sampleCode;
    
    private String sampleName;
    
    private String inputSampleFamilyRelation;//样本关系
    
    private String orderCode;
    
    private String sequencingCode;
    
    private String familyRelation;//家系关系
    
    private Integer ifUrgent;
    
    private Date urgentUpdateTime;
    
    private String urgentName;
    
    private String dataTemplateId;

    private String dataCode;//数据编号（检测）或者合并编号（验证）
    
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
    
    public String getSheetCode()
    {
        return sheetCode;
    }
    
    public void setSheetCode(String sheetCode)
    {
        this.sheetCode = sheetCode;
    }
    
    public TestingTask getTestingTask()
    {
        return testingTask;
    }
    
    public void setTestingTask(TestingTask testingTask)
    {
        this.testingTask = testingTask;
    }
    
    public Integer getType()
    {
        return type;
    }
    
    public void setType(Integer type)
    {
        this.type = type;
    }
    
    public String getProductName()
    {
        return productName;
    }
    
    public void setProductName(String productName)
    {
        this.productName = productName;
    }
    
    public String getProbe()
    {
        return probe;
    }
    
    public void setProbe(String probe)
    {
        this.probe = probe;
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

    public String getDataCode() {
        return dataCode;
    }

    public void setDataCode(String dataCode) {
        this.dataCode = dataCode;
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
