package com.todaysoft.lims.system.modules.bpm.multipcrqc.model;

import java.math.BigDecimal;

import com.todaysoft.lims.utils.excel.annotation.ExcelField;

public class MultipcrQcSubmitTaskModelExcel
{
 private String testingCode;
    
    @ExcelField(title = "实验编号", align = 2, sort = 10)
    public String getTestingCode()
    {
        return testingCode;
    }
    
    public void setTestingCode(String testingCode)
    {
        this.testingCode = testingCode;
    }
    
    private String sampleCode;
    
    private String concn;
    
    private String volume;
    
    private String ratio2628;
    
    private String receivedSampleName;
    
    private String receivedSampleCode;
    @ExcelField(title = "样本名称", align = 2, sort = 20)
    public String getReceivedSampleName()
    {
        return receivedSampleName;
    }

    public void setReceivedSampleName(String receivedSampleName)
    {
        this.receivedSampleName = receivedSampleName;
    }
    @ExcelField(title = "样本名称", align = 2, sort = 30)
    public String getReceivedSampleCode()
    {
        return receivedSampleCode;
    }

    public void setReceivedSampleCode(String receivedSampleCode)
    {
        this.receivedSampleCode = receivedSampleCode;
    }

    private String ratio2623;
    
    private String qualityLevel;
    
    private boolean qualified;
    
    private String unqualifiedRemark;
    
    private String unqualifiedStrategy;
    
    @ExcelField(title = "产物编号", align = 2, sort = 40)
    public String getSampleCode()
    {
        return sampleCode;
    }
    
    public void setSampleCode(String sampleCode)
    {
        this.sampleCode = sampleCode;
    }
    
    @ExcelField(title = "产物浓度", align = 2, sort = 50)
    public String getConcn()
    {
        return concn;
    }
    
    public void setConcn(String concn)
    {
        this.concn = concn;
    }
    
    @ExcelField(title = "体积(ul)", align = 2, sort = 60)
    public String getVolume()
    {
        return volume;
    }
    
    public void setVolume(String volume)
    {
        this.volume = volume;
    }
    
    @ExcelField(title = "A260/280", align = 2, sort = 70)
    public String getRatio2628()
    {
        return ratio2628;
    }
    
    public void setRatio2628(String ratio2628)
    {
        this.ratio2628 = ratio2628;
    }
    
    @ExcelField(title = "A260/230", align = 2, sort = 80)
    public String getRatio2623()
    {
        return ratio2623;
    }
    
    public void setRatio2623(String ratio2623)
    {
        this.ratio2623 = ratio2623;
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
