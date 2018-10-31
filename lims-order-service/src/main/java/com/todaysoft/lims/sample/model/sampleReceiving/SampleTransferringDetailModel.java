package com.todaysoft.lims.sample.model.sampleReceiving;

import java.math.BigDecimal;

public class SampleTransferringDetailModel
{
    private String sampleCode;//    '样本编号',
    
    private BigDecimal sampleSize;// '接收样本量',
    
    private BigDecimal lsSize; //'长期存储量',
    
    private BigDecimal tsSize;//'临时存储量',
    
    private String sizeUnit;//'收样量、存储量单位名称',
    
    //视图部分--前台传入
    private String sampleId; //样本id
    
    private String sampleName;//样本名称
    
    private String typeId;//样本类型
    
    private String typeName;
    
    private Integer sampleBtype;//传给前台显示并传入后台  ---业务类型：1-非科研主样本 2-非科研附属样本 3-科研样本
    
    private Integer purpose;//传给前台显示并传入后台  ---业务类型：1-2-3
    
    private Integer sort;
    
    public Integer getSort()
    {
        return sort;
    }
    
    public void setSort(Integer sort)
    {
        this.sort = sort;
    }
    
    public Integer getPurpose()
    {
        return purpose;
    }
    
    public void setPurpose(Integer purpose)
    {
        this.purpose = purpose;
    }
    
    public Integer getSampleBtype()
    {
        return sampleBtype;
    }
    
    public void setSampleBtype(Integer sampleBtype)
    {
        this.sampleBtype = sampleBtype;
    }
    
    public String getSampleId()
    {
        return sampleId;
    }
    
    public void setSampleId(String sampleId)
    {
        this.sampleId = sampleId;
    }
    
    public String getSampleName()
    {
        return sampleName;
    }
    
    public void setSampleName(String sampleName)
    {
        this.sampleName = sampleName;
    }
    
    public String getTypeId()
    {
        return typeId;
    }
    
    public void setTypeId(String typeId)
    {
        this.typeId = typeId;
    }
    
    public String getTypeName()
    {
        return typeName;
    }
    
    public void setTypeName(String typeName)
    {
        this.typeName = typeName;
    }
    
    public String getSampleCode()
    {
        return sampleCode;
    }
    
    public void setSampleCode(String sampleCode)
    {
        this.sampleCode = sampleCode;
    }
    
    public BigDecimal getSampleSize()
    {
        return sampleSize;
    }
    
    public void setSampleSize(BigDecimal sampleSize)
    {
        this.sampleSize = sampleSize;
    }
    
    public BigDecimal getLsSize()
    {
        return lsSize;
    }
    
    public void setLsSize(BigDecimal lsSize)
    {
        this.lsSize = lsSize;
    }
    
    public BigDecimal getTsSize()
    {
        return tsSize;
    }
    
    public void setTsSize(BigDecimal tsSize)
    {
        this.tsSize = tsSize;
    }
    
    public String getSizeUnit()
    {
        return sizeUnit;
    }
    
    public void setSizeUnit(String sizeUnit)
    {
        this.sizeUnit = sizeUnit;
    }
    
}
