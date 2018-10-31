package com.todaysoft.lims.system.modules.bpm.firstpcr.model;

import java.math.BigDecimal;
import java.util.Date;

public class FirstPCRTask
{
    private String id;
    
    private String sampleName;
    
    private String sampleCode;
    
    private String forwardPrimerCode;
    
    private String dnaLocation;
    
    private String sampleLocationTemp;
    
    private String verifyScheme;//验证方案
    
    private boolean resubmit;
    
    private Integer resubmitCount;
    
    private String forwardPrimerLocationTemp;
    
    private String reversePrimerLocationTemp;
    
    private String reversePrimerCode;
    
    private BigDecimal concentration;
    
    private BigDecimal volume;
    
    private String pcrTestCode;
    
    private String combineCode;
    
    private String remark;
    
    private Integer storageStatus;
    
    private Integer ifUrgent;
    
    private Date urgentUpdateTime;
    
    private String urgentName;
    
    private Date plannedFinishDate;
    
    private Date startTime;
    
    public Date getStartTime()
    {
        return startTime;
    }

    public void setStartTime(Date startTime)
    {
        this.startTime = startTime;
    }
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getCombineCode()
    {
        return combineCode;
    }
    
    public void setCombineCode(String combineCode)
    {
        this.combineCode = combineCode;
    }
    
    public String getSampleName()
    {
        return sampleName;
    }
    
    public void setSampleName(String sampleName)
    {
        this.sampleName = sampleName;
    }
    
    public String getPcrTestCode()
    {
        return pcrTestCode;
    }
    
    public void setPcrTestCode(String pcrTestCode)
    {
        this.pcrTestCode = pcrTestCode;
    }
    
    public String getSampleLocationTemp()
    {
        return sampleLocationTemp;
    }
    
    public void setSampleLocationTemp(String sampleLocationTemp)
    {
        this.sampleLocationTemp = sampleLocationTemp;
    }
    
    public String getSampleCode()
    {
        return sampleCode;
    }
    
    public void setSampleCode(String sampleCode)
    {
        this.sampleCode = sampleCode;
    }
    
    public String getForwardPrimerLocationTemp()
    {
        return forwardPrimerLocationTemp;
    }
    
    public void setForwardPrimerLocationTemp(String forwardPrimerLocationTemp)
    {
        this.forwardPrimerLocationTemp = forwardPrimerLocationTemp;
    }
    
    public String getForwardPrimerCode()
    {
        return forwardPrimerCode;
    }
    
    public void setForwardPrimerCode(String forwardPrimerCode)
    {
        this.forwardPrimerCode = forwardPrimerCode;
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
    
    public String getDnaLocation()
    {
        return dnaLocation;
    }
    
    public void setDnaLocation(String dnaLocation)
    {
        this.dnaLocation = dnaLocation;
    }
    
    public String getRemark()
    {
        return remark;
    }
    
    public void setRemark(String remark)
    {
        this.remark = remark;
    }
    
    public Integer getStorageStatus()
    {
        return storageStatus;
    }
    
    public void setStorageStatus(Integer storageStatus)
    {
        this.storageStatus = storageStatus;
    }

    public Integer getIfUrgent()
    {
        return ifUrgent;
    }

    public void setIfUrgent(Integer ifUrgent)
    {
        this.ifUrgent = ifUrgent;
    }

    public Date getUrgentUpdateTime()
    {
        return urgentUpdateTime;
    }

    public void setUrgentUpdateTime(Date urgentUpdateTime)
    {
        this.urgentUpdateTime = urgentUpdateTime;
    }

    public String getUrgentName()
    {
        return urgentName;
    }

    public void setUrgentName(String urgentName)
    {
        this.urgentName = urgentName;
    }

    public Date getPlannedFinishDate()
    {
        return plannedFinishDate;
    }

    public void setPlannedFinishDate(Date plannedFinishDate)
    {
        this.plannedFinishDate = plannedFinishDate;
    }
    
}
