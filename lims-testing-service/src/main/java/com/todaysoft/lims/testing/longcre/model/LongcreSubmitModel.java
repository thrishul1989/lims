package com.todaysoft.lims.testing.longcre.model;

import java.math.BigDecimal;

import com.todaysoft.lims.testing.base.entity.Product;
import com.todaysoft.lims.testing.base.entity.TestingTask;



public class LongcreSubmitModel
{
    private String id;
    
    private Product product;
    
    private TestingTask testingTask;
    
    private String outputSampleCode;
    
    private BigDecimal outputSampleConcn;
    
    private BigDecimal inputVolumn;
    
    private BigDecimal waterVolumn;
    
    private String location;
    
    public String getLocation()
    {
        return location;
    }

    public void setLocation(String location)
    {
        this.location = location;
    }

    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public Product getProduct()
    {
        return product;
    }
    
    public void setProduct(Product product)
    {
        this.product = product;
    }
    
    public TestingTask getTestingTask()
    {
        return testingTask;
    }
    
    public void setTestingTask(TestingTask testingTask)
    {
        this.testingTask = testingTask;
    }
    
    public String getOutputSampleCode()
    {
        return outputSampleCode;
    }
    
    public void setOutputSampleCode(String outputSampleCode)
    {
        this.outputSampleCode = outputSampleCode;
    }
    
    public BigDecimal getOutputSampleConcn()
    {
        return outputSampleConcn;
    }
    
    public void setOutputSampleConcn(BigDecimal outputSampleConcn)
    {
        this.outputSampleConcn = outputSampleConcn;
    }
    
  
    
    public BigDecimal getInputVolumn()
    {
        return inputVolumn;
    }

    public void setInputVolumn(BigDecimal inputVolumn)
    {
        this.inputVolumn = inputVolumn;
    }

    public BigDecimal getWaterVolumn()
    {
        return waterVolumn;
    }
    
    public void setWaterVolumn(BigDecimal waterVolumn)
    {
        this.waterVolumn = waterVolumn;
    }
    
    public String getTestingCode()
    {
        return testingCode;
    }
    
    public void setTestingCode(String testingCode)
    {
        this.testingCode = testingCode;
    }
    
    public String getConnectNum()
    {
        return connectNum;
    }
    
    public void setConnectNum(String connectNum)
    {
        this.connectNum = connectNum;
    }
    
    public String getQualityLevel()
    {
        return qualityLevel;
    }
    
    public void setQualityLevel(String qualityLevel)
    {
        this.qualityLevel = qualityLevel;
    }
    
    private String testingCode;
    
    private String connectNum;
    
    private String qualityLevel;//冗余字段质量等级
}
