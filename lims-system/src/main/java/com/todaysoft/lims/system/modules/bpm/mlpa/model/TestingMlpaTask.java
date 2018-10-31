package com.todaysoft.lims.system.modules.bpm.mlpa.model;

import com.todaysoft.lims.persist.UuidKeyEntity;
import com.todaysoft.lims.system.modules.bpm.samplestock.model.TestingSample;

import javax.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;

public class TestingMlpaTask extends UuidKeyEntity implements Serializable
{
    
    private static final long serialVersionUID = -2404502440463732549L;
    
    private String sheetId;
    
    private String testingTaskId;
    
    private String testCode;
    
    private TestingSample testingSample;
    
    private BigDecimal addSampleVolume;//加样体积
    
    private BigDecimal addWaterVolume;//补水体积
    
    private Integer type;//1-检测 2-对照
    
    private Integer attrType;//1-待测 2-对照
    
    private String probe;
    
    private String remark;
    
    private Integer orderFlag;//排序
    
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
    
    public String getTestCode()
    {
        return testCode;
    }
    
    public void setTestCode(String testCode)
    {
        this.testCode = testCode;
    }
    
    public TestingSample getTestingSample()
    {
        return testingSample;
    }
    
    public void setTestingSample(TestingSample testingSample)
    {
        this.testingSample = testingSample;
    }
    
    public BigDecimal getAddSampleVolume()
    {
        return addSampleVolume;
    }
    
    public void setAddSampleVolume(BigDecimal addSampleVolume)
    {
        this.addSampleVolume = addSampleVolume;
    }
    
    public BigDecimal getAddWaterVolume()
    {
        return addWaterVolume;
    }
    
    public void setAddWaterVolume(BigDecimal addWaterVolume)
    {
        this.addWaterVolume = addWaterVolume;
    }
    
    public Integer getType()
    {
        return type;
    }
    
    public void setType(Integer type)
    {
        this.type = type;
    }
    
    public Integer getAttrType()
    {
        return attrType;
    }
    
    public void setAttrType(Integer attrType)
    {
        this.attrType = attrType;
    }
    
    public String getProbe()
    {
        return probe;
    }
    
    public void setProbe(String probe)
    {
        this.probe = probe;
    }
    
    public String getRemark()
    {
        return remark;
    }
    
    public void setRemark(String remark)
    {
        this.remark = remark;
    }
    
    public Integer getOrderFlag()
    {
        return orderFlag;
    }
    
    public void setOrderFlag(Integer orderFlag)
    {
        this.orderFlag = orderFlag;
    }
}
