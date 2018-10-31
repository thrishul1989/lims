package com.todaysoft.lims.testing.base.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_TESTING_LONGPCR_TASK")
public class TestingLongpcrTask extends UuidKeyEntity
{
    
    private Product product;
    
    private TestingTask testingTask;
    
    private Primer primer;
    
    private BigDecimal inputVolume;
    
    private BigDecimal dnaVolume;
    
    private String testingCode;
    
    private String pcrCode;
    
    private String connectNum;
    
    private String location;
    
    
    @Column(name = "CONNECT_NUM")
    public String getConnectNum()
    {
        return connectNum;
    }

    public void setConnectNum(String connectNum)
    {
        this.connectNum = connectNum;
    }

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    public Product getProduct()
    {
        return product;
    }
    
    public void setProduct(Product product)
    {
        this.product = product;
    }
    
    @ManyToOne
    @JoinColumn(name = "TASK_ID")
    public TestingTask getTestingTask()
    {
        return testingTask;
    }
    
    public void setTestingTask(TestingTask testingTask)
    {
        this.testingTask = testingTask;
    }
    
    @ManyToOne
    @JoinColumn(name = "PRIMER_ID")
    public Primer getPrimer()
    {
        return primer;
    }
    
    public void setPrimer(Primer primer)
    {
        this.primer = primer;
    }
    
    @Column(name = "INPUT_VOLUME")
    public BigDecimal getInputVolume()
    {
        return inputVolume;
    }
    
    public void setInputVolume(BigDecimal inputVolume)
    {
        this.inputVolume = inputVolume;
    }
    
    @Column(name = "DNA_VOLUME")
    public BigDecimal getDnaVolume()
    {
        return dnaVolume;
    }
    
    public void setDnaVolume(BigDecimal dnaVolume)
    {
        this.dnaVolume = dnaVolume;
    }
    
    @Column(name = "TESTING_CODE")
    public String getTestingCode()
    {
        return testingCode;
    }
    
    public void setTestingCode(String testingCode)
    {
        this.testingCode = testingCode;
    }
    
    @Column(name = "PCR_CODE")
    public String getPcrCode()
    {
        return pcrCode;
    }
    
    public void setPcrCode(String pcrCode)
    {
        this.pcrCode = pcrCode;
    }

    @Transient
    public String getLocation()
    {
        return location;
    }

    public void setLocation(String location)
    {
        this.location = location;
    }
    
}
