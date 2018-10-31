package com.todaysoft.lims.testing.completeTasks.model.mlpa;

import com.todaysoft.lims.testing.base.entity.TestingTask;

public class MlpaDataSheetTask
{
    
    private TestingTask testingTask;
    
    private Integer type;//类型 1-检测 2-验证
    
    private String productName;
    
    private String probe;
    
    private String gene;//突变基因
    
    private String chromosomeLocation;//染色体位置
    
    private String sheetCode;
    
    private boolean qualified;
    
    private String unqualifiedRemark;
    
    private String unqualifiedStrategy;
    
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
    
    public String getProductName()
    {
        return productName;
    }
    
    public void setProductName(String productName)
    {
        this.productName = productName;
    }
    
    public void setType(Integer type)
    {
        this.type = type;
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
    
    public boolean isQualified()
    {
        return qualified;
    }
    
    public void setQualified(boolean qualified)
    {
        this.qualified = qualified;
    }
    
    public String getUnqualifiedRemark()
    {
        return unqualifiedRemark;
    }
    
    public void setUnqualifiedRemark(String unqualifiedRemark)
    {
        this.unqualifiedRemark = unqualifiedRemark;
    }
    
    public String getUnqualifiedStrategy()
    {
        return unqualifiedStrategy;
    }
    
    public void setUnqualifiedStrategy(String unqualifiedStrategy)
    {
        this.unqualifiedStrategy = unqualifiedStrategy;
    }
}
