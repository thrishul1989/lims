package com.todaysoft.lims.testing.base.model.task;

import java.math.BigDecimal;

public class DNAAssignSheetTask
{
    private String id;
    
    private String sheetId;//任务单ID
    
    private String testingTaskId;//检测任务ID
    
    private Integer resultType;//1正常2失败可重做3失败不可重做
    
    private String remark;//备注
    
    private BigDecimal inputQuantity;//投入量
    
    private String testCode;//实验编号
    
    private String sampleCode;//样本编号
    
    private String sampleLocation;//存储样本位置
    
    private String sampleType;//样本类型
    
    private String products;//产品
    
    private String inspectName;//受检人
    
    private String dnaCode;//DNA编号
    
    private BigDecimal outputQuantity;//提取量
    
    private String concentration;//浓度
    
    private String volume;//体积
    
    private String quality;//质量等级
    
    private String A260280;//
    
    private String A260230;
    
    private String qcResult;//质检结果
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getSheetId()
    {
        return sheetId;
    }
    
    public void setSheetId(String sheetId)
    {
        this.sheetId = sheetId;
    }
    
    public String getTestingTaskId()
    {
        return testingTaskId;
    }
    
    public void setTestingTaskId(String testingTaskId)
    {
        this.testingTaskId = testingTaskId;
    }
    
    public Integer getResultType()
    {
        return resultType;
    }
    
    public void setResultType(Integer resultType)
    {
        this.resultType = resultType;
    }
    
    public String getRemark()
    {
        return remark;
    }
    
    public void setRemark(String remark)
    {
        this.remark = remark;
    }
    
    public BigDecimal getInputQuantity()
    {
        return inputQuantity;
    }
    
    public void setInputQuantity(BigDecimal inputQuantity)
    {
        this.inputQuantity = inputQuantity;
    }
    
    public String getTestCode()
    {
        return testCode;
    }
    
    public void setTestCode(String testCode)
    {
        this.testCode = testCode;
    }
    
    public String getSampleCode()
    {
        return sampleCode;
    }
    
    public void setSampleCode(String sampleCode)
    {
        this.sampleCode = sampleCode;
    }
    
    public String getSampleLocation()
    {
        return sampleLocation;
    }
    
    public void setSampleLocation(String sampleLocation)
    {
        this.sampleLocation = sampleLocation;
    }
    
    public String getSampleType()
    {
        return sampleType;
    }
    
    public void setSampleType(String sampleType)
    {
        this.sampleType = sampleType;
    }
    
    public String getProducts()
    {
        return products;
    }
    
    public void setProducts(String products)
    {
        this.products = products;
    }
    
    public String getInspectName()
    {
        return inspectName;
    }
    
    public void setInspectName(String inspectName)
    {
        this.inspectName = inspectName;
    }
    
    public String getDnaCode()
    {
        return dnaCode;
    }
    
    public void setDnaCode(String dnaCode)
    {
        this.dnaCode = dnaCode;
    }
    
    public BigDecimal getOutputQuantity()
    {
        return outputQuantity;
    }
    
    public void setOutputQuantity(BigDecimal outputQuantity)
    {
        this.outputQuantity = outputQuantity;
    }
    
    public String getConcentration()
    {
        return concentration;
    }
    
    public void setConcentration(String concentration)
    {
        this.concentration = concentration;
    }
    
    public String getVolume()
    {
        return volume;
    }
    
    public void setVolume(String volume)
    {
        this.volume = volume;
    }
    
    public String getQuality()
    {
        return quality;
    }
    
    public void setQuality(String quality)
    {
        this.quality = quality;
    }
    
    public String getA260280()
    {
        return A260280;
    }
    
    public void setA260280(String a260280)
    {
        A260280 = a260280;
    }
    
    public String getA260230()
    {
        return A260230;
    }
    
    public void setA260230(String a260230)
    {
        A260230 = a260230;
    }
    
    public String getQcResult()
    {
        return qcResult;
    }
    
    public void setQcResult(String qcResult)
    {
        this.qcResult = qcResult;
    }
}
