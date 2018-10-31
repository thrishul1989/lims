package com.todaysoft.lims.system.modules.bpm.competeTasks.model.fluo;

import java.math.BigDecimal;

import com.todaysoft.lims.system.model.vo.Product;
import com.todaysoft.lims.system.modules.bpm.samplestock.model.TestingTask;

public class FluoAnalyseSheetTask
{
    private TestingTask testingTask;
    
    private Product product;
    
    private BigDecimal dnaConcn;//冗余字段DNA浓度
    
    private String qualityLevel;//冗余字段质量等级
    
    private boolean qualified;
    
    private String unqualifiedRemark;
    
    private String unqualifiedStrategy;
    
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
