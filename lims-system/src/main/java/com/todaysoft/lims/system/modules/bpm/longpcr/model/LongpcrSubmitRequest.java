package com.todaysoft.lims.system.modules.bpm.longpcr.model;

import java.math.BigDecimal;

import com.todaysoft.lims.system.modules.bpm.samplestock.model.TestingTask;
import com.todaysoft.lims.utils.excel.annotation.ExcelField;

public class LongpcrSubmitRequest
{
    
    private String sheetId;
    
    private BigDecimal concn;
    
    private BigDecimal A260280;
    
    private BigDecimal A260230;
    
    private String pcrCode;
    
    private TestingTask testingTask;
    
    public TestingTask getTestingTask()
    {
        return testingTask;
    }

    public void setTestingTask(TestingTask testingTask)
    {
        this.testingTask = testingTask;
    }

    private BigDecimal volumn;
    
    public String getSheetId()
    {
        return sheetId;
    }
    
    public void setSheetId(String sheetId)
    {
        this.sheetId = sheetId;
    }
    
    @ExcelField(title = "体积", align = 2, sort = 30)
    public BigDecimal getVolumn()
    {
        return volumn;
    }
    
    public void setVolumn(BigDecimal volumn)
    {
        this.volumn = volumn;
    }
    
    @ExcelField(title = "扩增编号", align = 2, sort = 10)
    public String getPcrCode()
    {
        return pcrCode;
    }
    
    public void setPcrCode(String pcrCode)
    {
        this.pcrCode = pcrCode;
    }
    
    @ExcelField(title = "产物浓度", align = 2, sort = 20)
    public BigDecimal getConcn()
    {
        return concn;
    }
    
    public void setConcn(BigDecimal concn)
    {
        this.concn = concn;
    }
    
    @ExcelField(title = "A260/280", align = 2, sort = 40)
    public BigDecimal getA260280()
    {
        return A260280;
    }
    
    public void setA260280(BigDecimal a260280)
    {
        A260280 = a260280;
    }
    
    @ExcelField(title = "A260/230", align = 2, sort = 50)
    public BigDecimal getA260230()
    {
        return A260230;
    }
    
    public void setA260230(BigDecimal a260230)
    {
        A260230 = a260230;
    }
}
