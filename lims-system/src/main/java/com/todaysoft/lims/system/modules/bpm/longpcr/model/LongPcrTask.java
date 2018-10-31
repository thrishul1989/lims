package com.todaysoft.lims.system.modules.bpm.longpcr.model;

import java.math.BigDecimal;

import javax.persistence.Column;

import com.todaysoft.lims.system.model.vo.Primer;
import com.todaysoft.lims.system.model.vo.Product;
import com.todaysoft.lims.system.modules.bpm.samplestock.model.TestingTask;

public class LongPcrTask
{
    private String id;
    
    private Product product;
    
    private TestingTask testingTask;
    
    private Primer primer;
    
    private BigDecimal inputVolume;
    
    private BigDecimal dnaVolume;
    
    private String testingCode;
    
    private String pcrCode;
    
    private String connectNum;
    
    private BigDecimal dnaConcn;//冗余字段DNA浓度
    
    private String qualityLevel;//冗余字段质量等级
    
    public String getConnectNum()
    {
        return connectNum;
    }
    
    public void setConnectNum(String connectNum)
    {
        this.connectNum = connectNum;
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
    
    public String getPcrCode()
    {
        return pcrCode;
    }
    
    public void setPcrCode(String pcrCode)
    {
        this.pcrCode = pcrCode;
    }
    
}
