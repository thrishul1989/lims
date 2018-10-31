package com.todaysoft.lims.testing.firstpcr.model;

import java.math.BigDecimal;

public class FirstPcrSubmitTaskArgs
{
    private String id;
    
    private Integer result;
    
    private String dispose;
    
    private String remark;
    
    private String pcrTaskCode;//pcr任务编号
    
    private String pcrTestCode;//pcr试验编号
    
    private String sampleName;
    
    private String sampleCode;
    
    private String forwardPrimerCode;
    
    private String sampleLocationTemp;
    
    private String dnaLocation;
    
    private String verifyScheme;//验证方案
    
    private boolean resubmit;
    
    private Integer resubmitCount;
    
    private String forwardPrimerLocationTemp;
    
    private String reversePrimerLocationTemp;
    
    private String reversePrimerCode;
    
    private BigDecimal concentration;
    
    private BigDecimal volume;
    
    private String combineCode;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public Integer getResult()
    {
        return result;
    }
    
    public void setResult(Integer result)
    {
        this.result = result;
    }
    
    public String getDispose()
    {
        return dispose;
    }
    
    public void setDispose(String dispose)
    {
        this.dispose = dispose;
    }
    
    public String getRemark()
    {
        return remark;
    }
    
    public void setRemark(String remark)
    {
        this.remark = remark;
    }
    
    public String getPcrTaskCode()
    {
        return pcrTaskCode;
    }
    
    public void setPcrTaskCode(String pcrTaskCode)
    {
        this.pcrTaskCode = pcrTaskCode;
    }
    
    public String getPcrTestCode()
    {
        return pcrTestCode;
    }
    
    public void setPcrTestCode(String pcrTestCode)
    {
        this.pcrTestCode = pcrTestCode;
    }
    
    public String getForwardPrimerLocationTemp()
    {
        return forwardPrimerLocationTemp;
    }
    
    public void setForwardPrimerLocationTemp(String forwardPrimerLocationTemp)
    {
        this.forwardPrimerLocationTemp = forwardPrimerLocationTemp;
    }
    
    public String getSampleName()
    {
        return sampleName;
    }
    
    public void setSampleName(String sampleName)
    {
        this.sampleName = sampleName;
    }
    
    public String getSampleCode()
    {
        return sampleCode;
    }
    
    public void setSampleCode(String sampleCode)
    {
        this.sampleCode = sampleCode;
    }
    
    public String getForwardPrimerCode()
    {
        return forwardPrimerCode;
    }
    
    public void setForwardPrimerCode(String forwardPrimerCode)
    {
        this.forwardPrimerCode = forwardPrimerCode;
    }
    
    public String getSampleLocationTemp()
    {
        return sampleLocationTemp;
    }
    
    public void setSampleLocationTemp(String sampleLocationTemp)
    {
        this.sampleLocationTemp = sampleLocationTemp;
    }
    
    public String getDnaLocation()
    {
        return dnaLocation;
    }
    
    public void setDnaLocation(String dnaLocation)
    {
        this.dnaLocation = dnaLocation;
    }
    
    public String getVerifyScheme()
    {
        return verifyScheme;
    }
    
    public void setVerifyScheme(String verifyScheme)
    {
        this.verifyScheme = verifyScheme;
    }
    
    public boolean isResubmit()
    {
        return resubmit;
    }
    
    public void setResubmit(boolean resubmit)
    {
        this.resubmit = resubmit;
    }
    
    public Integer getResubmitCount()
    {
        return resubmitCount;
    }
    
    public void setResubmitCount(Integer resubmitCount)
    {
        this.resubmitCount = resubmitCount;
    }
    
    public String getReversePrimerLocationTemp()
    {
        return reversePrimerLocationTemp;
    }
    
    public void setReversePrimerLocationTemp(String reversePrimerLocationTemp)
    {
        this.reversePrimerLocationTemp = reversePrimerLocationTemp;
    }
    
    public String getReversePrimerCode()
    {
        return reversePrimerCode;
    }
    
    public void setReversePrimerCode(String reversePrimerCode)
    {
        this.reversePrimerCode = reversePrimerCode;
    }
    
    public BigDecimal getConcentration()
    {
        return concentration;
    }
    
    public void setConcentration(BigDecimal concentration)
    {
        this.concentration = concentration;
    }
    
    public BigDecimal getVolume()
    {
        return volume;
    }
    
    public void setVolume(BigDecimal volume)
    {
        this.volume = volume;
    }
    
    public String getCombineCode()
    {
        return combineCode;
    }
    
    public void setCombineCode(String combineCode)
    {
        this.combineCode = combineCode;
    }
}
