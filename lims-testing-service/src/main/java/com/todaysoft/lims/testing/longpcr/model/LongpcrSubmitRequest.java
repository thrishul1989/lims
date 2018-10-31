package com.todaysoft.lims.testing.longpcr.model;

import java.math.BigDecimal;

import com.todaysoft.lims.testing.base.entity.TestingTask;

public class LongpcrSubmitRequest
{
    
    private String sheetId;
    
    public String getSheetId()
    {
        return sheetId;
    }
    
    public void setSheetId(String sheetId)
    {
        this.sheetId = sheetId;
    }
    
    private String pcrCode;
    
    private BigDecimal concn;
    
    private BigDecimal volumn;
    
    public TestingTask getTestingTask()
    {
        return testingTask;
    }

    public void setTestingTask(TestingTask testingTask)
    {
        this.testingTask = testingTask;
    }

    private TestingTask testingTask;
    
    public BigDecimal getVolumn()
    {
        return volumn;
    }
    
    public void setVolumn(BigDecimal volumn)
    {
        this.volumn = volumn;
    }
    
    private BigDecimal A260280;
    
    private BigDecimal A260230;
    
    public String getPcrCode()
    {
        return pcrCode;
    }
    
    public void setPcrCode(String pcrCode)
    {
        this.pcrCode = pcrCode;
    }
    
    public BigDecimal getConcn()
    {
        return concn;
    }
    
    public void setConcn(BigDecimal concn)
    {
        this.concn = concn;
    }
    
    public BigDecimal getA260280()
    {
        return A260280;
    }
    
    public void setA260280(BigDecimal a260280)
    {
        A260280 = a260280;
    }
    
    public BigDecimal getA260230()
    {
        return A260230;
    }
    
    public void setA260230(BigDecimal a260230)
    {
        A260230 = a260230;
    }
}
