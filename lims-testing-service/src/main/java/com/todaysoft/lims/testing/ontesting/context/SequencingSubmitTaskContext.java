package com.todaysoft.lims.testing.ontesting.context;

import java.math.BigDecimal;

import com.todaysoft.lims.testing.base.entity.TestingTask;

public class SequencingSubmitTaskContext
{
    private Integer cluster;
    
    private BigDecimal effectiveRate;
    
    private BigDecimal effectiveSize;
    
    private BigDecimal q30;
    
    private TestingTask testingTaskEntity;
    
    public Integer getCluster()
    {
        return cluster;
    }
    
    public void setCluster(Integer cluster)
    {
        this.cluster = cluster;
    }
    
    public BigDecimal getEffectiveRate()
    {
        return effectiveRate;
    }
    
    public void setEffectiveRate(BigDecimal effectiveRate)
    {
        this.effectiveRate = effectiveRate;
    }
    
    public BigDecimal getEffectiveSize()
    {
        return effectiveSize;
    }
    
    public void setEffectiveSize(BigDecimal effectiveSize)
    {
        this.effectiveSize = effectiveSize;
    }
    
    public BigDecimal getQ30()
    {
        return q30;
    }
    
    public void setQ30(BigDecimal q30)
    {
        this.q30 = q30;
    }
    
    public TestingTask getTestingTaskEntity()
    {
        return testingTaskEntity;
    }
    
    public void setTestingTaskEntity(TestingTask testingTaskEntity)
    {
        this.testingTaskEntity = testingTaskEntity;
    }
}
