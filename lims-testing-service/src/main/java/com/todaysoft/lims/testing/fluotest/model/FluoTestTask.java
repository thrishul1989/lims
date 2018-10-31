package com.todaysoft.lims.testing.fluotest.model;

import java.math.BigDecimal;

import com.todaysoft.lims.testing.base.entity.Product;
import com.todaysoft.lims.testing.base.entity.TestingTask;



public class FluoTestTask
{
    private TestingTask testingTask;
    
    private Product product;
    
    private BigDecimal inputVolume;
    
    private BigDecimal dnaVolume;
    
    private String testingCode;
    
    private BigDecimal waterVolume;
    
    private BigDecimal dnaConcn;//冗余字段DNA浓度
    
    private String qualityLevel;//冗余字段质量等级
    
    private String location;
    
    public TestingTask getTestingTask()
    {
        return testingTask;
    }
    
    public void setTestingTask(TestingTask testingTask)
    {
        this.testingTask = testingTask;
    }
    
    public Product getProduct()
    {
        return product;
    }
    
    public void setProduct(Product product)
    {
        this.product = product;
    }
    
    public BigDecimal getInputVolume()
    {
        return inputVolume;
    }
    
    public void setInputVolume(BigDecimal inputVolume)
    {
        this.inputVolume = inputVolume;
    }
    
    public BigDecimal getDnaVolume()
    {
        return dnaVolume;
    }
    
    public void setDnaVolume(BigDecimal dnaVolume)
    {
        this.dnaVolume = dnaVolume;
    }
    
    public String getTestingCode()
    {
        return testingCode;
    }
    
    public void setTestingCode(String testingCode)
    {
        this.testingCode = testingCode;
    }
    
    
    public BigDecimal getWaterVolume()
    {
        return waterVolume;
    }

    public void setWaterVolume(BigDecimal waterVolume)
    {
        this.waterVolume = waterVolume;
    }

    public BigDecimal getDnaConcn()
    {
        return dnaConcn;
    }
    
    public void setDnaConcn(BigDecimal dnaConcn)
    {
        this.dnaConcn = dnaConcn;
    }
    
    public String getQualityLevel()
    {
        return qualityLevel;
    }
    
    public void setQualityLevel(String qualityLevel)
    {
        this.qualityLevel = qualityLevel;
    }

    public String getLocation()
    {
        return location;
    }

    public void setLocation(String location)
    {
        this.location = location;
    }
    
}
