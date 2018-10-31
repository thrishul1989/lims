package com.todaysoft.lims.system.modules.bpm.multipcr.model;

import java.math.BigDecimal;

import javax.persistence.Column;

import com.todaysoft.lims.system.model.vo.Primer;
import com.todaysoft.lims.system.model.vo.Product;
import com.todaysoft.lims.system.modules.bpm.samplestock.model.TestingTask;

public class MultiPcrTask
{
    private String id;
    
    private Product product;
    
    private TestingTask testingTask;
    
    private Primer primer;
    
    private BigDecimal inputVolume;
    
    private BigDecimal dnaVolume;
    
    private String testingCode;
    
    private String outputSampleCode;//额外字段
    
    private BigDecimal dnaConcn;//冗余字段DNA浓度
    
    private String qualityLevel;//冗余字段质量等级
    
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

    public String getOutputSampleCode()
    {
        return outputSampleCode;
    }
    
    public void setOutputSampleCode(String outputSampleCode)
    {
        this.outputSampleCode = outputSampleCode;
    }
    
    private String connectNum;
    
    @Column(name = "CONNECT_NUM")
    public String getConnectNum()
    {
        return connectNum;
    }
    
    public void setConnectNum(String connectNum)
    {
        this.connectNum = connectNum;
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
    
    public Primer getPrimer()
    {
        return primer;
    }
    
    public void setPrimer(Primer primer)
    {
        this.primer = primer;
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
    
}
